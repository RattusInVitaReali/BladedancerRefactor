package code.cards;

import code.actions.BasicStanceAction;

import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static code.Blademaster.makeID;

public class Backstep extends AbstractBlademasterCard {

    public final static String ID = makeID("Backstep");
    private static final CardRarity RARITY = CardRarity.COMMON;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.SKILL;
    private static final int COST = 1;
    private static final int BLOCK = 9;
    private static final int UPGRADE_BLOCK = 4;

    public Backstep() {
        super(ID, COST, TYPE, RARITY, TARGET);
        baseBlock = BLOCK;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        block(block);
        addToBot(new BasicStanceAction());
    }

    @Override
    public void onUpgrade() {
        upgradeBlock(UPGRADE_BLOCK);
    }
}