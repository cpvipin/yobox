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
                    <h2 class="text-center">Sign Up</h2>
                    <div class="login_signup text-center">

                       <form action="doSignup.htm" method="post" name="signupFrm">
                          <div class="form-group"> 
                         <select name="age" id="age">
                            <option value="" selected>Choose your age</option>
                            <option value="12">Less than 12</option>
                            <option value="12">13</option>
                            <option value="12">14</option>
                            <option value="12">15</option>
                            <option value="12">16</option>
                            <option value="12">17</option>
                            <option value="12">18</option>
                            <option value="12">19</option>
                            <option value="12">20</option>
                            <option value="12">21</option>
                            <option value="12">22</option>
                            <option value="12">23</option>
                            <option value="12">24</option>
                            <option value="12">25</option>
                            <option value="12">26</option>
                            <option value="12">27</option>
                            <option value="12">28</option>
                            <option value="12">29</option>
                            <option value="12">30</option>
                            <option value="12">31</option>
                            <option value="12">32</option>
                            <option value="12">33</option>
                            <option value="12">34</option>
                            <option value="12">35</option>
                            <option value="12">36</option>
                            <option value="12">37</option>
                            <option value="12">38</option>
                            <option value="12">39</option>
                            <option value="12">40</option>
                            <option value="12">41</option>
                            <option value="12">42</option>
                            <option value="12">43</option>
                            <option value="12">44</option>
                            <option value="12">45</option>
                            <option value="12">46</option>
                            <option value="12">47</option>
                            <option value="12">48</option>
                            <option value="12">49</option>
                            <option value="12">50</option>
                            <option value="12">51</option>
                            <option value="12">52</option>
                            <option value="12">53</option>
                            <option value="12">54</option>
                            <option value="12">55</option>
                            <option value="12">56</option>
                            <option value="12">57</option>
                            <option value="12">58</option>
                            <option value="12">59</option>
                            <option value="12">60</option>
                            <option value="12">More than 60</option>
                            </select>
                          </div>
                          <div class="form-group">
                            <input type="text" class="form-control" id="userId" name="userId" placeholder="Email or Phone">
                          </div>
                          <div class="form-group">
                            <input type="password" class="form-control" id="password" name="password" placeholder="Password">
                          </div>
                          <button type="submit" class="btn btn-default mtbl">Sign Up</button>
                        </form>

                        <hr>
                        <p>Already have an account? <a href="login.htm" class="pink">Login</a></p>


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