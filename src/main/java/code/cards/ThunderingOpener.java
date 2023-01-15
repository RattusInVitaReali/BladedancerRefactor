package code.cards;

import code.Blademaster;
import code.actions.LightningStanceAction;
import code.util.BlademasterUtil;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.vfx.combat.LightningEffect;

import static code.Blademaster.makeID;

public class ThunderingOpener extends AbstractBlademasterCard {

    public final static String ID = makeID("ThunderingOpener");
    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.ENEMY;
    private static final CardType TYPE = CardType.ATTACK;
    private static final int COST = 1;
    private static final int DAMAGE = 7;
    private static final int UPGRADE_DAMAGE = 4;

    public ThunderingOpener() {
        super(ID, COST, TYPE, RARITY, TARGET);
        baseDamage = DAMAGE;
        this.isInnate = true;
        this.exhaust = true;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        damageMonster(m, damage, AbstractGameAction.AttackEffect.NONE);
        BlademasterUtil.lightningEffect(m);
        addToBot(new LightningStanceAction());
    }

    @Override
    public void onUpgrade() {
        upgradeDamage(UPGRADE_DAMAGE);
    }
}