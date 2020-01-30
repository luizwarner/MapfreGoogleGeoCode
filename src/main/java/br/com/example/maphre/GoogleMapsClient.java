package br.com.example.maphre;

import java.io.IOException;

import org.json.JSONArray;
import org.json.JSONObject;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.maps.GeoApiContext;
import com.google.maps.GeocodingApi;
import com.google.maps.errors.ApiException;
import com.google.maps.model.GeocodingResult;
import com.google.maps.model.LatLng;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class GoogleMapsClient {

	public static String retornarCoordenadas(String endereco)throws ApiException, InterruptedException, IOException {
		
		String retorno = null;
		GeoApiContext context = new GeoApiContext.Builder()
			    .apiKey("AIzaSyClTMOVOkTnEOnhWNjWA7estvUnWP6nzNQ")
			    .build();
			
		GeocodingResult[] results = GeocodingApi.geocode(context, "Rua amador bueno 1380, fortaleza ceará").await();
		
		Gson gson = new GsonBuilder().setPrettyPrinting().create();
		//System.out.println(gson.toJson(results[0].addressComponents));
		
		if(results != null && results.length > 0) {
			//System.out.println("Latitude: " + gson.toJson(results[0].geometry.location.lat));
			//System.out.println("Longitude: " + gson.toJson(results[0].geometry.location.lng));
			retorno = "Latitude: " + results[0].geometry.location.lat + " Longitude: " + results[0].geometry.location.lng;
		}
		
		return retorno;
	}
	
	
	public static String retornarEndereco(Double latitude, Double longitude) throws ApiException, 
																			 InterruptedException, 
																			 IOException {
		String retorno = null;
		Gson gson = new GsonBuilder().setPrettyPrinting().create();
		
		GeoApiContext context = new GeoApiContext.Builder()
			    .apiKey("AIzaSyClTMOVOkTnEOnhWNjWA7estvUnWP6nzNQ")
			    .build();
		
		//LatLng coordenadas = new LatLng(-3.8170514, -38.5074612);
		LatLng coordenadas = new LatLng(latitude, longitude);
		GeocodingResult[] results = GeocodingApi.reverseGeocode(context, coordenadas).await();
		
		if(results != null && results.length > 0) {
			//System.out.println("Endereço: " + gson.toJson(results[0].formattedAddress));
			retorno = gson.toJson(results[0].formattedAddress);
		}

		return retorno;
	}
	
	public static String retornarDistanciaEntreDoisPontos(	Double latitudeA, 
															Double longitudeA, 
															Double latitudeB, 
															Double longitudeB) throws IOException {
		String coordenadasA = latitudeA + "," + longitudeA;
		String coordenadasB = latitudeB + "," + longitudeB;
		
		String url="https://maps.googleapis.com/maps/api/distancematrix/json?origins="+coordenadasA+"&destinations="+coordenadasB+"&key=AIzaSyClTMOVOkTnEOnhWNjWA7estvUnWP6nzNQ";
        Request request = new Request.Builder()
            .url(url)
            .build();
        
        OkHttpClient client = new OkHttpClient();
        Response response = client.newCall(request).execute();
        
        String resultadoDistancia = response.body().string();
        
        JSONObject json = new JSONObject(resultadoDistancia);
		JSONArray array = json.getJSONArray("rows");
		String distancia = ((JSONObject)((JSONObject)(((JSONObject)array.get(0)).getJSONArray("elements")).get(0)).get("distance")).get("text").toString();
        
        return distancia;	
	}
	
}
