package code.actions;

import code.Blademaster;
import code.powers.stances.BasicStancePower;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.powers.AbstractPower;

public class BasicStanceAction extends AbstractChangeStanceAction {

    public BasicStanceAction() {
        super(Blademaster.BlademasterStance.BASIC);
    }

    @Override
    public AbstractPower getStancePower(AbstractPlayer p) {
        return new BasicStancePower(p);
    }
}
