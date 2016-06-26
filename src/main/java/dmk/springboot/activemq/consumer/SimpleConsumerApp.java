package dmk.springboot.activemq.consumer;

import javax.jms.ConnectionFactory;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.jms.listener.SimpleMessageListenerContainer;
import org.springframework.jms.listener.adapter.MessageListenerAdapter;

@Configuration
@EnableAutoConfiguration
@PropertySource("application-development.properties")
//@Profile("development")
//@EnableIntegration
public class SimpleConsumerApp {

	@Value("${dmk.queue.destinationName:foo.bar}")
	String destinationName;
	
	@Value("${dmk.queue.consumer.threads:4}")
	Integer consumerThreads;
	
	@Bean
	SimpleMessageConsumer simpleMessageConsumer() {
		return new SimpleMessageConsumer();
	}
	
	@Bean
	MessageListenerAdapter messageListener(SimpleMessageConsumer consumer) {
		MessageListenerAdapter adapter = new MessageListenerAdapter(consumer);
		adapter.setDefaultListenerMethod("onMessage");
		return adapter;
	}
	
	@Bean
	SimpleMessageListenerContainer container(MessageListenerAdapter messageListener, ConnectionFactory connectionFactory) {
		SimpleMessageListenerContainer container = new SimpleMessageListenerContainer();
		container.setMessageListener(messageListener);
		container.setConnectionFactory(connectionFactory);
		container.setDestinationName(destinationName);
		container.setConcurrentConsumers(consumerThreads);
		return container;
	}
	
	public static void main(String[] args) {
		SpringApplication.run(SimpleConsumerApp.class, args);
	}
}