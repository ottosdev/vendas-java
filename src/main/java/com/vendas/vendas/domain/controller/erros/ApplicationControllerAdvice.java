package com.vendas.vendas.domain.controller.erros;

import com.vendas.vendas.exception.RegraNegocioException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;
import java.util.stream.Collectors;

@RestControllerAdvice
public class ApplicationControllerAdvice {

    @ExceptionHandler(RegraNegocioException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiErros handleRegraNegocioException(RegraNegocioException ex) {
        String mensagemError = ex.getMessage();
        return new ApiErros(mensagemError);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiErros handleException(MethodArgumentNotValidException ex) {
        List<String> erros = ex.getBindingResult().getAllErrors().stream().map(error -> error.getDefaultMessage()).collect(Collectors.toList());
        return new ApiErros(erros);
    }
}
