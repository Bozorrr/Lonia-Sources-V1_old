package net.lonia.core.server.it;

import net.lonia.core.item.builder.ItemBuilder;
import net.lonia.core.server.ServerManager;
import org.bukkit.DyeColor;
import org.bukkit.Material;
import org.bukkit.block.banner.PatternType;
import org.bukkit.inventory.ItemStack;

public class ServerItemManager {

    public static ItemStack LOBBY1 = null;
    public static ItemStack BUILD1 = null;
    public static ItemStack BUILD2 = null;
    public static ItemStack BUILD3 = null;
    public static ItemStack BUILD4 = null;
    public static ItemStack BUILD5 = null;
    public static ItemStack BUILD6 = null;
    public static ItemStack BUILD7 = null;
    public static ItemStack BUILD8 = null;
    public static ItemStack BUILD9 = null;
    public static ItemStack BUILD10 = null;
    public static ItemStack BUILD11 = null;
    public static ItemStack BUILD12 = null;
    public static ItemStack DUELPVP = null;
    public static ItemStack ARENEC = null;
    public static ItemStack ARENEE = null;
    public static ItemStack FREECUBE = null;
    public static ItemStack UNEXUS1 = null;
    public static ItemStack UNEXUS2 = null;
    public static ItemStack UNEXUS3 = null;
    public static ItemStack UNEXUS4 = null;
    public static ItemStack UNEXUS5 = null;
    public static ItemStack RUNORKILL1 = null;
    public static ItemStack RUNORKILL2 = null;
    public static ItemStack RUNORKILL3 = null;
    public static ItemStack RUNORKILL4 = null;
    public static ItemStack RUNORKILL5 = null;
    public static ItemStack RUSH1 = null;
    public static ItemStack RUSH2 = null;
    public static ItemStack RUSH3 = null;
    public static ItemStack RUSH4 = null;
    public static ItemStack RUSH5 = null;
    public static ItemStack JUMPADVENTURE1 = null;
    public static ItemStack JUMPADVENTURE2 = null;
    public static ItemStack JUMPADVENTURE3 = null;
    public static ItemStack PIXELWAR = null;

    public ServerItemManager() {
        update();
    }

