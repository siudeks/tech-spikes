package my.demo.demo;

import java.util.Arrays;
import java.util.Properties;

import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.springframework.beans.BeansException;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.stereotype.Component;

@Component
public class SimpleConsumer implements ApplicationRunner, ApplicationContextAware {

   @Override
   public void run(ApplicationArguments args) throws Exception {
      //Kafka consumer configuration settings
      var topicName = Const.TOPIC_NAME;
      System.out.println("Start");
      var props = new Properties();
      
      props.put("bootstrap.servers", "localhost:9092");
      props.put("group.id", "test");
      props.put("enable.auto.commit", "true");
      props.put("auto.commit.interval.ms", "1000");
      props.put("session.timeout.ms", "30000");
      props.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
      props.put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
      var consumer = new KafkaConsumer<String, String>(props);
      
      //Kafka Consumer subscribes list of topics here.
      consumer.subscribe(Arrays.asList(topicName));
      
      //print the topic name
      System.out.println("Subscribed to topic " + topicName);
      int i = 0;
      
      while (true) {
         var records = consumer.poll(100);
         for (var record : records)
         
         // print the offset,key and value for the consumer records.
         System.out.printf("offset = %d, key = %s, value = %s\n", 
            record.offset(), record.key(), record.value());
      }
   }

   private ConfigurableApplicationContext context;

   @Override
   public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
      context = (ConfigurableApplicationContext) applicationContext;
   }
}