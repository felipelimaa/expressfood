package br.com.felipelima.api.expressfood.api.exceptionHandler

import br.com.felipelima.api.expressfood.domain.exception.EntidadeEmUsoException
import br.com.felipelima.api.expressfood.domain.exception.EntidadeNotFoundException
import com.ctc.wstx.exc.WstxUnexpectedCharException
import com.fasterxml.jackson.databind.exc.InvalidFormatException
import com.fasterxml.jackson.databind.exc.PropertyBindingException
import org.apache.commons.lang3.exception.ExceptionUtils
import org.springframework.beans.TypeMismatchException
import org.springframework.beans.factory.annotation.Value
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.http.converter.HttpMessageNotReadableException
import org.springframework.lang.Nullable
import org.springframework.validation.BindingResult
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.context.request.WebRequest
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException
import org.springframework.web.servlet.NoHandlerFoundException
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler

import java.time.LocalDateTime
import java.util.stream.Collectors

@ControllerAdvice
class ApiExceptionHandler extends ResponseEntityExceptionHandler {

    @Value('${expressfood.exception.uri-base}')
    String URI_BASE_EXCEPTION

    @Value('${expressfood.exception.printlog}')
    Boolean PRINT_LOG_EXCEPTION

    public static String MSG_ERRO_GENERICO = "Ocorreu um erro interno inesperado no sistema. Tente novamente e se o " +
            "problema persistir, entre em contato com o administrador e sistema."

