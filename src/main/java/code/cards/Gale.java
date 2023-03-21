package code.cards;

import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.vfx.combat.CleaveEffect;

import static code.Blademaster.makeID;

public class Gale extends AbstractStanceCard {

    public final static String ID = makeID("Gale");
    private static final CardRarity RARITY = CardRarity.SPECIAL;
    private static final CardTarget TARGET = CardTarget.ENEMY;
    private static final CardType TYPE = CardType.ATTACK;
    private static final int COST = 1;
    private static final int ALL_DAMAGE = 4;
    private static final int UPGRADE_ALL_DAMAGE = 1;
    private static final int DAMAGE = 4;
    private static final int UPGRADE_DAMAGE = 1;
    private static final int CONDUIT = 1;
    private static final int UPGRADE_CONDUIT = 1;

    public Gale() {
        super(ID, COST, TYPE, RARITY, TARGET, CardColor.COLORLESS, 0, 0);
        baseDamage = ALL_DAMAGE;
        isMultiDamage = true;
        baseSecondDamage = DAMAGE;
        cardsToPreview = new Zephyr();
        setBaseConduit(CONDUIT);
        this.exhaust = true;
        this.isEthereal = true;
    }

    @Override
    public void useBasic(AbstractPlayer p, AbstractMonster m) {
        damageMonster(m, secondDamage, AbstractGameAction.AttackEffect.SLASH_DIAGONAL);
        addToBot(new VFXAction(new CleaveEffect(), .2f));
        damageAllMonsters(multiDamage, AbstractGameAction.AttackEffect.NONE);
        Zephyr zephyr = new Zephyr();
        if (upgraded) zephyr.upgrade();
        addToBot(new MakeTempCardInHandAction(zephyr));
    }

    @Override
    public void triggerOnGlowCheck() {
        glowColor = Color.WHITE.cpy();
    }

    @Override
    public void onUpgrade() {
        upgradeDamage(UPGRADE_ALL_DAMAGE);
        upgradeSecondDamage(UPGRADE_DAMAGE);
        upgradeConduit(UPGRADE_CONDUIT);
        cardsToPreview.upgrade();
    }
}