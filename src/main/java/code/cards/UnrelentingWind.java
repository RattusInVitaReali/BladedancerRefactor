package code.cards;

import code.actions.WindStanceAction;
import code.powers.BleedingPower;
import code.powers.stances.WindChargePower;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static code.Blademaster.makeID;
import static code.util.BlademasterUtil.getAliveMonsters;
import static code.util.BlademasterUtil.playerApplyPower;

public class UnrelentingWind extends AbstractBlademasterCard {

    public final static String ID = makeID("UnrelentingWind");
    private static final CardRarity RARITY = CardRarity.RARE;
    private static final CardTarget TARGET = CardTarget.ALL_ENEMY;
    private static final CardType TYPE = CardType.SKILL;
    private static final int COST = 0;
    private static final int COMBO_REQ = 5;
    private static final int BLOCK = 4;
    private static final int UPGRADE_BLOCK = 2;
    private static final int MAGIC = 5;
    private static final int UPGRADE_MAGIC = 2;
    private static final int SECOND_MAGIC = 3;
    private static final int UPGRADE_SECOND_MAGIC = 1;

    public UnrelentingWind() {
        super(ID, COST, TYPE, RARITY, TARGET, 0, COMBO_REQ);
        baseBlock = BLOCK;
        baseMagicNumber = magicNumber = MAGIC;
        baseSecondMagic = secondMagic = SECOND_MAGIC;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new WindStanceAction());
        for (AbstractMonster monster : getAliveMonsters()) {
            block(block);
            playerApplyPower(monster, new BleedingPower(monster, magicNumber));
            playerApplyPower(p, new WindChargePower(p, secondMagic));
        }
    }

    @Override
    public void onUpgrade() {
        upgradeBlock(UPGRADE_BLOCK);
        upgradeMagicNumber(UPGRADE_MAGIC);
        upgradeSecondMagic(UPGRADE_SECOND_MAGIC);
    }
}