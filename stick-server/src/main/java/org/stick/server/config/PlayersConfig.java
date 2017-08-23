package org.stick.server.config;

public class PlayersConfig
{
    private int maxPlayers;
    private boolean onlineMode;
    private boolean whitelistEnabled;

    public int getMaxPlayers()
    {
        return maxPlayers;
    }

    public boolean isOnlineMode()
    {
        return onlineMode;
    }

    public boolean isWhitelistEnabled()
    {
        return whitelistEnabled;
    }
}
