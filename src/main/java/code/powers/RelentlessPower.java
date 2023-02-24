package code.powers;

import code.powers.interfaces.OnBloodiedPower;
import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static code.Blademaster.makeID;

public class RelentlessPower extends AbstractBlademasterPower implements OnBloodiedPower {

    public static final String POWER_ID = makeID("Relentless");
    public static final PowerType TYPE = PowerType.BUFF;
    public static final boolean TURN_BASED = false;

    public int energyGranted = 0;

    public RelentlessPower(AbstractCreature owner, int amount) {
        super(POWER_ID, TYPE, TURN_BASED, owner, amount);
        isTwoAmount = true;
    }

    @Override
    public void atStartOfTurn() {
        energyGranted = 0;
        amount2 = amount;
        updateDescription();
    }

    @Override
    public void onInitialApplication() {
        amount2 = amount;
        updateDescription();
    }

    @Override
    public void stackPower(int stackAmount) {
        super.stackPower(stackAmount);
        amount2 += stackAmount;
        updateDescription();
    }

    @Override
    public void onBloodied(AbstractMonster monster) {
        if (energyGranted < amount) {
            flash();
            ++energyGranted;
            --amount2;
            addToBot(new GainEnergyAction(1));
            updateDescription();
        }
    }

    @Override
    public void updateDescription() {
        if (amount > 1) {
            description = powerStrings.DESCRIPTIONS[1] + amount + powerStrings.DESCRIPTIONS[2];
        } else {
            description = powerStrings.DESCRIPTIONS[0];
        }
        if (amount2 == 1) {
            description += amount2 + powerStrings.DESCRIPTIONS[3];
        } else {
            description += amount2 + powerStrings.DESCRIPTIONS[4];
        }
    }

}
