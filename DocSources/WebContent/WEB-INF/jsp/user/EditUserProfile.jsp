<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>

<c:url var="EditUserProfileURL" value="/user/EditUserProfile.do" />


<c:url var="ShowUserProfileURL" value="/user/ShowUserProfile.do" />

<form:form id="EditUserProfileForm" action="${EditUserProfileURL}" method="post" cssClass="edit">
	<fieldset>
	<div>
		<form:label id="emailLabel" for="mail" path="mail">Email</form:label>
		<form:input id="email" path="mail" cssClass="input_20c" /><form:errors path="mail" />
	</div>
	
	<div>
		<label for="password" id="passwordLabel">Password</label>
		<input id="password" name="password" class="input_15c" type="password" />
		<label for="newPassword" id="newPasswordLabel">New Password</label>
		<input id="newPassword" name="newPassword" class="input_15c" type="password" />
	</div>
	
	<div>
        <label for="link" id="linkLabel">Link</label>
        <input id="link" name="link" class="input_33c" type="text" value="http://" /><span>(Portrait image)</span>
    </div>
    
    <div>	
        <label for="browse" id="browseLabel">Browse</label>
        <input id="browse" name="browse" class="input_33c" type="file" value="" size="40"/><span>(Portrait image)</span>
    </div>
	
	<div>
		<form:label id="addressLabel" for="address" path="address">Address</form:label>
		<form:input id="address" cssClass="input_20c" path="address"/><form:errors path="address" />
		<form:label for="country" id="countryLabel" path="country">Country</form:label><!-- Autocomplete or DropdownMenu -->
		<form:input id="country" path="country" cssClass="input_20c" /><form:errors path="country" />
	</div>
	
	<hr />
	
	<div>	
		<label for="group" id="groupLabel">Group</label><!-- Autocomplete or DropdownMenu -->
		<input id="group" name="group" class="input_20c" type="text" disabled="disabled" value=<security:authentication property="principal.significantRoleDescription"/> />
		<form:label for="title" id="titleLabel" path="title">Title</form:label>
		<form:input id="title" path="title" cssClass="input_20c" /><form:errors path="title" />
	</div>
	
	<div>
		<form:label for="organization" id="organizationLabel" path="organization">Organization</form:label>
		<form:input id="organization" path="organization" cssClass="input_20c" /><form:errors path="organization" />
		<form:label for="location" id="locationLabel" path="location">Location</form:label>
		<form:input id="location" path="location" cssClass="input_20c"/><form:errors path="location" />
	</div>
	
	<hr />
	
	<div>
		<form:label for="interests" id="interestsLabel" path="interests">Interests</form:label>
		<form:input id="interests" path="interests" cssClass="input_20c" /><form:errors path="interests" />
		<label for="resume" id="resumeLabel">Resume</label>
		<input id="resume" name="resume" class="input_20c" type="file" value="" size="20"/>
	</div>
	
		<input id="close" type="submit" value="Close" title="Do not save changes" />
		<input id="save" type="submit" value="Save" />
	</fieldset>
</form:form>
	
	
	
		
	
	
	
	<!--  <a href="http://courses.medici.org/" target="_blank"><img src="/DocSources/images/button_courses.jpg" alt="MAP courses" id="coursesProfile"/></a>-->


	
		<script type="text/javascript">
			$j(document).ready(function() {
				$j("#CloseUserProfile").click(function(){
					Modalbox.hide();
					return false;
				});

				$j("#EditUserProfileForm").submit(function(){
					$j.ajax({type:"POST", url:$j(this).attr("action"), data:$j(this).serialize(), async:false, success:function(html) {
						$j("#myProfile").html(html);
					}});
					return false;
				});	

				$j("#close").click(function(){
					$j.ajax({ url: '${ShowUserProfileURL}', cache: false, success: function(html){
						$j("#myProfile").html(html);
					}});
					return false;
				});
			});
		</script>
