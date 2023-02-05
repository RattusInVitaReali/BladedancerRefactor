package code.cards;

import code.powers.stances.LightningChargePower;
import code.powers.stances.WindChargePower;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import java.util.prefs.BackingStoreException;

import static code.Blademaster.makeID;
import static code.util.BlademasterUtil.*;

public class Requiem extends AbstractBlademasterCard {

    public final static String ID = makeID("Requiem");
    private static final CardRarity RARITY = CardRarity.RARE;
    private static final CardTarget TARGET = CardTarget.ALL_ENEMY;
    private static final CardType TYPE = CardType.ATTACK;
    private static final int COST = 0;
    private static final int DAMAGE = 0;
    private static final int FURY_REQ = 25;
    private static final int UPGRADE_FURY_REQ = 20;

    public Requiem() {
        super(ID, COST, TYPE, RARITY, TARGET, FURY_REQ, 0);
        baseDamage = DAMAGE;
        isMultiDamage = true;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        consumeFinisherCost();
        for (AbstractMonster monster : getAliveMonsters()) {
            lightningEffect(monster);
        }
        damageAllMonsters(multiDamage, AbstractGameAction.AttackEffect.SLASH_HEAVY);
        addToBot(new RemoveSpecificPowerAction(p, p, WindChargePower.POWER_ID));
        addToBot(new RemoveSpecificPowerAction(p, p, LightningChargePower.POWER_ID));
    }

    @Override
    public void applyPowers() {
        baseDamage = 2 * (getPlayerWindCharges() + getPlayerLightningCharges());
        super.applyPowers();
        if (baseDamage != DAMAGE) {
            isDamageModified = true;
            rawDescription = cardStrings.EXTENDED_DESCRIPTION[0];
        } else {
            rawDescription = cardStrings.DESCRIPTION;
        }
        initializeDescription();
    }

    @Override
    public void onMoveToDiscard() {
        setDescription(cardStrings.DESCRIPTION);
    }

    @Override
    public void onUpgrade() {
        upgradeFuryCost(UPGRADE_FURY_REQ);
    }
}