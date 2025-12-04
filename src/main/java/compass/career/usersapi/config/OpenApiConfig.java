package compass.career.usersapi.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class OpenApiConfig {

    @Value("${server.port:8081}")
    private String serverPort;

    @Bean
    public OpenAPI customOpenAPI() {
        final String securitySchemeName = "bearerAuth";

        //Detectar automáticamente si estamos en producción o desarrollo
        String productionUrl = System.getenv("RENDER_EXTERNAL_HOSTNAME");
        boolean isProduction = productionUrl != null && !productionUrl.isEmpty();

        List<Server> servers;
        if (isProduction) {
            // En producción (Render)
            servers = List.of(
                    new Server()
                            .url("https://" + productionUrl)
                            .description("Production (Render)")
            );
        } else {
            // En desarrollo local
            servers = List.of(
                    new Server()
                            .url("http://localhost:" + serverPort)
                            .description("Local Development")
            );
        }

        return new OpenAPI()
                .info(new Info()
                        .title("EvaluationsAPI - CareerCompass")
                        .version("1.0.0")
                        .description("API REST para evaluaciones vocacionales")
                        .contact(new Contact()
                                .name("CareerCompass Team")
                                .email("support@careercompass.com")))

                //Usar los servidores detectados automáticamente
                .servers(servers)

                .addSecurityItem(new SecurityRequirement()
                        .addList(securitySchemeName))

                .components(new Components()
                        .addSecuritySchemes(securitySchemeName, new SecurityScheme()
                                .name(securitySchemeName)
                                .type(SecurityScheme.Type.HTTP)
                                .scheme("bearer")
                                .bearerFormat("JWT")
                                .description("Ingrese el token JWT (sin 'Bearer', solo el token)")));
    }
}