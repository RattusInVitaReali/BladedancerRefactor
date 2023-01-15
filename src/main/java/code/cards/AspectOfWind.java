package code.cards;

import code.actions.WindStanceAction;
import code.powers.stances.WindCharge;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static code.Blademaster.makeID;
import static code.util.BlademasterUtil.playerApplyPower;

public class AspectOfWind extends AbstractStanceCard {

    public final static String ID = makeID("AspectOfWind");
    private static final CardRarity RARITY = CardRarity.COMMON;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.SKILL;
    private static final int COST = 0;
    private static final int MAGIC = 3;
    private static final int UPGRADE_MAGIC = 2;

    public AspectOfWind() {
        super(ID, COST, TYPE, RARITY, TARGET);
        baseMagicNumber = magicNumber = MAGIC;
        exhaust = true;
        updateDescription();
    }

    @Override
    public void useBasic(AbstractPlayer p, AbstractMonster m) {
        addToBot(new WindStanceAction());
    }

    @Override
    public void useWind(AbstractPlayer p, AbstractMonster m) {
        playerApplyPower(p, new WindCharge(p, magicNumber));
    }

    @Override
    public void onUpgrade() {
        upgradeMagicNumber(UPGRADE_MAGIC);
        exhaust = false;
        setUpgradeDescription();
    }
}