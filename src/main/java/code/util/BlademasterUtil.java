package code.util;

import code.Blademaster;
import code.powers.stances.AbstractStancePower;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.AbstractPower;

import static code.Blademaster.makeID;

public class BlademasterUtil {

    public static AbstractStancePower getPlayerStancePower() {
        for (AbstractPower power : AbstractDungeon.player.powers) {
            if (power instanceof AbstractStancePower) return (AbstractStancePower) power;
        }
        return null;
    }

    public static Blademaster.BlademasterStance getPlayerStance() {
        AbstractStancePower stancePower = getPlayerStancePower();
        if (stancePower != null)
            return stancePower.getStance();
        return Blademaster.BlademasterStance.BASIC;
    }

    public static void playerApplyPower(AbstractCreature target, AbstractPower power) {
        AbstractPlayer p = AbstractDungeon.player;
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(target, p, power));
    }

    public static int getPlayerWindCharges() {
        if (AbstractDungeon.player.hasPotion(makeID("WindCharge"))) {
            return AbstractDungeon.player.getPower(makeID("WindCharge")).amount;
        }
        return 0;
    }

    public static int getPlayerLightningCharges() {
        if (AbstractDungeon.player.hasPotion(makeID("LightningCharge"))) {
            return AbstractDungeon.player.getPower(makeID("LightningCharge")).amount;
        }
        return 0;
    }

}
