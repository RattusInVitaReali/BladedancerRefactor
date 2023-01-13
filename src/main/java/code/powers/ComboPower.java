package code.powers;

import code.patches.BlademasterTags;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

import static code.Blademaster.makeID;

public class ComboPower extends AbstractBlademasterPower {

    public static final String POWER_ID = makeID("Combo");
    public static final PowerType TYPE = PowerType.BUFF;
    public static final boolean TURN_BASED = true;
    private boolean canGain = true;

    public ComboPower(AbstractCreature owner, int amount) {
        super(POWER_ID, TYPE, TURN_BASED, owner, amount);
    }

    @Override
    public void onAfterUseCard(AbstractCard card, UseCardAction action) {
        canGain = true;
    }

    @Override
    public void updateDescription() {
        description = (powerStrings.DESCRIPTIONS[0] + amount + powerStrings.DESCRIPTIONS[1]);
    }

    @Override
    public void atStartOfTurn() {
        if (AbstractDungeon.player != null) {
            amount = 0;
        }
    }

    @Override
    public void onUseCard(AbstractCard card, UseCardAction action) {
        if (card.hasTag(BlademasterTags.FINISHER)) {
            canGain = false;
        }
        if ((owner != null)) {
            addToBot(new ApplyPowerAction(owner, owner, new ComboPower(owner, 1)));
            updateDescription();
        }
    }

}
