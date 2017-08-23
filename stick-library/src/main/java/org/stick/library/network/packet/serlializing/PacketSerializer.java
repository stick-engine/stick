package org.stick.library.network.packet.serlializing;

import java.io.IOException;
import org.stick.library.util.io.StreamReader;
import org.stick.library.util.io.StreamWriter;

public interface PacketSerializer<P>
{
    void read(P packet, StreamReader in) throws IOException;
    void write(P packet, StreamWriter out) throws IOException;
}
