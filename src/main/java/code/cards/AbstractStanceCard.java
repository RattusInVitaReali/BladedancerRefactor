package code.cards;

import basemod.helpers.TooltipInfo;
import code.Blademaster;
import code.Blademaster.BlademasterStance;
import code.characters.BlademasterCharacter;
import code.patches.BlademasterTags;
import code.powers.stances.AbstractStancePower;
import code.util.TextureLoader;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.GameDictionary;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.screens.SingleCardViewPopup;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static code.Blademaster.modID;
import static code.util.BlademasterUtil.*;

public abstract class AbstractStanceCard extends AbstractBlademasterCard {

    private static final HashMap<String, CardStrings> windStringsMap = new HashMap<>();
    private static final HashMap<String, CardStrings> lightningStringsMap = new HashMap<>();
    private final BasicState BASIC_STATE = new BasicState();
    private final WindState WIND_STATE = new WindState();
    private final LightningState LIGHTNING_STATE = new LightningState();
    public int conduit;
    public int baseConduit;
    public boolean upgradedConduit;
    public boolean isConduitModified;
    public StanceState state;
    protected CardStrings windCardStrings;
    protected CardStrings lightningCardStrings;
    private AbstractStanceCard PREVIEW_WIND = null;
    private AbstractStanceCard PREVIEW_LIGHTNING = null;
    private boolean renderPreviewCards = false;

    private boolean stancePreviewDisplayUpgrades = false;

    public AbstractStanceCard(final String cardID, final int cost, final CardType type, final CardRarity rarity, final CardTarget target) {
        this(cardID, cost, type, rarity, target, 0, 0);
    }

    public AbstractStanceCard(final String cardID, final int cost, final CardType type, final CardRarity rarity, final CardTarget target, int furyCost, int comboCost) {
        this(cardID, cost, type, rarity, target, BlademasterCharacter.Enums.BLADEMASTER_COLOR, furyCost, comboCost);
    }

    public AbstractStanceCard(final String cardID, final int cost, final CardType type, final CardRarity rarity, final CardTarget target, final CardColor color, int furyCost, int comboCost) {
        super(cardID, cost, type, rarity, target, color, furyCost, comboCost);
        setStance(BlademasterStance.BASIC);
    }

    private static CardStrings duplicateCardStrings(CardStrings cardStrings) {
        CardStrings newStrings = new CardStrings();
        newStrings.NAME = cardStrings.NAME;
        newStrings.DESCRIPTION = cardStrings.DESCRIPTION;
        newStrings.UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;
        if (newStrings.EXTENDED_DESCRIPTION != null)
            newStrings.EXTENDED_DESCRIPTION = cardStrings.EXTENDED_DESCRIPTION.clone();
        return newStrings;
    }

    CardStrings getStanceCardStrings(HashMap<String, CardStrings> map, String cardID, String STANCE) {
        if (!map.containsKey(cardID)) {
            map.put(cardID, loadStanceCardStrings(cardID, STANCE));
        }
        return map.get(cardID);
    }

    private CardStrings loadStanceCardStrings(String cardID, String STANCE) {
        CardStrings strings = loadCardStrings(cardID + ":" + STANCE);
        if (strings.NAME.equals("[MISSING_TITLE]")) {
            strings = duplicateCardStrings(cardStrings);
        }
        processCardStrings(strings);
        return strings;
    }

    private void processCardStrings(CardStrings strings) {
        strings.DESCRIPTION = addConduit(strings.DESCRIPTION);
        if (strings.UPGRADE_DESCRIPTION != null) strings.UPGRADE_DESCRIPTION = addConduit(strings.UPGRADE_DESCRIPTION);
    }

    private String addConduit(String description) {
        if (baseConduit > 0) {
            if (description.endsWith("Exhaust.")) {
                description = description.replace("Exhaust.", "blademaster:Conduit !blademaster:C!. NL Exhaust.");
            } else {
                description += " NL blademaster:Conduit !blademaster:C!.";
            }
        }
        return description;
    }

    protected void setBaseConduit(int baseConduit) {
        this.baseConduit = this.conduit = baseConduit;
        updateDescription();
    }

