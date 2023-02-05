package code.cards;

import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static code.Blademaster.makeID;
import static code.util.BlademasterUtil.getPlayerLightningCharges;
import static code.util.BlademasterUtil.getPlayerWindCharges;

public class HighVoltage extends AbstractBlademasterCard {

    public final static String ID = makeID("HighVoltage");
    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.SKILL;
    private static final int COST = 0;
    private static final int BLOCK = 0;

    public HighVoltage() {
        super(ID, COST, TYPE, RARITY, TARGET);
        baseBlock = BLOCK;
        this.exhaust = true;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        block(block);
    }

    @Override
    public void applyPowers() {
        baseBlock = getPlayerLightningCharges() + getPlayerWindCharges();
        super.applyPowers();
        if (baseBlock != BLOCK) {
            rawDescription = upgraded ? cardStrings.UPGRADE_DESCRIPTION : cardStrings.DESCRIPTION;
            rawDescription += cardStrings.EXTENDED_DESCRIPTION[0];
        } else {
            rawDescription = upgraded ? cardStrings.UPGRADE_DESCRIPTION : cardStrings.DESCRIPTION;
        }
        initializeDescription();
    }

    @Override
    public void onMoveToDiscard() {
        setDescription(upgraded ? cardStrings.UPGRADE_DESCRIPTION : cardStrings.DESCRIPTION);
    }

    @Override
    public void onUpgrade() {
        this.retain = true;
        setUpgradeDescription();
    }
}