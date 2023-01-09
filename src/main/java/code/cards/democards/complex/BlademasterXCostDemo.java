package code.cards.democards.complex;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.StrengthPower;
import code.actions.EasyXCostAction;
import code.cards.AbstractBlademasterCard;

import static code.util.Wiz.*;
import static code.Blademaster.makeID;

public class BlademasterXCostDemo extends AbstractBlademasterCard {
    public final static String ID = makeID(BlademasterXCostDemo.class.getSimpleName());
    // intellij stuff attack, enemy, rare, , , , , 0, 1

    public BlademasterXCostDemo() {
        super(ID, -1, CardType.ATTACK, CardRarity.RARE, CardTarget.ENEMY);
        baseMagicNumber = magicNumber = 0;
        baseDamage = 5;
        exhaust = true;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        atb(new EasyXCostAction(this, (effect, params) -> {
            for (int i = 0; i < effect + params[0]; i++) {
                damageActionTop(m, AbstractGameAction.AttackEffect.SLASH_DIAGONAL);
            }
            applyToSelfTop(new StrengthPower(p, effect + params[0]));
            return true;
        }, magicNumber));
    }

    public void onUpgrade() {
        upgradeMagicNumber(1);
        setUpgradeDescription();
    }
}