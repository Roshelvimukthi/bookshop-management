package com.pahanaedu.servlet;
import static org.mockito.Mockito.*;

import com.pahanaedu.servlet.ItemServlet;
import org.junit.Before;
import org.junit.Test;
import javax.servlet.http.*;
import javax.servlet.ServletException;
import java.io.IOException;

public class ItemServletTest {

    private ItemServlet servlet;
    private HttpServletRequest request;
    private HttpServletResponse response;
    private HttpSession session;

    @Before
    public void setUp() {
        servlet = new ItemServlet();
        request = mock(HttpServletRequest.class);
        response = mock(HttpServletResponse.class);
        session = mock(HttpSession.class);
        when(request.getSession(false)).thenReturn(session);
    }

    @Test
    public void testRedirectWithoutSession() throws IOException, ServletException {
        when(session.getAttribute("username")).thenReturn(null);
        // You can create a doGet in servlet for session check similar to CustomerServlet
        servlet.doPost(request, response);
        // Here you would verify response behavior if implemented
    }

    @Test
    public void testAddItemPost() throws ServletException, IOException {
        when(session.getAttribute("username")).thenReturn("admin");
        when(request.getParameter("action")).thenReturn("add");
        when(request.getParameter("name")).thenReturn("Pen");
        when(request.getParameter("price")).thenReturn("120.50");

        servlet.doPost(request, response);
        verify(request).getRequestDispatcher("manageItems.jsp");
    }

    @Test
    public void testUpdateItemPost() throws ServletException, IOException {
        when(session.getAttribute("username")).thenReturn("admin");
        when(request.getParameter("action")).thenReturn("update");
        when(request.getParameter("itemId")).thenReturn("1");
        when(request.getParameter("name")).thenReturn("Pen Updated");
        when(request.getParameter("price")).thenReturn("150.00");

        servlet.doPost(request, response);
        verify(request).getRequestDispatcher("manageItems.jsp");
    }
}
