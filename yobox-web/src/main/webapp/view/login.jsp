<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<!doctype html>
<html class="no-js" lang="">
    <head>
        <meta charset="utf-8">
        <meta http-equiv="x-ua-compatible" content="ie=edge">
        <title>Yobox</title>
        <meta name="description" content="">
        <meta name="viewport" content="width=device-width, initial-scale=1">

        <link rel="apple-touch-icon" href="apple-touch-icon.png">
        <!-- Place favicon.ico in the root directory -->
        <link href="https://fonts.googleapis.com/css?family=Montserrat:400,500,600" rel="stylesheet">
        <link rel="stylesheet" href="css/normalize.css">
        <link rel="stylesheet" href="css/bootstrap.min.css">
        <link rel="stylesheet" href="css/main.css">
        <script src="js/vendor/modernizr-2.8.3.min.js"></script>
    </head>
    <body class="login_signup_page">
        <!--[if lt IE 8]>
            <p class="browserupgrade">You are using an <strong>outdated</strong> browser. Please <a href="http://browsehappy.com/">upgrade your browser</a> to improve your experience.</p>
        <![endif]-->
<div class="alertBox">
        <div class="alert alert-success <c:if test="${ empty info_message}"> hidden </c:if>">
	     <a href="#" class="close" data-dismiss="alert" aria-label="close">&times;</a>
        ${info_message}
        </div>
        <div class="alert alert-danger <c:if test="${ empty error_message}"> hidden </c:if>">
      <a href="#" class="close" data-dismiss="alert" aria-label="close">&times;</a>
      ${error_message}
       </div>
        </div>
    
        <div class="container-fluid no-padding">
            <div class="container">
                <header class="header-main">
                    <nav class="navbar">
                      <div class="container-fluid">
                        <div class="row mts">
                            <div class="col-xs-12 text-center">
                                <a class="navbar-brand" href="index.htm"><img src="img/yobox.png" alt="" width="97"></a>
                            </div>
                            
                        </div>
                      </div><!-- /.container-fluid -->
                    </nav>
                </header>
            </div>
        </div><!-- Main header -->
        <div class="container-fluid cmain-wrapper">
            <div class="container">
                <div class="content-main">
                    <h2 class="text-center">Login</h2>
                    <div class="login_signup text-center">

                         <form action="dologin.htm" method="post" name="loginFrm">
                          <div class="form-group">
                            <input type="text" class="form-control" id="userId" name="userId" placeholder="Email or Phone">
                          </div>
                          <div class="form-group">
                            <input type="password" class="form-control" name="password" id="password" placeholder="Password">
                            <a href="forgotPassword.htm" class="small pull-right pink mtxs">Forgot password?</a>
                          </div>
                          <button type="submit" class="btn btn-default mtbl">Login</button>
                        </form>
                        <hr>
                        <p>Don't have an account?<a href="quiz.htm" class="pink">Create Profile</a></p>
                        
                        <hr>
                        <p><a href="index.htm" class="pink">Go Home</a></p>
                        
                    </div>
                </div>
            </div>
        </div>




        <script src="js/vendor/jquery-1.12.0.min.js"></script>
        <script src="js/bootstrap.min.js"></script>
        <script src="js/plugins.js"></script>
        <script src="js/main.js"></script>

        
    </body>
</html>