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

/* ADD PRODUCT TO FAVORITE CART */
function addToCart(name, brand, image, price) {

    $.ajax({

        url: "http://localhost:9090/addToCart",
        type: "GET",
        data:{
            "prodName" : name,
            "prodBrand" : brand,
            "prodImage" : image,
            "prodPrice" : price,
        },
        success: function (data, status, xhr) {

            var resp = xhr.getResponseHeader("Response");

            if(resp == "ProductSaved!!") {

                $("#snackbar").text("Prodotto aggiunto al carrello");
                $("#snackbar").css({
                    'background-color' : 'green'
                });
                showSnackbar();

            }
            else if(resp == "Product not Saved!"){

                $("#snackbar").text("Prodotto gia' presente nel carrello");
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