package org.stick.library.network;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.Socket;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.stick.library.Stick;
import org.stick.library.network.packet.NoHandler;
import org.stick.library.network.packet.Packet;
import org.stick.library.network.packet.PacketContainer;
import org.stick.library.network.packet.PacketRegistry;
import org.stick.library.network.packet.PacketHandler;
import org.stick.library.network.packet.serializing.PacketSerializer;
import org.stick.library.util.io.StreamReader;
import org.stick.library.util.io.StreamWriter;

public class NetworkConnection
{
    private static final Logger log = LoggerFactory.getLogger("NetworkConnection");

    private PacketRegistry packetRegistry;

    private StreamReader reader;
    private StreamWriter writer;

    private Side bound;
    private ConnectionState state;

    public NetworkConnection(PacketRegistry registry, Socket socket) throws IOException
    {
        this.packetRegistry = registry;

        this.reader = new StreamReader(socket.getInputStream());
        this.writer = new StreamWriter(socket.getOutputStream());

        this.bound = Stick.isClientSide() ? Side.CLIENT : Side.SERVER;
        this.state = ConnectionState.HANDSHAKING;
    }

    /**
     * Read the next packet from the Socket input;
     * Deserialize it using its registered {@link PacketSerializer};
     * Handle it using its registered {@link PacketHandler}
     *
     * @param <P> The packet type
     *
     * @throws IOException If it failed reading the packet from the stream
     */
    public <P> void processNextPacket() throws IOException
    {
        // Reading raw packet
        int length = reader.readVarInt();
        byte[] data = reader.readBytes(length);

        StreamReader contentReader = new StreamReader(data);
        int id = contentReader.readVarInt();
        byte[] content = contentReader.readBytes(data.length - contentReader.getReadAmount());

        // Getting its class and handler
        Class<P> packetClass = packetRegistry.find(id, state, bound);
        PacketContainer<P> container = packetRegistry.getPacketContainer(packetClass);

        if (packetClass == null)
        {
            log.error("Received unknown packet (state : {}) (id : 0x{}), skipping", state.name().toLowerCase(), Integer.toHexString(id));
            return;
        }

        // Deserializing it, then handling it
        P packet;

        try
        {
            packet = packetClass.newInstance();
        }
        catch (InstantiationException | IllegalAccessException e)
        {
            log.error("Couldn't instantiate packet " + packetClass.getName() + "; empty constructor is missing/isn't public");
            log.error("Packet will be ignored, this is definitely a development error", e);

            return;
        }

        container.getSerializer().read(packet, new StreamReader(content));

        if (container.getHandler() == null && ! packetClass.isAnnotationPresent(NoHandler.class))
        {
            log.warn("No handler was defined for packet " + packetClass.getName());
            log.warn("This is probably a development error, if it isn't, please put a @NoHandler annotation on the packet class");
            log.warn("Packet will be ignored");
        }
        else
        {
            container.getHandler().handle(packet, this);
        }
    }

    public <P> void sendPacket(P object)
    {
        // Getting packet infos
        Class<P> packetClass = (Class<P>) object.getClass();

        Packet packet = packetRegistry.getPacketInfos(packetClass);
        PacketContainer<P> container = packetRegistry.getPacketContainer(packetClass);

        if (packet == null)
        {
            throw new IllegalArgumentException("Can't send an unregistered packet (" + packetClass.getName() + ")");
        }

        ByteArrayOutputStream out = new ByteArrayOutputStream();
        try
        {
            container.getSerializer().write(object, new StreamWriter(out));

            // Getting packet raw data
            int id = packet.id();
            byte[] content = out.toByteArray();

            out = new ByteArrayOutputStream();
            StreamWriter stream = new StreamWriter(out);

            stream.writeVarInt(id);
            stream.writeBytes(content);
        }
        catch (IOException e)
        {
            log.error("IO Exception while serializing packet " + packetClass.getName(), e);
            log.error("Packet will not be sent");

            return;
        }

        byte[] data = out.toByteArray();
        int length = data.length;

        try
        {
            // Sending packet
            writer.writeVarInt(length);
            writer.writeBytes(data);
        }
        catch (IOException e)
        {
            log.error("IO Exception while sending packet " + packetClass.getName(), e);
            log.error("Packet will not be sent");

            return;
        }
    }

    public void setState(ConnectionState state)
    {
        this.state = state;
    }

    public ConnectionState getState()
    {
        return state;
    }
}
