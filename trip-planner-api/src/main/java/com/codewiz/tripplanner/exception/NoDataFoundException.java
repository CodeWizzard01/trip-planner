package com.codewiz.tripplanner.exception;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class NoDataFoundException extends RuntimeException{
    String message;
    int code;
}