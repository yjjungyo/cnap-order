package bookmarket;

import javax.persistence.*;
import org.springframework.beans.BeanUtils;
import java.util.List;

@Entity
//@Document
@Table(name="Order_table")
public class Order {
    // mongo db 적용시엔 id 는 고정값으로 key가 자동 발급되는 필드기 때문에 @Id 나 @GeneratedValue 를 주지 않아도 된다.
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Long id;
    private Long bookId;
    private Long qty;
    private String status;
    private Long customerId;
    private Long paymentId;
    private Long deliveryId;
    public Long getPaymentId() {
        return paymentId;
    }

    public void setPaymentId(Long paymentId) {
        this.paymentId = paymentId;
    }

    public Long getDeliveryId() {
        return deliveryId;
    }

    public void setDeliveryId(Long deliveryId) {
        this.deliveryId = deliveryId;
    }




    @PrePersist
    public void onPrePersist(){
/*
        Ordered ordered = new Ordered();
        this.setStatus("Ordered");
        System.out.println("************************************************\n\nOrder: this.getId() = "+ this.getId());


        BeanUtils.copyProperties(this, ordered);
        ordered.publishAfterCommit();

        //Following code causes dependency to external APIs
        // it is NOT A GOOD PRACTICE. instead, Event-Policy mapping is recommended.

        bookmarket.external.Payment payment = new bookmarket.external.Payment();
        // mappings goes here
        payment.setOrderId(this.getId());
        System.out.println("************************************************\n\nOrder: this.getId() = "+ this.getId());
        payment.setCustomerId(this.getCustomerId());
        payment.setStatus(ordered.getStatus());

        OrderApplication.applicationContext.getBean(bookmarket.external.PaymentService.class)
                .payReq(payment);


        try{
            System.out.println("................ Order . PrePersist ...... sleep .....");
            Thread.currentThread().sleep((long)(800+Math.random()*220));
        }
        catch(InterruptedException e){
            e.printStackTrace();
        }

 */
    }

    @PostUpdate
    //findby 로 검색 후, Save 하면 Update 이벤트가 발생한다. ( Persist 가 아닌 )
    public void onPostUpdate(){
        System.out.println("................Order update event raised by Shipped Event..........!!!");
    }

    @PostRemove
    public void onPostRemove(){
        System.out.println("\n\n\n\n\n\n................Order onPostRemove.........!!!");
        OrderCanceled orderedCanceled = new OrderCanceled();
        BeanUtils.copyProperties(this, orderedCanceled);
        orderedCanceled.setStatus("OrderCanceled");
        orderedCanceled.publishAfterCommit();
    }

    @PostPersist
    public void onPostPersist(){

        Ordered ordered = new Ordered();
        this.setStatus("Ordered");
        BeanUtils.copyProperties(this, ordered);
        ordered.publishAfterCommit();

        //Following code causes dependency to external APIs
        // it is NOT A GOOD PRACTICE. instead, Event-Policy mapping is recommended.

        bookmarket.external.Payment payment = new bookmarket.external.Payment();
        // mappings goes here
        payment.setOrderId(this.getId());
        System.out.println("Order: this.getStatus() = "+ this.getStatus());
        payment.setCustomerId(this.getCustomerId());
        payment.setStatus(ordered.getStatus());

        OrderApplication.applicationContext.getBean(bookmarket.external.PaymentService.class)
            .payReq(payment);


    }

    /**  이게 왜 있지 ???
    @PreUpdate
    public void onPreUpdate(){
        OrderCanceled orderCanceled = new OrderCanceled();
        BeanUtils.copyProperties(this, orderCanceled);
        orderCanceled.publishAfterCommit();


    }
    */

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    public Long getBookId() {
        return bookId;
    }

    public void setBookId(Long bookId) {
        this.bookId = bookId;
    }
    public Long getQty() {
        return qty;
    }

    public void setQty(Long qty) {
        this.qty = qty;
    }
    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
    public Long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }




}
