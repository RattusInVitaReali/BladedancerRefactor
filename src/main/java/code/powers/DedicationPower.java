package code.powers;

import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.PowerStrings;
import org.apache.commons.lang3.StringUtils;

import static code.Blademaster.makeID;
import static code.util.BlademasterUtil.getPlayerStance;

public class DedicationPower extends AbstractBlademasterPower {

    public static final String POWER_ID = makeID("Dedication");
    public static final PowerType TYPE = PowerType.BUFF;
    public static final boolean TURN_BASED = false;

    public DedicationPower(AbstractCreature owner, int amount) {
        super(POWER_ID, TYPE, TURN_BASED, owner, amount);
    }

    @Override
    public void onSpecificTrigger() {
        flash();
        addToBot(new DrawCardAction(owner, amount));
    }

    @Override
    public void updateDescription() {
        if (amount > 1) {
            description = powerStrings.DESCRIPTIONS[0] + StringUtils.capitalize(getPlayerStance().toString().toLowerCase()) + powerStrings.DESCRIPTIONS[1] + amount + powerStrings.DESCRIPTIONS[3];
        } else {
            description = powerStrings.DESCRIPTIONS[0] + StringUtils.capitalize(getPlayerStance().toString().toLowerCase()) + powerStrings.DESCRIPTIONS[1] + amount + powerStrings.DESCRIPTIONS[2];
        }
    }
}
