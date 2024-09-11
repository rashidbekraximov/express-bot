package uz.raximov.expressbot.dto;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import uz.raximov.expressbot.dto.base.BaseDto;
import uz.raximov.expressbot.entity.Client;
import uz.raximov.expressbot.entity.FileEntity;
import uz.raximov.expressbot.enums.PaymentStatus;

import java.time.OffsetDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class OrderDto extends BaseDto {

    private Long chatId;

    private Long clientId;

    private double count;

    private double weight;

    private double price;

    private String flightId;

    private Client client;

    private FileEntity file;

    @Enumerated(value = EnumType.STRING)
    private PaymentStatus status = PaymentStatus.NOT_PAYED;
}
