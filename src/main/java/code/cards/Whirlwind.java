package code.cards;

import code.actions.WhirlwindAction;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static code.Blademaster.makeID;

public class Whirlwind extends AbstractStanceCard {

    public final static String ID = makeID("Whirlwind");
    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.ALL_ENEMY;
    private static final CardType TYPE = CardType.ATTACK;
    private static final int COST = -1;
    private static final int DAMAGE = 5;
    private static final int UPGRADE_DAMAGE = 3;
    private static final int MAGIC = 1;
    private static final int UPGRADE_MAGIC = 1;

    public Whirlwind() {
        super(ID, COST, TYPE, RARITY, TARGET);
        baseDamage = DAMAGE;
        this.isMultiDamage = true;
        baseMagicNumber = magicNumber = MAGIC;
    }

    @Override
    public void useBasic(AbstractPlayer p, AbstractMonster m) {
        addToBot(new WhirlwindAction(p, multiDamage, damageTypeForTurn, freeToPlayOnce, energyOnUse, magicNumber));
    }

    @Override
    public void onUpgrade() {
        upgradeDamage(UPGRADE_DAMAGE);
        upgradeMagicNumber(UPGRADE_MAGIC);
    }
}