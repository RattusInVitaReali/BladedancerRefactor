package code.powers;

import code.util.TextureLoader;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.helpers.FontHelper;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;

import static code.Blademaster.modID;

public abstract class AbstractBlademasterPower extends AbstractPower {
    public static final Color redColor2 = Color.RED.cpy();
    public static final Color greenColor2 = Color.GREEN.cpy();
    public int amount2 = -1;
    public boolean isTwoAmount = false;
    public final boolean canGoNegative2 = false;
    protected final PowerStrings powerStrings;

    public AbstractBlademasterPower(String ID, PowerType powerType, boolean isTurnBased, AbstractCreature owner, int amount) {
        this.ID = ID;
        this.isTurnBased = isTurnBased;
        this.owner = owner;
        this.amount = amount;
        this.type = powerType;

        this.powerStrings = CardCrawlGame.languagePack.getPowerStrings(ID);
        this.name = powerStrings.NAME;

        Texture normalTexture = TextureLoader.getTexture(modID + "Resources/images/powers/" + this.ID.replace(modID + ":", "") + "Small.png");
        Texture hiDefImage = TextureLoader.getTexture(modID + "Resources/images/powers/" + this.ID.replace(modID + ":", "") + ".png");
        if (hiDefImage != null) {
            region128 = new TextureAtlas.AtlasRegion(hiDefImage, 0, 0, hiDefImage.getWidth(), hiDefImage.getHeight());
            if (normalTexture != null)
                region48 = new TextureAtlas.AtlasRegion(normalTexture, 0, 0, normalTexture.getWidth(), normalTexture.getHeight());
        } else if (normalTexture != null) {
            this.img = normalTexture;
            region48 = new TextureAtlas.AtlasRegion(normalTexture, 0, 0, normalTexture.getWidth(), normalTexture.getHeight());
        }

        updateDescription();
    }

    protected boolean renderAtZero() {
        return false;
    }

    @Override
    public void renderAmount(SpriteBatch sb, float x, float y, Color c) {
        super.renderAmount(sb, x, y, c);
        if (!isTwoAmount)
            return;
        if (amount2 >= 0) {
            if (!isTurnBased) {
                greenColor2.a = c.a;
                c = greenColor2;
            }

            FontHelper.renderFontRightTopAligned(sb, FontHelper.powerAmountFont, Integer.toString(amount2), x, y + 15.0F * Settings.scale, fontScale, c);
        } else if (canGoNegative2) {
            redColor2.a = c.a;
            c = redColor2;
            FontHelper.renderFontRightTopAligned(sb, FontHelper.powerAmountFont, Integer.toString(amount2), x, y + 15.0F * Settings.scale, fontScale, c);
        }
        if (amount == 0 && renderAtZero()) {
            FontHelper.renderFontRightTopAligned(sb, FontHelper.powerAmountFont, Integer.toString(this.amount), x, y, this.fontScale, c);
        }
    }

    @Override
    public void updateDescription() {
        description = powerStrings.DESCRIPTIONS[0];
    }

}