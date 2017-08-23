package org.stick.library.chat;

public class ChatHoverEvent
{
    private HoverEventAction action;
    private TextComponent textValue;

    // TODO: ITEM VALUE
    // TODO: SHOW ENTITY
    // TODO: SHOW ACHIEVEMENT

    public ChatHoverEvent(String value)
    {
        this(new TextComponent(value));
    }

    public ChatHoverEvent(TextComponent text)
    {
        this.action = HoverEventAction.SHOW_TEXT;
        this.textValue = text;
    }

    public HoverEventAction getAction()
    {
        return action;
    }

    public TextComponent getTextValue()
    {
        return textValue;
    }

    public enum HoverEventAction
    {
        SHOW_TEXT,
        SHOW_ITEM,
        SHOW_ENTITY
    }
}
