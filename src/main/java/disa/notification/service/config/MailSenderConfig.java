package disa.notification.service.config;

import java.util.Collections;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.web.client.RestTemplate;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.spring5.SpringTemplateEngine;
import org.thymeleaf.templatemode.TemplateMode;
import org.thymeleaf.templateresolver.ClassLoaderTemplateResolver;
import org.thymeleaf.templateresolver.ITemplateResolver;

import disa.notification.service.service.SeafileService;
import disa.notification.service.service.impl.FileSystemMailService;
import disa.notification.service.service.impl.MailServiceImpl;
import disa.notification.service.service.interfaces.MailService;

@Configuration
public class MailSenderConfig {

	@Bean
	public TemplateEngine emailTemplateEngine() {
		final SpringTemplateEngine templateEngine = new SpringTemplateEngine();
		templateEngine.addTemplateResolver(htmlTemplateResolver());
		return templateEngine;
	}

	@Bean
	@ConditionalOnProperty(name = "app.mailservice", havingValue = "javaMail")
	public MailService mailServiceImpl(Environment env, TemplateEngine templateEngine, MessageSource messageSource,
			SeafileService seafileService) {
		return new MailServiceImpl(templateEngine, messageSource, seafileService);
	}

	@Bean
	public RestTemplate restTemplate() {
		return new RestTemplate();
	}

	@Bean
	@ConditionalOnProperty(name = "app.mailservice", havingValue = "fileSystem")
	public MailService fileSystemMailService(Environment env, MessageSource messageSource) {
		return new FileSystemMailService(messageSource);
	}

	private ITemplateResolver htmlTemplateResolver() {
		final ClassLoaderTemplateResolver templateResolver = new ClassLoaderTemplateResolver();
		templateResolver.setOrder(Integer.valueOf(2));
		templateResolver.setResolvablePatterns(Collections.singleton("html/*"));
		templateResolver.setPrefix("/mail/");
		templateResolver.setSuffix(".html");
		templateResolver.setTemplateMode(TemplateMode.HTML);
		templateResolver.setCharacterEncoding("spring.mail.encoding");
		templateResolver.setCacheable(false);
		return templateResolver;
	}
}
