package uz.raximov.expressbot.dto;

import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.beans.BeanUtils;
import uz.raximov.expressbot.dto.base.BaseDto;
import uz.raximov.expressbot.entity.Client;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ClientDto extends BaseDto {

    private String telegramId;

    private String status;

    private String avtoId;

    private String avioId;

    private String username;

    private String fullName;

    private String phone;

    private String phone1;

    private String region;

    private String address;

    private String userInfo;

    private boolean isAdmin;

    private String grantAdmin;

    public Client getDto() {
        Client client = new Client();
        BeanUtils.copyProperties(this, client);
        return client;
    }
}
