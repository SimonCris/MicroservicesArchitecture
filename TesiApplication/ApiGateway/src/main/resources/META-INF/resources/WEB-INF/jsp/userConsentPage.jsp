<!DOCTYPE html>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<html lang="en">

<head>
    <meta charset="UTF-8">
    <title>Consent and Permission Page</title>

    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
    <script src="https://code.jquery.com/jquery-2.2.4.min.js"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>

    <!-- icons -->
    <link href="https://fonts.googleapis.com/icon?family=Material+Icons" rel="stylesheet">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">

    <script src="../js/userConsentPageJs.js" defer></script>

</head>
<body>

<div class="container">

    <!-- Modal -->
    <div class="modal fade" id="myModal" role="dialog">
        <div class="modal-dialog modal-lg">
            <div class="modal-content">
                <div class="modal-header">

                    <div class="container-fluid">
                        <div class="row" style="text-align: center">
                            <div class="col-sm-2">
                                <img class="img-responsive" src="../images/logo.jpg" alt="logo">
                            </div>
                            <div class="col-sm-8">
                                <h2 class="modal-title">Consent and Permission Form</h2>
                            </div>
                            <div class="col-sm-2">
                                <button type="button" class="close" data-dismiss="modal">&times;</button>
                            </div>
                        </div>
                    </div>

                </div>
                <div class="modal-body">

                        <div class="container-fluid">

                            <div class="container-fluid" style="text-align: center">

                                <h3>Hi,</h3>

                                <div>
                                    <h4><p><strong>This application would like to:</strong></p></h4>
                                </div>

                                <hr class="hrPage">

                                <div class="row">
                                    <div class="col-sm-2">
                                        <i class="material-icons" style="font-size:36px;color:red">contact_mail</i>
                                    </div>
                                    <div class="col-sm-8">Access and manage the email address</div>
                                    <div class="col-sm-2">
                                        <button type="button" class="btn btn-info btn-sm" data-toggle="tooltip" data-placement="bottom"
                                                title="Info sul trattamento dell'indirizzo email">
                                            <span class="glyphicon glyphicon-question-sign"></span>
                                        </button>
                                    </div>
                                </div>

                                <hr class="hrPage">

                                <div class="row">
                                    <div class="col-sm-2">
                                        <i class="fa fa-user-circle" style="font-size:36px;color:red"></i>
                                    </div>
                                    <div class="col-sm-8">Access and manage name and surname</div>
                                    <div class="col-sm-2">
                                        <button type="button" class="btn btn-info btn-sm" data-toggle="tooltip" data-placement="bottom"
                                                title="Info sul trattamento di nome e cognome">
                                            <span class="glyphicon glyphicon-question-sign"></span>
                                        </button>
                                    </div>
                                </div>

                                <hr class="hrPage">

                                <div class="row">
                                    <div class="col-sm-2">
                                        <i class="material-icons" style="font-size:36px;color:red">verified_user</i>
                                    </div>
                                    <div class="col-sm-8">Access and manage google user Id</div>
                                    <div class="col-sm-2">
                                        <button type="button" class="btn btn-info btn-sm" data-toggle="tooltip" data-placement="bottom"
                                                title="Info sul trattamento dell'id user">
                                            <span class="glyphicon glyphicon-question-sign"></span>
                                        </button>
                                    </div>
                                </div>

                                <!-- <hr class="hrPage"> -->

                            </div>

                            <br>
                            <div class="container-fluid" style="height: 1px; background-color: red">
                            </div>

                            <div class="container-fluid">

                                <br>
                                <div>
                                    <h4><p><strong>I declare to consent:</strong></p></h4>
                                </div>

                                <form action="/getUserConsent" method="get">
                                    <fieldset class="form-group">
                                        <hr class="hrPage">
                                        <div class="row">
                                            <legend class="col-form-label col-sm-8" style="font-size: 18px;">
                                                Access and use of the e-mail address</legend>
                                            <div class="col-sm-4">
                                                <div class="form-check">
                                                    <input class="form-check-input" type="radio" name="gridRadiosEmail" id="yesConsentEmail" value="yesConsentEmail" checked>
                                                    <label class="form-check-label" for="yesConsentEmail">
                                                        Yes
                                                    </label>
                                                </div>
                                            </div>
                                        </div>
                                        <hr class="hrPage">
                                        <div class="row">
                                            <legend class="col-form-label col-sm-8" style="font-size: 18px;">
                                                Access and use of the name and surname</legend>
                                            <div class="col-sm-4">
                                                <div class="form-check">
                                                    <input class="form-check-input" type="radio" name="gridRadiosNameAndSurname" id="yesConsentNameAndSurname" value="yesConsentNameAndSurname" checked>
                                                    <label class="form-check-label" for="yesConsentNameAndSurname">
                                                        Yes
                                                    </label>
                                                </div>
                                            </div>
                                        </div>
                                        <hr class="hrPage">
                                        <div class="row">
                                            <legend class="col-form-label col-sm-8" style="font-size: 18px;">
                                                Access and use of the user id</legend>
                                            <div class="col-sm-4">
                                                <div class="form-check">
                                                    <input class="form-check-input" type="radio" name="gridRadiosUserId" id="yesConsentUserId" value="yesConsentUserId" checked>
                                                    <label class="form-check-label" for="yesConsentUserId">
                                                        Yes
                                                    </label>
                                                </div>
                                            </div>
                                        </div>

                                        <div class="container-fluid" style="height: 1px; background-color: red">
                                        </div>

                                        <div>
                                            <h4><p><strong>Privacy Policy  & GDPR</strong></p></h4>
                                            <div class="row">
                                                <legend class="col-form-label col-sm-8" style="font-size: 18px;">
                                                    I declare that I have read the privacy policy available at the following link
                                                <a href="/getPolicyPage">Privacy Policy & GDPR</a>.</legend>
                                                <div class="col-sm-4">
                                                    <div class="form-check">
                                                        <input class="form-check-input" type="radio" name="gridRadiosPrivacy" id="yesConsentPrivacy" value="yesConsentPrivacy" checked>
                                                        <label class="form-check-label" for="yesConsentPrivacy">
                                                            Yes
                                                        </label>
                                                    </div>
                                                </div>
                                            </div>
                                        </div>
                                    </fieldset>

                                    <div class="form-group row">
                                        <div class="col-sm-10">
                                            <button type="submit" class="btn btn-primary">Consent</button>
                                        </div>
                                    </div>

                                </form>

                            </div>
                        </div>

                </div>
                <div class="modal-footer">

                    <div class="container-fluid">



                    </div>

                    <button type="button" class="btn btn-danger" data-dismiss="modal"
                    onclick="negateConsent()">Negate</button>
                </div>

            </div>
        </div>
    </div>
</div>


</body>
</html>