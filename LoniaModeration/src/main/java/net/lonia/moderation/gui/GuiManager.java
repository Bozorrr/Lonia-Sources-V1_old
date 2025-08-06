package net.lonia.moderation.gui;

import net.lonia.core.utils.GuiBuilder;
import net.lonia.moderation.LoniaModeration;
import net.lonia.moderation.gui.player.ConfirmChatReport;
import net.lonia.moderation.gui.player.ReportGUI;
import net.lonia.moderation.gui.staff.admin.AdminMenu;
import net.lonia.moderation.gui.staff.admin.moderation.AdminModerationGui;
import net.lonia.moderation.gui.staff.admin.moderation.sub.*;
import net.lonia.moderation.gui.staff.admin.server.servers.ServersPanelEvent;
import net.lonia.moderation.gui.staff.admin.server.servers.sub.ServersSubPanelEvent;
import net.lonia.moderation.gui.staff.admin.server.servers.sub.server.ServersBuildPanelEvent;
import net.lonia.moderation.gui.staff.admin.server.servers.sub.server.ServersHubPanelEvent;
import net.lonia.moderation.gui.staff.admin.server.setupserv.SetupServerPanelEvent;
import net.lonia.moderation.gui.staff.admin.server.setupserv.sub.SetupServerSubPanelEvent;
import net.lonia.moderation.gui.staff.moderator.moderation.ModerationGui;
import net.lonia.moderation.gui.staff.moderator.moderation.sub.*;
import net.lonia.moderation.gui.staff.moderator.moderation.sub.sub.SubMessageSanctionGui;
import net.lonia.moderation.gui.staff.responsable.UnBanGUI;
import net.lonia.moderation.gui.staff.responsable.UnMuteGUI;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryView;
import org.bukkit.inventory.ItemStack;

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
        LoniaModeration.getInstance().getGuiManager().getRegisteredMenus().values().stream().filter(menu -> invView.getTitle().equalsIgnoreCase(menu.name())).forEach(menu -> {
            menu.onClick(player, inv, invView, current, event.getSlot());
            event.setCancelled(true);
        });
    }

    public void addMenu(GuiBuilder m) {
        LoniaModeration.getInstance().getGuiManager().getRegisteredMenus().put(m.getClass(), m);
    }

    public void open(final Player player, Class<? extends GuiBuilder> gClass) {
        if (!LoniaModeration.getInstance().getGuiManager().getRegisteredMenus().containsKey(gClass)) return;
        final GuiBuilder menu = LoniaModeration.getInstance().getGuiManager().getRegisteredMenus().get(gClass);
        final Inventory inv = Bukkit.createInventory(null, menu.getSize(), menu.name());

        menu.contents(player, inv);

        Bukkit.getScheduler().runTaskLater(LoniaModeration.getInstance(), () -> player.openInventory(inv), 1L);
    }

    public void loadGui() {
        this.guiManager = new GuiManager();

        Bukkit.getPluginManager().registerEvents(this.guiManager, LoniaModeration.getInstance());

        this.registeredMenus = new HashMap<>();

        /**
         * AdminMenu
         */
        this.guiManager.addMenu(new AdminMenu());
        {

            /**
             * Admin gui
             */
            {
                /**
                 * Mod gui
                 */
                {
                    this.guiManager.addMenu(new AdminModerationGui());
                    {
                        this.guiManager.addMenu(new AdminMessageSanctionGui());
                        this.guiManager.addMenu(new AdminProfileSanctionGui());
                        this.guiManager.addMenu(new AdminGamePlaySanctionGui());
                        this.guiManager.addMenu(new AdminCheatSanctionGui());
                        this.guiManager.addMenu(new AdminOtherSanctionGui());
                    }
                }

                /**
                 * Servers panel
                 */
                this.guiManager.addMenu(new ServersPanelEvent());
                this.guiManager.addMenu(new ServersSubPanelEvent());
                {
                    this.guiManager.addMenu(new ServersHubPanelEvent());
                    this.guiManager.addMenu(new ServersBuildPanelEvent());
                }

                /**
                 * Setup panel
                 */
                {
                    this.guiManager.addMenu(new SetupServerPanelEvent());
                    this.guiManager.addMenu(new SetupServerSubPanelEvent());
                }
            }

            /**
             * MOd gui
             */
            this.guiManager.addMenu(new ModerationGui());

            this.guiManager.addMenu(new CheatSanctionGui());
            this.guiManager.addMenu(new GamePlaySanctionGui());
            this.guiManager.addMenu(new MessageSanctionGui());
            this.guiManager.addMenu(new OtherSanctionGui());
            this.guiManager.addMenu(new ProfileSanctionGui());

            this.guiManager.addMenu(new SubMessageSanctionGui());

            this.guiManager.addMenu(new UnBanGUI());
            this.guiManager.addMenu(new UnMuteGUI());
        }

        this.guiManager.addMenu(new ReportGUI());

        this.guiManager.addMenu(new ConfirmChatReport());
    }

    public Map<Class<? extends GuiBuilder>, GuiBuilder> getRegisteredMenus() {
        return registeredMenus;
    }

    public GuiManager getGuiManager() {
        return guiManager;
    }
}
