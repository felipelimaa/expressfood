package br.com.felipelima.api.expressfood.api.exceptionHandler

import org.springframework.beans.factory.annotation.Value

enum ProblemExceptionType {

    RECURSO_NAO_ENCONTRADO("/recurso-nao-encontrado", "Recurso não encontrado"),
    ENTIDADE_EM_USO("/entidade-em-uso", "Entidade em uso"),
    MENSAGEM_NAO_LEGIVEL("/mensagem-nao-legivel", "Mensagem não legível"),
    PARAMETRO_INVALIDO("/parametro-invalido", "Parametro inválido")


    String title

    String path

    ProblemExceptionType(String path, String detail){
        this.path = path
        this.title = detail
    }
}