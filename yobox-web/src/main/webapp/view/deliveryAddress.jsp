  <%@ include file="includes/header.jsp"%>
 
          <div class="container-fluid">
            <div class="container">
                <div class="content-main">
                    <div class="mtxl text-center">
                        <h1>You are almost there !</h1>
                        <div class="formDivOuter">
                            <h3 class="mtxl">Add delivery address</h3>
                            <div class="row mtbxl formDiv">
                                <div class="col-md-8 col-md-offset-2">
    <form action="confirmDeliveryAddress.htm" name="delAddrFrm" id="delAddrFrm" method="post" >
     <input type="hidden" name="subscriptionCycle" value="${subscriptionCycle}"  />
                                        <div class="form-group">
                                            <input type="text" class="form-control" value="${customerAddress.fullName}"  placeholder="Full Name" name="fullName" id="fullName">
                                        </div>
                                        <div class="form-group">
                                            <input type="text" class="form-control" value="${customerAddress.mobile}"  placeholder="Mobile" name="mobile" id="mobile" >
                                        </div>
                                        
                                        <div class="form-group">
                                            <input type="text" class="form-control" name="email" placeholder="email" value="${customerAddress.email}">
                                        </div>
                                        <div class="form-group">
                                            <input type="text" class="form-control" name="address" placeholder="Address" value="${customerAddress.address}">
                                        </div>
                                        <div class="form-group">
                                            <input type="text" class="form-control" value="${customerAddress.locality}" placeholder="Locality" name="locality" id="locality" >
                                        </div>
                                        <div class="form-group select_dd">
                                            <select class="form-control" name="state.id">
                                            <option value="">-select state -</option>
                                                <c:forEach var="stateObj" items="${stateColl}" varStatus="counter">
												<option <c:if test="${customerAddress.state.id==stateObj.id}">selected</c:if> value="${stateObj.id}">${stateObj.name}</option>
												</c:forEach>
                                            </select>
                                        </div>
                                        <div class="form-group">
                                            <input type="text" class="form-control"  value="${customerAddress.pinCode}" placeholder="pincode" name="pinCode" id="pinCode">
                                        </div>
                                    </form>
                                </div>
                            </div>
                        </div>
                        <div class="intro-btn">
                            <a href="#" id="delAddrBut" class="btn btn-primary">Proceed to Payment</a>
                        </div>
                    </div>
                </div>
            </div>
        </div>
 <script>
  $(document).ready(function(){
	
	  $("#delAddrBut").click(function(e){ e.preventDefault();  $("#delAddrFrm").submit();  });
	  
  })
		  ;
  
  </script>
      
      
   <%@ include file="includes/footer.jsp"%>
      