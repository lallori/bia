<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>

<security:authorize ifAnyGranted="ROLE_ADMINISTRATORS">
	<c:url var="ShowUserSearchURL" 	value="/admin/ShowUserSearch.do" />

<h3 class="userSearch">USER SEARCH</h3>

<form:form id="userSearchForm" method="post" cssClass="edit" action="${ShowUserSearchURL}">
<fieldset>
<legend><b>NEW FILTER</b></legend>
	<div style="margin-top:5px;">	
		<form:label path="fullName" for="fullName" id="fullNameLabel">Full name</form:label>
		<input id="fullName" name="fullName" class="input_7c" type="text" value="Contains" disabled="">
        <form:input id="fullName" path="fullName" name="fullName" cssClass="input_25c" type="text" value=""/>
	</div>	
	<div>
		<form:label path="userName" for="userName" id="userNameLabel">User name</form:label>
		<input id="userName" name="userName" class="input_7c" type="text" value="Contains" disabled/>
        <form:input id="userName" path="userName" name="userName" cssClass="input_25c" type="text" value=""/>
	</div>
	<div>
        <input class="search" type="submit" value="Search" />
	</div>
</fieldset>	
</form:form>

<script>
	$j(document).ready(function() {
				
		$j("#userSearchForm").submit(function(){
			var title = "User Search";
			var formSubmitURL = $j(this).attr("action") + '?' + $j(this).serialize();
			
			var tabExist = false;
			var numTab = 0;
			$j("#tabs ul li a").each(function(){
				if(!tabExist)
					numTab++;
				if(this.text == title)
					tabExist = true;
			});
			if(!tabExist){
				$j( "#tabs" ).tabs( "add" , formSubmitURL, title + "</span></a><span class=\"ui-icon ui-icon-close\" title=\"Close Tab\">Remove Tab");
				$j("#tabs").tabs("select", $j("#tabs").tabs("length")-1);
				return false;
			}else{
				$j("#tabs").tabs("select", numTab-1);
				$j("#tabs").tabs("url", numTab - 1, formSubmitURL);
				
				$j("#tabs").tabs("load", numTab - 1);
				return false;
			}
		});
	});
</script>
</security:authorize>