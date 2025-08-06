package net.lonia.moderation.commands.staff.modo;

import net.lonia.core.commands.AbstractCommand;
import net.lonia.core.message.MessageList;
import net.lonia.core.player.staff.StaffManager;
import net.lonia.core.rank.PermissionLevel;
import net.lonia.moderation.LoniaModeration;
import net.lonia.moderation.manager.PlayerManager;
import org.bukkit.GameMode;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.List;

public class ModCMD extends AbstractCommand {

    @Override
    protected boolean onCommand(CommandSender sender, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(MessageList.commandNotAccessibleHere());
            return false;
        }

        player = (Player) sender;
        account = accountManager.getAccount(player);

        if (!account.hasPermissionLevel(PermissionLevel.MODERATEUR)) return true;

        if (LoniaModeration.getInstance().getModerators().contains(player.getUniqueId())) {
            LoniaModeration.getInstance().getModerators().remove(player.getUniqueId());

            PlayerManager.unvanish(player.getUniqueId());
            StaffManager.removeInMod(player.getUniqueId());
            final PlayerManager pm = PlayerManager.getPlayerManager(player);

            player.sendMessage("§9[Staff] §3Vous n'êtes plus en mode modération.");
            player.setGameMode(GameMode.SURVIVAL);
            player.setAllowFlight(false);
            player.setFlying(false);
            pm.getInventory();
            pm.destroy();

            return true;
        }

        PlayerManager pm = PlayerManager.getPlayerManager(player);
        if (pm == null) {
            pm = new PlayerManager(player);
            pm.init();
        }

        LoniaModeration.getInstance().getModerators().add(player.getUniqueId());
        StaffManager.setInMod(player.getUniqueId());
        PlayerManager.vanish(player.getUniqueId());


        player.sendMessage("§9[Staff] §bVous êtes en mode modération.");
        player.setGameMode(GameMode.SURVIVAL);
        player.setAllowFlight(true);
        player.setFlying(true);

        pm.saveInventory();
        return true;
    }

    @Override
    protected List<String> onTabComplete(CommandSender sender, String label, String[] args) {
        return null;
    }
}
