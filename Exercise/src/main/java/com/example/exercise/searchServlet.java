package com.example.exercise;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;
//Before to proceed the program, it required tomcat to display contents and interact the input on local page
@WebServlet(name = "searchServlet", urlPatterns = {"/search"})
public class searchServlet extends HttpServlet {

    @Override
    public void init() throws ServletException {
        System.out.println("call init");
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //retrieve data from the original class, give an error if it does not contain anything.
        write2DB write2DB=new write2DB();
        try {
            write2DB.writeOnlineSourceToLocalDB();
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        String field=request.getParameter("Field");
        String input= request.getParameter("input");
        //connect to local mysql database
        Connection conn = null;
        java.sql.Statement statement = null;
        try {
            conn= DriverManager.getConnection("jdbc:mysql://localhost:3306/dataset", "root", "0812");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        try {
            statement=conn.createStatement();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        //search users input in database
        String queryStr="SELECT * FROM DATASET.USERS WHERE "+field+" LIKE '%"+input+"%'";
        Set<String> set = new HashSet<>();
        ResultSet resultSet=null;
        String nums = "";
        Map<String, Integer> map = new HashMap<>();
        HttpSession session = request.getSession();
        //if the resultset received the search result, it would store into a hashset that guarantee the information would not be duplicate.
        try {
            resultSet =statement.executeQuery(queryStr);
            while(resultSet.next()) {
                set.add(resultSet.getString("firstname") + resultSet.getString("lastname") + resultSet.getString("companyname") + resultSet.getString("address") + resultSet.getString("city") + resultSet.getString("county")
                        + resultSet.getString("state") + resultSet.getString("zip") + resultSet.getString("phone1") + resultSet.getString("phone2") + resultSet.getString("email") + resultSet.getString("web"));
        //optional
                //display persons in each state implement by hashmap.
                int freq = map.getOrDefault(resultSet.getString("state"), 0);
                map.put(resultSet.getString("state"), freq + 1);
            }
            //close the database.
            String queryStr1="TRUNCATE TABLE DATASET.USERS";
            //reset database to avoid the redundant data
            statement.executeUpdate(queryStr1);
            conn.close();
            statement.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        //send the result in map to each Servlet
        for (Map.Entry<String, Integer> entry : map.entrySet()) {
            nums += "States: " + entry.getKey()  + " " + "Nums: " + entry.getValue().toString() + "\n";
        }
        session.setAttribute("result", set);
        session.setAttribute("numsInState", nums);
        //for image Servlet only occur when result in one person searched by firstname, and video Servlet occur when result by one company name search by company name
        //if both cases not happened, it would go to the default Servlet.
        if(set.size() == 1 && field.equals("firstname")){
            request.getRequestDispatcher("imageResult.jsp").forward(request,response);
        }else if(set.size() == 1 && field.equals("companyname")){
            request.getRequestDispatcher("videoResult.jsp").forward(request,response);
        }else {
            request.getRequestDispatcher("result.jsp").forward(request, response);
        }
    }
}
