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
        Assert.assertEquals(true, result);
    }
    @Test
    public void givenDateRangeForRecordWhenRetrievedShouldReturnProperData() throws AddressBookException {
        List<ContactDetails> recordDataInGivenDateRange = addressBookFunction.getRecordAddedInDateRange("2018-01-01","2019-11-30");
        assertEquals(0, recordDataInGivenDateRange.size());
    }
    @Test
    public void givenCityWhenRetrievedShouldReturnProperDetails() throws AddressBookException {
        List<ContactDetails> recordDataByCityState = addressBookFunction.getRecordsByCity("Pune");
        assertEquals(1, recordDataByCityState.size());
    }
    @Test
    public void givenStateWhenRetrievedShouldReturnProperDetails() throws AddressBookException {
        List<ContactDetails> recordDataByCityState = addressBookFunction.getRecordsByState( "Maharashtra");
        assertEquals(3, recordDataByCityState.size());
    }
}
