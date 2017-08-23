package org.stick.library.network.packets.login.server;

import org.stick.library.network.ConnectionState;
import org.stick.library.network.Side;
import org.stick.library.network.packet.Packet;
import org.stick.library.network.packet.serlializing.AutoSerializing;

@Packet(id = 0x00, state = ConnectionState.LOGIN, bound = Side.SERVER)
@AutoSerializing({"username"})
public class LoginStartPacket
{
    private String username;

    public String getUsername()
    {
        return username;
    }
}
