package uz.raximov.expressbot.dto.base;


import lombok.Getter;
import lombok.Setter;

import jakarta.persistence.MappedSuperclass;
import java.io.Serializable;

@Getter
@Setter
@MappedSuperclass
public class BaseDto implements Serializable {

    private Long id;

}

