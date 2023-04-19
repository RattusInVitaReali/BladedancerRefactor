package code.cards;

import code.powers.EnchantedBladePower;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.StrengthPower;

import static code.Blademaster.makeID;
import static code.util.BlademasterUtil.playerApplyPower;

public class EnchantedBlade extends AbstractStanceCard {

    public final static String ID = makeID("EnchantedBlade");
    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.POWER;
    private static final int COST = 1;
    private static final int COMBO_REQ = 2;
    private static final int MAGIC = 2;
    private static final int UPGRADE_MAGIC = 1;
    private static final int SECOND_MAGIC = 5;
    private static final int CONDUIT = 3;
    private static final int UPGRADE_CONDUIT = 2;

    public EnchantedBlade() {
        super(ID, COST, TYPE, RARITY, TARGET, 0, COMBO_REQ);
        baseMagicNumber = magicNumber = MAGIC;
        baseSecondMagic = secondMagic = SECOND_MAGIC;
        baseConduit = conduit = CONDUIT;
    }

    @Override
    public void useBasic(AbstractPlayer p, AbstractMonster m) {
        consumeFinisherCost();
        playerApplyPower(p, new StrengthPower(p, magicNumber));
        playerApplyPower(p, new EnchantedBladePower(p, secondMagic));
    }

    @Override
    public void onUpgrade() {
        upgradeMagicNumber(UPGRADE_MAGIC);
        upgradeConduit(UPGRADE_CONDUIT);
    }
}