package com.szabolcs.SpringbootWebshop.ExceptionHandler;


public record ErrorResponse(int errorCode, String errorMessage, String timeStamp) {

}
