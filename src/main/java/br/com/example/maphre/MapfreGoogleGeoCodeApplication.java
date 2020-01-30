package br.com.example.maphre;

import java.io.IOException;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.google.maps.errors.ApiException;

@SpringBootApplication
public class MapfreGoogleGeoCodeApplication {

	public static void main(String[] args) throws ApiException, InterruptedException, IOException {
		SpringApplication.run(MapfreGoogleGeoCodeApplication.class, args);
		
		String coordenadas = GoogleMapsClient.retornarCoordenadas("Avenida Capitão Waldemar Paula Lima 483");
		System.out.println("coordenadas -> " + coordenadas);
		
		//Formato correto: Ex.: -3.7793972 
		String endereco = GoogleMapsClient.retornarEndereco(-3.7793972, -38.499708);
		System.out.println("endereco -> " + endereco);
		
		//Formato correto: Ex.: -3.7793972 
		String distancia = GoogleMapsClient.retornarDistanciaEntreDoisPontos(-3.7793972, -38.499708, -3.8170514, -38.5074612);
		System.out.println("Distância -> " + distancia);
		
	}

}
