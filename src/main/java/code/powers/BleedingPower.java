package code.powers;

import com.megacrit.cardcrawl.core.AbstractCreature;

import static code.Blademaster.makeID;

public class BleedingPower extends AbstractBlademasterPower {

    public static final String POWER_ID = makeID("Bleeding");
    public static final PowerType TYPE = PowerType.DEBUFF;
    public static final boolean TURN_BASED = true;

    public BleedingPower(AbstractCreature owner, int amount) {
        super(POWER_ID, TYPE, TURN_BASED, owner, amount);
    }

}
