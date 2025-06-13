package com.rebellworksllm.backend.matching.domain;

import com.rebellworksllm.backend.openai.presentation.dto.EmbeddingResult;


public record Vacancy(

        String title,
        String description,
        String workingHours,
        String salary,
        String function,
        String website,
        EmbeddingResult embeddingResult
) {
}
