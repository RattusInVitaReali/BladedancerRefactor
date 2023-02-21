package code.powers;

import com.megacrit.cardcrawl.core.AbstractCreature;

import static code.Blademaster.makeID;

public class MassacrePower extends AbstractBlademasterPower {

    public static final String POWER_ID = makeID("Massacre");
    public static final PowerType TYPE = PowerType.BUFF;
    public static final boolean TURN_BASED = false;

    public MassacrePower(AbstractCreature owner) {
        super(POWER_ID, TYPE, TURN_BASED, owner, 1);
    }

    @Override
    public void updateDescription() {
        if (amount > 1) {
            description = powerStrings.DESCRIPTIONS[0] + powerStrings.DESCRIPTIONS[2];
        } else {
            description = powerStrings.DESCRIPTIONS[0] + powerStrings.DESCRIPTIONS[1];
        }
    }

}
