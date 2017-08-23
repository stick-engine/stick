package org.stick.library.network.packet.handling;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Optional;
import java.util.stream.Stream;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.stick.library.network.NetworkConnection;

public class ReflectionHandler<P> implements PacketHandler<P>
{
    private static final Logger log = LoggerFactory.getLogger("ReflectionHandler");

    private Method handler;
    private boolean provideConnection;

    public ReflectionHandler(Class<P> packetClass)
    {
        Optional<Method> method = Stream.of(packetClass.getMethods())
                                        .filter(m -> m.isAnnotationPresent(Handle.class))
                                        .findFirst();

        if (method.isPresent())
        {
            handler = method.get();

            if (handler.getParameterCount() > 1 || (handler.getParameterCount() == 1 && handler.getParameterTypes()[0] != NetworkConnection.class))
            {
                log.error("Found @Handler method with wrong arguments (should be [], or [class org.stick.library.network.NetworkConnection], but are {})", Arrays.toString(handler.getParameterTypes()));
                log.error("for packet " + packetClass.getName() + "; handler method will be ignored");

                handler = null;
            }
            else
            {
                this.provideConnection = handler.getParameterCount() == 1;
            }
        }
    }

    @Override
    public void handle(P packet, NetworkConnection connection)
    {
        if (handler == null)
        {
            log.warn("Received packet {} but no custom handler was set", packet.getClass().getName());
            log.warn("and no method with @Handle was found in class; nothing will be done");

            return;
        }

        try
        {
            if (provideConnection)
            {
                handler.invoke(packet, connection);
            }
            else
            {
                handler.invoke(packet);
            }
        }
        catch (IllegalAccessException e)
        {
            log.error("Tried to invoke handler method '" + handler.getName() + "' (from class " + packet.getClass().getName() + ") but it isn't public");
            log.error("Nothing will be done, packet will be ignored; this is a serious error", e);
        }
        catch (InvocationTargetException e)
        {
            if (e.getTargetException() instanceof RuntimeException)
            {
                throw (RuntimeException) e.getTargetException();
            }

            throw new RuntimeException(packet.getClass().getName() + " handler method threw an exception", e);
        }
    }
}
