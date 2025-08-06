package net.lonia.core.player.account;

import net.lonia.core.server.account.ServerAccount;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class AccountManager {

    private final List<Account> accounts = new ArrayList<>();

    private final List<ServerAccount> serverAccounts = new ArrayList<>();

    public List<Account> getAccounts() {
        return this.accounts;
    }

    public Account getAccount(Player player) {
        return getAccount(player.getUniqueId());
    }

    public Account getAccount(UUID uuid) {
        Optional<Account> account = new ArrayList<>(accounts).stream().filter(a -> a.getUUID().equals(uuid.toString())).findFirst();
        return account.orElse(null);
    }

    public List<ServerAccount> getServerAccounts() {
        return this.serverAccounts;
    }
}
