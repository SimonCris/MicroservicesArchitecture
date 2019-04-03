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
    <link rel="stylesheet" href="../css/homePage.css">
    <link rel="stylesheet" href="../css/snackbar.css">

    <script src="../js/snackbar.js" defer></script>
    <script src="../js/homePageJs.js" defer></script>


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
            <a><img class="img img-responsive" src="../images/logo.png"
                    style="width: 50px; height: 50px;"></a>
        </div>
        <div class="collapse navbar-collapse" id="myNavbar">
            <ul class="nav navbar-nav">
                <li class="active"><a href="/home">Home</a></li>
                <li><a href="/goToLivingRoomProductsPage">Living Room Products</a></li>
                <li><a href="/goToKitchenProductsPage">Kitchen Products</a></li>
            </ul>
            <ul class="nav navbar-nav navbar-right">
                <c:if test="${userInfo.user_consent == 'true'}">
                <li><a href="/goToUserAccount"><span class="glyphicon glyphicon-user"></span> Welcome, ${userInfo.user_email}</a></li>
                </c:if>
                <c:if test="${userInfo.user_consent == 'false'}">
                    <li><a href="/goToUserAccount"><span class="glyphicon glyphicon-user"></span> Welcome</a></li>
                </c:if>
                <li><a href="/goToLogout"><span class="glyphicon glyphicon-log-out"></span> Logout</a></li>
            </ul>
        </div>
    </div>
</nav>

<!-- SE L'UTENTE NON HA DATO IL CONSENSO PER IL TRATTAMENTO DEI PROPRI DATI PERSONALI, LE FUNZIONALITA'
     DI GESTIONE DEI PRODOTTI (ADD TO FAVORITE) NON SARANNO ABILITATE -->

