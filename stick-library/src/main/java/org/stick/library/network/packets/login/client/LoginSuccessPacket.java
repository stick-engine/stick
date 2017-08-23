package org.stick.library.network.packets.login.client;

import org.stick.library.network.ConnectionState;
import org.stick.library.network.Side;
import org.stick.library.network.packet.Packet;
import org.stick.library.network.packet.serializing.AutoSerializing;

@AutoSerializing({"uuid", "username"})
@Packet(id = 0x02, state = ConnectionState.LOGIN, bound = Side.CLIENT)
public class LoginSuccessPacket
{
    private String uuid;
    private String username;

    public LoginSuccessPacket()
    {
    }

    public LoginSuccessPacket(String uuid, String username)
    {
        this.uuid = uuid;
        this.username = username;
    }

    public String getUuid()
    {
        return uuid;
    }

    public String getUsername()
    {
        return username;
    }
}
