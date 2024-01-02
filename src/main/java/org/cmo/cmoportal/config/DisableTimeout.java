package org.cmo.cmoportal.config;

import com.google.api.client.http.HttpRequest;
import com.google.api.client.http.HttpRequestInitializer;
import org.springframework.stereotype.Component;

@Component
public class DisableTimeout implements HttpRequestInitializer {
     public void initialize(HttpRequest request) { request.setConnectTimeout(0); request.setReadTimeout(0); }
}
