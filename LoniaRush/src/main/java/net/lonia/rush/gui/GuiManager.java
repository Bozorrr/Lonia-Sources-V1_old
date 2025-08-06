package net.lonia.rush.gui;

import net.lonia.core.utils.GuiBuilder;
import net.lonia.rush.LoniaRush;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryView;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.HashMap;
import java.util.Map;

public class GuiManager implements Listener {

    private GuiManager guiManager;
    private Map<Class<? extends GuiBuilder>, GuiBuilder> registeredMenus;

    @EventHandler
    public void onClick(InventoryClickEvent event) {

        Player player = (Player) event.getWhoClicked();
        Inventory inv = event.getInventory();
        InventoryView invView = event.getView();
        ItemStack current = event.getCurrentItem();

        if (event.getCurrentItem() == null) return;
        LoniaRush.INSTANCE.getGuiManager().getRegisteredMenus().values().stream().filter(menu -> invView.getTitle().equalsIgnoreCase(menu.name())).forEach(menu -> {
            menu.onClick(player, inv, invView, current, event.getSlot());
            event.setCancelled(true);
        });
    }

    public void addMenu(GuiBuilder m) {
        LoniaRush.INSTANCE.getGuiManager().getRegisteredMenus().put(m.getClass(), m);
    }

    public void open(final Player player, Class<? extends GuiBuilder> gClass) {
        if (!LoniaRush.INSTANCE.getGuiManager().getRegisteredMenus().containsKey(gClass)) return;
        final GuiBuilder menu = LoniaRush.INSTANCE.getGuiManager().getRegisteredMenus().get(gClass);
        final Inventory inv = Bukkit.createInventory(null, menu.getSize(), menu.name());

        menu.contents(player, inv);

        new BukkitRunnable() {
            public void run() {
                player.openInventory(inv);
            }
        }.runTaskLater(LoniaRush.INSTANCE, 1L);
    }

    public void loadGui() {
        this.guiManager = new GuiManager();

        Bukkit.getPluginManager().registerEvents(this.guiManager, LoniaRush.INSTANCE);

        this.registeredMenus = new HashMap<>();

        this.guiManager.addMenu(new TeamGUI());

        this.guiManager.addMenu(new RushSetupGUI());
    }

    public Map<Class<? extends GuiBuilder>, GuiBuilder> getRegisteredMenus() {
        return registeredMenus;
    }

    public GuiManager getGuiManager() {
        return guiManager;
    }
}
