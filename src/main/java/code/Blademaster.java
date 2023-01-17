package code;

import basemod.AutoAdd;
import basemod.BaseMod;
import basemod.helpers.RelicType;
import basemod.interfaces.*;
import code.cards.AbstractBlademasterCard;
import code.cards.cardvars.ConduitNumber;
import code.cards.cardvars.SecondDamage;
import code.cards.cardvars.SecondMagicNumber;
import code.characters.BlademasterCharacter;
import code.relics.AbstractEasyRelic;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.evacipated.cardcrawl.mod.stslib.Keyword;
import com.evacipated.cardcrawl.modthespire.lib.SpireInitializer;
import com.google.gson.Gson;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.localization.CharacterStrings;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.localization.RelicStrings;
import com.megacrit.cardcrawl.unlock.UnlockTracker;

import java.nio.charset.StandardCharsets;

@SuppressWarnings({"unused", "WeakerAccess"})
@SpireInitializer
public class Blademaster implements
        EditCardsSubscriber,
        EditRelicsSubscriber,
        EditStringsSubscriber,
        EditKeywordsSubscriber,
        EditCharactersSubscriber {

    public static final String modID = "blademaster";
    public static final String SHOULDER1 = modID + "Resources/images/characters/blademaster/shoulder.png";
    public static final String SHOULDER2 = modID + "Resources/images/characters/blademaster/shoulder2.png";
    public static final String CORPSE = modID + "Resources/images/characters/blademaster/corpse.png";
    public static final String BLADEMASTER_SKELETON_ATLAS = modID + "Resources/images/characters/blademaster/blademaster.atlas";
    public static final String BLADEMASTER_SKELETON_JSON = modID + "Resources/images/characters/blademaster/blademaster.json";
    private static final String ATTACK_S_ART = modID + "Resources/images/512/Attack.png";
    private static final String SKILL_S_ART = modID + "Resources/images/512/Skill.png";
    private static final String POWER_S_ART = modID + "Resources/images/512/Power.png";
    private static final String CARD_ENERGY_S = modID + "Resources/images/512/Energy.png";
    private static final String TEXT_ENERGY = modID + "Resources/images/512/TextEnergy.png";
    private static final String ATTACK_L_ART = modID + "Resources/images/1024/Attack.png";
    private static final String SKILL_L_ART = modID + "Resources/images/1024/Skill.png";
    private static final String POWER_L_ART = modID + "Resources/images/1024/Power.png";
    private static final String CARD_ENERGY_L = modID + "Resources/images/1024/Energy.png";
    private static final String CHARSELECT_BUTTON = modID + "Resources/images/characterSelect/blademaster/characterButton.png";
    private static final String CHARSELECT_PORTRAIT = modID + "Resources/images/characterSelect/blademaster/background.png";
    public static Color blademasterColor = new Color(.27f, .4f, .4f, 1);
    public static Settings.GameLanguage[] SupportedLanguages = {
            Settings.GameLanguage.ENG,
    };

    public Blademaster() {
        BaseMod.subscribe(this);

        BaseMod.addColor(BlademasterCharacter.Enums.BLADEMASTER_COLOR, blademasterColor, blademasterColor, blademasterColor,
                blademasterColor, blademasterColor, blademasterColor, blademasterColor,
                ATTACK_S_ART, SKILL_S_ART, POWER_S_ART, CARD_ENERGY_S,
                ATTACK_L_ART, SKILL_L_ART, POWER_L_ART,
                CARD_ENERGY_L, TEXT_ENERGY);
    }

    public static String makeID(String idText) {
        return modID + ":" + idText;
    }

    public static String makePath(String resourcePath) {
        return modID + "Resources/" + resourcePath;
    }

    public static String makeImagePath(String resourcePath) {
        return modID + "Resources/images/" + resourcePath;
    }

    public static String makeRelicPath(String resourcePath) {
        return modID + "Resources/images/relics/" + resourcePath;
    }

    public static String makePowerPath(String resourcePath) {
        return modID + "Resources/images/powers/" + resourcePath;
    }

    public static String makeCardPath(String resourcePath) {
        return modID + "Resources/images/cards/" + resourcePath;
    }

    public static void initialize() {
        Blademaster thisMod = new Blademaster();
    }

    private String getLangString() {
        for (Settings.GameLanguage lang : SupportedLanguages) {
            if (lang.equals(Settings.language)) {
                return Settings.language.name().toLowerCase();
            }
        }
        return "eng";
    }

    @Override
    public void receiveEditCharacters() {
        BaseMod.addCharacter(new BlademasterCharacter(BlademasterCharacter.characterStrings.NAMES[1], BlademasterCharacter.Enums.THE_BLADEMASTER),
                CHARSELECT_BUTTON, CHARSELECT_PORTRAIT, BlademasterCharacter.Enums.THE_BLADEMASTER);
    }

    @Override
    public void receiveEditRelics() {
        new AutoAdd(modID)
                .packageFilter(AbstractEasyRelic.class)
                .any(AbstractEasyRelic.class, (info, relic) -> {
                    if (relic.color == null) {
                        BaseMod.addRelic(relic, RelicType.SHARED);
                    } else {
                        BaseMod.addRelicToCustomPool(relic, relic.color);
                    }
                    if (!info.seen) {
                        UnlockTracker.markRelicAsSeen(relic.relicId);
                    }
                });
    }

    @Override
    public void receiveEditCards() {
        BaseMod.addDynamicVariable(new SecondMagicNumber());
        BaseMod.addDynamicVariable(new SecondDamage());
        BaseMod.addDynamicVariable(new ConduitNumber());
        new AutoAdd(modID)
                .packageFilter(AbstractBlademasterCard.class)
                .setDefaultSeen(true)
                .cards();
    }

    @Override
    public void receiveEditStrings() {
        BaseMod.loadCustomStringsFile(CardStrings.class, modID + "Resources/localization/" + getLangString() + "/Cardstrings.json");

        BaseMod.loadCustomStringsFile(RelicStrings.class, modID + "Resources/localization/" + getLangString() + "/Relicstrings.json");

        BaseMod.loadCustomStringsFile(CharacterStrings.class, modID + "Resources/localization/" + getLangString() + "/Charstrings.json");

        BaseMod.loadCustomStringsFile(PowerStrings.class, modID + "Resources/localization/" + getLangString() + "/Powerstrings.json");
    }

    @Override
    public void receiveEditKeywords() {
        Gson gson = new Gson();
        String json = Gdx.files.internal(modID + "Resources/localization/eng/Keywordstrings.json").readString(String.valueOf(StandardCharsets.UTF_8));
        Keyword[] keywords = gson.fromJson(json, Keyword[].class);

        if (keywords != null) {
            for (Keyword keyword : keywords) {
                System.out.println("REGISTERING KEYWORD: " + modID + ":" + keyword.PROPER_NAME);
                BaseMod.addKeyword(modID, keyword.PROPER_NAME, keyword.NAMES, keyword.DESCRIPTION);
            }
        } else {
            System.out.println("KEYWORDS ARE NULL! I REPEAT! KEYWORDS ARE NULL!");
        }
    }

    public enum BlademasterStance {
        BASIC,
        WIND,
        LIGHTNING
    }
}
