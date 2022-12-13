package com.addressbook;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
public class AddressBookDataBaseService {
    private static AddressBookDataBaseService addressBookDataBaseService;

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
        List<ContactDetails> dataList = new ArrayList<ContactDetails>();
        try {
            Connection connection = this.getConnection();
            try {
                Statement statement = connection.createStatement();
                ResultSet resultSet = statement.executeQuery(query);
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
            } finally {
                connection.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return dataList;
    }
}

