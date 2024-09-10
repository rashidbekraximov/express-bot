package uz.raximov.expressbot.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import uz.raximov.expressbot.entity.ClientAction;

import java.util.List;
import java.util.Optional;

@Repository
public interface ClientActionRepository extends JpaRepository<ClientAction,Long>, JpaSpecificationExecutor<ClientAction> {

    Optional<ClientAction> findFirstByChatIdOrderByActionDateDesc(Long chatId);

    List<ClientAction> findAllByChatIdOrderByActionDateDesc(Long chatId);

    Optional<ClientAction> findFirstByClientIdOrderByActionDateDesc(Long clientId);

    @Transactional
    void deleteAllByChatId(Long chatId);

//    @Query(value = "from ClientActionEntity ca where ca.clientId=?1 and lower(ca.action) like lower('%'|| ?2||'%')")
//    Page<ClientAction> findAllByClientId(Long clientId,String action, Pageable pageable);


}
