package code.relics;

import code.cards.AbstractBlademasterCard;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.HealAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static code.Blademaster.makeID;
import static code.util.BlademasterUtil.getAliveMonsters;

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
        addToBot(new HealAction(AbstractDungeon.player, AbstractDungeon.player, HEAL));
    }
}
