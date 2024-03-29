package code.cards;

import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.DexterityPower;
import com.megacrit.cardcrawl.powers.StrengthPower;

import static code.Blademaster.makeID;
import static code.util.BlademasterUtil.playerApplyPower;

public class ColdBlood extends AbstractBlademasterCard {

    public final static String ID = makeID("ColdBlood");
    private static final CardRarity RARITY = CardRarity.RARE;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.POWER;
    private static final int COST = 0;
    private static final int MAGIC = 4;
    private static final int UPGRADE_MAGIC = 2;
    private static final int COMBO_REQ = 5;

    public ColdBlood() {
        super(ID, COST, TYPE, RARITY, TARGET, 0, COMBO_REQ);
        baseMagicNumber = magicNumber = MAGIC;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        consumeFinisherCost();
        playerApplyPower(p, new DexterityPower(p, magicNumber));
        playerApplyPower(p, new StrengthPower(p, magicNumber));
    }

    @Override
    public void onUpgrade() {
        upgradeMagicNumber(UPGRADE_MAGIC);
    }
}