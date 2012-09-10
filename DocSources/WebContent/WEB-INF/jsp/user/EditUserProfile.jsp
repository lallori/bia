<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>

<c:url var="EditUserProfileURL" value="/user/EditUserProfile.do" />


<c:url var="ShowUserProfileURL" value="/user/ShowUserProfile.do" />

<form:form id="EditUserProfileForm" action="${EditUserProfileURL}" method="post" cssClass="edit">
	<fieldset>
	<div class="listFormProfile">
		<div class="row">
			<div class="col_l"><form:label id="emailLabel" for="mail" path="mail">Email</form:label></div>
			<div class="col_l"><form:input id="email" path="mail" cssClass="input_29c" /><form:errors path="mail" /></div>
		</div>
	</div>
	<div class="listFormProfile">
		<div class="row">
			<div class="col_l"><label for="link" id="linkLabel">Link</label></div>
			<div class="col_r"><input id="link" name="link" class="input_43c" type="text" value="http://" /></div>
			<div class="col_r"><span>(Portrait image)</span></div>
		</div>
		<div class="row">
			<div class="col_l"><label for="browse" id="browseLabel">Browse</label></div>
			<div class="col_r"><input id="browse" name="browse" class="input_43c" type="file" value="" size="24"/></div>
			<div class="col_r"><span>(Portrait image)</span></div>
		</div>
	</div>
	
	<div class="listFormProfile">
		<div class="row">
			<div class="col_r"><form:label id="addressLabel" for="address" path="address">Address</form:label></div>
			<div class="col_l"><form:input id="address" cssClass="input_43c" path="address"/><form:errors path="address" /></div>
			<div class="col_r"><form:label for="country" id="countryLabel" path="country">Country</form:label><!-- Autocomplete or DropdownMenu --></div>
			<div class="col_r"><form:input id="country" path="country" cssClass="input_20c" /><form:errors path="country" /></div>
		</div>
	</div>

	<hr />
	
	<div class="listFormProfile">
		<div class="row">
			<div class="col_l"><label for="group" id="groupLabel">Group</label><!-- Autocomplete or DropdownMenu --></div>
			<div class="col_l"><input id="group" name="group" class="input_20c" type="text" disabled="disabled" value=<security:authentication property="principal.significantRoleDescription"/> /></div>
			<div class="col_r"><form:label for="title" id="titleLabel" path="title">Title</form:label></div>
			<div class="col_r"><form:input id="title" path="title" cssClass="input_20c" /><form:errors path="title" /></div>
		</div>
		<div class="row">
			<div class="col_l"><form:label for="organization" id="organizationLabel" path="organization">Organization</form:label></div>
			<div class="col_l"><form:input id="organization" path="organization" cssClass="input_20c" /><form:errors path="organization" /></div>
			<div class="col_r"><form:label for="location" id="locationLabel" path="location">Location</form:label></div>
			<div class="col_r"><form:input id="location" path="location" cssClass="input_20c"/><form:errors path="location" /></div>
		</div>
	</div>
	
	<hr />
	
	<div class="listFormProfile">
		<div class="row">
			<div class="col_l"><form:label for="interests" id="interestsLabel" path="interests">Interests</form:label></div>
			<div class="col_r"><form:input id="interests" path="interests" cssClass="input_20c" /><form:errors path="interests" /></div>
			<div class="col_r"><label for="resume" id="resumeLabel">Resume</label></div>
			<div class="col_r"><input id="resume" name="resume" class="input_20c" type="file" value="" size="8"/></div>
		</div>
	</div>
	
	<div>
		<input id="close" type="submit" value="Close" title="Do not save changes" />
		<input id="save" type="submit" value="Save" />
	</div>
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
