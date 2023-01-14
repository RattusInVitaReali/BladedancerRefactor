package code.cards;

import code.actions.BleedingLoseHpAction;
import code.cards.AbstractBlademasterCard;

import code.powers.BleedingPower;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static code.Blademaster.makeID;
import static code.util.BlademasterUtil.playerApplyPower;

public class DeadlyDance extends AbstractBlademasterCard {

    public final static String ID = makeID("DeadlyDance");
    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.ALL_ENEMY;
    private static final CardType TYPE = CardType.SKILL;
    private static final int COST = 2;
    private static final int BLOCK = 7;
    private static final int UPGRADE_BLOCK = 4;
    private static final int MAGIC = 7;
    private static final int UPGRADE_MAGIC = 3;

    public DeadlyDance() {
        super(ID, COST, TYPE, RARITY, TARGET);
        baseBlock = BLOCK;
        baseMagicNumber = magicNumber = MAGIC;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        block(block);
        for (AbstractMonster monster : AbstractDungeon.getMonsters().monsters) {
            playerApplyPower(monster, new BleedingPower(monster, magicNumber));
        }
    }

    @Override
    public void onUpgrade() {
        upgradeBlock(UPGRADE_BLOCK);
        upgradeMagicNumber(UPGRADE_MAGIC);
    }
}