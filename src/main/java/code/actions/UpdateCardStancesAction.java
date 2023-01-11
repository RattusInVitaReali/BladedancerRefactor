package code.actions;

import code.Blademaster;
import code.cards.AbstractStanceCard;
import code.util.BlademasterUtil;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

import java.util.ArrayList;

public class UpdateCardStancesAction extends AbstractGameAction {

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
            }
        }
        isDone = true;
    }
}
