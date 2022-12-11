<%@ page import="java.util.HashSet" %>
<%@ page import="java.util.Set" %>

<!DOCTYPE html>
<html lang="en">
<form action="video" method="post">
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
        <div id="videoDiv">
            <video id="video" src="https://raw.githubusercontent.com/jinchen003/Nearabl.Sample.Data/main/
neARabl.mp4" width="70%" controls></video>
        </div>
        <%
            Set resultSet= (HashSet) session.getAttribute("result");
            out.println(resultSet);;
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