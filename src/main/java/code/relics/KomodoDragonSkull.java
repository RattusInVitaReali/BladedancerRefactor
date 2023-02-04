package code.relics;

import code.powers.BleedingPower;
import com.evacipated.cardcrawl.mod.stslib.relics.OnApplyPowerRelic;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.PoisonPower;

import static code.Blademaster.makeID;

public class KomodoDragonSkull extends AbstractBlademasterRelic implements OnApplyPowerRelic {

    public static final String ID = makeID("KomodoDragonSkull");
    private static final RelicTier TIER = RelicTier.COMMON;
    private static final LandingSound LANDING_SOUND = LandingSound.SOLID;

    private static final int POISON = 2;

    public KomodoDragonSkull() {
        super(ID, TIER, LANDING_SOUND);
    }

    @Override
    public boolean onApplyPower(AbstractPower power, AbstractCreature target, AbstractCreature source) {
        if (power.ID.equals(BleedingPower.POWER_ID)) {
            flash();
            addToTop(new ApplyPowerAction(power.owner, AbstractDungeon.player, new PoisonPower(power.owner, AbstractDungeon.player, POISON)));
        }
        return true;
    }
}
