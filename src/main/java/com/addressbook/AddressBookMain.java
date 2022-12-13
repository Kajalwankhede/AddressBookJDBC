package com.addressbook;

import java.util.List;

public class AddressBookMain {
    public List<ContactDetails> dataList;
    private AddressBookDataBaseService addressBookDataBaseService;


    public AddressBookMain() {

        addressBookDataBaseService=AddressBookDataBaseService.getInstance();

    }


    public AddressBookMain(List<ContactDetails> dataList) {
        this();
        this.dataList = dataList;
    }

    public List<ContactDetails> readContactData() {
        dataList = addressBookDataBaseService.readData();
        return dataList;
    }
}