<!DOCTYPE html>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<html lang="en">
<head>

    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Home Page</title>

    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
    <script src="https://code.jquery.com/jquery-2.2.4.min.js"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>

    <link rel="stylesheet" href="../css/footer.css">
    <link rel="stylesheet" href="../css/403Page.css">

</head>
<body>
<header style="background-color: black;">

    <div class="banner">
        <div class="container">
            <img class="img img-responsive" src="../images/headerImage.png"
                 id="bannerHeader" style="width: auto; margin-left: 0;">
        </div>
    </div>

</header>

<!-- NAVBAR -->
<nav class="navbar navbar-inverse">
    <div class="container-fluid">
        <div class="navbar-header">
            <button type="button" class="navbar-toggle" data-toggle="collapse" data-target="#myNavbar">
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
            </button>
            <a class="navbar-brand">Shopping Microservice</a>
        </div>
        <div class="collapse navbar-collapse" id="myNavbar">
            <ul class="nav navbar-nav">
                <li class="active"><a href="/home">Home</a></li>
                <li><a href="/goToLivingRoomProductsPage">Living Room Products</a></li>
                <li><a href="/goToKitchenProductsPage">Kitchen Products</a></li>
            </ul>
            <ul class="nav navbar-nav navbar-right">
                <li><a href="/goToUserAccount"><span class="glyphicon glyphicon-user"></span> Account</a></li>
                <li><a href="/goToLogout"><span class="glyphicon glyphicon-log-out"></span> Logout</a></li>
            </ul>
        </div>
    </div>
</nav>

<div>
    <h2>ACCESS DENIED</h2>
    <hr class="style1">
    <h5> You do not have permission to view this content.
        <br>
        Please go back to the home page: </h5>
    <a href="/home"><button type="button" class="btn btn-danger">Home Page</button></a>
</div>

<footer>
    <section class="nb-footer">
        <div class="container">
            <div class="row">
                <div class="col-md-4 col-sm-8">
                    <div class="footer-single">
                        <div class="footer-title">
                            <h2>Description</h2>
                        </div>
                        <img src="" class="img-responsive" alt="Logo">

                        <p>Nell'ambito della scelta dell'argomento di tesi, l'attenzione e' rivolta
                            principalmente all'utilizzo di uno standard di autenticazione basato sul
                            protocollo OAuth 2.0 chiamato openID Connect e applicato su un'architettura
                            basata sui microservizi.
                            OpenID Connect ha come obiettivo principale l'autenticazione di un utente
                            finale e consente alle applicazioni (come browser, cellulari, client desktop
                            ecc.) di richiedere e ricevere informazioni sulle sessioni autenticate e di
                            verificare l'identità dell'utente stesso.
                            L'architettura basata sui microservizi, invece, e' una pratica di sviluppo che
                            permette di suddividere un'applicazione in una serie di parti piu' piccole e
                            specializzate, ciascuna delle quali comunica con le altre attraverso interfacce
                            comuni come API e interfacce REST utilizzando il protocollo http.
                        </p>
                    </div>
                </div>

                <div class="col-md-4 col-sm-8">
                    <div class="footer-single useful-links">
                        <div class="footer-title">
                            <h2>Site Navigation</h2>
                        </div>

                        <ul class="list-unstyled">
                            <li><a href="#">Home</a></li>
                            <li><a href="/goToKitchenProductsPage">Kitchen Products</a></li>
                            <li><a href="/goToLivingRoomProductsPage">Living Room Products</a></li>
                            <li><a href="/goToUserAccount">Account</a></li>
                            <li><a href="#">Page 3</a></li>
                        </ul>

                    </div>
                </div>
                <div class="clearfix visible-sm"></div>

                <div class="col-md-4 col-sm-8">
                    <div class="footer-single">
                        <div class="footer-title">
                            <h2>Contacts</h2>
                        </div>
                        <address>
                            <p class="titoloIndirizzo" style="color: #7BFF00;">Sede</p>
                            <p>Rende, CS</p>
                            <br>
                            <p class="titoloIndirizzo" style="color: #7BFF00;">Sviluppatore</p>
                            <p>Simone Crisafi</p>
                            <br>
                            <p class="titoloIndirizzo" style="color: #7BFF00;">Indirizzo
                                E-mail</p>
                            <a href="mailto:crisafiSimone@gmail.com">crisafiSimone@gmail.com</a><br>
                        </address>
                    </div>
                </div>

            </div>
        </div>
    </section>

    <section class="nb-copyright">
        <div class="container">
            <div class="row">
                <div class="col-sm-6 copyrt xs-center">2018 © All Rights
                    Reserved.</div>
            </div>
        </div>
    </section>
</footer>

</body>
</html>