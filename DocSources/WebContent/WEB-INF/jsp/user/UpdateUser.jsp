<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

	<c:url var="uploadForm" value="/user/UploadUserPhoto.do"/>
	<c:url var="findCountryUrl" value="/user/ajax/FindCountry.json"/>
	<c:url var="tickIcon" value="/images/tick.png"/>
	<c:url var="busyIcon" value="/images/busy_icon.gif"/>
		
		<script type='text/javascript' src='<c:url value="/scripts/jquery-1.4.2.js"/>'></script>
		<script type='text/javascript' src='<c:url value="/scripts/jquery.autocomplete.js"/>'></script>
		<script type='text/javascript' src='<c:url value="/scripts/jqModal.js"/>'></script>
		<link rel="stylesheet" href='<c:url value="/styles/jqModal.css" />' type="text/css" media="screen, projection">
		<link rel="stylesheet" href="<c:url value="/styles/jquery.autocomplete2.css" />" type="text/css" media="screen, projection">

<div id="myProfile">
	<div id="userProfile">
		<h1>User Profile</h1>
		<h3>John Smith</h3>
		
		<br/>
	
		<img src="/DocSources/images/default_user.jpg" alt="default image" id="imgProfile"/>
	
		<div id="EditGroupProfileDiv">
			<b>Group:</b> Community User
		</div>

		
		<div id="EditTitleProfileDiv">
			<b>Title:</b> Curator <a id="EditTitleProfile" href="/DocSources/EditTitleProfile.html">edit</a>
		</div>
		
		<div id="EditOrgProfileDiv">
			<b>Organization:</b> Naples Museum of Art <a id="EditOrgProfile" href="/DocSources/EditOrgProfile.html">edit</a>

		</div>
		
		<div id="EditLocationProfileDiv">
			<b>Location:</b> Italy, Florence <a id="EditLocationProfile" href="/DocSources/EditLocationProfile.html">edit</a>
		</div>
		
		<div id="EditInterestsProfileDiv">
			<b>Interests:</b> Salvador Rosa, ecc. <a id="EditInterestsProfile" href="/DocSources/EditInterestsProfile.html">edit</a>

		</div>
		
		<div id="EditResumeProfileDiv">
			<b>Resume:</b> no. <a id="EditResumeProfile" href="/DocSources/EditResumeProfile.html">add/modify resume</a>
		</div>
	</div>
	
	<div id="helpProfile">
		<h1>Help System</h1>

		<ul>
			<li><a href="#">User Manual (HTML version)</a></li>
			<li><a href="#">Download Manual (PDF version)</a></li>
			<li><a href="#">Help Videos</a></li>
		</ul>
	</div>
	
	<a href="http://courses.medici.org/" target="_blank"><img src="/DocSources/images/button_courses.jpg" alt="MAP courses" id="coursesProfile"/></a>

</div>

<div id="modalBoxCloseDivProfile"><input value="Close" class="modalBox-close" onClick="Modalbox.hide(); return false;" type="submit"><br /><span>(or click the overlay)</span></div>
	
<script type="text/javascript">
			$j(document).ready(function() {
				$j("#EditTitleProfile").click(function(){$j("#EditTitleProfileDiv").load($j(this).attr("href"));return false;});
				$j("#EditOrgProfile").click(function(){$j("#EditOrgProfileDiv").load($j(this).attr("href"));return false;});
				$j("#EditLocationProfile").click(function(){$j("#EditLocationProfileDiv").load($j(this).attr("href"));return false;});
				$j("#EditInterestsProfile").click(function(){$j("#EditInterestsProfileDiv").load($j(this).attr("href"));return false;});
				$j("#EditResumeProfile").click(function(){$j("#EditResumeProfileDiv").load($j(this).attr("href"));return false;});
			});
