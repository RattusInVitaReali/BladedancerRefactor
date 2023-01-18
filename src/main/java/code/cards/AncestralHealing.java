package code.cards;

import com.megacrit.cardcrawl.actions.common.HealAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static code.Blademaster.makeID;

public class AncestralHealing extends AbstractStanceCard {

    public final static String ID = makeID("AncestralHealing");
    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.SKILL;
    private static final int COST = 2;
    private static final int MAGIC = 6;
    private static final int UPGRADE_MAGIC = 3;
    private static final int CONDUIT = 3;
    private static final int UPGRADE_CONDUIT = 2;

    public AncestralHealing() {
        super(ID, COST, TYPE, RARITY, TARGET);
        baseMagicNumber = magicNumber = MAGIC;
        baseConduit = conduit = CONDUIT;
        this.exhaust = true;
        setDescription(cardStrings.DESCRIPTION);
    }

    @Override
    public void useBasic(AbstractPlayer p, AbstractMonster m) {
        addToBot(new HealAction(p, p, this.magicNumber, .8f));
    }

    @Override
    public void onUpgrade() {
        upgradeMagicNumber(UPGRADE_MAGIC);
        upgradeConduit(UPGRADE_CONDUIT);
    }
}