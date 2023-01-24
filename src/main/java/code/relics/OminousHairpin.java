package code.relics;

import code.cards.AbstractBlademasterCard;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static code.Blademaster.makeID;
import static code.util.BlademasterUtil.getAliveMonsters;

public class OminousHairpin extends AbstractBlademasterRelic {

    public static final String ID = makeID("OminousHairpin");
    private static final RelicTier TIER = RelicTier.UNCOMMON;
    private static final LandingSound LANDING_SOUND = LandingSound.MAGICAL;

    private static final int DAMAGE = 4;

    public OminousHairpin() {
        super(ID, TIER, LANDING_SOUND);
    }

    @Override
    public void atTurnStart() {
        flash();
        for (AbstractMonster monster : getAliveMonsters()) {
            if (AbstractBlademasterCard.isBloodied(monster)) {
                addToBot(new DamageAction(monster, new DamageInfo(AbstractDungeon.player, DAMAGE, DamageInfo.DamageType.THORNS)));
            }
        }
    }

}
