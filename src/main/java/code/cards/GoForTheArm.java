package code.cards;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.ArtifactPower;
import com.megacrit.cardcrawl.powers.GainStrengthPower;
import com.megacrit.cardcrawl.powers.StrengthPower;
import com.megacrit.cardcrawl.powers.WeakPower;

import static code.Blademaster.makeID;
import static code.util.BlademasterUtil.playerApplyPower;

public class GoForTheArm extends AbstractStanceCard {

    public final static String ID = makeID("GoForTheArm");
    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.ENEMY;
    private static final CardType TYPE = CardType.ATTACK;
    private static final int COST = 1;
    private static final int DAMAGE = 10;
    private static final int UPGRADE_DAMAGE = 3;
    private static final int MAGIC = 3;
    private static final int UPGRADE_MAGIC = 1;
    private static final int SECOND_MAGIC = 1;
    private static final int FURY_REQ = 10;

    public GoForTheArm() {
        super(ID, COST, TYPE, RARITY, TARGET, FURY_REQ, 0);
        baseDamage = DAMAGE;
        baseMagicNumber = magicNumber = MAGIC;
        baseSecondMagic = secondMagic = SECOND_MAGIC;
    }

    @Override
    public void useBasic(AbstractPlayer p, AbstractMonster m) {
        consumeFinisherCost();
        damageMonster(m, damage, AbstractGameAction.AttackEffect.SLASH_HEAVY);
        playerApplyPower(m, new WeakPower(m, secondMagic, false));
    }

    @Override
    public void useWind(AbstractPlayer p, AbstractMonster m) {
        useBasic(p, m);
        playerApplyPower(m, new StrengthPower(m, -magicNumber));
        if (m != null && !m.hasPower(ArtifactPower.POWER_ID)) {
            playerApplyPower(m, new GainStrengthPower(m, magicNumber));
        }
    }

    @Override
    public void onUpgrade() {
        upgradeDamage(UPGRADE_DAMAGE);
        upgradeMagicNumber(UPGRADE_MAGIC);
    }
}