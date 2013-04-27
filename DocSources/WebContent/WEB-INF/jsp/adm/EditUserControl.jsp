<%@ taglib prefix="biasecurity" uri="http://bia.medici.org/jsp:jstl_security" %>  
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>

<security:authorize ifAnyGranted="ROLE_ADMINISTRATORS">
	<c:url var="ShowUserURL" value="/admin/ShowUser.do">
		<c:param name="account" value="${command.account}" />
	</c:url>
	
	<c:url var="EditUserURL" value="/admin/EditUserControl.do" />
	
<%-- 	<c:url var="FindUserURL" value="/admin/FindUser.json" /> --%>

	<form:form id="EditUserControlForm" method="post" class="edit" action="${EditUserURL}">
	<fieldset>
		<legend><b>USER CONTROL</b></legend>
		<div class="listForm">
        	<div class="row">
            	<div class="col_l"><form:label for="account" path="account">Username</form:label></div>
            	<div class="col_l"><form:input path="account" cssClass="input_14c" /></div>
        	</div>
        <div class="row">
            <div class="col_l"><form:label for="firstName" path="firstName">First Name</form:label></div>
            <div class="col_l"><form:input path="firstName" class="input_14c"/></div>
            <div class="col_r"><form:label for="middleName" path="middleName">Middle Name</form:label></div>
            <div class="col_r"><form:input path="middleName" class="input_14c" /></div>
        </div>
        <div class="row">
            <div class="col_l"><form:label for="lastName" id="lastNameLabel" path="lastName">Last Name</form:label></div>
            <div class="col_l"><form:input path="lastName" class="input_14c" /></div>
        </div>
    </div>
      
    <hr />
    
    <div class="listForm">
        <div class="row">
            <div class="col_l"><label id="passwordLabel" for="password">New password</label></div>
            <div class="col_l"><input type="password" name="password" id="password" class="input_15c" value=""></div>
        </div>
         <div class="row">
            <div class="col_l"><label id="confirmPasswordLabel" for="confirmPassword">Confirm password</label></div>
            <div class="col_l"><input type="password" name="confirmPassword" id="confirmPassword" class="input_15c" value=""></div>
        </div>
    </div>
   	<p><b>Password Expires:</b></p>
    <div class="listForm">
		<div class="row">
            <div class="col_r"><form:label for="yearExpirationPassword" path="yearExpirationPassword" cssErrorClass="error">Year</form:label></div>
			<div class="col_l"><form:input path="yearExpirationPassword" cssClass="input_4c" maxlength="4"/></div>
			<div class="col_r"><form:label for="monthExpirationPassword" path="monthExpirationPassword" cssErrorClass="error">Month</form:label></div>
			<div class="col_l"><form:select path="monthExpirationPassword" cssClass="selectform_long" items="${months}" itemValue="monthNum" itemLabel="monthName"/></div>
			<div class="col_r"><form:label  for="dayExpirationPassword" path="dayExpirationPassword" cssErrorClass="error">Day</form:label></div>
			<div class="col_r"><form:input path="dayExpirationPassword" class="input_2c" maxlength="2"/></div>
        </div>   
     </div>
     <div class="listForm">
		<div class="row">      
          <div class="col_r">
            	<label for="forcePswdChange" id="forcePswdChangeLabel">Force Password Change
                <input type="checkbox" name="forcePswdChange" class="checkboxPers2"/>
            </div>
        </div>
   	</div>

    <hr />
    
    <div class="listForm">
        <div class="row">
            <div class="col_l"><label for="groupPolicies" id="groupPoliciesLabel"><b>Group policies:</b></label></div>
        </div>
        <br>
        <div class="row">
            <div class="col_l">
            	<ul>
		            <c:forEach var="currentAuthority" items="${authorities}" varStatus="iterator">	
  						<li>
  							<biasecurity:checkbox id="groupPolicies" name="userRoles" cssClass="" value="${currentAuthority.authority}" userAuthorities="${command.getUserRoles()}" />
							<label for="groupPolicies">${authorities[iterator.index].description}</label>
						</li>			
		 			</c:forEach>
	 			</ul>           
            </div>
        </div>
    </div>
   
    <hr />
    
   	<p><b>Account Expiration Time:</b></p>
	<div class="listForm">
        <div class="row">
			<div class="col_r"><form:label id="yearExpirationUserLabel" for="yearExpirationUser" path="yearExpirationUser" cssErrorClass="error">Year</form:label></div>
			<div class="col_l"><form:input id="yearExpirationUser" path="yearExpirationUser" cssClass="input_4c" value="" maxlength="4"/></div>
			<div class="col_r"><form:label id="monthExpirationUserLabel" for="monthExpirationUser" path="monthExpirationUser" cssErrorClass="error">Month</form:label></div>
			<div class="col_l"><form:select id="monthExpirationUser" path="monthExpirationUser" cssClass="selectform_long" items="${months}" itemValue="monthNum" itemLabel="monthName"/></div>
			<div class="col_r"><form:label  id="dayExpirationUserLabel" path="dayExpirationUser" for="dayExpirationUser" cssErrorClass="error">Day</form:label></div>
			<div class="col_r"><form:input id="dayExpirationUser" path="dayExpirationUser" class="input_2c" maxlength="2"/></div>
		</div>
	</div>
	<div class="listForm">
        <div class="row">
	        <ul>
				<li>
					<form:checkbox path="active" cssClass="checkboxPers1"/>	
			        <label for="active" id="activeLabel">Active Account</label>
		        </li>
      			<li>
	      			<form:checkbox path="approved" cssClass="checkboxPers1"/>
	                <label for="approved" id="approvedLabel">Approved Account</label>
				</li>
				<li>
					<form:checkbox path="locked" cssClass="checkboxPers1"/>
					<label for="locked" id="lockedLabel">Lock account</label>
				</li>
			</ul>
         </div>
    </div>
    
    <form:errors path="password" cssClass="inputerrors" htmlEscape="false"/>
    <form:errors path="confirmPassword" cssClass="inputerrors" htmlEscape="false"/>
    
    <div>
		<input id="close" class="button_small fl" type="submit" value="Close" title="Do not save changes" />
		<input id="save" class="button_small fr" type="submit" value="Save" />
	</div>
	<input type="hidden" id="modify" value="" />
	<input type="hidden" id="roles" value="${command.userRoles}" />
	<form:hidden id="originalAccount" path="originalAccount"/>
