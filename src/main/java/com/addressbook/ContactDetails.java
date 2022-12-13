package com.addressbook;

import java.time.LocalDate;

public class ContactDetails {
        public String FirstName;
        public String LastName;
        public String Address;
        public String City;
        public String State;
        public String Zip;
        public String PhoneNumber;
        public String Email;
        public LocalDate dateAdded;

        public ContactDetails(String FirstName, String LastName, String Address, String City, String State, String Zip, String PhoneNumber, String Email) {
            this.FirstName = FirstName;
            this.LastName = LastName;
            this.Address = Address;
            this.City = City;
            this.State = State;
            this.Zip = Zip;
            this.PhoneNumber = PhoneNumber;
            this.Email = Email;
        }

    public ContactDetails(String FirstName, String LastName, String Address, String City, String State, String Zip,
                          String PhoneNumber, String Email,  LocalDate dateAdded) {

        this(FirstName, LastName, Address, City,
                State, Zip, PhoneNumber, Email);
        this.dateAdded = dateAdded;
    }

        @Override
        public String toString() {
            return "Created  "+FirstName+" "+LastName;
        }

        @Override
        public boolean equals(Object obj) {
            if (this == obj)
                return true;
            if (obj == null)
                return false;
            if (getClass() != obj.getClass())
                return false;
            com.addressbook.ContactDetails other = (com.addressbook.ContactDetails) obj;
            if (Address == null) {
                if (other.Address != null)
                    return false;
            } else if (!Address.equals(other.Address))
                return false;
            if (City == null) {
                if (other.City != null)
                    return false;
            } else if (!City.equals(other.City))
                return false;
            if (Email == null) {
                if (other.Email != null)
                    return false;
            } else if (!Email.equals(other.Email))
                return false;
            if (FirstName == null) {
                if (other.FirstName != null)
                    return false;
            } else if (!FirstName.equals(other.FirstName))
                return false;
            if (LastName == null) {
                if (other.LastName != null)
                    return false;
            } else if (!LastName.equals(other.LastName))
                return false;
            if (PhoneNumber == null) {
                if (other.PhoneNumber != null)
                    return false;
            } else if (!PhoneNumber.equals(other.PhoneNumber))
                return false;
            if (State == null) {
                if (other.State != null)
                    return false;
            } else if (!State.equals(other.State))
                return false;
            if (Zip != other.Zip)
                return false;
            return true;
        }
    }
