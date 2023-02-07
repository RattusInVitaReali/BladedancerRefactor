package code.potions;

import basemod.abstracts.CustomPotion;
import code.Blademaster;
import code.powers.BleedingPower;
import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.helpers.PowerTip;
import com.megacrit.cardcrawl.localization.PotionStrings;
import com.megacrit.cardcrawl.potions.AbstractPotion;

import static code.util.BlademasterUtil.playerApplyPower;

public class BleedingPotion extends CustomPotion {

    public static final String POTION_ID = Blademaster.makeID("BleedingPotion");
    public static final Color LIQUID_COLOR = new Color(155.F / 255.F, 0.F / 255.F, 0.F / 255.F, 1);
    public static final Color HYBRID_COLOR = new Color(200.F / 255.F, 0.F / 255.F, 0.F / 255.F, 1);
    public static final Color SPOTS_COLOR = new Color(238.F / 255.F, 238.F / 255.F, 238.F / 255.F, 1);
    private static final PotionStrings potionStrings = CardCrawlGame.languagePack.getPotionString(POTION_ID);
    private static final String NAME = potionStrings.NAME;
    private static final String[] DESCRIPTIONS = potionStrings.DESCRIPTIONS;
    private static final PotionRarity RARITY = PotionRarity.COMMON;
    private static final PotionSize SIZE = PotionSize.H;
    private static final int POTENCY = 9;

    public BleedingPotion() {
        super(NAME, POTION_ID, RARITY, SIZE, PotionColor.NONE);
        isThrown = true;
        targetRequired = true;
        labOutlineColor = Blademaster.blademasterColor.cpy();
        tips.clear();
        tips.add(new PowerTip(name, description));
    }

    @Override
    public void initializeData() {
        potency = getPotency();
        description = potionStrings.DESCRIPTIONS[0] + potency + potionStrings.DESCRIPTIONS[1];
    }


    @Override
    public void use(AbstractCreature target) {
        playerApplyPower(target, new BleedingPower(target, potency));
    }

    @Override
    public int getPotency(int ascension) {
        return POTENCY;
    }

    @Override
    public AbstractPotion makeCopy() {
        return new BleedingPotion();
    }
}
