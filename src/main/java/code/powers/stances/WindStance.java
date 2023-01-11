package code.powers.stances;

import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.powers.AbstractPower;

import static code.Blademaster.makeID;

public class WindStance extends AbstractStancePower {

    public static final String POWER_ID = makeID("WindStance");

    public WindStance(AbstractCreature owner) {
        super(POWER_ID, TYPE, TURN_BASED, owner, -1);
    }

    static Color particleColor = new Color(.5f, 1f, .1f, 1f);

    @Override
    public AbstractPower getChargePower(AbstractCreature owner, int amount) {
        return new WindCharge(owner, amount);
    }

    protected Color getParticleColor() {
        return particleColor;
    }
}
