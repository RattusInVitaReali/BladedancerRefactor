package code.cards;

import code.patches.BlademasterTags;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static code.Blademaster.makeID;
import static code.util.BlademasterUtil.getAliveMonsters;

public class BladeRuse extends AbstractBlademasterCard {

    public final static String ID = makeID("BladeRuse");
    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.SKILL;
    private static final int COST = 1;
    private static final int BLOCK = 6;
    private static final int UPGRADE_BLOCK = 3;

    public BladeRuse() {
        super(ID, COST, TYPE, RARITY, TARGET);
        baseBlock = BLOCK;
        tags.add(BlademasterTags.BLOODIED);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        block(block);
        for (AbstractMonster monster : getAliveMonsters()) {
            useBloodiedWrapper(p, monster);
        }
    }

    @Override
    public void useBloodied(AbstractPlayer p, AbstractMonster m) {
        block(block);
    }

    @Override
    public void onUpgrade() {
        upgradeBlock(UPGRADE_BLOCK);
    }
}