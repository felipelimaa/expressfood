package br.com.felipelima.api.expressfood.core.validation

import org.springframework.validation.BindingResult

class ValidationException extends RuntimeException {
    BindingResult getBindingResult() {
        return bindingResult
    }

    ValidationException(BindingResult bindingResult) {
        this.bindingResult = bindingResult
    }

    private BindingResult bindingResult
}
