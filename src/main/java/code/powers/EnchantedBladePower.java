package code.powers;

import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

import static code.Blademaster.makeID;
import static code.util.BlademasterUtil.playerApplyPower;

public class EnchantedBladePower extends AbstractBlademasterPower {

    public static final String POWER_ID = makeID("EnchantedBlade");
    public static final PowerType TYPE = PowerType.BUFF;
    public static final boolean TURN_BASED = false;

    public EnchantedBladePower(AbstractCreature owner, int amount) {
        super(POWER_ID, TYPE, TURN_BASED, owner, amount);
    }

    @Override
    public void atStartOfTurn() {
        this.flash();
        playerApplyPower(AbstractDungeon.player, new FuryPower(AbstractDungeon.player, amount));
    }

    @Override
    public void updateDescription() {
        description = powerStrings.DESCRIPTIONS[0] + amount + powerStrings.DESCRIPTIONS[1];
    }

}
