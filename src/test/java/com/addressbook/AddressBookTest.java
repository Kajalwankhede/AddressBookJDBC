package com.addressbook;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import java.util.List;

import static org.junit.Assert.*;

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

    @Test
    public void givenNewAddressForRecordWhenUpdatedShouldSyncWithDatabase() throws AddressBookException {
        List<ContactDetails>contactData=addressBookFunction.readContactData();
        addressBookFunction.updateRecord("Kajal","Indore");
        boolean result=addressBookFunction.checkAddressBookInSyncWithDB("Kajal");
        Assert.assertTrue(result);
    }
}
