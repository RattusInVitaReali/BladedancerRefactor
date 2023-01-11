package code.cards;

import code.Blademaster;
import code.characters.BlademasterCharacter;
import code.powers.stances.AbstractStancePower;
import code.powers.stances.LightningStance;
import code.powers.stances.WindStance;
import code.util.BlademasterUtil;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public abstract class AbstractStanceCard extends AbstractBlademasterCard {

    public int conduit;
    public int baseConduit;
    public boolean upgradedConduit;
    public boolean isConduitModified;

    public AbstractStanceCard(final String cardID, final int cost, final CardType type, final CardRarity rarity, final CardTarget target) {
        super(cardID, cost, type, rarity, target, BlademasterCharacter.Enums.BLADEMASTER_COLOR);
    }

    @Override
    public final void use(AbstractPlayer p, AbstractMonster m) {
        if (BlademasterUtil.getPlayerStance() instanceof WindStance) {
            useWind(p, m);
            conduit(p);
        } else if (BlademasterUtil.getPlayerStance() instanceof LightningStance) {
            useLightning(p, m);
            conduit(p);
        } else useBasic(p, m);
    }

    public abstract void useBasic(AbstractPlayer p, AbstractMonster m);

    public void useWind(AbstractPlayer p, AbstractMonster m) {
        useBasic(p, m);
    }

    public void useLightning(AbstractPlayer p, AbstractMonster m) {
        useBasic(p, m);
    }

    protected void conduit(AbstractPlayer p) {
        AbstractStancePower stance = BlademasterUtil.getPlayerStance();
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