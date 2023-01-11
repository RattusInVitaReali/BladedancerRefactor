package code.cards;

import code.Blademaster;
import code.Blademaster.BlademasterStance;
import code.characters.BlademasterCharacter;
import code.powers.stances.AbstractStancePower;
import code.util.BlademasterUtil;
import code.util.TextureLoader;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public abstract class AbstractStanceCard extends AbstractBlademasterCard {

    private final BasicState BASIC_STATE = new BasicState();
    private final WindState WIND_STATE = new WindState();
    private final LightningState LIGHTNING_STATE = new LightningState();
    public int conduit;
    public int baseConduit;
    public boolean upgradedConduit;
    public boolean isConduitModified;
    public StanceState state;

    public AbstractStanceCard(final String cardID, final int cost, final CardType type, final CardRarity rarity, final CardTarget target) {
        super(cardID, cost, type, rarity, target, BlademasterCharacter.Enums.BLADEMASTER_COLOR);
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
        loadCardImage(state.getCardTextureString());
        flash(state.getFlashColor().cpy());
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

    protected void conduit(AbstractPlayer p) {
        AbstractStancePower stance = BlademasterUtil.getPlayerStancePower();
        if (stance != null) {
            addToBot(new ApplyPowerAction(p, p, stance.getChargePower(p, conduit)));
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

    @Override
    protected void setDescription(String description) {
        rawDescription = description;
        if (baseConduit > 0) {
            rawDescription += " " + Blademaster.modID + ":Conduit !" + Blademaster.modID + ":cn!.";
        }
        initializeDescription();
    }

    private abstract class StanceState {

        public abstract void use(AbstractPlayer p, AbstractMonster m);

        public abstract String getCardTextureString();

        public abstract Color getFlashColor();

        public abstract Texture getBackgroundOverlayTexture();

    }

    private class BasicState extends StanceState {

        @Override
        public void use(AbstractPlayer p, AbstractMonster m) {
            AbstractStanceCard.this.useBasic(p, m);
        }

        @Override
        public String getCardTextureString() {
            return AbstractBlademasterCard.getCardTextureString(AbstractStanceCard.this.name, AbstractStanceCard.this.type);
        }

        @Override
        public Color getFlashColor() {
            return Color.WHITE;
        }

        @Override
        public Texture getBackgroundOverlayTexture() {
            return null;
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
            return AbstractBlademasterCard.getCardTextureString(AbstractStanceCard.this.name, AbstractStanceCard.this.type).replace("cards/", "cards/wind/");
        }

        @Override
        public Color getFlashColor() {
            return Color.GREEN;
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

    }

    private class LightningState extends StanceState {

        @Override
        public void use(AbstractPlayer p, AbstractMonster m) {
            AbstractStanceCard.this.useLightning(p, m);
            AbstractStanceCard.this.conduit(p);
        }

        @Override
        public String getCardTextureString() {
            return AbstractBlademasterCard.getCardTextureString(AbstractStanceCard.this.name, AbstractStanceCard.this.type).replace("cards/", "cards/lightning/");
        }

        @Override
        public Color getFlashColor() {
            return Color.BLUE;
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

    }

}