package code.cards;

import code.cards.AbstractBlademasterCard;

import code.patches.BlademasterTags;
import code.powers.MassacrePower;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.vfx.combat.ClashEffect;

import static code.Blademaster.makeID;
import static code.util.BlademasterUtil.getPowerAmount;

public class RagingBlow extends AbstractBlademasterCard {

    public final static String ID = makeID("RagingBlow");
    private static final CardRarity RARITY = CardRarity.BASIC;
    private static final CardTarget TARGET = CardTarget.ENEMY;
    private static final CardType TYPE = CardType.ATTACK;
    private static final int COST = 0;
    private static final int FURY_REQ = 10;
    private static final int DAMAGE = 8;
    private static final int UPGRADE_DAMAGE = 3;

    public RagingBlow() {
        super(ID, COST, TYPE, RARITY, TARGET, FURY_REQ, 0);
        baseDamage = DAMAGE;
        tags.add(BlademasterTags.BLOODIED);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        consumeFinisherCost();
        addToBot(new VFXAction(new ClashEffect(m.hb.cX, m.hb.cY), 0.1F));
        damageMonster(m, damage, AbstractGameAction.AttackEffect.NONE);
        useBloodiedWrapper(p, m);
    }

    @Override
    public void calculateCardDamage(AbstractMonster m) {
        super.calculateCardDamage(m);
        if (isBloodied(m)) {
            if (AbstractDungeon.player.hasPower(MassacrePower.POWER_ID))
                damage *= 2 + getPowerAmount(AbstractDungeon.player, MassacrePower.POWER_ID);
            else
                damage *= 2;
            this.isDamageModified = true;
        }
    }

    @Override
    public void onUpgrade() {
        upgradeDamage(UPGRADE_DAMAGE);
    }
}