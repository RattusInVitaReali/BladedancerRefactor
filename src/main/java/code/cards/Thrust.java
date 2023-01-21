package code.cards;

import code.util.BlademasterUtil;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static code.Blademaster.makeID;
import static code.util.BlademasterUtil.getPlayerLightningCharges;
import static code.util.BlademasterUtil.getPlayerWindCharges;

public class Thrust extends AbstractStanceCard {

    public final static String ID = makeID("Thrust");
    private static final CardRarity RARITY = CardRarity.COMMON;
    private static final CardTarget TARGET = CardTarget.ENEMY;
    private static final CardType TYPE = CardType.ATTACK;
    private static final int COST = 1;
    private static final int DAMAGE = 5;
    private static final int UPGRADE_DAMAGE = 2;

    public Thrust() {
        super(ID, COST, TYPE, RARITY, TARGET);
        baseDamage = DAMAGE;
    }

    @Override
    public void useBasic(AbstractPlayer p, AbstractMonster m) {
        damageMonster(m, damage, AbstractGameAction.AttackEffect.SLASH_HORIZONTAL);
    }

    @Override
    public void useWind(AbstractPlayer p, AbstractMonster m) {
        useBasic(p, m);
        BlademasterUtil.lightningEffect(m);
        damageMonster(m, getPlayerWindCharges(), AbstractGameAction.AttackEffect.NONE);
    }

    @Override
    public void useLightning(AbstractPlayer p, AbstractMonster m) {
        useBasic(p, m);
        BlademasterUtil.lightningEffect(m);
        damageMonster(m, getPlayerLightningCharges(), AbstractGameAction.AttackEffect.NONE);
    }

    @Override
    public void onUpgrade() {
        upgradeDamage(UPGRADE_DAMAGE);
    }
}