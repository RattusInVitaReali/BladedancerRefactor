package code.powers;

import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.PowerStrings;

import static code.Blademaster.makeID;

public class BleedingPower extends AbstractBlademasterPower {

    public static final String POWER_ID = makeID("Bleeding");
    public static final String NAME;
    public static final PowerType TYPE = PowerType.DEBUFF;
    public static final boolean TURN_BASED = true;


    private static final PowerStrings powerStrings;

    static {
        powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
        NAME = powerStrings.NAME;
        DESCRIPTIONS = powerStrings.DESCRIPTIONS;
    }

    public BleedingPower(AbstractCreature owner, int amount) {
        super(POWER_ID, NAME, TYPE, TURN_BASED, owner, amount);
    }

}
