package infoshare.app.conf;

import com.google.gson.*;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.springframework.http.*;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by hashcode on 2015/11/10.
 */
public class RestUtil {
    public static final String URL = "https://infoshareapi.hash-code.com";

    private static HttpEntity<?> getRequestHeaders() {
        HttpHeaders headers = new HttpHeaders();
//        headers.add("Authorization", SecurityService.getToken());
        return (HttpEntity<?>) new HttpEntity(headers);
    }

//    public static UserGeneratedToken getGeneratedToken(String url, Credential classTypeObject) {
//
//        CloseableHttpClient httpClient = HttpClients
//                .custom()
//                .setSSLHostnameVerifier(new NoopHostnameVerifier())
//                .build();
//        HttpComponentsClientHttpRequestFactory requestFactory =new HttpComponentsClientHttpRequestFactory();
//        requestFactory.setHttpClient(httpClient);
//        RestTemplate restTemplate = new RestTemplate(requestFactory);
//        HttpHeaders headers = new HttpHeaders();
//        headers.setContentType(MediaType.APPLICATION_JSON);
//
//
//        HttpEntity<Credential> body = new HttpEntity<>(classTypeObject, headers);
//        JsonElement element = new JsonParser()
//                .parse(restTemplate.postForObject(url, body, String.class))
//                .getAsJsonObject();
//        Gson gson = new GsonBuilder()
//                .registerTypeAdapter(Date.class,
//                        (JsonDeserializer<Date>)
//                                (jsonElement, type, jsonDeserializationContext) ->
//                                        new Date(jsonElement.getAsJsonPrimitive().getAsLong()))
//                .create();
//
//        return gson.fromJson(element, UserGeneratedToken.class);
//    }


    public static <T> Set<T> getAll(String fetchUrl, Class<T> classType) {

        CloseableHttpClient httpClient = HttpClients
                .custom()
                .setSSLHostnameVerifier(new NoopHostnameVerifier())
                .build();
        HttpComponentsClientHttpRequestFactory requestFactory =new HttpComponentsClientHttpRequestFactory();
        requestFactory.setHttpClient(httpClient);
        RestTemplate restTemplate = new RestTemplate(requestFactory);

        HttpEntity<?> request = getRequestHeaders();

        ResponseEntity<String> result = restTemplate.exchange(fetchUrl, HttpMethod.GET, request, String.class);
        String  response = result.getBody();
        Set<T> list = new HashSet<>();

        try {
            JsonParser jsonParser = new JsonParser();
            JsonArray json = jsonParser.parse(response).getAsJsonArray();
            Gson gson = new GsonBuilder()
                    .registerTypeAdapter(Date.class, (JsonDeserializer<Date>)
                            (jsonElement, type, jsonDeserializationContext) -> new Date(jsonElement.getAsJsonPrimitive().getAsLong()))
                    .create();

            for (JsonElement element : json) {
                list.add(gson.fromJson(element, classType));
            }
        } catch (Exception e) {
            System.out.println(e+" The Server Error "+e.getMessage());
        }

        return list;
    }



    public static <T> T getById(String fetchUrl, String ID, Class<T> classType) {
        CloseableHttpClient httpClient = HttpClients
                .custom()
                .setSSLHostnameVerifier(new NoopHostnameVerifier())
                .build();
        HttpComponentsClientHttpRequestFactory requestFactory =new HttpComponentsClientHttpRequestFactory();
        requestFactory.setHttpClient(httpClient);
        RestTemplate restTemplate = new RestTemplate(requestFactory);

        HttpEntity<?> request = getRequestHeaders();

        ResponseEntity<String> result = restTemplate.exchange(fetchUrl + ID, HttpMethod.GET, request, String.class);
        String  response = result.getBody();

        try {
            JsonParser jsonParser = new JsonParser();
            JsonElement element = jsonParser.parse(response);
            Gson gson = new GsonBuilder()
                    .registerTypeAdapter(Date.class, (JsonDeserializer<Date>) (jsonElement, type, jsonDeserializationContext) ->
                            new Date(jsonElement.getAsJsonPrimitive().getAsLong()))
                    .create();

            return gson.fromJson(element, classType);
        } catch (Exception e) {
            e.getMessage();
            return null;
        }
    }

