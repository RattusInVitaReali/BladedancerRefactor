package code.cards;

import code.Blademaster;
import code.powers.stances.LightningChargePower;
import code.util.BlademasterUtil;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static code.Blademaster.makeID;
import static code.util.BlademasterUtil.getPlayerLightningCharges;

public class Stormstrike extends AbstractStanceCard {

    public final static String ID = makeID("Stormstrike");
    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.ENEMY;
    private static final CardType TYPE = CardType.ATTACK;
    private static final int COST = 2;
    private static final int DAMAGE = 12;
    private static final int UPGRADE_DAMAGE = 4;
    private static final int SECOND_DAMAGE = 0;
    private static final int MAGIC = 3;
    private static final int UPGRADE_MAGIC = 1;

    public Stormstrike() {
        super(ID, COST, TYPE, RARITY, TARGET);
        baseDamage = DAMAGE;
        baseMagicNumber = magicNumber = MAGIC;
        baseSecondDamage = SECOND_DAMAGE;
        tags.add(CardTags.STRIKE);
    }

    @Override
    public void useBasic(AbstractPlayer p, AbstractMonster m) {
        damageMonster(m, damage, AbstractGameAction.AttackEffect.SLASH_VERTICAL);
    }

    @Override
    public void useLightning(AbstractPlayer p, AbstractMonster m) {
        useBasic(p, m);
        if (getPlayerLightningCharges() > 0) {
            BlademasterUtil.lightningEffect(m);
            damageMonster(m, secondDamage, AbstractGameAction.AttackEffect.NONE);
            addToBot(new RemoveSpecificPowerAction(p, p, LightningChargePower.POWER_ID));
        }
    }

    @Override
    public void applyPowers() {
        if (getStance() == Blademaster.BlademasterStance.BASIC) {
            super.applyPowers();
            return;
        }
        if (getStance() == Blademaster.BlademasterStance.LIGHTNING) {
            baseSecondDamage = magicNumber * getPlayerLightningCharges();
            super.applyPowers();
            if (baseSecondDamage != SECOND_DAMAGE) {
                isSecondDamageModified = true;
                rawDescription = upgraded ? lightningCardStrings.EXTENDED_DESCRIPTION[1] : lightningCardStrings.EXTENDED_DESCRIPTION[0];
            } else {
                rawDescription = upgraded ? lightningCardStrings.UPGRADE_DESCRIPTION : lightningCardStrings.DESCRIPTION;
            }
            initializeDescription();
        }
    }

    @Override
    public void onMoveToDiscard() {
        setDescription(cardStrings.DESCRIPTION);
    }

    @Override
    public void onUpgrade() {
        upgradeDamage(UPGRADE_DAMAGE);
        upgradeMagicNumber(UPGRADE_MAGIC);
    }
}