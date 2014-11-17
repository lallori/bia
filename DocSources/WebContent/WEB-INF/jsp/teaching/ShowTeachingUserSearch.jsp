<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>

<security:authorize ifAnyGranted="ROLE_ADMINISTRATORS, ROLE_TEACHERS">
	<c:url var="ShowUserSearchURL" 	value="/teaching/ShowTeachingUserSearch.do" />

	<h3 class="userSearch"><fmt:message key="teaching.showTeachingUserSearch.uSerSearchForTeachingModule"/></h3>
	<h4 class="userSearchSubTitle"><fmt:message key="teaching.showTeachingUserSearch.searchUserToGrantRevoke"/></h4>
	
	<form:form id="userSearchForm" method="post" cssClass="edit" action="${ShowUserSearchURL}">
		<fieldset>
			<legend><b><fmt:message key="teaching.showTeachingUserSearch.nEwFilter"/></b></legend>
				<div class="listForm">
					<div class="row">
						<div class="col_l"><form:label path="fullName" for="fullName"><fmt:message key="teaching.showTeachingUserSearch.fullName"/></form:label></div>
						<div class="col_r"><input id="fullName" name="fullName" class="input_7c" type="text" value="Contains" disabled=""></div>
						<div class="col_r"><form:input path="fullName" cssClass="input_25c"/></div>	
					</div>	
					<div class="row">
						<div class="col_l"><form:label path="userName" for="userName"><fmt:message key="teaching.showTeachingUserSearch.userName"/></form:label></div>
						<div class="col_r"><input id="userName" name="userName" class="input_7c" type="text" value="Contains" disabled/></div>
				        <div class="col_r"><form:input path="userName" cssClass="input_25c"/></div>
					</div>
					<div class="row">
						<div class="col_l"><form:label path="role" for="role"><fmt:message key="teaching.showTeachingUserSearch.userRole"/></form:label></div>
						<div class="col_r"><form:radiobutton path="role" value="All"/></div>
				        <div class="col_l"><fmt:message key="teaching.showTeachingUserSearch.all"/></div>
					</div>
					<div class="row">
						<div class="col_l"></div>
						<div class="col_r"><form:radiobutton path="role" value="Stud"/></div>
				        <div class="col_l"><fmt:message key="teaching.showTeachingUserSearch.onlyStudents"/></div>
					</div>
					<div class="row">
						<div class="col_l"></div>
						<div class="col_r"><form:radiobutton path="role" value="NoStud"/></div>
				        <div class="col_l"><fmt:message key="teaching.showTeachingUserSearch.notStudents"/></div>
					</div>
				</div>	
			<input class="search button_small fl" type="submit" value="Search" />
		</fieldset>	
	</form:form>
	
	<script>
		$j(document).ready(function() {
			
			$j(".studRadio").click(function() {
				
			});
					
			$j("#userSearchForm").submit(function() {
				var title = "Teaching User Search";
				var formSubmitURL = $j(this).attr("action") + '?' + $j(this).serialize();
				
				var tabExist = false;
				var numTab = 0;
				$j("#tabs ul li a").each(function() {
					if (!tabExist) {
						 numTab++;
					}
					if (this.text == title) {
						tabExist = true;
					}
				});
				if (!tabExist) {
					$j("#tabs").tabs( "add" , formSubmitURL, title + "</span></a><span class=\"ui-icon ui-icon-close\" title=\"Close Tab\">Remove Tab");
					$j("#tabs").tabs("select", $j("#tabs").tabs("length")-1);
					return false;
				} else {
					$j("#tabs").tabs("select", numTab-2);
					$j('#tabs ul li').eq(numTab-2).data('loaded', false).find('a').attr('href', formSubmitURL);
	
					console.log(" tabs ->" + (numTab-1) + " url ->" + formSubmitURL);
	
					$j("#tabs").tabs("load", numTab-2);
					return false;
				}
			});
		});
	</script>
</security:authorize>