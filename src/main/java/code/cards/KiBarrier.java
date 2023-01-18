package code.cards;

import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.BlurPower;

import static code.Blademaster.makeID;
import static code.util.BlademasterUtil.playerApplyPower;

public class KiBarrier extends AbstractBlademasterCard {

    public final static String ID = makeID("KiBarrier");
    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.SKILL;
    private static final int COST = 0;
    private static final int COMBO_REQ = 3;
    private static final int BLOCK = 9;
    private static final int UPGRADE_BLOCK = 4;
    private static final int MAGIC = 1;

    public KiBarrier() {
        super(ID, COST, TYPE, RARITY, TARGET, 0, COMBO_REQ);
        baseBlock = BLOCK;
        baseMagicNumber = magicNumber = MAGIC;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        consumeFinisherCost();
        block(block);
        playerApplyPower(p, new BlurPower(p, magicNumber));
    }

    @Override
    public void onUpgrade() {
        upgradeBlock(UPGRADE_BLOCK);
    }
}