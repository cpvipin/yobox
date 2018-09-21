  <%@ include file="includes/header.jsp"%>
 
           <div class="container-fluid">
            <div class="container">
                <div class="content-main">
                    <div class="content_dashboard text-center">
                        <div class="row mtb">


                            <!-- Nav tabs -->
                              <ul class="nav nav-tabs" role="tablist">
                                <li role="presentation"  class="active"><a href="#my_pro" aria-controls="my_pro" role="tab" data-toggle="tab"><span class="glyphicon glyphicon-user"></span> <span class="hidden-xs">My Profile</span></a></li>
                                <li role="presentation"><a href="#my_sub" aria-controls="my_sub" role="tab" data-toggle="tab"><span class="glyphicon glyphicon-shopping-cart"></span> <span class="hidden-xs">My Subscriptions</span></a></li>
                                <li role="presentation"><a href="#my_pref" aria-controls="my_pref" role="tab" data-toggle="tab"><span class="glyphicon glyphicon-cog"></span> <span class="hidden-xs">My preference</span></a></li>
                                <li role="presentation"><a href="#chg_pass" aria-controls="chg_pass" role="tab" data-toggle="tab"><span class="glyphicon glyphicon-lock"></span> <span class="hidden-xs">Change Password</span></a></li>
                              </ul>

                              <!-- Tab panes -->	
                              <div class="tab-content">
                              <div role="tabpanel" class="tab-pane active" id="my_pro">
                                    <div class="row">
                                        <form action="updateCustomerProfile.htm" name="profileFrm" method="post">
                                        <input type="hidden" name="id" value="${customer.id}" />
                                            <div class="form-inner">
                                                
                                                <div class="col-md-6">
                                                    <div class="form-groups">
                                                      <div class="form-group">
                                                        <input type="text" class="form-control" name="fullName" value="${customer.fullName}" placeholder="Your full name" />
                                                      </div>
                                                      <div class="form-group">
                                                        <input type="email" class="form-control" name="email"  value="${customer.email}" placeholder="Email id" />
                                                      </div>
                                                      <div class="form-group">
                                                        <input type="text" class="form-control" name="phone" placeholder="Mobile" value="${customer.phone}">
                                                      </div>
                                                      <button type="submit" class="btn btn-default mtbl">Update Profile</button>
                                                    </div>
                                                </div>
                                                <div class="col-md-6">
                                                <!--     <div class="edit-profile-pic">
                                                        <img src="img/profilepic.jpg" alt="">
                                                        <label><input type="file" name="" class="edit-profile">Edit</label>
                                                    </div> -->
                                                </div>
                                            </div>
                                        </form>
                                    </div>
                                </div>
                                <div role="tabpanel" class="tab-pane" id="my_sub">
                                    <div class="table-responsive">
                                        <table class="table table-striped text-left">
                                            <thead>
                                                <tr>
                                                    <th>Subscription Box</th>
                                                    <th>Order Status</th>
                                                    <th>Order Date</th>
                                                    <th>Total Amount</th>
                                                </tr>
                                            </thead>
                                            <tbody>
          <c:forEach var="orderObj" items="${customerSubsColl}">                                  
                                            
                                                <tr>
                                                    <td>${orderObj.subscriptionName}</td>
                                                    <td>${orderObj.orderStatus}</td>
                                                    <td>${viewTools.formatDate(orderObj.dateAdded)}</td>
                                                    <td>Rs. ${orderObj.customerSubscription.totalAmount}</td>
                                                    
                                                 </tr>
        </c:forEach>                                        
                                            </tbody>
                                        </table>
                                    </div>
                                </div>
                                
                                <div role="tabpanel" class="tab-pane" id="my_pref">
                                    <div class="table-responsive">
                                        <table class="table table-striped text-left">
                                            <thead>
                                                <tr>
                                                    <th>Preference Name</th>
                                                    <th>Chosen Value</th>
                                                </tr>
                                            </thead>
                                            <tbody>
                                             <c:forEach var="prefObj" items="${beautyPreference}">      
                       
                       <tr>
                                                    <td>${prefObj.key}</td>
                                                    <td>${prefObj.value}</td>
                                                </tr>
                       
                       </c:forEach>
                                         </tbody>
                                        </table>
                                    </div>
                                    <a href="quiz.htm?edit=true" class="btn btn-default">Update preference</a>
                                </div>
                                <div role="tabpanel" class="tab-pane" id="chg_pass">
                                        <form action="changeOldPassword.htm" method="post">
                                            <div class="form-inner">
                                                
                                                <div class="col-md-8 col-md-offset-2">
                                                    <div class="form-groups">
                                                      <div class="form-group">
                                                        <input type="password" class="form-control" id="oldPassword" name="oldPassword" placeholder="Old Password" />
                                                      </div>
                                                      <div class="form-group">
                                                        <input type="password" class="form-control" id="newPassword" name="newPassword" placeholder="New Password" />
                                                      </div>
                                                      <div class="form-group">
                                                        <input type="password" class="form-control" id="confirmPassword" name="confirmPassword" placeholder="Confirm Password" />
                                                      </div>
                                                      <button type="submit" class="btn btn-default mtbl">Update</button>
                                                    </div>
                                                </div>
                                            </div>
                                        </form>
                                </div>
                              </div>



                        </div>
                    </div>
                </div>
            </div>
        </div>
        
        
        
      
      
   <%@ include file="includes/footer.jsp"%>
      