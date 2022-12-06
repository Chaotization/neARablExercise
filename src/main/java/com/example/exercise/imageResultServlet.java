package com.example.exercise;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

import java.io.IOException;

@WebServlet(name = "imageServlet", urlPatterns = {"/image"})
public class imageResultServlet extends HttpServlet {
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
