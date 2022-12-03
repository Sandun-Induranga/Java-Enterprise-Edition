<%@ page import="java.util.ArrayList" %>
<%@ page import="model.CustomerDTO" %>
<%@ page import="java.sql.*" %>
<!DOCTYPE html>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>POS | Customers</title>

    <!-- Font -->
    <link rel="preconnect" href="https://fonts.googleapis.com">
    <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
    <link href="https://fonts.googleapis.com/css2?family=Inter&family=Poppins&family=Source+Sans+Pro:wght@300&display=swap"
          rel="stylesheet">

    <!-- Bootstrap -->
    <link rel="stylesheet" href="assets/css/bootstrap.min.css">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.3.0/font/bootstrap-icons.css" rel="stylesheet">

    <link rel="stylesheet" href="assets/css/styles.css">

    <meta name="viewport" content="width=device-width initial-scale=1">

</head>

<body class="container-fluid">

<%
    //    ArrayList<model.CustomerDTO> customerDTOS;

//    Class.forName("com.mysql.jdbc.Driver");
//    Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/POS", "sandu", "1234");
//    PreparedStatement pstm = connection.prepareStatement("SELECT * FROM Customer");
//    ResultSet resultSet = pstm.executeQuery();
//
//    while (resultSet.next()){
//        System.out.println("Come");
//    }

//    customerDTOS.add(new CustomerDTO("C00-001", "Dasun", "Galle", 10000));
//    customerDTOS.add(new CustomerDTO("C00-002", "Dasun", "Galle", 10000));
//    customerDTOS.add(new CustomerDTO("C00-003", "Dasun", "Galle", 10000));
//    customerDTOS.add(new CustomerDTO("C00-004", "Dasun", "Galle", 10000));
//    customerDTOS.add(new CustomerDTO("C00-005", "Dasun", "Galle", 10000));

%>

