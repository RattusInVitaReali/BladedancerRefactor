package code.cards;

import code.cards.AbstractBlademasterCard;

import code.powers.BladeFormPower;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static code.Blademaster.makeID;
import static code.util.BlademasterUtil.playerApplyPower;

public class BladeForm extends AbstractBlademasterCard {

    public final static String ID = makeID("BladeForm");
    private static final CardRarity RARITY = CardRarity.RARE;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.POWER;
    private static final int COST = 2;
    private static final int FURY_REQ = 25;
    private static final int UPGRADE_FURY_REQ = 20;
    private static final int COMBO_REQ = 5;
    private static final int UPGRADE_COMBO_REQ = 4;
    private static final int MAGIC = 1;

    public BladeForm() {
        super(ID, COST, TYPE, RARITY, TARGET, FURY_REQ, COMBO_REQ);
        baseMagicNumber = magicNumber = MAGIC;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        consumeFinisherCost();
        playerApplyPower(p, new BladeFormPower(p, magicNumber));
    }

    @Override
    public void onUpgrade() {
        upgradeFuryCost(UPGRADE_FURY_REQ);
        upgradeComboCost(UPGRADE_COMBO_REQ);
    }
}