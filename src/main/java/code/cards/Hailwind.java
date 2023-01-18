package code.cards;

import code.powers.stances.WindChargePower;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.vfx.combat.ThrowDaggerEffect;

import static code.Blademaster.makeID;
import static code.util.BlademasterUtil.getPlayerWindCharges;

public class Hailwind extends AbstractBlademasterCard {

    public final static String ID = makeID("Hailwind");
    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.ENEMY;
    private static final CardType TYPE = CardType.ATTACK;
    private static final int COST = 1;
    private static final int DAMAGE = 3;
    private static final int UPGRADE_DAMAGE = 1;

    public Hailwind() {
        super(ID, COST, TYPE, RARITY, TARGET);
        baseDamage = DAMAGE;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        for (int i = 0; i < getPlayerWindCharges(); i++) {
            addToBot(new VFXAction(new ThrowDaggerEffect(m.hb_x, m.hb_y)));
            damageMonster(m, damage, AbstractGameAction.AttackEffect.SLASH_HORIZONTAL);
        }
        addToBot(new RemoveSpecificPowerAction(p, p, WindChargePower.POWER_ID));
    }

    @Override
    public void onUpgrade() {
        upgradeDamage(UPGRADE_DAMAGE);
    }
}