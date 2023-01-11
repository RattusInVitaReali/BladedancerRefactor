package code.powers.stances;

import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.powers.AbstractPower;

import static code.Blademaster.makeID;

public class LightningStance extends AbstractStancePower {

    public static final String POWER_ID = makeID("LightningStance");

    public LightningStance(AbstractCreature owner) {
        super(POWER_ID, TYPE, TURN_BASED, owner, -1);
    }

    static Color particleColor = new Color(.3f, 1f, 1f, 1f);

    @Override
    public AbstractPower getChargePower(AbstractCreature owner, int amount) {
        return new LightningCharge(owner, amount);
    }

    @Override
    protected Color getParticleColor() {
        return particleColor;
    }
}
