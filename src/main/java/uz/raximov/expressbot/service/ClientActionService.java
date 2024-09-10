package uz.raximov.expressbot.service;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import uz.raximov.expressbot.dto.ClientActionDto;
import uz.raximov.expressbot.entity.ClientAction;
import uz.raximov.expressbot.repository.ClientActionRepository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class ClientActionService {

    private static final Logger logger = LoggerFactory.getLogger(ClientActionService.class);

    private final ClientActionRepository clientActionRepository;

    @Transactional(readOnly = true)
    public ClientActionDto findLastActionByChatId(Long chatId) {
        Optional<ClientAction> clientActionEntityOptional = clientActionRepository.findFirstByChatIdOrderByActionDateDesc(chatId);
        if (clientActionEntityOptional.isPresent()) {
            ClientAction actionEntity = clientActionEntityOptional.get();
            logger.info("Klient CHATID => " + chatId + " oxirgi harakati olindi !");
            return actionEntity.getDto();
        }
        logger.warn("Klient CHATID => " + chatId + " oxirgi harakati topilmadi  !");
        return null;
    }

    @Transactional(readOnly = true)
    public ClientActionDto findARowBeforeLastActionByChatId(Long chatId) {
        List<ClientAction> clientActionEntityOptional = clientActionRepository.findAllByChatIdOrderByActionDateDesc(chatId);
        if (clientActionEntityOptional.get(1) != null) {
            ClientAction actionEntity = clientActionEntityOptional.get(1);
            logger.info("Klient CHATID => " + chatId + " oxirgi harakati olindi !");
            return actionEntity.getDto();
        }
        logger.warn("Klient CHATID => " + chatId + " oxirgi harakati topilmadi  !");
        return null;
    }

    public void saveAction(String action, Long chatId, Long clientId) {
        saveAction(action, chatId, clientId, null);
    }

    public void saveAction(String action, Long chatId, Long clientId, Long data) {
        ClientAction clientActionEntity = new ClientAction();
        clientActionEntity.setChatId(chatId);
        clientActionEntity.setActionDate(new Date());
        clientActionEntity.setAction(action);
        clientActionEntity.setClientId(clientId);
        clientActionEntity.setData(data);
        try{
            logger.info("Klient CHATID => " + chatId + " bo'lgan client harakati saqlandi !");
            clientActionRepository.save(clientActionEntity);
        }catch (Exception e){
            logger.error("Klient CHATID => " + chatId + " Actionni saqlashda xatolik yuzaga keldi !");
        }
    }


    public void clearActions(Long chatId) {
        try{
            clientActionRepository.deleteAllByChatId(chatId);
            logger.info("Klient CHATID =>" + chatId + " bo'lgan klient harakatlari o'chirildi !");
        }catch (Exception e){
            logger.error("Klient CHATID => " + chatId + " harakatlari o'chirishda xatolik yuzaga keldi !");
        }
    }

    public void updateAction(ClientActionDto action) {
        ClientAction clientActionEntity = new ClientAction();
        BeanUtils.copyProperties(action, clientActionEntity);
        if (action.getClient() != null) {
            clientActionEntity.setClientId(action.getClient().getId());
        }
        try{
            logger.info("Klient CHATID => " + action.getChatId() + " bo'lgan client harakati yangilandi !");
            clientActionRepository.save(clientActionEntity);
        }catch (Exception e){
            logger.error("Klient CHATID => " + action.getChatId() + " Actionni yangilashda xatolik yuzaga keldi !");
        }
    }

    public void deleteAction(ClientActionDto action) {
        try{
            logger.info("Klient CHATID => " + action.getChatId() + " bo'lgan client harakati o'chirildi !");
            clientActionRepository.deleteById(action.getId());
        }catch (Exception e){
            logger.error("Klient CHATID => " + action.getChatId() + "Actionni o'chirishda xatolik yuzaga keldi !");
        }
    }
}

