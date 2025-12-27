package com.cmc.global.docs;

import com.cmc.global.auth.annotation.PreAuth;
import com.cmc.global.auth.annotation.UserPrincipal;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.servers.Server;
import org.springdoc.core.customizers.ParameterCustomizer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    private static final String DOCS_VERSION = "v1.0.0";

    @Value("${app.server.url}")
    private String serverUrl;

    @Bean
    public OpenAPI openAPI() {
        return new OpenAPI()
                .info(generateInfo())
                .addServersItem(generateServer());
    }

    @Bean
    public ParameterCustomizer parameterCustomizer() {
        return (parameterModel, methodParameter) -> {
            if (methodParameter.hasParameterAnnotation(UserPrincipal.class) ||
                    methodParameter.hasParameterAnnotation(PreAuth.class)) {
                return null;
            }
            return parameterModel;
        };
    }

    private Server generateServer() {
        Server server = new Server();
        server.setUrl(serverUrl);
        server.setDescription("게시판 서버 API 문서");
        return server;
    }

    private Info generateInfo() {
        return new Info()
                .title("CMC Board API")
                .version(DOCS_VERSION)
                .description("CMC 게시판 전용 API 정보입니다.");
    }
}
