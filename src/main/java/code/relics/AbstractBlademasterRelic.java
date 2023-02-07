package code.relics;

import basemod.abstracts.CustomRelic;
import code.characters.BlademasterCharacter;
import code.util.TextureLoader;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.relics.AbstractRelic;

import static code.Blademaster.makeRelicPath;
import static code.Blademaster.modID;

public abstract class AbstractBlademasterRelic extends CustomRelic {

    public final AbstractCard.CardColor color;

    public AbstractBlademasterRelic(String setId, AbstractRelic.RelicTier tier, AbstractRelic.LandingSound sfx) {
        super(setId, TextureLoader.getTexture(makeRelicPath(setId.replace(modID + ":", "") + ".png")), tier, sfx);
        outlineImg = TextureLoader.getTexture(makeRelicPath(setId.replace(modID + ":", "") + "Outline.png"));
        color = BlademasterCharacter.Enums.BLADEMASTER_COLOR;
    }

    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }
}