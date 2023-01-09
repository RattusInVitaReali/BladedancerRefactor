package code.cards;

import code.cards.AbstractBlademasterCard;

import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static code.Blademaster.makeID;
import static code.util.Wiz.*;

public class AirSlash extends AbstractBlademasterCard {
    public final static String ID = makeID("AirSlash");
    private static final CardRarity RARITY = CardRarity.COMMON;
    private static final CardTarget TARGET = CardTarget.ENEMY;
    private static final CardType TYPE = CardType.SKILL;
    private static final int COST = 1;
    private static final int DAMAGE = 7;
    private static final int UPGRADE_DAMAGE = 3;

    public AirSlash() {
        super(ID, COST, TYPE, RARITY, TARGET);
        baseDamage = DAMAGE;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {

    }

    public void onUpgrade() {
        upgradeDamage(UPGRADE_DAMAGE);
    }
}