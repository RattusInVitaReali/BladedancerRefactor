package code.cards;

import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.DrawCardNextTurnPower;
import com.megacrit.cardcrawl.powers.EnergizedPower;

import static code.Blademaster.makeID;
import static code.util.BlademasterUtil.playerApplyPower;

public class CombatPrep extends AbstractStanceCard {

    public final static String ID = makeID("CombatPrep");
    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.SKILL;
    private static final int COST = 1;
    private static final int MAGIC = 1;
    private static final int UPGRADE_MAGIC = 1;
    private static final int CONDUIT = 3;
    private static final int UPGRADE_CONDUIT = 2;

    public CombatPrep() {
        super(ID, COST, TYPE, RARITY, TARGET);
        baseMagicNumber = magicNumber = MAGIC;
        setBaseConduit(CONDUIT);
    }

    @Override
    public void useBasic(AbstractPlayer p, AbstractMonster m) {
        playerApplyPower(p, new EnergizedPower(p, magicNumber));
        playerApplyPower(p, new DrawCardNextTurnPower(p, magicNumber));
    }

    @Override
    public void onUpgrade() {
        upgradeMagicNumber(UPGRADE_MAGIC);
        upgradeConduit(UPGRADE_CONDUIT);
    }
}