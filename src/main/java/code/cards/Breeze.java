package code.cards;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.vfx.combat.CleaveEffect;

import static code.Blademaster.makeID;

public class Breeze extends AbstractStanceCard {

    public final static String ID = makeID("Breeze");
    private static final CardRarity RARITY = CardRarity.COMMON;
    private static final CardTarget TARGET = CardTarget.ENEMY;
    private static final CardType TYPE = CardType.ATTACK;
    private static final int COST = 1;
    private static final int ALL_DAMAGE = 3;
    private static final int UPGRADE_ALL_DAMAGE = 1;
    private static final int DAMAGE = 3;
    private static final int UPGRADE_DAMAGE = 1;
    private static final int CONDUIT = 1;
    private static final int UPGRADE_CONDUIT = 1;

    public Breeze() {
        super(ID, COST, TYPE, RARITY, TARGET);
        baseDamage = ALL_DAMAGE;
        isMultiDamage = true;
        baseSecondDamage = DAMAGE;
        cardsToPreview = new Gale();
        setBaseConduit(CONDUIT);
    }

    @Override
    public void useBasic(AbstractPlayer p, AbstractMonster m) {
        damageMonster(m, secondDamage, AbstractGameAction.AttackEffect.SLASH_DIAGONAL);
        addToBot(new VFXAction(new CleaveEffect(), .2f));
        damageAllMonsters(multiDamage, AbstractGameAction.AttackEffect.NONE);
        Gale gale = new Gale();
        if (upgraded) gale.upgrade();
        addToBot(new MakeTempCardInHandAction(gale));
    }

    @Override
    public void onUpgrade() {
        upgradeDamage(UPGRADE_ALL_DAMAGE);
        upgradeSecondDamage(UPGRADE_DAMAGE);
        upgradeConduit(UPGRADE_CONDUIT);
        cardsToPreview.upgrade();
    }
}