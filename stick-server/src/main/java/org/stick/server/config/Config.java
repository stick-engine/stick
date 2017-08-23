package org.stick.server.config;

import com.moandjiezana.toml.Toml;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class Config
{
    private static final Logger log = LoggerFactory.getLogger("Config");

    private static final Toml toml;
    private static final File folder;

    static
    {
        toml = new Toml();

        String path = System.getProperty("org.stick.server.configFolder");

        if (path == null)
        {
            path = System.getProperty("user.dir") + "/config/";
        }

        folder = new File(path);
        folder.mkdirs();
    }

    public static final NetworkConfig network = get("network", NetworkConfig.class);
    public static final PlayersConfig players = get("players", PlayersConfig.class);
    public static final GeneralConfig general = get("general", GeneralConfig.class);

    public static <T> T get(String name, Class<T> type)
    {
        File file = new File(folder, name + ".toml");

        if (!file.exists())
        {
            if (file.getParentFile() != null)
            {
                file.getParentFile().mkdirs();
            }

            try
            {
                String defPath = "/" + name + ".default.toml";
                URL defURL = Config.class.getResource("/" + name + ".default.toml");

                if (defURL == null)
                {
                    throw new FileNotFoundException("(classpath) " + defPath);
                }

                File def = new File(defURL.toURI());

                if (def.exists())
                {
                    IOUtils.copy(new FileInputStream(def), new FileOutputStream(file));
                    log.info("Created config '{}' ({}) from default config {}", name, file, def);
                }
            }
            catch (IOException | URISyntaxException e)
            {
                log.error("Couldn't write default configuration '" + name + "', creating empty one", e);

                try
                {
                    file.createNewFile();
                }
                catch (IOException ignored)
                {
                }

                log.info("Created config '{}' ({}) as empty config", name, file);
            }
        }
        else
        {
            log.info("Loaded config '{}' ({})", name, file);
        }

        return toml.read(file).to(type);
    }
}
