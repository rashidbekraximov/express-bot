package uz.raximov.expressbot.entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@Entity
@Table(name = "file")
public class FileEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String fileName;

    private String filePath;

    private long fileSize;

    private LocalDateTime uploadDateTime;

    // Constructors, getters, and setters

    public FileEntity() {
        this.uploadDateTime = LocalDateTime.now();
    }

    public FileEntity(String fileName, String filePath, long fileSize) {
        this.fileName = fileName;
        this.filePath = filePath;
        this.fileSize = fileSize;
        this.uploadDateTime = LocalDateTime.now();
    }
}

