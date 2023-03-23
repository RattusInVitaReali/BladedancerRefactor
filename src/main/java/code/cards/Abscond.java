package code.cards;

import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.DrawCardNextTurnPower;

import static code.Blademaster.makeID;
import static code.util.BlademasterUtil.playerApplyPower;

public class Abscond extends AbstractStanceCard {

    public final static String ID = makeID("Abscond");
    private static final CardRarity RARITY = CardRarity.COMMON;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.SKILL;
    private static final int COST = 1;
    private static final int BLOCK = 8;
    private static final int UPGRADE_BLOCK = 3;
    private static final int MAGIC = 2;
    private static final int CONDUIT = 1;
    private static final int UPGRADE_CONDUIT = 1;

    public Abscond() {
        super(ID, COST, TYPE, RARITY, TARGET);
        baseBlock = BLOCK;
        baseMagicNumber = magicNumber = MAGIC;
        baseConduit = conduit = CONDUIT;
    }

    @Override
    public void useBasic(AbstractPlayer p, AbstractMonster m) {
        block(block);
        playerApplyPower(p, new DrawCardNextTurnPower(p, magicNumber));
    }

    @Override
    public void onUpgrade() {
        upgradeBlock(UPGRADE_BLOCK);
        upgradeConduit(UPGRADE_CONDUIT);
    }
}