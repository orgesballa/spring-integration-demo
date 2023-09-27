package de.lhind.dlh.javaleap.integrationdemo.client;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.ApplicationEventPublisherAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.integration.channel.DirectChannel;
import org.springframework.integration.ip.tcp.TcpOutboundGateway;
import org.springframework.integration.ip.tcp.connection.AbstractClientConnectionFactory;
import org.springframework.integration.ip.tcp.connection.CachingClientConnectionFactory;
import org.springframework.integration.ip.tcp.connection.TcpNioClientConnectionFactory;
import org.springframework.integration.ip.tcp.serializer.AbstractByteArraySerializer;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.MessageHandler;

@Configuration
public class ClientCConfig implements ApplicationEventPublisherAware {

    @Value("${clientC.tcp.host}")
    private String host;

    @Value("${clientC.tcp.port}")
    private int port;

    @Value("${clientC.tcp.connection.poolSize}")
    private int connectionPoolSize;

    private ApplicationEventPublisher applicationEventPublisher;

    private final AbstractByteArraySerializer codec;

    public ClientCConfig(AbstractByteArraySerializer codec) {
        this.codec = codec;
    }

    @Override
    public void setApplicationEventPublisher(ApplicationEventPublisher applicationEventPublisher) {
        this.applicationEventPublisher = applicationEventPublisher;
    }

    /**
     * Client Connection Factory
     *
     * @return a TCP Non Blocking IO Connection Factory
     */
    @Bean
    public AbstractClientConnectionFactory clientCConnectionFactory() {
        TcpNioClientConnectionFactory tcpNioClientConnectionFactory =
                new TcpNioClientConnectionFactory(host, port);
        tcpNioClientConnectionFactory.setUsingDirectBuffers(true);
        tcpNioClientConnectionFactory.setSerializer(codec);
        tcpNioClientConnectionFactory.setDeserializer(codec);
        tcpNioClientConnectionFactory.setApplicationEventPublisher(applicationEventPublisher);
        return new CachingClientConnectionFactory(tcpNioClientConnectionFactory, connectionPoolSize);
    }

    /**
     * The channel to use for messages. In this case we use a direct Channel which is a channel that
     * invokes a single subscriber for each sent Message. The invocation will occur in the sender's
     * thread.
     *
     * @return The message channel
     */
    @Bean
    public MessageChannel clientCOutboundChannel() {
        return new DirectChannel();
    }

    /**
     * The outbound gateway that handles the messages using the outbound channel
     *
     * @param clientConnectionFactory It takes the client factory that we created above
     * @return Returns a Message Handler
     */
    @Bean
    @ServiceActivator(inputChannel = "clientCOutboundChannel")
    public MessageHandler clientCOutboundGateway(
            @Qualifier("clientCConnectionFactory") AbstractClientConnectionFactory clientConnectionFactory) {
        TcpOutboundGateway tcpOutboundGateway = new TcpOutboundGateway();
        tcpOutboundGateway.setConnectionFactory(clientConnectionFactory);
        return tcpOutboundGateway;
    }
}
