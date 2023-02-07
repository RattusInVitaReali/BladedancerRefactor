package code.powers;

import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.utility.SFXAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.vfx.combat.CleaveEffect;

import static code.Blademaster.makeID;
import static code.util.BlademasterUtil.getAliveMonsters;

public class BloodbathPower extends AbstractBlademasterPower {

    public static final String POWER_ID = makeID("Bloodbath");
    public static final PowerType TYPE = PowerType.BUFF;
    public static final boolean TURN_BASED = false;

    public BloodbathPower(AbstractCreature owner, int amount) {
        super(POWER_ID, TYPE, TURN_BASED, owner, amount);
    }

    @Override
    public void onPlayCard(AbstractCard card, AbstractMonster m) {
        if (card.type == AbstractCard.CardType.SKILL) {
            this.flash();
            this.addToBot(new SFXAction("ATTACK_HEAVY"));
            if (Settings.FAST_MODE) {
                this.addToBot(new VFXAction(new CleaveEffect()));
            } else {
                this.addToBot(new VFXAction(this.owner, new CleaveEffect(), 0.2F));
            }
            for (AbstractMonster monster : getAliveMonsters()) {
                addToBot(new ApplyPowerAction(monster, owner, new BleedingPower(monster, amount)));
            }
        }
    }

    @Override
    public void updateDescription() {
        description = powerStrings.DESCRIPTIONS[0] + amount + powerStrings.DESCRIPTIONS[1];
    }

}
