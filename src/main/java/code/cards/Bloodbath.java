package code.cards;

import code.cards.AbstractBlademasterCard;

import code.powers.BloodbathPower;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static code.Blademaster.makeID;
import static code.util.BlademasterUtil.playerApplyPower;

public class Bloodbath extends AbstractBlademasterCard {

    public final static String ID = makeID("Bloodbath");
    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.POWER;
    private static final int COST = 1;
    private static final int MAGIC = 1;
    private static final int UPGRADE_MAGIC = 1;

    public Bloodbath() {
        super(ID, COST, TYPE, RARITY, TARGET);
        baseMagicNumber = magicNumber = MAGIC;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        playerApplyPower(p, new BloodbathPower(p, magicNumber));
    }

    @Override
    public void onUpgrade() {
        upgradeMagicNumber(UPGRADE_MAGIC);
    }
}