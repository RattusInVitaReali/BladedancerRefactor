package code.powers;

import com.megacrit.cardcrawl.core.AbstractCreature;

import static code.Blademaster.makeID;

public class HarmonyPower extends AbstractBlademasterPower {

    public static final String POWER_ID = makeID("Harmony");
    public static final PowerType TYPE = PowerType.BUFF;
    public static final boolean TURN_BASED = false;

    public HarmonyPower(AbstractCreature owner) {
        super(POWER_ID, TYPE, TURN_BASED, owner, -1);
    }

}
