package cl.difrap.productos.tiendaark.config;

import javax.mail.Session;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import cl.difrap.biblioteca.util.BuscarObjectoJndi;
@Configuration
public class ConfiguracionMail 
{
	@Bean
    public JavaMailSenderImpl mailSender()
    {
 		BuscarObjectoJndi bodd= new BuscarObjectoJndi();
    	Session session = bodd.obtenerJavaMailSession("java:/mailDifRap");
    	JavaMailSenderImpl senderImpl = new JavaMailSenderImpl();
    	senderImpl.setSession(session);
    	return senderImpl;
    }
}
