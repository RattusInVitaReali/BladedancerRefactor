package code.powers;

import com.megacrit.cardcrawl.actions.common.HealAction;
import com.megacrit.cardcrawl.core.AbstractCreature;

import static code.Blademaster.makeID;

public class RegenerationPower extends AbstractBlademasterPower {

    public static final String POWER_ID = makeID("Regeneration");
    public static final PowerType TYPE = PowerType.BUFF;
    public static final boolean TURN_BASED = true;

    public RegenerationPower(AbstractCreature owner, int amount) {
        super(POWER_ID, TYPE, TURN_BASED, owner, amount);
    }

    @Override
    public void atEndOfTurn(boolean isPlayer) {
        this.flash();
        if (!this.owner.halfDead && !this.owner.isDying && !this.owner.isDead) {
            this.addToBot(new HealAction(this.owner, this.owner, this.amount));
        }

    }

    @Override
    public void updateDescription() {
        this.description = powerStrings.DESCRIPTIONS[0] + this.amount + powerStrings.DESCRIPTIONS[1];
    }

}
