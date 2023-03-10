package code.cards;

import code.patches.BlademasterTags;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static code.Blademaster.makeID;

public class Execute extends AbstractBlademasterCard {

    public final static String ID = makeID("Execute");
    private static final CardRarity RARITY = CardRarity.COMMON;
    private static final CardTarget TARGET = CardTarget.ENEMY;
    private static final CardType TYPE = CardType.ATTACK;
    private static final int COST = 1;
    private static final int DAMAGE = 7;
    private static final int SECOND_DAMAGE = 9;
    private static final int UPGRADE_SECOND_DAMAGE = 5;

    public Execute() {
        super(ID, COST, TYPE, RARITY, TARGET);
        baseDamage = DAMAGE;
        baseSecondDamage = SECOND_DAMAGE;
        tags.add(BlademasterTags.BLOODIED);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        damageMonster(m, damage, AbstractGameAction.AttackEffect.SLASH_DIAGONAL);
        useBloodiedWrapper(p, m);
    }

    @Override
    public void useBloodied(AbstractPlayer p, AbstractMonster m) {
        damageMonster(m, secondDamage, AbstractGameAction.AttackEffect.SLASH_HEAVY);
    }

    @Override
    public void onUpgrade() {
        upgradeSecondDamage(UPGRADE_SECOND_DAMAGE);
    }
}