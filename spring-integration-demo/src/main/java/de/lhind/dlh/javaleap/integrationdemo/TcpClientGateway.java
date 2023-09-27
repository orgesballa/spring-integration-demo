package de.lhind.dlh.javaleap.integrationdemo;

import org.springframework.integration.annotation.Gateway;
import org.springframework.integration.annotation.MessagingGateway;
import org.springframework.stereotype.Component;

/**
 * The usable gateway that uses our outbound channel
 *
 */
@Component
@MessagingGateway
public interface TcpClientGateway {

    @Gateway(requestChannel = "clientAOutboundChannel")
    byte[] sendClientAMessage(byte[] message);

    @Gateway(requestChannel = "clientBOutboundChannel")
    byte[] sendClientBMessage(byte[] message);

    @Gateway(requestChannel = "clientCOutboundChannel")
    byte[] sendClientCMessage(byte[] message);
}

