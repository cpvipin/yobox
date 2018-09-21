   <%@ include file="includes/header.jsp"%>
 <div class="container-fluid cmain-wrapper">
            <div class="container">
                <div class="content-main">
                    <div class="questions text-center">
                        <div class="questionsList">
                            <form action="addBeautyPreference.htm" method="post" name="beautyQuiz" id="beautyQuiz">

                                <div id="carousel-example-generic" class="carousel carousel-fade" data-ride="carousel" data-interval="false">
                                  <!-- Indicators -->
                                  <ol class="carousel-indicators">
                                    <li data-target="#carousel-example-generic" data-slide-to="0" class="active"></li>
                                    <li data-target="#carousel-example-generic" data-slide-to="1"></li>
                                    <li data-target="#carousel-example-generic" data-slide-to="2"></li>
                                    <li data-target="#carousel-example-generic" data-slide-to="3"></li>
                                  </ol>

                                  <!-- Wrapper for slides -->
                                  <div class="carousel-inner" role="listbox">
                                    <div class="item active">
                                        <div class="question">
                                            <h1 class="intro_heading">What is your skin colour?</h1>
                                            <ul class="optionsList">
                                                <li>                                          
                                                    <input type="radio" value="fair" data-color="#000" class="checkbox q_input data-color" id="skin_ton_1" name="skinColour">
                                                    <label for="skin_ton_1" class="q_label"><span>Fair</span></label>
                                                </li>
                                                <li>                                          
                                                    <input type="radio" value="light" data-color="#fff" class="checkbox q_input data-color" id="skin_ton_2" name="skinColour">
                                                    <label for="skin_ton_2" class="q_label"><span>Light</span></label>
                                                </li>
                                                <li>                                          
                                                    <input type="radio" value="light medium" data-color="#deab7d" class="checkbox q_input data-color" id="skin_ton_3" name="skinColour">
                                                    <label for="skin_ton_3" class="q_label"><span>Light Medium</span></label>
                                                </li>
                                                <li>                                          
                                                    <input type="radio" value="medium" data-color="#b67245" class="checkbox q_input data-color" id="skin_ton_4" name="skinColour">
                                                    <label for="skin_ton_4" class="q_label"><span>Medium</span></label>
                                                </li>
                                                <li>                                          
                                                    <input type="radio" value="medium dark" data-color="#64321e" class="checkbox q_input data-color" id="skin_ton_5" name="skinColour">
                                                    <label for="skin_ton_5" class="q_label"><span>Medium Dark</span></label>
                                                </li>
                                                <li>                                          
                                                    <input type="radio" value="dark" data-color="#452c28" class="checkbox q_input data-color" id="skin_ton_6" name="skinColour">
                                                    <label for="skin_ton_6" class="q_label"><span>Dark</span></label>
                                                </li>
                                            </ul>
                                        </div>
                                    </div>
                                    <div class="item">
                                      <div class="question">
                                            <h1 class="intro_heading">What color is your hair?</h1>
                                            <ul class="optionsList">
                                                <li>                                          
                                                    <input type="radio" value="brown" data-color="#5a2209" class="checkbox q_input data-color" id="hair_color_1" name="hairColour">
                                                    <label for="hair_color_1" class="q_label"><span>BROWN</span></label>
                                                </li>
                                                <li>                                          
                                                    <input type="radio" value="black" data-color="#a77144" class="checkbox q_input data-color" id="hair_color_2" name="hairColour">
                                                    <label for="hair_color_2" class="q_label"><span>BLACK</span></label>
                                                </li>
                                                <li>                                          
                                                    <input type="radio" value="white" data-color="#5e7393" class="checkbox q_input data-color" id="hair_color_3" name="hairColour">
                                                    <label for="hair_color_3" class="q_label"><span>WHITE</span></label>
                                                </li>
                                                <li>                                          
                                                    <input type="radio" value="red" data-color="#994812" class="checkbox q_input data-color" id="hair_color_4" name="hairColour">
                                                    <label for="hair_color_4" class="q_label"><span>RED</span></label>
                                                </li>
                                                <li>                                          
                                                    <input type="radio" value="blonde" data-color="#818088" class="checkbox q_input data-color" id="hair_color_5" name="hairColour">
                                                    <label for="hair_color_5" class="q_label"><span>BLONDE</span></label>
                                                </li>
                                                <li>                                          
                                                    <input type="radio" value="other" data-color="#c8b22d" class="checkbox q_input data-color" id="hair_color_6" name="hairColour">
                                                    <label for="hair_color_6" class="q_label"><span>OTHER</span></label>
                                                </li>
                                            </ul>
                                        </div>
                                    </div>
                                    
                                    
                                    
                                    
                                    
                                    <div class="item">
                                      <div class="question">
                                            <h1 class="intro_heading">How is your hair?</h1>
                                            <ul class="optionsList">
                                                <li>                                          
                                                    <input type="checkbox" value="wavy"  class="checkbox q_input data-img" id="hair_type_1" name="hairType">
                                                    <label for="hair_type_1"  style="background: url(img/images/wavy.png) 0 0/contain no-repeat;" class="q_label"><span>WAVY</span></label>
                                                </li>
                                                <li>                                          
                                                    <input type="checkbox" value="curly" class="checkbox q_input data-img" id="hair_type_2" name="hairType">
                                                    <label for="hair_type_2"  style="background: url(img/images/curly.png) 0 0/contain no-repeat;" class="q_label"><span>CURLY</span></label>
                                                </li>
                                                <li>                                          
                                                    <input type="checkbox" value="straight" class="checkbox q_input data-img" id="hair_type_3" name="hairType">
                                                    <label for="hair_type_3"  style="background: url(img/images/straight.png) 0 0/contain no-repeat;" class="q_label"><span>STRAIGHT</span></label>
                                                </li>
                                                
                                                <li>                                          
                                                    <input type="checkbox" value="dry" class="checkbox q_input data-img" id="hair_type_5" name="hairType">
                                                    <label for="hair_type_5"  style="background: url(img/images/dry.png) 0 0/contain no-repeat;" class="q_label"><span>DRY</span></label>
                                                </li>
                                                <li>                                          
                                                    <input type="checkbox" value="oily" class="checkbox q_input data-img" id="hair_type_6" name="hairType">
                                                    <label for="hair_type_6"  style="background: url(img/images/oily.png) 0 0/contain no-repeat;" class="q_label"><span>OILY</span></label>
                                                </li>
                                               <li>                                          
                                                    <input type="checkbox" value="tooshort" class="checkbox q_input data-img" id="hair_type_7" name="hairType">
                                                    <label for="hair_type_7"  style="background: url(img/images/short.png) 0 0/contain no-repeat;" class="q_label"><span>TOO SHORT</span></label>
                                                </li>
                                                
                                                </ul>
                                         
                                        </div>
                                    </div>
                                    
                                    
                                    
 <div class="item">
                                      <div class="question">
                                            <h1 class="intro_heading">Your facial skin concerns?</h1>
                                            <ul class="optionsList">
                                                <li>                                          
                                                    <input type="checkbox" value="acne"  class="checkbox q_input data-img" id="skin_concern_1" name="skinConcern">
                                                    <label for="skin_concern_1"  style="background: url(img/images/acne.png) 0 0/contain no-repeat;"  class="q_label"><span>Acne</span></label>
                                                </li>
                                              	
                                                <li>                                          
                                                    <input type="checkbox" value="undereyecircle" class="checkbox q_input" id="skin_concern_3" name="skinConcern">
                                                    <label for="skin_concern_3"  style="background: url(img/images/undereyecircle.png) 0 0/contain no-repeat;"  class="q_label"><span>UnderEye Circles</span></label>
                                                </li>
                                                <li>                                          
                                                    <input type="checkbox" value="stretch Mark"  class="checkbox q_input" id="skin_concern_4" name="skinConcern">
                                                    <label for="skin_concern_4"  style="background: url(img/images/stretchmarks.png) 0 0/contain no-repeat;"  class="q_label"><span>Stretch Mark</span></label>
                                                </li>
                                               <li>                                          
                                                    <input type="checkbox" value="sun" class="checkbox q_input" id="skin_concern_6" name="skinConcern">
                                                    <label for="skin_concern_6"   style="background: url(img/images/sunscreen.png) 0 0/contain no-repeat;"  class="q_label"><span>Sun Protection</span></label>
                                                </li>
                                                
                                                
                                              
                                            </ul>
                                         
                                        </div>
                                    </div>
                                    
                                    

                                  </div><!-- /.carousel-inner -->

                                 
                                    <div class="question-btn">
                                        <a class="btn btn-default btn-grey btn-prev" href="#carousel-example-generic" role="button" data-slide="prev">Back</a>
                                        <a class="btn btn-primary btn-next" href="#carousel-example-generic" role="button" data-slide="next">Next</a>
                                       <input type="submit" class="btn btn-primary" id="submitBut" style="display:none" value="NEXT" />
                                    </div>
                                </div>

                                
                            </form>
                        </div>
                        
                        
                        
                    </div>
                </div>
            </div>
        </div>
          <%@ include file="includes/footer.jsp"%>
        