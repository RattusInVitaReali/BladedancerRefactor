package code.cards;

import code.powers.MomentumPower;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static code.Blademaster.makeID;
import static code.util.BlademasterUtil.playerApplyPower;

public class Momentum extends AbstractBlademasterCard {

    public final static String ID = makeID("Momentum");
    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.POWER;
    private static final int COST = 1;
    private static final int MAGIC = 8;
    private static final int UPGRADE_MAGIC = 3;

    public Momentum() {
        super(ID, COST, TYPE, RARITY, TARGET);
        baseMagicNumber = magicNumber = MAGIC;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        playerApplyPower(p, new MomentumPower(p, magicNumber));
    }

    @Override
    public void onUpgrade() {
        upgradeMagicNumber(UPGRADE_MAGIC);
    }
}