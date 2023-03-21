package code.cards;

import code.actions.BlitzAction;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static code.Blademaster.makeID;

public class Blitz extends AbstractBlademasterCard {

    public final static String ID = makeID("Blitz");
    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.ALL_ENEMY;
    private static final CardType TYPE = CardType.ATTACK;
    private static final int COST = 2;
    private static final int DAMAGE = 3;
    private static final int MAGIC = 3;
    private static final int SECOND_MAGIC = 3;
    private static final int UPGRADE_SECOND_MAGIC = 1;

    public Blitz() {
        super(ID, COST, TYPE, RARITY, TARGET);
        baseDamage = DAMAGE;
        baseMagicNumber = magicNumber = MAGIC;
        baseSecondMagic = secondMagic = SECOND_MAGIC;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        for (int i = 0; i < secondMagic; i++) {
            addToBot(new BlitzAction(this, AbstractGameAction.AttackEffect.SLASH_DIAGONAL));
        }
    }

    @Override
    public void onUpgrade() {
        upgradeSecondMagic(UPGRADE_SECOND_MAGIC);
    }
}