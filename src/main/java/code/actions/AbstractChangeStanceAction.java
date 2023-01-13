package code.actions;

import code.Blademaster;
import code.util.BlademasterUtil;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.AbstractPower;

import static code.util.BlademasterUtil.playerApplyPower;

public abstract class AbstractChangeStanceAction extends AbstractGameAction {

    private final Blademaster.BlademasterStance stance;

    public AbstractChangeStanceAction(Blademaster.BlademasterStance stance) {
        this.actionType = ActionType.SPECIAL;
        this.stance = stance;
    }

    @Override
    public void update() {
        if (BlademasterUtil.getPlayerStance() == stance && BlademasterUtil.getPlayerStancePower() != null) {
            isDone = true;
            return;
        }
        AbstractPlayer p = AbstractDungeon.player;
        playerApplyPower(p, getStancePower(p));
        isDone = true;
    }

    public abstract AbstractPower getStancePower(AbstractPlayer p);

}
