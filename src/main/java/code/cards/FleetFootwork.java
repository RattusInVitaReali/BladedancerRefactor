package code.cards;

import code.powers.FleetFootworkPower;
import code.powers.RegenerationPower;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.DexterityPower;

import static code.Blademaster.makeID;
import static code.util.BlademasterUtil.playerApplyPower;

public class FleetFootwork extends AbstractBlademasterCard {

    public final static String ID = makeID("FleetFootwork");
    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.POWER;
    private static final int COST = 1;
    private static final int COMBO_REQ = 3;
    private static final int MAGIC = 2;
    private static final int UPGRADE_MAGIC = 1;
    private static final int SECOND_MAGIC = 1;

    public FleetFootwork() {
        super(ID, COST, TYPE, RARITY, TARGET, 0, COMBO_REQ);
        baseMagicNumber = magicNumber = MAGIC;
        baseSecondMagic = secondMagic = SECOND_MAGIC;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        consumeFinisherCost();
        playerApplyPower(p, new DexterityPower(p, magicNumber));
        playerApplyPower(p, new FleetFootworkPower(p, secondMagic));
    }

    @Override
    public void onUpgrade() {
        upgradeMagicNumber(UPGRADE_MAGIC);
    }
}