package org.stick.library.network.packets.status.server;

import org.stick.library.network.ConnectionState;
import org.stick.library.network.Side;
import org.stick.library.network.packet.Packet;
import org.stick.library.network.packet.serializing.AutoSerializing;

@AutoSerializing({"payload"})
@Packet(id = 0x01, state = ConnectionState.STATUS, bound=Side.SERVER)
public class PingPacket
{
    private long payload;

    public PingPacket()
    {
    }

    public PingPacket(long payload)
    {
        this.payload = payload;
    }

    public long getPayload()
    {
        return payload;
    }
}
