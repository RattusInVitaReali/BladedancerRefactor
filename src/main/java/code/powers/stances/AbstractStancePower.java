package code.powers.stances;

import code.Blademaster;
import code.actions.UpdateStanceAction;
import code.powers.AbstractBlademasterPower;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.powers.AbstractPower;

public abstract class AbstractStancePower extends AbstractBlademasterPower {

    public static final PowerType TYPE = PowerType.BUFF;
    public static final boolean TURN_BASED = false;

    public AbstractStancePower(String ID, PowerType powerType, boolean isTurnBased, AbstractCreature owner, int amount) {
        super(ID, powerType, isTurnBased, owner, amount);
    }

    public abstract AbstractPower getChargePower(AbstractCreature owner, int amount);

    public abstract Blademaster.BlademasterStance getStance();

    @Override
    public void onInitialApplication() {
        addToBot(new UpdateStanceAction());
    }

    @Override
    public void onApplyPower(AbstractPower power, AbstractCreature target, AbstractCreature source) {
        if (power.getClass() == this.getClass()) {
            return;
        }
        if (power instanceof AbstractStancePower) {
            addToTop(new RemoveSpecificPowerAction(this.owner, this.owner, this));
        }
    }

}
