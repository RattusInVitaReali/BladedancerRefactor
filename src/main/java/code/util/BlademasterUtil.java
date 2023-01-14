package code.util;

import code.Blademaster;
import code.powers.stances.AbstractStancePower;
import code.powers.stances.LightningCharge;
import code.powers.stances.WindCharge;
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

    public static int getPowerAmount(AbstractCreature c, String powerId) {
        AbstractPower power = c.getPower(powerId);
        if (power != null) {
            return power.amount;
        }
        return 0;
    }

    public static int getPlayerWindCharges() {
        return getPowerAmount(AbstractDungeon.player, WindCharge.POWER_ID);
    }

    public static int getPlayerLightningCharges() {
        return getPowerAmount(AbstractDungeon.player, LightningCharge.POWER_ID);
    }

}
