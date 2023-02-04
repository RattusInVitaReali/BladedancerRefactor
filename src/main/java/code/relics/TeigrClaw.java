package code.relics;

import static code.Blademaster.makeID;

public class TeigrClaw extends AbstractBlademasterRelic {

    public static final String ID = makeID("TeigrClaw");
    private static final RelicTier TIER = RelicTier.UNCOMMON;
    private static final LandingSound LANDING_SOUND = LandingSound.SOLID;

    public TeigrClaw() {
        super(ID, TIER, LANDING_SOUND);
    }
}
