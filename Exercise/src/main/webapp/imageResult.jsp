<%@ page import="java.util.HashSet" %>
<%@ page import="java.util.Set" %>
<%-- this file contains the url of image when it Servlet called, it would post the information to webpage --%>
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
        <img alt="pic" src="https://raw.githubusercontent.com/jinchen003/Nearabl.Sample.Data/main/user.png" style="width: 300px; height: 200px;"> <br />
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
            <!-- HTML !-->

        </center>
    </center>
    </body>
    </form>
</html>