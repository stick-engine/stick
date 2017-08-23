package org.stick.library.network.packet.handling;

import org.stick.library.network.NetworkConnection;

@FunctionalInterface
public interface PacketHandler<T>
{
    void handle(T packet, NetworkConnection connection);
}
