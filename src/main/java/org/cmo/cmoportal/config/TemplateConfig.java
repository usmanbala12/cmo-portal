package org.cmo.cmoportal.config;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.HttpRequest;
import com.google.api.client.http.HttpRequestInitializer;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.json.gson.GsonFactory;
import com.google.api.services.drive.Drive;
import com.google.api.services.drive.DriveScopes;
import com.google.api.services.gmail.Gmail;
import com.google.api.services.gmail.GmailScopes;
import nz.net.ultraq.thymeleaf.layoutdialect.LayoutDialect;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.thymeleaf.spring6.SpringTemplateEngine;

import java.io.FileInputStream;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.Collections;

@Configuration
public class TemplateConfig {

    private static final GsonFactory JSON_FACTORY = GsonFactory.getDefaultInstance();
    private static final String KEY_FILE_LOCATION = "/home/codegeek/Downloads/cmoportal-c3232d99d73c.json";

    @Bean
    public Drive drive() throws GeneralSecurityException, IOException {
        HttpTransport httpTransport = GoogleNetHttpTransport.newTrustedTransport();
        GoogleCredential credential = GoogleCredential
                .fromStream(new FileInputStream(KEY_FILE_LOCATION))
                .createScoped(DriveScopes.all());

        // Construct the drive service object.
        Drive service = new Drive.Builder(httpTransport, JSON_FACTORY, credential)
                .setApplicationName("cmoportal").build();
        return service;
    }

    @Bean
    public Gmail gmail() throws GeneralSecurityException, IOException {
        HttpTransport httpTransport = GoogleNetHttpTransport.newTrustedTransport();
        GoogleCredential credentialFromJson = GoogleCredential
                .fromStream(new FileInputStream(KEY_FILE_LOCATION))
                .createScoped(Collections.singletonList(GmailScopes.GMAIL_SEND));

        GoogleCredential credential = new GoogleCredential.Builder()
                .setTransport(httpTransport)
                .setJsonFactory(JSON_FACTORY)
                .setServiceAccountId(credentialFromJson.getServiceAccountId())
                .setServiceAccountPrivateKey(credentialFromJson.getServiceAccountPrivateKey())
                .setServiceAccountScopes(GmailScopes.all())
                .setServiceAccountUser("usmanxp12@gmail.com").build();

        // Construct the drive service object.
        Gmail service = new Gmail.Builder(httpTransport, JSON_FACTORY, credential)
                .setApplicationName("cmoportal").build();
        return service;
    }

}
