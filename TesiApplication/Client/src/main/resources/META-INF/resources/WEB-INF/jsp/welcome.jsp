<!DOCTYPE html>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Welcome Page</title>

    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
    <script src="https://code.jquery.com/jquery-2.2.4.min.js"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>

    <script src="../Js/welcomePageJS.js"></script>
    <link rel="stylesheet" href="../css/welcomePage.css">

</head>
<body>

<header style="background-color: black;">

    <div class="header">
        <div class="col-sm-6 copyrt xs-center" style="color:white;">2019 © All Rights
            Reserved.</div>
        <div class="header-right">
            <a href="#" class="fa fa-facebook"></a>
            <a href="#" class="fa fa-linkedin"></a>
            <a href="#" class="fa fa-instagram"></a>
        </div>
    </div>

</header>

<nav class="navbar navbar-default">
    <div class="container">
        <div class="navbar-header">
            <button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target="#navbar" aria-expanded="false" aria-controls="navbar">
                <span class="sr-only">Toggle navigation</span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
            </button>
            <img class="img img-responsive" src="../images/logo.png"
            style="width: 10%; height: 10%">
        </div>
        <div id="navbar" class="collapse navbar-collapse">
            <ul class="nav navbar-nav navbar-right">
                <li><a class="menuOption" href="#">Contacts</a></li>
                <li><a class="menuOption" href="#">About Us</a></li>
            </ul>
        </div><!--/.nav-collapse -->
    </div>
</nav>

<div class="image">
    <h1 class="heading">Welcome</h1>
    <p><button class="btn btn-large" onclick="goToApiGateway()">Login With Google</button></p>
    <br>
    <h3 class="description" style="text-align: center; color: white">Main Description</h3>
    <p style="color: white">Welcome to the example web application, created as part of a thesis work</p>
</div>

<!-- Footer -->
<footer>
</footer>

</body>
</html>