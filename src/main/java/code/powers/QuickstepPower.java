package code.powers;

import code.Blademaster;
import code.powers.interfaces.OnStanceChangedPower;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.core.AbstractCreature;

import static code.Blademaster.makeID;

public class QuickstepPower extends AbstractBlademasterPower implements OnStanceChangedPower {

    public static final String POWER_ID = makeID("Quickstep");
    public static final PowerType TYPE = PowerType.BUFF;
    public static final boolean TURN_BASED = false;

    public QuickstepPower(AbstractCreature owner, int amount) {
        super(POWER_ID, TYPE, TURN_BASED, owner, amount);
    }

    @Override
    public void onStanceChanged(Blademaster.BlademasterStance stance) {
        addToBot(new GainBlockAction(owner, amount));
    }
}