<!-- PRODUCTS CONTAINERS -->
<div class="container productsContainer">
    <h2 class = "productInfo">Kitchen Products List</h2>
    <p class = "productInfo">This list shows all the kitchen products contained in the DB:</p>

    <hr class="style1">

    <div class="well well-sm">
        <strong>Display</strong>
        <div class="btn-group">
            <a href="#" id="list" class="btn btn-default btn-sm"><span class="glyphicon glyphicon-th-list">
            </span>List</a> <a href="#" id="grid" class="btn btn-default btn-sm"><span
                class="glyphicon glyphicon-th"></span>Grid</a>
        </div>
    </div>

    <!-- KITCHEN PRODUCTS -->
    <div id="products" class="row list-group">
        <c:forEach items="${kitchenProductsList}" var="prod" begin="0" end="2" step="1">
        <div class="item  col-xs-4 col-lg-4">
            <div class="thumbnail">
                <a href="/productPage/${prod.idkitchen_product}&kitchen"><img class="group list-group-image img-responsive"
                                                                              src="../images/allProducts/${prod.kitchen_product_image}"
                                            alt="${prod.kitchen_product_image}" style="height: 20%; weight: 50%;"/> </a>
                <div class="caption">
                    <h4 class="group inner list-group-item-heading productInfo">
                        ${prod.kitchen_product_name}</h4>
                    <p class="group inner list-group-item-text productInfo">
                        ${prod.kitchen_product_brand}</p>
                    <div class="row">
                        <div class="col-xs-12 col-md-6">
                            <p class="lead productInfo">
                                ${prod.kitchen_product_price}</p>
                        </div>
                        <c:if test="${userInfo.user_consent == 'true'}">
                        <div class="col-xs-12 col-md-6">
                            <button type="button" class="btn btn-success"
                            onclick="addToFavorite('${prod.kitchen_product_name}',
                            '${prod.kitchen_product_brand}','${prod.kitchen_product_image}',
                                    '${prod.kitchen_product_price}')"
                            >Add to favorite</button>
                        </div>
                        </c:if>
                    </div>
                </div>
            </div>
        </div>
        </c:forEach>
        <c:forEach items="${kitchenProductsList}" var="prod" begin="3" end="5" step="1">
            <div class="item  col-xs-4 col-lg-4">
                <div class="thumbnail">
                    <a href="/productPage/${prod.idkitchen_product}&kitchen"><img class="group list-group-image img-responsive"
                                                                                  src="../images/allProducts/${prod.kitchen_product_image}"
                                                alt="${prod.kitchen_product_image}" style="height: 20%; weight: 50%;"/> </a>
                    <div class="caption">
                        <h4 class="group inner list-group-item-heading productInfo">
                                ${prod.kitchen_product_name}</h4>
                        <p class="group inner list-group-item-text productInfo">
                                ${prod.kitchen_product_brand}</p>
                        <div class="row">
                            <div class="col-xs-12 col-md-6">
                                <p class="lead productInfo">
                                        ${prod.kitchen_product_price}</p>
                            </div>
                            <c:if test="${userInfo.user_consent == 'true'}">
                            <div class="col-xs-12 col-md-6">
                                <button type="button" class="btn btn-success"
                                        onclick="addToFavorite('${prod.kitchen_product_name}',
                                                '${prod.kitchen_product_brand}','${prod.kitchen_product_image}',
                                                '${prod.kitchen_product_price}')"
                                >Add to favorite</button>
                            </div>
                            </c:if>
                        </div>
                    </div>
                </div>
            </div>
        </c:forEach>
    </div>

    <hr class="style2">

    <h2 class = "productInfo">Living Room Products List</h2>
    <p  class = "productInfo">This list shows all the living room products contained in the DB:</p>

    <hr class="style1">

    <!-- KITCHEN PRODUCTS -->
    <div id="products2" class="row list-group">
        <c:forEach items="${livingRoomProductsList}" var="prod" begin="0" end="2" step="1">
            <div class="item  col-xs-4 col-lg-4">
                <div class="thumbnail">
                    <a href="/productPage/${prod.idliving_room_product}&livingRoom"> <img class="group list-group-image img-responsive"
                                                 src="../images/allProducts/${prod.living_room_product_image}"
                                                 alt="${prod.living_room_product_image}" style="height: 20%; weight: 50%;"/> </a>
                    <div class="caption">
                        <h4 class="group inner list-group-item-heading productInfo">
                                    ${prod.living_room_product_name}</h4>
                        <p class="group inner list-group-item-text productInfo">
                                    ${prod.living_room_product_brand}</p>
                        <div class="row">
                            <div class="col-xs-12 col-md-6">
                                <p class="lead productInfo">
                                            ${prod.living_room_product_price}</p>
                            </div>
                            <c:if test="${userInfo.user_consent == 'true'}">
                            <div class="col-xs-12 col-md-6">
                                <button type="button" class="btn btn-success"
                                        onclick="addToFavorite('${prod.living_room_product_name}',
                                                '${prod.living_room_product_brand}','${prod.living_room_product_image}',
                                                '${prod.living_room_product_price}')"
                                >Add to favorite</button>
                            </div>
                            </c:if>
                        </div>
                    </div>
                </div>
            </div>
        </c:forEach>
        <c:forEach items="${livingRoomProductsList}" var="prod" begin="3" end="5" step="1">
            <div class="item  col-xs-4 col-lg-4">
                <div class="thumbnail">
                    <a href="/productPage/${prod.idliving_room_product}&livingRoom"> <img class="group list-group-image img-responsive"
                                                                                          src="../images/allProducts/${prod.living_room_product_image}"
                             alt="${prod.living_room_product_image}" style="height: 20%; weight: 50%;"/> </a>
                    <div class="caption">
                        <h4 class="group inner list-group-item-heading productInfo">
                                    ${prod.living_room_product_name}</h4>
                        <p class="group inner list-group-item-text productInfo">
                                    ${prod.living_room_product_brand}</p>
                        <div class="row">
                            <div class="col-xs-12 col-md-6">
                                <p class="lead productInfo">
                                            ${prod.living_room_product_price}</p>
                            </div>
                            <c:if test="${userInfo.user_consent == 'true'}">
                            <div class="col-xs-12 col-md-6">
                                <button type="button" class="btn btn-success"
                                        onclick="addToFavorite('${prod.living_room_product_name}',
                                                '${prod.living_room_product_brand}','${prod.living_room_product_image}',
                                                '${prod.living_room_product_price}')"
                                >Add to favorite</button>
                            </div>
                            </c:if>
                        </div>
                    </div>
                </div>
            </div>
        </c:forEach>
        </div>
    </div>
</div>

<br>

<div id="snackbar"></div>
<button onclick="topFunction()" id="myBtn" title="Go to top">Top</button>

<footer>
    <section class="nb-footer">
        <div class="container">
            <div class="row">
                <div class="col-md-4 col-sm-8">
                    <div class="footer-single">
                        <div class="footer-title">
                            <h2>Description</h2>
                        </div>
                        <a><img class="img img-responsive" src="../images/logo.png" style="width: 50%; height: 50%;"></a>

                        <p>Nell'ambito della scelta dell'argomento di tesi, l'attenzione e' rivolta
                            principalmente all'utilizzo di uno standard di autenticazione basato sul
                            protocollo OAuth 2.0 chiamato openID Connect e applicato su un'architettura
                            basata sui microservizi.
                            OpenID Connect ha come obiettivo principale l'autenticazione di un utente
                            finale e consente alle applicazioni (come browser, cellulari, client desktop
                            ecc.) di richiedere e ricevere informazioni sulle sessioni autenticate e di
                            verificare l'identita' dell'utente stesso.
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
                            <li><a href="/goToLogout"> Logout</a></li>
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
                            <a href="mailto:crisafisimone@gmail.com">crisafisimone@gmail.com</a><br>
                        </address>
                    </div>
                </div>

            </div>
        </div>
    </section>

    <section class="nb-copyright">
        <div class="container">
            <div class="row">
                <div class="col-sm-6 copyrt xs-center">2019 © All Rights
                    Reserved.</div>
            </div>
        </div>
    </section>
</footer>

</body>
</html>