package code.relics;

import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static code.Blademaster.makeID;

public class DancersAmulet extends AbstractBlademasterRelic {

    public static final String ID = makeID("DancersAmulet");
    private static final RelicTier TIER = RelicTier.STARTER;
    private static final LandingSound LANDING_SOUND = LandingSound.MAGICAL;

    public DancersAmulet() {
        super(ID, TIER, LANDING_SOUND);
    }

    @Override
    public void atTurnStart() {
        counter = 0;
    }

    @Override
    public void onPlayCard(AbstractCard c, AbstractMonster m) {
        counter++;
        if (counter >= 3) {
            flash();
            addToBot(new RelicAboveCreatureAction(AbstractDungeon.player, this));
            addToBot(new DrawCardAction(AbstractDungeon.player, 1));
            counter = 0;
        }
    }

    @Override
    public void onVictory() {
        counter = -1;
    }

}