package com.bridgelabz.addressbook;


public class AddressBookException extends RuntimeException  {
	enum ExceptionType {
        FAILED_TO_CONNECT, CANNOT_EXECUTE_QUERY, UPDATE_FAILED, NULL_CONTACT_OBJECT
    }
    ExceptionType exceptionType;

    public AddressBookException(ExceptionType exceptionType, String message) {
        super(message);
        this.exceptionType = exceptionType;
    }
}
