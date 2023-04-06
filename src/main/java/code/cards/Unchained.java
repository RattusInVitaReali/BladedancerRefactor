package code.cards;

import code.patches.BlademasterTags;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.unlock.UnlockTracker;

import java.util.ArrayList;

import static code.Blademaster.makeID;
import static com.megacrit.cardcrawl.dungeons.AbstractDungeon.*;

public class Unchained extends AbstractBlademasterCard {

    public final static String ID = makeID("Unchained");
    private static final CardRarity RARITY = CardRarity.RARE;
    private static final CardTarget TARGET = CardTarget.NONE;
    private static final CardType TYPE = CardType.SKILL;
    private static final int COST = 1;
    private static final int UPGRADED_COST = 0;
    private static final int MAGIC = 1;

    public Unchained() {
        super(ID, COST, TYPE, RARITY, TARGET);
        baseMagicNumber = magicNumber = MAGIC;
        exhaust = true;
    }

    public static AbstractCard returnTrulyRandomFinisherInCombat() {
        ArrayList<AbstractCard> list = new ArrayList<>();
        for (AbstractCard c : srcCommonCardPool.group) {
            if (c.hasTag(BlademasterTags.COMBO_FINISHER) || c.hasTag(BlademasterTags.FURY_FINISHER)) {
                list.add(c);
                UnlockTracker.markCardAsSeen(c.cardID);
            }
        }
        for (AbstractCard c : srcUncommonCardPool.group) {
            if (c.hasTag(BlademasterTags.COMBO_FINISHER) || c.hasTag(BlademasterTags.FURY_FINISHER)) {
                list.add(c);
                UnlockTracker.markCardAsSeen(c.cardID);
            }
        }
        for (AbstractCard c : srcRareCardPool.group) {
            if (c.hasTag(BlademasterTags.COMBO_FINISHER) || c.hasTag(BlademasterTags.FURY_FINISHER)) {
                list.add(c);
                UnlockTracker.markCardAsSeen(c.cardID);
            }
        }
        return list.get(cardRandomRng.random(list.size() - 1));
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        AbstractCard card = returnTrulyRandomFinisherInCombat().makeCopy();
        card.setCostForTurn(0);
        addToBot(new MakeTempCardInHandAction(card));
    }

    @Override
    public void onUpgrade() {
        upgradeBaseCost(UPGRADED_COST);
    }
}