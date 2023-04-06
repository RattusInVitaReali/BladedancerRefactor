package code.relics;

import static code.Blademaster.makeID;

public class PaperDregon extends AbstractBlademasterRelic {

    public static final String ID = makeID("PaperDregon");
    private static final RelicTier TIER = RelicTier.RARE;
    private static final LandingSound LANDING_SOUND = LandingSound.FLAT;

    public PaperDregon() {
        super(ID, TIER, LANDING_SOUND);
    }
}
