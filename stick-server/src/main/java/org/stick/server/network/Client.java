package org.stick.server.network;

import java.io.Closeable;
import java.io.EOFException;
import java.io.IOException;
import java.net.Socket;
import java.net.SocketException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.stick.library.Stick;
import org.stick.library.network.NetworkConnection;

public class Client extends Thread implements Closeable
{
    private static final Logger log = LoggerFactory.getLogger("Client");

    private Socket socket;
    private NetworkConnection connection;

    private String username;

    public Client(Socket socket)
    {
        this.socket = socket;
    }

    @Override
    public void run()
    {
        log.info("Connection from {}", socket.getInetAddress().getHostAddress());

        try
        {
            connection = new NetworkConnection(Stick.get().getPacketRegistry(), socket);
        }
        catch (IOException e)
        {
            log.error("Couldn't create connection for {}", socket.getInetAddress().getHostAddress());

            try
            {
                close();
            }
            catch (IOException ignored)
            {
            }

            return;
        }

        while (!socket.isClosed())
        {
            try
            {
                connection.processNextPacket();
            }
            catch (EOFException e)
            {
                try
                {
                    close();
                }
                catch (IOException ignored)
                {
                }
            }
            catch (IOException e)
            {
                if (e instanceof SocketException && (e.getMessage().contains("closed") || e.getMessage().contains("reset") || e.getMessage().contains("pipe")))
                {
                    try
                    {
                        close();
                    }
                    catch (IOException ignored)
                    {
                    }
                }
                else
                {
                    log.error("IO Exception while processing a packet", e);
                }
            }
            catch (Throwable t)
            {
                log.error("Unhandled exception while processing a packet", t);
            }
        }
    }

    public void setUsername(String username)
    {
        this.username = username;
    }

    public String getUsername()
    {
        return username;
    }

    @Override
    public void close() throws IOException
    {
        socket.close();
        this.interrupt();

        log.info("Closed connection from " + socket.getInetAddress().getHostAddress());
    }

    public Socket getSocket()
    {
        return socket;
    }

    public NetworkConnection getConnection()
    {
        return connection;
    }
}
