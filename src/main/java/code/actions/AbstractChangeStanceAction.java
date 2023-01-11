package code.actions;

import code.Blademaster;
import code.util.BlademasterUtil;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.AbstractPower;

public abstract class AbstractChangeStanceAction extends AbstractGameAction {

    private final Blademaster.BlademasterStance stance;

    public AbstractChangeStanceAction(Blademaster.BlademasterStance stance) {
        this.actionType = ActionType.SPECIAL;
        this.stance = stance;
    }

    @Override
    public void update() {
        if (BlademasterUtil.getPlayerStance() == stance) {
            isDone = true;
            return;
        }
        AbstractPlayer p = AbstractDungeon.player;
        addToBot(new ApplyPowerAction(p, p, getStancePower(p)));
        addToBot(new UpdateCardStancesAction());
        isDone = true;
    }

    public abstract AbstractPower getStancePower(AbstractPlayer p);

}
