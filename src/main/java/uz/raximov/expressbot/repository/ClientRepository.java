package uz.raximov.expressbot.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uz.raximov.expressbot.entity.Client;

import java.util.List;
import java.util.Optional;

@Repository
public interface ClientRepository extends JpaRepository<Client,Long> {

    Optional<Client> findByTelegramId(String telegramId);

    Optional<Client> findByPhone(String telegramId);

    List<Client> findAllByIsAdmin(boolean isAdmin);

}
