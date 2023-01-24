package code.relics;

import code.Blademaster;
import code.patches.BlademasterTags;
import code.powers.FuryPower;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;

import static code.Blademaster.makeID;

public class FancyChopsticks extends AbstractBlademasterRelic {

    public static final String ID = makeID("FancyChopsticks");
    private static final RelicTier TIER = RelicTier.COMMON;
    private static final LandingSound LANDING_SOUND = LandingSound.FLAT;

    private boolean appliedThisTurn = false;

    public FancyChopsticks() {
        super(ID, TIER, LANDING_SOUND);
    }

    @Override
    public void atTurnStart() {
        appliedThisTurn = false;
    }

    @Override
    public void onUseCard(AbstractCard card, UseCardAction action) {
        if ((card.hasTag(BlademasterTags.COMBO_FINISHER) || card.hasTag(BlademasterTags.FURY_FINISHER)) && !appliedThisTurn) {
            flash();
            addToBot(new DrawCardAction(1));
        }
    }


}
