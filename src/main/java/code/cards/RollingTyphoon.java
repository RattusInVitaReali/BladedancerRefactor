package code.cards;

import code.Blademaster;
import code.powers.BleedingPower;
import code.util.BlademasterUtil;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.vfx.combat.GrandFinalEffect;

import static code.Blademaster.makeID;
import static code.util.BlademasterUtil.getPlayerLightningCharges;
import static code.util.BlademasterUtil.getPlayerWindCharges;

public class RollingTyphoon extends AbstractStanceCard {

    public final static String ID = makeID("RollingTyphoon");
    private static final CardRarity RARITY = CardRarity.RARE;
    private static final CardTarget TARGET = CardTarget.ENEMY;
    private static final CardType TYPE = CardType.ATTACK;
    private static final int COST = 0;
    private static final int FURY_REQ = 20;
    private static final int MULTI_DAMAGE = 10;
    private static final int UPGRADE_MULTI_DAMAGE = 3;
    private static final int DAMAGE = 15;
    private static final int UPGRADE_DAMAGE = 7;
    private static final int MAGIC = 0;
    private static final int BLOCK = 0;

    public RollingTyphoon() {
        super(ID, COST, TYPE, RARITY, TARGET, FURY_REQ, 0);
        this.isMultiDamage = true;
        baseDamage = MULTI_DAMAGE;
        baseSecondDamage = DAMAGE;
        baseMagicNumber = magicNumber = MAGIC;
        baseBlock = BLOCK;
        setDescription(cardStrings.DESCRIPTION);
    }

    @Override
    public void useBasic(AbstractPlayer p, AbstractMonster m) {
        consumeFinisherCost();
        addToBot(new VFXAction(new GrandFinalEffect(), .8f));
        damageMonster(m, secondDamage, AbstractGameAction.AttackEffect.SLASH_HEAVY);
        damageAllMonsters(multiDamage, AbstractGameAction.AttackEffect.SLASH_HEAVY);
    }

    @Override
    public void useWind(AbstractPlayer p, AbstractMonster m) {
        useBasic(p, m);
        BlademasterUtil.playerApplyPower(m, new BleedingPower(m, magicNumber));
    }

    @Override
    public void useLightning(AbstractPlayer p, AbstractMonster m) {
        useBasic(p, m);
        block(block);
    }

    @Override
    public void applyPowers() {
        if (getStance() == Blademaster.BlademasterStance.BASIC) {
            super.applyPowers();
            return;
        }
        if (getStance() == Blademaster.BlademasterStance.WIND) {
            baseMagicNumber = magicNumber = getPlayerWindCharges();
            super.applyPowers();
            if (baseMagicNumber != MAGIC) {
                rawDescription = windCardStrings.EXTENDED_DESCRIPTION[0];
                isMagicNumberModified = true;
            } else {
                rawDescription = windCardStrings.DESCRIPTION;
            }
        }
        if (getStance() == Blademaster.BlademasterStance.LIGHTNING) {
            baseBlock = getPlayerLightningCharges() * 2;
            super.applyPowers();
            if (baseBlock != BLOCK) {
                rawDescription = lightningCardStrings.EXTENDED_DESCRIPTION[0];
                isBlockModified = true;
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
        upgradeDamage(UPGRADE_MULTI_DAMAGE);
        upgradeSecondDamage(UPGRADE_DAMAGE);
    }
}