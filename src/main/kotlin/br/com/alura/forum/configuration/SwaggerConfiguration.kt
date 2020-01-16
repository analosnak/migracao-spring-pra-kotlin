package br.com.alura.forum.configuration

import br.com.alura.forum.model.User
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

@Configuration
@EnableSwagger2
class SwaggerConfiguration {

    @Bean
    fun api(): Docket = Docket(DocumentationType.SWAGGER_2)
            .select()
            .apis(RequestHandlerSelectors.basePackage("br.com.alura.forum"))
            .paths(PathSelectors.ant("/api/**"))
            .build()

            .apiInfo(apiInfo())
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
                                    .build()
                            ))

            .ignoredParameterTypes(User::class.java)
            .globalOperationParameters(
                    listOf(
                            ParameterBuilder()
                                    .name("Authorization")
                                    .description("Header para facilitar o envio do Authorization Bearer Token")
                                    .modelRef(ModelRef("string"))
                                    .parameterType("header")
                                    .required(false)
                                    .build()
                    )
            )



    private fun apiInfo(): ApiInfo = ApiInfoBuilder()
            .title("Alura Forum API Documentation")
            .description("Esta é a documentação interativa da Rest API do Fórum da Alura. Tente enviar algum request ;)")
            .version("1.0")
            .contact(Contact("Alura", "https://cursos.alura.com.br/", "contato@alura.com.br"))
            .build()
}