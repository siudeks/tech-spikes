package my.demo.demo;

import java.util.Properties;

import org.springframework.boot.ApplicationRunner;

@Slf4j
@Component
public class SimpleConsumer implements ApplicationRunner {
  public static void main(String[] args) throws Exception {
     if(args.length == 0){
        System.out.println("Enter topic name");
        return;
     }
     //Kafka consumer configuration settings
     var topicName = args[0].toString();
     var props = new Properties();
     
     props.put("bootstrap.servers", "localhost:9092");
     props.put("group.id", "test");
     props.put("enable.auto.commit", "true");
     props.put("auto.commit.interval.ms", "1000");
     props.put("session.timeout.ms", "30000");
     props.put("key.deserializer", 
        "org.apache.kafka.common.serializa-tion.StringDeserializer");
     props.put("value.deserializer", 
        "org.apache.kafka.common.serializa-tion.StringDeserializer");
     KafkaConsumer<String, String> consumer = new KafkaConsumer
        <String, String>(props);
     
     //Kafka Consumer subscribes list of topics here.
     consumer.subscribe(Arrays.asList(topicName))
     
     //print the topic name
     System.out.println("Subscribed to topic " + topicName);
     int i = 0;
     
     while (true) {
        ConsumerRecords<String, String> records = con-sumer.poll(100);
        for (ConsumerRecord<String, String> record : records)
        
        // print the offset,key and value for the consumer records.
        System.out.printf("offset = %d, key = %s, value = %s\n", 
           record.offset(), record.key(), record.value());
     }
  }
}