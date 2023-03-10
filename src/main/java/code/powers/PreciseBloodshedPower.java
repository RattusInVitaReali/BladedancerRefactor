package code.powers;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.ArtifactPower;

import static code.Blademaster.makeID;

public class PreciseBloodshedPower extends AbstractBlademasterPower {

    public static final String POWER_ID = makeID("PreciseBloodshed");
    public static final PowerType TYPE = PowerType.BUFF;
    public static final boolean TURN_BASED = true;

    private boolean active = true;

    public PreciseBloodshedPower(AbstractCreature owner, int amount) {
        super(POWER_ID, TYPE, TURN_BASED, owner, amount);
    }

    @Override
    public void onApplyPower(AbstractPower power, AbstractCreature target, AbstractCreature source) {
        if (power.ID.equals(BleedingPower.POWER_ID) && source == owner && !target.hasPower(ArtifactPower.POWER_ID)) {
            if (!active) {
                active = true;
                return;
            }
            addToTop(new ApplyPowerAction(target, owner, new BleedingPower(target, power.amount)));
            active = false;
        }
    }

    @Override
    public void atEndOfRound() {
        if (this.amount == 0) {
            this.addToBot(new RemoveSpecificPowerAction(this.owner, this.owner, this));
        } else {
            this.addToBot(new ReducePowerAction(this.owner, this.owner, this, 1));
        }
    }

    @Override
    public void updateDescription() {
        if (amount > 1) {
            description = powerStrings.DESCRIPTIONS[1] + amount + powerStrings.DESCRIPTIONS[2];
        } else
            description = powerStrings.DESCRIPTIONS[0];
    }

}
