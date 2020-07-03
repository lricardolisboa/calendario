package br.com.r7.calendario.config

import br.com.r7.calendario.security.UsuarioLogado
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.bind.annotation.RequestMethod
import springfox.documentation.builders.*
import springfox.documentation.schema.ModelRef
import springfox.documentation.service.ApiInfo
import springfox.documentation.service.Contact
import springfox.documentation.spi.DocumentationType
import springfox.documentation.spring.web.plugins.Docket
import springfox.documentation.swagger2.annotations.EnableSwagger2
import java.util.*

@Configuration
@EnableSwagger2
class SwaggerConfig {

    @Bean
    fun api(): Docket = Docket(DocumentationType.SWAGGER_2)
            .apiInfo(ApiInfoBuilder().description("Calendário").build())
            .select()
            .apis(RequestHandlerSelectors.basePackage("br.com.r7.calendario"))
            .paths(PathSelectors.any())
            .build()
            .apiInfo(apiInfo())
            .ignoredParameterTypes(UsuarioLogado::class.java)
            .globalResponseMessage(RequestMethod.GET,
                    listOf(
                            ResponseMessageBuilder()
                                    .code(500)
                                    .message("Xii! Deu erro interno no servidor.")
                                    .build(),
                            ResponseMessageBuilder()
                                    .code(403)
                                    .message("Forbidden! Você não pode acessar esse recurso.")
                                    .build(),
                            ResponseMessageBuilder()
                                    .code(404)
                                    .message("O recurso que você buscou não foi encontrado.")
                                    .build()))
            .globalOperationParameters(
                    listOf(ParameterBuilder()
                            .name("Authorization")
                            .description("Authorization Bearer Token")
                            .modelRef(ModelRef("string"))
                            .parameterType("header")
                            .required(false)
                            .build()))

    private fun apiInfo(): ApiInfo? {
        val contato = Contact("Calendario",
                "https://calendario.r7.com.br/", "contato@r7.com.br")
        return ApiInfoBuilder()
                .title("Calendario API Documentation")
                .version("1.0")
                .contact(contato)
                .build()
    }

}