package code.patches;

import basemod.ReflectionHacks;
import code.cards.AbstractStanceCard;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.cards.AbstractCard;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class CardRenderPatch {

    private static Method cardRenderHelperMethod;
    private static boolean renderReflectFailureNotified = false;
    private static boolean renderActualFailureNotified = false;

    @SpirePatch(
            clz = AbstractCard.class,
            method = "renderCardBg"
    )
    public static class BackgroundColorPatch {

        public static void Postfix(AbstractCard __card_instance, SpriteBatch sb, float x, float y) {

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

            if (__card_instance instanceof AbstractStanceCard) {
                Texture texture = ((AbstractStanceCard) __card_instance).getBackgroundOverlayTexture();
                if (texture != null) {
                    try {
                        Color reflectedColor = ReflectionHacks.getPrivate(__card_instance, AbstractCard.class, "renderColor");
                        cardRenderHelperMethod.setAccessible(true);
                        cardRenderHelperMethod.invoke(__card_instance, sb, reflectedColor, texture, x - 512, y - 512);
                    } catch (IllegalAccessException | InvocationTargetException e) {
                        if (!renderActualFailureNotified) {
                            renderActualFailureNotified = true;
                            System.out.println("ALERT: Blademaster failed to invoke AbstractCard method renderHelper");
                            e.printStackTrace();
                        }
                    }
                }

            }

        }

    }

}