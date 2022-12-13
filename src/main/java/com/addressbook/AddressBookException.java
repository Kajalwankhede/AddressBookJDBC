package com.addressbook;

    public class AddressBookException extends Exception {

        public enum ExceptionType
        {
            UPDATE_ERROR
        }

        public ExceptionType type;

        public AddressBookException(ExceptionType type,String message) {
            super(message);
            this.type = type;
        }
    }