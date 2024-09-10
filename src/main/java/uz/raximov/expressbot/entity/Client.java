package uz.raximov.expressbot.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import jakarta.persistence.*;
import org.springframework.beans.BeanUtils;
import uz.raximov.expressbot.dto.ClientDto;

import java.time.LocalDateTime;

@Entity
@Table(name = "sj_client")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Client {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "telegram_id", nullable = false, unique = true)
    private String telegramId;

    @Column(name = "status")
    private String status;

    @Column(name = "username", nullable = false)
    private String username;

    @Column(name = "avto_id")
    private String avtoId;

    @Column(name = "avio_id")
    private String avioId;

    @Column(name = "full_name", nullable = false)
    private String fullName;

    @Column(name = "phone")
    private String phone;

    @Column(name = "phone1")
    private String phone1;

    @Column(name = "region")
    private String region;

    @Column(name = "address")
    private String address;

    @Column(name = "user_info")
    private String userInfo;

    @Column(name = "isAdmin", columnDefinition = "BOOLEAN DEFAULT FALSE")
    private boolean isAdmin;

    @Column(name = "createDate",columnDefinition = "TIMESTAMP DEFAULT NOW()")
    private LocalDateTime createDate = LocalDateTime.now();

    public ClientDto getDto() {
        ClientDto clientDto = new ClientDto();
        BeanUtils.copyProperties(this, clientDto);
        return clientDto;
    }
}
