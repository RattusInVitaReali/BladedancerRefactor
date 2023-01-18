package code.modifiers;

import basemod.cardmods.ExhaustMod;
import com.megacrit.cardcrawl.cards.AbstractCard;

public class ExhaustNoDescriptionMod extends ExhaustMod {

    @Override
    public String modifyDescription(String rawDescription, AbstractCard card) {
        return rawDescription;
    }


}
