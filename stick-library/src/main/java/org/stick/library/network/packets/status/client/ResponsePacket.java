package org.stick.library.network.packets.status.client;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.io.IOException;
import org.stick.library.network.ConnectionState;
import org.stick.library.network.Side;
import org.stick.library.network.packet.Packet;
import org.stick.library.network.packet.serializing.Deserializer;
import org.stick.library.network.packet.serializing.Serializer;
import org.stick.library.util.io.StreamReader;
import org.stick.library.util.io.StreamWriter;

@Packet(id = 0x00, state = ConnectionState.STATUS, bound = Side.CLIENT)
public class ResponsePacket
{
    private static final Gson gson = new GsonBuilder().create();
    private static final JsonParser parser = new JsonParser();

    private JsonObject infos;

    public ResponsePacket()
    {
    }

    public ResponsePacket(JsonObject infos)
    {
        this.infos = infos;
    }

    @Serializer
    public void write(StreamWriter writer) throws IOException
    {
        writer.writeString(gson.toJson(infos));
    }

    @Deserializer
    public void read(StreamReader in) throws IOException
    {
        infos = parser.parse(in.readString()).getAsJsonObject();
    }

    public JsonObject getInfos()
    {
        return infos;
    }
}
