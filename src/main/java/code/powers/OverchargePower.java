package code.powers;

import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

import static code.Blademaster.makeID;
import static code.util.BlademasterUtil.getPlayerLightningCharges;
import static code.util.BlademasterUtil.getPlayerWindCharges;

public class OverchargePower extends AbstractBlademasterPower {

    public static final String POWER_ID = makeID("");
    public static final PowerType TYPE = PowerType.BUFF;
    public static final boolean TURN_BASED = true;

    public OverchargePower(AbstractCreature owner) {
        super(POWER_ID, TYPE, TURN_BASED, owner, -1);
    }

    @Override
    public void update(int slot) {
        super.update(1);
        this.amount = getPlayerWindCharges() + getPlayerLightningCharges();
    }

    @Override
    public void atEndOfTurn(boolean isPlayer) {
        addToBot(new DamageAction(AbstractDungeon.getRandomMonster(), new DamageInfo(owner, amount, DamageInfo.DamageType.THORNS)));
    }

    @Override
    public void updateDescription() {
        description = powerStrings.DESCRIPTIONS[0] + amount + powerStrings.DESCRIPTIONS[1];
    }

}
