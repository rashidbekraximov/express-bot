package uz.raximov.expressbot.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uz.raximov.expressbot.entity.Order;

@Repository
public interface OrderRepository extends JpaRepository<Order,Long> {
}
