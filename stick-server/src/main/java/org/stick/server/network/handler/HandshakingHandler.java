package org.stick.server.network.handler;

import org.stick.library.network.ConnectionState;
import org.stick.library.network.NetworkConnection;
import org.stick.library.network.packet.PacketRegistry;
import org.stick.library.network.packets.handshaking.server.HandshakePacket;

public class HandshakingHandler
{
    public HandshakingHandler(PacketRegistry registry)
    {
        registry.handle(HandshakePacket.class, this::handleHanshaking);
    }

    public void handleHanshaking(HandshakePacket packet, NetworkConnection connection)
    {
        switch (packet.getNextState())
        {
            case 1:
                connection.setState(ConnectionState.STATUS);
                break;
            case 2:
                connection.setState(ConnectionState.LOGIN);
                break;
        }
    }
}
