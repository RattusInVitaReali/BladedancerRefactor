package code.cards.cardvars;

import basemod.abstracts.DynamicVariable;
import code.cards.AbstractBlademasterCard;
import com.megacrit.cardcrawl.cards.AbstractCard;

import static code.Blademaster.makeID;

public class SecondMagicNumber extends DynamicVariable {

    @Override
    public String key() {
        return makeID("M2");
    }

    @Override
    public boolean isModified(AbstractCard card) {
        if (card instanceof AbstractBlademasterCard) {
            return ((AbstractBlademasterCard) card).isSecondMagicModified;
        }
        return false;
    }

    @Override
    public int value(AbstractCard card) {
        if (card instanceof AbstractBlademasterCard) {
            return ((AbstractBlademasterCard) card).secondMagic;
        }
        return -1;
    }

    @Override
    public void setIsModified(AbstractCard card, boolean v) {
        if (card instanceof AbstractBlademasterCard) {
            ((AbstractBlademasterCard) card).isSecondMagicModified = v;
        }
    }

    @Override
    public int baseValue(AbstractCard card) {
        if (card instanceof AbstractBlademasterCard) {
            return ((AbstractBlademasterCard) card).baseSecondMagic;
        }
        return -1;
    }

    @Override
    public boolean upgraded(AbstractCard card) {
        if (card instanceof AbstractBlademasterCard) {
            return ((AbstractBlademasterCard) card).upgradedSecondMagic;
        }
        return false;
    }
}