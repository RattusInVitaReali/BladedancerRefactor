package code.actions;

import code.powers.BleedingPower;
import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.utility.WaitAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.rooms.AbstractRoom;

import static code.Blademaster.makeID;

public class BleedingLoseHpAction extends AbstractGameAction {

    private static final float DURATION = 0.33f;

    public BleedingLoseHpAction(AbstractCreature target, int amount, AbstractGameAction.AttackEffect effect) {
        setValues(target, AbstractDungeon.player, amount);
        this.actionType = ActionType.DAMAGE;
        this.attackEffect = effect;
        this.duration = DURATION;
    }

    @Override
    public void update() {
        if (AbstractDungeon.getCurrRoom().phase != AbstractRoom.RoomPhase.COMBAT) {
            isDone = true;
            return;
        }
        tickDuration();
        if (isDone) {
            if (this.target.currentHealth > 0) {
                this.target.tint.color = Color.SCARLET.cpy();
                this.target.tint.changeColor(Color.WHITE.cpy());
                this.target.damage(new DamageInfo(this.source, this.amount, DamageInfo.DamageType.HP_LOSS));
            }
            AbstractPower p = this.target.getPower(makeID("Bleeding"));
            if (p != null) {
                ((BleedingPower) p).decrementAmount();
            }
            if (AbstractDungeon.getCurrRoom().monsters.areMonstersBasicallyDead()) {
                AbstractDungeon.actionManager.clearPostCombatActions();
            }
            AbstractDungeon.actionManager.addToTop(new WaitAction(0.1F));
        }
    }
}
