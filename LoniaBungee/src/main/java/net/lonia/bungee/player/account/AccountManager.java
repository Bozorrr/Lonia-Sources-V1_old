package net.lonia.bungee.player.account;

import net.md_5.bungee.api.connection.ProxiedPlayer;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class AccountManager {

    private final List<Account> accounts = new ArrayList<>();

    public List<Account> getAccounts() {
        return this.accounts;
    }

    public Account getAccount(ProxiedPlayer player) {
        Optional<Account> account = new ArrayList<>(accounts).stream().filter(a -> a.getUUID().equals(player.getUniqueId().toString())).findFirst();
        return account.orElse(null);
    }

    public Account getAccount(UUID uuid) {
        Optional<Account> account = new ArrayList<>(accounts).stream().filter(a -> a.getUUID().equals(uuid.toString())).findFirst();
        return account.orElse(null);
    }
}
