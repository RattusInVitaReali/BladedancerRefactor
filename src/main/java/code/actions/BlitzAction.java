//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package code.actions;

import code.cards.Blitz;
import code.powers.BleedingPower;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class BlitzAction extends AbstractGameAction {
    private final Blitz card;
    private final AbstractGameAction.AttackEffect effect;

    public BlitzAction(Blitz card, AbstractGameAction.AttackEffect effect) {
        this.card = card;
        this.effect = effect;
    }

    public BlitzAction(Blitz card) {
        this(card, AttackEffect.NONE);
    }

    @Override
    public void update() {
        target = AbstractDungeon.getMonsters().getRandomMonster(null, true, AbstractDungeon.cardRandomRng);
        if (target == null) return;
        card.calculateCardDamage((AbstractMonster) target);
        addToTop(new ApplyPowerAction(target, AbstractDungeon.player, new BleedingPower(target, card.magicNumber)));
        addToTop(new DamageAction(target, new DamageInfo(AbstractDungeon.player, card.damage, card.damageTypeForTurn), effect));
        isDone = true;
    }
}
