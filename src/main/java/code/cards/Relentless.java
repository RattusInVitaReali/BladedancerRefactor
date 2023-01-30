package code.cards;

import code.cards.AbstractBlademasterCard;

import code.powers.RelentlessPower;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static code.Blademaster.makeID;
import static code.util.BlademasterUtil.playerApplyPower;

public class Relentless extends AbstractBlademasterCard {

    public final static String ID = makeID("Relentless");
    private static final CardRarity RARITY = CardRarity.RARE;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.POWER;
    private static final int COST = 2;
    private static final int MAGIC = 1;

    public Relentless() {
        super(ID, COST, TYPE, RARITY, TARGET);
        baseMagicNumber = magicNumber = MAGIC;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        playerApplyPower(p, new RelentlessPower(p, magicNumber));
    }

    @Override
    public void onUpgrade() {
        this.isInnate = true;
        setUpgradeDescription();
    }
}