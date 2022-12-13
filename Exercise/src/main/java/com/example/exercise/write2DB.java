package com.example.exercise;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

//create column on mysql
// create table dataset.users
// (
//     id          int auto_increment
//         primary key,
//     firstname   varchar(45) null,
//     lastname    varchar(45) null,
//     companyname varchar(45) null,
//     address     varchar(45) null,
//     city        varchar(45) null,
//     county      varchar(45) null,
//     state       varchar(45) null,
//     zip         varchar(45) null,
//     phone1      varchar(45) null,
//     phone2      varchar(45) null,
//     email       varchar(45) null,
//     web         varchar(45) null
// );

public class write2DB {
    private Users users;
    public void writeOnlineSourceToLocalDB() throws IOException, SQLException, ClassNotFoundException {
        URL url = new URL("https://raw.githubusercontent.com/jinchen003/Nearabl.Sample.Data/main/us-500.csv");
        Class.forName("com.mysql.jdbc.Driver");
        //connect to the database
        Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/dataset", "root", "0812");
        //conncect to the url
        HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
        //retrieve data when it received the correct response
        if (httpURLConnection.getResponseCode() == 200) {
            InputStreamReader inputStreamReader = new InputStreamReader(httpURLConnection.getInputStream());
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            //format indexs on the database
            PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO dataset.users" +
                    "(firstname, lastname, companyname, address, city, county, state, zip, phone1, phone2, email, web)" +
                    "Values(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");
            connection.setAutoCommit(false);
            String content = null;
            bufferedReader.readLine();
            int count = 0;
            users = new Users();
            //save the current data received to local by specific lables
            while((content = bufferedReader.readLine()) != null){
                String[] data = content.split(",");
                if(data.length == 13){
                    users.setFirstname(data[0]);
                    users.setLastname(data[1]);
                    users.setCompanyname(data[2] + ", " + data[3]);
                    users.setAddress(data[4]);
                    users.setCity(data[5]);
                    users.setCounty(data[6]);
                    users.setState(data[7]);
                    users.setZip( data[8]);
                    users.setPhone1(data[9]);
                    users.setPhone2(data[10]);
                    users.setEmail(data[11]);
                    users.setWeb(data[12]);
                }else{
                    users.setFirstname(data[0]);
                    users.setLastname(data[1]);
                    users.setCompanyname(data[2]);
                    users.setAddress(data[3]);
                    users.setCity(data[4]);
                    users.setCounty(data[5]);
                    users.setState(data[6]);
                    users.setZip( data[7]);
                    users.setPhone1(data[8]);
                    users.setPhone2(data[9]);
                    users.setEmail(data[10]);
                    users.setWeb(data[11]);
                }
                //send data to databases
                preparedStatement.setString(1, users.getFirstname());
                preparedStatement.setString(2, users.getLastname());
                preparedStatement.setString(3, users.getCompanyname());
                preparedStatement.setString(4, users.getAddress());
                preparedStatement.setString(5, users.getCity());
                preparedStatement.setString(6, users.getCounty());
                preparedStatement.setString(7, users.getState());
                preparedStatement.setString(8, users.getZip());
                preparedStatement.setString(9, users.getPhone1());
                preparedStatement.setString(10, users.getPhone2());
                preparedStatement.setString(11, users.getEmail());
                preparedStatement.setString(12, users.getWeb());
                //when it reached the preset amount, the database closed
                if (count % 500 == 0) {
                    preparedStatement.executeBatch();
                }
                preparedStatement.addBatch();
            }
            bufferedReader.close();
            preparedStatement.executeBatch();
            connection.commit();
            connection.close();
        }
    }
}
