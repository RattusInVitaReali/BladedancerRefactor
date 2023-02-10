package code.cards;

import code.powers.HarmonyPower;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static code.Blademaster.makeID;
import static code.util.BlademasterUtil.playerApplyPower;

public class Harmony extends AbstractBlademasterCard {

    public final static String ID = makeID("Harmony");
    private static final CardRarity RARITY = CardRarity.RARE;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.POWER;
    private static final int COST = 2;


    public Harmony() {
        super(ID, COST, TYPE, RARITY, TARGET);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        playerApplyPower(p, new HarmonyPower(p));
    }

    @Override
    public void onUpgrade() {
        selfRetain = true;
        setUpgradeDescription();
    }
}