package code.cards;

import code.Blademaster;
import code.patches.BlademasterTags;
import code.powers.MassacrePower;
import code.powers.stances.LightningChargePower;
import code.powers.stances.WindChargePower;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.vfx.combat.ClashEffect;

import static code.Blademaster.makeID;
import static code.util.BlademasterUtil.*;

public class RagingBlow extends AbstractStanceCard {

    public final static String ID = makeID("RagingBlow");
    private static final CardRarity RARITY = CardRarity.BASIC;
    private static final CardTarget TARGET = CardTarget.ENEMY;
    private static final CardType TYPE = CardType.ATTACK;
    private static final int COST = 0;
    private static final int DAMAGE = 8;
    private static final int SECOND_DAMAGE = 0;
    private static final int UPGRADE_DAMAGE = 3;
    private static final int FURY_REQ = 10;

    public RagingBlow() {
        super(ID, COST, TYPE, RARITY, TARGET, FURY_REQ, 0);
        baseSecondDamage = DAMAGE;
        baseDamage = SECOND_DAMAGE;
        setDescription(cardStrings.DESCRIPTION);
        tags.add(BlademasterTags.BLOODIED);
    }

    @Override
    public void useBasic(AbstractPlayer p, AbstractMonster m) {
        consumeFinisherCost();
        addToBot(new VFXAction(new ClashEffect(m.hb.cX, m.hb.cY), 0.1F));
        damageMonster(m, secondDamage, AbstractGameAction.AttackEffect.NONE);
        useBloodiedWrapper(p, m);
    }

    @Override
    public void useWind(AbstractPlayer p, AbstractMonster m) {
        useBasic(p, m);
        for (AbstractMonster monster : getAliveMonsters()) {
            lightningEffect(monster);
        }
        damageAllMonsters(multiDamage, AbstractGameAction.AttackEffect.SLASH_DIAGONAL);
        addToBot(new RemoveSpecificPowerAction(p, p, WindChargePower.POWER_ID));
    }

    @Override
    public void useLightning(AbstractPlayer p, AbstractMonster m) {
        useBasic(p, m);
        lightningEffect(m);
        damageMonster(m, damage, AbstractGameAction.AttackEffect.SLASH_DIAGONAL);
        addToBot(new RemoveSpecificPowerAction(p, p, LightningChargePower.POWER_ID));
    }

    @Override
    public void setStance(Blademaster.BlademasterStance stance) {
        super.setStance(stance);
        isMultiDamage = getStance() == Blademaster.BlademasterStance.WIND;
    }

    @Override
    public void calculateCardDamage(AbstractMonster m) {
        super.calculateCardDamage(m);
        if (isBloodied(m)) {
            if (AbstractDungeon.player.hasPower(MassacrePower.POWER_ID))
                secondDamage *= 3;
            else
                secondDamage *= 2;
            this.isSecondDamageModified = true;
        }
    }

    @Override
    public void applyPowers() {
        if (getStance() == Blademaster.BlademasterStance.BASIC) return;
        if (getStance() == Blademaster.BlademasterStance.WIND) {
            baseDamage = getPlayerWindCharges();
            super.applyPowers();
            if (baseDamage != SECOND_DAMAGE) {
                rawDescription = windCardStrings.EXTENDED_DESCRIPTION[0];
                isDamageModified = true;
            } else {
                rawDescription = windCardStrings.DESCRIPTION;
            }
        }
        if (getStance() == Blademaster.BlademasterStance.LIGHTNING) {
            baseDamage = getPlayerLightningCharges() * 2;
            super.applyPowers();
            if (baseDamage != SECOND_DAMAGE) {
                rawDescription = lightningCardStrings.EXTENDED_DESCRIPTION[0];
                isDamageModified = true;
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
        upgradeSecondDamage(UPGRADE_DAMAGE);
    }
}