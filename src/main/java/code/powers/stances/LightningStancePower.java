package code.powers.stances;

import basemod.ReflectionHacks;
import code.Blademaster;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;
import com.megacrit.cardcrawl.vfx.stance.StanceAuraEffect;
import com.megacrit.cardcrawl.vfx.stance.WrathParticleEffect;

import static code.Blademaster.makeID;

public class LightningStancePower extends AbstractStancePower {

    public static final String POWER_ID = makeID("LightningStance");

    private float particleTimer = 0f;
    private float particleTimer2 = 0f;

    public LightningStancePower(AbstractCreature owner) {
        super(POWER_ID, TYPE, TURN_BASED, owner, -1);
    }

    @Override
    public AbstractPower getChargePower(AbstractCreature owner, int amount) {
        return new LightningChargePower(owner, amount);
    }

    @Override
    public Blademaster.BlademasterStance getStance() {
        return Blademaster.BlademasterStance.LIGHTNING;
    }

    private Color generateParticleColor() {
        return new Color(
                MathUtils.random(.25f, .35f),
                MathUtils.random(.35f, .45f),
                MathUtils.random(.95f, 1.0f),
                0.0f
        );
    }

    @Override
    public void updateParticles() {
        if (!Settings.DISABLE_EFFECTS) {
            particleTimer -= Gdx.graphics.getDeltaTime();
            if (particleTimer < 0.0F) {
                particleTimer = 0.04F;
                WrathParticleEffect particleEffect = new WrathParticleEffect();
                ReflectionHacks.setPrivate(particleEffect, AbstractGameEffect.class, "color", generateParticleColor());
                AbstractDungeon.effectsQueue.add(particleEffect);
            }
        }

        particleTimer2 -= Gdx.graphics.getDeltaTime();
        if (particleTimer2 < 0.0F) {
            particleTimer2 = MathUtils.random(0.45F, 0.55F);
            StanceAuraEffect effect = new StanceAuraEffect("CALM");
            ReflectionHacks.setPrivate(effect, AbstractGameEffect.class, "color", generateParticleColor());
            AbstractDungeon.effectsQueue.add(effect);
        }

    }
}
