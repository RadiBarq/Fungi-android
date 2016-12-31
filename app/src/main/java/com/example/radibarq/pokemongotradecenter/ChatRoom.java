package com.example.radibarq.pokemongotradecenter;

/**
 * Created by radibarq on 10/30/16.
 */

public class ChatRoom {


    ChatMessage chatMessage;
    String user;
    String currentUser;

    ChatRoom(ChatMessage chatMessage, String user, String currentUser)
    {
        this.chatMessage = chatMessage;
        this.user = user;
        this.currentUser = currentUser;
    }

}
