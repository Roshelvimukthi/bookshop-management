<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.pahanaedu.dao.CustomerDAO, com.pahanaedu.model.Customer, com.pahanaedu.dao.ItemDAO, com.pahanaedu.model.Item, java.util.List, java.util.stream.Collectors, java.time.ZonedDateTime, java.time.format.DateTimeFormatter" %>
<%!
    private CustomerDAO customerDAO = new CustomerDAO();
%>
<%
    List<Customer> customers = customerDAO.getAllCustomers();
    String message = (String) session.getAttribute("message");
    String error = (String) session.getAttribute("error");
    session.removeAttribute("message");
    session.removeAttribute("error");
%>
<!DOCTYPE html>
<html>
<head>
    <title>Pahana Edu - Generate Bill</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <style>
        #billPreview { min-height: 300px; padding: 20px; border: 1px solid #ddd; }
        .printable-bill { font-size: 14px; }
        .printable-bill h4 { margin-top: 0; }
        .printable-bill ul { list-style: none; padding-left: 0; }
        .printable-bill ul li { margin-bottom: 5px; }
        @media print {
            body * { visibility: hidden; }
            #billPreview, #billPreview * { visibility: visible; }
            #billPreview { position: absolute; left: 0; top: 0; width: 100%; }
        }
        .alert { margin-top: 10px; }
    </style>
    <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
    <script>
        var customers = [
            <%
                for (int i = 0; i < customers.size(); i++) {
                    Customer c = customers.get(i);
                    out.print("{accountNumber: '" + c.getAccountNumber() + "', name: '" + c.getName().replace("'", "\\'") + "'}");
                    if (i < customers.size() - 1) out.print(",");
                }
            %>
        ];

        var items = [];
        <%
            ItemDAO itemDAO = new ItemDAO();
            List<Item> itemList = itemDAO.getAllItems();
            for (Item item : itemList) {
                out.println("items.push({id: " + item.getItemId() + ", name: '" + item.getName().replace("'", "\\'") + "', price: " + item.getPrice() + "});");
            }
        %>

        function addItemRow() {
            var table = document.getElementById("itemsTable").getElementsByTagName("tbody")[0];
            var row = table.insertRow();
            var cell1 = row.insertCell(0);
            var cell2 = row.insertCell(1);
            var cell3 = row.insertCell(2);
            var cell4 = row.insertCell(3);
            var cell5 = row.insertCell(4);

            var select = document.createElement("select");
            select.name = "itemIds[]";
            select.className = "form-control form-select-sm";
            select.onchange = function() { updatePrice(this); updateBillPreview(); };
            var option = document.createElement("option");
            option.value = "";
            option.text = "Select Item";
            select.add(option);
            items.forEach(function(item) {
                var option = document.createElement("option");
                option.value = item.id;
                option.text = item.name + " (Rs." + item.price.toFixed(2) + ")";
                select.add(option);
            });
            cell1.appendChild(select);

            var quantity = document.createElement("input");
            quantity.type = "number";
            quantity.name = "quantities[]";
            quantity.className = "form-control form-control-sm";
            quantity.min = "1";
            quantity.value = "1";
            quantity.onchange = function() { calculateSubtotal(this); updateBillPreview(); };
            cell2.appendChild(quantity);

            var priceSpan = document.createElement("span");
            priceSpan.className = "price";
            priceSpan.innerText = "Rs.0.00";
            cell3.appendChild(priceSpan);

            var subtotalSpan = document.createElement("span");
            subtotalSpan.className = "subtotal";
            subtotalSpan.innerText = "Rs.0.00";
            cell4.appendChild(subtotalSpan);

            var removeBtn = document.createElement("button");
            removeBtn.type = "button";
            removeBtn.className = "btn btn-sm btn-danger";
            removeBtn.innerText = "Remove";
            removeBtn.onclick = function() { removeRow(this); updateBillPreview(); };
            cell5.appendChild(removeBtn);

            updateBillPreview();
        }

        function removeRow(button) {
            var row = button.parentNode.parentNode;
            row.parentNode.removeChild(row);
            calculateTotal();
        }

        function updatePrice(select) {
            var row = select.parentNode.parentNode;
            var priceSpan = row.cells[2].firstChild;
            var selectedItem = items.find(item => item.id == select.value);
            priceSpan.innerText = selectedItem ? "Rs." + selectedItem.price.toFixed(2) : "Rs.0.00";
            calculateSubtotal(select);
        }

        function calculateSubtotal(element) {
            var row = element.parentNode.parentNode;
            var price = parseFloat(row.cells[2].firstChild.innerText.replace("Rs.", "")) || 0;
            var quantity = parseInt(row.cells[1].firstChild.value) || 0;
            row.cells[3].firstChild.innerText = "Rs." + (price * quantity).toFixed(2);
            calculateTotal();
        }

        function calculateTotal() {
            var subtotals = document.querySelectorAll(".subtotal");
            var total = 0;
            subtotals.forEach(function(st) {
                total += parseFloat(st.innerText.replace("Rs.", "")) || 0;
            });
            document.getElementById("totalAmount").innerText = "Rs." + total.toFixed(2);
            document.getElementById("totalAmountHidden").value = total.toFixed(2);
            updateBillPreview();
        }

        function updateCustomerDetails() {
            var accountNumber = document.getElementById("accountNumber").value;
            var customer = customers.find(c => c.accountNumber === accountNumber);
            if (customer) {
                document.getElementById("customerName").innerText = customer.name;
                document.getElementById("customerAccount").innerText = accountNumber;
            } else {
                document.getElementById("customerName").innerText = "";
                document.getElementById("customerAccount").innerText = "";
            }
            var now = new Date().toLocaleString("en-US", { timeZone: "Asia/Colombo" });
            document.getElementById("billDateTime").innerText = now;
            updateBillPreview();
        }

        function updateBillPreview() {
            var accountNumber = document.getElementById("accountNumber").value;
            var customer = customers.find(c => c.accountNumber === accountNumber);
            if (customer) {
                document.getElementById("customerName").innerText = customer.name;
                document.getElementById("customerAccount").innerText = accountNumber;
            } else {
                document.getElementById("customerName").innerText = "";
                document.getElementById("customerAccount").innerText = "";
            }
            var now = new Date().toLocaleString("en-US", { timeZone: "Asia/Colombo" });
            document.getElementById("billDateTime").innerText = now;

            var rows = document.getElementById("itemsTable").getElementsByTagName("tbody")[0].rows;
            var billItems = document.getElementById("billItems");
            billItems.innerHTML = "";
            for (var i = 0; i < rows.length; i++) {
                var select = rows[i].cells[0].firstChild;
                var quantity = rows[i].cells[1].firstChild.value;
                var price = rows[i].cells[2].firstChild.innerText;
                var subtotal = rows[i].cells[3].firstChild.innerText;
                if (select.value) {
                    var itemName = select.options[select.selectedIndex].text.split(" (Rs.")[0];
                    billItems.innerHTML += "<li>" + itemName + " x " + quantity + "  -  " + price + " = " + subtotal + "</li>";
                }
            }
            var total = parseFloat(document.getElementById("totalAmount").innerText.replace("Rs.", "")) || 0;
            document.getElementById("totalAmount").innerText = "Rs." + total.toFixed(2);
        }

        function saveAndPrintBill() {
            updateBillPreview();
            var formData = $("#billForm").serialize();
            $.ajax({
                url: "bill",
                type: "POST",
                data: formData,
                success: function(response) {
                    if (response.billId) {
                        document.getElementById("billNumber").innerText = "Bill Number: " + response.billId;
                        alert("Bill saved successfully with ID: " + response.billId);
                    } else if (response.error) {
                        alert("Error: " + response.error);
                    }
                    window.print();
                    // Clear form after print
                    clearForm();
                },
                error: function() {
                    alert("Failed to save bill");
                    window.print();
                    // Clear form even if save fails, to reset for next use
                    clearForm();
                }
            });
        }

        function clearForm() {
            // Reset customer selection
            document.getElementById("accountNumber").selectedIndex = 0;
            document.getElementById("customerName").innerText = "";
            document.getElementById("customerAccount").innerText = "";
            document.getElementById("billDateTime").innerText = "";
            document.getElementById("billNumber").innerText = "";
            document.getElementById("totalAmount").innerText = "Rs.0.00";
            document.getElementById("totalAmountHidden").value = "0.00";
            document.getElementById("billItems").innerHTML = "";

            // Clear item table
            var table = document.getElementById("itemsTable").getElementsByTagName("tbody")[0];
            table.innerHTML = "";

            // Add a single empty row
            addItemRow();
        }

        window.onload = function() {
            addItemRow();
            <% if (message != null) { %>
            alert("<%= message %>");
            <% } else if (error != null) { %>
            alert("<%= error %>");
            <% } %>
        };
    </script>
