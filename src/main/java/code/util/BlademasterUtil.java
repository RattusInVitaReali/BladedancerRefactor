package code.util;

import code.Blademaster;
import code.powers.stances.AbstractStancePower;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.AbstractPower;

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

}
