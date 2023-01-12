package code.powers.stances;

import code.Blademaster;
import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.powers.AbstractPower;

import static code.Blademaster.makeID;

public class BasicStancePower extends AbstractStancePower {

    public static final String POWER_ID = makeID("BasicStance");
    private static final Color particleColor = new Color(1f, 1f, 1f, 0f);

    public BasicStancePower(AbstractCreature owner) {
        super(POWER_ID, TYPE, TURN_BASED, owner, -1);
    }

    @Override
    public AbstractPower getChargePower(AbstractCreature owner, int amount) {
        return null;
    }

    @Override
    public Blademaster.BlademasterStance getStance() {
        return Blademaster.BlademasterStance.BASIC;
    }

    @Override
    protected Color getParticleColor() {
        return particleColor;
    }
}
