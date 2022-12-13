package com.addressbook;

import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertEquals;

public class AddressBookTest {
    AddressBookMain addressBookFunction;
    List<ContactDetails> dataList;

    @Before
    public void init() {
        addressBookFunction = new AddressBookMain();
        dataList = addressBookFunction.readContactData();
    }

    @Test
    public void givenAddressBookWhenRetrievedShouldMatchCount() {
        assertEquals(5, dataList.size());
    }
}