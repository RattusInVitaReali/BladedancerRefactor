package code.cards;

import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.ArtifactPower;
import com.megacrit.cardcrawl.powers.FrailPower;
import com.megacrit.cardcrawl.powers.VulnerablePower;
import com.megacrit.cardcrawl.powers.WeakPower;

import static code.Blademaster.makeID;
import static code.util.BlademasterUtil.playerApplyPower;

public class Stability extends AbstractBlademasterCard {

    public final static String ID = makeID("Stability");
    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.SKILL;
    private static final int COST = 1;
    private static final int MAGIC = 1;
    private static final int UPGRADE_MAGIC = 1;
    private static final int BLOCK = 6;
    private static final int UPGRADE_BLOCK = 3;

    private static final String[] POWERS_TO_REDUCE = { WeakPower.POWER_ID, VulnerablePower.POWER_ID, FrailPower.POWER_ID };

    public Stability() {
        super(ID, COST, TYPE, RARITY, TARGET);
        baseMagicNumber = magicNumber = MAGIC;
        baseBlock = BLOCK;
        this.exhaust = true;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        block(block);
        playerApplyPower(p, new ArtifactPower(p, magicNumber));
        for (String powerID : POWERS_TO_REDUCE) {
            if (p.hasPower(powerID)) {
                addToBot(new ReducePowerAction(p, p, powerID, magicNumber));
            }
        }
    }

    @Override
    public void onUpgrade() {
        upgradeMagicNumber(UPGRADE_MAGIC);
        upgradeBlock(UPGRADE_BLOCK);
    }
}