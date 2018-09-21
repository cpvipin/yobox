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
<c:set var="req" value="${pageContext.request}" />
<c:set var="url">${req.requestURL}</c:set>
<c:set var="base" value="${fn:substring(url, 0, fn:length(url) - fn:length(req.requestURI))}${req.contextPath}" />
        <link rel="apple-touch-icon" href="apple-touch-icon.png">
        <!-- Place favicon.ico in the root directory -->
        <link href="https://fonts.googleapis.com/css?family=Montserrat:400,500,600" rel="stylesheet">
        <link rel="stylesheet" href="${base}/css/normalize.css">
        <link rel="stylesheet" href="${base}/css/bootstrap.min.css">
        <link rel="stylesheet" href="${base}/css/main.css">
        <script src="${base}/js/vendor/jquery-1.12.0.min.js"></script>
        <script src="${base}/js/vendor/modernizr-2.8.3.min.js"></script>
    </head>
    <!--Start of Tawk.to Script-->
<script type="text/javascript">
var Tawk_API=Tawk_API||{}, Tawk_LoadStart=new Date();
(function(){
var s1=document.createElement("script"),s0=document.getElementsByTagName("script")[0];
s1.async=true;
s1.src='https://embed.tawk.to/594f63ffe9c6d324a473731f/default';
s1.charset='UTF-8';
s1.setAttribute('crossorigin','*');
s0.parentNode.insertBefore(s1,s0);
})();
</script>
<!--End of Tawk.to Script-->
    <body class="questions-page">
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
                            <div class="col-xs-3">
                                <a class="navbar-brand" href="index.htm"><img src="img/yobox.png" alt="" width="97"></a>
                            </div>
                            <div class="col-xs-9">
                                <ul class="nav navbar-nav navbar-right">
                                    <c:choose>
                                    <c:when test="${viewTools.isCustomerLoggedin()==true}">
                                    <li class="dropdown">
                                      <a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button" aria-haspopup="true" aria-expanded="false">${viewTools.getLoggedInUserName()}<span class="caret"></span></a>
                                      <ul class="dropdown-menu">
                                        <li><a href="index.htm">Go Home</a></li>
                                        <li role="separator" class="divider"></li>
                                        <li><a href="dashboard.htm">Dashboard</a></li>
                                        <li role="separator" class="divider"></li>
                                        <li><a href="logout.htm">Logout</a></li>
                                      </ul>
                                    </li>
                                    </c:when>
<c:otherwise>
<li><a href="login.htm" class="btn btn-default btn-login">Login</a></li>
</c:otherwise>
                                    
                                    </c:choose>
                                    
                                </ul>
                            </div>
                        </div>
                      </div><!-- /.container-fluid -->
                    </nav>
                </header>
            </div>
        </div><!-- Main header -->