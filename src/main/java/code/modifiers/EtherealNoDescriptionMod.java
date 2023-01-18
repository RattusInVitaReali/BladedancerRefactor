package code.modifiers;

import basemod.cardmods.EtherealMod;
import com.megacrit.cardcrawl.cards.AbstractCard;

public class EtherealNoDescriptionMod extends EtherealMod {

    @Override
    public String modifyDescription(String rawDescription, AbstractCard card) {
        return rawDescription;
    }

}
