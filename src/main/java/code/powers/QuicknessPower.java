package code.powers;

import code.Blademaster;
import code.powers.interfaces.OnStanceChangedPower;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.core.AbstractCreature;

import static code.Blademaster.makeID;

public class QuicknessPower extends AbstractBlademasterPower implements OnStanceChangedPower {

    public static final String POWER_ID = makeID("Quickness");
    public static final PowerType TYPE = PowerType.BUFF;
    public static final boolean TURN_BASED = false;

    public QuicknessPower(AbstractCreature owner, int amount) {
        super(POWER_ID, TYPE, TURN_BASED, owner, amount);
    }

    @Override
    public void onStanceChanged(Blademaster.BlademasterStance stance) {
        addToBot(new DrawCardAction(owner, amount));
    }

    @Override
    public void updateDescription() {
        if (amount == 1)
            description = powerStrings.DESCRIPTIONS[0] + amount + powerStrings.DESCRIPTIONS[1];
        else
            description = powerStrings.DESCRIPTIONS[0] + amount + powerStrings.DESCRIPTIONS[2];
    }
}
