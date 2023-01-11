package code.actions;

import code.Blademaster;
import code.powers.stances.WindStancePower;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.powers.AbstractPower;

public class WindStanceAction extends AbstractChangeStanceAction {

    public WindStanceAction() {
        super(Blademaster.BlademasterStance.WIND);
    }

    @Override
    public AbstractPower getStancePower(AbstractPlayer p) {
        return new WindStancePower(p);
    }
}
