package dmk.springboot.activemq.consumer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SimpleMessageConsumer {
	private static final Logger logger = LoggerFactory.getLogger(SimpleMessageConsumer.class);
	
	public void onMessage(final String msg) {
		logger.info(msg);
	}
}
