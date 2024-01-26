package cl.difrap.apirest.lib;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URISyntaxException;
import java.util.Collections;
import java.util.List;

import org.apache.log4j.Logger;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.extensions.java6.auth.oauth2.AuthorizationCodeInstalledApp;
import com.google.api.client.extensions.jetty.auth.oauth2.LocalServerReceiver;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.client.util.store.FileDataStoreFactory;
import com.google.api.services.drive.Drive;
import com.google.api.services.drive.DriveScopes;
import com.google.api.services.drive.model.File;
import com.google.api.services.drive.model.FileList;

public class GDriveUtil 
{
	private static final Logger LOG = Logger.getLogger(GDriveUtil.class);
    private static final String APPLICATION_NAME = "Google Drive API Java Quickstart";
    private static final JsonFactory JSON_FACTORY = JacksonFactory.getDefaultInstance();
    private static final String TOKENS_DIRECTORY_PATH = "/opt/jboss7/tokensDrive";

    /**
     * Global instance of the scopes required by this quickstart.
     * If modifying these scopes, delete your previously saved tokens/ folder.
     */
    private static final List<String> SCOPES = Collections.singletonList(DriveScopes.DRIVE);
    private static final String CREDENTIALS_FILE_PATH = "/credenciales.json";
    private NetHttpTransport HTTP_TRANSPORT;
    private Drive service;
    
    public GDriveUtil()  
    {
    	try 
    	{
    		HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport();
    		service = new Drive.Builder(HTTP_TRANSPORT, JSON_FACTORY, getCredentials(HTTP_TRANSPORT))
                    .setApplicationName(APPLICATION_NAME)
                    .build();
    	}catch(Exception ex) 
    	{
    		LOG.error("Ha ocurrido un error al conectar con Google Drive",ex);
    	}
    	
    }
    
    
    
    //---------------------MIS METODOS------------------
    //--------------------------------------------------
    public List<File> listarArchivos()
    {
    	try 
    	{
    		FileList result = service.files().list()
	    		.setQ("mimeType = 'application/vnd.google-apps.folder'")
	            .setFields("nextPageToken, files(id, name)")
	            .execute();
	        List<File> files = result.getFiles();
	        return files;
    	}
    	catch(Exception ex) 
    	{
    		LOG.error("Ha ocurrido un error al listar archivos");
    		return null;
    	}     
    }
    
    public ByteArrayOutputStream descargarArchivo(String fileId) 
    {
    	try 
    	{
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            service.files().get(fileId)
                .executeMediaAndDownloadTo(outputStream);
	        return outputStream;
    	}
    	catch(Exception ex) 
    	{
    		LOG.error("Ha ocurrido un error al descargar archivo",ex);
    		return null;
    	} 
    }
    
    
    
    //----------------------------------------METODOS DE GOOGLE--------------------------------
    
    /**
     * Creates an authorized Credential object.
     * @param HTTP_TRANSPORT The network HTTP Transport.
     * @return An authorized Credential object.
     * @throws IOException If the credentials.json file cannot be found.
     * @throws URISyntaxException 
     */
    private Credential getCredentials(final NetHttpTransport HTTP_TRANSPORT) throws IOException, URISyntaxException 
    {
        // Load client secrets.
        InputStream in = GDriveUtil.class.getResourceAsStream(CREDENTIALS_FILE_PATH);
        LOG.info("Obteniendo Credenciales de "+CREDENTIALS_FILE_PATH);
        if (in == null) {
            throw new FileNotFoundException("Resource not found: " + CREDENTIALS_FILE_PATH);
        }
        GoogleClientSecrets clientSecrets = GoogleClientSecrets.load(JSON_FACTORY, new InputStreamReader(in));
        LOG.info("Continuamos ahora buscamos el token en  "+TOKENS_DIRECTORY_PATH);
        // Build flow and trigger user authorization request.
        GoogleAuthorizationCodeFlow flow = new GoogleAuthorizationCodeFlow.Builder(
                HTTP_TRANSPORT, JSON_FACTORY, clientSecrets, SCOPES)
                .setDataStoreFactory(new FileDataStoreFactory(new java.io.File(TOKENS_DIRECTORY_PATH)))
                .setAccessType("offline")
                .build();
        LocalServerReceiver receiver = new LocalServerReceiver.Builder().setPort(8888).build();
        return new AuthorizationCodeInstalledApp(flow, receiver).authorize("user");
    }
}