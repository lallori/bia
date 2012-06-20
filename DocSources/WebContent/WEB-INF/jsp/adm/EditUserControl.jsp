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
            <div class="col_l"><form:input id="newPassword" name="newPassword" class="input_8c" type="password" value="" path="password"/></div>
            <div class="col_r">
            	<label for="unmask" id="unmaskLabel">Unmask</label>
        		<input type="checkbox" name="unmaskLabel" class="checkboxPers2"/>
            </div>
        </div>
        <div class="row">
        	<b>Password Expires:</b>
			<br />
            <div class="col_r"><form:label id="yearPassExpLabel" for="yearPassExp" path="yearPassExp" cssErrorClass="error">Year</form:label></div>
			<div class="col_l"><form:input id="yearPassExp" path="yearPassExp" class="input_4c" value="" maxlength="4"/></div>
			<div class="col_r"><form:label id="monthPassExpLabel" for="monthPassExp" path="monthPassExp" cssErrorClass="error">Month</form:label></div>
			<div class="col_l"><form:select id="monthPassExp" path="monthPassExp" cssClass="selectform_long" items="${months}" itemValue="monthNum" itemLabel="monthName"/></div>
			<div class="col_r"><form:label  for="dayPassExp" id="dayPassExpLabel" path="dayPassExp" cssErrorClass="error">Day</form:label></div>
			<div class="col_r"><form:input id="dayPassExp" path="dayPassExp" class="input_2c" maxlength="2"/></div>
            <div class="col_r">
            	<label for="forcePswdChange" id="forcePswdChangeLabel">Force Password Change
                <input type="checkbox" name="forcePswdChange" class="checkboxPers2"/>
            </div>
        </div>
   	</div>
    
    <hr />
    
    <div class="listForm">
        <div class="row">
            <div class="col_l"><label for="groupPolicies" id="groupPoliciesLabel">Group policies</label></div>
            <div class="col_r">
            	<form:select id="groupPolicies" name="groupPolicies" cssClass="selectform_Xlong" path="userRole" items="${userRoles}" />
            </div>
        </div>
    </div>
    <div class="listForm">
    	<b>Account Expiration Time:</b>
		<br />
        <div class="row">
<%--             <div class="col_l"><form:label for="accExpirTime" id="accExpirTimeLabel" path="accExpirTime">Account Expiration Time</form:label></div> --%>
<%--             <div class="col_l"><form:input id="yearExpirTime" name="accExpirTime" class="input_8c" type="text" value="" path="accExpirTime"/></div> --%>
			<div class="col_r"><form:label id="yearExpirTimeLabel" for="yearExpirTime" path="yearExpirTime" cssErrorClass="error">Year</form:label></div>
			<div class="col_l"><form:input id="yearExpirTime" path="yearExpirTime" class="input_4c" value="" maxlength="4"/></div>
			<div class="col_r"><form:label id="monthExpirTimeLabel" for="monthExpirTime" path="monthExpirTime" cssErrorClass="error">Month</form:label></div>
			<div class="col_l"><form:select id="monthExpirTime" path="monthExpirTime" cssClass="selectform_long" items="${months}" itemValue="monthNum" itemLabel="monthName"/></div>
			<div class="col_r"><form:label  for="dayExpirTime" id="dayExpirTimeLabel" path="dayExpirTime" cssErrorClass="error">Day</form:label></div>
			<div class="col_r"><form:input id="dayExpirTime" path="dayExpirTime" class="input_2c" maxlength="2"/></div>
		</div>
		<div class="row">
            <div class="col_r">
            	<label for="lockAccount" id="lockAccountLabel">Lock account</label>
				<input type="checkbox" name="lockAccount" class="checkboxPers1"/>
            </div>
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