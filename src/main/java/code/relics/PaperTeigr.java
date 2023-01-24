package code.relics;

import static code.Blademaster.makeID;

public class PaperTeigr extends AbstractBlademasterRelic {

    public static final String ID = makeID("PaperTeigr");
    private static final RelicTier TIER = RelicTier.UNCOMMON;
    private static final LandingSound LANDING_SOUND = LandingSound.FLAT;

    public PaperTeigr() {
        super(ID, TIER, LANDING_SOUND);
    }
}
