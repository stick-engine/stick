package org.stick.library.network.packet;

import org.stick.library.network.NetworkConnection;

@FunctionalInterface
public interface PacketHandler<T>
{
    void handle(T packet, NetworkConnection connection);
}
