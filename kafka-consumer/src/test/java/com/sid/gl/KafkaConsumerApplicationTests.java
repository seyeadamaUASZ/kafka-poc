package com.sid.gl;

import com.sid.gl.dto.Customer;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.KafkaContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;

import java.time.Duration;
import java.util.concurrent.TimeUnit;

import static org.awaitility.Awaitility.await;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Testcontainers
public class KafkaConsumerApplicationTests {
	Logger logger = LoggerFactory.getLogger(KafkaConsumerApplicationTests.class);
	@Container
	static KafkaContainer container= new KafkaContainer(DockerImageName.parse("confluentinc/cp-kafka:latest"));

	@DynamicPropertySource
	public static void  initKafkaProperties(DynamicPropertyRegistry registry){
		registry.add("spring.kafka.bootstrap-servers",container::getBootstrapServers);

	}

	@Autowired
	private KafkaTemplate<String,Object> kafkaTemplate;

	@Test
	public void testConsume(){
		logger.info("testConsume method event started... ");
		Customer customer = new Customer(123,"diallo henry","diallo@gmail.com","d89399");
        kafkaTemplate.send("kafka-topic",customer);
	    logger.info("test consume execution ended");
		await().pollInterval(Duration.ofSeconds(3))
				.atMost(10, TimeUnit.SECONDS).untilAsserted(() -> {
					// assert statement
				});
	}

}
