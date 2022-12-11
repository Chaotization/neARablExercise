<%@ page import="java.sql.ResultSet" %>
<%@ page import="java.util.HashSet" %>
<%@ page import="java.util.Set" %>
<%@ page import="java.net.URL" %>
<%@ page import="java.util.HashMap" %>
<%@ page import="java.util.Map" %>
<!DOCTYPE html>
<html lang="en">
<form action="result" method="post">
  <head>
    <meta charset="UTF-8">
    <title>Search Result</title>
    <style>
      .button {
        background-color: #FF7F50;
        border: none;
        color: white;
        padding: 15px 32px;
        text-align: center;
        text-decoration: none;
        display: inline-block;
        font-size: 26px;
        margin: 4px 2px;
        cursor: pointer;
      }
    </style>
  </head>
  <body>
  <center>
    <h1>Search Result</h1>
    <%
      Set resultSet= (HashSet) session.getAttribute("result");
      out.println(resultSet);
    %>
    <br>
    <h1>Number Per State</h1>
      <%
            String nums = (String) session.getAttribute("numsInState");
            out.println(nums);
        %>
    <br>
    <center>
      <button class="button" type="back" value = "back" name="back" >back</button>
    </center>

  </body>
</html>