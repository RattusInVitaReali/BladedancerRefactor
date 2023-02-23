package code.powers;

import com.megacrit.cardcrawl.core.AbstractCreature;

import static code.Blademaster.makeID;

public class MarkedForDeathPower extends AbstractBlademasterPower {

    public static final String POWER_ID = makeID("MarkedForDeath");
    public static final PowerType TYPE = PowerType.DEBUFF;
    public static final boolean TURN_BASED = false;

    public MarkedForDeathPower(AbstractCreature owner) {
        super(POWER_ID, TYPE, TURN_BASED, owner, -1);
    }

}