    public BlademasterStance getStance() {
        if (state == null) return BlademasterStance.NONE;
        return state.getStance();
    }

    public void setStance(BlademasterStance stance) {
        if (getStance() == stance) return;
        StanceState new_state;
        switch (stance) {
            case WIND:
                windCardStrings = getStanceCardStrings(windStringsMap, this.cardID, "WIND");
                new_state = WIND_STATE;
                break;
            case LIGHTNING:
                lightningCardStrings = getStanceCardStrings(lightningStringsMap, this.cardID, "LIGHTNING");
                new_state = LIGHTNING_STATE;
                break;
            default:
                new_state = BASIC_STATE;
        }
        state = new_state;
        loadCardImage(state.getCardTextureString());
        updateDescription();
        superFlash(state.getFlashColor().cpy());
    }

    @Override
    public void applyPowers() {
        super.applyPowers();
        setStance(getPlayerStance());
    }

    @Override
    public AbstractCard makeStatEquivalentCopy() {
        AbstractStanceCard card = (AbstractStanceCard) super.makeStatEquivalentCopy();
        card.setStance(getStance());
        return card;
    }

    public Texture getBackgroundOverlayTexture() {
        if (state != null)
            return state.getBackgroundOverlayTexture();
        return null;
    }

    @Override
    public final void use(AbstractPlayer p, AbstractMonster m) {
        state.use(p, m);
    }

    public abstract void useBasic(AbstractPlayer p, AbstractMonster m);

    public void useWind(AbstractPlayer p, AbstractMonster m) {
        useBasic(p, m);
    }

    public void useLightning(AbstractPlayer p, AbstractMonster m) {
        useBasic(p, m);
    }

    public void onStanceChanged(BlademasterStance stance) {

    }

    protected void conduit(AbstractPlayer p) {
        conduit(p, conduit);
    }

    protected void conduit(AbstractPlayer p, int amount) {
        if (amount == 0) return;
        AbstractStancePower power = getPlayerStancePower();
        AbstractPower chargesPower = null;
        if (power != null)
            chargesPower = power.getChargePower(p, amount);
        if (chargesPower != null)
            playerApplyPower(p, chargesPower);
    }

    @Override
    public void resetAttributes() {
        super.resetAttributes();
        conduit = baseConduit;
        isConduitModified = false;
    }

    @Override
    public void displayUpgrades() {
        super.displayUpgrades();
        if (upgradedConduit) {
            conduit = baseConduit;
            isConduitModified = true;
        }
        stancePreviewDisplayUpgrades = true;
    }

    protected void upgradeConduit(int amount) {
        baseConduit += amount;
        conduit = baseConduit;
        upgradedConduit = true;
    }

    protected void updateDescription() {
        if (upgraded) {
            setDescription(state.getUpgradeDescription());
        } else {
            setDescription(state.getDescription());
        }
    }

    protected String getDescription() {
        return cardStrings.DESCRIPTION;
    }

    protected String getUpgradeDescription() {
        if (cardStrings.UPGRADE_DESCRIPTION != null && !cardStrings.UPGRADE_DESCRIPTION.equals(""))
            return cardStrings.UPGRADE_DESCRIPTION;
        return cardStrings.DESCRIPTION;
    }

    protected String getWindDescription() {
        return windCardStrings.DESCRIPTION;
    }

    protected String getWindUpgradeDescription() {
        if (windCardStrings.UPGRADE_DESCRIPTION != null && !windCardStrings.UPGRADE_DESCRIPTION.equals(""))
            return windCardStrings.UPGRADE_DESCRIPTION;
        return windCardStrings.DESCRIPTION;
    }

    protected String getLightningDescription() {
        return lightningCardStrings.DESCRIPTION;
    }

    protected String getLightningUpgradeDescription() {
        if (lightningCardStrings.UPGRADE_DESCRIPTION != null && !lightningCardStrings.UPGRADE_DESCRIPTION.equals(""))
            return lightningCardStrings.UPGRADE_DESCRIPTION;
        return lightningCardStrings.DESCRIPTION;
    }

    @Override
    public void upgrade() {
        super.upgrade();
        updateDescription();
    }

