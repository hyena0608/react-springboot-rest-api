package com.devcourse.blind.config;

import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.restdocs.mockmvc.MockMvcRestDocumentation;
import org.springframework.restdocs.mockmvc.RestDocumentationResultHandler;
import org.springframework.restdocs.operation.preprocess.Preprocessors;

import static org.springframework.restdocs.operation.preprocess.Preprocessors.prettyPrint;

@TestConfiguration
public class RestDocsConfiguration {

    @Bean
    public RestDocumentationResultHandler write() {
        return MockMvcRestDocumentation.document("{class-name}/{method-name}",
                Preprocessors.preprocessRequest(prettyPrint()),
                Preprocessors.preprocessResponse(prettyPrint()));
    }
}
