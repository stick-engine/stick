package org.stick.library.chat;

public class ChatMessage extends TextComponent
{
    private String insertion;
    private ChatClickEvent clickEvent;
    private ChatMessage[] extra;

    public ChatMessage(String text)
    {
        super(text);
    }

    public ChatMessage(String text, boolean bold, boolean italic, boolean underlined, boolean strikethrough, boolean obfuscated, ChatColor color, String insertion, ChatClickEvent clickEvent, ChatMessage[] extra)
    {
        super(text, bold, italic, underlined, strikethrough, obfuscated, color);

        this.insertion = insertion;
        this.clickEvent = clickEvent;
        this.extra = extra;
    }

    public String getInsertion()
    {
        return insertion;
    }

    public ChatClickEvent getClickEvent()
    {
        return clickEvent;
    }

    public ChatMessage[] getExtra()
    {
        return extra;
    }
}
