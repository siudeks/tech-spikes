package my.demo.demo;

import java.util.Properties;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class SimpleProducer implements ApplicationRunner {

  @Override
  public void run(ApplicationArguments args) throws Exception {
    // Assign topicName to string variable
    var topicName = Const.TOPIC_NAME;

    // create instance for properties to access producer configs
    var props = new Properties();

    // Assign localhost id
    props.put("bootstrap.servers", "localhost:9092");

    // Set acknowledgements for producer requests.
    props.put("acks", "all");

    // If the request fails, the producer can automatically retry,
    props.put("retries", 0);

    // Specify buffer size in config
    props.put("batch.size", 16384);

    // Reduce the no of requests less than 0
    props.put("linger.ms", 1);

    // The buffer.memory controls the total amount of memory available to the
    // producer for buffering.
    props.put("buffer.memory", 33554432);
    props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
    props.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");

    try (var producer = new KafkaProducer<String, String>(props)) {
      for (var i = 0; i < 10; i++)
        producer.send(new ProducerRecord<>(topicName, Integer.toString(i), Integer.toString(i)));
    }
    log.debug("Message sent successfully");
    System.out.println("Message sent successfully");
  }
}