</fieldset>	
</form:form>

<script type="text/javascript">
	$j(document).ready(function() {
		$j("#EditUserControlForm :input").change(function(){
			$j("#modify").val(1); <%-- //set the hidden field if an element is modified --%>
			return false;
		});
		
		//MD: Code for check the roles (Beta)
// 		var allRoles = $j("#roles").val();
// 		var roles = allRoles.substring(1, allRoles.length - 1).split(", ");
// 		for(var i = 0; i < roles.length; i++){
// 			$j(":checkbox").each(function(){
// 				if($j(this).val() == roles[i]){
// 					$j(this).attr("checked", "checked");
// 				}
// 			});
// 		}

// 		$j("#account").keyup(function(){
// 			$j.ajax({ type:"POST", url:"${FindUserURL}" + '?account=' + $j("#account").val(), async:false, success:function(json) {
// 				if(json.available == 'OK' || $j("#account").val() == $j("#originalAccount").val()){
// 					$j("#account").css("border", "1px solid");
// 					$j("#account").css("border-color", "green");
// 				}else{
// 					$j("#account").css("border", "1px solid");
// 					$j("#account").css("border-color", "red");
// 				}
// 			}});
// 			return false;
// 		});
		
		$j("#EditUserControlForm").submit(function (){
			$j.ajax({ type:"POST", url:$j(this).attr("action"), data:$j(this).serialize(), async:false, success:function(html) {
				if ($j(html).find(".inputerrors").length > 0){
					$j("#EditUserControlDiv").html(html);
				} else {
					$j("#body_left").html(html);
				}
			}});
			return false;
		});
		
		$j("#close").click(function(){
			if($j("#modify").val() == 1){
				$j("#EditUserControlForm").block({ message: $j("#question"),
					css: { 
						border: 'none', 
						padding: '5px',
						boxShadow: '1px 1px 10px #666',
						'-webkit-box-shadow': '1px 1px 10px #666'
						} ,
						overlayCSS: { backgroundColor: '#999' }	
				});
				return false;
			}else{
				$j.ajax({ url: '${ShowUserURL}', cache: false, success:function(html) { 
					$j("#body_left").html(html);
				}});
				return false;
			}
		});
	});
</script>

<div id="question" style="display:none; cursor: default"> 
	<h1>Discard changes?</h1> 
	<input type="button" id="yes" value="Yes" /> 
	<input type="button" id="no" value="No" /> 
</div>

<script type="text/javascript">
	$j(document).ready(function() {
		$j('#no').click(function() { 
			$j.unblockUI();
			$j(".blockUI").fadeOut("slow");
			$j("#question").hide();
			// Block is attached to form otherwise this block does not function when we use in transcribe and contextualize document
			$j("#EditUserControlForm").append($j("#question"));
			$j(".blockUI").remove();
			return false; 
		}); 
        
		$j('#yes').click(function() { 
			$j.ajax({ url: '${ShowUserURL}', cache: false, success:function(html) { 
				$j("#body_left").html(html);
			}});
				
			return false; 
		}); 
     	
	});
</script>
</security:authorize>