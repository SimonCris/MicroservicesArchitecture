<!DOCTYPE html>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<html lang="en">
<head>

    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Product Page</title>

    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
    <script src="https://code.jquery.com/jquery-2.2.4.min.js"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>

    <link rel="stylesheet" href="../css/footer.css">
    <link rel="stylesheet" href="../css/productPage.css">
    <link rel="stylesheet" href="../css/snackbar.css">

    <script src="../js/singleProductPageJs.js" defer></script>
    <script src="../js/snackbar.js" defer></script>

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
                <li><a href="/home">Home</a></li>
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
     DI GESTIONE DEI PRODOTTI (ADD TO CART) NON SARANNO ABILITATE -->

<c:if test="${ProductType == 'Kitchen'}">
<!-- Page Content -->
<div class="container">

    <div class="row">

        <div class="col-lg-3">
            <h1 class="my-4 productInfo">Product Area</h1>
            <hr class="style1">
            <h3 class="my-4 productInfo">Kitchen</h3>
            <br>
        </div>

        <div class="col-lg-9 productContainer container">

            <div class="card mt-4">
                    <img class="card-img-top img-fluid responsive"
                        src="../images/allProducts/${KitchenProduct.kitchen_product_image}"
                        alt="${KitchenProduct.kitchen_product_image}">
                <hr class="style1">
                <div class="card-body">
                    <h3 class="card-title productInfo">Name & Brand</h3>
                    <h5 class="productInfo">${KitchenProduct.kitchen_product_name} --- ${KitchenProduct.kitchen_product_brand}</h5>
                    <hr class="separator">
                    <h3 class="card-title productInfo">Price</h3>
                    <h5 class="productInfo">${KitchenProduct.kitchen_product_price}</h5>
                    <hr class="separator">
                    <h3 class="card-title productInfo">Description</h3>
                    <p class="card-text productInfo">${KitchenProduct.kitchen_product_description}</p>
                    <hr class="separator">
                    <c:if test="${userInfo.user_consent == 'true'}">
                    <div style="text-align: right">
                        <button type="button" class="btn btn-warning"
                                onclick="addToCart('${KitchenProduct.kitchen_product_name}', '${KitchenProduct.kitchen_product_brand}',
                                    '${KitchenProduct.kitchen_product_image}', '${KitchenProduct.kitchen_product_price}')"
                        >Add to Cart</button>
                    </div>
                    </c:if>
                </div>
            </div>
        </div>
    </div>

</div>
</c:if>

<c:if test="${ProductType == 'LivingRoom'}">
    <!-- Page Content -->
    <div class="container">

        <div class="row">

            <div class="col-lg-3">
                <h1 class="my-4 productInfo">Product Area</h1>
                <hr class="style1">
                <h3 class="my-4 productInfo">Living Room</h3>
                <br>
            </div>

            <div class="col-lg-9 productContainer container">

                <div class="card mt-4">
                    <img class="card-img-top img-fluid responsive"
                         src="../images/allProducts/${LivingRoomProduct.living_room_product_image}"
                         alt="${LivingRoomProduct.living_room_product_image}">
                    <hr class="style1">
                    <div class="card-body">
                        <h3 class="card-title productInfo">Name & Brand</h3>
                        <h5 class="productInfo">${LivingRoomProduct.living_room_product_name} --- ${LivingRoomProduct.living_room_product_brand}</h5>
                        <hr class="separator">
                        <h3 class="card-title productInfo">Price</h3>
                        <h5 class="productInfo">${LivingRoomProduct.living_room_product_price}</h5>
                        <hr class="separator">
                        <h3 class="card-title productInfo">Description</h3>
                        <p class="card-text productInfo">${LivingRoomProduct.living_room_product_description}</p>
                        <hr class="separator">
                        <c:if test="${userInfo.user_consent == 'true'}">
                        <div style="text-align: right">
                            <button type="button" class="btn btn-warning"
                                    onclick="addToCart('${LivingRoomProduct.living_room_product_name}', '${LivingRoomProduct.living_room_product_brand}',
                                            '${LivingRoomProduct.living_room_product_image}', '${LivingRoomProduct.living_room_product_price}')"
                            >Add to Cart</button>
                        </div>
                        </c:if>
                    </div>
                </div>
            </div>
        </div>
    </div>
</c:if>

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