package code.powers;

import code.powers.stances.LightningChargePower;
import code.powers.stances.WindChargePower;
import com.evacipated.cardcrawl.mod.stslib.powers.interfaces.BetterOnApplyPowerPower;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.powers.AbstractPower;

import static code.Blademaster.makeID;

public class OverchargePower extends AbstractBlademasterPower implements BetterOnApplyPowerPower {

    public static final String POWER_ID = makeID("Overcharge");
    public static final PowerType TYPE = PowerType.BUFF;
    public static final boolean TURN_BASED = false;

    public OverchargePower(AbstractCreature owner, int amount) {
        super(POWER_ID, TYPE, TURN_BASED, owner, amount);
    }

    @Override
    public boolean betterOnApplyPower(AbstractPower power, AbstractCreature target, AbstractCreature source) {
        if (power.ID.equals(WindChargePower.POWER_ID) || power.ID.equals(LightningChargePower.POWER_ID)) {
            flash();
            power.amount += amount;
        }
        return true;
    }

    @Override
    public int betterOnApplyPowerStacks(AbstractPower power, AbstractCreature target, AbstractCreature source, int stackAmount) {
        if (power.ID.equals(WindChargePower.POWER_ID) || power.ID.equals(LightningChargePower.POWER_ID)) {
            flash();
            return stackAmount + amount;
        }
        return stackAmount;
    }

    @Override
    public void updateDescription() {
        if (amount == 1)
            description = powerStrings.DESCRIPTIONS[0] + amount + powerStrings.DESCRIPTIONS[1];
        else
            description = powerStrings.DESCRIPTIONS[0] + amount + powerStrings.DESCRIPTIONS[2];
    }

}
