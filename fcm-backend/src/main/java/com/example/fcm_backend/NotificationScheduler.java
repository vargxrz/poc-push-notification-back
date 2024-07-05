package com.example.fcm_backend;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class NotificationScheduler {

    @Autowired
    private TokenService tokenService;

    @Scheduled(cron = "0 49 9 * * *") //
    public void scheduleDailyNotification() {
        tokenService.sendDailyNotifications();
    }
}

//Explicação da Expressão Cron
//"0 0 9 * * ?": Esta expressão cron execute a tarefa às 9:00 AM todos os dias.
//0: Segundo (0 segundos)
//0: Minuto (0 minutos)
//9: Hora (9 AM)
//*: Dia do mês (qualquer)
//*: Mês (qualquer)
//?: Dia da semana (não especificado)
