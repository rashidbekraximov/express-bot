package uz.raximov.expressbot.dto;

import lombok.Getter;
import lombok.Setter;
import uz.raximov.expressbot.dto.base.BaseDto;

import java.util.Date;

@Getter
@Setter
public class ClientActionDto extends BaseDto {

    private Long chatId;
    private ClientDto client;
    private String action;
    private Date actionDate;
    private Long data;
}