    @Override
    protected ResponseEntity<Object> handleExceptionInternal(Exception ex, @Nullable Object body, HttpHeaders headers, HttpStatus status, WebRequest request) {

        if (PRINT_LOG_EXCEPTION == true) {
            print(ex.printStackTrace())
        }

        if (body == null) {
            body = ProblemException.builder()
                    .timestamp(LocalDateTime.now())
                    .title(status.getReasonPhrase())
                    .status(status.value())
                    .detail(ex.getMessage())
                    .uiMessage(MSG_ERRO_GENERICO)
                    .build()
        } else if (body instanceof String) {
            body = ProblemException.builder()
                    .timestamp(LocalDateTime.now())
                    .title((String) body)
                    .status(status.value())
                    .detail(ex.getMessage())
                    .uiMessage(MSG_ERRO_GENERICO)
                    .build()
        }

        return super.handleExceptionInternal(ex, body, headers, status, request)
    }

    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(
            HttpMessageNotReadableException ex,
            HttpHeaders headers,
            HttpStatus status,
            WebRequest request
    ) {

        Throwable rootCause = ExceptionUtils.getRootCause(ex)

        if (rootCause instanceof InvalidFormatException) {
            return handleInvalidFormatException((InvalidFormatException) rootCause, headers, status, request)
        } else if (rootCause instanceof PropertyBindingException) {
            return handlePropertyBindingException((PropertyBindingException) rootCause, headers, status, request)
        } else if (rootCause instanceof WstxUnexpectedCharException) {
            return handleJsonParseException((WstxUnexpectedCharException) rootCause, headers, status, request)
        }


        ProblemExceptionType problemType = ProblemExceptionType.MENSAGEM_NAO_LEGIVEL

        String mensagem = "O corpo da requisição está inválido, verifique a sintaxe."

        ProblemException problem = createProblemBuilder(status, problemType, mensagem, MSG_ERRO_GENERICO)

        return handleExceptionInternal(ex, problem, headers, status, request)

    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(
            MethodArgumentNotValidException ex,
            HttpHeaders headers,
            HttpStatus status,
            WebRequest request
    ) {

        ProblemExceptionType problemType = ProblemExceptionType.DADOS_INVALIDOS

        String mensagem = "Um ou mais campos estão inválidos. Faça o preenchimento correto e tente novamente."

        BindingResult bindingResult = ex.getBindingResult()

        List<ProblemException.Field> problemFields = bindingResult.getFieldErrors()
                .stream()
                .map(fieldError -> ProblemException.Field.builder()
                    .name(fieldError.getField())
                    .uiMessage(fieldError.getDefaultMessage())
                    .build()
                )
                .collect(Collectors.toList())

        ProblemException problem = createProblemBuilder(status, problemType, mensagem, mensagem, problemFields)

        return handleExceptionInternal(ex, problem, headers, status, request)

    }

    @Override
    protected ResponseEntity<Object> handleNoHandlerFoundException(
            NoHandlerFoundException e,
            HttpHeaders headers,
            HttpStatus status,
            WebRequest request
    ) {

        ProblemExceptionType problemType = ProblemExceptionType.RECURSO_NAO_ENCONTRADO

        String mensagem = String.format("O recurso '%s', que você tentou acessar, não existe", e.getRequestURL())

        ProblemException problem = createProblemBuilder(status, problemType, mensagem, mensagem)

        return handleExceptionInternal(e, problem, headers, status, request)
    }

    @Override
    protected ResponseEntity<Object> handleTypeMismatch(TypeMismatchException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {

        if (ex instanceof MethodArgumentTypeMismatchException) {
            return handleMethodArgumentTypeMismatchException(
                    (MethodArgumentTypeMismatchException) ex, headers, status, request
            )
        }

        return super.handleTypeMismatch(ex, headers, status, request)
    }

    @ExceptionHandler(Exception.class)
    ResponseEntity<?> handleUncaught(Exception e, WebRequest request){

        if (PRINT_LOG_EXCEPTION == true) {
            print(e.printStackTrace())
        }
        ProblemExceptionType problemType = ProblemExceptionType.ERRO_NAO_TRATADO

        HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR

        ProblemException problem = createProblemBuilder(status, problemType, MSG_ERRO_GENERICO, MSG_ERRO_GENERICO)

        return handleExceptionInternal(e, problem, new HttpHeaders(), status, request)
    }

    @ExceptionHandler(EntidadeNotFoundException.class)
    ResponseEntity<?> handleEntidadeNotFoundException(EntidadeNotFoundException e, WebRequest request){

        ProblemExceptionType problemType = ProblemExceptionType.RECURSO_NAO_ENCONTRADO

        ProblemException problem = createProblemBuilder(e.status, problemType, e.getReason(), e.getReason())

        return handleExceptionInternal(e, problem, new HttpHeaders(), e.status, request)
    }

    @ExceptionHandler(EntidadeEmUsoException.class)
    ResponseEntity<?> handleEntidadeEmUsoException(EntidadeEmUsoException e, WebRequest request){

        ProblemExceptionType problemType = ProblemExceptionType.ENTIDADE_EM_USO

        ProblemException problem = createProblemBuilder(e.status, problemType, e.getReason(), e.getReason())

        return handleExceptionInternal(e, problem, new HttpHeaders(), e.status, request)
    }

    private ResponseEntity<Object> handleJsonParseException(
            WstxUnexpectedCharException e,
            HttpHeaders headers,
            HttpStatus status,
            WebRequest request
    ) {
        ProblemExceptionType problemType = ProblemExceptionType.MENSAGEM_NAO_LEGIVEL

        String mensagem = "O tipo de conteúdo da requisição não está de acordo com o aceitado pela API."

        ProblemException problem = createProblemBuilder(status, problemType, mensagem, MSG_ERRO_GENERICO)

        return handleExceptionInternal(e, problem, headers, status, request)
    }

    private ResponseEntity<Object> handleInvalidFormatException(
            InvalidFormatException e,
            HttpHeaders headers,
            HttpStatus status,
            WebRequest request
    ) {

        String path = joinPath(e.getPath())

        ProblemExceptionType problemType = ProblemExceptionType.MENSAGEM_NAO_LEGIVEL

        String mensagem = String.format(
                "A propriedade '%s' recebeu o valor '%s', que é de um tipo inválido. Corrija e informe um valor compatível com o tipo %s.",
                path, e.getValue(), e.getTargetType().getSimpleName()
        )

        ProblemException problem = createProblemBuilder(status, problemType, mensagem, MSG_ERRO_GENERICO)

        return handleExceptionInternal(e, problem, headers, status, request)

    }

    private ResponseEntity<Object> handlePropertyBindingException(
            PropertyBindingException e,
            HttpHeaders headers,
            HttpStatus status,
            WebRequest request
    ) {
        String path = joinPath(e.getPath())

        ProblemExceptionType problemType = ProblemExceptionType.MENSAGEM_NAO_LEGIVEL

        String mensagem = String.format(
                "A propriedade '%s' não existe. " +
                        "Corrija ou remova essa propriedade e tente novamente", path
        )

        ProblemException problem = createProblemBuilder(status, problemType, mensagem, MSG_ERRO_GENERICO)

        return handleExceptionInternal(e, problem, headers, status, request)
    }

    private ResponseEntity<Object> handleMethodArgumentTypeMismatchException(
            MethodArgumentTypeMismatchException ex,
            HttpHeaders headers,
            HttpStatus status,
            WebRequest request
    ) {
        ProblemExceptionType problemType = ProblemExceptionType.PARAMETRO_INVALIDO

        String mensagem = String.format("O parâmetro de URL '%s' recebeu o valor '%s'," +
                "que é de um tipo inválido. Corrija e informe um valor compatível com o tipo %s.",
                ex.getName(), ex.getValue(), ex.getRequiredType().getSimpleName()
        )

        ProblemException problem = createProblemBuilder(status, problemType, mensagem, MSG_ERRO_GENERICO)

        return handleExceptionInternal(ex, problem, headers, status, request)
    }

    private ProblemException createProblemBuilder(
            HttpStatus status,
            ProblemExceptionType problemType,
            String detail,
            @Nullable String uiMessage
    ){

        if (uiMessage == null) {
            uiMessage = MSG_ERRO_GENERICO
        }

        return ProblemException.builder()
                .timestamp(LocalDateTime.now())
                .status(status.value())
                .type(URI_BASE_EXCEPTION + problemType.getPath())
                .title(problemType.getTitle())
                .detail(detail)
                .uiMessage(uiMessage)
                .build()
    }

    private ProblemException createProblemBuilder(
            HttpStatus status,
            ProblemExceptionType problemType,
            String detail,
            String uiMessage,
            List<ProblemException.Field> fields
    ){

        if (uiMessage == null) {
            uiMessage = MSG_ERRO_GENERICO
        }

        return ProblemException.builder()
                .timestamp(LocalDateTime.now())
                .status(status.value())
                .type(URI_BASE_EXCEPTION + problemType.getPath())
                .title(problemType.getTitle())
                .detail(detail)
                .uiMessage(uiMessage)
                .fields(fields)
                .build()
    }

    private String joinPath(List<Reference> references) {
        return references.stream().map(ref -> ref.getFieldName()).collect(Collectors.joining("."))
    }
}
