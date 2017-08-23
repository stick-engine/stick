package org.stick.library;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.stick.library.network.ConnectionState;
import org.stick.library.network.packet.PacketRegistry;
import org.stick.library.network.packets.handshaking.server.HandshakePacket;
import org.stick.library.network.packets.login.client.LoginDisconnectPacket;
import org.stick.library.network.packets.login.client.LoginSuccessPacket;
import org.stick.library.network.packets.login.server.LoginStartPacket;
import org.stick.library.network.packets.status.client.PongPacket;
import org.stick.library.network.packets.status.client.ResponsePacket;
import org.stick.library.network.packets.status.server.PingPacket;
import org.stick.library.network.packets.status.server.RequestPacket;
import org.stick.library.util.ReflectionUtils;

public class Stick
{
    private static final Logger log = LoggerFactory.getLogger("Stick");

    public static final String VERSION = "0.0.1-ALPHA";
    public static final int PROTOCOL_VERSION = 338;

    public static final String CLIENT_CLASS = "org.stick.client.StickClient";
    public static final String SERVER_CLASS = "org.stick.server.StickServer";

    private static Stick current;

    private boolean running;

    // Network
    private PacketRegistry packets;

    private Stick()
    {
        this.packets = new PacketRegistry();
    }

    public void start()
    {
        // Registering packets
        packets.register(HandshakePacket.class);
        packets.register(RequestPacket.class);
        packets.register(ResponsePacket.class);
        packets.register(PingPacket.class);
        packets.register(PongPacket.class);
        packets.register(LoginStartPacket.class);
        packets.register(LoginSuccessPacket.class);
        packets.register(LoginDisconnectPacket.class);
    }

    public PacketRegistry getPacketRegistry()
    {
        return packets;
    }

    public boolean isRunning()
    {
        return this.running;
    }

    public static boolean isClientSide()
    {
        return ReflectionUtils.classExists(CLIENT_CLASS);
    }

    public static boolean isServerSide()
    {
        return ReflectionUtils.classExists(SERVER_CLASS);
    }

    public static Stick create()
    {
        if (isCreated())
        {
            log.error("Tried to create a new Stick instance when another one was created; nothing will be done");
        }

        return current = new Stick();
    }

    public static Stick get()
    {
        if (!isCreated())
        {
            log.warn("Tried to get the current Stick instance without one currently running");
            log.warn("This will probably lead to a NPE, consider using Stick.isCreated() to check for Stick to be running");
        }

        return current;
    }

    public static boolean isCreated()
    {
        return current != null;
    }
}
