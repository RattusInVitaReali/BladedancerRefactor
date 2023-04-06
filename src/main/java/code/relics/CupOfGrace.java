package code.relics;

import code.patches.BlademasterTags;
import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static code.Blademaster.makeID;

public class CupOfGrace extends AbstractBlademasterRelic {

    public static final String ID = makeID("CupOfGrace");
    private static final RelicTier TIER = RelicTier.BOSS;
    private static final LandingSound LANDING_SOUND = LandingSound.MAGICAL;
    private boolean triggeredThisTurn = false;

    public CupOfGrace() {
        super(ID, TIER, LANDING_SOUND);
    }

    @Override
    public void atTurnStart() {
        this.triggeredThisTurn = false;
    }

    @Override
    public void onPlayCard(AbstractCard c, AbstractMonster m) {
        if ((c.hasTag(BlademasterTags.FURY_FINISHER) || c.hasTag(BlademasterTags.COMBO_FINISHER)) && !triggeredThisTurn) {
            triggeredThisTurn = true;
            flash();
            addToBot(new RelicAboveCreatureAction(AbstractDungeon.player, this));
            addToBot(new GainEnergyAction(1));
        }
    }
}
