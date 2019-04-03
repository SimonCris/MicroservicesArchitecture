<!DOCTYPE html>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<html lang="en">
<head>

    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Account Page</title>

    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
    <script src="https://code.jquery.com/jquery-2.2.4.min.js"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>

    <link rel="stylesheet" href="../css/footer.css">
    <link rel="stylesheet" href="../css/userAccount.css">
    <link rel="stylesheet" href="../css/snackbar.css">

    <script src="../js/snackbar.js" defer></script>
    <script src="../js/userAccountJs.js" defer></script>

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

<!-- SE L'UTENTE NON HA DATO IL CONSENSO PER IL TRATTAMENTO DEI PROPRI DATI PERSONALI, IN QUESTA SEZIONE
     NON VERRANNO RECUPERATE LE USER INFO E NEMMENO I PRODOTTI "SCELTI" DALL'UTENTE -->

<div class="container">
    <h2 class = "productInfo">User Account</h2>
    <p class = "productInfo">Portal for managing user data, favorite products and shopping cart.</p>

    <hr class="style1">

    <ul class="nav nav-tabs">
        <li class="active singleTab"><a data-toggle="tab" href="#menu1">User Info</a></li>
        <li class="singleTab"><a data-toggle="tab" href="#menu2">Favorite Products</a></li>
        <li class="singleTab"><a data-toggle="tab" href="#menu3">Shopping Cart</a></li>
        <li class="singleTab"><a data-toggle="tab" href="#menu4">Order Products</a></li>
    </ul>

    <div class="tab-content productContainer">
        <div id="menu1" class="tab-pane fade in active">
            <h3 class="productInfo">User Info</h3>

            <hr class="style1">

            <div class="container bootstrap snippet">
                <div class="panel-body inf-content">
                    <div class="row">
                        <div class="col-md-4">
                            <img alt="" style="width:600px;" title="" class="img-circle img-thumbnail isTooltip" src="../images/user.png" data-original-title="Usuario">
                        </div>
                        <div class="col-md-6">
                            <h4 class="productInfo">Information</h4><br>
                            <c:if test="${userInfo.user_consent == 'true'}">
                            <div class="table-responsive">
                                <table class="table table-condensed table-responsive table-user-information">
                                    <tbody>
                                    <tr>
                                        <td>
                                            <strong>
                                                <p class="productInfo"><span class="glyphicon glyphicon-user text-primary"></span>
                                                Name</p>
                                            </strong>
                                        </td>
                                        <td class="text-primary">
                                            ${userInfo.user_name}
                                        </td>
                                    </tr>
                                    <tr>
                                        <td>
                                            <strong>
                                                <p class="productInfo"><span class="glyphicon glyphicon-cloud text-primary"></span>
                                                    Surname</p>
                                            </strong>
                                        </td>
                                        <td class="text-primary">
                                            ${userInfo.user_surname}
                                        </td>
                                    </tr>
                                    <tr>
                                        <td>
                                            <strong>
                                                <p class="productInfo"><span class="glyphicon glyphicon-calendar text-primary"></span>
                                                    Birthday Date</p>
                                            </strong>
                                        </td>
                                        <td class="text-primary">
                                            ${userInfo.user_birthday_date}
                                        </td>
                                    </tr>
                                    <tr>
                                        <td>
                                            <strong>
                                                <p class="productInfo"><span class="glyphicon glyphicon-envelope text-primary"></span>
                                                    Email</p>
                                            </strong>
                                        </td>
                                        <td class="text-primary">
                                            ${userInfo.user_email}
                                        </td>
                                    </tr>
                                    <tr>
                                        <td>
                                            <strong>
                                                <p class="productInfo"><span class="glyphicon glyphicon-home text-primary"></span>
                                                    Address</p>
                                            </strong>
                                        </td>
                                        <td class="text-primary">
                                            ${userInfo.user_address}
                                        </td>
                                    </tr>
                                    <tr>
                                        <td>
                                            <strong>
                                                <p class="productInfo"><span class="glyphicon glyphicon-flag text-primary"></span>
                                                    City</p>
                                            </strong>
                                        </td>
                                        <td class="text-primary">
                                            ${userInfo.user_domicile_city}
                                        </td>
                                    </tr>
                                    <tr>
                                        <td>
                                            <strong>
                                                <p class="productInfo"><span class="glyphicon glyphicon-list-alt text-primary"></span>
                                                    User Consent</p>
                                            </strong>
                                        </td>
                                        <td class="text-primary row">
                                            <div class="col-sm-3">
                                                <c:if test="${userInfo.user_consent == 'true'}">
                                                    Yes
                                                </c:if>
                                                <c:if test="${userInfo.user_consent == 'false'}">
                                                    No
                                                </c:if>
                                            </div>

                                            <div class="col-sm-3">
                                                <button type="button" class="btn btn-danger"
                                                        onclick="revokeConsent('${userInfo.user_email}')">Revoke Consent</button>
                                            </div>

                                        </td>
                                    </tr>
                                    </tbody>
                                </table>
                            </div>
                            </c:if>
                            <c:if test="${userInfo.user_consent == 'false'}">
                                <div class="table-responsive">
                                    <table class="table table-condensed table-responsive table-user-information">
                                        <tbody>
                                        <tr>
                                            <td class="text-primary">
                                                You have not consented to the processing and management of personal data
                                            </td>
                                        </tr>
                                        </tbody>
                                    </table>
                                </div>
                            </c:if>
                        </div>
                    </div>
                </div>
            </div>

        </div>
        <div id="menu2" class="tab-pane fade">
            <h3 class="productInfo">Favorite Products</h3>

            <hr class="style1">

            <div class="container">

                <c:if test="${! empty (favoriteProducts)}">

                <div class="table-responsive">
                    <table class="table" style="width: 95%;">
                        <thead>
                        <tr>
                            <th class = "productInfo headerName">Image</th>
                            <th class = "productInfo headerName">Name</th>
                            <th class = "productInfo headerName">Brand</th>
                            <th class = "productInfo headerName">Price</th>
                            <th class = "productInfo headerName">Remove from Favorites</th>
                        </tr>
                        </thead>
                        <tbody>
                        <c:forEach items="${favoriteProducts}" var="prod">
                            <tr>
                                <td>
                                    <img class="group list-group-image img-responsive"
                                         src="../images/allProducts/${prod.favorite_product_image}"
                                         alt="${prod.favorite_product_image}" style="height: 20%; weight: 35%;"/> </a>
                                </td>
                                <td class = "productInfo"><strong>${prod.favorite_product_name}</strong></td>
                                <td class = "productInfo"><strong>${prod.favorite_product_brand}</strong></td>
                                <td class = "productInfo"><strong>${prod.favorite_product_price}</strong></td>
                                <td><button type="button" class="btn btn-danger"
                                            onclick="removeFromFavorite('${prod.favorite_product_name}',
                                                    '${userInfo.user_email}')">Remove</button></td>
                            </tr>
                        </c:forEach>
                        </tbody>
                    </table>
                </div>
                </c:if>
                <c:if test="${empty (favoriteProducts)}">

                    <h4 class="productInfo">Favorites Products list is empty!</h4>

                </c:if>
            </div>

        </div>
        <div id="menu3" class="tab-pane fade">
            <h3 class = "productInfo">Shopping Cart</h3>
            <hr class="style1">

            <div class="container">

                <c:if test="${! empty (cartProducts)}">

                    <div class="table-responsive">
                        <table class="table" style="width: 95%;">
                            <thead>
                            <tr>
                                <th class = "productInfo headerName">Image</th>
                                <th class = "productInfo headerName">Name</th>
                                <th class = "productInfo headerName">Brand</th>
                                <th class = "productInfo headerName">Price</th>
                                <th class = "productInfo headerName">Remove from Cart</th>
                                <th class = "productInfo headerName">Add to Order</th>
                            </tr>
                            </thead>
                            <tbody>
                            <c:forEach items="${cartProducts}" var="prod">
                                <tr>
                                    <td>
                                        <img class="group list-group-image img-responsive"
                                             src="../images/allProducts/${prod.cart_product_image}"
                                             alt="${prod.cart_product_image}" style="height: 20%; weight: 35%;"/> </a>
                                    </td>
                                    <td class = "productInfo"><strong>${prod.cart_product_name}</strong></td>
                                    <td class = "productInfo"><strong>${prod.cart_product_brand}</strong></td>
                                    <td class = "productInfo"><strong>${prod.cart_product_price}</strong></td>
                                    <td><button type="button" class="btn btn-danger"
                                                onclick="removeFromCart('${prod.cart_product_name}',
                                                        '${prod.cart_product_user_email}')">Remove</button></td>
                                    <td><button type="button" class="btn btn-success"
                                                onclick="addToOrder('${prod.idcart_product}')">Add</button></td>
                                </tr>
                            </c:forEach>
                            </tbody>
                        </table>
                    </div>
                </c:if>
                <c:if test="${empty (cartProducts)}">

                    <h4 class="productInfo">Cart Products list is empty!</h4>

                </c:if>
            </div>
        </div>
        <div id="menu4" class="tab-pane fade">
            <h3 class = "productInfo">Order Products</h3>
            <hr class="style1">

            <div class="container">

                <c:if test="${! empty (orderProducts)}">

                    <div class="table-responsive">
                        <table class="table" style="width: 95%;">
                            <thead>
                            <tr>
                                <th class = "productInfo headerName">Image</th>
                                <th class = "productInfo headerName">Name</th>
                                <th class = "productInfo headerName">Brand</th>
                                <th class = "productInfo headerName">Price</th>
                                <th class = "productInfo headerName">Remove from Order</th>
                            </tr>
                            </thead>
                            <tbody>
                            <c:forEach items="${orderProducts}" var="prod">
                                <tr>
                                    <td>
                                        <img class="group list-group-image img-responsive"
                                             src="../images/allProducts/${prod.cart_product_image}"
                                             alt="${prod.cart_product_image}" style="height: 20%; weight: 35%;"/> </a>
                                    </td>
                                    <td class = "productInfo"><strong>${prod.cart_product_name}</strong></td>
                                    <td class = "productInfo"><strong>${prod.cart_product_brand}</strong></td>
                                    <td class = "productInfo"><strong>${prod.cart_product_price}</strong></td>
                                    <td><button type="button" class="btn btn-danger"
                                                onclick="removeToOrder('${prod.idcart_product}')">Remove</button></td>
                                </tr>
                            </c:forEach>
                            <tr style="text-align: right">
                                <td></td>
                                <td></td>
                                <td></td>
                                <td></td>
                                <td>
                                    <button type="button" class="btn btn-success"
                                            onclick="buyFunction()">Buy</button>
                                </td>
                            </tr>
                            </tbody>
                        </table>
                    </div>
                </c:if>
                <c:if test="${empty (orderProducts)}">

                    <h4 class="productInfo">Order Products list is empty!</h4>

                </c:if>
            </div>
        </div>
    </div>
</div>

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