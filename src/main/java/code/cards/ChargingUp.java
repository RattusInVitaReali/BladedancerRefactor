package code.cards;

import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static code.Blademaster.makeID;

public class ChargingUp extends AbstractStanceCard {

    public final static String ID = makeID("ChargingUp");
    private static final CardRarity RARITY = CardRarity.COMMON;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.SKILL;
    private static final int COST = 1;
    private static final int BLOCK = 6;
    private static final int UPGRADE_BLOCK = 3;
    private static final int CONDUIT = 4;
    private static final int UPGRADE_CONDUIT = 2;

    public ChargingUp() {
        super(ID, COST, TYPE, RARITY, TARGET);
        baseBlock = BLOCK;
        baseConduit = conduit = CONDUIT;
        setDescription(cardStrings.DESCRIPTION);
    }

    @Override
    public void useBasic(AbstractPlayer p, AbstractMonster m) {
        block(block);
    }

    @Override
    public void onUpgrade() {
        upgradeBlock(UPGRADE_BLOCK);
        upgradeConduit(UPGRADE_CONDUIT);
    }
}