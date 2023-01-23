package code.cards;

import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.StrengthPower;

import static code.Blademaster.makeID;
import static code.util.BlademasterUtil.playerApplyPower;

public class EnchantedBlade extends AbstractStanceCard {

    public final static String ID = makeID("EnchantedBlade");
    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.SKILL;
    private static final int COST = 1;
    private static final int COMBO_REQ = 3;
    private static final int MAGIC = 3;
    private static final int UPGRADE_MAGIC = 1;
    private static final int SECOND_MAGIC = 1;
    private static final int CONDUIT = 3;
    private static final int UPGRADE_CONDUIT = 2;

    public EnchantedBlade() {
        super(ID, COST, TYPE, RARITY, TARGET, 0, COMBO_REQ);
        baseMagicNumber = magicNumber = MAGIC;
        baseSecondMagic = secondMagic = SECOND_MAGIC;
        baseConduit = conduit = CONDUIT;
        this.exhaust = true;
    }

    @Override
    public void useBasic(AbstractPlayer p, AbstractMonster m) {
        consumeFinisherCost();
        playerApplyPower(p, new StrengthPower(p, secondMagic));
        addToBot(new DrawCardAction(p, magicNumber));
    }

    @Override
    public void onUpgrade() {
        upgradeMagicNumber(UPGRADE_MAGIC);
        upgradeConduit(UPGRADE_CONDUIT);
    }
}