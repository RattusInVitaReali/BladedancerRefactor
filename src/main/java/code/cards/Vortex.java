package code.cards;

import code.powers.BleedingPower;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.utility.SFXAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.VulnerablePower;
import com.megacrit.cardcrawl.powers.WeakPower;
import com.megacrit.cardcrawl.vfx.combat.CleaveEffect;

import static code.Blademaster.makeID;
import static code.util.BlademasterUtil.getAliveMonsters;
import static code.util.BlademasterUtil.playerApplyPower;

public class Vortex extends AbstractStanceCard {

    public final static String ID = makeID("Vortex");
    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.ALL_ENEMY;
    private static final CardType TYPE = CardType.SKILL;
    private static final int COST = 1;
    private static final int MAGIC = 5;
    private static final int UPGRADE_MAGIC = 2;
    private static final int SECOND_MAGIC = 1;
    private static final int UPGRADE_SECOND_MAGIC = 1;

    public Vortex() {
        super(ID, COST, TYPE, RARITY, TARGET);
        baseMagicNumber = magicNumber = MAGIC;
        baseSecondMagic = secondMagic = SECOND_MAGIC;
        setDescription(cardStrings.DESCRIPTION);
    }

    @Override
    public void useBasic(AbstractPlayer p, AbstractMonster m) {
        addToBot(new SFXAction("ATTACK_HEAVY"));
        addToBot(new VFXAction(p, new CleaveEffect(), .1f));
        for (AbstractMonster monster : getAliveMonsters()) {
            playerApplyPower(monster, new BleedingPower(monster, magicNumber));
        }
    }

    @Override
    public void useWind(AbstractPlayer p, AbstractMonster m) {
        useBasic(p, m);
        for (AbstractMonster monster : getAliveMonsters()) {
            playerApplyPower(monster, new WeakPower(monster, secondMagic, false));
        }
    }

    @Override
    public void useLightning(AbstractPlayer p, AbstractMonster m) {
        useBasic(p, m);
        for (AbstractMonster monster : getAliveMonsters()) {
            playerApplyPower(monster, new VulnerablePower(monster, secondMagic, false));
        }
    }

    @Override
    public void onUpgrade() {
        upgradeMagicNumber(UPGRADE_MAGIC);
        upgradeSecondMagic(UPGRADE_SECOND_MAGIC);
    }
}