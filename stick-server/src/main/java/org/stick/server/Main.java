package org.stick.server;

public final class Main
{
    public static void main(String[] args)
    {
        StickServer server = StickServer.create();
        server.start();
    }
}