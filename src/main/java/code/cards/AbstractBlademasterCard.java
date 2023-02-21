package code.cards;

import basemod.abstracts.CustomCard;
import basemod.helpers.TooltipInfo;
import code.characters.BlademasterCharacter;
import code.patches.BlademasterTags;
import code.powers.BleedingPower;
import code.powers.ComboPower;
import code.powers.FuryPower;
import code.powers.MassacrePower;
import code.powers.interfaces.OnBloodiedPower;
import code.relics.SwordBladeShards;
import code.relics.TeigrClaw;
import code.util.BlademasterUtil;
import code.util.ImageHelper;
import code.util.TextureLoader;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
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
import com.megacrit.cardcrawl.powers.AbstractPower;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static code.Blademaster.*;
import static code.util.BlademasterUtil.getAliveMonsters;

@SuppressWarnings("BooleanMethodIsAlwaysInverted")
public abstract class AbstractBlademasterCard extends CustomCard {

    private static final HashMap<String, CardStrings> stringsMap = new HashMap<>();
    protected static final CardStrings tooltipCardStrings = getCardStrings(makeID("AbstractBlademasterCard"));
    protected static final String[] tooltips = tooltipCardStrings.EXTENDED_DESCRIPTION;
    public static final TextureAtlas.AtlasRegion furySCVPTexture = ImageHelper.asAtlasRegion(TextureLoader.getTexture(modID + "Resources/images/1024/Fury.png"));
    public static final TextureAtlas.AtlasRegion comboSCVPTexture = ImageHelper.asAtlasRegion(TextureLoader.getTexture(modID + "Resources/images/1024/Combo.png"));
    public int secondMagic;
    public int baseSecondMagic;
    public boolean upgradedSecondMagic;
    public boolean isSecondMagicModified;
    public int secondDamage;
    public int baseSecondDamage;
    public boolean upgradedSecondDamage;
    public boolean isSecondDamageModified;
    public int furyCost = 0;
    public int furyCostForTurn = furyCost;
    public boolean isFuryCostModified = false;
    public boolean upgradedFuryCost = false;
    public int comboCost = 0;
    public int comboCostForTurn = comboCost;
    public boolean isComboCostModified = false;
    public boolean upgradedComboCost = false;
    protected final CardStrings cardStrings;

    public AbstractBlademasterCard(final String cardID, final int cost, final CardType type, final CardRarity rarity, final CardTarget target) {
        this(cardID, cost, type, rarity, target, 0, 0);
    }

    public AbstractBlademasterCard(final String cardID, final int cost, final CardType type, final CardRarity rarity, final CardTarget target, final CardColor color) {
        this(cardID, cost, type, rarity, target, color, 0, 0);
    }

    public AbstractBlademasterCard(final String cardID, final int cost, final CardType type, final CardRarity rarity, final CardTarget target, int furyCost, int comboCost) {
        this(cardID, cost, type, rarity, target, BlademasterCharacter.Enums.BLADEMASTER_COLOR, furyCost, comboCost);
    }

    public AbstractBlademasterCard(final String cardID, final int cost, final CardType type, final CardRarity rarity, final CardTarget target, final CardColor color, int furyCost, int comboCost) {
        super(cardID, "", getCardTextureString(cardID.replace(modID + ":", ""), type),
                cost, "", type, color, rarity, target);
        cardStrings = getCardStrings(this.cardID);
        setDescription(cardStrings.DESCRIPTION);
        name = originalName = cardStrings.NAME;
        this.furyCost = this.furyCostForTurn = furyCost;
        this.comboCost = this.comboCostForTurn = comboCost;
        if (furyReq() > 0)
            this.tags.add(BlademasterTags.FURY_FINISHER);
        if (comboReq() > 0)
            this.tags.add(BlademasterTags.COMBO_FINISHER);
        initializeTitle();
    }

    private static CardStrings getCardStrings(String cardID) {
        if (!stringsMap.containsKey(cardID)) {
            stringsMap.put(cardID, loadCardStrings(cardID));
        }
        return stringsMap.get(cardID);
    }

