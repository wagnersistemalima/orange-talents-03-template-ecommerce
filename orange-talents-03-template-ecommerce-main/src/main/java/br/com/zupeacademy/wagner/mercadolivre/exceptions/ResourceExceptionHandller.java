package br.com.zupeacademy.wagner.mercadolivre.exceptions;

import java.time.Instant;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

// Classe que vai interceptar erros que veio da requisição

@ControllerAdvice
public class ResourceExceptionHandller {

	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<ValidationError> validatiom(MethodArgumentNotValidException e, HttpServletRequest request) {
		HttpStatus status = HttpStatus.BAD_REQUEST;
		ValidationError error = new ValidationError(Instant.now(), status.value(), "Validation exception!",
				e.getMessage(), request.getRequestURI());

		for (FieldError f : e.getBindingResult().getFieldErrors()) {
			error.addErro(f.getField(), f.getDefaultMessage());
		}
		return ResponseEntity.status(status).body(error);
	}
	
	// metodo para tratamento de erro recurso não encontrado / resposta 400
		@ExceptionHandler(ResourceNotFoundException.class)
		public ResponseEntity<ValidationError> entityNotFound(ResourceNotFoundException e, HttpServletRequest request) {
			HttpStatus status = HttpStatus.BAD_REQUEST;
			ValidationError error = new ValidationError(Instant.now(), status.value(), "recurso não encontrado",
					e.getMessage(), request.getRequestURI());
		

			return ResponseEntity.status(status).body(error);
		}

}
