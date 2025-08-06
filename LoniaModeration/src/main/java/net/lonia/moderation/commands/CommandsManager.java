package net.lonia.moderation.commands;

import net.lonia.core.commands.SimpleCommand;
import net.lonia.moderation.commands.player.FlyCMD;
import net.lonia.moderation.commands.player.MuteHelpCMD;
import net.lonia.moderation.commands.player.ReportCMD;
import net.lonia.moderation.commands.player.ReportMessageCMD;
import net.lonia.moderation.commands.staff.modo.*;
import net.lonia.moderation.commands.staff.responsable.player.ArgentCMD;
import net.lonia.moderation.commands.staff.responsable.player.EtoileCMD;
import net.lonia.moderation.commands.staff.responsable.player.HealCMD;
import net.lonia.moderation.commands.staff.responsable.player.SetRankCMD;
import net.lonia.moderation.commands.staff.responsable.sanctions.BanCMD;
import net.lonia.moderation.commands.staff.responsable.sanctions.MuteCMD;
import net.lonia.moderation.commands.staff.responsable.sanctions.UnBanCMD;
import net.lonia.moderation.commands.staff.responsable.sanctions.UnMuteCMD;
import net.lonia.moderation.commands.staff.responsable.server.ChatCMD;
import net.lonia.moderation.commands.staff.responsable.server.ServersCMD;
import net.lonia.moderation.commands.staff.responsable.server.SetSpawnCMD;
import net.lonia.moderation.commands.staff.responsable.server.SetupServerCMD;
import org.bukkit.Bukkit;
import org.bukkit.craftbukkit.v1_8_R3.CraftServer;

public class CommandsManager {

    public CommandsManager() {
        // Commandes de gestion du serveur
        registerCommand(new SimpleCommand("setup-server", ".", new SetupServerCMD(), new SetupServerCMD(), "setup"));
        registerCommand(new SimpleCommand("servers", ".", new ServersCMD(), new ServersCMD(), "serv"));
        registerCommand(new SimpleCommand("setrank", ".", new SetRankCMD(), new SetRankCMD(), "setr"));
        registerCommand(new SimpleCommand("setspawn", ".", new SetSpawnCMD(), new SetSpawnCMD()));
        registerCommand(new SimpleCommand("etoile", ".", new EtoileCMD(), new EtoileCMD()));
        registerCommand(new SimpleCommand("argent", ".", new ArgentCMD(), new ArgentCMD()));
        registerCommand(new SimpleCommand("argent", ".", new ArgentCMD(), new ArgentCMD()));
        registerCommand(new SimpleCommand("unban", ".", new UnBanCMD(), new UnBanCMD()));
        registerCommand(new SimpleCommand("unmute", ".", new UnMuteCMD(), new UnMuteCMD()));

        // Commandes de modération
        registerCommand(new SimpleCommand("ban", "", new BanCMD(), new BanCMD()));
        registerCommand(new SimpleCommand("mute", "", new MuteCMD(), new MuteCMD()));
        registerCommand(new SimpleCommand("kick", "", new KickCMD(), new KickCMD()));
        registerCommand(new SimpleCommand("chat", ".", new ChatCMD(), new ChatCMD()));
        registerCommand(new SimpleCommand("reportmessage", "", new ReportMessageCMD(), new ReportMessageCMD()));
        registerCommand(new SimpleCommand("check", "", new CheckCMD(), new CheckCMD()));
        registerCommand(new SimpleCommand("freeze", "", new FreezeCMD(), new FreezeCMD()));
        registerCommand(new SimpleCommand("killinstant ", "", new KillInstantCMD(), new KillInstantCMD(), "ki"));
        registerCommand(new SimpleCommand("tpa", "", new TPCMD(), new TPCMD()));
        registerCommand(new SimpleCommand("teleporthere", "", new TPHCMD(), new TPHCMD(), "tph"));
        registerCommand(new SimpleCommand("trollcheater", "", new TrollCheaterCMD(), new TrollCheaterCMD(), "trollc"));

        // Commandes du staff
        registerCommand(new SimpleCommand("moderation", "", new ModCMD(), new ModCMD(), "modération", "mod"));
        registerCommand(new SimpleCommand("stafflist", "", new StaffListCMD(), new StaffListCMD(), "sl"));
        registerCommand(new SimpleCommand("s", "", new SCMD(), new SCMD()));
        registerCommand(new SimpleCommand("fly", "", new FlyCMD(), new FlyCMD()));
        registerCommand(new SimpleCommand("gm", ".", new GameModeCMD(), new GameModeCMD()));
        registerCommand(new SimpleCommand("heal", ".", new HealCMD(), new HealCMD()));

        // Commandes joueur
        registerCommand(new SimpleCommand("mutehelp", ".", new MuteHelpCMD(), new MuteHelpCMD()));
        registerCommand(new SimpleCommand("report", ".", new ReportCMD(), new ReportCMD()));

    }


    private void registerCommand(SimpleCommand command) {
        CraftServer server = (CraftServer) Bukkit.getServer();
        server.getCommandMap().register(Bukkit.getName(), command);
    }
}

