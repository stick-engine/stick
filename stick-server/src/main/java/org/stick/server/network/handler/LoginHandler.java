package org.stick.server.network.handler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.stick.library.network.NetworkConnection;
import org.stick.library.network.packet.PacketRegistry;
import org.stick.library.network.packets.login.server.LoginStartPacket;
import org.stick.server.StickServer;
import org.stick.server.network.Client;

public class LoginHandler
{
    private static final Logger log = LoggerFactory.getLogger("LoginHandler");

    private StickServer server;

    public LoginHandler(StickServer server, PacketRegistry registry)
    {
        this.server = server;

        registry.handle(LoginStartPacket.class, this::handleLoginStart);
    }

    protected void handleLoginStart(LoginStartPacket packet, NetworkConnection connection)
    {
        Client client = server.getClient(connection);
        client.setUsername(packet.getUsername());

        log.info("Logging in '{}' ({})", packet.getUsername(), client.getSocket().getInetAddress());
    }
}
