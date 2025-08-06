package net.lonia.gui.runnable;

import net.lonia.core.item.builder.ItemBuilder;
import net.lonia.core.server.ServerManager;
import net.lonia.core.server.type.ServerType;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

public class ItemsRunnable extends BukkitRunnable {

    public static ItemStack LOBBY;
    public static ItemStack PVP;
    public static ItemStack Freecube;
    public static ItemStack Nexus;
    public static ItemStack RunOrKill;
    public static ItemStack Rush;
    public static ItemStack JumpAdventure;
    public static ItemStack PixelWar;


    @Override
    public void run() {
        LOBBY = new ItemBuilder(Material.BEACON).setName("§3§lLobby").setLore("§7Type: Lobby", "§a", "§3Nb de joueurs: §b" + ServerManager.getConnectedPlayersByType(ServerType.DUEL)).toItemStack();
        PVP = new ItemBuilder(Material.IRON_SWORD).setName("§b§lArène PVP / Duel").setLore("§7Type: PVP", "§a", "§3Nb de joueurs: §b" + ServerManager.getConnectedPlayersByType(ServerType.DUEL)).toItemStack();
        Freecube = new ItemBuilder(Material.GRASS).setName("§a§lFreecube").setLore("§7Type: Build", "§a", "§3Nb de joueurs: §b" + ServerManager.getConnectedPlayersByType(ServerType.FREECUBE)).toItemStack();
        Nexus = new ItemBuilder(Material.BLAZE_ROD).setName("§e§lNexus").setLore("§7Type: PVP/Technique", "§a", "§3Nb de joueurs: §b" + ServerManager.getConnectedPlayersByType(ServerType.UNEXUS)).toItemStack();
        Rush = new ItemBuilder(Material.BED).setName("§c§lRush").setLore("§7Type: PVP/Technique", "§a", "§3Nb de joueurs: §b" + ServerManager.getConnectedPlayersByType(ServerType.RUSH)).toItemStack();
        JumpAdventure = new ItemBuilder(Material.LADDER).setName("§2§lJump/Aventure").setLore("§7Type: Jump/Technique", "§a", "§3Nb de joueurs: §b" + ServerManager.getConnectedPlayersByType(ServerType.JUMPADVENTURE)).toItemStack();
        PixelWar = new ItemBuilder(Material.WOOL).setName("§f§lPixel War").setLore("§7Type: Build", "§a", "§3Nb de joueurs: §b" + ServerManager.getConnectedPlayersByType(ServerType.PIXELWAR)).toItemStack();
    }
}
