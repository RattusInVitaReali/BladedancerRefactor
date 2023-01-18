package code.cards;

import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static code.Blademaster.makeID;
import static code.util.BlademasterUtil.getPlayerLightningCharges;
import static code.util.BlademasterUtil.getPlayerWindCharges;

public class HighVoltage extends AbstractBlademasterCard {

    public final static String ID = makeID("HighVoltage");
    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.SKILL;
    private static final int COST = 0;


    public HighVoltage() {
        super(ID, COST, TYPE, RARITY, TARGET);
        this.exhaust = true;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        block(getPlayerWindCharges() + getPlayerLightningCharges());
    }

    @Override
    public void onUpgrade() {
        this.retain = true;
    }
}