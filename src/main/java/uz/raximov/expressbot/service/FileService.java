package uz.raximov.expressbot.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import uz.raximov.expressbot.entity.FileEntity;
import uz.raximov.expressbot.repository.FileRepository;


@Slf4j
@Service
@RequiredArgsConstructor
public class FileService {

    private final FileRepository fileRepository;

    public FileEntity saveFile(FileEntity file)  {
        try{
            FileEntity saved = fileRepository.save(file);
            log.info("Buyurtma uchun rasm saqlandi !");
            return saved;
        }catch (Exception e){
            log.error("Rasmni saqlashda xatolik !");
            return null;
        }
    }
}
