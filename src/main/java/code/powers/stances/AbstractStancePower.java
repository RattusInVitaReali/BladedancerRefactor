package code.powers.stances;

import code.Blademaster;
import code.actions.UpdateCardStancesAction;
import code.effects.StanceEffect;
import code.effects.particles.BetterFireBurstParticleEffect;
import code.powers.AbstractBlademasterPower;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.AbstractPower;

public abstract class AbstractStancePower extends AbstractBlademasterPower {

    public static final PowerType TYPE = PowerType.BUFF;
    public static final boolean TURN_BASED = false;

    private float particleTimer = 0.0F;
    private float particleTimer2 = 0.03F;

    public AbstractStancePower(String ID, PowerType powerType, boolean isTurnBased, AbstractCreature owner, int amount) {
        super(ID, powerType, isTurnBased, owner, amount);
    }

    public abstract AbstractPower getChargePower(AbstractCreature owner, int amount);

    public abstract Blademaster.BlademasterStance getStance();

    protected abstract Color getParticleColor();

    @Override
    public void onApplyPower(AbstractPower power, AbstractCreature target, AbstractCreature source) {
        if (power.getClass() == this.getClass()) {
            return;
        }
        if (power instanceof AbstractStancePower) {
            addToBot(new RemoveSpecificPowerAction(this.owner, this.owner, this));
            addToBot(new UpdateCardStancesAction());
        }
    }

    @Override
    public void updateParticles() {
        this.particleTimer -= Gdx.graphics.getDeltaTime();
        this.particleTimer2 -= Gdx.graphics.getDeltaTime();
        if (this.particleTimer < 0.0F) {
            int xOff = MathUtils.random(-70, 70);
            AbstractDungeon.effectList.add(new BetterFireBurstParticleEffect(this.owner.drawX + xOff, this.owner.drawY + 20F, getParticleColor().cpy()));
            AbstractDungeon.effectsQueue.add(new StanceEffect(getParticleColor().cpy()));
            this.particleTimer = 0.06F;
        }
        if (this.particleTimer2 < 0.0F) {
            int xOff = MathUtils.random(-70, 70);
            AbstractDungeon.effectList.add(new BetterFireBurstParticleEffect(this.owner.drawX + xOff, this.owner.drawY + 20F, getParticleColor().cpy()));
            this.particleTimer2 = 0.06F;
        }
    }

}
