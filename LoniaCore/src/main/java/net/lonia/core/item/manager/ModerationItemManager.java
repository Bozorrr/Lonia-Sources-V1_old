package net.lonia.core.item.manager;

import net.lonia.core.item.builder.ItemBuilder;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public class ModerationItemManager {
    public static final ItemStack BADMSG = new ItemBuilder(Material.ARROW).setName("§6Message dérangeant").setLore("§cmute 1m", "§f- Immaturité abusive", "§f- Abus de formatages, symboles").toItemStack();
    public static final ItemStack LIE = new ItemBuilder(Material.STRING).setName("§6Fausse information").setLore("§cmute 2m", "§f- Mensonge toxique, dérangeant", "§f- Usurpation d'identité").toItemStack();
    public static final ItemStack FLOOD = new ItemBuilder(Material.SNOW_BALL).setName("§6Spam").setLore("§cmute 5m").toItemStack();
    public static final ItemStack PROVOC = new ItemBuilder(Material.FLINT_AND_STEEL).setName("§6Provocation").setLore("§cmute 8m").toItemStack();
    public static final ItemStack INCIT = new ItemBuilder(Material.TNT).setName("§6Incitation à l'infraction").setLore("§cmute 12m").hideItemFlag().toItemStack();
    public static final ItemStack BADLANGAGE = new ItemBuilder(Material.ROTTEN_FLESH).setName("§6Langage incorrect").setLore("§cmute 20m", "§f- Insulte sans insulter qq'un", "§f- Rabaissement", "§f- Vulgarité excessive").hideItemFlag().toItemStack();
    public static final ItemStack INSULT = new ItemBuilder(Material.IRON_SWORD).setName("§6Insulte").setLore("§cmute 35m").hideItemFlag().toItemStack();
    public static final ItemStack TRASH = new ItemBuilder(Material.IRON_CHESTPLATE).setName("§6Haine").setLore("§cban 9h", "§f- Trop d'insultes", "§f- Haine, apologie haineuse").toItemStack();
    public static final ItemStack CONTOURN = new ItemBuilder(Material.BOOK).setName("§6Tentative de contournement").setLore("§cmute 50m", "§f- Infraction en plusieurs messages").toItemStack();
    public static final ItemStack SELL = new ItemBuilder(Material.NAME_TAG).setName("§6Vente").setLore("§cban 1h").toItemStack();
    public static final ItemStack PUB = new ItemBuilder(Material.BANNER, 1, 15).setName("§6Publicité interdite").setLore("§cban 6h", "§f- Pour serveur Minecraft").toItemStack();
    public static final ItemStack DOOM = new ItemBuilder(Material.ENDER_PEARL).setName("§6Menace DOX/DDOS/Hack/...").setLore("§cban 18h", "§f- Menace brève et futile").toItemStack();
    public static final ItemStack DOOMBLOOD = new ItemBuilder(Material.BONE).setName("§6Menace IRL/DOX/DDOS/Hack/...").setLore("§cban 4d", "§f- Toute menace IRL", "§f- Menace sérieuse visant à faire peure").toItemStack();
    public static final ItemStack BADLINK = new ItemBuilder(Material.FIREBALL).setName("§6Lien incorrect").setLore("§cban 8h", "§f- Pornographie/Screamer/Épilepsie/...").toItemStack();
    public static final ItemStack TRASHLINK = new ItemBuilder(Material.REDSTONE).setName("§6Lien incorrect").setLore("§cban 5d", "§f- Contenu gore/Trash").toItemStack();
    public static final ItemStack LIELINK = new ItemBuilder(Material.BARRIER).setName("§6Lien incorrect").setLore("§cban 14d", "§f- Phishing/Arnaque suspectée", "§4§l⚠ §cFaire un rapport").toItemStack();
    public static final ItemStack DOX = new ItemBuilder(Material.LAVA_BUCKET).setName("§6Partage d'informations privées").setLore("§cban 14d", "§f- Nom, addresse, numéro, IP, lieu, ...", "§4§l⚠ §cFaire un rapport").toItemStack();

    public static final ItemStack CPSLimitExceededI = new ItemBuilder(Material.IRON_SWORD).setName("§6Dépassement de la limite CPS").setLore("§cban 1d", "§f- 16 à 20 cps").hideItemFlag().toItemStack();
    public static final ItemStack CPSLimitExceededII = new ItemBuilder(Material.DIAMOND_SWORD).setName("§6Dépassement de la limite CPS").setLore("§cban 10d", "§f- Plus de 20 cps", "§4§l⚠ §cFaire un rapport").hideItemFlag().toItemStack();
    public static final ItemStack HackClient = new ItemBuilder(Material.DIAMOND_CHESTPLATE).setName("§6Client de triche").setLore("§cban 30d", "§4§l⚠ §cFaire un rapport").toItemStack();

    public static final ItemStack invalidBuild1 = new ItemBuilder(Material.GRASS).setName("§6Construction incorrecte").setLore("§cban 18h", "§f- Nudité, insulte", "§4§l⚠ §cLes zizis simples faits avec 4 blocs sont tolérés").toItemStack();
    public static final ItemStack invalidBuild2 = new ItemBuilder(Material.GRASS).setName("§6Construction incorrecte").setLore("§cban 36h", "§f- Haine, insulte", "§4§l⚠ §cLes zizis simples faits avec 4 blocs sont tolérés").toItemStack();
    public static final ItemStack antiGamePlay = new ItemBuilder(Material.TNT).setName("§6Anti-jeu").setLore("§cban 1d", "§f- Ne joue pas le but du jeu", "§f- Empêche ses coéquipiers de jouer", "§f- Spawn-kill").toItemStack();
    public static final ItemStack crossTeam = new ItemBuilder(Material.CHAINMAIL_CHESTPLATE).setName("§6Alliance interdite").setLore("§cban 1d").toItemStack();
    public static final ItemStack invalidConnection = new ItemBuilder(Material.WEB).setName("§6Connexion dérangeante").setLore("§cban 5m", "§f- Peut kick pour avertir avant", "§f- Ban si: Répétition/Comportement toxique").toItemStack();
    public static final ItemStack grief = new ItemBuilder(Material.LAVA_BUCKET).setName("§6Grief en freecube").setLore("§cban 14d", "§4§l⚠ §cSeulement en flagrant délit").toItemStack();
    public static final ItemStack boosting = new ItemBuilder(Material.COMPASS).setName("§6Boost-statistiques en multi-comptes").setLore("§5§l‣ §dResponsable", "§cban 3d").toItemStack();

    public static final ItemStack spamReportI = new ItemBuilder(Material.BLAZE_POWDER).setName("§6Abus de signalement").setLore("§cban 10m").toItemStack();
    public static final ItemStack spamReportII = new ItemBuilder(Material.BLAZE_ROD).setName("§6Abus de signalement").setLore("§cban 35m").toItemStack();
    public static final ItemStack HPDA = new ItemBuilder(Material.LAVA_BUCKET).setName("§6Hack/Phishing/DDOS/Arnaque/...").setLore("§5§l‣ §dResponsable", "§cban 5000d", "§4§l⚠ §cFaire un rapport").toItemStack();

    public static final ItemStack invalidPseudo = new ItemBuilder(Material.NAME_TAG).setName("§6Pseudonyme incorrect").setLore("§cban 30d").toItemStack();
    public static final ItemStack invalidSkinI = new ItemBuilder(Material.LEATHER_CHESTPLATE).setName("§6Skin/Cape incorrect(e)").setLore("§cban 4h", "§f- Skin de nudité/insultant", "§4§l⚠ §cLes skins/capes clignotant(e)s sont autorisé(e)s").toItemStack();
    public static final ItemStack invalidSkinII = new ItemBuilder(Material.LEATHER_CHESTPLATE).setName("§6Skin/Cape incorrect(e)").setLore("§cban 18h", "§f- Skin de haine", "§4§l⚠ §cLes skins/capes clignotant(e)s sont autorisé(e)s").toItemStack();
    public static final ItemStack invalidStatusI = new ItemBuilder(Material.ANVIL).setName("§6Statut incorrect").setLore("§cban 4h", "§f- Provocation/insulte", "§f- Nom de zone/bloc freecube incorrect(e)", "§f- Nom d'animal/avantage incorrect").toItemStack();
    public static final ItemStack invalidStatusII = new ItemBuilder(Material.ANVIL).setName("§6Statut incorrect").setLore("§cban 18h", "§f- Haine/insulte", "§f- Nom de zone/bloc freecube incorrect(e)", "§f- Nom d'animal/avantage incorrect").toItemStack();

}
