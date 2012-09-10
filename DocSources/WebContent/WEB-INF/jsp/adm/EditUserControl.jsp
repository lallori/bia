<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>

	<security:authorize ifAnyGranted="ROLE_ADMINISTRATORS">
		<c:url var="ShowUserURL" 	value="/admin/ShowUser.do">
			<c:param name="account"   	value="${command.account}" />
		</c:url>
		
		<c:url var="EditUserURL" value="/admin/EditUserControl.do" />
	</security:authorize>
	
	

<security:authorize ifAnyGranted="ROLE_ADMINISTRATORS">
	<form:form id="EditUserControlForm" method="post" class="edit">
	<fieldset>
		<legend><b>USER CONTROL</b></legend>
		<div class="listForm">
        	<div class="row">
            	<div class="col_l"><form:label for="account" id="userNameLabel" path="newAccount">Username</form:label></div>
            	<div class="col_l"><form:input id="account" name="account" cssClass="input_14c" path="newAccount"/></div>
        	</div>
        <div class="row">
            <div class="col_l"><form:label for="firstName" id="firstNameLabel" path="firstName">First Name</form:label></div>
            <div class="col_l"><form:input id="firstName" name="firstName" class="input_14c" type="text" value="" path="firstName"/></div>
            <div class="col_r"><form:label for="middleName" id="middleNameLabel" path="middleName">Middle Name</form:label></div>
            <div class="col_r"><form:input id="middleName" name="middleName" class="input_14c" type="text" value="" path="middleName"/></div>
        </div>
        <div class="row">
            <div class="col_l"><form:label for="lastName" id="lastNameLabel" path="lastName">Last Name</form:label></div>
            <div class="col_l"><form:input id="lastName" name="lastName" class="input_14c" type="text" value="" path="lastName"/></div>
        </div>
    </div>
      
    <hr />
    
    <div class="listForm">
        <div class="row">
            <div class="col_l"><form:label for="newPassword" id="newPasswordLabel" path="password">New password</form:label></div>
            <div class="col_l"><form:input id="newPassword" name="newPassword" class="input_15c" type="password" value="" path="password"/></div>
        </div>
    </div>
   	<p><b>Password Expires:</b></p>
    <div class="listForm">
		<div class="row">
            <div class="col_r"><form:label id="yearExpirationPasswordLabel" for="yearExpirationPassword" path="yearExpirationPassword" cssErrorClass="error">Year</form:label></div>
			<div class="col_l"><form:input id="yearExpirationPassword" path="yearExpirationPassword" class="input_4c" value="" maxlength="4"/></div>
			<div class="col_r"><form:label id="monthExpirationPasswordLabel" for="monthExpirationPassword" path="monthExpirationPassword" cssErrorClass="error">Month</form:label></div>
			<div class="col_l"><form:select id="monthExpirationPassword" path="monthExpirationPassword" cssClass="selectform_long" items="${months}" itemValue="monthNum" itemLabel="monthName"/></div>
			<div class="col_r"><form:label  for="dayExpirationPassword" id="dayExpirationPassword" path="dayExpirationPassword" cssErrorClass="error">Day</form:label></div>
			<div class="col_r"><form:input id="dayExpirationPassword" path="dayExpirationPassword" class="input_2c" maxlength="2"/></div>
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
	            <c:forEach var="userRoles" items="${authorities}" varStatus="iterator">
		  			<ul>
					<li>
						<input id="groupPolicies" name="userRoles" type="checkbox" value="${authorities[iterator.index].authority}">
						<label for="groupPolicies">${authorities[iterator.index].description}</label>
					</li>
					</ul>    				
	 			</c:forEach>           
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
    <div>
		<input id="close" type="submit" value="Close" title="Do not save changes" />
		<input id="save" class="save" type="submit" value="Save" />
	</div>
	<input type="hidden" value="" id="modify" />
</fieldset>	
</form:form>

<script type="text/javascript">
	$j(document).ready(function() {
		$j("#EditUserControlForm :input").change(function(){
			$j("#modify").val(1); <%-- //set the hidden field if an element is modified --%>
			return false;
		});
		
		$j("#EditUserControlForm").submit(function (){
			$j.ajax({ type:"POST", url:$j(this).attr("action"), data:$j(this).serialize(), async:false, success:function(html) {
				$j("#body_left").html(html);
			}});
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