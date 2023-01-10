package code.relics;

import code.characters.BlademasterCharacter;

import static code.Blademaster.makeID;

public class TodoItem extends AbstractEasyRelic {
    public static final String ID = makeID("TodoItem");

    public TodoItem() {
        super(ID, RelicTier.STARTER, LandingSound.FLAT, BlademasterCharacter.Enums.BLADEMASTER_COLOR);
    }
}
