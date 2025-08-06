package net.lonia.rush.commands;

import io.netty.buffer.Unpooled;
import net.lonia.core.commands.AbstractCommand;
import net.lonia.core.message.MessageList;
import net.lonia.core.rank.PermissionLevel;
import net.lonia.rush.messages.MessageManager;
import net.minecraft.server.v1_8_R3.EntityPlayer;
import net.minecraft.server.v1_8_R3.PacketDataSerializer;
import net.minecraft.server.v1_8_R3.PacketPlayOutCustomPayload;
import net.minecraft.server.v1_8_R3.PlayerConnection;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.craftbukkit.v1_8_R3.inventory.CraftItemStack;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BookMeta;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.util.Collections;
import java.util.List;

public class ButCMD extends AbstractCommand {
    @Override
    protected boolean onCommand(CommandSender sender, String label, String[] args) {

        if (!(sender instanceof Player)) {
            sender.sendMessage(MessageList.commandNotAccessibleHere());
            return false;
        }

        player = (Player) sender;
        account = accountManager.getAccount(player);

        if (!account.hasPermissionLevel(PermissionLevel.JOUEUR)) return true;

        player.sendMessage(MessageManager.butMessage);

        return false;
    }

    @Override
    protected List<String> onTabComplete(CommandSender sender, String label, String[] args) {
        return Collections.emptyList();
    }
}