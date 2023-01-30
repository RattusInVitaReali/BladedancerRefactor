package code.cards;

import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static code.Blademaster.makeID;

public class Empower extends AbstractStanceCard {

    public final static String ID = makeID("Empower");
    private static final CardRarity RARITY = CardRarity.RARE;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.SKILL;
    private static final int COST = 0;
    private static final int MAGIC = 2;
    private static final int SECOND_MAGIC = 1;
    private static final int UPGRADE_SECOND_MAGIC = 1;
    private static final int CONDUIT = 3;
    private static final int UPGRADE_CONDUIT = 2;

    public Empower() {
        super(ID, COST, TYPE, RARITY, TARGET);
        baseMagicNumber = magicNumber = MAGIC;
        baseSecondMagic = secondMagic = SECOND_MAGIC;
        setBaseConduit(CONDUIT);
        exhaust = true;
    }

    @Override
    public void useBasic(AbstractPlayer p, AbstractMonster m) {
        addToBot(new DrawCardAction(p, magicNumber));
        addToBot(new GainEnergyAction(secondMagic));
    }

    @Override
    public void onUpgrade() {
        upgradeSecondMagic(UPGRADE_SECOND_MAGIC);
        upgradeConduit(UPGRADE_CONDUIT);
    }
}