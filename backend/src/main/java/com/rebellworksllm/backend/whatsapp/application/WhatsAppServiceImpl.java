package com.rebellworksllm.backend.whatsapp.application;

import com.rebellworksllm.backend.matching.domain.Vacancy;
import com.rebellworksllm.backend.whatsapp.config.WhatsAppCredentials;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

@Service
public class WhatsAppServiceImpl implements WhatsAppService {

    private final WhatsAppCredentials credentials;

    public WhatsAppServiceImpl(WhatsAppCredentials credentials) {
        this.credentials = credentials;
    }

    public void sendWithVacancyTemplate(String phoneNumber,
                                        String name,
                                        Vacancy vac1,
                                        Vacancy vac2,
                                        Vacancy vac3) {
        try {
            String jsonBody = """
        {
          "messaging_product": "whatsapp",
          "to": "31657771880",
          "type": "template",
          "template": {
            "name": "rebell_template",
            "language": {
              "code": "nl"
            },
            "components": [
              {
                "type": "body",
                "parameters": [
                  { "type": "text", "text": "%s" },

                  { "type": "text", "text": "%s" },
                  { "type": "text", "text": "%s" },
                  { "type": "text", "text": "%s" },
                  { "type": "text", "text": "%s" },
                  { "type": "text", "text": "%s" },
                  

                  { "type": "text", "text": "%s" },
                  { "type": "text", "text": "%s" },
                  { "type": "text", "text": "%s" },
                  { "type": "text", "text": "%s" },
                  { "type": "text", "text": "%s" },
                  
                ]
              }
            ]
          }
        }
        """.formatted(name,
                    vac1.title(), vac1.description(), vac1.workingHours(), vac1.salary(),  vac1.function(),
                    vac2.title(), vac2.description(), vac2.workingHours(), vac2.salary(),  vac2.function());

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(credentials.getApiBaseUrl() + credentials.getPhoneNumberId() + "/messages"))
                    .header("Authorization", "Bearer " + credentials.getApiKey())
                    .header("Content-Type", "application/json")
                    .POST(HttpRequest.BodyPublishers.ofString(jsonBody))
                    .build();

            try (HttpClient client = HttpClient.newHttpClient()) {
                HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

                if (response.statusCode() >= 400) {
                    System.err.println("Fout bij verzenden WhatsApp-bericht:");
                    System.err.println("Statuscode: " + response.statusCode());
                    System.err.println("Responsetekst: " + response.body());
                }
            }
        } catch (IOException | InterruptedException e) {
            System.err.println("Er is een fout opgetreden bij het verzenden van het WhatsApp-bericht:");
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }
}
