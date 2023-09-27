package de.lhind.dlh.javaleap.integrationdemo.server;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.channel.DirectChannel;
import org.springframework.integration.ip.tcp.TcpInboundGateway;
import org.springframework.integration.ip.tcp.connection.AbstractServerConnectionFactory;
import org.springframework.integration.ip.tcp.connection.TcpNioServerConnectionFactory;
import org.springframework.integration.ip.tcp.serializer.AbstractByteArraySerializer;
import org.springframework.messaging.MessageChannel;


@Configuration
public class ServerCConfig {

    @Value("${serverC.tcp.port}")
    private int serverPort;

    private final AbstractByteArraySerializer serializer;

    public ServerCConfig(AbstractByteArraySerializer serializer) {
        this.serializer = serializer;
    }

    @Bean
    public AbstractServerConnectionFactory serverCConnectionFactory() {
        TcpNioServerConnectionFactory serverConnectionFactory =
                new TcpNioServerConnectionFactory(serverPort);
        serverConnectionFactory.setUsingDirectBuffers(true);
        serverConnectionFactory.setSerializer(serializer);
        serverConnectionFactory.setDeserializer(serializer);
        return serverConnectionFactory;
    }

    /**
     * Inbound channel to receive messages
     *
     * @return MessageChannel
     */
    @Bean
    @Qualifier(value = "serverCInboundChannel")
    public MessageChannel serverCInboundChannel() {
        return new DirectChannel();
    }

    /**
     * Two direction gateway to receive and send messages
     *
     * @param serverConnectionFactory connection factory that we created
     * @param inboundChannel          the channel that we created
     * @return TcpInboundGateway an inbound gateway
     */
    @Bean
    public TcpInboundGateway serverCInboundGateway(
            @Qualifier(value = "serverCConnectionFactory")
            AbstractServerConnectionFactory serverConnectionFactory,
            @Qualifier(value = "serverCInboundChannel") MessageChannel inboundChannel) {
        TcpInboundGateway tcpInboundGateway = new TcpInboundGateway();
        tcpInboundGateway.setConnectionFactory(serverConnectionFactory);
        tcpInboundGateway.setRequestChannel(inboundChannel);
        return tcpInboundGateway;
    }
}
