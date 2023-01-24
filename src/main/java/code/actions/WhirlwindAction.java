package code.actions;

import code.cards.AbstractBlademasterCard;
import code.powers.MassacrePower;
import code.powers.stances.AbstractStancePower;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.AbstractGameAction.ActionType;
import com.megacrit.cardcrawl.actions.AbstractGameAction.AttackEffect;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction;
import com.megacrit.cardcrawl.actions.utility.SFXAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.ui.panels.EnergyPanel;
import com.megacrit.cardcrawl.vfx.combat.CleaveEffect;
import com.megacrit.cardcrawl.vfx.combat.WhirlwindEffect;

import static code.util.BlademasterUtil.*;

public class WhirlwindAction extends AbstractGameAction {
    public int[] multiDamage;
    private boolean freeToPlayOnce = false;
    private DamageInfo.DamageType damageType;
    private AbstractPlayer p;
    private int energyOnUse = -1;
    private int charges = 0;

    public WhirlwindAction(AbstractPlayer p, int[] multiDamage, DamageInfo.DamageType damageType, boolean freeToPlayOnce, int energyOnUse, int charges) {
        this.multiDamage = multiDamage;
        this.damageType = damageType;
        this.p = p;
        this.freeToPlayOnce = freeToPlayOnce;
        this.duration = Settings.ACTION_DUR_XFAST;
        this.actionType = ActionType.SPECIAL;
        this.energyOnUse = energyOnUse;
        this.charges = charges;
    }

    @Override
    public void update() {
        int effect = EnergyPanel.totalCount;
        if (this.energyOnUse != -1) {
            effect = this.energyOnUse;
        }

        if (this.p.hasRelic("Chemical X")) {
            effect += 2;
            this.p.getRelic("Chemical X").flash();
        }

        for (AbstractMonster m : getAliveMonsters()) {
            if (AbstractBlademasterCard.isBloodied(m)) {
                ++effect;
                AbstractBlademasterCard.triggerBloodiedPowers(AbstractDungeon.player, m);
                if (p.hasPower(MassacrePower.POWER_ID)) {
                    ++effect;
                    AbstractBlademasterCard.triggerBloodiedPowers(AbstractDungeon.player, m);
                }
            }
        }

        if (effect > 0) {
            for(int i = 0; i < effect; ++i) {
                if (i == 0) {
                    addToBot(new SFXAction("ATTACK_WHIRLWIND"));
                    addToBot(new VFXAction(new WhirlwindEffect(), 0.0F));
                }

                addToBot(new SFXAction("ATTACK_HEAVY"));
                addToBot(new VFXAction(this.p, new CleaveEffect(), 0.0F));
                addToBot(new DamageAllEnemiesAction(this.p, this.multiDamage, this.damageType, AttackEffect.NONE, true));
                AbstractStancePower power = getPlayerStancePower();
                AbstractPower chargesPower = null;
                if (power != null)
                    chargesPower = power.getChargePower(p, charges);
                if (chargesPower != null)
                    playerApplyPower(p, getPlayerStancePower().getChargePower(p, this.charges));
            }

            if (!this.freeToPlayOnce) {
                this.p.energy.use(EnergyPanel.totalCount);
            }
        }

        this.isDone = true;
    }
}
