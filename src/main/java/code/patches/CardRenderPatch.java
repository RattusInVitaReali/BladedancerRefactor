package code.patches;

import basemod.ReflectionHacks;
import code.cards.AbstractBlademasterCard;
import code.cards.AbstractStanceCard;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.helpers.FontHelper;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class CardRenderPatch {

    private static Method cardRenderHelperMethod;
    private static boolean renderReflectFailureNotified = false;
    private static boolean renderActualFailureNotified = false;

    public static void getRenderMethod() {
        if (cardRenderHelperMethod == null) {
            try {
                cardRenderHelperMethod = AbstractCard.class.getDeclaredMethod("renderHelper", SpriteBatch.class, Color.class, Texture.class, float.class, float.class);
            } catch (NoSuchMethodException e) {
                if (!renderReflectFailureNotified) {
                    renderReflectFailureNotified = true;
                    System.out.println("ALERT: Blademaster failed to reflect AbstractCard method renderHelper");
                    e.printStackTrace();
                }
            }
        }
    }

    public static void renderImage(AbstractCard __card_instance, SpriteBatch sb, float x, float y, Texture texture) {
        try {
            Color reflectedColor = ReflectionHacks.getPrivate(__card_instance, AbstractCard.class, "renderColor");
            cardRenderHelperMethod.setAccessible(true);
            cardRenderHelperMethod.invoke(__card_instance, sb, reflectedColor, texture, x, y);
        } catch (IllegalAccessException | InvocationTargetException e) {
            if (!renderActualFailureNotified) {
                renderActualFailureNotified = true;
                System.out.println("ALERT: Blademaster failed to invoke AbstractCard method renderHelper");
                e.printStackTrace();
            }
        }
    }

    @SpirePatch(
            clz = AbstractCard.class,
            method = "renderCardBg"
    )
    public static class BackgroundColorPatch {

        public static void Postfix(AbstractCard __card_instance, SpriteBatch sb, float x, float y) {

            getRenderMethod();

            if (__card_instance instanceof AbstractStanceCard) {
                Texture texture = ((AbstractStanceCard) __card_instance).getBackgroundOverlayTexture();
                if (texture != null) {
                    renderImage(__card_instance, sb, x - 512, y - 512, texture);
                }

            }

        }

    }

    @SpirePatch(
            clz = AbstractCard.class,
            method = "renderEnergy"
    )
    public static class FinisherRequirementsPatch {

        public static void Postfix(AbstractCard __card_instance, SpriteBatch sb) {

            getRenderMethod();

            if (__card_instance instanceof AbstractBlademasterCard) {
                if (__card_instance.hasTag(BlademasterTags.FURY_FINISHER)) {
                    Texture texture = ((AbstractBlademasterCard) __card_instance).getFuryOverlayTexture();
                    renderImage(__card_instance, sb, __card_instance.current_x - 512, __card_instance.current_y - 512, texture);

                    Color costColor = Color.WHITE.cpy();
                    costColor.a = __card_instance.transparency;
                    String text = Integer.toString(((AbstractBlademasterCard)__card_instance).furyReq());
                    FontHelper.cardEnergyFont_L.getData().setScale(__card_instance.drawScale);
                    BitmapFont font = FontHelper.cardEnergyFont_L;
                    FontHelper.renderRotatedText(sb, font, text,
                            __card_instance.current_x, __card_instance.current_y,
                            -132.0F * __card_instance.drawScale * Settings.scale,
                            132.0F * __card_instance.drawScale * Settings.scale,
                            __card_instance.angle, false, costColor);

                }

                if (__card_instance.hasTag(BlademasterTags.COMBO_FINISHER)) {
                    Texture texture = ((AbstractBlademasterCard) __card_instance).getComboOverlayTexture();
                    renderImage(__card_instance, sb, __card_instance.current_x - 512, __card_instance.current_y - 512, texture);

                    Color costColor = Color.WHITE.cpy();
                    costColor.a = __card_instance.transparency;
                    String text = Integer.toString(((AbstractBlademasterCard)__card_instance).comboReq());
                    FontHelper.cardEnergyFont_L.getData().setScale(__card_instance.drawScale);
                    BitmapFont font = FontHelper.cardEnergyFont_L;
                    FontHelper.renderRotatedText(sb, font, text,
                            __card_instance.current_x, __card_instance.current_y,
                            -132.0F * __card_instance.drawScale * Settings.scale,
                            72.0F * __card_instance.drawScale * Settings.scale,
                            __card_instance.angle, false, costColor);

                }
            }

        }

    }
}