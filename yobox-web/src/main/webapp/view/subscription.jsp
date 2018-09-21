  <%@ include file="includes/header.jsp"%>
 
 
 <form action="deliveryAddress.htm" name="subsFrm" method="post" id="subscriptionFrm">
     <div class="container-fluid">
            <div class="container">
                <div class="content-main">
                    <div class="intro text-center">
                        <h1>Choose subscription</h1>
                        <div class="row mtbxl subsriptionDiv">
                        
                        <c:forEach var="listVar" items="${subscriptionColl}" varStatus="counter"> 
                        
                            <div class="col-sm-4">
                                <input type="radio" value="${listVar.id}" name="subscriptionCycle" <c:if test="${counter.count==2}">checked="checked"</c:if> id="${counter.count}_m" class="sub_radio">
                                <label for="${counter.count}_m">
                                    <div class="whiteBox">
                                        <header>
                                            <h1>${listVar.durationMonths}</h1>
                                            <h3><small>${listVar.displayText}</small></h3>
                                        </header>
                                        <div class="whiteBoxContent">
                                            <p>${listVar.description}</p>
                                        </div>
                                        <footer>
                                            <span class="label label-default">Rs. ${listVar.price}</span>
                                        </footer>
                                    </div>
                                </label>
                            </div>
                            
                          </c:forEach>  
                            
                           
                        </div>
                        <div class="intro-btn">
                            <a href="#" id="subsFrmBut" class="btn btn-primary">Continue to Payment</a>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        
        
        </form>
  <script>
  $(document).ready(function(){
	
	  $("#subsFrmBut").click(function(e){ e.preventDefault();  $("#subscriptionFrm").submit();  });
	  
  })
		  ;
  
  </script>
   <%@ include file="includes/footer.jsp"%>
      