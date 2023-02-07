package code.cards;

import basemod.helpers.CardModifierManager;
import code.modifiers.EtherealNoDescriptionMod;
import code.modifiers.ExhaustNoDescriptionMod;
import code.powers.BleedingPower;
import code.powers.FuryPower;
import code.util.BlademasterUtil;
import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static code.Blademaster.makeID;
import static code.util.BlademasterUtil.playerApplyPower;

public class Flash extends AbstractStanceCard {

    public final static String ID = makeID("Flash");
    private static final CardRarity RARITY = CardRarity.RARE;
    private static final CardTarget TARGET = CardTarget.ENEMY;
    private static final CardType TYPE = CardType.ATTACK;
    private static final int COST = 0;
    private static final int FURY_REQ = 12;
    private static final int FURY_REQ_PER_USE = 6;
    private static final int DAMAGE = 8;
    private static final int UPGRADE_DAMAGE = 3;
    private static final int MAGIC = 3;
    private static final int UPGRADE_MAGIC = 2;
    private static final int CONDUIT = 1;
    private static final int UPGRADE_CONDUIT = 1;

    public Flash(int fury) {
        super(ID, COST, TYPE, RARITY, TARGET, fury, 0);
        baseDamage = DAMAGE;
        baseMagicNumber = magicNumber = MAGIC;
        setBaseConduit(CONDUIT);
    }

    public Flash() {
        this(FURY_REQ);
    }

    @Override
    public void useBasic(AbstractPlayer p, AbstractMonster m) {
        consumeFinisherCost();
        damageMonster(m, damage, AbstractGameAction.AttackEffect.SLASH_DIAGONAL);
        playerApplyPower(m, new BleedingPower(m, magicNumber));
        Flash newFlash = (Flash) makeStatEquivalentCopy();
        newFlash.setFuryCostForTurn(furyCostForTurn + FURY_REQ_PER_USE);
        CardModifierManager.addModifier(newFlash, new ExhaustNoDescriptionMod());
        CardModifierManager.addModifier(newFlash, new EtherealNoDescriptionMod());
        addToBot(new MakeTempCardInHandAction(newFlash));
    }

    @Override
    public void triggerOnGlowCheck() {
        this.glowColor = Color.WHITE.cpy();
        if (BlademasterUtil.getPowerAmount(AbstractDungeon.player, FuryPower.POWER_ID) >= furyReq()) {
            this.glowColor = Color.ORANGE.cpy();
            superFlash(glowColor);
        }
    }

    @Override
    public void onUpgrade() {
        upgradeDamage(UPGRADE_DAMAGE);
        upgradeMagicNumber(UPGRADE_MAGIC);
        upgradeConduit(UPGRADE_CONDUIT);
    }
}