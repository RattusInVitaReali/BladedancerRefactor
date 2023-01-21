package code.cards;

import code.powers.MassacrePower;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static code.Blademaster.makeID;
import static code.util.BlademasterUtil.playerApplyPower;

public class Massacre extends AbstractBlademasterCard {

    public final static String ID = makeID("Massacre");
    private static final CardRarity RARITY = CardRarity.RARE;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.POWER;
    private static final int COST = 1;
    private static final int UPGRADE_COST = 0;
    private static final int FURY_REQ = 30;
    private static final int UPGRADE_FURY_REQ = 25;

    public Massacre() {
        super(ID, COST, TYPE, RARITY, TARGET, FURY_REQ, 0);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        consumeFinisherCost();
        playerApplyPower(p, new MassacrePower(p));
    }

    @Override
    public void onUpgrade() {
        upgradeBaseCost(UPGRADE_COST);
        upgradeFuryCost(UPGRADE_FURY_REQ);
    }
}