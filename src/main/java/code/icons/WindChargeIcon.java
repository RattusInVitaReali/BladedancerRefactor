package code.icons;

import code.Blademaster;
import code.util.TextureLoader;
import com.evacipated.cardcrawl.mod.stslib.icons.AbstractCustomIcon;

import static code.Blademaster.modID;

public class WindChargeIcon extends AbstractCustomIcon {
    public static final String ID = Blademaster.makeID("WindCharge");
    private static final String TEXTURE = modID + "Resources/images/icons/WindCharge.png";
    private static WindChargeIcon instance;

    public WindChargeIcon() {
        super(ID, TextureLoader.getTexture(TEXTURE));
    }

    public static WindChargeIcon get() {
        if (instance == null) {
            instance = new WindChargeIcon();
        }
        return instance;
    }
}