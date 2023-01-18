package code.cards;

import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.MetallicizePower;

import static code.Blademaster.makeID;
import static code.util.BlademasterUtil.playerApplyPower;

public class EnchantedCuirass extends AbstractBlademasterCard {

    public final static String ID = makeID("EnchantedCuirass");
    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.POWER;
    private static final int COST = 0;
    private static final int COMBO_REQ = 5;
    private static final int MAGIC = 8;
    private static final int UPGRADE_MAGIC = 3;

    public EnchantedCuirass() {
        super(ID, COST, TYPE, RARITY, TARGET, 0, COMBO_REQ);
        baseMagicNumber = magicNumber = MAGIC;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        consumeFinisherCost();
        playerApplyPower(p, new MetallicizePower(p, magicNumber));
    }

    @Override
    public void onUpgrade() {
        upgradeMagicNumber(UPGRADE_MAGIC);
    }
}