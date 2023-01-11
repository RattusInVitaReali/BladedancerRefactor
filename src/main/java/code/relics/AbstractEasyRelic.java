package code.relics;

import basemod.abstracts.CustomRelic;
import code.util.TextureLoader;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.relics.AbstractRelic;

import static code.Blademaster.makeRelicPath;
import static code.Blademaster.modID;

public abstract class AbstractEasyRelic extends CustomRelic {
    public AbstractCard.CardColor color;

    public AbstractEasyRelic(String setId, AbstractRelic.RelicTier tier, AbstractRelic.LandingSound sfx) {
        this(setId, tier, sfx, null);
    }

    public AbstractEasyRelic(String setId, AbstractRelic.RelicTier tier, AbstractRelic.LandingSound sfx, AbstractCard.CardColor color) {
        super(setId, TextureLoader.getTexture(makeRelicPath(setId.replace(modID + ":", "") + ".png")), tier, sfx);
        outlineImg = TextureLoader.getTexture(makeRelicPath(setId.replace(modID + ":", "") + "Outline.png"));
        this.color = color;
    }

    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }
}