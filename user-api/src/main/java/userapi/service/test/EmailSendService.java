package userapi.service.test;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import userapi.client.MailgunClient;
import userapi.client.mailgun.SendMailForm;

@Service
@RequiredArgsConstructor
public class EmailSendService {
    private final MailgunClient mailgunClient;

    public String sendMail() {
        SendMailForm form = SendMailForm.builder()
                .from("zerbase-test@test.com")
                .to("saab35@naver.com")
                .subject("Test email from zero base")
                .text("just test")
                .build();
        return mailgunClient.sendMail(form).getBody();
    }
}
