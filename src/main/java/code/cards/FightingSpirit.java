package code.cards;

import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static code.Blademaster.makeID;

public class FightingSpirit extends AbstractBlademasterCard {

    public final static String ID = makeID("FightingSpirit");
    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.SKILL;
    private static final int COST = 0;
    private static final int COMBO_REQ = 3;
    private static final int MAGIC = 2;
    private static final int UPGRADE_MAGIC = 1;

    public FightingSpirit() {
        super(ID, COST, TYPE, RARITY, TARGET, 0, COMBO_REQ);
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