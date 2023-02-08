package code.cards;

import code.actions.WindStanceAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.BufferPower;

import static code.Blademaster.makeID;
import static code.util.BlademasterUtil.playerApplyPower;

public class WindBarrier extends AbstractStanceCard {

    public final static String ID = makeID("WindBarrier");
    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.SKILL;
    private static final int COST = 2;
    private static final int BLOCK = 15;
    private static final int UPGRADE_BLOCK = 5;
    private static final int MAGIC = 1;
    private static final int CONDUIT = 3;
    private static final int UPGRADE_CONDUIT = 2;

    public WindBarrier() {
        super(ID, COST, TYPE, RARITY, TARGET);
        baseBlock = BLOCK;
        baseMagicNumber = magicNumber = MAGIC;
        baseConduit = conduit = CONDUIT;
        exhaust = true;
    }

    @Override
    public void useBasic(AbstractPlayer p, AbstractMonster m) {
        block(block);
        addToBot(new WindStanceAction());
    }

    @Override
    public void useWind(AbstractPlayer p, AbstractMonster m) {
        block(block);
        playerApplyPower(p, new BufferPower(p, magicNumber));
    }

    @Override
    public void onUpgrade() {
        upgradeBlock(UPGRADE_BLOCK);
        upgradeConduit(UPGRADE_CONDUIT);
    }
}