</script>
				<form:form commandName="command" method="post">
				  	<fieldset>		
						<legend>Account Fields</legend>
						<p>
							<form:label	id="accountLabel" for="account" path="account" cssErrorClass="error">Account*</form:label><br/>
							<form:input readonly="true" path="account" /><form:errors path="account" />
						</p>
						<p>	
							<form:label id="firstNameLabel" for="firstName" path="firstName" cssErrorClass="error">First Name*</form:label><br/>
							<form:input path="firstName" /><form:errors path="firstName" />
						</p>
						<p>	
							<form:label id="lastNameLabel" for="lastName" path="lastName" cssErrorClass="error">Last Name*</form:label><br/>
							<form:input path="lastName" /><form:errors path="lastName" />
						</p>
						<p>	
							<form:label id="cityLabel" for="city" path="city" cssErrorClass="error">City*</form:label><br/>
							<form:input path="city" /><form:errors path="city"/>
						</p>
						<p>	
							<form:label id="countryDescriptionLabel" for="countryDescription" path="countryDescription" cssErrorClass="error">Country*</form:label><br/>
							<form:input path="countryDescription" id="countryAutoCompleter"/><form:errors path="countryDescription"/>
						</p>
						<p>	
							<form:label id="mailLabel" for="mail" path="mail" cssErrorClass="error">Mail*</form:label><br/>
							<form:input path="mail" /><form:errors path="mail"/>
						</p>
						<p>	
							<form:label id="addressLabel" for="address" path="address" cssErrorClass="error">Address</form:label><br/>
							<form:input path="address" /><form:errors path="address"/>
						</p>
						<p>	
							<form:label id="organizationLabel" for="organization" path="organization" cssErrorClass="error">Organization</form:label><br/>
							<form:input path="organization" /><form:errors path="organization"/>
						</p>
						<p>	
							<form:label id="titleLabel" for="title" path="title" cssErrorClass="error">Title</form:label><br/>
							<form:input path="title" /><form:errors path="title"/>
						</p>
						<p>	
							<form:label id="interestsLabel" for="interests" path="interests" cssErrorClass="error">Interests*</form:label><br/>
							<form:input path="interests" /><form:errors path="interests"/>
						</p>
						<img src="<c:url value="/user/ShowUserPhoto.do"/>" class="ex2trigger"/>
						<p>	
							<input id="update" type="submit" value="Update" />
						</p>
      					<form:hidden path="countryCode"/>
					</fieldset>
				</form:form>

				<div class="jqmWindow" id="ex2">
					Please wait... <img src="<c:url value="/images/busy_icon.gif"/>" alt="loading" />
				</div>

		<script type='text/javascript'>
		$().ready(function() {
			$('#ex2').jqm({ajax: '${uploadForm}', trigger: 'img.ex2trigger'});

			var a = $('#countryAutoCompleter').autocomplete({ 
			    serviceUrl:'${findCountryUrl}',
			    minChars:1, 
			    delimiter: /(,|;)\s*/, // regex or character
			    maxHeight:400,
			    width:300,
			    zIndex: 9999,
			    deferRequestBy: 0, //miliseconds
			    noCache: false, //default is false, set to true to disable caching
			    onSelect: function(value, data){ $('#countryAutoCompleter').parent().next().find("#countryCode").val(data); }
			  });

			function fieldValidated(field, result) {
				if (result.valid) {
					$("#" + field ).removeClass("error");
					$("#" + field + "\\.errors").remove();

					if ($("#" + field + "\\.errors").length == 0) {
						$("#" + field).after("<span id='" + field + ".errors'><image id='" + field + "OkIcon' src='${tickIcon}'/></span>");
					} else {
						$("#" + field + ".errors").html("<span id='" + field + ".errors'><image id='" + field + "OkIcon' src='${tickIcon}'/></span>");		
					}
				} else {
					$("#" + field ).addClass("error");
					if ($("#" + field + "\\.errors").length == 0) {
						$("#" + field).after("<span id='" + field + ".errors'>" + result.message + "</span>");		
					} else {
						$("#" + field + "\\.errors").html("<span id='" + field + ".errors'>" + result.message + "</span>");		
					}
				}			
			}
			});
		</script>
