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
    private ContactDetails getRecordDataByName(String FirstName) {
        ContactDetails contactData = this.dataList.stream()
                .filter(contact->contact.FirstName.equals(FirstName))
                .findFirst()
                .orElse(null);
        return contactData;
    }

    public void updateRecord(String FirstName, String City) throws AddressBookException {
        int result = addressBookDataBaseService.updateData(FirstName,City);
        if(result == 0)
            return;
        ContactDetails contactData=this.getRecordDataByName(FirstName);
        if (contactData!=null){
            contactData.City=City;
        }
    }

    public boolean checkAddressBookInSyncWithDB(String FirstName) {
        List<ContactDetails> checkList = addressBookDataBaseService.getRecordDataByName(FirstName);
        return checkList.get(0).equals(getRecordDataByName(FirstName));
    }
    public List<ContactDetails> getRecordAddedInDateRange(String date1, String date2) {
        List<ContactDetails> dataList = addressBookDataBaseService.getRecordsAddedInGivenDateRange(date1, date2);
        return dataList;
    }
    public List<ContactDetails> getRecordsByCity(String City) {
        List<ContactDetails> dataList = addressBookDataBaseService.getRecordsByCity(City);
        return dataList;
    }
    public List<ContactDetails> getRecordsByState(String State) {
        List<ContactDetails> dataList = addressBookDataBaseService.getRecordsByCityOrState(State);
        return dataList;
    }
}
