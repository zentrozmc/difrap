package test;
import com.google.api.services.drive.model.File;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
public class Runt {

	public static void main(String[] args) 
	{
		
		try 
		{
			
			GDriveUtil gdrive = new GDriveUtil();
			String id = "102fp8kIXm9xJ2S-te5386085xQQjPDN5";
			List<File> files = gdrive.listarArchivos(id);
			BufferedWriter bw = new BufferedWriter(new FileWriter(new java.io.File("insert.sql")));
			for(File f:files)
			{
				System.out.println(f.getName() + " "+f.getId() );
				
				Pattern pattern = Pattern.compile("\\((.*?)\\)");
			    Matcher matcher = pattern.matcher(f.getName());
			    String ano="0";
			    if(matcher.find())
			    	ano=matcher.group(1);			 
				String[] nombre = f.getName().split("-");
				
				if(nombre.length>1)
					bw.write("insert into albumes(artista,album,anho,link,youtube,drive) VALUES ('"+nombre[0].trim()+"' , '"+nombre[1].trim()+"', "+ano+",  '',  '' ,'"+f.getId()+"' );\n");
				else
					bw.write("insert into albumes(artista,album,anho,link,youtube,drive) VALUES ('"+nombre[0].trim()+"' , '"+nombre[0].trim()+"',  "+ano+",  '',  '' ,'"+f.getId()+"' );\n");
				
				
				List<File> tracks = gdrive.listarArchivos(f.getId());
				for(File t:tracks) 
				{
					bw.write("insert into tracks(id_album,nombre,duracion,drive) VALUES ((SELECT Auto_increment FROM information_schema.tables WHERE table_name='albumes'), '"+t.getName().replaceAll(".mp3", "").trim()+"',  0,'"+t.getId()+"' );\n");
				}
				
			}
			bw.flush();
			bw.close();
		}catch (Exception e) 
		{
			e.printStackTrace();
		}
		
		
		
	}

}
