package code.cards;

import code.cards.AbstractBlademasterCard;

import code.powers.BleedingPower;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static code.Blademaster.makeID;
import static code.util.BlademasterUtil.getPowerAmount;
import static code.util.BlademasterUtil.playerApplyPower;

public class Rupture extends AbstractBlademasterCard {

    public final static String ID = makeID("Rupture");
    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.ENEMY;
    private static final CardType TYPE = CardType.SKILL;
    private static final int COST = 1;
    private static final int MAGIC = 1;
    private static final int UPGRADE_MAGIC = 1;

    public Rupture() {
        super(ID, COST, TYPE, RARITY, TARGET);
        baseMagicNumber = magicNumber = MAGIC;
        exhaust = true;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        playerApplyPower(m, new BleedingPower(m, getPowerAmount(m, BleedingPower.POWER_ID) * magicNumber));
    }

    @Override
    public void onUpgrade() {
        upgradeMagicNumber(UPGRADE_MAGIC);
        setUpgradeDescription();
    }
}