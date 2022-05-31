package com.example.starter.base;

import java.util.Map;

import javax.ws.rs.GET;

import org.eclipse.microprofile.rest.client.annotation.RegisterClientHeaders;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

@RegisterRestClient(configKey = "whoami")
@RegisterClientHeaders(AuthClientHeader.class)
public interface WhoAmIService {
    
    @GET
    Map<String, String> whoami();
}