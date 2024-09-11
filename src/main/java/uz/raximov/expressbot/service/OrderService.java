package uz.raximov.expressbot.service;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.User;
import uz.raximov.expressbot.entity.Client;
import uz.raximov.expressbot.entity.Order;
import uz.raximov.expressbot.repository.OrderRepository;

@Service
@Slf4j
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;

    public void saveOrder(Order order) {
        try {
            orderRepository.save(order);
            log.info("ChatId =>" + order.getClient().getTelegramId() + " bo'lgan client ma'lumotlari yangilandi !");
        } catch (Exception e) {
            log.error("Klient ID = " + order.getClient().getTelegramId() + " ma'lumotlarini yangilashda xatolik yuzaga keldi !");
        }
    }
}
