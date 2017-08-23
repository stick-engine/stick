package org.stick.library.network.packets.login.client;

import org.stick.library.chat.ChatMessage;
import org.stick.library.network.ConnectionState;
import org.stick.library.network.Side;
import org.stick.library.network.packet.Packet;
import org.stick.library.network.packet.serializing.AutoSerializing;

@AutoSerializing({"reason"})
@Packet(id = 0x00, state = ConnectionState.LOGIN, bound = Side.CLIENT)
public class LoginDisconnectPacket
{
    private ChatMessage reason;

    public LoginDisconnectPacket(ChatMessage reason)
    {
        this.reason = reason;
    }

    public ChatMessage getReason()
    {
        return reason;
    }
}
