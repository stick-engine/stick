package org.stick.library.world;

public class Position3D extends Position2D
{
    private int z;

    public Position3D()
    {
        this(0, 0, 0);
    }

    public Position3D(int x, int y, int z)
    {
        super(x, y);
        this.z = z;
    }

    public int getZ()
    {
        return z;
    }

    public void setZ(int z)
    {
        this.z = z;
    }

    public double encode()
    {
        return ((getX() & 0x3FFFFFF) << 38) | ((getY() & 0xFFF) << 26) | (getZ() & 0x3FFFFFF);
    }

    public static Position3D decode(long value)
    {
        return new Position3D((int) (value >> 38), (int) ((value >> 26) & 0xFFF), (int) (value << 38 >> 38));
    }
}
