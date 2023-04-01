package code.cards;

import code.actions.WindStanceAction;
import code.powers.stances.WindChargePower;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.DexterityPower;
import com.megacrit.cardcrawl.powers.LoseDexterityPower;

import static code.Blademaster.makeID;
import static code.util.BlademasterUtil.playerApplyPower;

public class TimeToLeave extends AbstractStanceCard {

    public final static String ID = makeID("TimeToLeave");
    private static final CardRarity RARITY = CardRarity.COMMON;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.SKILL;
    private static final int COST = 1;
    private static final int MAGIC = 3;
    private static final int UPGRADE_MAGIC = 2;
    private static final int SECOND_MAGIC = 2;
    private static final int UPGRADE_SECOND_MAGIC = 2;

    public TimeToLeave() {
        super(ID, COST, TYPE, RARITY, TARGET);
        baseMagicNumber = magicNumber = MAGIC;
        baseSecondMagic = secondMagic = SECOND_MAGIC;
    }

    @Override
    public void useBasic(AbstractPlayer p, AbstractMonster m) {
        playerApplyPower(p, new DexterityPower(p, magicNumber));
        playerApplyPower(p, new LoseDexterityPower(p, secondMagic));
        addToBot(new WindStanceAction());
    }

    @Override
    public void useWind(AbstractPlayer p, AbstractMonster m) {
        playerApplyPower(p, new DexterityPower(p, magicNumber));
        playerApplyPower(p, new LoseDexterityPower(p, secondMagic));
        playerApplyPower(p, new WindChargePower(p, magicNumber));
    }

    @Override
    public void onUpgrade() {
        upgradeMagicNumber(UPGRADE_MAGIC);
        upgradeSecondMagic(UPGRADE_SECOND_MAGIC);
    }
}