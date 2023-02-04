package code.cards;

import code.powers.OverchargePower;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static code.Blademaster.makeID;
import static code.util.BlademasterUtil.playerApplyPower;

public class Overcharge extends AbstractBlademasterCard {

    public final static String ID = makeID("Overcharge");
    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.POWER;
    private static final int COST = 2;
    private static final int UPGRADED_COST = 1;
    private static final int MAGIC = 1;

    public Overcharge() {
        super(ID, COST, TYPE, RARITY, TARGET);
        baseMagicNumber = magicNumber = MAGIC;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        playerApplyPower(p, new OverchargePower(p, magicNumber));
    }

    @Override
    public void onUpgrade() {
        upgradeBaseCost(UPGRADED_COST);
    }
}