package org.stick.library.chat;

import org.apache.commons.lang3.ArrayUtils;

public class ChatMessageBuilder
{
    private String text;
    private boolean bold;
    private boolean italic;
    private boolean underlined;
    private boolean strikethrough;
    private boolean obfuscated;
    private ChatColor color;
    private String insertion;
    private ChatClickEvent clickEvent;
    private ChatMessage[] extra;

    public ChatMessageBuilder()
    {
        this.text = "";
        this.bold = false;
        this.italic = false;
        this.underlined = false;
        this.strikethrough = false;
        this.obfuscated = false;
        this.color = null;
        this.insertion = null;
        this.clickEvent = null;
        this.extra = null;
    }

    public ChatMessageBuilder setText(String text)
    {
        this.text = text;
        return this;
    }

    public ChatMessageBuilder setBold(boolean bold)
    {
        this.bold = bold;
        return this;
    }

    public ChatMessageBuilder setItalic(boolean italic)
    {
        this.italic = italic;
        return this;
    }

    public ChatMessageBuilder setUnderlined(boolean underlined)
    {
        this.underlined = underlined;
        return this;
    }

    public ChatMessageBuilder setStrikethrough(boolean strikethrough)
    {
        this.strikethrough = strikethrough;
        return this;
    }

    public ChatMessageBuilder setObfuscated(boolean obfuscated)
    {
        this.obfuscated = obfuscated;
        return this;
    }

    public ChatMessageBuilder setColor(ChatColor color)
    {
        this.color = color;
        return this;
    }

    public ChatMessageBuilder setInsertion(String insertion)
    {
        this.insertion = insertion;
        return this;
    }

    public ChatMessageBuilder setClickEvent(ChatClickEvent clickEvent)
    {
        this.clickEvent = clickEvent;
        return this;
    }

    public ChatMessageBuilder addExtra(ChatMessage message)
    {
        extra = ArrayUtils.add(extra, message);
        return this;
    }

    public ChatMessage build()
    {
        return new ChatMessage(text, bold, italic, underlined, strikethrough, obfuscated, color, insertion, clickEvent, extra);
    }
}