<main class="d-flex row align-items-center justify-content-center">

    <section
            class="d-flex full-height flex-column h-75 col-md-10 bg-white shadow-lg rounded-5 align-items-center justify-content-between">

        <!-- Nav bar -->
        <div class="row w-100">
            <nav class="navbar navbar-expand-lg bg-transparent border-bottom sticky-top">
                <div class="container-fluid">
                    <a class="navbar-brand" href="#"><i class="bi bi-back red-text"></i> Business <span
                            class="red-text">Name</span></a>
                    <button class="navbar-toggler" type="button" data-bs-toggle="collapse"
                            data-bs-target="#navbarSupportedContent" aria-controls="navbarSupportedContent"
                            aria-expanded="false" aria-label="Toggle navigation">
                        <span class="navbar-toggler-icon"></span>
                    </button>
                    <div class="collapse navbar-collapse" id="navbarSupportedContent">
                        <ul class="navbar-nav me-auto mb-2 mb-lg-0">
                            <li class="nav-item">
                                <a class="nav-link" aria-current="page" href="#"><i class="bi bi-house-fill"></i>
                                    Dashboard</a>
                            </li>
                            <li class="nav-item">
                                <a class="nav-link active" aria-current="page" href="#"><i
                                        class="bi bi-people-fill"></i> Customers</a>
                            </li>
                            <li class="nav-item">
                                <a class="nav-link" aria-current="page" href="#"><i
                                        class="bi bi-handbag-fill"></i> Items</a>
                            </li>
                            <li class="nav-item">
                                <a class="nav-link" aria-current="page" href="#"><i class="bi bi-cart-check-fill"></i>
                                    Place Orders</a>
                            </li>
                            <li class="nav-item">
                                <a class="nav-link active" aria-current="page" href="#"><i
                                        class="bi bi-cloud-arrow-down-fill"></i>
                                    Order Details</a>
                            </li>

                        </ul>
                        <ul class="navbar-nav me-auto mb-2 mb-lg-0">
                            <li class="nav-item">
                                <a class="nav-link" aria-current="page" href="#"><i class="bi bi-box-arrow-left"></i>
                                    Logout</a>
                            </li>
                        </ul>
                    </div>
                </div>
            </nav>
        </div>

        <!-- Search bar -->
        <div class="row">
            <nav class="navbar">
                <div class="container-fluid d-flex flex-column flex-lg-row">
                    <form class="d-flex flex-column flex-lg-row" role="search" method="get" id="form1">
                        <input class="form-control me-2 mb-2 mb-lg-0" type="search" placeholder="Search"
                               aria-label="Search" id="txtSearch">
                        <button class="btn btn-warning dropdown-toggle me-2 mb-2 mb-lg-0" type="button"
                                data-bs-toggle="dropdown" aria-expanded="false">
                            Dropdown button
                        </button>
                        <ul class="dropdown-menu">
                            <li><a class="dropdown-item" href="#">Action</a></li>
                            <li><a class="dropdown-item" href="#">Another action</a></li>
                            <li><a class="dropdown-item" href="#">Something else here</a></li>
                        </ul>
                        <button class="btn btn-outline-success me-2" type="submit">Search</button>
                    </form>
                    <!-- Add Customers Button -->
                    <button type="button" class="btn btn-dark me-2" data-bs-toggle="modal"
                            data-bs-target="#staticBackdrop">
                        Add New Customer
                    </button>

                    <!-- Get All Button -->
                    <button type="submit" class="btn btn-dark" id="btnGetAll" form="form1" formaction="customer" formmethod="get">
                        Get All
                    </button>
                </div>
            </nav>
        </div>

        <!-- Modal -->
        <div class="modal fade" id="staticBackdrop" data-bs-backdrop="static" data-bs-keyboard="false" tabindex="-1"
             aria-labelledby="staticBackdropLabel" aria-hidden="true">
            <div class="modal-dialog">
                <div class="modal-content">
                    <div class="modal-header">
                        <h5 class="modal-title" id="staticBackdropLabel">Add Customers</h5>
                        <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                    </div>

                    <!-- Form -->
                    <div class="d-flex justify-content-center">
                        <form id="customerForm" class="w-75">
                            <div class="mb-3">
                                <label for="cusId" class="form-label">Customer ID</label>
                                <input type="text" class="form-control" id="cusId" name="cusId">
                            </div>
                            <div class="mb-3">
                                <label for="cusName" class="form-label">Customer Name</label>
                                <input type="text" class="form-control" id="cusName" name="cusName">
                            </div>
                            <div class="mb-3">
                                <label for="cusAddress" class="form-label">Address</label>
                                <input type="text" class="form-control" id="cusAddress" name="cusAddress">
                            </div>
                            <div class="mb-3">
                                <label for="cusSalary" class="form-label">Salary</label>
                                <input type="text" class="form-control" id="cusSalary" name="cusSalary">
                            </div>
                        </form>
                    </div>

                    <div class="modal-footer">
                        <button name="" type="button" class="btn btn-warning" data-bs-dismiss="modal">Close</button>
                        <button type="submit" class="btn btn-success" form="customerForm"
                                formaction="customer?operation=add"
                                formmethod="post" id="btnSaveCustomer">Save
                        </button>
                    </div>
                </div>
            </div>
        </div>

        <!-- Table -->
        <div class="row d-flex overflow-auto mb-5 h-50 col-lg-8 col-12">
            <table class="table table-striped table-hover" id="tblCustomer">
                <thead class="table-dark sticky-top">
                <tr>
                    <th scope="col">Customer ID</th>
                    <th scope="col">Name</th>
                    <th scope="col">Address</th>
                    <th scope="col">Salary</th>
                    <th scope="col">Options</th>
                </tr>
                </thead>
                <tbody id="body">

                <%
                    //                    ArrayList<CustomerDTO> customerDTOS = new ArrayList<>();
                    ArrayList<CustomerDTO> customerDTOs = (ArrayList<CustomerDTO>) request.getAttribute("customers");


                    for (CustomerDTO customerDTO : customerDTOs) {

                %>
                <tr>
                    <td><%=customerDTO.getId()%>
                    </td>
                    <td><%=customerDTO.getName()%>
                    </td>
                    <td><%=customerDTO.getAddress()%>
                    </td>
                    <td><%=customerDTO.getSalary()%>
                    </td>
                    <td class="">
                        <form id="buttons">
                            <button type="button" class="border border-0 customer-edits"><i
                                    class="bi bi-pencil-fill text-success" data-bs-toggle="modal"
                                    data-bs-target="#staticBackdrop"></i></button>
                        </form>
                        <button class="border border-0 customer-deletes" form="customerForm" formaction="customer?operation=delete" formmethod="post"><i class="bi bi-trash text-danger"></i></button>
                    </td>
                </tr>
                <%
                    }

                %>

                </tbody>
            </table>
        </div>

    </section>
</main>

<!-- JS For Bootstrap -->
<script src="assets/js/bootstrap.min.js"></script>
<script src="assets/js/jquery-3.6.1.min.js"></script>

<script>
    // Edit button on action

    $(".customer-edits").on("click", function () {
        var id = $(this).parent().parent().parent().children(":eq(0)").text();

        var name = $(this).parent().parent().parent().children(":eq(1)").text();

        var address = $(this).parent().parent().parent().children(":eq(2)").text();

        var salary = $(this).parent().parent().parent().children(":eq(3)").text();

        setCustomerTextFields(id, name, address, salary);
        $("#btnSaveCustomer").text("Update");
        $("#btnSaveCustomer").attr("formaction", "customer?operation=update");
    });

    $(".customer-deletes").on("click", function () {
        var id = $(this).parent().parent().children(":eq(0)").text();

        var name = $(this).parent().parent().children(":eq(1)").text();

        var address = $(this).parent().parent().children(":eq(2)").text();

        var salary = $(this).parent().parent().children(":eq(3)").text();

        setCustomerTextFields(id, name, address, salary);
        $("#btnSaveCustomer").text("Update");
        $("#btnSaveCustomer").attr("formaction", "customer?operation=update");
    });

    function setCustomerTextFields(id, name, address, salary) {
        $("#cusId").val(id.trim());
        $("#cusName").val(name.trim());
        $("#cusAddress").val(address.trim());
        $("#cusSalary").val(salary.trim());
    }

    $("#btnGetAll").on("click", function () {
        $("#btnSaveCustomer").text("Update");
        $("#btnSaveCustomer").attr("formaction", "customer?operation=add");
    })

</script>

</body>

</html>