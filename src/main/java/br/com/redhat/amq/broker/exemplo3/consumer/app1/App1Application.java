package br.com.redhat.amq.broker.exemplo3.consumer.app1;

import java.util.HashMap;
import java.util.Map;
import javax.jms.ConnectionFactory;
import javax.jms.Session;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jms.DefaultJmsListenerContainerFactoryConfigurer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.jms.annotation.EnableJms;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.jms.config.DefaultJmsListenerContainerFactory;
import org.springframework.jms.config.JmsListenerContainerFactory;
import org.springframework.jms.support.converter.MappingJackson2MessageConverter;
import org.springframework.jms.support.converter.MessageConverter;
import org.springframework.jms.support.converter.MessageType;
import org.springframework.messaging.handler.annotation.Headers;
import org.springframework.messaging.handler.annotation.Payload;

@SpringBootApplication
@ComponentScan(basePackageClasses = App1Application.class)
@EnableJms
public class App1Application {


  public static void main(String[] args) {
    SpringApplication.run(App1Application.class, args);
  }

  @JmsListener(destination = "config", containerFactory = "jmsSubscricaoNAODuravel")
  public void receiveMessage(Session session, @Headers Map<String, Object> messageAttributes, @Payload HashMap<String,String> config) {
    System.out.printf("App1 - Recarregando config: %s\n", config);
  }

  @Bean
  public MessageConverter jacksonJmsMessageConverter() {

    // usamos o objeto MappingJackson2MessageConverter com o objetivo de controlar a serialização dos
    // objetos, a mensagem é colocada no formato json e a propriedade _type_ é usada para identificar a
    // classe pertencente a mensagem

    MappingJackson2MessageConverter converter = new MappingJackson2MessageConverter();
    converter.setTargetType(MessageType.TEXT);
    converter.setTypeIdPropertyName("_type_");
    return converter;
  }


  @Bean
  public JmsListenerContainerFactory<?> jmsSubscricaoNAODuravel(ConnectionFactory connectionFactory, DefaultJmsListenerContainerFactoryConfigurer configurer) {

    DefaultJmsListenerContainerFactory factory = new DefaultJmsListenerContainerFactory();
    factory.setSessionAcknowledgeMode(Session.AUTO_ACKNOWLEDGE);
    configurer.configure(factory, connectionFactory);
    factory.setConcurrency("1");
    factory.setPubSubDomain(true);
    factory.setSubscriptionDurable(false);
    factory.setSubscriptionShared(false);
    return factory;
  }



}
