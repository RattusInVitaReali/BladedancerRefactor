package code.powers;

import code.patches.BlademasterTags;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardQueueItem;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static code.Blademaster.makeID;

public class BladeFormPower extends AbstractBlademasterPower {

    public static final String POWER_ID = makeID("BladeForm");
    public static final PowerType TYPE = PowerType.BUFF;
    public static final boolean TURN_BASED = true;

    private int cardsDoubledThisTurn = 0;

    public BladeFormPower(AbstractCreature owner, int amount) {
        super(POWER_ID, TYPE, TURN_BASED, owner, amount);
    }

    @Override
    public void atStartOfTurn() {
        this.cardsDoubledThisTurn = 0;
    }

    @Override
    public void onUseCard(AbstractCard card, UseCardAction action) {
        if (!card.purgeOnUse && this.amount > 0 && cardsDoubledThisTurn < this.amount && (card.hasTag(BlademasterTags.COMBO_FINISHER) || card.hasTag(BlademasterTags.FURY_FINISHER))) {
            ++this.cardsDoubledThisTurn;
            this.flash();
            AbstractMonster m = null;
            if (action.target != null) {
                m = (AbstractMonster)action.target;
            }

            AbstractCard tmp = card.makeSameInstanceOf();
            AbstractDungeon.player.limbo.addToBottom(tmp);
            tmp.current_x = card.current_x;
            tmp.current_y = card.current_y;
            tmp.target_x = (float) Settings.WIDTH / 2.0F - 300.0F * Settings.scale;
            tmp.target_y = (float)Settings.HEIGHT / 2.0F;
            if (m != null) {
                tmp.calculateCardDamage(m);
            }

            tmp.purgeOnUse = true;
            AbstractDungeon.actionManager.addCardQueueItem(new CardQueueItem(tmp, m, card.energyOnUse, true, true), true);
        }

    }

    @Override
    public void updateDescription() {
        if (amount > 1) {
            description = powerStrings.DESCRIPTIONS[1] + powerStrings.DESCRIPTIONS[2];
        } else {
            description = powerStrings.DESCRIPTIONS[0];
        }
    }
}
