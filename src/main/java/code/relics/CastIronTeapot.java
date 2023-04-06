package code.relics;

import com.megacrit.cardcrawl.actions.common.HealAction;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

import static code.Blademaster.makeID;

public class CastIronTeapot extends AbstractBlademasterRelic {

    public static final String ID = makeID("CastIronTeapot");
    private static final RelicTier TIER = RelicTier.RARE;
    private static final LandingSound LANDING_SOUND = LandingSound.FLAT;

    private static final int HEAL = 4;

    public CastIronTeapot() {
        super(ID, TIER, LANDING_SOUND);
    }

    @Override
    public void onTrigger() {
        flash();
        addToBot(new RelicAboveCreatureAction(AbstractDungeon.player, this));
        addToBot(new HealAction(AbstractDungeon.player, AbstractDungeon.player, HEAL));
    }
}
