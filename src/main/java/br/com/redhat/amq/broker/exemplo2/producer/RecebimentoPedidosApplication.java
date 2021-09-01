package br.com.redhat.amq.broker.exemplo2.producer;

import javax.jms.ConnectionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.support.converter.MappingJackson2MessageConverter;
import org.springframework.jms.support.converter.MessageConverter;
import org.springframework.jms.support.converter.MessageType;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import br.com.redhat.amq.broker.exemplo2.NovoPedidoRecebidoEvent;

@SpringBootApplication
@EnableScheduling
@ComponentScan(basePackageClasses = RecebimentoPedidosApplication.class)
public class RecebimentoPedidosApplication {

  public static void main(String[] args) {
    SpringApplication.run(RecebimentoPedidosApplication.class, args);
  }

  @Autowired
  private JmsTemplate jmsTemplate;

  @Scheduled(fixedRate = 2000)
  public void recebeNovoPedido() {

    /// Lógica de recebimento de um novo pedido

    NovoPedidoRecebidoEvent event = new NovoPedidoRecebidoEvent(123456, "Carlos Alberto");

    System.out.printf("Disparando evento: %s\n", event);
    jmsTemplate.setPubSubDomain(true);
    jmsTemplate.convertAndSend("evento-novo-pedido-recebido", event);
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

 

}
