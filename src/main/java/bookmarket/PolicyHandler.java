package bookmarket;

import bookmarket.config.kafka.KafkaProcessor;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class PolicyHandler{
    @StreamListener(KafkaProcessor.INPUT)
    public void onStringEventListener(@Payload String eventString){

    }


    @Autowired OrderRepository orderRepository;  //


    @StreamListener(KafkaProcessor.INPUT)
    public void wheneverPaid_UpdateStatus(@Payload Paid paid){

        if(paid.isMe()){
            System.out.println("##### listener UpdateStatus : " + paid.toJson());


            System.out.println("##### listener UpdateStatus : " + paid.toJson());
            Optional<Order> orderOptional = orderRepository.findById(paid.getOrderId());
            Order order = orderOptional.get();
            order.setPaymentId(paid.getId());

            order.setStatus(paid.getStatus());

            orderRepository.save(order); //repository에서 find 하고 save 하면 Update 이벤트가 발생된다.



        }
    }
    @StreamListener(KafkaProcessor.INPUT)
    public void wheneverShipped_UpdateStatus(@Payload Shipped shipped){

        if(shipped.isMe()){
            System.out.println("##### listener UpdateStatus : " + shipped.toJson());

            Optional<Order> orderOptional = orderRepository.findById(shipped.getOrderId());
            Order order = orderOptional.get();
            order.setDeliveryId(shipped.getId());
            order.setStatus(shipped.getStatus());

            orderRepository.save(order); //repository에서 find 하고 save 하면 Update 이벤트가 발생된다.


        }
    }
    @StreamListener(KafkaProcessor.INPUT)
    public void wheneverDeliveryCanceled_UpdateStatus(@Payload DeliveryCanceled deliveryCanceled){

        if(deliveryCanceled.isMe()){
            System.out.println("##### listener UpdateStatus : " + deliveryCanceled.toJson());

            Optional<Order> orderOptional = orderRepository.findById(deliveryCanceled.getOrderId());
            Order order = orderOptional.get();
            order.setStatus(deliveryCanceled.getStatus());

            orderRepository.save(order); //repository에서 find 하고 save 하면 Update 이벤트가 발생된다.


        }
    }

}
