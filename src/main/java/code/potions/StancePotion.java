package code.potions;

import basemod.abstracts.CustomPotion;
import code.Blademaster;
import code.cards.choiceCards.ChooseLightning;
import code.cards.choiceCards.ChooseWind;
import code.powers.BleedingPower;
import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.actions.watcher.ChooseOneAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.optionCards.ChooseCalm;
import com.megacrit.cardcrawl.cards.optionCards.ChooseWrath;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.helpers.PowerTip;
import com.megacrit.cardcrawl.helpers.input.InputHelper;
import com.megacrit.cardcrawl.localization.PotionStrings;
import com.megacrit.cardcrawl.potions.AbstractPotion;

import java.util.ArrayList;

import static code.util.BlademasterUtil.playerApplyPower;

public class StancePotion extends CustomPotion {

    public static final String POTION_ID = Blademaster.makeID("StancePotion");
    private static final PotionStrings potionStrings = CardCrawlGame.languagePack.getPotionString(POTION_ID);
    private static final String NAME = potionStrings.NAME;
    private static final String[] DESCRIPTIONS = potionStrings.DESCRIPTIONS;
    private static final PotionRarity RARITY = PotionRarity.UNCOMMON;
    private static final PotionSize SIZE = PotionSize.BOLT;
    private static final int POTENCY = 1;

    public static final Color LIQUID_COLOR = new Color(155.F / 255.F, 190.F / 255.F, 150.F / 255.F, 1);
    public static final Color HYBRID_COLOR = new Color(53.F / 255.F, 115.F / 255.F, 115.F / 255.F, 1);
    public static final Color SPOTS_COLOR = new Color(238.F / 255.F, 238.F / 255.F, 238.F / 255.F, 1);

    public StancePotion() {
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
        description = potionStrings.DESCRIPTIONS[0];
    }


    @Override
    public void use(AbstractCreature target) {
        InputHelper.moveCursorToNeutralPosition();
        ArrayList<AbstractCard> stanceChoices = new ArrayList<>();
        stanceChoices.add(new ChooseWind());
        stanceChoices.add(new ChooseLightning());
        this.addToBot(new ChooseOneAction(stanceChoices));
    }

    @Override
    public int getPotency(int ascension) {
        return POTENCY;
    }

    @Override
    public AbstractPotion makeCopy() {
        return new StancePotion();
    }
}
