package code.powers;

import code.powers.stances.LightningChargePower;
import code.powers.stances.WindChargePower;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.powers.AbstractPower;

import static code.Blademaster.makeID;

public class StanceMasteryPower extends AbstractBlademasterPower {

    public static final String POWER_ID = makeID("StanceMastery");
    public static final PowerType TYPE = PowerType.BUFF;
    public static final boolean TURN_BASED = false;

    private boolean active = true;

    public StanceMasteryPower(AbstractCreature owner, int amount) {
        super(POWER_ID, TYPE, TURN_BASED, owner, amount);
    }

    @Override
    public void onApplyPower(AbstractPower power, AbstractCreature target, AbstractCreature source) {
        if (power.ID.equals(WindChargePower.POWER_ID)) {
            if (!active) {
                active = true;
                return;
            }
            addToBot(new ApplyPowerAction(target, source, new WindChargePower(target, amount)));
            active = false;
        }

        if (power.ID.equals(LightningChargePower.POWER_ID)) {
            if (!active) {
                active = true;
                return;
            }
            addToBot(new ApplyPowerAction(target, source, new LightningChargePower(target, amount)));
            active = false;
        }
    }

    @Override
    public void updateDescription() {
        if (amount == 1)
            description = powerStrings.DESCRIPTIONS[0] + amount + powerStrings.DESCRIPTIONS[1];
        else
            description = powerStrings.DESCRIPTIONS[0] + amount + powerStrings.DESCRIPTIONS[2];
    }

}
