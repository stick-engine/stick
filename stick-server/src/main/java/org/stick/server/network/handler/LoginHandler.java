package org.stick.server.network.handler;

import java.util.UUID;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.stick.library.network.ConnectionState;
import org.stick.library.network.NetworkConnection;
import org.stick.library.network.packet.PacketRegistry;
import org.stick.library.network.packets.login.client.LoginSuccessPacket;
import org.stick.library.network.packets.login.server.LoginStartPacket;
import org.stick.server.StickServer;
import org.stick.server.config.Config;
import org.stick.server.network.Client;

public class LoginHandler
{
    private static final Logger log = LoggerFactory.getLogger("LoginHandler");

    // TODO: AUTH AND ENCRYPTION

    private StickServer server;

    public LoginHandler(StickServer server, PacketRegistry registry)
    {
        this.server = server;

        registry.handle(LoginStartPacket.class, this::handleLoginStart);
    }

    public void handleLoginStart(LoginStartPacket packet, NetworkConnection connection)
    {
        Client client = server.getClient(connection);
        client.getPlayerInfos().setUsername(packet.getUsername());

        if (Config.players.isOnlineMode())
        {
            log.info("Logging in '{}' ({})", packet.getUsername(), client.getSocket().getInetAddress());
            // TODO: Do auth and encryption
        }
        else
        {
            String uuid = UUID.randomUUID().toString();

            client.getPlayerInfos().setUuid(uuid);
            connection.sendPacket(new LoginSuccessPacket(uuid, packet.getUsername()));

            log.info("Player '{}', (Offline-Mode) is connecting [{}]", client.getPlayerInfos().getUsername(), client.getSocket().getInetAddress().getHostAddress());
        }

        connection.setState(ConnectionState.PLAY);
    }
}
