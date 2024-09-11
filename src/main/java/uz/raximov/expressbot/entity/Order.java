package uz.raximov.expressbot.entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.beans.BeanUtils;
import org.springframework.data.annotation.CreatedDate;
import uz.raximov.expressbot.dto.OrderDto;
import uz.raximov.expressbot.entity.base.BaseEntity;
import uz.raximov.expressbot.enums.PaymentStatus;

import java.time.OffsetDateTime;

@Entity
@Table(name = "sj_order")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Order extends BaseEntity {

    @Column(name = "chatId")
    private Long chatId;

    @Column(name = "client_id")
    private Long clientId;

    @Column(name = "count")
    private double count;

    @Column(name = "weight")
    private double weight;

    @Column(name = "price")
    private double price;

    @Column(name = "flightId")
    private String flightId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "client_id", insertable = false, updatable = false)
    private Client client;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "image_id", insertable = false, updatable = false)
    private FileEntity file;

    @CreatedDate
    @Column(nullable = false, updatable = false)
    private OffsetDateTime dateCreated;

    @Enumerated(value = EnumType.STRING)
    @Column(name = "status")
    private PaymentStatus status = PaymentStatus.NOT_PAYED;

    public OrderDto getDto() {
        OrderDto orderDTO = new OrderDto();
        BeanUtils.copyProperties(this, orderDTO);
        return orderDTO;
    }

    public Order(Long chatId,Client clientEntity,Long clientId,FileEntity file){
        this.chatId = chatId;
        this.client = clientEntity;
        this.clientId = clientId;
        this.file = file;
        dateCreated = OffsetDateTime.now();
    }
}
