package br.com.redhat.amq.broker.exemplo1.producer;

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
import br.com.redhat.amq.broker.exemplo1.Funcionario;

@SpringBootApplication
@EnableScheduling 
@ComponentScan(basePackageClasses = RecursosHumanosApplication.class)
public class RecursosHumanosApplication {

  public static void main(String[] args) {
    SpringApplication.run(RecursosHumanosApplication.class, args);
  }

  @Autowired
  private JmsTemplate jmsTemplate; 

  @Scheduled(fixedRate = 2000)
  public void enviaPagamentoParaFuncionario() {
    Funcionario funcionario = new Funcionario(1,"Carlos Alberto");

    System.out.printf("Enviar pagamento para o funcionário %s\n",funcionario);
    jmsTemplate.convertAndSend("rh-processar-pagamento", funcionario);
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
