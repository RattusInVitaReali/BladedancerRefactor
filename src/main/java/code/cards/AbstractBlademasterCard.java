package code.cards;

import basemod.abstracts.CustomCard;
import code.Blademaster;
import code.characters.BlademasterCharacter;
import code.patches.BlademasterTags;
import code.powers.ComboPower;
import code.powers.FuryPower;
import code.util.BlademasterUtil;
import code.util.TextureLoader;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static code.Blademaster.*;

public abstract class AbstractBlademasterCard extends CustomCard {

    protected final CardStrings cardStrings;

    public int secondMagic;
    public int baseSecondMagic;
    public boolean upgradedSecondMagic;
    public boolean isSecondMagicModified;

    public int secondDamage;
    public int baseSecondDamage;
    public boolean upgradedSecondDamage;
    public boolean isSecondDamageModified;

    public AbstractBlademasterCard(final String cardID, final int cost, final CardType type, final CardRarity rarity, final CardTarget target) {
        this(cardID, cost, type, rarity, target, BlademasterCharacter.Enums.BLADEMASTER_COLOR);
    }

    public AbstractBlademasterCard(final String cardID, final int cost, final CardType type, final CardRarity rarity, final CardTarget target, final CardColor color) {
        super(cardID, "", getCardTextureString(cardID.replace(modID + ":", ""), type),
                cost, "", type, color, rarity, target);
        cardStrings = CardCrawlGame.languagePack.getCardStrings(this.cardID);
        setDescription(cardStrings.DESCRIPTION);
        name = originalName = cardStrings.NAME;
        if (furyReq() > 0)
            this.tags.add(BlademasterTags.FURY_FINISHER);
        if (comboReq() > 0)
            this.tags.add(BlademasterTags.COMBO_FINISHER);
        initializeTitle();
    }

    public static String getCardTextureString(final String cardName, final AbstractCard.CardType cardType) {
        String textureString = makeImagePath("cards/" + cardName + ".png");
        FileHandle h = Gdx.files.internal(textureString);
        if (!h.exists()) {
            switch (cardType) {
                case ATTACK:
                    textureString = makeImagePath("cards/Attack.png");
                    break;
                case POWER:
                    textureString = makeImagePath("cards/Power.png");
                    break;
                default:
                    textureString = makeImagePath("cards/Skill.png");
            }
        }
        return textureString;
    }

    @Override
    public void applyPowers() {
        if (baseSecondDamage > -1) {
            secondDamage = baseSecondDamage;

            int tmp = baseDamage;
            baseDamage = baseSecondDamage;

            super.applyPowers();

            secondDamage = damage;
            baseDamage = tmp;

            super.applyPowers();

            isSecondDamageModified = (secondDamage != baseSecondDamage);
        } else super.applyPowers();
    }

    @Override
    public void triggerOnGlowCheck() {
        this.glowColor = BLUE_BORDER_GLOW_COLOR.cpy();
        if (furyReq() > 0 && BlademasterUtil.getPowerAmount(AbstractDungeon.player, FuryPower.POWER_ID) >= furyReq()) {
            this.glowColor = Color.ORANGE.cpy();
            if (BlademasterUtil.getPowerAmount(AbstractDungeon.player, FuryPower.POWER_ID) >= furyReq())
                superFlash(glowColor);
        }
        if (comboReq() > 0 && BlademasterUtil.getPowerAmount(AbstractDungeon.player, ComboPower.POWER_ID) >= comboReq()) {
            this.glowColor = Color.RED.cpy();
            if (BlademasterUtil.getPowerAmount(AbstractDungeon.player, ComboPower.POWER_ID) == comboReq())
                superFlash(glowColor);
        }
    }

    @Override
    public void calculateCardDamage(AbstractMonster mo) {
        if (baseSecondDamage > -1) {
            secondDamage = baseSecondDamage;

            int tmp = baseDamage;
            baseDamage = baseSecondDamage;

            super.calculateCardDamage(mo);

            secondDamage = damage;
            baseDamage = tmp;

            super.calculateCardDamage(mo);

            isSecondDamageModified = (secondDamage != baseSecondDamage);
        } else super.calculateCardDamage(mo);
    }

    @Override
    public void resetAttributes() {
        super.resetAttributes();
        secondMagic = baseSecondMagic;
        isSecondMagicModified = false;
        secondDamage = baseSecondDamage;
        isSecondDamageModified = false;
    }

    @Override
    public void displayUpgrades() {
        super.displayUpgrades();
        if (upgradedSecondMagic) {
            secondMagic = baseSecondMagic;
            isSecondMagicModified = true;
        }
        if (upgradedSecondDamage) {
            secondDamage = baseSecondDamage;
            isSecondDamageModified = true;
        }
    }

    protected void upgradeSecondMagic(int amount) {
        baseSecondMagic += amount;
        secondMagic = baseSecondMagic;
        upgradedSecondMagic = true;
    }

    protected void upgradeSecondDamage(int amount) {
        baseSecondDamage += amount;
        secondDamage = baseSecondDamage;
        upgradedSecondDamage = true;
    }

    protected void setDescription(String description) {
        rawDescription = description;
        initializeDescription();
    }

    protected void setUpgradeDescription() {
        setDescription(cardStrings.UPGRADE_DESCRIPTION);
    }

    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            onUpgrade();
        }
    }

    public abstract void onUpgrade();

    public int furyReq() {
        return 0;
    }

    public int comboReq() {
        return 0;
    }

    public boolean hasEnoughFury() {
        return BlademasterUtil.getPowerAmount(AbstractDungeon.player, FuryPower.POWER_ID) >= furyReq();
    }

    public boolean hasEnoughCombo() {
        return BlademasterUtil.getPowerAmount(AbstractDungeon.player, ComboPower.POWER_ID) >= comboReq();
    }

    public Texture getFuryOverlayTexture() {
        return TextureLoader.getTexture(modID + "Resources/images/512/Fury.png");
    }

    public Texture getComboOverlayTexture() {
        return TextureLoader.getTexture(modID + "Resources/images/512/Combo.png");
    }

    protected final void consumeFinisherCost() {
        AbstractPlayer p = AbstractDungeon.player;
        if (furyReq() > 0) {
            BlademasterUtil.playerApplyPower(p, new FuryPower(p, -furyReq()));
        }
        if (comboReq() > 0) {
            BlademasterUtil.playerApplyPower(p, new ComboPower(p, -comboReq()));
        }

    }

    protected boolean isBloodied(AbstractMonster m ) {
        return (m.currentHealth <= m.maxHealth / 2);
    }

    @Override
    public boolean canUse(AbstractPlayer p, AbstractMonster m) {
        if (!super.canUse(p, m))
            return false;
        if (!hasEnoughFury()) {
            cantUseMessage = "I'm not furious enough!";
            return false;
        }
        if (!hasEnoughCombo()) {
            cantUseMessage = "I'm not.. comboing? enough!";
            return false;
        }
        return true;
    }

    protected void damageMonster(AbstractMonster m, int amount, AbstractGameAction.AttackEffect fx) {
        addToBot(new DamageAction(m, new DamageInfo(AbstractDungeon.player, amount, damageTypeForTurn), fx));
    }

    protected void damageAllMonsters(int[] amounts, AbstractGameAction.AttackEffect fx) {
        addToBot(new DamageAllEnemiesAction(AbstractDungeon.player, amounts, damageTypeForTurn, fx));
    }

    protected void block(int amount) {
        addToBot(new GainBlockAction(AbstractDungeon.player, AbstractDungeon.player, amount));
    }

}
