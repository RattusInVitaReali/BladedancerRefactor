package code.cards;

import code.Blademaster;
import code.util.BlademasterUtil;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.VulnerablePower;
import com.megacrit.cardcrawl.powers.WeakPower;

import static code.Blademaster.makeID;
import static code.util.BlademasterUtil.*;

public class Discharge extends AbstractStanceCard {

    public final static String ID = makeID("Discharge");
    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.SKILL;
    private static final int COST = 1;
    private static final int DAMAGE = 0;
    private static final int BLOCK = 6;
    private static final int UPGRADE_BLOCK = 3;
    private static final int MAGIC = 1;
    private static final int UPGRADE_MAGIC = 1;

    public Discharge() {
        super(ID, COST, TYPE, RARITY, TARGET);
        baseDamage = 0;
        baseBlock = BLOCK;
        baseMagicNumber = magicNumber = MAGIC;
        setDescription(cardStrings.DESCRIPTION);
    }

    @Override
    public void useBasic(AbstractPlayer p, AbstractMonster m) {
        block(block);
    }

    @Override
    public void useWind(AbstractPlayer p, AbstractMonster m) {
        useBasic(p, m);
        AbstractMonster monster = AbstractDungeon.getRandomMonster();
        BlademasterUtil.lightningEffect(monster);
        damageMonster(monster, damage, AbstractGameAction.AttackEffect.NONE);
        playerApplyPower(monster, new WeakPower(monster, magicNumber, false));
    }

    @Override
    public void useLightning(AbstractPlayer p, AbstractMonster m) {
        useBasic(p, m);
        AbstractMonster monster = AbstractDungeon.getRandomMonster();
        BlademasterUtil.lightningEffect(monster);
        damageMonster(monster, damage, AbstractGameAction.AttackEffect.NONE);
        playerApplyPower(monster, new VulnerablePower(monster, magicNumber, false));
    }

    @Override
    public void applyPowers() {
        if (getStance() == Blademaster.BlademasterStance.BASIC) {
            super.applyPowers();
            return;
        }
        if (getStance() == Blademaster.BlademasterStance.WIND) {
            baseDamage = getPlayerWindCharges();
            super.applyPowers();
            if (baseDamage != DAMAGE) {
                rawDescription = windCardStrings.EXTENDED_DESCRIPTION[0];
                isDamageModified = true;
            } else {
                rawDescription = windCardStrings.DESCRIPTION;
            }
        }
        if (getStance() == Blademaster.BlademasterStance.LIGHTNING) {
            baseDamage = getPlayerLightningCharges();
            super.applyPowers();
            if (baseDamage != DAMAGE) {
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
        upgradeBlock(UPGRADE_BLOCK);
        upgradeMagicNumber(UPGRADE_MAGIC);
    }
}