package code.cards;

import code.cards.AbstractBlademasterCard;

import code.patches.BlademasterTags;
import code.powers.BleedingPower;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.WeakPower;

import java.util.WeakHashMap;

import static code.Blademaster.makeID;
import static code.util.BlademasterUtil.playerApplyPower;

public class Anklebiter extends AbstractBlademasterCard {

    public final static String ID = makeID("Anklebiter");
    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.ENEMY;
    private static final CardType TYPE = CardType.SKILL;
    private static final int COST = 1;
    private static final int BLOCK = 5;
    private static final int UPGRADE_BLOCK = 3;
    private static final int MAGIC = 1;
    private static final int UPGRADE_MAGIC = 1;
    private static final int SECOND_MAGIC = 5;
    private static final int UPGRADE_SECOND_MAGIC = 2;

    public Anklebiter() {
        super(ID, COST, TYPE, RARITY, TARGET);
        baseBlock = BLOCK;
        baseMagicNumber = magicNumber = MAGIC;
        baseSecondMagic = secondMagic = SECOND_MAGIC;
        tags.add(BlademasterTags.BLOODIED);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        playerApplyPower(m, new WeakPower(m, magicNumber, false));
        playerApplyPower(m, new BleedingPower(m, secondMagic));
        useBloodiedWrapper(p, m);
    }

    @Override
    public void useBloodied(AbstractPlayer p, AbstractMonster m) {
        block(block);
    }

    @Override
    public void onUpgrade() {
        upgradeBlock(UPGRADE_BLOCK);
        upgradeMagicNumber(UPGRADE_MAGIC);
        upgradeSecondMagic(UPGRADE_SECOND_MAGIC);
    }
}