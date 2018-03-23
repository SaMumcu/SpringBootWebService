package com.sabihamumcu.spring_mongo_firebase_android;

import org.springframework.http.HttpEntity;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.concurrent.CompletableFuture;

@Service
public class AndroidPushNotificationService {

    private static final String FIREBASE_SERVER_KEY = "AAAANvD55WY:APA91bHrrLVkq69Of115Ri_mJcfnBDvbXtEeKG3EIfDxb6wn2U1GR4IivJkzwu7ieFwagbYCbVdHxPDAtaP_k8e1M4gxYgcdUUmcawLpu9-LsF1I1O2ParkARbQZJ7-BPTDQGI-GDPGn";
    private static final String FIREBASE_API_URL = "https://fcm.googleapis.com/fcm/send";

    @Async
    public CompletableFuture<String> send(HttpEntity<String> entity) {

        RestTemplate restTemplate = new RestTemplate();

        ArrayList<ClientHttpRequestInterceptor> interceptors = new ArrayList<>();
        interceptors.add(new HeaderRequestInterceptor("Authorization", "key=" + FIREBASE_SERVER_KEY));
        interceptors.add(new HeaderRequestInterceptor("Content-Type", "application/json"));
        restTemplate.setInterceptors(interceptors);

        String firebaseResponse = restTemplate.postForObject(FIREBASE_API_URL, entity, String.class);

        return CompletableFuture.completedFuture(firebaseResponse);


    }

}