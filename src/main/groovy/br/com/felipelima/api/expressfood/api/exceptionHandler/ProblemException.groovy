package br.com.felipelima.api.expressfood.api.exceptionHandler

import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.annotation.JsonInclude.Include
import groovy.transform.builder.Builder

import java.time.LocalDateTime

@JsonInclude(Include.NON_NULL)
@Builder
class ProblemException {

    Integer status

    String type

    String title

    String detail

    LocalDateTime dataHora

}
