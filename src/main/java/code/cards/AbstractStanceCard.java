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
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.rooms.AbstractRoom;

import static code.Blademaster.modID;
import static code.util.BlademasterUtil.getPlayerStance;
import static code.util.BlademasterUtil.playerApplyPower;

public abstract class AbstractStanceCard extends AbstractBlademasterCard {

    protected final CardStrings cardStringsWind;
    protected final CardStrings cardStringsLightning;
    private final BasicState BASIC_STATE = new BasicState();
    private final WindState WIND_STATE = new WindState();
    private final LightningState LIGHTNING_STATE = new LightningState();
    public int conduit;
    public int baseConduit;
    public boolean upgradedConduit;
    public boolean isConduitModified;
    public StanceState state;

    private AbstractStanceCard PREVIEW_WIND = null;
    private AbstractStanceCard PREVIEW_WIND_UPGRADED = null;
    private AbstractStanceCard PREVIEW_LIGHTNING = null;
    private AbstractStanceCard PREVIEW_LIGHTNING_UPGRADED = null;
    private boolean renderPreviewCards = false;

    public AbstractStanceCard(final String cardID, final int cost, final CardType type, final CardRarity rarity, final CardTarget target) {
        this(cardID, cost, type, rarity, target, 0, 0);
    }

    public AbstractStanceCard(final String cardID, final int cost, final CardType type, final CardRarity rarity, final CardTarget target, int furyCost, int comboCost) {
        super(cardID, cost, type, rarity, target, BlademasterCharacter.Enums.BLADEMASTER_COLOR, furyCost, comboCost);
        CardStrings windStrings = CardCrawlGame.languagePack.getCardStrings(this.cardID + ":WIND");
        cardStringsWind = windStrings.NAME.equals("[MISSING_TITLE]") ? cardStrings : windStrings;
        CardStrings lightningStrings = CardCrawlGame.languagePack.getCardStrings(this.cardID + ":LIGHTNING");
        cardStringsLightning = lightningStrings.NAME.equals("[MISSING_TITLE]") ? cardStrings : lightningStrings;
        setStance(BlademasterStance.BASIC);
    }

    public void setStance(BlademasterStance stance) {
        StanceState new_state;
        switch (stance) {
            case WIND:
                new_state = WIND_STATE;
                break;
            case LIGHTNING:
                new_state = LIGHTNING_STATE;
                break;
            default:
                new_state = BASIC_STATE;
        }
        if (state == new_state) {
            return;
        }
        state = new_state;
        System.out.println("CARD CHANGED STATE: " + this.name + " STATE: " + this.state);
        loadCardImage(state.getCardTextureString());
        updateDescription();
        superFlash(state.getFlashColor().cpy());
    }

    @Override
    public void applyPowers() {
        super.applyPowers();
        setStance(getPlayerStance());
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

    @Override
    protected void setDescription(String description) {
        rawDescription = description;
        if (baseConduit > 0) {
            rawDescription += " " + Blademaster.modID + ":Conduit !" + Blademaster.modID + ":C!.";
        }
        initializeDescription();
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
        return cardStringsWind.DESCRIPTION;
    }

    protected String getWindUpgradeDescription() {
        if (cardStringsWind.UPGRADE_DESCRIPTION != null && !cardStringsWind.UPGRADE_DESCRIPTION.equals(""))
            return cardStringsWind.UPGRADE_DESCRIPTION;
        return cardStringsWind.DESCRIPTION;
    }

    protected String getLightningDescription() {
        return cardStringsLightning.DESCRIPTION;
    }

    protected String getLightningUpgradeDescription() {
        if (cardStringsLightning.UPGRADE_DESCRIPTION != null && !cardStringsLightning.UPGRADE_DESCRIPTION.equals(""))
            return cardStringsLightning.UPGRADE_DESCRIPTION;
        return cardStringsLightning.DESCRIPTION;
    }

    @Override
    public void upgrade() {
        super.upgrade();
        updateDescription();
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
            AbstractStanceCard previewWind = upgraded ? PREVIEW_WIND_UPGRADED : PREVIEW_WIND;
            AbstractStanceCard previewLightning = upgraded ? PREVIEW_LIGHTNING_UPGRADED : PREVIEW_LIGHTNING;
            if (current_x > Settings.WIDTH * .75f) {
                previewWind.current_x = previewLightning.current_x = current_x + IMG_WIDTH * drawScale * .9f;
            } else {
                previewWind.current_x = previewLightning.current_x = current_x - IMG_WIDTH * drawScale * .9f;
            }
            previewWind.current_y = current_y + IMG_HEIGHT / 2f * drawScale;
            previewLightning.current_y = current_y - IMG_HEIGHT / 6f * drawScale;
            float previewDrawScale = drawScale / 1.5f;
            previewWind.drawScale = previewLightning.drawScale = previewDrawScale;
            previewWind.render(sb);
            previewLightning.render(sb);
        }
    }

    private void generatePreviewCards() {
        if (PREVIEW_WIND == null) {
            PREVIEW_WIND = (AbstractStanceCard) this.makeCopy();
            PREVIEW_WIND.setStance(BlademasterStance.WIND);
            PREVIEW_WIND_UPGRADED = (AbstractStanceCard) this.makeCopy();
            PREVIEW_WIND_UPGRADED.setStance(BlademasterStance.WIND);
        }
        if (PREVIEW_LIGHTNING == null) {
            PREVIEW_LIGHTNING = (AbstractStanceCard) this.makeCopy();
            PREVIEW_LIGHTNING.setStance(BlademasterStance.LIGHTNING);
            PREVIEW_LIGHTNING_UPGRADED = (AbstractStanceCard) this.makeCopy();
            PREVIEW_LIGHTNING_UPGRADED.setStance(BlademasterStance.LIGHTNING);
        }
    }

    private abstract class StanceState {

        public abstract void use(AbstractPlayer p, AbstractMonster m);

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