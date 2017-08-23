package org.stick.library.chat;

public class ChatClickEvent
{
    private ClickEventAction action;
    private String value;

    // TODO: CHANGE PAGE BOOK EVENT

    public ChatClickEvent(ClickEventAction action, String value)
    {
        this.action = action;
        this.value = value;
    }

    public ClickEventAction getAction()
    {
        return action;
    }

    public String getValue()
    {
        return value;
    }

    public enum ClickEventAction
    {
        OPEN_URL,
        RUN_COMMAND,
        SUGGEST_COMMAND
    }
}
