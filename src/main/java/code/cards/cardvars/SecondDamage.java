package code.cards.cardvars;

import basemod.abstracts.DynamicVariable;
import com.megacrit.cardcrawl.cards.AbstractCard;
import code.cards.AbstractBlademasterCard;

import static code.Blademaster.makeID;

public class SecondDamage extends DynamicVariable {

    @Override
    public String key() {
        return makeID("sd");
    }

    @Override
    public boolean isModified(AbstractCard card) {
        if (card instanceof AbstractBlademasterCard) {
            return ((AbstractBlademasterCard) card).isSecondDamageModified;
        }
        return false;
    }

    public void setIsModified(AbstractCard card, boolean v) {
        if (card instanceof AbstractBlademasterCard) {
            ((AbstractBlademasterCard) card).isSecondDamageModified = v;
        }
    }

    @Override
    public int value(AbstractCard card) {
        if (card instanceof AbstractBlademasterCard) {
            return ((AbstractBlademasterCard) card).secondDamage;
        }
        return -1;
    }

    @Override
    public int baseValue(AbstractCard card) {
        if (card instanceof AbstractBlademasterCard) {
            return ((AbstractBlademasterCard) card).baseSecondDamage;
        }
        return -1;
    }

    @Override
    public boolean upgraded(AbstractCard card) {
        if (card instanceof AbstractBlademasterCard) {
            return ((AbstractBlademasterCard) card).upgradedSecondDamage;
        }
        return false;
    }
}