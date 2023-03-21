package code.cards;

import com.megacrit.cardcrawl.actions.common.BetterDiscardPileToHandAction;
import com.megacrit.cardcrawl.actions.common.BetterDrawPileToHandAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static code.Blademaster.makeID;

public class LightningSheath extends AbstractStanceCard {

    public final static String ID = makeID("LightningSheath");
    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.SKILL;
    private static final int COST = 1;
    private static final int BLOCK = 3;
    private static final int UPGRADE_BLOCK = 2;
    private static final int MAGIC = 1;

    public LightningSheath() {
        super(ID, COST, TYPE, RARITY, TARGET);
        baseBlock = BLOCK;
        baseMagicNumber = magicNumber = MAGIC;
        exhaust = true;
    }

    @Override
    public void useBasic(AbstractPlayer p, AbstractMonster m) {
        block(block);
        addToBot(new BetterDiscardPileToHandAction(magicNumber));
    }

    @Override
    public void useLightning(AbstractPlayer p, AbstractMonster m) {
        useBasic(p, m);
        addToBot(new BetterDrawPileToHandAction(magicNumber));
    }

    @Override
    public void onUpgrade() {
        upgradeBlock(UPGRADE_BLOCK);
    }
}