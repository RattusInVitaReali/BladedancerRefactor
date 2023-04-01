package code.cards;

import code.actions.WindStanceAction;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.utility.SFXAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.vfx.combat.CleaveEffect;

import static code.Blademaster.makeID;

public class WindSlash extends AbstractStanceCard {

    public final static String ID = makeID("WindSlash");
    private static final CardRarity RARITY = CardRarity.COMMON;
    private static final CardTarget TARGET = CardTarget.ALL_ENEMY;
    private static final CardType TYPE = CardType.ATTACK;
    private static final int COST = 1;
    private static final int DAMAGE = 5;
    private static final int UPGRADE_DAMAGE = 3;

    public WindSlash() {
        super(ID, COST, TYPE, RARITY, TARGET);
        baseDamage = DAMAGE;
        isMultiDamage = true;
    }

    @Override
    public void useBasic(AbstractPlayer p, AbstractMonster m) {
        addToBot(new SFXAction("ATTACK_HEAVY"));
        addToBot(new VFXAction(p, new CleaveEffect(), 0.1F));
        damageAllMonsters(multiDamage, AbstractGameAction.AttackEffect.NONE);
        addToBot(new WindStanceAction());
    }

    @Override
    public void useWind(AbstractPlayer p, AbstractMonster m) {
        addToBot(new SFXAction("ATTACK_HEAVY"));
        addToBot(new VFXAction(p, new CleaveEffect(), 0.1F));
        damageAllMonsters(multiDamage, AbstractGameAction.AttackEffect.NONE);
        addToBot(new SFXAction("ATTACK_HEAVY"));
        addToBot(new VFXAction(p, new CleaveEffect(), 0.1F));
        damageAllMonsters(multiDamage, AbstractGameAction.AttackEffect.NONE);
    }

    @Override
    public void onUpgrade() {
        upgradeDamage(UPGRADE_DAMAGE);
    }
}