package com.devsuperior.dslist;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class DslistApplication {

	public static void main(String[] args) {
		SpringApplication.run(DslistApplication.class, args);
	}

	// O CORS PODERIA SER CONFIGURADO AQUI TAMBEM, MAS NESSE CASO FOI CRIADO O ARQUIVO "WebConfig" SEPARADO PARA ISSO
	
	// E atualmente o CORS está sendo configurado no arquivo "SecurityConfig" pelo metodo "corsConfigurationSource()"

}
