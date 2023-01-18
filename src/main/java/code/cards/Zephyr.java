package code.cards;

import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.vfx.combat.CleaveEffect;

import static code.Blademaster.makeID;

public class Zephyr extends AbstractStanceCard {

    public final static String ID = makeID("Zephyr");
    private static final CardRarity RARITY = CardRarity.BASIC;
    private static final CardTarget TARGET = CardTarget.ENEMY;
    private static final CardType TYPE = CardType.ATTACK;
    private static final int COST = 1;
    private static final int ALL_DAMAGE = 5;
    private static final int UPGRADE_ALL_DAMAGE = 2;
    private static final int DAMAGE = 6;
    private static final int UPGRADE_DAMAGE = 3;
    private static final int CONDUIT = 1;
    private static final int UPGRADE_CONDUIT = 1;

    public Zephyr() {
        super(ID, COST, TYPE, RARITY, TARGET);
        baseDamage = ALL_DAMAGE;
        baseSecondDamage = DAMAGE;
        baseConduit = conduit = CONDUIT;
        this.exhaust = true;
        this.isEthereal = true;
        setDescription(cardStrings.DESCRIPTION);
    }

    @Override
    public void useBasic(AbstractPlayer p, AbstractMonster m) {
        damageMonster(m, secondDamage, AbstractGameAction.AttackEffect.SLASH_DIAGONAL);
        addToBot(new VFXAction(new CleaveEffect(), .2f));
        damageAllMonsters(multiDamage, AbstractGameAction.AttackEffect.NONE);
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
    }
}