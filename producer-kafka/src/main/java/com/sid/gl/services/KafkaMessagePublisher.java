package com.sid.gl.services;

import java.util.concurrent.CompletableFuture;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;

import com.sid.gl.dto.Customer;

@Service
public class KafkaMessagePublisher {

    @Autowired
    private KafkaTemplate<String,Object> kafkaTemplate;

    public void sendMessage(String message){
      CompletableFuture<SendResult<String,Object>> future=  kafkaTemplate.send("kafka-topic",message);
      
      future.whenComplete((result,ex)->{
        if(ex==null){
            System.out.println("Sent message=[ "+message+ "] with offset= ["+result.getRecordMetadata().offset() +"]");
        }else{
            System.out.println(" Unable send message=["+message+" ] due to:  "+ex.getMessage());
        }
      
    });

    }


    public void sendEventTopics(Customer customer){
        try{
            CompletableFuture<SendResult<String,Object>> future=  kafkaTemplate.send("kafka-topic",customer);
            future.whenComplete((result,ex)->{
            if(ex==null){
                System.out.println("Sent message=[ "+customer.toString()+ "] with offset= ["+result.getRecordMetadata().offset() +"]");
            }else{
                System.out.println(" Unable send message=["+customer.toString()+" ] due to:  "+ex.getMessage());
            }
        
      });
        }catch(Exception e){
          System.out.println("Error "+ e.getMessage());
        }
        
  
      }
    
}
