//package com.hlyf.kafka.message.producer;
//
//import com.google.gson.GsonBuilder;
//import com.hlyf.kafka.message.OrderBasic;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.kafka.core.KafkaTemplate;
//import org.springframework.stereotype.Component;
//
//import javax.annotation.Resource;
//
///**
// * Created by Administrator on 2019-02-21.
// */
//@Component
//public class KafkaProducer {
//
//    private static final Logger logger = LoggerFactory.getLogger(KafkaProducer.class);
//
//    @Autowired
//    private KafkaTemplate<String, String> kafkaTemplate;
//
//    @Value("${kafka.topic.order}")
//    private String topicOrder;
//
//    /**
//     * 发送订单消息
//     *
//     * @param orderBasic 订单信息
//     */
//    public  void sendOrderMessage(OrderBasic orderBasic) {
//        GsonBuilder builder = new GsonBuilder();
//        builder.setPrettyPrinting();
//        builder.setDateFormat("yyyy-MM-dd HH:mm:ss");
//        String message = builder.create().toJson(orderBasic);
//        kafkaTemplate.send(topicOrder, message);
//        logger.info("\n生产消息至Kafka\n" + message);
//    }
//
//}
