package com.pahanaedu.servlet;

import com.pahanaedu.dao.BillDAO;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Timestamp;

@WebServlet("/bill")
public class BillServlet extends HttpServlet {
    private BillDAO billDAO = new BillDAO();

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("application/json");
        PrintWriter out = response.getWriter();

        String accountNumber = request.getParameter("accountNumber");
        String totalAmountStr = request.getParameter("totalAmount");
        String[] itemIds = request.getParameterValues("itemIds[]");
        String[] quantities = request.getParameterValues("quantities[]");

        if (accountNumber == null || totalAmountStr == null || itemIds == null || quantities == null) {
            out.write("{\"error\": \"Invalid bill data\"}");
            out.flush();
            return;
        }

        double totalAmount = Double.parseDouble(totalAmountStr);
        Timestamp billDate = new Timestamp(System.currentTimeMillis());

        int billId = billDAO.saveBill(accountNumber, totalAmount, billDate, itemIds, quantities);
        if (billId > 0) {
            out.write("{\"billId\": " + billId + "}");
        } else {
            out.write("{\"error\": \"Failed to save bill\"}");
        }
        out.flush();
    }
}