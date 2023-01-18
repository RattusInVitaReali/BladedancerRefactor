package code.cards;

import code.powers.ComboPower;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static code.Blademaster.makeID;
import static code.util.BlademasterUtil.playerApplyPower;

public class DragonStrike extends AbstractStanceCard {

    public final static String ID = makeID("DragonStrike");
    private static final CardRarity RARITY = CardRarity.COMMON;
    private static final CardTarget TARGET = CardTarget.ENEMY;
    private static final CardType TYPE = CardType.ATTACK;
    private static final int COST = 1;
    private static final int DAMAGE = 5;
    private static final int UPGRADE_DAMAGE = 2;
    private static final int MAGIC = 1;
    private static final int UPGRADE_MAGIC = 1;
    private static final int CONDUIT = 1;
    private static final int UPGRADE_CONDUIT = 1;

    public DragonStrike() {
        super(ID, COST, TYPE, RARITY, TARGET);
        baseDamage = DAMAGE;
        baseMagicNumber = magicNumber = MAGIC;
        baseConduit = conduit = CONDUIT;
        updateDescription();
    }

    @Override
    public void useBasic(AbstractPlayer p, AbstractMonster m) {
        damageMonster(m, damage, AbstractGameAction.AttackEffect.SLASH_VERTICAL);
        playerApplyPower(p, new ComboPower(p, magicNumber));
    }

    @Override
    public void onUpgrade() {
        upgradeDamage(UPGRADE_DAMAGE);
        upgradeMagicNumber(UPGRADE_MAGIC);
        upgradeConduit(UPGRADE_CONDUIT);
    }
}