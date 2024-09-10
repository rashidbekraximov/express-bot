package uz.raximov.expressbot.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.beans.BeanUtils;
import uz.raximov.expressbot.dto.ClientActionDto;
import uz.raximov.expressbot.entity.base.BaseEntity;

import java.util.Date;

@Entity
@Table(name = "sj_client_action")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ClientAction extends BaseEntity {

    @Column(name = "clientId")
    private Long clientId;

    @Column(name = "chatId")
    private Long chatId;

    @Column(name = "cartMessageId")
    private Integer cartMessageId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "clientId", insertable = false, updatable = false)
    private Client client;

    private String action;

    @Column(name = "action_date")
    private Date actionDate;

    private Long data;

    public ClientActionDto getDto() {
        ClientActionDto dto = new ClientActionDto();
        BeanUtils.copyProperties(this,dto);
        if (getClient() != null) {
            dto.setClient(getClient().getDto());
        }
        return dto;
    }
}

