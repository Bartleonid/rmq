import java.io.IOException;
import java.util.concurrent.TimeoutException;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.AMQP;

String QUEUE_NAME = "jmeter_test";

ConnectionFactory factory = new ConnectionFactory();
				factory.setUri("amqp://username:password@qa-rmq-host:5672/queue");
				try {
					Connection connection = factory.newConnection();
					Channel channel = connection.createChannel();
					AMQP.Queue.DeclareOk messages = channel.queueDeclarePassive(QUEUE_NAME);
					log.info("messages count before PURGE: " + messages.getMessageCount());
						int messageCount = messages.getMessageCount();
						while (messageCount > 0) {
							channel.queuePurge(QUEUE_NAME);
							messages = channel.queueDeclarePassive(QUEUE_NAME);
							messageCount = messages.getMessageCount();
							log.info("messages count after PURGE: " + messages.getMessageCount());						
						}
				} catch (IOException e) {
					e.printStackTrace();
				} catch (TimeoutException e) {
					e.printStackTrace();	
				}
