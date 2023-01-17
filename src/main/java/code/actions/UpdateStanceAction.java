package code.actions;

import code.Blademaster;
import code.cards.AbstractStanceCard;
import code.powers.interfaces.OnStanceChangedPower;
import code.util.BlademasterUtil;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.AbstractPower;

import java.util.ArrayList;

public class UpdateStanceAction extends AbstractGameAction {

    @Override
    public void update() {
        Blademaster.BlademasterStance stance = BlademasterUtil.getPlayerStance();
        ArrayList<AbstractCard> allCards = new ArrayList<>();
        allCards.addAll(AbstractDungeon.player.drawPile.group);
        allCards.addAll(AbstractDungeon.player.discardPile.group);
        allCards.addAll(AbstractDungeon.player.hand.group);
        for (AbstractCard card : allCards) {
            if (card instanceof AbstractStanceCard) {
                ((AbstractStanceCard) card).setStance(stance);
                ((AbstractStanceCard) card).onStanceChanged(stance);
            }
        }
        for (AbstractPower power : AbstractDungeon.player.powers) {
            if (power instanceof OnStanceChangedPower) {
                ((OnStanceChangedPower) power).onStanceChanged(stance);
            }
        }
        isDone = true;
    }
}
