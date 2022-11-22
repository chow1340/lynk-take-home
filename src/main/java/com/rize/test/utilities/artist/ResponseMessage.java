package com.rize.test.utilities.artist;

import java.util.List;

public class ResponseMessage {
    private final ResponseStatus status;
    private final List<String> messages;

    public ResponseMessage(ResponseStatus status, String message) {
        this.messages = List.of(message);
        this.status = status;
    }
    public ResponseMessage(ResponseStatus status, List<String> message) {
        this.status = status;
        this.messages = message;
    }

    public List<String> getMessages(){
        return this.messages;
    }

    public ResponseStatus getStatus() {
        return status;
    }

}
