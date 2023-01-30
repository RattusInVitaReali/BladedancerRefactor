package code.characters;

import basemod.abstracts.CustomEnergyOrb;
import basemod.abstracts.CustomPlayer;
import code.actions.BasicStanceAction;
import code.cards.Defend;
import code.cards.RagingBlow;
import code.cards.Strike;
import code.powers.ComboPower;
import code.powers.FuryPower;
import code.powers.stances.AbstractStancePower;
import code.powers.stances.LightningChargePower;
import code.powers.stances.WindChargePower;
import code.relics.DancersAmulet;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.evacipated.cardcrawl.modthespire.lib.SpireEnum;
import com.evacipated.cardcrawl.modthespire.lib.SpireOverride;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
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
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.screens.CharSelectInfo;
import com.sun.jna.StringArray;

import java.util.ArrayList;
import java.util.Arrays;

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
            modID + "Resources/images/characters/blademaster/orb/layer5d.png"
    };

    private static final float POWER_ICON_PADDING_X = Settings.isMobile ? (55.0F * Settings.scale) : (48.0F * Settings.scale);
    private static final Color POWER_AMOUNT_COLOR = Color.WHITE.cpy();
    private static final ArrayList<String> SPECIAL_POWERS = new ArrayList<>(Arrays.asList(ComboPower.POWER_ID, FuryPower.POWER_ID, WindChargePower.POWER_ID, LightningChargePower.POWER_ID));

    public BlademasterCharacter(String name, PlayerClass setClass) {
        super(name, setClass, new CustomEnergyOrb(orbTextures, modID + "Resources/images/characters/blademaster/orb/vfx.png", null), null, null);
        initializeClass(null,
                SHOULDER1,
                SHOULDER2,
                CORPSE,
                getLoadout(), 20.0F, -10.0F, 166.0F, 327.0F, new EnergyManager(3));

        dialogX = (drawX + 0.0F * Settings.scale);
        dialogY = (drawY + 240.0F * Settings.scale);

        loadAnimation(BLADEMASTER_SKELETON_ATLAS, BLADEMASTER_SKELETON_JSON, 1f);
        if (MathUtils.random(100) == 69) {
            state.setAnimation(0, "Thonk", true);
        } else {
            state.setAnimation(0, "Idle", true);
        }
    }

    @Override
    public CharSelectInfo getLoadout() {
        return new CharSelectInfo(NAMES[0], TEXT[0],
                75, 75, 0, 99, 5, this, getStartingRelics(),
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
        retVal.add(RagingBlow.ID);
        return retVal;
    }

    @Override
    public ArrayList<String> getStartingRelics() {
        ArrayList<String> retVal = new ArrayList<>();
        retVal.add(DancersAmulet.ID);
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
        return new RagingBlow();
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
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(this, this, new ComboPower(this, 0)));
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(this, this, new FuryPower(this, 0)));
    }

    @SpireOverride
    public void renderPowerIcons(SpriteBatch sb, float x, float y) {
        float offset = 10.0F * Settings.scale;
        float specialOffset = 10.0F * Settings.scale;
        for (AbstractPower p : this.powers) {
            if (p instanceof AbstractStancePower) {
                p.renderIcons(sb, x + (200.0F * Settings.scale), y - 20F * Settings.scale, POWER_AMOUNT_COLOR);
            } else if (SPECIAL_POWERS.contains(p.ID)) {
                if (Settings.isMobile) {
                    p.renderIcons(sb, x + specialOffset, y - 93.0F * Settings.scale, POWER_AMOUNT_COLOR);
                } else {
                    p.renderIcons(sb, x + specialOffset, y - 88.0F * Settings.scale, POWER_AMOUNT_COLOR);
                }
                specialOffset += POWER_ICON_PADDING_X;
            } else {
                if (Settings.isMobile) {
                    p.renderIcons(sb, x + offset, y - 53.0F * Settings.scale, POWER_AMOUNT_COLOR);
                } else {
                    p.renderIcons(sb, x + offset, y - 48.0F * Settings.scale, POWER_AMOUNT_COLOR);
                }
                offset += POWER_ICON_PADDING_X;
            }
        }
        offset = 0.0F * Settings.scale;
        specialOffset = 0.0F * Settings.scale;
        for (AbstractPower p : this.powers) {
            if (p instanceof AbstractStancePower) {
                continue;
            } else if (SPECIAL_POWERS.contains(p.ID)) {
                if (Settings.isMobile) {
                    p.renderAmount(sb, x + specialOffset + 32.0F * Settings.scale, y - 115.0F * Settings.scale, POWER_AMOUNT_COLOR);
                } else {
                    p.renderAmount(sb, x + specialOffset + 32.0F * Settings.scale, y - 106.0F * Settings.scale, POWER_AMOUNT_COLOR);
                }
                specialOffset += POWER_ICON_PADDING_X;
            } else {
                if (Settings.isMobile) {
                    p.renderAmount(sb, x + offset + 32.0F * Settings.scale, y - 75.0F * Settings.scale, POWER_AMOUNT_COLOR);
                } else {
                    p.renderAmount(sb, x + offset + 32.0F * Settings.scale, y - 66.0F * Settings.scale, POWER_AMOUNT_COLOR);
                }
                offset += POWER_ICON_PADDING_X;
            }
        }
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
