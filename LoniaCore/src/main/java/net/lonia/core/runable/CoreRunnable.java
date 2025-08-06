package net.lonia.core.runable;

import net.lonia.core.LoniaCore;
import net.lonia.core.player.account.Account;
import org.bukkit.scheduler.BukkitRunnable;

public class CoreRunnable extends BukkitRunnable {

    @Override
    public void run() {
        LoniaCore.get().getServerAccount().updateData();

        LoniaCore.get().getAccountManager().getAccounts().forEach(Account::updateData);
    }
}
