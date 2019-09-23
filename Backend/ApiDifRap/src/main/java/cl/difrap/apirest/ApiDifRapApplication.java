package cl.difrap.apirest;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@SpringBootApplication
public class ApiDifRapApplication extends SpringBootServletInitializer
{
	public static void main(String[] args) 
	{
		SpringApplication.run(ApiDifRapApplication.class, args);
	}
	
	@Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) 
	{
        return application.sources(ApiDifRapApplication.class);
    }
	
	 @Bean
	 public WebMvcConfigurer corsConfigurer() 
	 {
        return new WebMvcConfigurer () 
        {
            @Override
            public void addCorsMappings(CorsRegistry registry) 
            {
                registry.addMapping("/**").allowedOrigins("*").allowedMethods("*");
            }
        };
	 }
}