package uz.raximov.expressbot.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uz.raximov.expressbot.entity.FileEntity;

@Repository
public interface FileRepository extends JpaRepository<FileEntity,Long> {
}
