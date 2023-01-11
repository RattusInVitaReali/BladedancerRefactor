package code.cards.cardvars;

import basemod.abstracts.DynamicVariable;
import code.cards.AbstractStanceCard;
import com.megacrit.cardcrawl.cards.AbstractCard;

import static code.Blademaster.makeID;

public class ConduitNumber extends DynamicVariable {

    @Override
    public String key() {
        return makeID("cn");
    }

    @Override
    public boolean isModified(AbstractCard card) {
        if (card instanceof AbstractStanceCard) {
            return ((AbstractStanceCard) card).isConduitModified;
        }
        return false;
    }

    @Override
    public int value(AbstractCard card) {
        if (card instanceof AbstractStanceCard) {
            return ((AbstractStanceCard) card).conduit;
        }
        return -1;
    }

    @Override
    public void setIsModified(AbstractCard card, boolean v) {
        if (card instanceof AbstractStanceCard) {
            ((AbstractStanceCard) card).isConduitModified = v;
        }
    }

    @Override
    public int baseValue(AbstractCard card) {
        if (card instanceof AbstractStanceCard) {
            return ((AbstractStanceCard) card).baseConduit;
        }
        return -1;
    }

    @Override
    public boolean upgraded(AbstractCard card) {
        if (card instanceof AbstractStanceCard) {
            return ((AbstractStanceCard) card).upgradedConduit;
        }
        return false;
    }
}