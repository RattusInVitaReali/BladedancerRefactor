package code.cards;

import code.powers.BleedingPower;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static code.Blademaster.makeID;
import static code.util.BlademasterUtil.playerApplyPower;

public class BloodyBlow extends AbstractStanceCard {

    public final static String ID = makeID("BloodyBlow");
    private static final CardRarity RARITY = CardRarity.COMMON;
    private static final CardTarget TARGET = CardTarget.ENEMY;
    private static final CardType TYPE = CardType.ATTACK;
    private static final int COST = 1;
    private static final int DAMAGE = 7;
    private static final int UPGRADE_DAMAGE = 4;
    private static final int MAGIC = 5;
    private static final int UPGRADE_MAGIC = 3;
    private static final int CONDUIT = 1;
    private static final int UPGRADE_CONDUIT = 1;

    public BloodyBlow() {
        super(ID, COST, TYPE, RARITY, TARGET);
        baseDamage = DAMAGE;
        baseMagicNumber = magicNumber = MAGIC;
        setBaseConduit(CONDUIT);
    }

    @Override
    public void useBasic(AbstractPlayer p, AbstractMonster m) {
        damageMonster(m, damage, AbstractGameAction.AttackEffect.SLASH_DIAGONAL);
        useBloodiedWrapper(p, m);
    }

    @Override
    public void useBloodied(AbstractPlayer p, AbstractMonster m) {
        playerApplyPower(m, new BleedingPower(m, magicNumber));
    }

    @Override
    public void onUpgrade() {
        upgradeDamage(UPGRADE_DAMAGE);
        upgradeMagicNumber(UPGRADE_MAGIC);
        upgradeConduit(UPGRADE_CONDUIT);
    }
}