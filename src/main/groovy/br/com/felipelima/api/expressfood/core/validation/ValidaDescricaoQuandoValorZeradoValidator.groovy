package br.com.felipelima.api.expressfood.core.validation

import org.springframework.beans.BeanUtils

import javax.validation.ConstraintValidator
import javax.validation.ConstraintValidatorContext
import javax.validation.ValidationException

class ValidaDescricaoQuandoValorZeradoValidator implements ConstraintValidator<ValidaDescricaoQuandoValorZerado, Object>{

    private String atributoDescricao

    private String atributoDescricaoConteudo

    private String atributoValor


    @Override
    void initialize(ValidaDescricaoQuandoValorZerado constraint) {
        this.atributoDescricao = constraint.atributoDescricao()
        this.atributoDescricaoConteudo = constraint.atributoDescricaoConteudo()
        this.atributoValor = constraint.atributoValor()
    }

    @Override
    boolean isValid(Object objetoValidacao, ConstraintValidatorContext context) {
        boolean valido = true

        try {
            BigDecimal valor = (BigDecimal) BeanUtils.getPropertyDescriptor(objetoValidacao.getClass(), atributoValor).getReadMethod().invoke(objetoValidacao)

            String descricao = (String) BeanUtils.getPropertyDescriptor(objetoValidacao.getClass(), atributoDescricao).getReadMethod().invoke(objetoValidacao)

            if (valor != null && BigDecimal.ZERO.compareTo(valor) == 0 && descricao != null){
                valido = descricao.toLowerCase().contains(this.atributoDescricaoConteudo.toLowerCase())
            }

            return valido

        } catch (Exception e) {
            throw new ValidationException(e)
        }

    }
}
