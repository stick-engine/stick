package org.stick.library.util;

public final class ReflectionUtils
{
    public static boolean classExists(String name)
    {
        try
        {
            Class.forName(name);
            return true;
        }
        catch (ClassNotFoundException e)
        {
            return false;
        }
    }
}
