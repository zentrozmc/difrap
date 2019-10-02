package cl.difrap.apirest.controller;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import cl.difrap.apirest.dto.EnviarDisco;

@RestController
@RequestMapping("/contacto")
public class ContactoCtrl 
{
	private static final Logger log = Logger.getLogger(ContactoCtrl.class);
	
	@Autowired
    private JavaMailSender mailSender;
	
	@RequestMapping(value="/envianostudisco", method=RequestMethod.POST)
	public ResponseEntity<Boolean> envianosTuDisco(@RequestBody EnviarDisco entidad) 
	{
		try 
		{
			sendHTMLMail(entidad);
			return new ResponseEntity<>(true,HttpStatus.OK);
		} catch (MessagingException e) 
		{
			log.error("Error al enviar correo.",e);
			return new ResponseEntity<>(false,HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
	}
	
	private void sendHTMLMail(EnviarDisco disco) throws MessagingException 
    {
		String contenido = "<html>";
		contenido+="<body>";
		contenido+="<p><b>Album<b></p>";
		contenido+="<p><table>";
		contenido+="<tr>";
		contenido+="<td>Nombre: </td><td>"+disco.getAlbum().getAlbum()+"</td>";
		contenido+="</tr><tr>";
		contenido+="<td>Artista: </td><td>"+disco.getAlbum().getArtista()+"</td>";
		contenido+="</tr><tr>";
		contenido+="<td>Año: </td><td>"+disco.getAlbum().getAnho()+"</td>";
		contenido+="</tr><tr>";
		contenido+="<td>Link: </td><td>"+disco.getAlbum().getLink()+"</td>";
		contenido+="</tr><tr>";
		contenido+="<td>Correo Colaborador: </td><td>"+disco.getCorreo()+"</td>";
		contenido+="</tr>";
		contenido+="</body>";
		contenido+="</html>";

        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, false, "utf-8");
        helper.setTo("difundiendoerreape@gmail.com");
        message.setFrom("contacto@difrap.cl");
        helper.setSubject("Disco Enviado Desde Portal");   
        message.setContent(contenido, "text/html");
        mailSender.send(message);
    }

}
