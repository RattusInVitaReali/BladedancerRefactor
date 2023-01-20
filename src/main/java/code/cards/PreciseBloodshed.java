package code.cards;

import code.cards.AbstractBlademasterCard;

import code.powers.PreciseBloodshedPower;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static code.Blademaster.makeID;
import static code.util.BlademasterUtil.playerApplyPower;

public class PreciseBloodshed extends AbstractBlademasterCard {

    public final static String ID = makeID("PreciseBloodshed");
    private static final CardRarity RARITY = CardRarity.RARE;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.SKILL;
    private static final int COST = 1;
    private static final int MAGIC = 1;

    public PreciseBloodshed() {
        super(ID, COST, TYPE, RARITY, TARGET);
        baseMagicNumber = magicNumber = MAGIC;
        this.exhaust = true;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        playerApplyPower(p, new PreciseBloodshedPower(p, magicNumber));
    }

    @Override
    public void onUpgrade() {
        this.exhaust = false;
        setUpgradeDescription();
    }
}