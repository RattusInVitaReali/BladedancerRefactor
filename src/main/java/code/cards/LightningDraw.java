package code.cards;

import code.cards.AbstractBlademasterCard;

import code.powers.BleedingPower;
import code.util.BlademasterUtil;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.utility.SFXAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.vfx.combat.LightningEffect;

import static code.Blademaster.makeID;

public class LightningDraw extends AbstractBlademasterCard {

    public final static String ID = makeID("LightningDraw");
    private static final CardRarity RARITY = CardRarity.RARE;
    private static final CardTarget TARGET = CardTarget.ALL_ENEMY;
    private static final CardType TYPE = CardType.ATTACK;
    private static final int COST = 0;
    private static final int DAMAGE = 10;
    private static final int UPGRADE_DAMAGE = 4;
    private static final int MAGIC = 5;
    private static final int UPGRADE_MAGIC = 3;
    private static final int FURY_REQ = 20;

    public LightningDraw() {
        super(ID, COST, TYPE, RARITY, TARGET);
        baseDamage = DAMAGE;
        baseMagicNumber = magicNumber = MAGIC;
    }

    @Override
    public int furyReq() {
        return FURY_REQ;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        consumeFinisherCost();
        for (AbstractMonster monster : AbstractDungeon.getMonsters().monsters) {
            BlademasterUtil.lightningEffect(monster);
            damageMonster(monster, damage, AbstractGameAction.AttackEffect.NONE);
            BlademasterUtil.playerApplyPower(monster, new BleedingPower(monster, magicNumber));
        }
    }

    @Override
    public void onUpgrade() {
        upgradeDamage(UPGRADE_DAMAGE);
        upgradeMagicNumber(UPGRADE_MAGIC);
    }
}