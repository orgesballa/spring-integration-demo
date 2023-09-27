package de.lhind.dlh.javaleap.integrationdemo;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class EchoService {

    private final TcpClientGateway tcpClientGateway;

    public String sendMessageA(String message) {
        log.info("Sending message via client A: {}", message);
        String response = new String(tcpClientGateway.sendClientAMessage(message.getBytes()));
        log.info("Received response: {}", response);
        return response + " - from server A";
    }

    public String sendMessageB(String message) {
        log.info("Sending message via client B: {}", message);
        String response = new String(tcpClientGateway.sendClientBMessage(message.getBytes()));
        log.info("Received response: {}", response);
        return response + " - from server B";
    }

    public String sendMessageC(String message) {
        log.info("Sending message via client C: {}", message);
        String response = new String(tcpClientGateway.sendClientCMessage(message.getBytes()));
        log.info("Received response: {}", response);
        return response + " - from server C";
    }
}
