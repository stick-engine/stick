package org.stick.library.network.packet;

import gnu.trove.map.TIntObjectMap;
import gnu.trove.map.hash.TIntObjectHashMap;
import java.util.HashMap;
import java.util.Map;
import org.stick.library.network.ConnectionState;
import org.stick.library.network.Side;
import org.stick.library.network.packet.handling.PacketHandler;
import org.stick.library.network.packet.handling.ReflectionHandler;
import org.stick.library.network.packet.serializing.AutoSerializer;
import org.stick.library.network.packet.serializing.AutoSerializing;
import org.stick.library.network.packet.serializing.ReflectionSerializer;

public class PacketRegistry
{
    // Client-bound packets
    private TIntObjectMap<Class> clientHandshaking = new TIntObjectHashMap<>();
    private TIntObjectMap<Class> clientStatus = new TIntObjectHashMap<>();
    private TIntObjectMap<Class> clientLogin = new TIntObjectHashMap<>();
    private TIntObjectMap<Class> clientPlay = new TIntObjectHashMap<>();

    // Server-bound packets
    private TIntObjectMap<Class> serverHandshaking = new TIntObjectHashMap<>();
    private TIntObjectMap<Class> serverStatus = new TIntObjectHashMap<>();
    private TIntObjectMap<Class> serverLogin = new TIntObjectHashMap<>();
    private TIntObjectMap<Class> serverPlay = new TIntObjectHashMap<>();

    // Packet handlers and serializers
    private Map<Class, PacketContainer> packetContainers = new HashMap<>();

    // Packet annotations, cached to not have to use reflection at each packet sending
    private Map<Class, Packet> packetInfos = new HashMap<>();

    public <P> void register(Class<P> packetClass)
    {
        if (!packetClass.isAnnotationPresent(Packet.class))
        {
            throw new IllegalArgumentException("Cannot register class " + packetClass.getName() + " as packet : missing annotation @Packet");
        }

        Packet packet = packetClass.getAnnotation(Packet.class);
        packetInfos.put(packetClass, packet);

        register(packet.id(), packet.state(), packet.bound(), packetClass);
    }

    public <P> void register(int id, ConnectionState state, Side bound, Class<P> packet)
    {
        TIntObjectMap<Class> map = getPacketsFor(state, bound);

        if (map == null)
        {
            return;
        }

        handle(packet, new ReflectionHandler<>(packet));
        getPacketContainer(packet).setSerializer(packet.isAnnotationPresent(AutoSerializing.class) ?
                                                 new AutoSerializer<>(packet) : new ReflectionSerializer<>(packet));

        map.put(id, packet);
    }

    public <P> void handle(Class<P> packetClass, PacketHandler<P> handler)
    {
        getPacketContainer(packetClass).setHandler(handler);
    }

    public Class find(int id, ConnectionState state, Side bound)
    {
        TIntObjectMap<Class> map = getPacketsFor(state, bound);

        if (map == null)
        {
            return null;
        }

        return map.get(id);
    }

    public TIntObjectMap<Class> getPacketsFor(ConnectionState state, Side bound)
    {
        boolean client = bound == Side.CLIENT;

        switch (state)
        {
            case HANDSHAKING:
                return client ? clientHandshaking : serverHandshaking;
            case STATUS:
                return client ? clientStatus : serverStatus;
            case LOGIN:
                return client ? clientLogin : serverLogin;
            case PLAY:
                return client ? clientPlay : serverPlay;
        }

        return null;
    }

    public <P> PacketContainer<P> getPacketContainer(Class<P> packetClass)
    {
        PacketContainer<P> container = packetContainers.get(packetClass);

        if (container == null)
        {
            container = new PacketContainer<>();
            packetContainers.put(packetClass, container);
        }

        return container;
    }

    public <P> Packet getPacketInfos(Class<P> packetClass)
    {
        return packetInfos.get(packetClass);
    }
}
