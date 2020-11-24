package cl.difrap.productos.tiendaark;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.PropertySource;

@SpringBootApplication
@PropertySource({"classpath:application.properties"})
public class ApiTiendaArkApplication extends SpringBootServletInitializer
{
	public static void main(String[] args) 
	{
		SpringApplication.run(ApiTiendaArkApplication.class, args);
	}

	@Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) 
	{
        return application.sources(ApiTiendaArkApplication.class);
    }
}