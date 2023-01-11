package code.cards;

import code.Blademaster;
import code.Blademaster.BlademasterStance;
import code.characters.BlademasterCharacter;
import code.powers.stances.AbstractStancePower;
import code.util.BlademasterUtil;
import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public abstract class AbstractStanceCard extends AbstractBlademasterCard {

    public int conduit;
    public int baseConduit;
    public boolean upgradedConduit;
    public boolean isConduitModified;

    private final BasicState BASIC_STATE = new BasicState();
    private final WindState WIND_STATE = new WindState();
    private final LightningState LIGHTNING_STATE = new LightningState();

    private StanceState state;

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
        //flash(state.getFlashColor()); NO FLASHING IT BREAKS THE GAME
    }

    private abstract class StanceState {

        public abstract void use(AbstractPlayer p, AbstractMonster m);

        public abstract String getCardTextureString();

        public abstract Color getFlashColor();

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

    protected String getWindTextureString() {
        return getCardTextureString(this.name, this.type).replace("cards/", "cards/Wind");
    }

    protected String getLightningTextureString() {
        return getCardTextureString(this.name, this.type).replace("cards/", "cards/Lightning");
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
            rawDescription += " Conduit !" + Blademaster.modID + ":cn!.";
        }
        initializeDescription();
    }

}