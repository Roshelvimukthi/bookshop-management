package com.pahanaedu.servlet;
import static org.mockito.Mockito.*;

import com.pahanaedu.servlet.CustomerServlet;
import org.junit.Before;
import org.junit.Test;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.*;
import java.io.IOException;

public class CustomerServletTest {

    private CustomerServlet servlet;
    private HttpServletRequest request;
    private HttpServletResponse response;
    private HttpSession session;

    @Before
    public void setUp() {
        servlet = new CustomerServlet();
        request = mock(HttpServletRequest.class);
        response = mock(HttpServletResponse.class);
        session = mock(HttpSession.class);

        // When request.getSession(false) is called → return mocked session
        when(request.getSession(false)).thenReturn(session);
    }

    @Test
    public void testRedirectWithoutSession() throws IOException, ServletException {
        // No username in session
        when(session.getAttribute("username")).thenReturn(null);

        servlet.doGet(request, response);

        // Verify redirect happens
        verify(response).sendRedirect("login.jsp");
    }

    @Test
    public void testForwardWithSession() throws IOException, ServletException {
        // Username exists in session
        when(session.getAttribute("username")).thenReturn("admin");

        // Mock dispatcher
        RequestDispatcher dispatcher = mock(RequestDispatcher.class);
        when(request.getRequestDispatcher("manageCustomers.jsp")).thenReturn(dispatcher);

        servlet.doGet(request, response);

        // Verify forward happens
        verify(dispatcher).forward(request, response);
    }
}
