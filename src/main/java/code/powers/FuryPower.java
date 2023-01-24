package code.powers;

import code.patches.BlademasterTags;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;

import static code.Blademaster.makeID;

public class FuryPower extends AbstractBlademasterPower {

    public static final String POWER_ID = makeID("Fury");
    public static final PowerType TYPE = PowerType.BUFF;
    public static final boolean TURN_BASED = true;
    private boolean canGain = true;

    public FuryPower(AbstractCreature owner, int amount) {
        super(POWER_ID, TYPE, TURN_BASED, owner, amount);
        priority = 1;
    }

    @Override
    protected boolean renderAtZero() {
        return true;
    }

    @Override
    public void atStartOfTurn() {
        if (owner != null) {
            amount = 0;
            updateDescription();
        }
    }

    @Override
    public void onUseCard(AbstractCard card, UseCardAction action) {
        if (card.hasTag(BlademasterTags.COMBO_FINISHER) || card.hasTag(BlademasterTags.FURY_FINISHER)) {
            canGain = false;
        }
    }

    @Override
    public void onAfterUseCard(AbstractCard card, UseCardAction action) {
        canGain = true;
    }

    @Override
    public void updateDescription() {
        description = powerStrings.DESCRIPTIONS[0] + amount + powerStrings.DESCRIPTIONS[1];
    }

    @Override
    public void onAttack(DamageInfo info, int damageAmount, AbstractCreature target) {
        if ((info.type == DamageInfo.DamageType.NORMAL) && (owner != null) && canGain) {
            flash();
            amount += info.output;
            updateDescription();
        }
    }

    @Override
    public void onRemove() {
        addToBot(new ApplyPowerAction(owner, owner, new FuryPower(owner, 0)));
    }

}
