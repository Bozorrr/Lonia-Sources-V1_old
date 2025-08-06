package net.lonia.gui.gui;

import net.lonia.core.utils.GuiBuilder;
import net.lonia.gui.LoniaGui;
import net.lonia.gui.gui.main.*;
import net.lonia.gui.gui.main.settings.SettingsMenu;
import net.lonia.gui.gui.main.shop.ShopMenu;
import net.lonia.gui.gui.main.shop.rank.heros.ConfirmationPaymentHeros;
import net.lonia.gui.gui.main.shop.rank.heros.PaymentHerosMenu;
import net.lonia.gui.gui.main.shop.rank.legend.ConfirmationPaymentLegend;
import net.lonia.gui.gui.main.shop.rank.legend.PaymentLegendMenu;
import net.lonia.gui.gui.main.shop.rank.minivip.ConfirmationPaymentMiniVIP;
import net.lonia.gui.gui.main.shop.rank.minivip.PaymentMiniVIPMenu;
import net.lonia.gui.gui.main.shop.rank.vip.ConfirmationPaymentVIP;
import net.lonia.gui.gui.main.shop.rank.vip.PaymentVIPMenu;
import net.lonia.gui.gui.main.shop.rank.vipp.ConfirmationPaymentVIPP;
import net.lonia.gui.gui.main.shop.rank.vipp.PaymentVIPPMenu;
import net.lonia.gui.gui.main.stats.ResetState;
import net.lonia.gui.gui.main.stats.StatsMenu;
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
        LoniaGui.getInstance().getGuiManager().getRegisteredMenus().values().stream().filter(menu -> invView.getTitle().equalsIgnoreCase(menu.name())).forEach(menu -> {
            menu.onClick(player, inv, invView, current, event.getSlot());
            event.setCancelled(true);
        });
    }

    public void addMenu(GuiBuilder m) {
        LoniaGui.getInstance().getGuiManager().getRegisteredMenus().put(m.getClass(), m);
    }

    public void open(final Player player, Class<? extends GuiBuilder> gClass) {
        if (!LoniaGui.getInstance().getGuiManager().getRegisteredMenus().containsKey(gClass)) return;
        final GuiBuilder menu = LoniaGui.getInstance().getGuiManager().getRegisteredMenus().get(gClass);
        final Inventory inv = Bukkit.createInventory(null, menu.getSize(), menu.name());

        menu.contents(player, inv);

        new BukkitRunnable() {
            public void run() {
                player.openInventory(inv);
            }
        }.runTaskLater(LoniaGui.getInstance(), 1L);
    }

    public void loadGui() {
        this.guiManager = new GuiManager();

        Bukkit.getPluginManager().registerEvents(this.guiManager, LoniaGui.getInstance());

        this.registeredMenus = new HashMap<>();

        /**
         * Main menu
         */
        this.guiManager.addMenu(new MainMenu());
        {
            /**
             * Shop
             */

            this.guiManager.addMenu(new ShopMenu());
            {
                {
                    this.guiManager.addMenu(new PaymentMiniVIPMenu());
                    this.guiManager.addMenu(new ConfirmationPaymentMiniVIP());
                }
                {
                    this.guiManager.addMenu(new PaymentVIPMenu());
                    this.guiManager.addMenu(new ConfirmationPaymentVIP());
                }
                {
                    this.guiManager.addMenu(new PaymentVIPPMenu());
                    this.guiManager.addMenu(new ConfirmationPaymentVIPP());

                }
                {
                    this.guiManager.addMenu(new PaymentHerosMenu());
                    this.guiManager.addMenu(new ConfirmationPaymentHeros());
                }
                {
                    this.guiManager.addMenu(new PaymentLegendMenu());
                    this.guiManager.addMenu(new ConfirmationPaymentLegend());
                }
            }
            /**
             * Settings
             */
            this.guiManager.addMenu(new SettingsMenu());

            /**
             * Stats
             */
            this.guiManager.addMenu(new StatsMenu());
            this.guiManager.addMenu(new ResetState());

            this.guiManager.addMenu(new LobbyMenu());
            this.guiManager.addMenu(new DUELMenu());
            this.guiManager.addMenu(new FreecubeMenu());
            this.guiManager.addMenu(new UNexusMenu());
            this.guiManager.addMenu(new RunOrKillMenu());
            this.guiManager.addMenu(new RushMenu());
            this.guiManager.addMenu(new JumpAdventureMenu());
            this.guiManager.addMenu(new PixelWarMenu());

        }
    }

    public Map<Class<? extends GuiBuilder>, GuiBuilder> getRegisteredMenus() {
        return registeredMenus;
    }

    public GuiManager getGuiManager() {
        return guiManager;
    }
}
