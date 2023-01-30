package code.cards;

import code.cards.AbstractBlademasterCard;

import code.powers.ComboPower;
import code.powers.FuryPower;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static code.Blademaster.makeID;
import static code.util.BlademasterUtil.playerApplyPower;

public class PerfectStorm extends AbstractBlademasterCard {

    public final static String ID = makeID("PerfectStorm");
    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.SKILL;
    private static final int COST = 2;
    private static final int BLOCK = 11;
    private static final int UPGRADE_BLOCK = 4;
    private static final int MAGIC = 15;
    private static final int UPGRADE_MAGIC = 5;
    private static final int SECOND_MAGIC = 2;
    private static final int UPGRADE_SECOND_MAGIC = 1;

    public PerfectStorm() {
        super(ID, COST, TYPE, RARITY, TARGET);
        baseBlock = BLOCK;
        baseMagicNumber = magicNumber = MAGIC;
        baseSecondMagic = secondMagic = SECOND_MAGIC;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        block(block);
        playerApplyPower(p, new FuryPower(p, magicNumber));
        playerApplyPower(p, new ComboPower(p, secondMagic));
    }

    @Override
    public void onUpgrade() {
        upgradeBlock(UPGRADE_BLOCK);
        upgradeMagicNumber(UPGRADE_MAGIC);
        upgradeSecondMagic(UPGRADE_SECOND_MAGIC);
    }
}