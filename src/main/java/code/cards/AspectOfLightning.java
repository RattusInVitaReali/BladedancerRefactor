package code.cards;

import basemod.devcommands.draw.Draw;
import code.actions.LightningStanceAction;
import code.powers.stances.LightningChargePower;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static code.Blademaster.makeID;
import static code.util.BlademasterUtil.playerApplyPower;

public class AspectOfLightning extends AbstractStanceCard {

    public final static String ID = makeID("AspectOfLightning");
    private static final CardRarity RARITY = CardRarity.COMMON;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.SKILL;
    private static final int COST = 1;
    private static final int MAGIC = 3;
    private static final int UPGRADE_MAGIC = 2;
    private static final int SECOND_MAGIC = 2;
    private static final int UPGRADE_SECOND_MAGIC = 1;

    public AspectOfLightning() {
        super(ID, COST, TYPE, RARITY, TARGET);
        baseMagicNumber = magicNumber = MAGIC;
        baseSecondMagic = secondMagic = SECOND_MAGIC;
    }

    @Override
    public void useBasic(AbstractPlayer p, AbstractMonster m) {
        addToBot(new DrawCardAction(secondMagic));
        addToBot(new LightningStanceAction());
    }

    @Override
    public void useLightning(AbstractPlayer p, AbstractMonster m) {
        addToBot(new DrawCardAction(secondMagic));
        playerApplyPower(p, new LightningChargePower(p, magicNumber));
    }

    @Override
    public void onUpgrade() {
        upgradeMagicNumber(UPGRADE_MAGIC);
        upgradeSecondMagic(UPGRADE_SECOND_MAGIC);
    }
}