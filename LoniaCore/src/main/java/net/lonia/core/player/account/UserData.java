package net.lonia.core.player.account;

import net.lonia.core.rank.Rank;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class UserData extends AbstractData {

    private final DataMP dataMP;
    private final DataRank dataRank;
    private final DataEtoile dataEtoile;
    private final DataPearl dataPearl;
    private final DataSettings dataSettings;

    private String server;
    private int connected;
    private int isInGame;

    public UserData(UUID uuid) {
        this.uuid = uuid;
        this.name = Account.getName(uuid);

        this.dataMP = new DataMP();
        this.dataRank = new DataRank();
        this.dataEtoile = new DataEtoile();
        this.dataPearl = new DataPearl();
        this.dataSettings = new DataSettings();
    }

    public DataMP getDataMP() {
        return this.dataMP;
    }

    public DataRank getDataRank() {
        return this.dataRank;
    }

    public DataEtoile getDataEtoile() {
        return this.dataEtoile;
    }

    public DataPearl getDataPearl() {
        return this.dataPearl;
    }

    public DataSettings getDataSettings() {
        return this.dataSettings;
    }

    public String getServer() {
        return this.server;
    }

    public int getConnected() {
        return this.connected;
    }

    public int getIsInGame() {
        return this.isInGame;
    }

    public void setServer(String server) {
        this.server = server;
    }

    public void setConnected(int connected) {
        this.connected = connected;
    }

    public void setIsInGame(int isInGame) {
        this.isInGame = isInGame;
    }

    public boolean isConnected() {
        return this.connected == 1;
    }

    public boolean isInGame() {
        return this.isInGame == 1;
    }

    public class DataMP {
        private final List<String> ignoredPlayers = new ArrayList<>();

        public List<String> getIgnoredPlayers() {
            return this.ignoredPlayers;
        }

        public void addIgnoredPlayer(String name) {
            this.ignoredPlayers.add(name);
        }

        public void removeIgnoredPlayer(String name) {
            this.ignoredPlayers.remove(name);
        }
    }

    public class DataRank {
        private Rank grade;

        public void setRank(Rank rank) {
            this.grade = rank;
        }

        public Rank getRank() {
            return this.grade;
        }
    }

    public class DataEtoile {
        private long etoile;

        public void setEtoile(long etoile) {
            if (etoile < 0) {
                etoile = 0;
            }
            this.etoile = etoile;
        }

        public long getEtoile() {
            return this.etoile;
        }

        public void addEtoile(long etoile) {
            this.etoile += etoile;
        }

        public void removeEtoile(long etoile) {
            if (etoile > this.etoile) etoile = this.etoile;
            this.etoile -= etoile;
        }

        public boolean hasEtoile(long etoile) {
            if (etoile < 0) return false;
            return this.etoile >= etoile;
        }
    }

    public class DataPearl {
        private long pearl;

        public void setPearl(long pearl) {
            if (pearl < 0) pearl = 0;
            this.pearl = pearl;
        }

        public long getPearl() {
            return pearl;
        }

        public void addPearl(long pearl) {
            this.pearl += pearl;
        }

        public void removePearl(long pearl) {
            if (pearl > this.pearl) pearl = this.pearl;
            this.pearl -= pearl;
        }

        public boolean hasPearl(long pearl) {
            if (pearl < 0) return false;
            return this.pearl >= pearl;
        }
    }

    public class DataSettings {
        public int enabled_mp = 0;
        public int enabled_friends_request = 0;
        public int enabled_group_request = 0;
        public int enabled_following_group = 0;
        public int enabled_show_particle_effects = 0;
        public int enabled_show_all_sanctions = 0;
        public int enabled_show_ban = 0;

        public void setEnabled_mp(int enabled_mp) {
            this.enabled_mp = enabled_mp;
        }

        public void setEnabled_friends_request(int enabled_friends_request) {
            this.enabled_friends_request = enabled_friends_request;
        }

        public void setEnabled_group_request(int enabled_group_request) {
            this.enabled_group_request = enabled_group_request;
        }

        public void setEnabled_following_group(int enabled_following_group) {
            this.enabled_following_group = enabled_following_group;
        }

        public void setEnabled_show_particle_effects(int enabled_show_particle_effects) {
            this.enabled_show_particle_effects = enabled_show_particle_effects;
        }

        public void setEnabled_show_all_sanctions(int enabled_show_all_sanctions) {
            this.enabled_show_all_sanctions = enabled_show_all_sanctions;
        }

        public void setEnabled_show_ban(int enabled_show_ban) {
            this.enabled_show_ban = enabled_show_ban;
        }
    }
}
