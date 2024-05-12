package userapi.client;

import feign.Response;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.cloud.openfeign.SpringQueryMap;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import userapi.client.mailgun.SendMailForm;

@FeignClient(name = "mailgun", url = "https://api.mailgun.net/v3/")
@Qualifier("mailgun")
public interface MailgunClient {

    @PostMapping("sandbox874e64426bc84a82b9a15f7fba8aa964.mailgun.org/messages")
    ResponseEntity<String> sendMail(@SpringQueryMap SendMailForm form);
}
