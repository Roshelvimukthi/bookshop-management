package com.pahanaedu.servlet;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import java.io.IOException;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)  // ✅ Important
public class LoginServletTest {

    private LoginServlet servlet;
    private HttpServletRequest request;
    private HttpServletResponse response;
    private HttpSession session;
    private RequestDispatcher dispatcher;

    @BeforeEach
    public void setUp() {
        servlet = new LoginServlet();
        request = mock(HttpServletRequest.class);
        response = mock(HttpServletResponse.class);
        session = mock(HttpSession.class);
        dispatcher = mock(RequestDispatcher.class);
    }

    @Test
    public void testDoPost_ValidLogin() throws ServletException, IOException {
        when(request.getParameter("username")).thenReturn("admin");
        when(request.getParameter("password")).thenReturn("1234");
        when(request.getSession()).thenReturn(session);

        servlet.doPost(request, response);

        verify(response).sendRedirect("dashboard.jsp");
    }

    @Test
    public void testDoPost_InvalidLogin() throws ServletException, IOException {
        when(request.getParameter("username")).thenReturn("wrong");
        when(request.getParameter("password")).thenReturn("wrong");
        when(request.getRequestDispatcher("login.jsp")).thenReturn(dispatcher);

        servlet.doPost(request, response);

        verify(request).setAttribute(eq("error"), eq("Invalid username or password"));
        verify(dispatcher).forward(request, response);
    }

    @Test
    public void testDoGet_SessionExists() throws ServletException, IOException {
        when(request.getSession(false)).thenReturn(session);
        when(session.getAttribute("username")).thenReturn("admin");

        servlet.doGet(request, response);

        verify(response).sendRedirect("dashboard.jsp");
    }

    @Test
    public void testDoGet_NoSession() throws ServletException, IOException {
        when(request.getSession(false)).thenReturn(null);
        when(request.getRequestDispatcher("login.jsp")).thenReturn(dispatcher);

        servlet.doGet(request, response);

        verify(dispatcher).forward(request, response);
    }
}
