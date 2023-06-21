package cl.difrap.apirest.controller;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import cl.difrap.apirest.dao.TrackDao;
import cl.difrap.apirest.dto.Track;
import cl.difrap.apirest.lib.GDriveUtil;
import cl.difrap.biblioteca.Controlador;

@RestController
@RequestMapping("/track")
public class TrackCtrl extends Controlador<TrackDao,Track>{

	private static final Logger log = LoggerFactory.getLogger(TrackCtrl.class);
	@GetMapping(value = "/play/{driveID}")
	public ResponseEntity<Object> escuchar(@PathVariable String driveID)
	{
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.parseMediaType("application/octet-stream"));
		
		String filename = driveID;
		headers.setContentDispositionFormData(filename, filename);
		headers.set("Accept-Ranges","bytes");
		headers.setCacheControl("must-revalidate, post-check=0, pre-check=0");
		List<String> exposeHeader = new ArrayList<>();
		exposeHeader.add("Content-Disposition");
		headers.setAccessControlExposeHeaders(exposeHeader);
		try
		{
			GDriveUtil gdrive = new GDriveUtil();
			ByteArrayOutputStream bos = gdrive.descargarArchivo(driveID);
			headers.setContentLength(bos.size());
			return new ResponseEntity<>(bos.toByteArray(), headers, HttpStatus.OK);
		}
		catch (Exception e) {
			log.error("Error al reproducir track",e);
			return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
		}
	}
	
}
