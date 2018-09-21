  <%@ include file="includes/header.jsp"%>
  <script>
  	$("body").removeClass("questions-page");
  	</script>
  
  <div class="container-fluid cmain-wrapper">
            <div class="container">
                <div class="content-main">
                    <div class="intro text-center">
                        <h1 class="intro_heading">
                        <span>#getyobox</span><br/>
                        Not every make up suits for you. Get a customized makeup box every month chosen by our stylist exclusively for you
                        <small>Want to know more ? chat with us !</small>
                        </h1>
                        <ul class="mtbl">
                            
                            <li>Just Rs. 500/ month</li>
                            <li>Free shipping</li>
                        </ul>
                        <div class="intro-btn">
                            <a href="quiz.htm" class="btn btn-primary">Get YOBOX for ${viewTools.getSubscriptionMonth()}</a>
                        </div>
                    </div>
                </div>
            </div>
        </div>
  <%@ include file="includes/footer.jsp"%>
      