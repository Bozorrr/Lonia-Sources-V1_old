package net.lonia.core.player.account;

import java.util.UUID;

public class DModeration extends AbstractData {

    public DModeration(UUID uuid) {
        this.uuid = uuid;
    }

    String reportedPlayer;
    String reportedMessage;
    String u;

    public String getReportedPlayer() {
        return reportedPlayer;
    }

    public void setReportedPlayer(String reportedPlayer) {
        this.reportedPlayer = reportedPlayer;
    }

    public String getReportedMessage() {
        return reportedMessage;
    }

    public void setReportedMessage(String reportedMessage) {
        this.reportedMessage = reportedMessage;
    }

    public String getU() {
        return u;
    }

    public void setU(String u) {
        this.u = u;
    }
}