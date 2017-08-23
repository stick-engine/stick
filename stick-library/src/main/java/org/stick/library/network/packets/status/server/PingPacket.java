package org.stick.library.network.packets.status.server;

import org.stick.library.network.ConnectionState;
import org.stick.library.network.NetworkConnection;
import org.stick.library.network.Side;
import org.stick.library.network.packet.Packet;
import org.stick.library.network.packet.handling.Handle;
import org.stick.library.network.packet.serializing.AutoSerializing;
import org.stick.library.network.packets.status.client.PongPacket;

@AutoSerializing({"payload"})
@Packet(id = 0x01, state = ConnectionState.STATUS, bound=Side.SERVER)
public class PingPacket
{
    private long payload;

    @Handle
    public void handle(NetworkConnection connection)
    {
        connection.sendPacket(new PongPacket(payload));
    }

    public long getPayload()
    {
        return payload;
    }
}
