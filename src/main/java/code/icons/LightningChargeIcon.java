package code.icons;

import code.Blademaster;
import code.util.TextureLoader;
import com.evacipated.cardcrawl.mod.stslib.icons.AbstractCustomIcon;

import static code.Blademaster.modID;

public class LightningChargeIcon extends AbstractCustomIcon {
    public static final String ID = Blademaster.makeID("LightningCharge");
    private static final String TEXTURE = modID + "Resources/images/icons/LightningCharge.png";
    private static LightningChargeIcon instance;

    public LightningChargeIcon() {
        super(ID, TextureLoader.getTexture(TEXTURE));
    }

    public static LightningChargeIcon get() {
        if (instance == null) {
            instance = new LightningChargeIcon();
        }
        return instance;
    }
}