    private void generatePreviewCards() {
        if (PREVIEW_WIND != null && PREVIEW_LIGHTNING != null) return;
        PREVIEW_WIND = (AbstractStanceCard) this.makeStatEquivalentCopy();
        if (SingleCardViewPopup.isViewingUpgrade) {
            PREVIEW_WIND.upgrade();
            PREVIEW_WIND.displayUpgrades();
        }
        if (stancePreviewDisplayUpgrades) PREVIEW_WIND.displayUpgrades();
        PREVIEW_WIND.setStance(BlademasterStance.WIND);
        PREVIEW_LIGHTNING = (AbstractStanceCard) this.makeStatEquivalentCopy();
        if (SingleCardViewPopup.isViewingUpgrade) {
            PREVIEW_LIGHTNING.upgrade();
            PREVIEW_LIGHTNING.displayUpgrades();
        }
        if (stancePreviewDisplayUpgrades) PREVIEW_LIGHTNING.displayUpgrades();
        PREVIEW_LIGHTNING.setStance(BlademasterStance.LIGHTNING);
        initializeDescription();
    }

    @Override
    public void hover() {
        super.hover();
        renderPreviewCards = true;
    }

    @Override
    public void unhover() {
        super.unhover();
        PREVIEW_WIND = null;
        PREVIEW_LIGHTNING = null;
        renderPreviewCards = false;
    }

    // Ugly but works, boli me kurac
    @Override
    public List<TooltipInfo> getCustomTooltips() {
        List<TooltipInfo> list = new ArrayList<>();
        if (rawDescription.contains("ChargeIcon]")
                || (PREVIEW_WIND != null && PREVIEW_WIND.rawDescription.contains("ChargeIcon]"))
                || (PREVIEW_LIGHTNING != null && PREVIEW_LIGHTNING.rawDescription.contains("ChargeIcon]"))) {
            list.add(new TooltipInfo(tooltips[0], tooltips[1]));
        }
        if (rawDescription.contains("]Wind[")
                || (PREVIEW_WIND != null && PREVIEW_WIND.rawDescription.contains("]Wind["))
                || (PREVIEW_LIGHTNING != null && PREVIEW_LIGHTNING.rawDescription.contains("]Wind["))) {
            list.add(new TooltipInfo(tooltips[8], tooltips[9]));
        }
        if (rawDescription.contains("]Lightning[")
                || (PREVIEW_WIND != null && PREVIEW_WIND.rawDescription.contains("]Lightning["))
                || (PREVIEW_LIGHTNING != null && PREVIEW_LIGHTNING.rawDescription.contains("]Lightning["))) {
            list.add(new TooltipInfo(tooltips[10], tooltips[11]));
        }
        if (rawDescription.contains("]Basic[")
                || (PREVIEW_WIND != null && PREVIEW_WIND.rawDescription.contains("]Basic["))
                || (PREVIEW_LIGHTNING != null && PREVIEW_LIGHTNING.rawDescription.contains("]Basic["))) {
            list.add(new TooltipInfo(tooltips[12], tooltips[13]));
        }
        if (hasTag(BlademasterTags.FURY_FINISHER) ||
                rawDescription.contains("]Fury[")) {
            list.add(new TooltipInfo(tooltips[2], tooltips[3]));
        }
        if (hasTag(BlademasterTags.COMBO_FINISHER) ||
                rawDescription.contains("]Combo[")) {
            list.add(new TooltipInfo(tooltips[4], tooltips[5]));
        }
        return list;
    }

    public void getStanceVariantKeywords() {
        if (PREVIEW_WIND == null || PREVIEW_LIGHTNING == null) return;
        for (String word : PREVIEW_WIND.rawDescription.split(" ")) {
            String keywordTmp = word.toLowerCase();
            keywordTmp = keywordTmp.replace(",", "");
            keywordTmp = keywordTmp.replace(".", "");
            keywordTmp = dedupeKeyword(keywordTmp);
            if (GameDictionary.keywords.containsKey(keywordTmp)) {
                if (!this.keywords.contains(keywordTmp))
                    this.keywords.add(keywordTmp);
            }
        }
        for (String word : PREVIEW_LIGHTNING.rawDescription.split(" ")) {
            String keywordTmp = word.toLowerCase();
            keywordTmp = keywordTmp.replace(",", "");
            keywordTmp = keywordTmp.replace(".", "");
            keywordTmp = dedupeKeyword(keywordTmp);
            if (GameDictionary.keywords.containsKey(keywordTmp)) {
                if (!this.keywords.contains(keywordTmp))
                    this.keywords.add(keywordTmp);
            }
        }
    }

