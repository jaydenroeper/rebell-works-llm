package com.rebellworksllm.backend.whatsapp.application;

import com.rebellworksllm.backend.matching.domain.Vacancy;

public interface WhatsAppService {

    void sendWithVacancyTemplate(String phoneNumber,
                                   String name,
                                   Vacancy vac1,
                                   Vacancy vac2,
                                   Vacancy vac3);

}
