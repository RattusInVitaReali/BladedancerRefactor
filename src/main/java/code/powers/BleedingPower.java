package code.powers;

import code.actions.BleedingLoseHpAction;
import code.relics.PaperTeigr;
import com.badlogic.gdx.graphics.Color;
import com.evacipated.cardcrawl.mod.stslib.powers.interfaces.HealthBarRenderPower;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.rooms.AbstractRoom;

import static code.Blademaster.makeID;

public class BleedingPower extends AbstractBlademasterPower implements HealthBarRenderPower {

    public static final String POWER_ID = makeID("Bleeding");
    public static final PowerType TYPE = PowerType.DEBUFF;
    public static final boolean TURN_BASED = true;
    private static final Color COLOR = new Color(.4f, 0f, 0f, 1.f);

    public BleedingPower(AbstractCreature owner, int amount) {
        super(POWER_ID, TYPE, TURN_BASED, owner, amount);
    }

    @Override
    public void playApplyPowerSfx() {
        CardCrawlGame.sound.play("POWER_POISON", 0.05F);
    }

    @Override
    public float atDamageReceive(float damage, DamageInfo.DamageType type) {
        if (type == DamageInfo.DamageType.NORMAL) {
            return AbstractDungeon.player.hasRelic(PaperTeigr.ID) ? damage * 1.3F : damage * 1.2F;
        }
        return damage;
    }

    @Override
    public void atStartOfTurn() {
        if (AbstractDungeon.getCurrRoom().phase == AbstractRoom.RoomPhase.COMBAT && !AbstractDungeon.getMonsters().areMonstersBasicallyDead()) {
            this.flashWithoutSound();
            addToBot(new BleedingLoseHpAction(owner, amount, AbstractGameAction.AttackEffect.SLASH_HORIZONTAL));
        }
    }

    @Override
    public void updateDescription() {
        boolean hasRelic = AbstractDungeon.player.hasRelic(PaperTeigr.ID);
        description = powerStrings.DESCRIPTIONS[0] +
                (hasRelic ? 30 : 20) + powerStrings.DESCRIPTIONS[1] +
                amount + powerStrings.DESCRIPTIONS[2] +
                (hasRelic ? 2 : 3) + powerStrings.DESCRIPTIONS[3];
    }

    @Override
    public int getHealthBarAmount() {
        return this.amount;
    }

    @Override
    public Color getColor() {
        return COLOR;
    }

    public void decrementAmount() {
        amount -= AbstractDungeon.player.hasRelic(PaperTeigr.ID) ? 2 : 3;
        if (amount <= 0)
            addToBot(new RemoveSpecificPowerAction(owner, owner, this));
        updateDescription();
    }
}
