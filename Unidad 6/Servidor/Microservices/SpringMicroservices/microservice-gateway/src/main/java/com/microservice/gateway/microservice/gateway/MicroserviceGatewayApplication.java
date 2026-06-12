package com.microservice.gateway.microservice.gateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Bean;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import java.util.Arrays;

@EnableDiscoveryClient
@SpringBootApplication
public class MicroserviceGatewayApplication {

	public static void main(String[] args) {
		SpringApplication.run(MicroserviceGatewayApplication.class, args);
	}

	// --- ÚNICO FILTRO GLOBAL DE CORS REQUERIDO PARA REGSITROS Y BAJAS ---
	@Bean
	public CorsFilter corsFilter() {
		CorsConfiguration corsConfig = new CorsConfiguration();

		// Permitimos el acceso al puerto local de tu Frontend en Angular
		corsConfig.setAllowedOrigins(Arrays.asList("http://localhost:4200"));

		// Habilitamos todos los métodos HTTP, liberando POST, PUT, DELETE y OPTIONS (Preflight)
		corsConfig.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS"));

		// Permitimos el paso de todas las cabeceras estándar
		corsConfig.setAllowedHeaders(Arrays.asList("*"));

		// Habilitamos el intercambio de credenciales si la app lo requiere
		corsConfig.setAllowCredentials(true);

		UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		source.registerCorsConfiguration("/**", corsConfig);

		return new CorsFilter(source);
	}
}