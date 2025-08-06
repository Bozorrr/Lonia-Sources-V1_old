package net.lonia.moderation.gui.player;

import net.lonia.core.LoniaCore;
import net.lonia.core.item.builder.ItemBuilder;
import net.lonia.core.item.manager.ItemManager;
import net.lonia.core.player.account.Account;
import net.lonia.core.utils.GuiBuilder;
import net.lonia.moderation.LoniaModeration;
import net.lonia.moderation.gui.GuiManager;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryView;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class ReportGUI implements GuiBuilder {

    private final ItemStack BOW = new ItemBuilder(Material.BOW).setName("§cReport").toItemStack();

    private final ItemStack invalidName = new ItemBuilder(Material.NAME_TAG).setName("§6Pseudonyme incorrect").setLore("§f- Insultant, vulgère ou de haine", "§f- Usurpant, provocateur, sujet sensible").toItemStack();
    private final ItemStack invalidSkin = new ItemBuilder(Material.LEATHER_CHESTPLATE).setName("§6Skin/Cape incorrect(e)").setLore("§f- Haine, insulte ou nudité", "§4§l⚠ §cLes skins/capes clignotant(e)s sont autorisé(e)s").toItemStack();
    private final ItemStack invalidStatus = new ItemBuilder(Material.ANVIL).setName("§6Statut incorrect").setLore("§f- Nom incorrect d'animal ou d'avantage incorrect", "§f- Freecube: nom de bloc/zone incorrect(e)").toItemStack();
    private final ItemStack CPS = new ItemBuilder(Material.SANDSTONE, 1, 2).setName("§6CPS trop excessifs").setLore("§f- CPS supérieurs à 15 (clic gauche/droit)").toItemStack();
    private final ItemStack cheat = new ItemBuilder(Material.DIAMOND_SWORD).setName("§6Triche").setLore("§f- Utilise un client de triche").toItemStack();
    private final ItemStack boostStat = new ItemBuilder(Material.COMPASS).setName("§6Boost de statistiques").setLore("§f- Multi-comptes pour augmenter ses statistiques").toItemStack();
    private final ItemStack antiGame = new ItemBuilder(Material.TNT).setName("§6Anti-jeu").setLore("§f- Ne joue pas le but du jeu", "§f- Empêche ses coéquipiers de jouer", "§f- Spawn-kill").toItemStack();
    private final ItemStack crossPlay = new ItemBuilder(Material.CHAINMAIL_CHESTPLATE).setName("§6Alliance interdite").setLore("§f- Dans un jeu où les alliances sont interdites").toItemStack();
    private final ItemStack co = new ItemBuilder(Material.WEB).setName("§6Connexion dérengeante").setLore("§f- Connexion très lente qui dérange les autres joueurs").toItemStack();
    private final ItemStack invalidBuild = new ItemBuilder(Material.GRASS).setName("§6Construction incorrecte").setLore("§f- Haine, nudité, insulte", "§4§l⚠ §cLes zizis simples faits avec 4 blocs sont tolérés").toItemStack();
    private final ItemStack grief = new ItemBuilder(Material.LAVA_BUCKET).setName("§6Grief en freecube").setLore("§f- Détruit la zone d'un autre joueur", "§4§l⚠ §cSeulement si le joueur est en train de le faire").toItemStack();
    private final ItemStack invalidMessage = new ItemBuilder(Material.PAPER).setName("§6Message incorrect").setLore("§c- Utilisez le symbole §c§l⚠ §cdans le chat", "§f- Insulte, haine, provocation, spam, vente, etc...").toItemStack();

    @Override
    public String name() {
        return "§c§lSignaler un joueur";
    }

    @Override
    public int getSize() {
        return 54;
    }

    @Override
    public void contents(Player player, Inventory inv) {
        inv.setItem(0, BOW);

        inv.setItem(12, invalidName);
        inv.setItem(13, invalidSkin);
        inv.setItem(14, invalidStatus);

        inv.setItem(20, CPS);
        inv.setItem(21, cheat);
        inv.setItem(22, boostStat);
        inv.setItem(23, antiGame);
        inv.setItem(24, crossPlay);

        inv.setItem(30, co);
        inv.setItem(31, invalidBuild);
        inv.setItem(32, grief);

        inv.setItem(40, invalidMessage);

        inv.setItem(53, ItemManager.CLOSE);

    }

    @Override
    public void onClick(Player player, Inventory inv, InventoryView invView, ItemStack current, int slot) {
        if (!invView.getTitle().equals(name()))
            return;

        if (!current.hasItemMeta())
            return;
        final ItemMeta im = current.getItemMeta();

        if (!im.hasDisplayName())
            return;
        final String displayName = im.getDisplayName();
        final GuiManager guiManager = LoniaModeration.getInstance().getGuiManager();

        final Account account = LoniaCore.get().getAccountManager().getAccount(player);

        if (displayName.equals(invalidName.getItemMeta().getDisplayName())) {
//            ReportManager.addReport();
        } else if (displayName.equals(ItemManager.CLOSE.getItemMeta().getDisplayName())) {
            player.closeInventory();
        }
    }
}
