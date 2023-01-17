package code.cards;

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
    private static final int UPGRADE_DAMAGE = 3;
    private static final int FURY_REQ = 10;

    public RagingBlow() {
        super(ID, COST, TYPE, RARITY, TARGET);
        baseDamage = DAMAGE;
        setDescription(cardStrings.DESCRIPTION);
    }

    @Override
    public int furyReq() {
        return FURY_REQ;
    }

    @Override
    public void useBasic(AbstractPlayer p, AbstractMonster m) {
        consumeFinisherCost();
        addToBot(new VFXAction(new ClashEffect(m.drawX, m.drawY)));
        damageMonster(m, damage, AbstractGameAction.AttackEffect.NONE);
    }

    @Override
    public void useWind(AbstractPlayer p, AbstractMonster m) {
        useBasic(p, m);
        for (AbstractMonster monster : AbstractDungeon.getMonsters().monsters) {
            lightningEffect(monster);
            damageMonster(monster, getPlayerWindCharges(), AbstractGameAction.AttackEffect.NONE);
        }
        addToBot(new RemoveSpecificPowerAction(p, p, WindChargePower.POWER_ID));
    }

    @Override
    public void useLightning(AbstractPlayer p, AbstractMonster m) {
        useBasic(p, m);
        lightningEffect(m);
        damageMonster(m, 2 * getPlayerLightningCharges(), AbstractGameAction.AttackEffect.NONE);
        addToBot(new RemoveSpecificPowerAction(p, p, LightningChargePower.POWER_ID));
    }

    @Override
    public void calculateCardDamage(AbstractMonster m) {
        super.calculateCardDamage(m);
        if (isBloodied(m)) {
            damage *= 2;
            this.isDamageModified = true;
        }
    }

    @Override
    public void onUpgrade() {
        upgradeDamage(UPGRADE_DAMAGE);
    }
}