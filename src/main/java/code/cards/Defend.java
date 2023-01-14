package code.cards;

import code.actions.LightningStanceAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static code.Blademaster.makeID;

public class Defend extends AbstractStanceCard {

    public final static String ID = makeID("Defend");
    private static final CardRarity RARITY = CardRarity.BASIC;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.SKILL;
    private static final int COST = 1;
    private static final int BLOCK = 5;
    private static final int UPGRADE_BLOCK = 3;
    private static final int CONDUIT = 1;
    private static final int UPGRADE_CONDUIT = 1;

    public Defend() {
        super(ID, COST, TYPE, RARITY, TARGET);
        baseBlock = BLOCK;
        baseConduit = conduit = CONDUIT;
        updateDescription();
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