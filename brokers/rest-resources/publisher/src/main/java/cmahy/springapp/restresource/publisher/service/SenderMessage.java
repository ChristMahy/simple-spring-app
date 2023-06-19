package cmahy.springapp.restresource.publisher.service;

import cmahy.springapp.restresource.publisher.domain.Message;

public interface SenderMessage {

    void sendMessage(Message message);
}
