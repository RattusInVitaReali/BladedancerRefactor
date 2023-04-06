package code.patches;

import code.powers.stances.LightningStancePower;
import code.powers.stances.WindStancePower;
import code.relics.PaperDregon;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.VulnerablePower;
import com.megacrit.cardcrawl.powers.WeakPower;

public class PaperDregonPatch {

    @SpirePatch(
            clz = VulnerablePower.class,
            method = "atDamageReceive"
    )
    public static class VulnerableDamagePatch {

        public static float Postfix(float __retval, VulnerablePower __instance, float damage, DamageInfo.DamageType type) {
            if (type == DamageInfo.DamageType.NORMAL && __instance.owner != null && !__instance.owner.isPlayer && AbstractDungeon.player.hasRelic(PaperDregon.ID) && AbstractDungeon.player.hasPower(LightningStancePower.POWER_ID)) {
                return damage * 1.75F;
            }
            return __retval;
        }
    }

    @SpirePatch(
            clz = VulnerablePower.class,
            method = "updateDescription"
    )
    public static class VulnerableDescriptionPatch {

        public static void Postfix(VulnerablePower __instance) {
            if (__instance.owner != null && !__instance.owner.isPlayer && AbstractDungeon.player.hasRelic(PaperDregon.ID) && AbstractDungeon.player.hasPower(LightningStancePower.POWER_ID)) {
                if (__instance.amount == 1)
                    __instance.description = VulnerablePower.DESCRIPTIONS[0] + 75 + VulnerablePower.DESCRIPTIONS[1] + __instance.amount + VulnerablePower.DESCRIPTIONS[2];
                else
                    __instance.description = VulnerablePower.DESCRIPTIONS[0] + 75 + VulnerablePower.DESCRIPTIONS[1] + __instance.amount + VulnerablePower.DESCRIPTIONS[3];
            }
        }

    }

    @SpirePatch(
            clz = WeakPower.class,
            method = "atDamageGive"
    )
    public static class WeakDamagePatch {

        public static float Postfix(float __retval, WeakPower __instance, float damage, DamageInfo.DamageType type) {
            if (type == DamageInfo.DamageType.NORMAL && !__instance.owner.isPlayer && AbstractDungeon.player.hasRelic(PaperDregon.ID) && AbstractDungeon.player.hasPower(WindStancePower.POWER_ID)) {
                return damage * 0.6f;
            }
            return __retval;
        }

    }

    @SpirePatch(
            clz = WeakPower.class,
            method = "updateDescription"
    )
    public static class WeakDescriptionPatch {

        public static void Postfix(WeakPower __instance) {
            if (__instance.owner != null && !__instance.owner.isPlayer && AbstractDungeon.player.hasRelic(PaperDregon.ID) && AbstractDungeon.player.hasPower(WindStancePower.POWER_ID)) {
                if (__instance.amount == 1)
                    __instance.description = WeakPower.DESCRIPTIONS[0] + 40 + WeakPower.DESCRIPTIONS[1] + __instance.amount + WeakPower.DESCRIPTIONS[2];
                else
                    __instance.description = WeakPower.DESCRIPTIONS[0] + 40 +WeakPower. DESCRIPTIONS[1] + __instance.amount + WeakPower.DESCRIPTIONS[3];
            }
        }

    }

}
