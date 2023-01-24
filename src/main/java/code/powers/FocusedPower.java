package code.powers;

import code.powers.interfaces.OnBloodiedPower;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static code.Blademaster.makeID;

public class FocusedPower extends AbstractBlademasterPower implements OnBloodiedPower {

    public static final String POWER_ID = makeID("Focused");
    public static final PowerType TYPE = PowerType.BUFF;
    public static final boolean TURN_BASED = false;

    public FocusedPower(AbstractCreature owner, int amount) {
        super(POWER_ID, TYPE, TURN_BASED, owner, amount);
    }

    @Override
    public void onBloodied(AbstractMonster monster) {
        flash();
        addToBot(new GainBlockAction(owner, amount));
    }

    @Override
    public void updateDescription() {
        description = powerStrings.DESCRIPTIONS[0] + amount + powerStrings.DESCRIPTIONS[1];
    }

}
