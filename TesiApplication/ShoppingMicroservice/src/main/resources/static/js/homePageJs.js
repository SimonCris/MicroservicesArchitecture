/* List or grid view */
$('#list').click(function(event){

    event.preventDefault();
    $('#products .item').addClass('list-group-item');
});

$('#grid').click(function(event){
    event.preventDefault();
    $('#products .item').removeClass('list-group-item');
    $('#products .item').addClass('grid-group-item');
});

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

/* ADD PRODUCT TO FAVORITE FUNCTION */
function addToFavorite(name, brand, image, price) {

    $.ajax({

        url: "http://localhost:9090/addToFavorite",
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

                $("#snackbar").text("Prodotto salvato nei preferiti");
                $("#snackbar").css({
                    'background-color' : 'green'
                });
                showSnackbar();

            }
            else if(resp == "Product not Saved!"){

                $("#snackbar").text("Prodotto gia' presente nei preferiti");
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
