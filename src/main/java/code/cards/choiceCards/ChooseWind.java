package code.cards.choiceCards;

import code.actions.WindStanceAction;
import code.cards.AbstractBlademasterCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static code.Blademaster.makeID;

public class ChooseWind extends AbstractBlademasterCard {

    public final static String ID = makeID("ChooseWind");
    private static final CardRarity RARITY = CardRarity.COMMON;
    private static final CardTarget TARGET = CardTarget.NONE;
    private static final CardType TYPE = CardType.STATUS;
    private static final CardColor COLOR = CardColor.COLORLESS;
    private static final int COST = -2;


    public ChooseWind() {
        super(ID, COST, TYPE, RARITY, TARGET, COLOR);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {

    }

    @Override
    public void onChoseThisOption() {
        addToBot(new WindStanceAction());
    }

    @Override
    public void onUpgrade() {
    }
}