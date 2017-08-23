package org.stick.server;

import com.google.gson.JsonObject;
import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.util.ArrayList;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.stick.library.Stick;
import org.stick.library.network.ConnectionState;
import org.stick.library.network.NetworkConnection;
import org.stick.server.config.Config;
import org.stick.server.network.Client;
import org.stick.server.network.handler.LoginHandler;
import org.stick.server.network.handler.StatusHandler;

public class StickServer
{
    private static final Logger log = LoggerFactory.getLogger("StickServer");

    public static final String VERSION = "0.0.1-ALPHA";

    private static StickServer current;

    private boolean running = false;

    // Main
    private Stick stick;

    // Network
    private ServerSocket socket;
    private List<Client> clients;

    // Handlers
    private StatusHandler statusHandler;
    private LoginHandler loginHandler;

    private StickServer()
    {
        this.stick = Stick.create();
        this.clients = new ArrayList<>();
    }

    public void start()
    {
        if (isRunning())
        {
            log.error("Tried to stop the server when it was started/already started");
            log.error("Nothing will be done");
        }

        running = true;

        long time = System.currentTimeMillis();

        log.info("Starting Stick Server v{}", VERSION);

        Runtime.getRuntime().addShutdownHook(new Thread(this::stop));

        log.info("Loading Stick core...");
        stick.start();

        log.info("Loading Stick server...");
        statusHandler = new StatusHandler(this, stick.getPacketRegistry());
        loginHandler = new LoginHandler(this, stick.getPacketRegistry());

        log.info("Creating server...");

        String address = Config.network.getAddress();
        int port = Config.network.getPort();

        try
        {
            socket = new ServerSocket(port, 50, InetAddress.getByName(address));
        }
        catch (IOException e)
        {
            log.error("Couldn't create server !", e);
            log.error("This is probably caused by a used port or a malformed network config, server won't be launched");

            System.exit(1);
        }

        log.info("Done ! Listening on {}:{} Initialization took {}ms", address, port, System.currentTimeMillis() - time);

        while (isRunning())
        {
            try
            {
                Client client = new Client(socket.accept());
                clients.add(client);

                client.start();
            }
            catch (IOException e)
            {
                if (isRunning() && !e.getMessage().contains("closed"))
                {
                    log.error("Major error during client connection", e);
                }
            }
        }
    }

    public Client getClient(NetworkConnection connection)
    {
        return getClients().stream().filter(client -> client.getConnection() == connection).findFirst().orElse(null);
    }

    public JsonObject generateServerInfos()
    {
        JsonObject infos = new JsonObject();

        JsonObject version = new JsonObject();
        JsonObject players = new JsonObject();
        JsonObject description = new JsonObject();

        version.addProperty("name", "1.12.1"); // :'(
        version.addProperty("protocol", Stick.PROTOCOL_VERSION);

        int onlinePlayers = (int) clients.stream()
                                   .filter(client -> client.getConnection().getState() == ConnectionState.PLAY)
                                   .count();

        players.addProperty("online", onlinePlayers);
        players.addProperty("max", Config.players.getMaxPlayers());

        description.addProperty("text", Config.general.getMotd());

        infos.add("version", version);
        infos.add("players", players);
        infos.add("description", description);

        return infos;
    }

    public void stop()
    {
        if (!isRunning())
        {
            log.error("Tried to stop the server when it was stopping/already stopped");
            log.error("Nothing will be done");
        }

        running = false;

        log.info("Stopping...");

        clients.forEach(client -> {
            try
            {
                client.close();
            }
            catch (IOException ignored)
            {
            }
        });

        if (socket != null)
        {
            try
            {
                socket.close();
            }
            catch (IOException ignored)
            {
            }
        }
    }

    public List<Client> getClients()
    {
        return clients;
    }

    public boolean isRunning()
    {
        return this.running;
    }

    public static StickServer create()
    {
        if (isCreated())
        {
            log.warn("Tried to create a new Stick server when another one was running");
            log.warn("Stopping the previous one; consider using StickServer.get().stop(); before");

            get().stop();
        }

        return current = new StickServer();
    }

    public static StickServer get()
    {
        if (!isCreated())
        {
            log.warn("Tried to get the current server instance without one currently running");
            log.warn("This will probably lead to a NPE, consider using StickServer.isCreated() to check for the server to be launched");
        }

        return current;
    }

    public static boolean isCreated()
    {
        return current != null;
    }
}