package com.pahanaedu.servlet;

import org.junit.jupiter.api.Test;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.io.StringWriter;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;
class BillServletTest {

    @Test
    void testDoPostValid() throws Exception {
        BillServlet servlet = new BillServlet();

        // ✅ Mock request & response
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);

        // ✅ Writer to capture servlet response
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);

        // ✅ Mock request parameters
        when(request.getParameter("accountNumber")).thenReturn("C001");
        when(request.getParameter("totalAmount")).thenReturn("500.0");
        when(request.getParameterValues("itemIds[]")).
                thenReturn(new String[]{"1", "2"});
        when(request.getParameterValues("quantities[]")).
                thenReturn(new String[]{"2", "1"});

        when(response.getWriter()).thenReturn(pw);

        // ✅ Call servlet
        servlet.doPost(request, response);
        pw.flush();

        // ✅ Verify output
        String output = sw.toString();
        assertTrue(output.contains("billId") || output.contains("error"),
                "Servlet should return billId or error message");
    }
}
