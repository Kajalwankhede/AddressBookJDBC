package com.addressbook;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.time.LocalDate;
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
    public void givenAddressBookWhenRetrievedShouldMatchCount()
    {
        assertEquals(5, dataList.size());
    }

    @Test
    public void givenNewAddressForRecordWhenUpdatedShouldSyncWithDatabase() throws AddressBookException {
        List<ContactDetails>dataList=addressBookFunction.readContactData();
        addressBookFunction.updateRecord("Kajal","Indore");
       addressBookFunction.checkAddressBookInSyncWithDB("Kajal");

    }
    @Test
    public void givenDateRangeForRecordWhenRetrievedShouldReturnProperData() throws AddressBookException {
        List<ContactDetails> recordDataInGivenDateRange = addressBookFunction.getRecordAddedInDateRange("2018-01-01","2019-11-30");
        assertEquals(0, recordDataInGivenDateRange.size());
    }
    @Test
    public void givenCityWhenRetrievedShouldReturnProperDetails() throws AddressBookException {
        List<ContactDetails> recordsByCity = addressBookFunction.getRecordsByCity("Pune");
        assertEquals(1, recordsByCity.size());
    }
    @Test
    public void givenStateWhenRetrievedShouldReturnProperDetails() throws AddressBookException {
        List<ContactDetails> recordDataByState = addressBookFunction.getRecordsByState( "Maharashtra");
        assertEquals(3, recordDataByState.size());
    }
    @Test
    public void givenNewContactWhenAddedShouldSyncWithDB() throws AddressBookException {
        addressBookFunction.addContactToDatabase("Manisha", "Naik", "Chennai", "Chennai", "Karnataka",
                "448797", "9807654567", "mnaik@gmail.com", "ManishaNaik", "Family", LocalDate.now());
        addressBookFunction.checkAddressBookInSyncWithDB("Manisha");

    }

}
