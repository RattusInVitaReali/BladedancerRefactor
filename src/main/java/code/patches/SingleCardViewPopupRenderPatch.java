package code.patches;

import basemod.ReflectionHacks;
import code.cards.AbstractBlademasterCard;
import code.cards.AbstractStanceCard;
import code.util.ImageHelper;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.helpers.FontHelper;
import com.megacrit.cardcrawl.screens.SingleCardViewPopup;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class SingleCardViewPopupRenderPatch {

    private static Method renderHelperMethod;
    private static boolean renderReflectFailureNotified = false;
    private static boolean renderActualFailureNotified = false;

    public static void getRenderMethod() {
        if (renderHelperMethod == null) {
            try {
                renderHelperMethod = SingleCardViewPopup.class.getDeclaredMethod("renderHelper", SpriteBatch.class, float.class, float.class,  TextureAtlas.AtlasRegion.class);
            } catch (NoSuchMethodException e) {
                if (!renderReflectFailureNotified) {
                    renderReflectFailureNotified = true;
                    System.out.println("ALERT: Blademaster failed to reflect AbstractCard method renderHelper");
                    e.printStackTrace();
                }
            }
        }
    }

    public static void renderImage(SingleCardViewPopup __card_instance, SpriteBatch sb, float x, float y, TextureAtlas.AtlasRegion region) {
        try {
            renderHelperMethod.setAccessible(true);
            renderHelperMethod.invoke(__card_instance, sb, x, y, region);
        } catch (IllegalAccessException | InvocationTargetException e) {
            if (!renderActualFailureNotified) {
                renderActualFailureNotified = true;
                System.out.println("ALERT: Blademaster failed to invoke SingleCardViewPopup method renderHelper");
                e.printStackTrace();
            }
        }
    }

    @SpirePatch(
            clz = SingleCardViewPopup.class,
            method = "renderCost"
    )
    public static class FinisherRequirementsSCVPPatch {

        public static void Postfix(SingleCardViewPopup __instance, SpriteBatch sb) {

            getRenderMethod();

            AbstractCard card = ReflectionHacks.getPrivate(__instance, SingleCardViewPopup.class, "card");
            if (card.hasTag(BlademasterTags.FURY_FINISHER) && !card.isLocked && card.isSeen) {
                AbstractBlademasterCard blademasterCard = (AbstractBlademasterCard) card;
                renderImage(__instance, sb, (float)Settings.WIDTH / 2.0F - 270.0F * Settings.scale, (float)Settings.HEIGHT / 2.0F + 230.0F * Settings.scale, AbstractBlademasterCard.furySCVPTexture);
                Color c;
                if (blademasterCard.isFuryCostModified) {
                    c = Settings.GREEN_TEXT_COLOR;
                } else {
                    c = Settings.CREAM_COLOR;
                }
                FontHelper.renderFont(sb, FontHelper.SCP_cardEnergyFont, Integer.toString(blademasterCard.furyCost), (float)Settings.WIDTH / 2.0F - 316.0F * Settings.scale, (float)Settings.HEIGHT / 2.0F + 254.0F * Settings.scale, c);
            }

            if (card.hasTag(BlademasterTags.COMBO_FINISHER) && !card.isLocked && card.isSeen) {
                AbstractBlademasterCard blademasterCard = (AbstractBlademasterCard) card;
                renderImage(__instance, sb, (float)Settings.WIDTH / 2.0F - 270.0F * Settings.scale, (float)Settings.HEIGHT / 2.0F + 80.0F * Settings.scale, AbstractBlademasterCard.comboSCVPTexture);
                Color c;
                if (((AbstractBlademasterCard) blademasterCard).isComboCostModified) {
                    c = Settings.GREEN_TEXT_COLOR;
                } else {
                    c = Settings.CREAM_COLOR;
                }
                FontHelper.renderFont(sb, FontHelper.SCP_cardEnergyFont, Integer.toString(blademasterCard.comboCost), (float)Settings.WIDTH / 2.0F - 292.0F * Settings.scale, (float)Settings.HEIGHT / 2.0F + 104.0F * Settings.scale, c);
            }

        }

    }

    @SpirePatch(
            clz = SingleCardViewPopup.class,
            method = "renderCost"
    )
    public static class StancePreviewSCVPPatch {

        public static void Postfix(SingleCardViewPopup __instance, SpriteBatch sb) {
            AbstractCard card = ReflectionHacks.getPrivate(__instance, SingleCardViewPopup.class, "card");
            if (card instanceof AbstractStanceCard) {
                AbstractStanceCard stanceCard = (AbstractStanceCard) card;
                stanceCard.renderStancePreviewInSingleView(sb);
            }

        }

    }

}
