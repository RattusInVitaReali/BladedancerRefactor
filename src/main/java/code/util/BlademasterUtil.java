package code.util;

import code.Blademaster;
import code.powers.stances.AbstractStancePower;
import code.powers.stances.LightningChargePower;
import code.powers.stances.WindChargePower;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.utility.SFXAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.vfx.combat.LightningEffect;

import java.util.ArrayList;

public class BlademasterUtil {

    private static void addToBot(AbstractGameAction a) {
        AbstractDungeon.actionManager.addToBottom(a);
    }

    private static void addToTop(AbstractGameAction a) {
        AbstractDungeon.actionManager.addToTop(a);
    }

    public static void lightningEffect(AbstractCreature creature) {
        addToTop(new SFXAction("ORB_LIGHTNING_EVOKE"));
        addToTop(new VFXAction(new LightningEffect(creature.drawX, creature.drawY), .2f));
    }

    public static AbstractStancePower getPlayerStancePower() {
        if (!AbstractDungeon.isPlayerInDungeon() || AbstractDungeon.player == null)
            return null;
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
        return getPowerAmount(AbstractDungeon.player, WindChargePower.POWER_ID);
    }

    public static int getPlayerLightningCharges() {
        return getPowerAmount(AbstractDungeon.player, LightningChargePower.POWER_ID);
    }

    public static ArrayList<AbstractMonster> getAliveMonsters() {
        ArrayList<AbstractMonster> monsters = new ArrayList<>();
        if (AbstractDungeon.getMonsters() == null) return monsters;
        if (AbstractDungeon.getMonsters().monsters == null) return monsters;
        for (AbstractMonster m : AbstractDungeon.getMonsters().monsters) {
            if (!m.isDeadOrEscaped()) monsters.add(m);
        }
        return monsters;
    }

}
