package use.spiral.event.impl;

import lombok.AllArgsConstructor;
import use.spiral.event.Event;

@AllArgsConstructor
@SuppressWarnings("all")
public class SendMessageEvent extends Event {
    public String message;
}