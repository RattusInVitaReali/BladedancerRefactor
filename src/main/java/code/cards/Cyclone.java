package code.cards;

import code.actions.WindStanceAction;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.utility.SFXAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.IntangiblePlayerPower;
import com.megacrit.cardcrawl.vfx.combat.CleaveEffect;

import static code.Blademaster.makeID;
import static code.util.BlademasterUtil.playerApplyPower;

public class Cyclone extends AbstractBlademasterCard {

    public final static String ID = makeID("Cyclone");
    private static final CardRarity RARITY = CardRarity.RARE;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.SKILL;
    private static final int COST = 0;
    private static final int COMBO_REQ = 4;
    private static final int DAMAGE = 10;
    private static final int MAGIC = 1;
    private static final int UPGRADE_MAGIC = 1;

    public Cyclone() {
        super(ID, COST, TYPE, RARITY, TARGET, 0, COMBO_REQ);
        baseDamage = DAMAGE;
        isMultiDamage = true;
        baseMagicNumber = magicNumber = MAGIC;
        exhaust = true;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        consumeFinisherCost();
        this.addToBot(new SFXAction("ATTACK_HEAVY"));
        if (Settings.FAST_MODE) {
            this.addToBot(new VFXAction(new CleaveEffect()));
        } else {
            this.addToBot(new VFXAction(p, new CleaveEffect(), 0.2F));
        }
        damageAllMonsters(multiDamage, AbstractGameAction.AttackEffect.NONE);
        playerApplyPower(p, new IntangiblePlayerPower(p, magicNumber));
        addToBot(new WindStanceAction());
    }

    @Override
    public void onUpgrade() {
        upgradeMagicNumber(UPGRADE_MAGIC);
    }
}