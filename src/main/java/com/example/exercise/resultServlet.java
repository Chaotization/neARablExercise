package com.example.exercise;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;


import java.io.IOException;

@WebServlet(name = "resultServlet", urlPatterns = {"/result"})
public class resultServlet extends HttpServlet {
    @Override
    public void init() throws ServletException {
        System.out.println("call init");
    }
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        if (request.getParameter("back") !=null){
            request.getRequestDispatcher("search.html").forward(request, response);
        }
    }
}

