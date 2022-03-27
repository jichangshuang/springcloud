package com.chinasoft.kafka;

import org.apache.kafka.clients.producer.*;

import java.util.Properties;

public class MsgProducer {

    public static void main(String[] args) {
        Properties p = new Properties();
        p.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        p.put(ProducerConfig.ACKS_CONFIG, "1");
        p.put(ProducerConfig.RETRIES_CONFIG, 3);
        p.put(ProducerConfig.LINGER_MS_CONFIG, 1);
        p.put(ProducerConfig.BATCH_SIZE_CONFIG, 16384);
        p.put(ProducerConfig.BUFFER_MEMORY_CONFIG, 33554432); //32M
        p.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringSerializer");
        p.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringSerializer");

        Producer<String, String> producer = new KafkaProducer<String, String>(p);

        for (int i = 0; i < 5; i++) {
            producer.send(new ProducerRecord<String, String>("my-topic", Integer.toString(i)), new Callback() {
                @Override
                public void onCompletion(RecordMetadata recordMetadata, Exception e) {
                    System.out.println("同步方式发送消息结果: " + recordMetadata.topic() + "| partition-" + recordMetadata.partition() + "| offset-" + recordMetadata.offset());

                }
            });
        }


    }
}
