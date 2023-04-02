package code.cards;

import code.actions.LightningStanceAction;
import code.powers.stances.LightningChargePower;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.LoseStrengthPower;
import com.megacrit.cardcrawl.powers.StrengthPower;

import static code.Blademaster.makeID;
import static code.util.BlademasterUtil.playerApplyPower;

public class TimeToStrike extends AbstractStanceCard {

    public final static String ID = makeID("TimeToStrike");
    private static final CardRarity RARITY = CardRarity.COMMON;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.SKILL;
    private static final int COST = 1;
    private static final int MAGIC = 3;
    private static final int UPGRADE_MAGIC = 1;
    private static final int SECOND_MAGIC = 2;

    public TimeToStrike() {
        super(ID, COST, TYPE, RARITY, TARGET);
        baseMagicNumber = magicNumber = MAGIC;
        baseSecondMagic = secondMagic = SECOND_MAGIC;
    }

    @Override
    public void useBasic(AbstractPlayer p, AbstractMonster m) {
        playerApplyPower(p, new StrengthPower(p, magicNumber));
        playerApplyPower(p, new LoseStrengthPower(p, secondMagic));
        addToBot(new LightningStanceAction());
    }

    @Override
    public void useLightning(AbstractPlayer p, AbstractMonster m) {
        playerApplyPower(p, new StrengthPower(p, magicNumber));
        playerApplyPower(p, new LoseStrengthPower(p, secondMagic));
        playerApplyPower(p, new LightningChargePower(p, magicNumber));
    }

    @Override
    public void onUpgrade() {
        upgradeMagicNumber(UPGRADE_MAGIC);
    }
}