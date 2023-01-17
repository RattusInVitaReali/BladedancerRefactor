package code.cards;

import code.cards.AbstractBlademasterCard;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static code.Blademaster.makeID;

public class ThreePointStrike extends AbstractBlademasterCard {

    public final static String ID = makeID("ThreePointStrike");
    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.ENEMY;
    private static final CardType TYPE = CardType.ATTACK;
    private static final int COST = 0;
    private static final int COMBO_REQ = 4;
    private static final int DAMAGE = 3;
    private static final int UPGRADE_DAMAGE = 2;
    private static final int BLOCK = 4;
    private static final int UPGRADE_BLOCK = 2;
    private static final int MAGIC = 1;
    private static final int UPGRADE_MAGIC = 1;

    public ThreePointStrike() {
        super(ID, COST, TYPE, RARITY, TARGET);
        baseDamage = DAMAGE;
        baseBlock = BLOCK;
        baseMagicNumber = magicNumber = MAGIC;
    }

    @Override
    public int comboReq() {
        return COMBO_REQ;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        consumeFinisherCost();
        damageMonster(m, damage, AbstractGameAction.AttackEffect.SLASH_VERTICAL);
        damageMonster(m, damage, AbstractGameAction.AttackEffect.SLASH_HORIZONTAL);
        damageMonster(m, damage, AbstractGameAction.AttackEffect.SLASH_DIAGONAL);
        block(block);
        addToBot(new DrawCardAction(p, magicNumber));
    }

    @Override
    public void onUpgrade() {
        upgradeDamage(UPGRADE_DAMAGE);
        upgradeBlock(UPGRADE_BLOCK);
        upgradeMagicNumber(UPGRADE_MAGIC);
        setUpgradeDescription();
    }
}