package net.lonia.moderation.commands.staff.modo;

import net.lonia.core.LoniaCore;
import net.lonia.core.commands.AbstractCommand;
import net.lonia.core.message.MessageList;
import net.lonia.core.rank.PermissionLevel;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class TrollCheaterCMD extends AbstractCommand {

    final String sub[] = new String[] {"sp", "badtrip", "bt", "speed", "lightning", "ln", "levitation", "lev"};

    @Override
    protected boolean onCommand(CommandSender sender, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(MessageList.commandNotAccessibleHere());
            return false;
        }

        player = (Player) sender;
        account = accountManager.getAccount(player);

        if (!account.hasPermissionLevel(PermissionLevel.MODERATEUR)) return true;
        if (args.length >= 2) {
            final String sub = args[0].toLowerCase();
            final Player target = getPlayer(args[1]);

            if (target == null) {
                player.sendMessage(MessageList.playerNotConnected());
                return true;
            }

            if (isSub(sub)) {
                switch (sub) {
                    case "sp":
                        target.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 30 * 20, 5));
                        player.sendMessage("§9[Mod.] §bVous avez trollé §f" + target.getName() + "§b en lui appliquant un effet de lenteur et de poison.");
                        break;
                    case "badtrip":
                    case "bt":
                        target.addPotionEffect(new PotionEffect(PotionEffectType.CONFUSION, 30 * 20, 5));
                        break;
                    case "speed":
                        target.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 30 * 20, 5));
                        player.sendMessage("§9[Mod.] §bVous avez trollé §f" + target.getName() + "§b en lui appliquant un effet de rapidité.");
                        break;
                    case "lightning":
                    case "ln":
                        spawnLightning(target.getWorld(), target.getLocation().getX(), target.getLocation().getY(), target.getLocation().getZ());
                        break;
                    case "levitation":
                    case "lev":
                        int duration = 5;
                        final double velocity = 1.0;
                        target.setVelocity(target.getVelocity().setY(velocity));
                        Bukkit.getScheduler().runTaskLater(LoniaCore.get(), new Runnable() {
                            @Override
                            public void run() {
                                target.setVelocity(target.getVelocity().setY(0));
                            }
                        }, duration * 50L);
                        player.sendMessage("§9[Mod.] §bVous avez trollé §f" + target.getName() + "§b en lui appliquant un effet de lévitation.");
                        break;
                    default:
                        break;
                }
            }
        }
        else {
            player.sendMessage("");
        }


        return true;
    }

    boolean isSub(String sub) {
        return Arrays.stream(this.sub).anyMatch(s -> s.equalsIgnoreCase(sub));
    }

    void spawnLightning(World world, double x, double y, double z) {
        if (world == null) {
            throw new IllegalArgumentException("World cannot be null!");
        }

        Location location = new Location(world, x, y, z);
        world.strikeLightning(location);
    }

    @Override
    protected List<String> onTabComplete(CommandSender sender, String label, String[] args) {
        return Collections.emptyList();
    }
}
