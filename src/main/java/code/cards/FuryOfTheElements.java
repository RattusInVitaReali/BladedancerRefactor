package code.cards;

import code.powers.stances.LightningChargePower;
import code.powers.stances.WindChargePower;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static code.Blademaster.makeID;
import static code.util.BlademasterUtil.*;

public class FuryOfTheElements extends AbstractBlademasterCard {

    public final static String ID = makeID("FuryOfTheElements");
    private static final CardRarity RARITY = CardRarity.RARE;
    private static final CardTarget TARGET = CardTarget.ALL_ENEMY;
    private static final CardType TYPE = CardType.ATTACK;
    private static final int COST = 0;
    private static final int FURY_REQ = 25;
    private static final int UPGRADE_FURY_REQ = 20;

    public FuryOfTheElements() {
        super(ID, COST, TYPE, RARITY, TARGET, FURY_REQ, 0);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        consumeFinisherCost();
        for (AbstractMonster monster : AbstractDungeon.getMonsters().monsters) {
            lightningEffect(monster);
        }
        damageAllMonsters(DamageInfo.createDamageMatrix(2 * (getPlayerWindCharges() + getPlayerLightningCharges())), AbstractGameAction.AttackEffect.SLASH_HEAVY);
        addToBot(new RemoveSpecificPowerAction(p, p, WindChargePower.POWER_ID));
        addToBot(new RemoveSpecificPowerAction(p, p, LightningChargePower.POWER_ID));
    }

    @Override
    public void onUpgrade() {
    }
}