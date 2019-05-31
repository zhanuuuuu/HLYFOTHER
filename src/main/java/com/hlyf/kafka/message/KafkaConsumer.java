//package com.hlyf.kafka.message;
//
//import com.google.gson.Gson;
//import com.google.gson.GsonBuilder;
//import com.google.gson.reflect.TypeToken;
//import com.hlyf.kafka.message.producer.KafkaProducer;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.kafka.annotation.KafkaListener;
//import org.springframework.messaging.handler.annotation.Payload;
//import org.springframework.stereotype.Component;
//
///**
// * Created by Administrator on 2019-02-21.
// */
//@Component
//public class KafkaConsumer {
//    private static final Logger logger = LoggerFactory.getLogger(KafkaConsumer.class);
//
//    @KafkaListener(topics = "${kafka.topic.order}", containerFactory = "kafkaListenerContainerFactory")
//    public void consume(@Payload String message) {
//        GsonBuilder builder = new GsonBuilder();
//        builder.setPrettyPrinting();
//        builder.setDateFormat("yyyy-MM-dd HH:mm:ss");
//        Gson gson = builder.create();
//        OrderBasic orderBasic = gson.fromJson(message, new TypeToken<OrderBasic>() {
//        }.getType());
//        String json = gson.toJson(orderBasic);
//        logger.info("\n接受并消费消息\n" + json);
//    }
//
//}
