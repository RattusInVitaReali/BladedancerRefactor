package code.cards;

import code.actions.LightningStanceAction;
import code.cards.AbstractBlademasterCard;

import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.stances.AbstractStance;

import static code.Blademaster.makeID;

public class Stormguard extends AbstractStanceCard {

    public final static String ID = makeID("Stormguard");
    private static final CardRarity RARITY = CardRarity.COMMON;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.SKILL;
    private static final int COST = 1;
    private static final int BLOCK = 5;
    private static final int UPGRADE_BLOCK = 3;

    public Stormguard() {
        super(ID, COST, TYPE, RARITY, TARGET);
        baseBlock = BLOCK;
    }

    @Override
    public void useBasic(AbstractPlayer p, AbstractMonster m) {
        block(block);
        addToBot(new LightningStanceAction());
    }

    @Override
    public void useLightning(AbstractPlayer p, AbstractMonster m) {
        block(block);
        block(block);
    }

    @Override
    public void onUpgrade() {
        upgradeBlock(UPGRADE_BLOCK);
    }
}