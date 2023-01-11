package code.actions;

import code.Blademaster;
import code.powers.stances.LightningStancePower;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.powers.AbstractPower;

public class LightningStanceAction extends AbstractChangeStanceAction {

    public LightningStanceAction() {
        super(Blademaster.BlademasterStance.LIGHTNING);
    }

    @Override
    public AbstractPower getStancePower(AbstractPlayer p) {
        return new LightningStancePower(p);
    }
}
