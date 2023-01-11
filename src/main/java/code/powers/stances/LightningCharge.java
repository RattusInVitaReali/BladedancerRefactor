package code.powers.stances;

import code.powers.AbstractBlademasterPower;
import com.megacrit.cardcrawl.core.AbstractCreature;

import static code.Blademaster.makeID;

public class LightningCharge extends AbstractBlademasterPower {

    public static final String POWER_ID = makeID("LightningCharge");
    public static final PowerType TYPE = PowerType.BUFF;
    public static final boolean TURN_BASED = false;

    public LightningCharge(AbstractCreature owner, int amount) {
        super(POWER_ID, TYPE, TURN_BASED, owner, amount);
    }

}
