package code.cards;

import code.cards.AbstractBlademasterCard;

import code.powers.BleedingPower;
import code.powers.RelentlessPower;
import code.util.BlademasterUtil;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static code.Blademaster.makeID;
import static code.util.BlademasterUtil.playerApplyPower;

public class Carve extends AbstractBlademasterCard {

    public final static String ID = makeID("Carve");
    private static final CardRarity RARITY = CardRarity.SPECIAL;
    private static final CardTarget TARGET = CardTarget.ENEMY;
    private static final CardType TYPE = CardType.ATTACK;
    private static final int COST = 1;
    private static final int DAMAGE = 6;
    private static final int UPGRADE_DAMAGE = 3;
    private static final int MAGIC = 6;
    private static final int UPGRADE_MAGIC = 3;

    public Carve() {
        super(ID, COST, TYPE, RARITY, TARGET, CardColor.COLORLESS, 0, 0);
        baseDamage = DAMAGE;
        baseMagicNumber = magicNumber = MAGIC;
        this.exhaust = true;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        damageMonster(m, damage, AbstractGameAction.AttackEffect.SLASH_DIAGONAL);
        playerApplyPower(m, new BleedingPower(m, magicNumber));
    }

    @Override
    public void onUpgrade() {
        upgradeDamage(UPGRADE_DAMAGE);
        upgradeMagicNumber(UPGRADE_MAGIC);
    }
}