    protected static CardStrings loadCardStrings(String cardID) {
        return CardCrawlGame.languagePack.getCardStrings(cardID);
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

    public static Texture getFuryOverlayTexture() {
        return TextureLoader.getTexture(modID + "Resources/images/512/Fury.png");
    }

    public static Texture getComboOverlayTexture() {
        return TextureLoader.getTexture(modID + "Resources/images/512/Combo.png");
    }

    public static boolean isBloodied(AbstractMonster m) {
        return (m.currentHealth <= m.maxHealth * 0.6)
                || (m.currentHealth <= m.maxHealth * 0.75 && AbstractDungeon.player.hasRelic(TeigrClaw.ID))
                || (m.hasPower(BleedingPower.POWER_ID) && AbstractDungeon.player.hasRelic(SwordBladeShards.ID));
    }

    public static void triggerBloodiedPowers(AbstractPlayer p, AbstractMonster m) {
        for (AbstractPower power : p.powers) {
            if (power instanceof OnBloodiedPower) {
                ((OnBloodiedPower) power).onBloodied(m);
            }
        }
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
        if (hasTag(BlademasterTags.BLOODIED)) {
            for (AbstractMonster monster : getAliveMonsters()) {
                if (isBloodied(monster)) {
                    this.glowColor = GOLD_BORDER_GLOW_COLOR;
                }
            }
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
        furyCostForTurn = furyCost;
        comboCostForTurn = comboCost;
        isFuryCostModified = false;
        isComboCostModified = false;
    }

    @Override
    public AbstractCard makeStatEquivalentCopy() {
        AbstractCard abstractCard = super.makeStatEquivalentCopy();
        AbstractBlademasterCard card = (AbstractBlademasterCard) abstractCard;
        card.baseSecondDamage = baseSecondDamage;
        card.baseSecondMagic = baseSecondMagic;
        card.furyCost = furyCost;
        card.furyCostForTurn = furyCostForTurn;
        card.comboCost = comboCost;
        card.comboCostForTurn = comboCostForTurn;
        return card;
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
        if (upgradedFuryCost) {
            isFuryCostModified = true;
        }
        if (upgradedComboCost) {
            isComboCostModified = true;
        }
    }

    @Override
    public List<TooltipInfo> getCustomTooltips() {
        List<TooltipInfo> list = new ArrayList<>();
        if (rawDescription.contains("ChargeIcon]")) {
            list.add(new TooltipInfo(tooltips[0], tooltips[1]));
        }
        if (rawDescription.contains("]Wind[")) {
            list.add(new TooltipInfo(tooltips[8], tooltips[9]));
        }
        if (rawDescription.contains("]Lightning[")) {
            list.add(new TooltipInfo(tooltips[10], tooltips[11]));
        }
        if (rawDescription.contains("]Basic[")) {
            list.add(new TooltipInfo(tooltips[12], tooltips[13]));
        }
        if (hasTag(BlademasterTags.FURY_FINISHER) ||
                rawDescription.contains("]Fury[")) {
            list.add(new TooltipInfo(tooltips[2], tooltips[3]));
        }
        if (hasTag(BlademasterTags.COMBO_FINISHER) ||
                rawDescription.contains("]Combo[")) {
            list.add(new TooltipInfo(tooltips[4], tooltips[5]));
        }
        return list;
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

    protected void upgradeFuryCost(int newFuryCost) {
        int diff = this.furyCostForTurn - this.furyCost;
        this.furyCost = newFuryCost;
        if (this.furyCostForTurn > 0) {
            this.furyCostForTurn = this.furyCost + diff;
        }

        if (this.furyCostForTurn < 0) {
            this.furyCostForTurn = 0;
        }

        this.upgradedFuryCost = true;
    }

    protected void upgradeComboCost(int newComboCost) {
        int diff = this.comboCostForTurn - this.comboCost;
        this.comboCost = newComboCost;
        if (this.comboCostForTurn > 0) {
            this.comboCostForTurn = this.comboCost + diff;
        }

        if (this.comboCostForTurn < 0) {
            this.comboCostForTurn = 0;
        }

        this.upgradedComboCost = true;
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
        return furyCostForTurn;
    }

    public int comboReq() {
        return comboCostForTurn;
    }

    @Override
    public void setCostForTurn(int amt) {
        super.setCostForTurn(amt);
        if (amt == 0) {
            setComboCostForTurn(amt);
            setFuryCostForTurn(amt);
        }
    }

    public void setComboCostForTurn(int comboCostForTurn) {
        this.comboCostForTurn = comboCostForTurn;
        if (comboCostForTurn != comboCost) isComboCostModified = true;
    }

    public void setFuryCostForTurn(int furyCostForTurn) {
        this.furyCostForTurn = furyCostForTurn;
        if (furyCostForTurn != furyCost) isFuryCostModified = true;
    }

    public boolean hasEnoughFury() {
        return BlademasterUtil.getPowerAmount(AbstractDungeon.player, FuryPower.POWER_ID) >= furyReq();
    }

    public boolean hasEnoughCombo() {
        return BlademasterUtil.getPowerAmount(AbstractDungeon.player, ComboPower.POWER_ID) >= comboReq();
    }

    protected final void consumeFinisherCost() {
        AbstractPlayer p = AbstractDungeon.player;
        if (furyReq() > 0 && !isInAutoplay) {
            BlademasterUtil.playerApplyPower(p, new FuryPower(p, -furyReq()));
        }
        if (comboReq() > 0 && !isInAutoplay) {
            BlademasterUtil.playerApplyPower(p, new ComboPower(p, -comboReq()));
        }

    }

    protected final void useBloodiedWrapper(AbstractPlayer p, AbstractMonster m) {
        if (isBloodied(m)) {
            useBloodied(p, m);
            triggerBloodiedPowers(p, m);
            if (p.hasPower(MassacrePower.POWER_ID)) {
                for (int i = 0; i < BlademasterUtil.getPowerAmount(p, MassacrePower.POWER_ID); i++) {
                    useBloodied(p, m);
                    triggerBloodiedPowers(p, m);
                }
            }
        }
    }

    protected void useBloodied(AbstractPlayer p, AbstractMonster m) {

    }

    @Override
    public boolean canUse(AbstractPlayer p, AbstractMonster m) {
        if (!super.canUse(p, m))
            return false;
        if (!hasEnoughFury() && !isInAutoplay) {
            cantUseMessage = "I'm not furious enough!";
            return false;
        }
        if (!hasEnoughCombo() && !isInAutoplay) {
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
