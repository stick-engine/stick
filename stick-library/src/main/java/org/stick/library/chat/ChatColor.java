package org.stick.library.chat;

import com.google.gson.annotations.SerializedName;
import java.awt.Color;

public enum ChatColor
{
    @SerializedName("black")
    BLACK("0", "Black", new Color(0, 0, 0), new Color(0, 0, 0)),

    @SerializedName("dark_blue")
    DARK_BLUE("1", "Dark blue", new Color(0, 0, 170), new Color(0, 0, 42)),

    @SerializedName("dark_green")
    DARK_GREEN("2", "Dark green", new Color(0, 170, 0), new Color(0, 42, 0)),

    @SerializedName("dark_aqua")
    DARK_AQUA("3", "Dark cyan", new Color(0, 170, 170), new Color(0, 42, 42)),

    @SerializedName("dark_read")
    DARK_RED("4", "Dark red", new Color(170, 0, 0), new Color(42, 0, 0)),

    @SerializedName("dark_purple")
    DARK_PURPLE("5", "Purple", new Color(170, 0, 170), new Color(42, 0, 42)),

    @SerializedName("gold")
    GOLD("6", "Gold", new Color(255, 170, 0), new Color(42, 42, 0)),

    @SerializedName("gray")
    GRAY("7", "Gray", new Color(170, 170, 170), new Color(42, 42, 42)),

    @SerializedName("dark_gray")
    DARK_GRAY("8", "Dark gray", new Color(85, 85, 85), new Color(21, 21, 21)),

    @SerializedName("blue")
    BLUE("9", "Blue", new Color(85, 85, 255), new Color(21, 21, 63)),

    @SerializedName("green")
    GREEN("a", "Dark Green", new Color(85, 255, 85), new Color(21, 63, 21)),

    @SerializedName("aqua")
    AQUA("b", "Cyan", new Color(85, 255, 255), new Color(21, 63, 63)),

    @SerializedName("red")
    RED("c", "Red", new Color(255, 85, 85), new Color(63, 21, 21)),

    @SerializedName("light_purple")
    LIGHT_PURPLE("d", "Pink", new Color(255, 85, 255), new Color(63, 21, 63)),

    @SerializedName("yellow")
    YELLOW("e", "Yellow", new Color(255, 255, 85), new Color(63, 63, 21)),

    @SerializedName("white")
    WHITE("f", "White", new Color(255, 255, 255), new Color(63, 63, 63));

    private String code;
    private String commonName;
    private Color foreground;
    private Color background;

    ChatColor(String code, String commonName, Color foreground, Color background)
    {
        this.code = code;
        this.commonName = commonName;
        this.foreground = foreground;
        this.background = background;
    }

    public String getCode()
    {
        return code;
    }

    public String getCommonName()
    {
        return commonName;
    }

    public Color getForeground()
    {
        return foreground;
    }

    public Color getBackground()
    {
        return background;
    }
}
