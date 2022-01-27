package br.com.felipelima.api.expressfood.core.validation

import javax.validation.Constraint
import javax.validation.Payload
import java.lang.annotation.ElementType
import java.lang.annotation.Retention
import java.lang.annotation.Target

import static java.lang.annotation.RetentionPolicy.RUNTIME

@Target(ElementType.TYPE)
@Retention(RUNTIME)
@Constraint(validatedBy = ValidaDescricaoQuandoValorZeradoValidator.class )
@interface ValidaDescricaoQuandoValorZerado {

    String message() default "descrição obrigatória inválida"

    Class<?>[] groups() default [ ]

    Class<? extends Payload>[] payload() default [ ]

    String atributoDescricao()

    String atributoDescricaoConteudo()

    String atributoValor()

}