package org.stick.library.chat;

public class TextComponent
{
    private String text;
    private boolean bold;
    private boolean italic;
    private boolean underlined;
    private boolean strikethrough;
    private boolean obfuscated;
    private ChatColor color;

    public TextComponent(String text)
    {
        this.text = text;
    }

    public TextComponent(String text, boolean bold, boolean italic, boolean underlined, boolean strikethrough, boolean obfuscated, ChatColor color)
    {
        this.text = text;
        this.bold = bold;
        this.italic = italic;
        this.underlined = underlined;
        this.strikethrough = strikethrough;
        this.obfuscated = obfuscated;
        this.color = color;
    }

    public String getText()
    {
        return text;
    }

    public boolean isBold()
    {
        return bold;
    }

    public boolean isItalic()
    {
        return italic;
    }

    public boolean isUnderlined()
    {
        return underlined;
    }

    public boolean isStrikethrough()
    {
        return strikethrough;
    }

    public boolean isObfuscated()
    {
        return obfuscated;
    }

    public ChatColor getColor()
    {
        return color;
    }
}
