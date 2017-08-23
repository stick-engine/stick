package org.stick.library.network.packets.login.server;

import org.stick.library.network.ConnectionState;
import org.stick.library.network.Side;
import org.stick.library.network.packet.Packet;
import org.stick.library.network.packet.serializing.AutoSerializing;

@AutoSerializing({"username"})
@Packet(id = 0x00, state = ConnectionState.LOGIN, bound = Side.SERVER)
public class LoginStartPacket
{
    private String username;

    public LoginStartPacket()
    {
    }

    public LoginStartPacket(String username)
    {
        this.username = username;
    }

    public String getUsername()
    {
        return username;
    }
}
