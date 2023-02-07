package code.actions;

import code.powers.BleedingPower;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.utility.WaitAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.vfx.combat.FlashAtkImgEffect;

import static code.util.BlademasterUtil.playerApplyPower;

public class BlitzAction extends AbstractGameAction {
    private static final float DURATION = 0.01F;
    private static final float POST_ATTACK_WAIT_DUR = 0.2F;
    private final DamageInfo info;
    private int numTimes;
    private final int bleedingStacks;

    public BlitzAction(AbstractCreature target, DamageInfo info, int numTimes, int bleedingStacks) {
        this.info = info;
        this.target = target;
        this.actionType = ActionType.DAMAGE;
        this.attackEffect = AttackEffect.SLASH_HORIZONTAL;
        this.duration = 0.01F;
        this.numTimes = numTimes;
        this.bleedingStacks = bleedingStacks;
    }

    public BlitzAction(DamageInfo info, int numTimes, int bleedingStacks) {
        this.info = info;
        this.actionType = ActionType.DAMAGE;
        this.attackEffect = AttackEffect.SLASH_HORIZONTAL;
        this.duration = 0.01F;
        this.numTimes = numTimes;
        this.bleedingStacks = bleedingStacks;
        if (!AbstractDungeon.getMonsters().areMonstersBasicallyDead()) {
            this.addToTop(new BlitzAction(AbstractDungeon.getMonsters().getRandomMonster(null, true, AbstractDungeon.cardRandomRng), info, numTimes, bleedingStacks));
        }

    }

    @Override
    public void update() {
        if (this.target == null) {
            this.isDone = true;
        } else if (AbstractDungeon.getCurrRoom().monsters.areMonstersBasicallyDead()) {
            AbstractDungeon.actionManager.clearPostCombatActions();
            this.isDone = true;
        } else {
            if (this.target.currentHealth > 0) {
                AbstractDungeon.effectList.add(new FlashAtkImgEffect(this.target.hb.cX, this.target.hb.cY, this.attackEffect));
                this.info.applyPowers(this.info.owner, this.target);
                this.target.damage(this.info);
                playerApplyPower(target, new BleedingPower(target, bleedingStacks));
                if (this.numTimes > 1 && !AbstractDungeon.getMonsters().areMonstersBasicallyDead()) {
                    --this.numTimes;
                    this.addToTop(new BlitzAction(AbstractDungeon.getMonsters().getRandomMonster(null, true, AbstractDungeon.cardRandomRng), this.info, this.numTimes, this.bleedingStacks));
                }

                this.addToTop(new WaitAction(0.2F));
            } else {
                this.addToTop(new BlitzAction(AbstractDungeon.getMonsters().getRandomMonster(null, true, AbstractDungeon.cardRandomRng), this.info, this.numTimes, this.bleedingStacks));
            }

            this.isDone = true;
        }
    }
}
