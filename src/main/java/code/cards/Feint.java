package code.cards;

import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static code.Blademaster.makeID;

public class Feint extends AbstractBlademasterCard {

    public final static String ID = makeID("Feint");
    private static final CardRarity RARITY = CardRarity.COMMON;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.SKILL;
    private static final int COST = 1;
    private static final int BLOCK = 6;
    private static final int UPGRADE_BLOCK = 3;
    private static final int MAGIC = 1;

    public Feint() {
        super(ID, COST, TYPE, RARITY, TARGET);
        baseBlock = BLOCK;
        baseMagicNumber = magicNumber = MAGIC;
        this.cardsToPreview = new Carve();
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        block(block);
        Carve carve = new Carve();
        if (upgraded) carve.upgrade();
        addToBot(new MakeTempCardInHandAction(carve, magicNumber));
    }

    @Override
    public void onUpgrade() {
        upgradeBlock(UPGRADE_BLOCK);
        setUpgradeDescription();
        this.cardsToPreview.upgrade();
    }
}