package code.characters;

import basemod.abstracts.CustomEnergyOrb;
import basemod.abstracts.CustomPlayer;
import code.actions.BasicStanceAction;
import code.cards.Defend;
import code.cards.Strike;
import code.relics.TodoItem;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.math.MathUtils;
import com.evacipated.cardcrawl.modthespire.lib.SpireEnum;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.EnergyManager;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.CardLibrary;
import com.megacrit.cardcrawl.helpers.FontHelper;
import com.megacrit.cardcrawl.helpers.ScreenShake;
import com.megacrit.cardcrawl.localization.CharacterStrings;
import com.megacrit.cardcrawl.screens.CharSelectInfo;

import java.util.ArrayList;

import static code.Blademaster.*;
import static code.characters.BlademasterCharacter.Enums.BLADEMASTER_COLOR;

public class BlademasterCharacter extends CustomPlayer {

    public static final String ID = makeID("ModdedCharacter");
    public static final CharacterStrings characterStrings = CardCrawlGame.languagePack.getCharacterString(ID);
    public static final String[] NAMES = characterStrings.NAMES;
    public static final String[] TEXT = characterStrings.TEXT;
    private static final String[] orbTextures = {
            modID + "Resources/images/characters/blademaster/orb/layer1.png",
            modID + "Resources/images/characters/blademaster/orb/layer2.png",
            modID + "Resources/images/characters/blademaster/orb/layer3.png",
            modID + "Resources/images/characters/blademaster/orb/layer4.png",
            modID + "Resources/images/characters/blademaster/orb/layer5.png",
            modID + "Resources/images/characters/blademaster/orb/layer6.png",
            modID + "Resources/images/characters/blademaster/orb/layer1d.png",
            modID + "Resources/images/characters/blademaster/orb/layer2d.png",
            modID + "Resources/images/characters/blademaster/orb/layer3d.png",
            modID + "Resources/images/characters/blademaster/orb/layer4d.png",
            modID + "Resources/images/characters/blademaster/orb/layer5d.png",};

    public BlademasterCharacter(String name, PlayerClass setClass) {
        super(name, setClass, new CustomEnergyOrb(orbTextures, modID + "Resources/images/characters/blademaster/orb/vfx.png", null), null, null);
        initializeClass(null,
                SHOULDER1,
                SHOULDER2,
                CORPSE,
                getLoadout(), 20.0F, -10.0F, 166.0F, 327.0F, new EnergyManager(3));

        dialogX = (drawX + 0.0F * Settings.scale);
        dialogY = (drawY + 240.0F * Settings.scale);

        loadAnimation(BLADEMASTER_SKELETON_ATLAS, BLADEMASTER_SKELETON_JSON, 0.95f);
        if (MathUtils.random(100) == 69) {
            state.setAnimation(0, "Thonk", true);
        } else {
            state.setAnimation(0, "Idle", true);
        }
    }

    @Override
    public CharSelectInfo getLoadout() {
        return new CharSelectInfo(NAMES[0], TEXT[0],
                80, 80, 0, 99, 5, this, getStartingRelics(),
                getStartingDeck(), false);
    }

    @Override
    public ArrayList<String> getStartingDeck() {
        ArrayList<String> retVal = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            retVal.add(Strike.ID);
        }
        for (int i = 0; i < 4; i++) {
            retVal.add(Defend.ID);
        }
        return retVal;
    }

    @Override
    public ArrayList<String> getStartingRelics() {
        ArrayList<String> retVal = new ArrayList<>();
        retVal.add(TodoItem.ID);
        return retVal;
    }

    @Override
    public void doCharSelectScreenSelectEffect() {
        CardCrawlGame.sound.playA("UNLOCK_PING", MathUtils.random(-0.2F, 0.2F));
        CardCrawlGame.screenShake.shake(ScreenShake.ShakeIntensity.LOW, ScreenShake.ShakeDur.SHORT,
                false);
    }

    @Override
    public String getCustomModeCharacterButtonSoundKey() {
        return "UNLOCK_PING";
    }

    @Override
    public int getAscensionMaxHPLoss() {
        return 8;
    }

    @Override
    public AbstractCard.CardColor getCardColor() {
        return BLADEMASTER_COLOR;
    }

    @Override
    public Color getCardTrailColor() {
        return blademasterColor.cpy();
    }

    @Override
    public BitmapFont getEnergyNumFont() {
        return FontHelper.energyNumFontRed;
    }

    @Override
    public String getLocalizedCharacterName() {
        return NAMES[0];
    }

    @Override
    public AbstractCard getStartCardForEvent() {
        System.out.println("YOU NEED TO SET getStartCardForEvent() in your " + getClass().getSimpleName() + " file!");
        return null;
    }

    @Override
    public String getTitle(AbstractPlayer.PlayerClass playerClass) {
        return NAMES[1];
    }

    @Override
    public AbstractPlayer newInstance() {
        return new BlademasterCharacter(name, chosenClass);
    }

    @Override
    public Color getCardRenderColor() {
        return blademasterColor.cpy();
    }

    @Override
    public Color getSlashAttackColor() {
        return blademasterColor.cpy();
    }

    @Override
    public AbstractGameAction.AttackEffect[] getSpireHeartSlashEffect() {
        return new AbstractGameAction.AttackEffect[]{
                AbstractGameAction.AttackEffect.FIRE,
                AbstractGameAction.AttackEffect.BLUNT_HEAVY,
                AbstractGameAction.AttackEffect.FIRE};
    }

    @Override
    public String getSpireHeartText() {
        return TEXT[1];
    }

    @Override
    public String getVampireText() {
        return TEXT[2];
    }

    @Override
    public void preBattlePrep() {
        super.preBattlePrep();
        AbstractDungeon.actionManager.addToBottom(new BasicStanceAction());
    }

    public static class Enums {
        @SpireEnum
        public static AbstractPlayer.PlayerClass THE_BLADEMASTER;
        @SpireEnum(name = "BLADEMASTER_COLOR")
        public static AbstractCard.CardColor BLADEMASTER_COLOR;
        @SpireEnum(name = "BLADEMASTER_COLOR")
        @SuppressWarnings("unused")
        public static CardLibrary.LibraryType LIBRARY_COLOR;
    }
}