</head>
<body>
<% if (session.getAttribute("username") == null) { response.sendRedirect("login.jsp"); return; } %>
<div class="container mt-5">
    <div class="row">
        <div class="col-md-8">
            <h2>Generate Bill</h2>
            <% if (message != null) { %>
            <div class="alert alert-success"><%= message %></div>
            <% } else if (error != null) { %>
            <div class="alert alert-danger"><%= error %></div>
            <% } %>
            <form id="billForm" action="bill" method="post">
                <div class="mb-3">
                    <label for="accountNumber" class="form-label">Customer Account Number</label>
                    <select class="form-control" id="accountNumber" name="accountNumber" required onchange="updateCustomerDetails()">
                        <option value="">Select Customer</option>
                        <%
                            for (Customer customer : customers) {
                        %>
                        <option value="<%= customer.getAccountNumber() %>"><%= customer.getAccountNumber() %> - <%= customer.getName() %></option>
                        <% } %>
                    </select>
                </div>
                <h3>Items</h3>
                <table class="table" id="itemsTable">
                    <thead>
                    <tr>
                        <th>Item</th>
                        <th>Quantity</th>
                        <th>Price</th>
                        <th>Subtotal</th>
                        <th>Action</th>
                    </tr>
                    </thead>
                    <tbody></tbody>
                </table>
                <button type="button" class="btn btn-secondary mb-3" onclick="addItemRow();">Add Item</button>
                <input type="hidden" name="totalAmount" id="totalAmountHidden" value="0.00">
            </form>
            <a href="dashboard.jsp" class="btn btn-secondary mt-3">Back to Dashboard</a>
        </div>
        <div class="col-md-4">
            <div id="billPreview" class="card p-3">
                <div class="printable-bill" id="printableBill">
                    <h4>Bill Receipt</h4>
                    <p><strong>Bill Number:</strong> <span id="billNumber"></span></p>
                    <p><strong>Customer:</strong> <span id="customerName"></span></p>
                    <p><strong>Account Number:</strong> <span id="customerAccount"></span></p>
                    <p><strong>Date & Time:</strong> <span id="billDateTime"></span></p>
                    <p><strong>Pahana Edu Tel</strong> : 0779812245/0776317245</p>
                    <p><strong>Thank you! and come a gain.....</strong></p>
                    <ul id="billItems"></ul>
                    <hr>
                    <p><strong>Total: <span id="totalAmount">Rs.0.00</span></strong></p>
                    <button type="button" class="btn btn-sm btn-primary mt-2" onclick="saveAndPrintBill()">Print Bill</button>
                </div>
            </div>
        </div>
    </div>
</div>
</body>
</html>