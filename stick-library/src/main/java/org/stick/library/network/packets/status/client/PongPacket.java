package org.stick.library.network.packets.status.client;

import org.stick.library.network.ConnectionState;
import org.stick.library.network.Side;
import org.stick.library.network.packet.Packet;
import org.stick.library.network.packet.serializing.AutoSerializing;

@Packet(id = 0x01, state = ConnectionState.STATUS, bound = Side.CLIENT)
@AutoSerializing({"payload"})
public class PongPacket
{
    private long payload;

    public PongPacket(long payload)
    {
        this.payload = payload;
    }

    public long getPayload()
    {
        return payload;
    }
}
