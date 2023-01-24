package code.cards;

import code.Blademaster;
import code.Blademaster.BlademasterStance;
import code.characters.BlademasterCharacter;
import code.powers.stances.AbstractStancePower;
import code.util.BlademasterUtil;
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
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.screens.SingleCardViewPopup;

import java.util.HashMap;

import static code.Blademaster.modID;
import static code.util.BlademasterUtil.getPlayerStance;
import static code.util.BlademasterUtil.playerApplyPower;

public abstract class AbstractStanceCard extends AbstractBlademasterCard {

    protected CardStrings windCardStrings;
    protected CardStrings lightningCardStrings;
    private final BasicState BASIC_STATE = new BasicState();
    private final WindState WIND_STATE = new WindState();
    private final LightningState LIGHTNING_STATE = new LightningState();
    public int conduit;
    public int baseConduit;
    public boolean upgradedConduit;
    public boolean isConduitModified;
    public StanceState state;

    private static final HashMap<String, CardStrings> windStringsMap = new HashMap<>();
    private static final HashMap<String, CardStrings> lightningStringsMap = new HashMap<>();

    private AbstractStanceCard PREVIEW_WIND = null;
    private AbstractStanceCard PREVIEW_LIGHTNING = null;
    private boolean renderPreviewCards = false;

    public AbstractStanceCard(final String cardID, final int cost, final CardType type, final CardRarity rarity, final CardTarget target) {
        this(cardID, cost, type, rarity, target, 0, 0);
    }

    public AbstractStanceCard(final String cardID, final int cost, final CardType type, final CardRarity rarity, final CardTarget target, int furyCost, int comboCost) {
        super(cardID, cost, type, rarity, target, BlademasterCharacter.Enums.BLADEMASTER_COLOR, furyCost, comboCost);
        setStance(BlademasterStance.BASIC);
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

    private static CardStrings duplicateCardStrings(CardStrings cardStrings) {
        CardStrings newStrings = new CardStrings();
        newStrings.NAME = cardStrings.NAME;
        newStrings.DESCRIPTION = cardStrings.DESCRIPTION;
        newStrings.UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;
        if (newStrings.EXTENDED_DESCRIPTION != null) newStrings.EXTENDED_DESCRIPTION = cardStrings.EXTENDED_DESCRIPTION.clone();
        return newStrings;
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

    public void setStance(BlademasterStance stance) {
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
        if (state == new_state) {
            return;
        }
        state = new_state;
        loadCardImage(state.getCardTextureString());
        updateDescription();
        superFlash(state.getFlashColor().cpy());
    }

    public BlademasterStance getStance() {
        return state.getStance();
    }

    @Override
    public void applyPowers() {
        super.applyPowers();
        setStance(getPlayerStance());
    }

    @Override
    public AbstractCard makeStatEquivalentCopy() {
        AbstractStanceCard card = (AbstractStanceCard)super.makeStatEquivalentCopy();
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
        AbstractStancePower stance = BlademasterUtil.getPlayerStancePower();
        if (stance != null && conduit > 0) {
            playerApplyPower(p, stance.getChargePower(p, conduit));
        }
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
        PREVIEW_WIND = (AbstractStanceCard) this.makeCopy();
        if (SingleCardViewPopup.isViewingUpgrade && !PREVIEW_WIND.upgraded) {
            PREVIEW_WIND.upgrade();
            PREVIEW_WIND.displayUpgrades();
        }
        PREVIEW_WIND.setStance(BlademasterStance.WIND);
        PREVIEW_LIGHTNING = (AbstractStanceCard) this.makeCopy();
        if (SingleCardViewPopup.isViewingUpgrade && !PREVIEW_LIGHTNING.upgraded) {
            PREVIEW_LIGHTNING.upgrade();
            PREVIEW_LIGHTNING.displayUpgrades();
        }
        PREVIEW_LIGHTNING.setStance(BlademasterStance.LIGHTNING);
    }

    @Override
    public void hover() {
        super.hover();
        renderPreviewCards = true;
    }

    @Override
    public void unhover() {
        super.unhover();
        renderPreviewCards = false;
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
            PREVIEW_WIND.current_x = 485.0F * Settings.scale;
            PREVIEW_WIND.current_y = 795.0F * Settings.scale;
            PREVIEW_WIND.drawScale = 0.8F;
            PREVIEW_WIND.render(sb);
        }
        if (PREVIEW_LIGHTNING != null) {
            PREVIEW_LIGHTNING.current_x = 485.0F * Settings.scale;
            PREVIEW_LIGHTNING.current_y = 285.0F * Settings.scale;
            PREVIEW_LIGHTNING.drawScale = 0.8F;
            PREVIEW_LIGHTNING.render(sb);
        }
    }

    private abstract class StanceState {

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