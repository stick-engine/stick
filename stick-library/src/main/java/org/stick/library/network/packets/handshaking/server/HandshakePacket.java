package org.stick.library.network.packets.handshaking.server;

import org.stick.library.network.ConnectionState;
import org.stick.library.network.NetworkConnection;
import org.stick.library.network.Side;
import org.stick.library.network.packet.handling.Handle;
import org.stick.library.network.packet.serializing.AutoSerializing;
import org.stick.library.network.packet.Packet;
import org.stick.library.network.packet.serializing.types.UnsignedShort;
import org.stick.library.network.packet.serializing.types.VarNum;

@Packet(id = 0x00, state = ConnectionState.HANDSHAKING, bound = Side.SERVER)
@AutoSerializing({"protocolVersion", "serverAddress", "serverPort", "nextState"})
public class HandshakePacket
{
    @VarNum
    private int protocolVersion;

    private String serverAddress;

    @UnsignedShort
    private int serverPort;

    @VarNum
    private int nextState;

    @Handle
    public void handle(NetworkConnection connection)
    {
        switch (nextState)
        {
            case 1:
                connection.setState(ConnectionState.STATUS);
                break;
            case 2:
                connection.setState(ConnectionState.LOGIN);
                break;
        }
    }

    public int getProtocolVersion()
    {
        return protocolVersion;
    }

    public String getServerAddress()
    {
        return serverAddress;
    }

    public int getServerPort()
    {
        return serverPort;
    }

    public int getNextState()
    {
        return nextState;
    }
}