    public static <T> T getValue(String fetchUrl, Class<T> classType) {
        CloseableHttpClient httpClient = HttpClients
                .custom()
                .setSSLHostnameVerifier(new NoopHostnameVerifier())
                .build();
        HttpComponentsClientHttpRequestFactory requestFactory =new HttpComponentsClientHttpRequestFactory();
        requestFactory.setHttpClient(httpClient);
        RestTemplate restTemplate = new RestTemplate(requestFactory);
        HttpEntity<?> request = getRequestHeaders();
        ResponseEntity<String> result = restTemplate.exchange(fetchUrl, HttpMethod.GET, request, String.class);
        String  response = result.getBody();


        try {
            JsonParser jsonParser = new JsonParser();
            JsonElement element = jsonParser.parse(response);
            Gson gson = new GsonBuilder()
                    .registerTypeAdapter(Date.class, (JsonDeserializer<Date>) (jsonElement, type, jsonDeserializationContext) ->
                            new Date(jsonElement.getAsJsonPrimitive().getAsLong()))
                    .create();

            return gson.fromJson(element, classType);
        } catch (Exception e) {
            e.getMessage();
            return null;
        }
    }

    public static <T> T save(String url, T classTypeObject, Class<T> classType) {

        CloseableHttpClient httpClient = HttpClients
                .custom()
                .setSSLHostnameVerifier(new NoopHostnameVerifier())
                .build();
        HttpComponentsClientHttpRequestFactory requestFactory =new HttpComponentsClientHttpRequestFactory();
        requestFactory.setHttpClient(httpClient);
        RestTemplate restTemplate = new RestTemplate(requestFactory);
        HttpHeaders headers = new HttpHeaders();
//        headers.add("Authorization", SecurityService.getToken());
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<T> body = new HttpEntity<>(classTypeObject, headers);
        JsonElement element = new JsonParser()
                .parse(restTemplate.postForObject(url, body, String.class))
                .getAsJsonObject();
        Gson gson = new GsonBuilder()
                .registerTypeAdapter(Date.class,
                        (JsonDeserializer<Date>)
                                (jsonElement, type, jsonDeserializationContext) ->
                                        new Date(jsonElement.getAsJsonPrimitive().getAsLong()))
                .create();

        return gson.fromJson(element, classType);
    }

    public static  <T> Set<T> postCollection(String url, Set<T> classTypeObject, Class<Set<T>> classType) {
        CloseableHttpClient httpClient = HttpClients
                .custom()
                .setSSLHostnameVerifier(new NoopHostnameVerifier())
                .build();
        HttpComponentsClientHttpRequestFactory requestFactory =new HttpComponentsClientHttpRequestFactory();
        requestFactory.setBufferRequestBody(false);
        requestFactory.setHttpClient(httpClient);
        RestTemplate restTemplate = new RestTemplate(requestFactory);
        HttpHeaders headers = new HttpHeaders();
//        headers.add("Authorization", SecurityService.getToken());
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<Set<T>> body = new HttpEntity<>(classTypeObject, headers);
        JsonElement element = new JsonParser()
                .parse(restTemplate.postForObject(url, body, String.class)).getAsJsonArray();
        Gson gson = new GsonBuilder()
                .registerTypeAdapter(Date.class,
                        (JsonDeserializer<Date>)
                                (jsonElement, type, jsonDeserializationContext) ->
                                        new Date(jsonElement.getAsJsonPrimitive().getAsLong()))
                .create();
        return gson.fromJson(element, classType);
    }

    public static <T> T update(String url, T classType) {
        CloseableHttpClient httpClient = HttpClients
                .custom()
                .setSSLHostnameVerifier(new NoopHostnameVerifier())
                .build();
        HttpComponentsClientHttpRequestFactory requestFactory =new HttpComponentsClientHttpRequestFactory();
        requestFactory.setHttpClient(httpClient);
        RestTemplate restTemplate = new RestTemplate(requestFactory);
        HttpHeaders headers = new HttpHeaders();
//        headers.add("Authorization", SecurityService.getToken());
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<T> body = new HttpEntity<>(classType, headers);
        restTemplate.put(url, body);
        return classType;
    }


}
