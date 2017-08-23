package org.stick.library.network.packets.handshaking.server;

import org.stick.library.network.ConnectionState;
import org.stick.library.network.Side;
import org.stick.library.network.packet.serializing.AutoSerializing;
import org.stick.library.network.packet.Packet;
import org.stick.library.network.packet.serializing.types.UnsignedShort;
import org.stick.library.network.packet.serializing.types.VarNum;

@AutoSerializing({"protocolVersion", "serverAddress", "serverPort", "nextState"})
@Packet(id = 0x00, state = ConnectionState.HANDSHAKING, bound = Side.SERVER)
public class HandshakePacket
{
    @VarNum
    private int protocolVersion;

    private String serverAddress;

    @UnsignedShort
    private int serverPort;

    @VarNum
    private int nextState;

    public HandshakePacket()
    {
    }

    public HandshakePacket(int protocolVersion, String serverAddress, int serverPort, int nextState)
    {
        this.protocolVersion = protocolVersion;
        this.serverAddress = serverAddress;
        this.serverPort = serverPort;
        this.nextState = nextState;
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
