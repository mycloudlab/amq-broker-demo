package br.com.redhat.amq.broker.exemplo1.consumer;

import java.util.Map;
import javax.jms.Session;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.jms.annotation.EnableJms;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.jms.support.converter.MappingJackson2MessageConverter;
import org.springframework.jms.support.converter.MessageConverter;
import org.springframework.jms.support.converter.MessageType;
import org.springframework.messaging.handler.annotation.Headers;
import org.springframework.messaging.handler.annotation.Payload;
import br.com.redhat.amq.broker.exemplo1.Funcionario;

@SpringBootApplication
@ComponentScan(basePackageClasses = ProcessadorFolhaPagamentoApplication.class)
@EnableJms
public class ProcessadorFolhaPagamentoApplication {


  public static void main(String[] args) {
    SpringApplication.run(ProcessadorFolhaPagamentoApplication.class, args);
  }

  @JmsListener(destination = "rh-processar-pagamento")
  public void receiveMessage(Session session, @Headers Map<String, Object> messageAttributes, @Payload Funcionario funcionario) {
    System.out.printf("Processando pagamento do funcionário: %s\n", funcionario);
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
