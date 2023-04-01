package code.deprecated;

import code.cards.AbstractStanceCard;
import code.powers.DedicationPower;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static code.Blademaster.makeID;
import static code.util.BlademasterUtil.playerApplyPower;

public class Dedication extends AbstractStanceCard {

    public final static String ID = makeID("Dedication");
    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.POWER;
    private static final int COST = 1;
    private static final int MAGIC = 1;

    public Dedication() {
        super(ID, COST, TYPE, RARITY, TARGET);
        baseMagicNumber = magicNumber = MAGIC;
        setDescription(cardStrings.DESCRIPTION);
    }

    @Override
    public void useBasic(AbstractPlayer p, AbstractMonster m) {
        playerApplyPower(p, new DedicationPower(p, magicNumber));
    }

    @Override
    public void onUpgrade() {
        selfRetain = true;
    }
}