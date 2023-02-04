package code.relics;

import static code.Blademaster.makeID;

public class SwordBladeShards extends AbstractBlademasterRelic {

    public static final String ID = makeID("SwordBladeShards");
    private static final RelicTier TIER = RelicTier.SHOP;
    private static final LandingSound LANDING_SOUND = LandingSound.CLINK;

    public SwordBladeShards() {
        super(ID, TIER, LANDING_SOUND);
    }
}
