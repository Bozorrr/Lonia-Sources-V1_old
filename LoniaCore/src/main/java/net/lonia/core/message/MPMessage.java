package net.lonia.core.message;

import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.chat.ComponentSerializer;

public class MPMessage {
    public static String messageReceivedFrom(String prefix, String player, String message) {
        TextComponent reportButton = new TextComponent("⚠");
        reportButton.setColor(net.md_5.bungee.api.ChatColor.RED);
        reportButton.setBold(true);
        reportButton.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/reportMessage " + player.replaceAll("§", "&") + " " + message.replaceAll("§", "&")));
        reportButton.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder("Cliquez pour signaler ce message").color(ChatColor.RED).create()));

        TextComponent mess = new TextComponent(ChatColor.DARK_AQUA + "Message reçu de " + prefix + player + "§3: §f" + message);

        TextComponent msg = new TextComponent("");
        msg.addExtra(reportButton);
        msg.addExtra(" ");
        msg.addExtra(mess);

        return ComponentSerializer.toString(msg);
    }

    public static String messageSendTo(String prefix, String player, String message) {
        TextComponent reportButton = new TextComponent("⚠");
        reportButton.setColor(net.md_5.bungee.api.ChatColor.RED);
        reportButton.setBold(true);
        reportButton.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/reportMessage " + player.replaceAll("§", "&") + " " + message.replaceAll("§", "&")));
        reportButton.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder("Cliquez pour signaler ce message").color(ChatColor.RED).create()));

        TextComponent mess = new TextComponent("§bMessage envoyé à " + prefix + player + "§b: §f" + message);

        TextComponent msg = new TextComponent("");
        msg.addExtra(reportButton);
        msg.addExtra(" ");
        msg.addExtra(mess);

        return ComponentSerializer.toString(msg);
    }

    public static String ignoreUsage() {
        return "§9[MP] §3Utilisation: §f/ignore <pseudo>";
    }

    public static String ignoreHimSelf() {
        return "§9[MP] §3Erreur: Vous ne pouvez pas vous ignorer.";
    }

    public static String unIgnored() {
        return "§9[MP] §bLe joueur n'est plus ignoré.";
    }

    public static String ignore() {
        return "§9[MP] §bLe joueur est maintenant ignoré jusqu'au prochain redémarage du serveur.";
    }

    public static String ignored() {
        return "§9[MP] §3Erreur: Ce joueur vous à ignoré.";
    }

    public static String isIgnored() {
        return "§9[MP] §3Erreur: Vous avez ignoré ce joueur.";
    }

    public static String needMPEnable() {
        return "§9[MP] §3Erreur: Vous devez activer vos MP afin d'en envoyer.";
    }

    public static String playerDisabledMP() {
        return "§9[MP] §3Erreur: Ce joueur à désactivé ses MP.";
    }

    public static String sendMPHimSelf() {
        return "§9[MP] §3Erreur: Vous ne pouvez pas vous envoyer de message.";
    }
}
