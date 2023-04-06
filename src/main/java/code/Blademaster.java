package code;

import basemod.AutoAdd;
import basemod.BaseMod;
import basemod.ModLabeledToggleButton;
import basemod.ModPanel;
import basemod.helpers.RelicType;
import basemod.interfaces.*;
import code.cards.AbstractBlademasterCard;
import code.cards.cardvars.ConduitNumber;
import code.cards.cardvars.SecondDamage;
import code.cards.cardvars.SecondMagicNumber;
import code.characters.BlademasterCharacter;
import code.icons.LightningChargeIcon;
import code.icons.WindChargeIcon;
import code.potions.BleedingPotion;
import code.potions.FinisherPotion;
import code.potions.StancePotion;
import code.relics.AbstractBlademasterRelic;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.evacipated.cardcrawl.mod.stslib.Keyword;
import com.evacipated.cardcrawl.mod.stslib.icons.CustomIconHelper;
import com.evacipated.cardcrawl.modthespire.lib.SpireConfig;
import com.evacipated.cardcrawl.modthespire.lib.SpireInitializer;
import com.google.gson.Gson;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.helpers.FontHelper;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.localization.*;
import com.megacrit.cardcrawl.unlock.UnlockTracker;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Properties;

@SuppressWarnings({"unused", "WeakerAccess"})
@SpireInitializer
public class Blademaster implements
        EditCardsSubscriber,
        EditRelicsSubscriber,
        EditStringsSubscriber,
        EditKeywordsSubscriber,
        EditCharactersSubscriber,
        PostInitializeSubscriber {

    public static final String modID = "blademaster";
    public static final String SHOULDER1 = modID + "Resources/images/characters/blademaster/shoulder.png";
    public static final String SHOULDER2 = modID + "Resources/images/characters/blademaster/shoulder2.png";
    public static final String CORPSE = modID + "Resources/images/characters/blademaster/corpse.png";
    public static final String BLADEMASTER_SKELETON_ATLAS = modID + "Resources/images/characters/blademaster/blademaster.atlas";
    public static final String BLADEMASTER_SKELETON_JSON = modID + "Resources/images/characters/blademaster/blademaster.json";
    public static final String BLADEMASTER_TRUE_VICTORY_BG = modID + "Resources/images/scenes/blademasterBg.png";
    public static final String BLADEMASTER_TRUE_VICTORY_1 = modID + "Resources/images/scenes/blademaster1.png";
    public static final String BLADEMASTER_TRUE_VICTORY_2 = modID + "Resources/images/scenes/blademaster2.png";
    public static final String BLADEMASTER_TRUE_VICTORY_3 = modID + "Resources/images/scenes/blademaster3.png";
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

    private static SpireConfig config = null;

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

        try {
            Properties defaults = new Properties();
            defaults.put("BloodiedParticles", Boolean.toString(true));
            defaults.put("BloodiedParticlesOnlyOnHover", Boolean.toString(true));
            config = new SpireConfig("blademaster", "config", defaults);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static boolean bloodiedParticlesEnabled() {
        return config != null && config.getBool("BloodiedParticles");
    }

    public static boolean bloodiedParticlesOnlyOnHover() {
        return config != null && config.getBool("BloodiedParticlesOnlyOnHover");
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
                .packageFilter(AbstractBlademasterRelic.class)
                .any(AbstractBlademasterRelic.class, (info, relic) -> {
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
        CustomIconHelper.addCustomIcon(WindChargeIcon.get());
        CustomIconHelper.addCustomIcon(LightningChargeIcon.get());

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

        BaseMod.loadCustomStringsFile(PotionStrings.class, modID + "Resources/localization/" + getLangString() + "/Potionstrings.json");

        BaseMod.loadCustomStringsFile(UIStrings.class, modID + "Resources/localization/" + getLangString() + "/UIstrings.json");
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

    @Override
    public void receivePostInitialize() {
        BaseMod.addPotion(BleedingPotion.class, BleedingPotion.LIQUID_COLOR, BleedingPotion.HYBRID_COLOR, BleedingPotion.SPOTS_COLOR, BleedingPotion.POTION_ID, BlademasterCharacter.Enums.THE_BLADEMASTER);
        BaseMod.addPotion(StancePotion.class, StancePotion.LIQUID_COLOR, StancePotion.HYBRID_COLOR, StancePotion.SPOTS_COLOR, StancePotion.POTION_ID, BlademasterCharacter.Enums.THE_BLADEMASTER);
        BaseMod.addPotion(FinisherPotion.class, FinisherPotion.LIQUID_COLOR, FinisherPotion.HYBRID_COLOR, FinisherPotion.SPOTS_COLOR, FinisherPotion.POTION_ID, BlademasterCharacter.Enums.THE_BLADEMASTER);

        UIStrings buttonStrings = CardCrawlGame.languagePack.getUIString("blademaster:ModConfig");
        ModPanel settingsPanel = new ModPanel();
        ModLabeledToggleButton bloodiedButton = new ModLabeledToggleButton(buttonStrings.TEXT[0], 350, 750, Settings.CREAM_COLOR, FontHelper.charDescFont, bloodiedParticlesEnabled(), settingsPanel, l -> {
        },
                button -> {
                    if (config != null) {
                        config.setBool("BloodiedParticles", button.enabled);
                        try {
                            config.save();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                });
        settingsPanel.addUIElement(bloodiedButton);
        ModLabeledToggleButton bloodiedParticlesOnlyOnHover = new ModLabeledToggleButton(buttonStrings.TEXT[1], 350, 700, Settings.CREAM_COLOR, FontHelper.charDescFont, bloodiedParticlesOnlyOnHover(), settingsPanel, l -> {
        },
                button -> {
                    if (config != null) {
                        config.setBool("BloodiedParticlesOnlyOnHover", button.enabled);
                        try {
                            config.save();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                });
        settingsPanel.addUIElement(bloodiedParticlesOnlyOnHover);

        BaseMod.registerModBadge(ImageMaster.loadImage(makeImagePath("modBadge.png")), "The Bladedancer", "Rattus", "Bladed Ancer.", settingsPanel);
    }

    public enum BlademasterStance {
        BASIC,
        WIND,
        LIGHTNING,
        NONE
    }
}
