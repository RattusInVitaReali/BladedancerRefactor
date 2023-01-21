package code.cards;

import code.powers.FuryPower;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static code.Blademaster.makeID;
import static code.util.BlademasterUtil.playerApplyPower;

public class Parry extends AbstractStanceCard {

    public final static String ID = makeID("Parry");
    private static final CardRarity RARITY = CardRarity.COMMON;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.SKILL;
    private static final int COST = 1;
    private static final int BLOCK = 6;
    private static final int UPGRADE_BLOCK = 3;
    private static final int MAGIC = 5;
    private static final int UPGRADE_MAGIC = 5;
    private static final int CONDUIT = 1;
    private static final int UPGRADE_CONDUIT = 1;

    public Parry() {
        super(ID, COST, TYPE, RARITY, TARGET);
        baseBlock = BLOCK;
        baseMagicNumber = magicNumber = MAGIC;
        setBaseConduit(CONDUIT);
    }

    @Override
    public void useBasic(AbstractPlayer p, AbstractMonster m) {
        block(block);
        playerApplyPower(p, new FuryPower(p, magicNumber));
    }

    @Override
    public void onUpgrade() {
        upgradeBlock(UPGRADE_BLOCK);
        upgradeMagicNumber(UPGRADE_MAGIC);
        upgradeConduit(UPGRADE_CONDUIT);
    }
}