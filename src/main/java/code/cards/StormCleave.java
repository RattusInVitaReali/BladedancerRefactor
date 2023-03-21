package code.cards;

import code.actions.LightningStanceAction;
import code.powers.BleedingPower;
import code.util.BlademasterUtil;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static code.Blademaster.makeID;
import static code.util.BlademasterUtil.getAliveMonsters;

public class StormCleave extends AbstractBlademasterCard {

    public final static String ID = makeID("StormCleave");
    private static final CardRarity RARITY = CardRarity.RARE;
    private static final CardTarget TARGET = CardTarget.ALL_ENEMY;
    private static final CardType TYPE = CardType.ATTACK;
    private static final int COST = 0;
    private static final int DAMAGE = 10;
    private static final int UPGRADE_DAMAGE = 4;
    private static final int MAGIC = 5;
    private static final int UPGRADE_MAGIC = 3;
    private static final int FURY_REQ = 15;

    public StormCleave() {
        super(ID, COST, TYPE, RARITY, TARGET, FURY_REQ, 0);
        baseDamage = DAMAGE;
        baseMagicNumber = magicNumber = MAGIC;
        exhaust = true;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        consumeFinisherCost();
        for (AbstractMonster monster : getAliveMonsters()) {
            BlademasterUtil.lightningEffect(monster);
            damageMonster(monster, damage, AbstractGameAction.AttackEffect.NONE);
            BlademasterUtil.playerApplyPower(monster, new BleedingPower(monster, magicNumber));
        }
        addToBot(new LightningStanceAction());
    }

    @Override
    public void onUpgrade() {
        upgradeDamage(UPGRADE_DAMAGE);
        upgradeMagicNumber(UPGRADE_MAGIC);
    }
}