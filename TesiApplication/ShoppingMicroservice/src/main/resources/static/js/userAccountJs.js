/* Back to Top Button */
window.onscroll = function() {
    scrollFunction()
};

function scrollFunction() {
    if (document.body.scrollTop > 20 || document.documentElement.scrollTop > 20) {
        document.getElementById("myBtn").style.display = "block";
    } else {
        document.getElementById("myBtn").style.display = "none";
    }
}

// When the user clicks on the button, scroll to the top of the document
function topFunction() {
    document.body.scrollTop = 0;
    document.documentElement.scrollTop = 0;
}

//Remove Product from favorites
function removeFromFavorite(name, userEmail) {

    $.ajax({

        url: "http://localhost:9090/removeFromFavorite",
        type: "GET",
        data:{
            "prodName" : name,
            "userEmail" : userEmail
        },
        success: function (data, status, xhr) {

            var resp = xhr.getResponseHeader("Response");

            if(resp == "ProductRemoved!!") {

                $("#snackbar").text("Prodotto rimosso dai preferiti");
                $("#snackbar").css({
                    'background-color' : 'green'
                });
                showSnackbar();

                window.setTimeout(function(){
                    window.location.replace("http://localhost:9090/goToUserAccount");}, 1500);

            }
            else if(resp == "Product not Removed!"){

                $("#snackbar").text("Prodotto gia' rimosso dai preferiti");
                $("#snackbar").css({
                    'background-color' : 'red'
                });
                showSnackbar();

            }

        },
        error: function(result) {

            $("#snackbar").text("Non hai i permessi per poter eseguire questa operazione");
            $("#snackbar").css({
                'background-color' : 'red'
            });
            showSnackbar();

        }

    });

}

//Remove Product from cart
function removeFromCart(name, userEmail) {

    $.ajax({

        url: "http://localhost:9090/removeFromCart",
        type: "GET",
        data:{
            "prodName" : name,
            "userEmail" : userEmail
        },
        success: function (data, status, xhr) {

            var resp = xhr.getResponseHeader("Response");

            if(resp == "ProductRemoved!!") {

                $("#snackbar").text("Prodotto rimosso dal carrello");
                $("#snackbar").css({
                    'background-color' : 'green'
                });
                showSnackbar();

                window.setTimeout(function(){
                    window.location.replace("http://localhost:9090/goToUserAccount");}, 1500);

            }
            else if(resp == "Product not Removed!"){

                $("#snackbar").text("Prodotto gia' rimosso dal carrello");
                $("#snackbar").css({
                    'background-color' : 'red'
                });
                showSnackbar();

            }

        },
        error: function(result) {

            $("#snackbar").text("Non hai i permessi per poter eseguire questa operazione");
            $("#snackbar").css({
                'background-color' : 'red'
            });
            showSnackbar();

        }

    });

}

//Revoke user consent
function revokeConsent() {

    $.ajax({

        url: "http://localhost:9090/revokeUserConsent",
        type: "POST",
        success: function (data, status, xhr) {

            var resp = xhr.getResponseHeader("Response");

            if(resp == "User Info removed!!") {

                $("#snackbar").text("Tutte le info dell'utente sono state eliminate");
                $("#snackbar").css({
                    'background-color' : 'green'
                });
                showSnackbar();

                window.setTimeout(function(){
                    window.location.replace("http://localhost:8081/logout");}, 1500);
            }
            else if(resp == "There are already some user info into products!"){

                $("#snackbar").text("Sono presenti prodotti nell'ordine. Non e' possibile rimuovere le user info per questi prodotti. Rimuoverli dal carrello e riprovare.");
                $("#snackbar").css({
                    'background-color' : 'red'
                });

                showSnackbar();

                window.setTimeout(function(){
                    window.location.replace("http://localhost:9090/goToUserAccount");}, 4000);

            }

        },
        error: function(result) {

            $("#snackbar").text("Non hai i permessi per poter eseguire questa operazione");
            $("#snackbar").css({
                'background-color' : 'red'
            });
            showSnackbar();

        }

    });

}

//Set product to order
function addToOrder(productID) {

    $.ajax({

        url: "http://localhost:9090/addToOrder",
        type: "POST",
        data: {
            "product_id" : productID
        },
        success: function (data, status, xhr) {

            var resp = xhr.getResponseHeader("Response");

            if(resp == "Product updated") {

                $("#snackbar").text("Prodotto inserito nell'ordine di acquisto");
                $("#snackbar").css({
                    'background-color' : 'green'
                });
                showSnackbar();

                window.setTimeout(function(){
                    window.location.replace("http://localhost:9090/goToUserAccount");}, 1500);

            }
            else if(resp == "Product not updated"){

                $("#snackbar").text("Prodotto gia' inserito nell'ordine di acquisto");
                $("#snackbar").css({
                    'background-color' : 'red'
                });
                showSnackbar();

            }

        },
        error: function(result) {

            $("#snackbar").text("Non hai i permessi per poter eseguire questa operazione");
            $("#snackbar").css({
                'background-color' : 'red'
            });
            showSnackbar();

        }

    });

}

//Remove product to order
function removeToOrder(productID) {

    $.ajax({

        url: "http://localhost:9090/removeToOrder",
        type: "POST",
        data: {
            "product_id" : productID
        },
        success: function (data, status, xhr) {

            var resp = xhr.getResponseHeader("Response");

            if(resp == "Product updated") {

                $("#snackbar").text("Prodotto rimosso dall'ordine di acquisto");
                $("#snackbar").css({
                    'background-color' : 'green'
                });
                showSnackbar();

                window.setTimeout(function(){
                    window.location.replace("http://localhost:9090/goToUserAccount");}, 1500);

            }
            else if(resp == "Product not updated"){

                $("#snackbar").text("Prodotto gia' rimosso dall'ordine di acquisto");
                $("#snackbar").css({
                    'background-color' : 'red'
                });
                showSnackbar();

            }

        },
        error: function(result) {

            $("#snackbar").text("Non hai i permessi per poter eseguire questa operazione");
            $("#snackbar").css({
                'background-color' : 'red'
            });
            showSnackbar();

        }

    });

}


//Buy function
function buyFunction() {

    $.ajax({

        url: "http://localhost:9090/buyProducts",
        type: "POST",
        success: function (data, status, xhr) {

            var resp = xhr.getResponseHeader("Response");

            if(resp == "Buy completed") {

                $("#snackbar").text("Ordine completato con successo");
                $("#snackbar").css({
                    'background-color' : 'green'
                });
                showSnackbar();

                window.setTimeout(function(){
                    window.location.replace("http://localhost:9090/goToUserAccount");}, 1500);

            }
            else if(resp == "Buy not completed"){

                $("#snackbar").text("Ordine non completato");
                $("#snackbar").css({
                    'background-color' : 'red'
                });
                showSnackbar();

            }

        },
        error: function(result) {

            $("#snackbar").text("Non hai i permessi per poter eseguire questa operazione");
            $("#snackbar").css({
                'background-color' : 'red'
            });
            showSnackbar();

        }

    });

}
