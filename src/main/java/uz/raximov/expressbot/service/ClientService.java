package uz.raximov.expressbot.service;


import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.Contact;
import org.telegram.telegrambots.meta.api.objects.User;
import uz.raximov.expressbot.dto.ClientDto;
import uz.raximov.expressbot.entity.Client;
import uz.raximov.expressbot.repository.ClientRepository;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ClientService {

    private static final Logger logger = LoggerFactory.getLogger(ClientService.class);

    private final ClientRepository clientRepository;

    public List<Client> getOnlyUserList(){
        return clientRepository.findAllByIsAdmin(false);
    }

    public ClientDto findClientByUserId(Long userId) {
        Optional<Client> clientEntityOptional = clientRepository.findByTelegramId(userId.toString());
        if (clientEntityOptional.map(Client::getDto).isPresent()){
            logger.info("Klient USERID =>" + userId + " bo'lgan client ma'lumotlari olindi !");
            return clientEntityOptional.map(Client::getDto).get();
        }else{
            logger.warn("Klient USERID =>" + userId + " bo'lgan client ma'lumotlari topilmadi !");
            return null;
        }
    }

    public Client findClientByTelegramId(Long userId) {
        Optional<Client> clientEntityOptional = clientRepository.findByTelegramId(userId.toString());
        if (clientEntityOptional.map(Client::getDto).isPresent()){
            logger.info("Klient USERID =>" + userId + " bo'lgan client ma'lumotlari olindi !");
            return clientEntityOptional.get();
        }else{
            logger.warn("Klient USERID =>" + userId + " bo'lgan client ma'lumotlari topilmadi !");
            return null;
        }
    }

    public ClientDto makeAdmin(Long userId, Long chatId) {
        Optional<Client> clientEntityOptional = clientRepository.findByTelegramId(userId.toString());
        if (clientEntityOptional.isPresent()) {
            clientEntityOptional.get().setAdmin(true);
            clientEntityOptional.get().setGrantAdmin(String.valueOf(chatId));
            try {
                logger.info("Klient ID = > " + clientEntityOptional.get().getTelegramId() + " admin qilib tayinlandi !");
                return clientRepository.save(clientEntityOptional.get()).getDto();
            } catch (Exception e) {
                logger.error("Klient ID = " + clientEntityOptional.get().getTelegramId() + " admin qilishda xatolik yuzaga keldi !");
                return null;
            }
        } else {
            return null;
        }
    }

    public ClientDto deleteAdmin(String userId, Boolean isAdmin){
        Optional<Client> clientEntityOptional = clientRepository.findByIdAndIsAdmin(userId, isAdmin);
        if (clientEntityOptional.isPresent()) {
            clientEntityOptional.get().setAdmin(false);
            try {
                logger.info("Klient ID = > " + clientEntityOptional.get().getTelegramId() + " adminlikdan o'chirildi !");
                return clientRepository.save(clientEntityOptional.get()).getDto();
            } catch (Exception e) {
                logger.error("Klient ID = " + clientEntityOptional.get().getTelegramId() + " adminlikdan o'chirishda xatolik yuzaga keldi !");
                return null;
            }
        } else {
            return null;
        }

    }

    public ClientDto findByPhone(String phone) {
        Optional<Client> clientEntityOptional = clientRepository.findByPhone(phone);
        if (clientEntityOptional.map(Client::getDto).isPresent()){
            logger.info("Klient USER PHONE =>" + phone + " bo'lgan client ma'lumotlari olindi !");
            return clientEntityOptional.map(Client::getDto).get();
        }else{
            logger.warn("Klient USER PHONE =>" + phone + " bo'lgan client ma'lumotlari topilmadi !");
            return null;
        }
    }

    public ClientDto findByIdAndIsAdmin(String id, Boolean isAdmin) {
        Optional<Client> clientEntityOptional = clientRepository.findByIdAndIsAdmin(id,isAdmin);
        if (clientEntityOptional.map(Client::getDto).isPresent()){
            logger.info("Klient Admin Id =>" + id + " bo'lgan admin ma'lumotlari olindi !");
            return clientEntityOptional.map(Client::getDto).get();
        }else{
            logger.warn("Klient Admin Id =>" + id + " bo'lgan client ma'lumotlari topilmadi !");
            return null;
        }
    }

    public void saveUser(User user) {
        Optional<Client> clientEntityOptional = clientRepository.findByTelegramId(user.getId().toString());
        Client clientEntity;
        if (clientEntityOptional.isPresent()) {
            clientEntity = clientEntityOptional.get();
            try{
                logger.info("ChatId =>" + clientEntity.getTelegramId() + " bo'lgan client ma'lumotlari yangilandi !");
                clientRepository.save(clientEntity);
            }catch (Exception e){
                logger.error("Klient ID = " + clientEntity.getTelegramId() + " ma'lumotlarini yangilashda xatolik yuzaga keldi !");
            }
        } else {
            clientEntity = new Client();
            clientEntity.setTelegramId(user.getId().toString());
        }
        clientEntity.setFullName(user.getFirstName() + " " + user.getLastName());
        clientEntity.setUsername(user.getUserName());

        try{
            clientRepository.save(clientEntity);
            logger.info("ChatId =>" + clientEntity.getTelegramId() + " bo'lgan client saqlandi !");
        }catch (Exception e){
            logger.error("Klient ID = " + clientEntity.getTelegramId() + " ma'lumotlarini saqlashda xatolik yuzaga keldi !");
        }
    }

    public ClientDto saveContact(String phone, Long clientId) {
        Optional<Client> clientEntityOptional = clientRepository.findById(clientId);
        Client clientEntity;
        if (clientEntityOptional.isPresent()) {
            clientEntity = clientEntityOptional.get();
            clientEntity.setPhone(phone);
            clientRepository.save(clientEntity);
            return clientEntity.getDto();
        }
        return null;
    }

    public ClientDto saveContact(Contact contact) {
        Optional<Client> clientEntityOptional = clientRepository.findByTelegramId(contact.getUserId().toString());
        Client clientEntity;
        if (clientEntityOptional.isPresent()) {
            clientEntity = clientEntityOptional.get();
            clientEntity.setPhone(contact.getPhoneNumber());
            try{
                logger.info("ChatId =>" + clientEntity.getTelegramId() + " bo'lgan klient kontakti saqlandi !");
                clientRepository.save(clientEntity);
            }catch (Exception e){
                logger.error("Klient ID = " + clientEntity.getTelegramId() + " kontaktini saqlashda xatolik yuzaga keldi !");
            }
            return clientEntity.getDto();
        }
        return null;
    }

    public String findAllByIsAdmin(Boolean isAdmin){
        List<Client> adminList = clientRepository.findAllByIsAdmin(isAdmin);
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < adminList.size(); i++) {
            result.append(adminList.get(i).getId() + ". " + adminList.get(i).getUsername() + "\n" );
        }
        return result.toString();
    }

}
