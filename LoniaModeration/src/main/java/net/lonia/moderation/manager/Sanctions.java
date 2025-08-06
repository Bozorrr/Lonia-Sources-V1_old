package net.lonia.moderation.manager;

import net.lonia.core.item.manager.ModerationItemManager;
import org.bukkit.inventory.ItemStack;

import java.text.SimpleDateFormat;


public enum Sanctions {

    BAD_MSG("§cMessage dérangeant", 1 * 60, ModerationItemManager.BADMSG),
    LIE("§cFausse information", 2 * 60, ModerationItemManager.LIE),
    FLOOD("§cSpam", 5 * 60, ModerationItemManager.FLOOD),
    PROVOCATION("§cProvocation", 8 * 60, ModerationItemManager.PROVOC),
    INCITE_INFRACTION("§cIncitation à l'infraction", 12 * 60, ModerationItemManager.INCIT),
    BAD_LANG("§cLangage incorrect", 20 * 60, ModerationItemManager.BADLANGAGE),
    INSULT("§cInsulte", 35 * 60, ModerationItemManager.INSULT),
    BYPASS_ATTEMPT("§cTentative de contournement", 50 * 60, ModerationItemManager.CONTOURN),

    TRASH("§cHaine", 9 * 60 * 60, ModerationItemManager.TRASH),
    SELL("§cVente", 1 * 60 * 60, ModerationItemManager.SELL),
    ILLEGAL_AD("§cPublicité interdite", 6 * 60 * 60, ModerationItemManager.PUB),
    TRASHDDOS1("§cMenace DOX/DDOS/Hack/...", 18 * 60 * 60, ModerationItemManager.DOOM),
    TRASHDDOS2("§cMenace IRL/DOX/DDOS/Hack/...", 4 * 60 * 60 * 24, ModerationItemManager.DOOMBLOOD),
    BAD_LINK("§cLien incorrect", 8 * 60 * 60, ModerationItemManager.BADLINK),
    BAD_LINK2("§cLien incorrect", 5 * 60 * 60 * 24, ModerationItemManager.TRASHLINK),
    BAD_LINK3("§cLien incorrect", 14 * 60 * 60 * 24, ModerationItemManager.LIELINK),
    PRIVATE_INFO_SHARE("§cPartage d'informations privées", 14 * 60 * 60 * 24, ModerationItemManager.DOX),
    BAD_USERNAME("§cPseudonyme incorrect", 30 * 60 * 60 * 24, ModerationItemManager.invalidPseudo),
    BAD_SKIN("§cSkin/Cape incorrect(e)", 4 * 60 * 60, ModerationItemManager.invalidSkinI),
    BAD_SKIN2("§cSkin/Cape incorrect(e)", 18 * 60 * 60, ModerationItemManager.invalidSkinII),
    BAD_STATUS("§cStatut incorrect", 4 * 60 * 60, ModerationItemManager.invalidStatusI),
    BAD_STATUS2("§cStatut incorrect", 18 * 60 * 60, ModerationItemManager.invalidStatusII),
    BAD_BUILD("§cConstruction incorrecte", 18 * 60 * 60, ModerationItemManager.invalidBuild1),
    BAD_BUILD2("§cConstruction incorrecte", 36 * 60 * 60, ModerationItemManager.invalidBuild2),
    SABOTAGE("§cAnti-jeu", 1 * 60 * 60 * 24, ModerationItemManager.antiGamePlay),
    CROSSTEAM("§cAlliance interdite", 1 * 60 * 60 * 24, ModerationItemManager.crossTeam),
    BAD_CO("§cConnexion dérangeante", 5 * 60, ModerationItemManager.invalidConnection),
    GRIEF("§cGrief en freecube", 14 * 60 * 60 * 24, ModerationItemManager.grief),
    BOOST_STATS("§cBoost-statistiques en multi-comptes", 3 * 60 * 60 * 24, ModerationItemManager.boosting),
    CPS_LIMIT("§cDépassement de la limite CPS", 1 * 60 * 60 * 24, ModerationItemManager.CPSLimitExceededI),
    CPS_LIMIT2("§cDépassement de la limite CPS", 10 * 60 * 60 * 24, ModerationItemManager.CPSLimitExceededII),
    CHEAT("§cClient de triche", 30 * 60 * 60 * 24, ModerationItemManager.HackClient),
    REPORT_ABUSE("§cAbus de signalement", 10 * 60, ModerationItemManager.spamReportI),
    REPORT_ABUSE2("§cAbus de signalement", 35 * 60, ModerationItemManager.spamReportII),
    DDOS_HACK("§cDDOS ou Hack/Phishing/Arnaque/...", 5000 * 60 * 60 * 24, ModerationItemManager.HPDA);

    private String name;
    private int duration;
    private ItemStack it;

    Sanctions(String name, int duration, ItemStack it) {
        this.name = name;
        this.duration = duration;
        this.it = it;
    }

    public static String getCurrentDate() {
        SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return sdf.format(new java.util.Date());
    }

    public String getName() {
        return name;
    }

    public int getDuration() {
        return duration;
    }

    public ItemStack getIt() {
        return it;
    }
}
