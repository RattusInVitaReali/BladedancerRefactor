package code.cards;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static code.Blademaster.makeID;

public class Sunder extends AbstractStanceCard {

    public final static String ID = makeID("Sunder");
    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.ENEMY;
    private static final CardType TYPE = CardType.ATTACK;
    private static final int COST = 1;
    private static final int COMBO_REQ = 2;
    private static final int DAMAGE = 14;
    private static final int UPGRADE_DAMAGE = 4;
    private static final int CONDUIT = 4;
    private static final int UPGRADE_CONDUIT = 2;

    public Sunder() {
        super(ID, COST, TYPE, RARITY, TARGET, 0, COMBO_REQ);
        baseDamage = DAMAGE;
        setBaseConduit(CONDUIT);
    }

    @Override
    public void useBasic(AbstractPlayer p, AbstractMonster m) {
        consumeFinisherCost();
        damageMonster(m, damage, AbstractGameAction.AttackEffect.SLASH_HEAVY);
    }

    @Override
    public void onUpgrade() {
        upgradeDamage(UPGRADE_DAMAGE);
        upgradeConduit(UPGRADE_CONDUIT);
    }
}