package org.stick.server.network.handler;

import org.stick.library.network.NetworkConnection;
import org.stick.library.network.packet.PacketRegistry;
import org.stick.library.network.packets.status.client.PongPacket;
import org.stick.library.network.packets.status.client.ResponsePacket;
import org.stick.library.network.packets.status.server.PingPacket;
import org.stick.library.network.packets.status.server.RequestPacket;
import org.stick.server.StickServer;

public class StatusHandler
{
    // All the packets of status state not handled here are handled in their classes

    private StickServer server;

    public StatusHandler(StickServer server, PacketRegistry registry)
    {
        this.server = server;

        // Registering handlers
        registry.handle(RequestPacket.class, this::handleRequest);
        registry.handle(PingPacket.class, this::handlePing);
    }

    public void handleRequest(RequestPacket packet, NetworkConnection connection)
    {
        connection.sendPacket(new ResponsePacket(server.generateServerInfos()));
    }

    public void handlePing(PingPacket packet, NetworkConnection connection)
    {
        connection.sendPacket(new PongPacket(packet.getPayload()));
    }
}
