package code.cards;

import code.cards.AbstractBlademasterCard;

import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static code.Blademaster.makeID;

public class InnerFire extends AbstractBlademasterCard {

    public final static String ID = makeID("InnerFire");
    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.SKILL;
    private static final int COST = 0;
    private static final int FURY_REQ = 15;
    private static final int MAGIC = 2;
    private static final int UPGRADE_MAGIC = 1;

    public InnerFire() {
        super(ID, COST, TYPE, RARITY, TARGET, FURY_REQ, 0);
        baseMagicNumber = magicNumber = MAGIC;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        consumeFinisherCost();
        addToBot(new GainEnergyAction(magicNumber));
    }

    @Override
    public void onUpgrade() {
        upgradeMagicNumber(UPGRADE_MAGIC);
        setUpgradeDescription();
    }
}