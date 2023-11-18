//package com.michalenok.wallet.kafka.listener;
//
//import com.michalenok.wallet.kafka.listener.api.MessageListener;
//import com.michalenok.wallet.kafka.schema.Transfer;
//import lombok.RequiredArgsConstructor;
//import lombok.extern.log4j.Log4j2;
//import org.springframework.kafka.annotation.KafkaListener;
//import org.springframework.stereotype.Component;
//
//@Log4j2
//@Component
//@RequiredArgsConstructor
//@KafkaListener(topics = "${kafka.topic.verification}", groupId = "${spring.kafka.consumer.group-id}")
//public class OperationMessageListenerImpl implements MessageListener<Transfer> {
//    @Override
//    public void listenMessage(Transfer message) {
//
//    }
//}
