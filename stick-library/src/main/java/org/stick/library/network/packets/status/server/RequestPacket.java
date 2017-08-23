package org.stick.library.network.packets.status.server;

import org.stick.library.network.ConnectionState;
import org.stick.library.network.Side;
import org.stick.library.network.packet.Packet;
import org.stick.library.network.packet.serializing.AutoSerializing;

@Packet(id = 0x00, state = ConnectionState.STATUS, bound = Side.SERVER)
@AutoSerializing({})
public class RequestPacket
{
}
