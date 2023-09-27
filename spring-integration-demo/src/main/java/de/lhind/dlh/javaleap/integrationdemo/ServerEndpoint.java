package de.lhind.dlh.javaleap.integrationdemo;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.integration.annotation.MessageEndpoint;
import org.springframework.integration.annotation.ServiceActivator;

import java.nio.charset.StandardCharsets;

@Slf4j
@MessageEndpoint
@RequiredArgsConstructor
public class ServerEndpoint {

    private final EchoService echoService;

    /**
     * Processor of incoming message that are read by Server A Channel
     *
     * @param bytes Receives bytes that are sent in that buffer
     * @return message in bytes from RTC
     */
    @ServiceActivator(inputChannel = "serverAInboundChannel")
    public byte[] processServerAMessage(byte[] bytes) {
        String message = new String(bytes, StandardCharsets.ISO_8859_1);
        log.info("Server A input: {}", message);

        String response = echoService.sendMessageA(message);
        log.info("Server A output: {}", message);
        return response.getBytes(StandardCharsets.ISO_8859_1);
    }

    /**
     * Processor of incoming message that are read by FRA SC Inbound Channel
     *
     * @param bytes Receives bytes that are sent in that buffer
     * @return message in bytes from RTC
     */
    @ServiceActivator(inputChannel = "serverBInboundChannel")
    public byte[] processServerBMessage(byte[] bytes) {
        String message = new String(bytes, StandardCharsets.ISO_8859_1);
        log.info("Server B input: {}", message);

        String response = echoService.sendMessageB(message);
        log.info("Server B output: {}", message);
        return response.getBytes(StandardCharsets.ISO_8859_1);
    }

    /**
     * Processor of incoming message that are read by FRA CB Inbound Channel
     *
     * @param bytes Receives bytes that are sent in that buffer
     * @return message in bytes from RTC
     */
    @ServiceActivator(inputChannel = "serverCInboundChannel")
    public byte[] processServerCMessage(byte[] bytes) {
        String message = new String(bytes, StandardCharsets.ISO_8859_1);
        log.info("Server C input: {}", message);

        String response = echoService.sendMessageC(message);
        log.info("Server C output: {}", message);
        return response.getBytes(StandardCharsets.ISO_8859_1);
    }
}
