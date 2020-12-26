package ktsnwt_tim8.demo.service;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Date;

import javax.mail.MessagingException;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import com.icegreen.greenmail.util.GreenMailUtil;

import ktsnwt_tim8.demo.model.Post;


@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource("classpath:test.properties")
public class EmailServiceIntegrationTest {

//	@Rule
//	public final GreenMailRule greenMail = new GreenMailRule(ServerSetupTest.SMTP_IMAP);
	
	@Autowired
    private EmailService service;
	
    @Test
    public void testSomething() throws MessagingException {
//        GreenMailUtil.sendTextEmailTest("to@localhost.com", "from@localhost.com", "New post on nova predstava", "body");
//        Post post = new Post(1L, "nova predstava", "SADRZAJ", new Date());
////        SimpleMailMessage emails = service.sendEmailNotification("to@localhost.com", post);
////        MimeMessage[] emails = greenMail.getReceivedMessages();
////        assertEquals(1, emails.length);
//        System.out.println(emails.equals(emails));
//        assertEquals("New post on nova predstava", emails.getSubject());
//        assertEquals("body", emails.getText());
        // ...
    }

}