    private String dedupeKeyword(String keyword) {
        String retVal = GameDictionary.parentWord.get(keyword);
        if (retVal != null)
            return retVal;
        return keyword;
    }

    @Override
    public void initializeDescription() {
        super.initializeDescription();
        getStanceVariantKeywords();
    }

    @Override
    public void renderCardTip(SpriteBatch sb) {
        super.renderCardTip(sb);
        if (!Settings.hideCards && this.renderPreviewCards) {
            if (AbstractDungeon.player != null && AbstractDungeon.player.isDraggingCard) {
                return;
            }
            generatePreviewCards();
            if (current_x > Settings.WIDTH * .75f) {
                PREVIEW_WIND.current_x = PREVIEW_LIGHTNING.current_x = current_x + IMG_WIDTH * drawScale * .9f;
            } else {
                PREVIEW_WIND.current_x = PREVIEW_LIGHTNING.current_x = current_x - IMG_WIDTH * drawScale * .9f;
            }
            PREVIEW_WIND.current_y = current_y + IMG_HEIGHT / 2f * drawScale;
            PREVIEW_LIGHTNING.current_y = current_y - IMG_HEIGHT / 6f * drawScale;
            float previewDrawScale = drawScale / 1.5f;
            PREVIEW_WIND.drawScale = PREVIEW_LIGHTNING.drawScale = previewDrawScale;
            PREVIEW_WIND.render(sb);
            PREVIEW_LIGHTNING.render(sb);
        }
    }

    public void renderStancePreviewInSingleView(SpriteBatch sb) {
        generatePreviewCards();
        if (PREVIEW_WIND != null) {
            PREVIEW_WIND.current_x = Settings.WIDTH * 0.5F - 480.0F * Settings.scale;
            PREVIEW_WIND.current_y = 795.0F * Settings.scale;
            PREVIEW_WIND.drawScale = 0.8F;
            PREVIEW_WIND.render(sb);
        }
        if (PREVIEW_LIGHTNING != null) {
            PREVIEW_LIGHTNING.current_x = Settings.WIDTH * 0.5F - 480.0F * Settings.scale;
            PREVIEW_LIGHTNING.current_y = 285.0F * Settings.scale;
            PREVIEW_LIGHTNING.drawScale = 0.8F;
            PREVIEW_LIGHTNING.render(sb);
        }
    }

    @Override
    public void renderCardPreviewInSingleView(SpriteBatch sb) {
        cardsToPreview.current_x = Settings.WIDTH * 0.5F - 750.0F * Settings.scale;
        cardsToPreview.current_y = 795.0F * Settings.scale;
        cardsToPreview.drawScale = 0.8F;
        if (cardsToPreview.upgraded) {
            cardsToPreview.displayUpgrades();
        }
        cardsToPreview.render(sb);
    }

    @Override
    public void renderCardPreview(SpriteBatch sb) {
        if (AbstractDungeon.player == null || !AbstractDungeon.player.isDraggingCard) {
            float tmpScale = drawScale * 0.8F;
            if (this.current_x > (float) Settings.WIDTH * 0.75F) {
                cardsToPreview.current_x = current_x + (IMG_WIDTH * 0.9F + IMG_WIDTH * 0.9F * 0.8F + 16.0F) * drawScale;
            } else {
                cardsToPreview.current_x = current_x - (IMG_WIDTH * 0.9F + IMG_WIDTH * 0.9F * 0.8F + 16.0F) * drawScale;
            }

            cardsToPreview.current_y = current_y + (IMG_HEIGHT / 2.0F - IMG_HEIGHT / 2.0F * 0.8F) * drawScale;
            cardsToPreview.drawScale = tmpScale;
            cardsToPreview.render(sb);
        }
    }

