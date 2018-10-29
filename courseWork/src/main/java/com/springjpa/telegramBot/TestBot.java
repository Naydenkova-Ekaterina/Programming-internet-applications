package com.springjpa.telegramBot;

import com.springjpa.SpringJpaPostgreSqlApplication;
import com.springjpa.controller.GladiatorController;
import com.springjpa.controller.UserController;
import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.objects.Update;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.exceptions.TelegramApiException;


public class TestBot extends TelegramLongPollingBot {


    @Override
    public String getBotUsername() {

        return "testBot1998_bot";
    }

    @Override
    public String getBotToken() {
        return "489590200:AAFDKNIrRazwPg2uFagKW0OqVALlWN3FYt0";
    }

    @Override
    public void onUpdateReceived(Update update) {

        if (update.hasMessage() && update.getMessage().hasText()) {
            String message_text = update.getMessage().getText();
            long chat_id = update.getMessage().getChatId();

            if(message_text.equals("/help")){
                SendMessage message = new SendMessage()
                        .setChatId(chat_id)
                        .setText("В поле лебеда, и мне напели птички, что мне дана голова, чтоб висеть в петличке.");
                try {
                    execute(message);
                } catch (TelegramApiException e) {
                    e.printStackTrace();
                }
            }

            if(message_text.equals("/top_masters")){
                top_masters(chat_id);
            }

            if(message_text.equals("/top_gladiators")){
                top_gladiators(chat_id);
            }

            if(message_text.equals("/find_nagibator")){
                findNagibator(chat_id);
            }

        }

    }

    public void top_masters(long chat_id){

        UserController userController=SpringJpaPostgreSqlApplication.context.getBean(UserController.class);
        String answer=userController.findByMasterIsNotNullOrderByMoney();

        SendMessage message = new SendMessage()
                .setChatId(chat_id)
                .setText(answer);
        try {
            execute(message);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    public void top_gladiators(long chat_id){
        GladiatorController gladiatorController=SpringJpaPostgreSqlApplication.context.getBean(GladiatorController.class);
        String answer=gladiatorController.sortByWins();
        SendMessage message = new SendMessage()
                .setChatId(chat_id)
                .setText(answer);
        try {
            execute(message);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    public void findNagibator(long chat_id){
        GladiatorController gladiatorController=SpringJpaPostgreSqlApplication.context.getBean(GladiatorController.class);
        String answer=gladiatorController.findNagibator().getName();
        SendMessage message = new SendMessage()
                .setChatId(chat_id)
                .setText(answer);
        try {
            execute(message);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

}
