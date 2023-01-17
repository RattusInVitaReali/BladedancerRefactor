package code.cards;

import code.powers.BleedingPower;
import code.util.BlademasterUtil;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.vfx.combat.GrandFinalEffect;

import static code.Blademaster.makeID;

public class RollingTyphoon extends AbstractStanceCard {

    public final static String ID = makeID("RollingTyphoon");
    private static final CardRarity RARITY = CardRarity.RARE;
    private static final CardTarget TARGET = CardTarget.ENEMY;
    private static final CardType TYPE = CardType.ATTACK;
    private static final int COST = 0;
    private static final int MULTI_DAMAGE = 10;
    private static final int UPGRADE_MULTI_DAMAGE = 3;
    private static final int DAMAGE = 15;
    private static final int UPGRADE_DAMAGE = 7;
    private static final int FURY_REQ = 30;

    public RollingTyphoon() {
        super(ID, COST, TYPE, RARITY, TARGET);
        this.isMultiDamage = true;
        baseDamage = MULTI_DAMAGE;
        baseSecondDamage = DAMAGE;
        setDescription(cardStrings.DESCRIPTION);
    }

    @Override
    public int furyReq() {
        return FURY_REQ;
    }

    @Override
    public void useBasic(AbstractPlayer p, AbstractMonster m) {
        consumeFinisherCost();
        addToBot(new VFXAction(new GrandFinalEffect(), .8f));
        damageAllMonsters(multiDamage, AbstractGameAction.AttackEffect.SLASH_DIAGONAL);
        damageMonster(m, secondDamage, AbstractGameAction.AttackEffect.SLASH_HEAVY);
    }

    @Override
    public void useWind(AbstractPlayer p, AbstractMonster m) {
        useBasic(p, m);
        BlademasterUtil.playerApplyPower(m, new BleedingPower(m, BlademasterUtil.getPlayerWindCharges()));
    }

    @Override
    public void useLightning(AbstractPlayer p, AbstractMonster m) {
        useBasic(p, m);
        block(2 * BlademasterUtil.getPlayerLightningCharges());
    }

    @Override
    public void onUpgrade() {
        upgradeDamage(UPGRADE_MULTI_DAMAGE);
        upgradeSecondDamage(UPGRADE_DAMAGE);
    }
}