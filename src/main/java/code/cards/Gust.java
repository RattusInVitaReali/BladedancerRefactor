package code.cards;

import code.actions.WindStanceAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static code.Blademaster.makeID;

public class Gust extends AbstractBlademasterCard {

    public final static String ID = makeID("Gust");
    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.SKILL;
    private static final int COST = 1;
    private static final int BLOCK = 7;
    private static final int UPGRADE_BLOCK = 4;

    public Gust() {
        super(ID, COST, TYPE, RARITY, TARGET);
        baseBlock = BLOCK;
        this.isInnate = true;
        this.exhaust = true;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        block(block);
        addToBot(new WindStanceAction());
    }

    @Override
    public void onUpgrade() {
        upgradeBlock(UPGRADE_BLOCK);
    }
}