    public static void update() {

        LOBBY1 = new ItemBuilder(Material.BEACON).setName(ServerManager.lobby1.getPrefix3()).setLore(ServerManager.getStatus(ServerManager.lobby1.getId()).getPrefix(), "§bNombre de joueur connecté " + ServerManager.getConnectedPlayers(ServerManager.lobby1.getId())).toItemStack();

        BUILD1 = new ItemBuilder(Material.BRICK).setName(ServerManager.build1.getPrefix3()).setLore(ServerManager.getStatus(ServerManager.build1.getId()).getPrefix(), "§bNombre de joueur connecté " + ServerManager.getConnectedPlayers(ServerManager.build1.getId())).toItemStack();
        BUILD2 = new ItemBuilder(Material.BRICK).setName(ServerManager.build2.getPrefix3()).setLore(ServerManager.getStatus(ServerManager.build2.getId()).getPrefix(), "§bNombre de joueur connecté " + ServerManager.getConnectedPlayers(ServerManager.build2.getId())).toItemStack();
        BUILD3 = new ItemBuilder(Material.BRICK).setName(ServerManager.build3.getPrefix3()).setLore(ServerManager.getStatus(ServerManager.build3.getId()).getPrefix(), "§bNombre de joueur connecté " + ServerManager.getConnectedPlayers(ServerManager.build3.getId())).toItemStack();
        BUILD4 = new ItemBuilder(Material.BRICK).setName(ServerManager.build4.getPrefix3()).setLore(ServerManager.getStatus(ServerManager.build4.getId()).getPrefix(), "§bNombre de joueur connecté " + ServerManager.getConnectedPlayers(ServerManager.build4.getId())).toItemStack();
        BUILD5 = new ItemBuilder(Material.BRICK).setName(ServerManager.build5.getPrefix3()).setLore(ServerManager.getStatus(ServerManager.build5.getId()).getPrefix(), "§bNombre de joueur connecté " + ServerManager.getConnectedPlayers(ServerManager.build5.getId())).toItemStack();
        BUILD6 = new ItemBuilder(Material.BRICK).setName(ServerManager.build6.getPrefix3()).setLore(ServerManager.getStatus(ServerManager.build6.getId()).getPrefix(), "§bNombre de joueur connecté " + ServerManager.getConnectedPlayers(ServerManager.build6.getId())).toItemStack();
        BUILD7 = new ItemBuilder(Material.BRICK).setName(ServerManager.build7.getPrefix3()).setLore(ServerManager.getStatus(ServerManager.build7.getId()).getPrefix(), "§bNombre de joueur connecté " + ServerManager.getConnectedPlayers(ServerManager.build7.getId())).toItemStack();
        BUILD8 = new ItemBuilder(Material.BRICK).setName(ServerManager.build8.getPrefix3()).setLore(ServerManager.getStatus(ServerManager.build8.getId()).getPrefix(), "§bNombre de joueur connecté " + ServerManager.getConnectedPlayers(ServerManager.build8.getId())).toItemStack();
        BUILD9 = new ItemBuilder(Material.BRICK).setName(ServerManager.build9.getPrefix3()).setLore(ServerManager.getStatus(ServerManager.build9.getId()).getPrefix(), "§bNombre de joueur connecté " + ServerManager.getConnectedPlayers(ServerManager.build9.getId())).toItemStack();
        BUILD10 = new ItemBuilder(Material.BRICK).setName(ServerManager.build10.getPrefix3()).setLore(ServerManager.getStatus(ServerManager.build10.getId()).getPrefix(), "§bNombre de joueur connecté " + ServerManager.getConnectedPlayers(ServerManager.build10.getId())).toItemStack();
        BUILD11 = new ItemBuilder(Material.BRICK).setName(ServerManager.build11.getPrefix3()).setLore(ServerManager.getStatus(ServerManager.build11.getId()).getPrefix(), "§bNombre de joueur connecté " + ServerManager.getConnectedPlayers(ServerManager.build11.getId())).toItemStack();
        BUILD12 = new ItemBuilder(Material.BRICK).setName(ServerManager.build12.getPrefix3()).setLore(ServerManager.getStatus(ServerManager.build12.getId()).getPrefix(), "§bNombre de joueur connecté " + ServerManager.getConnectedPlayers(ServerManager.build12.getId())).toItemStack();

        DUELPVP = new ItemBuilder(Material.DIAMOND_SWORD).setName("§9Duel - 1v1").setLore("§a", "§3Nb de joueurs: §b" + ServerManager.getConnectedPlayers(ServerManager.duel_pvp.getId())).hideItemFlag().toItemStack();

        ARENEC = new ItemBuilder(Material.DIAMOND_CHESTPLATE).setName("§9Arène PVP - Classique").setLore("§a", "§3Nb de joueurs: §b" + ServerManager.getConnectedPlayers(ServerManager.arenec.getId())).hideItemFlag().toItemStack();
        ARENEE = new ItemBuilder(Material.POTION, 1, 16385).setName("§9Arène PVP - Effects").setLore("§a", "§3Nb de joueurs: §b" + ServerManager.getConnectedPlayers(ServerManager.arenee.getId())).hideItemFlag().toItemStack();

        FREECUBE = new ItemBuilder(Material.BANNER).setBannerColor(DyeColor.GREEN).setBannerColor(DyeColor.LIME).addBannerPattern(DyeColor.BLACK, PatternType.STRIPE_RIGHT).addBannerPattern(DyeColor.BLACK, PatternType.STRIPE_LEFT).addBannerPattern(DyeColor.BLACK, PatternType.STRIPE_MIDDLE).addBannerPattern(DyeColor.BLACK, PatternType.STRIPE_TOP).addBannerPattern(DyeColor.LIME, PatternType.BORDER).setName("§9Freecube #A").setLore("§a", "§3Nb de joueurs: §b" + ServerManager.getConnectedPlayers(ServerManager.freecube.getId())).hideItemFlag().toItemStack();

        UNEXUS1 = new ItemBuilder(Material.BANNER).setBannerColor(DyeColor.YELLOW).addBannerPattern(DyeColor.BLACK, PatternType.STRIPE_TOP).addBannerPattern(DyeColor.YELLOW, PatternType.RHOMBUS_MIDDLE).addBannerPattern(DyeColor.BLACK, PatternType.STRIPE_DOWNLEFT).addBannerPattern(DyeColor.YELLOW, PatternType.CURLY_BORDER).addBannerPattern(DyeColor.BLACK, PatternType.STRIPE_BOTTOM).addBannerPattern(DyeColor.YELLOW, PatternType.BORDER).setName("§9U-Nexus - 2v2").setLore("§a", "§3Nb de joueurs: §b" + ServerManager.getConnectedPlayers(ServerManager.unexus1.getId())).hideItemFlag().toItemStack();
        UNEXUS2 = new ItemBuilder(Material.BANNER).setBannerColor(DyeColor.YELLOW).addBannerPattern(DyeColor.BLACK, PatternType.STRIPE_TOP).addBannerPattern(DyeColor.YELLOW, PatternType.RHOMBUS_MIDDLE).addBannerPattern(DyeColor.BLACK, PatternType.STRIPE_DOWNLEFT).addBannerPattern(DyeColor.YELLOW, PatternType.CURLY_BORDER).addBannerPattern(DyeColor.BLACK, PatternType.STRIPE_BOTTOM).addBannerPattern(DyeColor.YELLOW, PatternType.BORDER).setName("§9U-Nexus - 2v2").setLore("§a", "§3Nb de joueurs: §b" + ServerManager.getConnectedPlayers(ServerManager.unexus2.getId())).hideItemFlag().toItemStack();
        UNEXUS3 = new ItemBuilder(Material.BANNER).setBannerColor(DyeColor.YELLOW).addBannerPattern(DyeColor.BLACK, PatternType.STRIPE_TOP).addBannerPattern(DyeColor.YELLOW, PatternType.RHOMBUS_MIDDLE).addBannerPattern(DyeColor.BLACK, PatternType.STRIPE_DOWNLEFT).addBannerPattern(DyeColor.YELLOW, PatternType.CURLY_BORDER).addBannerPattern(DyeColor.BLACK, PatternType.STRIPE_BOTTOM).addBannerPattern(DyeColor.YELLOW, PatternType.BORDER).setName("§9U-Nexus - 2v2").setLore("§a", "§3Nb de joueurs: §b" + ServerManager.getConnectedPlayers(ServerManager.unexus3.getId())).hideItemFlag().toItemStack();
        UNEXUS4 = new ItemBuilder(Material.BANNER).setBannerColor(DyeColor.YELLOW).addBannerPattern(DyeColor.BLACK, PatternType.STRIPE_TOP).addBannerPattern(DyeColor.YELLOW, PatternType.RHOMBUS_MIDDLE).addBannerPattern(DyeColor.BLACK, PatternType.STRIPE_DOWNLEFT).addBannerPattern(DyeColor.YELLOW, PatternType.CURLY_BORDER).addBannerPattern(DyeColor.BLACK, PatternType.STRIPE_BOTTOM).addBannerPattern(DyeColor.YELLOW, PatternType.BORDER).setName("§9U-Nexus - 2v2").setLore("§a", "§3Nb de joueurs: §b" + ServerManager.getConnectedPlayers(ServerManager.unexus4.getId())).hideItemFlag().toItemStack();
        UNEXUS5 = new ItemBuilder(Material.BANNER).setBannerColor(DyeColor.YELLOW).addBannerPattern(DyeColor.BLACK, PatternType.STRIPE_TOP).addBannerPattern(DyeColor.YELLOW, PatternType.RHOMBUS_MIDDLE).addBannerPattern(DyeColor.BLACK, PatternType.STRIPE_DOWNLEFT).addBannerPattern(DyeColor.YELLOW, PatternType.CURLY_BORDER).addBannerPattern(DyeColor.BLACK, PatternType.STRIPE_BOTTOM).addBannerPattern(DyeColor.YELLOW, PatternType.BORDER).setName("§9U-Nexus - 2v2").setLore("§a", "§3Nb de joueurs: §b" + ServerManager.getConnectedPlayers(ServerManager.unexus5.getId())).hideItemFlag().toItemStack();

        RUNORKILL1 = new ItemBuilder(Material.SNOW_BALL).setName("§9RK Partie #1").setLore("§a", "§3Nb de joueurs: §b" + ServerManager.getConnectedPlayers(ServerManager.runorkill1.getId())).hideItemFlag().toItemStack();
        RUNORKILL2 = new ItemBuilder(Material.SNOW_BALL).setName("§9RK Partie #2").setLore("§a", "§3Nb de joueurs: §b" + ServerManager.getConnectedPlayers(ServerManager.runorkill2.getId())).hideItemFlag().toItemStack();
        RUNORKILL3 = new ItemBuilder(Material.SNOW_BALL).setName("§9RK Partie #3").setLore("§a", "§3Nb de joueurs: §b" + ServerManager.getConnectedPlayers(ServerManager.runorkill3.getId())).hideItemFlag().toItemStack();
        RUNORKILL4 = new ItemBuilder(Material.SNOW_BALL).setName("§9RK Partie #4").setLore("§a", "§3Nb de joueurs: §b" + ServerManager.getConnectedPlayers(ServerManager.runorkill4.getId())).hideItemFlag().toItemStack();
        RUNORKILL5 = new ItemBuilder(Material.SNOW_BALL).setName("§9RK Partie #5").setLore("§a", "§3Nb de joueurs: §b" + ServerManager.getConnectedPlayers(ServerManager.runorkill5.getId())).hideItemFlag().toItemStack();

        RUSH1 = new ItemBuilder(Material.BANNER, 1, 10).setBannerColor(DyeColor.RED).addBannerPattern(DyeColor.BLACK, PatternType.STRIPE_TOP).addBannerPattern(DyeColor.RED, PatternType.RHOMBUS_MIDDLE).addBannerPattern(DyeColor.BLACK, PatternType.STRIPE_DOWNLEFT).addBannerPattern(DyeColor.RED, PatternType.CURLY_BORDER).addBannerPattern(DyeColor.BLACK, PatternType.STRIPE_BOTTOM).addBannerPattern(DyeColor.RED, PatternType.BORDER).setName(ServerManager.rush1.getPrefix3()).setLore("§a", "§3Nb de joueurs: §b" + ServerManager.getConnectedPlayers(ServerManager.rush1.getId())).hideItemFlag().toItemStack();
        RUSH2 = new ItemBuilder(Material.BANNER, 1, 10).setBannerColor(DyeColor.RED).addBannerPattern(DyeColor.BLACK, PatternType.STRIPE_TOP).addBannerPattern(DyeColor.RED, PatternType.RHOMBUS_MIDDLE).addBannerPattern(DyeColor.BLACK, PatternType.STRIPE_DOWNLEFT).addBannerPattern(DyeColor.RED, PatternType.CURLY_BORDER).addBannerPattern(DyeColor.BLACK, PatternType.STRIPE_BOTTOM).addBannerPattern(DyeColor.RED, PatternType.BORDER).setName(ServerManager.rush2.getPrefix3()).setLore("§a", "§3Nb de joueurs: §b" + ServerManager.getConnectedPlayers(ServerManager.rush2.getId())).hideItemFlag().toItemStack();
        RUSH3 = new ItemBuilder(Material.BANNER, 1, 10).setBannerColor(DyeColor.RED).addBannerPattern(DyeColor.BLACK, PatternType.STRIPE_TOP).addBannerPattern(DyeColor.RED, PatternType.RHOMBUS_MIDDLE).addBannerPattern(DyeColor.BLACK, PatternType.STRIPE_DOWNLEFT).addBannerPattern(DyeColor.RED, PatternType.CURLY_BORDER).addBannerPattern(DyeColor.BLACK, PatternType.STRIPE_BOTTOM).addBannerPattern(DyeColor.RED, PatternType.BORDER).setName(ServerManager.rush3.getPrefix3()).setLore("§a", "§3Nb de joueurs: §b" + ServerManager.getConnectedPlayers(ServerManager.rush3.getId())).hideItemFlag().toItemStack();
        RUSH4 = new ItemBuilder(Material.BANNER, 1, 10).setBannerColor(DyeColor.RED).addBannerPattern(DyeColor.BLACK, PatternType.STRIPE_TOP).addBannerPattern(DyeColor.RED, PatternType.RHOMBUS_MIDDLE).addBannerPattern(DyeColor.BLACK, PatternType.STRIPE_DOWNLEFT).addBannerPattern(DyeColor.RED, PatternType.CURLY_BORDER).addBannerPattern(DyeColor.BLACK, PatternType.STRIPE_BOTTOM).addBannerPattern(DyeColor.RED, PatternType.BORDER).setName(ServerManager.rush4.getPrefix3()).setLore("§a", "§3Nb de joueurs: §b" + ServerManager.getConnectedPlayers(ServerManager.rush4.getId())).hideItemFlag().toItemStack();
        RUSH5 = new ItemBuilder(Material.BANNER, 1, 10).setBannerColor(DyeColor.RED).addBannerPattern(DyeColor.BLACK, PatternType.STRIPE_TOP).addBannerPattern(DyeColor.RED, PatternType.RHOMBUS_MIDDLE).addBannerPattern(DyeColor.BLACK, PatternType.STRIPE_DOWNLEFT).addBannerPattern(DyeColor.RED, PatternType.CURLY_BORDER).addBannerPattern(DyeColor.BLACK, PatternType.STRIPE_BOTTOM).addBannerPattern(DyeColor.RED, PatternType.BORDER).setName(ServerManager.rush5.getPrefix3()).setLore("§a", "§3Nb de joueurs: §b" + ServerManager.getConnectedPlayers(ServerManager.rush5.getId())).hideItemFlag().toItemStack();

        JUMPADVENTURE1 = new ItemBuilder(Material.SANDSTONE).setName("§eTemple de la mort").setLore("§7Type: Jump/Aventure", "§7(Jumps, pièges, labyrinthes, énigmes, ...)", "§e", "§7Difficulté: §cDifficile", "§7Durée: §cLongue", "§a", "§7Récompenses:", "§7- §e800⭐ §fau total", "§3Nb de joueurs: §f" + ServerManager.getConnectedPlayers(ServerManager.jumpaddv1.getId())).toItemStack();
        JUMPADVENTURE2 = new ItemBuilder(Material.IRON_BLOCK).setName("§fLe laboratoire").setLore("§7Type: Jump/Aventure", "§7(Escape game: jumps, énigmes, ...)", "§e", "§7Difficulté: §eMoyenne", "§7Durée: §eMoyenne", "§a", "§7Récompenses:", "§7- §e200⭐", "§3Nb de joueurs: §f" + ServerManager.getConnectedPlayers(ServerManager.jumpaddv2.getId())).toItemStack();
        JUMPADVENTURE3 = new ItemBuilder(Material.STONE, 1, 6).setName("§9The platform").setLore("§7Type: Jump/Aventure", "§7(Jumps)", "§e", "§7Difficulté: §aTrès facile §fà §4Très difficile", "§7Durée: §4Très longue", "§a", "§7Récompenses:", "§7- §e750⭐ §fau total", "§3Nb de joueurs: §f" + ServerManager.getConnectedPlayers(ServerManager.jumpaddv3.getId())).toItemStack();

        PIXELWAR = new ItemBuilder(Material.WOOL).setName(ServerManager.pixelwar.getPrefix3()).setLore("§a", "§3Nb de joueurs: §b" + ServerManager.getConnectedPlayers(ServerManager.pixelwar.getId())).hideItemFlag().toItemStack();
    }

}