    private static abstract class StanceState {

        public abstract void use(AbstractPlayer p, AbstractMonster m);

        public abstract BlademasterStance getStance();

        public abstract String getCardTextureString();

        public abstract Color getFlashColor();

        public abstract Texture getBackgroundOverlayTexture();

        public abstract String getDescription();

        public abstract String getUpgradeDescription();

    }

    private class BasicState extends StanceState {

        @Override
        public void use(AbstractPlayer p, AbstractMonster m) {
            AbstractStanceCard.this.useBasic(p, m);
        }

        @Override
        public BlademasterStance getStance() {
            return BlademasterStance.BASIC;
        }

        @Override
        public String getCardTextureString() {
            return AbstractBlademasterCard.getCardTextureString(AbstractStanceCard.this.cardID.replace(modID + ":", ""), AbstractStanceCard.this.type);
        }

        @Override
        public Color getFlashColor() {
            return Color.WHITE;
        }

        @Override
        public Texture getBackgroundOverlayTexture() {
            return null;
        }

        @Override
        public String getDescription() {
            return AbstractStanceCard.this.getDescription();
        }

        @Override
        public String getUpgradeDescription() {
            return AbstractStanceCard.this.getUpgradeDescription();
        }

    }

    private class WindState extends StanceState {

        @Override
        public void use(AbstractPlayer p, AbstractMonster m) {
            AbstractStanceCard.this.useWind(p, m);
            AbstractStanceCard.this.conduit(p);
        }

        @Override
        public BlademasterStance getStance() {
            return BlademasterStance.WIND;
        }

        @Override
        public String getCardTextureString() {
            String textureString = BASIC_STATE.getCardTextureString().replace("cards/", "cards/wind/");
            FileHandle h = Gdx.files.internal(textureString);
            if (!h.exists()) {
                return BASIC_STATE.getCardTextureString();
            }
            return textureString;
        }

        @Override
        public Color getFlashColor() {
            return Color.GREEN.cpy();
        }

        @Override
        public Texture getBackgroundOverlayTexture() {
            String cardType = "";
            switch (AbstractStanceCard.this.type) {
                case ATTACK:
                    cardType = "Attack";
                    break;
                case SKILL:
                    cardType = "Skill";
                    break;
                case POWER:
                    cardType = "Power";
                    break;
            }
            return TextureLoader.getTexture(Blademaster.modID + "Resources/images/512/Wind" + cardType + "Small.png");
        }

        @Override
        public String getDescription() {
            return AbstractStanceCard.this.getWindDescription();
        }

        @Override
        public String getUpgradeDescription() {
            return AbstractStanceCard.this.getWindUpgradeDescription();
        }

    }

    private class LightningState extends StanceState {

        @Override
        public void use(AbstractPlayer p, AbstractMonster m) {
            AbstractStanceCard.this.useLightning(p, m);
            AbstractStanceCard.this.conduit(p);
        }

        @Override
        public BlademasterStance getStance() {
            return BlademasterStance.LIGHTNING;
        }

        @Override
        public String getCardTextureString() {
            String textureString = BASIC_STATE.getCardTextureString().replace("cards/", "cards/lightning/");
            FileHandle h = Gdx.files.internal(textureString);
            if (!h.exists()) {
                return BASIC_STATE.getCardTextureString();
            }
            return textureString;
        }

        @Override
        public Color getFlashColor() {
            return Color.BLUE.cpy();
        }

        @Override
        public Texture getBackgroundOverlayTexture() {
            String cardType = "";
            switch (AbstractStanceCard.this.type) {
                case ATTACK:
                    cardType = "Attack";
                    break;
                case SKILL:
                    cardType = "Skill";
                    break;
                case POWER:
                    cardType = "Power";
                    break;
            }
            return TextureLoader.getTexture(Blademaster.modID + "Resources/images/512/Lightning" + cardType + "Small.png");
        }

        @Override
        public String getDescription() {
            return AbstractStanceCard.this.getLightningDescription();
        }

        @Override
        public String getUpgradeDescription() {
            return AbstractStanceCard.this.getLightningUpgradeDescription();
        }

    }

}