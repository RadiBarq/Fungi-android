package com.example.radibarq.pokemongotradecenter;

import java.util.Date;

/**
 * Created by radibarq on 10/30/16.
 */

public class ChatMessage {

    public String messageText;
    public String messageUser;
    public long messageTime;

    public ChatMessage(String messageText, String messageUser)
    {
        this.messageText = messageText;
        this.messageUser = messageUser;
        messageTime = new Date().getTime();
    }
}
