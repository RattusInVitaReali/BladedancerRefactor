package code.cards;

import code.powers.MarkedForDeathPower;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.StrengthPower;

import static code.Blademaster.makeID;
import static code.util.BlademasterUtil.playerApplyPower;

public class MarkedForDeath extends AbstractBlademasterCard {

    public final static String ID = makeID("MarkedForDeath");
    private static final CardRarity RARITY = CardRarity.RARE;
    private static final CardTarget TARGET = CardTarget.ENEMY;
    private static final CardType TYPE = CardType.SKILL;
    private static final int COST = 1;
    private static final int UPGRADED_COST = 0;
    private static final int MAGIC = 2;

    public MarkedForDeath() {
        super(ID, COST, TYPE, RARITY, TARGET);
        baseMagicNumber = magicNumber = MAGIC;
        exhaust = true;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        playerApplyPower(m, new MarkedForDeathPower(m));
        playerApplyPower(m, new StrengthPower(m, -magicNumber));
    }

    @Override
    public void onUpgrade() {
        upgradeBaseCost(UPGRADED_COST);
    }
}