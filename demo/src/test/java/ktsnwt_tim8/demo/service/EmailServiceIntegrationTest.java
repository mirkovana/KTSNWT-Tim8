package ktsnwt_tim8.demo.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Store;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.jvnet.mock_javamail.Mailbox;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.PropertySource;
import org.springframework.scheduling.annotation.AsyncConfigurer;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.test.context.junit4.SpringRunner;

import ktsnwt_tim8.demo.model.Post;

@Configuration
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
//@EnableScheduling
//@Profile("non-async")

public class EmailServiceIntegrationTest implements AsyncConfigurer {
	@Autowired
	private EmailService mailSender;

	@Autowired
	private ThreadPoolTaskExecutor executor;

	@Before
	public void setUp() {
		// clear Mock JavaMail box
		Mailbox.clearAll();
	}

	@Test
	public void testSendInRegualarJavaMail() throws MessagingException, IOException, IllegalStateException, InterruptedException {

		String subject = "New post on TItLE";
		String body = "Context\n" + "Vise o ovome pogledajte na : ..........";

		Post post = new Post(65L, "TItLE", "Context", new Date());
		mailSender.sendEmailNotification("test.dest@nutpan.com", post);
		executor.getThreadPoolExecutor().awaitTermination(5, TimeUnit.SECONDS);

		Session session = Session.getDefaultInstance(new Properties());
		Store store = session.getStore("pop3");
		store.connect("nutpan.com", "test.dest", "password");

		Folder folder = store.getFolder("inbox");

		folder.open(Folder.READ_ONLY);
		Message[] msg = folder.getMessages();

		assertTrue(msg.length == 1);
		assertEquals(subject, msg[0].getSubject());
		assertEquals(body, msg[0].getContent());
		folder.close(true);
		store.close();
	}

	@Test
	public void testSendInMockWay() throws MessagingException, IOException, InterruptedException {

		String subject = "New post on TItLE";
		String body = "Context\n" + "Vise o ovome pogledajte na : ..........";

		Post post = new Post(65L, "TItLE", "Context", new Date());
		mailSender.sendEmailNotification("test.dest@nutpan.com", post);

		executor.getThreadPoolExecutor().awaitTermination(5, TimeUnit.SECONDS);

		List<Message> inbox = Mailbox.get("test.dest@nutpan.com");
		assertTrue(inbox.size() == 1);
		assertEquals(subject, inbox.get(0).getSubject());
		assertEquals(body, inbox.get(0).getContent());

	}
}