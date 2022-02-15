package com.lasik.parser;

import com.github.javaparser.ParseProblemException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ParsingExceptionHandler {
    @ExceptionHandler(value = {ParseProblemException.class})
    public ResponseEntity<ParseProblemException> handleParseRequestException(ParseProblemException e)
    {
        HttpStatus badRequest = HttpStatus.BAD_REQUEST;
        ParseProblemException parseProblemException = new ParseProblemException(e.getProblems());
        return new ResponseEntity<ParseProblemException>(parseProblemException,badRequest);
    }
}
