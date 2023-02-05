package code.cards;

import code.Blademaster;
import code.util.BlademasterUtil;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static code.Blademaster.makeID;
import static code.util.BlademasterUtil.getPlayerLightningCharges;
import static code.util.BlademasterUtil.getPlayerWindCharges;

public class Thrust extends AbstractStanceCard {

    public final static String ID = makeID("Thrust");
    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.ENEMY;
    private static final CardType TYPE = CardType.ATTACK;
    private static final int COST = 1;
    private static final int DAMAGE = 6;
    private static final int UPGRADE_DAMAGE = 3;
    private static final int SECOND_DAMAGE = 0;

    public Thrust() {
        super(ID, COST, TYPE, RARITY, TARGET);
        baseDamage = DAMAGE;
        baseSecondDamage = SECOND_DAMAGE;
    }

    @Override
    public void useBasic(AbstractPlayer p, AbstractMonster m) {
        damageMonster(m, damage, AbstractGameAction.AttackEffect.SLASH_HORIZONTAL);
    }

    @Override
    public void useWind(AbstractPlayer p, AbstractMonster m) {
        useBasic(p, m);
        BlademasterUtil.lightningEffect(m);
        damageMonster(m, secondDamage, AbstractGameAction.AttackEffect.NONE);
    }

    @Override
    public void useLightning(AbstractPlayer p, AbstractMonster m) {
        useBasic(p, m);
        BlademasterUtil.lightningEffect(m);
        damageMonster(m, secondDamage, AbstractGameAction.AttackEffect.NONE);
    }

    @Override
    public void applyPowers() {
        if (getStance() == Blademaster.BlademasterStance.BASIC) return;
        if (getStance() == Blademaster.BlademasterStance.WIND) {
            baseSecondDamage = getPlayerWindCharges();
            super.applyPowers();
            if (baseSecondDamage != SECOND_DAMAGE) {
                rawDescription = windCardStrings.DESCRIPTION;
                rawDescription += windCardStrings.EXTENDED_DESCRIPTION[0];
                isSecondDamageModified = true;
            } else {
                rawDescription = windCardStrings.DESCRIPTION;
            }
        }
        if (getStance() == Blademaster.BlademasterStance.LIGHTNING) {
            baseSecondDamage = getPlayerLightningCharges();
            super.applyPowers();
            if (baseSecondDamage != SECOND_DAMAGE) {
                rawDescription = lightningCardStrings.DESCRIPTION;
                rawDescription += lightningCardStrings.EXTENDED_DESCRIPTION[0];
                isSecondDamageModified = true;
            } else {
                rawDescription = lightningCardStrings.DESCRIPTION;
            }
        }
        initializeDescription();
    }

    @Override
    public void onMoveToDiscard() {
        updateDescription();
    }

    @Override
    public void onUpgrade() {
        upgradeDamage(UPGRADE_DAMAGE);
    }
}