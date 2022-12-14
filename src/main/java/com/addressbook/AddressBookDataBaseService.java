package com.addressbook;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static com.addressbook.AddressBookException.ExceptionType.INSERTION_PROBLEM;

public class AddressBookDataBaseService {
    private static AddressBookDataBaseService addressBookDataBaseService;
    private PreparedStatement recordStatement;

    public enum StatementType
    {
        PREPARED_STATEMENT, STATEMENT
    }

    private void preparedStatementForRecord() {
        try {
            Connection connection = this.getConnection();
            String query = "SELECT * FROM addressbook WHERE firstName = ?";
            recordStatement = connection.prepareStatement(query);
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
    }

    public  synchronized Connection getConnection() throws SQLException {
        String jdbcURL = "jdbc:mysql://localhost:3306/addressbook_service?useSSL=false";
        String userName = "root";
        String password = "Kajal@123";
        Connection connection;
        System.out.println("Connecting to database: " + jdbcURL);
        connection = DriverManager.getConnection(jdbcURL, userName, password);
        System.out.println("Connection successful!!" + connection);
        return connection;
    }

    public static AddressBookDataBaseService getInstance() {
        if (addressBookDataBaseService == null)
            addressBookDataBaseService = new AddressBookDataBaseService();
        return addressBookDataBaseService;
    }

    public List<ContactDetails> readData() {
        String query = "SELECT * FROM addressbook";
        return getAddressBookDetails(query);
    }
    public List<ContactDetails>getAddressBookDetails(String query) {
        List<ContactDetails> dataList = new ArrayList<ContactDetails>();
        try (Connection connection = this.getConnection();) {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query);
            dataList = this.getAddressBookDetails(resultSet);
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
        return dataList;
    }
    private  List<ContactDetails>getAddressBookDetails(ResultSet resultSet){
        List<ContactDetails> dataList=new ArrayList<>();
       try{
                while (resultSet.next()) {
                    String FirstName = resultSet.getString("FirstName");
                    String LastName = resultSet.getString("LastName");
                    String Address = resultSet.getString("Address");
                    String City = resultSet.getString("City");
                    String State = resultSet.getString("State");
                    String Zip = resultSet.getString("Zip");
                    String PhoneNumber = resultSet.getString("PhoneNumber");
                    String Email = resultSet.getString("Email");
                    dataList.add(new ContactDetails(FirstName, LastName, Address, City, State, Zip, PhoneNumber, Email));
                }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return dataList;
    }

    public List<ContactDetails> getRecordDataByName(String FirstName) {
        List<ContactDetails> dataList= null;
        if (this.recordStatement == null) this.preparedStatementForRecord();
        try {
            recordStatement.setString(1, FirstName);
            ResultSet resultSet = recordStatement.executeQuery();
            dataList = this.getAddressBookDetails(resultSet);
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
        return dataList;
    }


    public int updateData(String FirstName, String City)throws AddressBookException{
        return this.updateDataUsingPreparedStatement(FirstName,City);
    }

    public int updateDataUsingPreparedStatement(String FirstName, String City) {
        try (Connection connection = this.getConnection();) {
            String query = "UPDATE addressbook SET City= ? WHERE FirstName= ?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, City);
            preparedStatement.setString(2, FirstName);
            int status= preparedStatement.executeUpdate();
            return status;
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
        return 0;
    }
    public List<ContactDetails> getRecordsAddedInGivenDateRange(String date1, String date2) {
        String query = String.format("SELECT * FROM addressbook WHERE dateAdded BETWEEN '%s' AND '%s';", date1, date2);
        return this.getAddressBookDetails(query);
    }
    public List<ContactDetails> getRecordsByCity(String City) {
        String query = String.format("SELECT * FROM addressbook WHERE City='%s';", City);
        return this.getAddressBookDetails(query);
    }
    public List<ContactDetails> getRecordsByCityOrState( String State) {
        String query = String.format("SELECT * FROM addressbook WHERE  State='%s';", State);
        return this.getAddressBookDetails(query);
    }
    public ContactDetails addNewContactToAddressBook(String FirstName, String LastName, String Address, String City, String State, String Zip,
                                                     String PhoneNumber, String Email, String NAME, String TYPE, LocalDate dateAdded) throws AddressBookException {
        int contactId = -1;
        Connection connection = null;
        ContactDetails contactPerson = null;
        try {
            connection = this.getConnection();
            connection.setAutoCommit(false);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try (Statement statement = connection.createStatement()) {
            String sqlQuery = String.format("INSERT INTO addressbook VALUES ('%s','%s','%s','%s','%s','%s','%s','%s','%s','%s','%s')",
                    FirstName, LastName, Address, City, State, Zip,
                    PhoneNumber, Email, NAME, TYPE, dateAdded);
            int rowAffected = statement.executeUpdate(sqlQuery, Statement.RETURN_GENERATED_KEYS);
            if (rowAffected == 1) {
                ResultSet resultSet = statement.getGeneratedKeys();
                if (resultSet.next()) contactId = resultSet.getInt(1);
            }
            contactPerson = new ContactDetails(FirstName,LastName,Address,City,State,Zip,PhoneNumber,Email,dateAdded);
            if (connection != null) {
                connection.commit();
            }
        } catch (SQLException exception) {
            throw new AddressBookException(INSERTION_PROBLEM, "Insertion Problem");
        }
        return contactPerson;
    }
}



