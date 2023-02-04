package code.potions;

import basemod.abstracts.CustomPotion;
import code.Blademaster;
import code.powers.BleedingPower;
import code.powers.ComboPower;
import code.powers.FuryPower;
import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.PowerTip;
import com.megacrit.cardcrawl.localization.PotionStrings;
import com.megacrit.cardcrawl.potions.AbstractPotion;

import static code.util.BlademasterUtil.playerApplyPower;

public class FinisherPotion extends CustomPotion {

    public static final String POTION_ID = Blademaster.makeID("FinisherPotion");
    private static final PotionStrings potionStrings = CardCrawlGame.languagePack.getPotionString(POTION_ID);
    private static final String NAME = potionStrings.NAME;
    private static final String[] DESCRIPTIONS = potionStrings.DESCRIPTIONS;
    private static final PotionRarity RARITY = PotionRarity.RARE;
    private static final PotionSize SIZE = PotionSize.S;
    private static final int POTENCY = 4;
    private static final int FURY_POTENCY_MULTIPLIER = 5;

    public static final Color LIQUID_COLOR = new Color(227.F / 255.F, 95.F / 255.F, 0.F / 255.F, 1);
    public static final Color HYBRID_COLOR = new Color(135.F / 255.F, 57.F / 255.F, 0.F / 255.F, 1);
    public static final Color SPOTS_COLOR = new Color(238.F / 255.F, 238.F / 255.F, 238.F / 255.F, 1);

    public FinisherPotion() {
        super(NAME, POTION_ID, RARITY, SIZE, PotionColor.NONE);
        isThrown = false;
        targetRequired = false;
        labOutlineColor = Blademaster.blademasterColor.cpy();
        tips.clear();
        tips.add(new PowerTip(name, description));
    }

    @Override
    public void initializeData() {
        potency = getPotency();
        description = potionStrings.DESCRIPTIONS[0] + potency + potionStrings.DESCRIPTIONS[1] + potency * FURY_POTENCY_MULTIPLIER + potionStrings.DESCRIPTIONS[2];
    }


    @Override
    public void use(AbstractCreature target) {
        playerApplyPower(AbstractDungeon.player, new ComboPower(AbstractDungeon.player, potency));
        playerApplyPower(AbstractDungeon.player, new FuryPower(AbstractDungeon.player, potency * FURY_POTENCY_MULTIPLIER));
    }

    @Override
    public int getPotency(int ascension) {
        return POTENCY;
    }

    @Override
    public AbstractPotion makeCopy() {
        return new FinisherPotion();
    }
}
