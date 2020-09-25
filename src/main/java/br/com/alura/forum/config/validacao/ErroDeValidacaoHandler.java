package br.com.alura.forum.config.validacao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.ArrayList;
import java.util.List;

// Um filter que semppre irá capturar erros
@RestControllerAdvice
public class ErroDeValidacaoHandler {
    @Autowired
    private MessageSource messageSource;

    // Irá capturar erros do tipo informado no parametro
    @ExceptionHandler(MethodArgumentNotValidException.class) /* Bean Validation emite esse tipo de erro nos forms*/
    @ResponseStatus(code = HttpStatus.BAD_REQUEST) // Se não declarar ele vai entender que funcionou, retornando 200
    public List<ErroDeFormularioDto> handle(MethodArgumentNotValidException exception) {
        List<FieldError> fieldErrors = exception.getBindingResult().getFieldErrors();
        ArrayList<ErroDeFormularioDto> listaErroDto = new ArrayList<>();

        fieldErrors.forEach(e -> {
            ErroDeFormularioDto erroDto = new ErroDeFormularioDto(e.getField(), messageSource.getMessage(e, LocaleContextHolder.getLocale()));
            listaErroDto.add(erroDto);
        });

        return listaErroDto;
    }
}
