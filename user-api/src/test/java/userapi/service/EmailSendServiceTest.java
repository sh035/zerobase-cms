package userapi.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import userapi.client.MailgunClient;
import userapi.service.test.EmailSendService;

@SpringBootTest
class EmailSendServiceTest {

    @Autowired
    private MailgunClient mailgunClient;

    @Test
    public void EmailTest() {
        // 숙제
        mailgunClient.sendMail(null);
//        String response = emailSendService.sendMail();
//        System.out.println(response);
    }

}