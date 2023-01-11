package code.powers.stances;

import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.powers.AbstractPower;

import static code.Blademaster.makeID;

public class WindStance extends AbstractStancePower {

    public static final String POWER_ID = makeID("WindStance");
    static Color particleColor = new Color(.5f, 1f, .1f, 1f);

    public WindStance(AbstractCreature owner) {
        super(POWER_ID, TYPE, TURN_BASED, owner, -1);
    }

    @Override
    public AbstractPower getChargePower(AbstractCreature owner, int amount) {
        return new WindCharge(owner, amount);
    }

    @Override
    protected Color getParticleColor() {
        return particleColor;
    }
}
