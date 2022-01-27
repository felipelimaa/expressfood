package br.com.felipelima.api.expressfood.core.validation

import org.springframework.context.MessageSource
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean

@Configuration
class ValidationConfig {

    // Especificando um MessageSource no setValidationMessageSource para que o spring entenda e use as mensagens customizadas de messages.properties.
    // Se não é especificado dessa forma, o spring usará o arquivo ValidationMessages.properties
    @Bean
    LocalValidatorFactoryBean validator(MessageSource messageSource) {
        LocalValidatorFactoryBean bean = new LocalValidatorFactoryBean()
        bean.setValidationMessageSource(messageSource)

        return bean
    }
}
