package code.relics;

import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static code.Blademaster.makeID;

public class BlademastersAmulet extends AbstractBlademasterRelic {

    public static final String ID = makeID("BlademastersAmulet");
    private static final RelicTier TIER = RelicTier.BOSS;
    private static final LandingSound LANDING_SOUND = LandingSound.CLINK;

    private static final int CARD_COUNT = 3;

    public BlademastersAmulet() {
        super(ID, TIER, LANDING_SOUND);
    }

    @Override
    public void atTurnStart() {
        counter = 0;
    }

    @Override
    public void onPlayCard(AbstractCard c, AbstractMonster m) {
        counter += 1;
        if (counter == 2) {
            beginPulse();
        }
        if (counter >= 3) {
            flash();
            addToBot(new RelicAboveCreatureAction(AbstractDungeon.player, this));
            addToBot(new DrawCardAction(AbstractDungeon.player, 1));
            counter = 0;
            stopPulse();
        }
    }

    @Override
    public void onVictory() {
        counter = -1;
        stopPulse();
    }

    @Override
    public void obtain() {
        if (AbstractDungeon.player.hasRelic(DancersAmulet.ID)) {
            for (int i = 0; i < AbstractDungeon.player.relics.size(); ++i) {
                if (AbstractDungeon.player.relics.get(i).relicId.equals(DancersAmulet.ID)) {
                    instantObtain(AbstractDungeon.player, i, true);
                    break;
                }
            }
        } else {
            super.obtain();
        }
    }

    @Override
    public boolean canSpawn() {
        return AbstractDungeon.player.hasRelic(DancersAmulet.ID);
    }

}
