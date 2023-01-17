package code.powers.stances;

import code.Blademaster;
import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.powers.AbstractPower;

import static code.Blademaster.makeID;

public class WindStancePower extends AbstractStancePower {

    public static final String POWER_ID = makeID("WindStance");
    private final static Color particleColor = new Color(.5f, 1f, .1f, 1f);

    public WindStancePower(AbstractCreature owner) {
        super(POWER_ID, TYPE, TURN_BASED, owner, -1);
    }

    @Override
    public AbstractPower getChargePower(AbstractCreature owner, int amount) {
        return new WindChargePower(owner, amount);
    }

    @Override
    public Blademaster.BlademasterStance getStance() {
        return Blademaster.BlademasterStance.WIND;
    }

    @Override
    protected Color getParticleColor() {
        return particleColor;
    }
}
