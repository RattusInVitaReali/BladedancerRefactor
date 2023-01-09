package code.cards.democards.complex;

import code.cards.AbstractBlademasterCard;
import code.cards.BlademasterModalChoiceCard;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.StrengthPower;
import code.actions.EasyModalChoiceAction;

import java.util.ArrayList;

import static code.Blademaster.makeID;
import static code.util.Wiz.*;

public class BlademasterModalChoiceDemo extends AbstractBlademasterCard {
    public final static String ID = makeID(BlademasterModalChoiceDemo.class.getSimpleName());
    // intellij stuff skill, self, uncommon, , , , , , 

    public BlademasterModalChoiceDemo() {
        super(ID, 1, CardType.SKILL, CardRarity.UNCOMMON, CardTarget.SELF);
        baseMagicNumber = magicNumber = 3;
        baseSecondMagic = secondMagic = 1;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        ArrayList<AbstractCard> easyCardList = new ArrayList<>();
        easyCardList.add(new BlademasterModalChoiceCard(cardStrings.EXTENDED_DESCRIPTION[0], cardStrings.EXTENDED_DESCRIPTION[1] + magicNumber + cardStrings.EXTENDED_DESCRIPTION[2], () -> att(new DrawCardAction(magicNumber))));
        easyCardList.add(new BlademasterModalChoiceCard(cardStrings.EXTENDED_DESCRIPTION[3], cardStrings.EXTENDED_DESCRIPTION[4]+ secondMagic + cardStrings.EXTENDED_DESCRIPTION[5], () -> applyToSelfTop(new StrengthPower(p, secondMagic))));
        atb(new EasyModalChoiceAction(easyCardList));
    }

    public void onUpgrade() {
        upgradeMagicNumber(1);
        upgradeSecondMagic(1);
    }
}