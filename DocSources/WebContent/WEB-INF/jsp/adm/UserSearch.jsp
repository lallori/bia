<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>

	<security:authorize ifAnyGranted="ROLE_ADMINISTRATORS">
		<c:url var="" 	value="">
			<c:param name=""   	value="" />
		</c:url>
	</security:authorize>
	
	

<security:authorize ifAnyGranted="ROLE_ADMINISTRATORS">
	<h3 class="userSearch">USER SEARCH</h3>

<form id="userSearchForm" action="/DocSources/adm/UserSearch.do" method="post" class="edit">
<fieldset>
<legend><b>NEW FILTER</b></legend>
	<div style="margin-top:5px;">	
		<label for="fullName" id="fullNameLabel">Full name</label>
		<input id="fullName" name="fullName" class="input_7c" type="text" value="Contains" disabled="">
        <input id="fullName" name="fullName" class="input_25c" type="text" value="">
	</div>	
	<div>
		<label for="userName" id="userNameLabel">User name</label>
		<input id="userName" name="userName" class="input_7c" type="text" value="Contains" disabled/>
        <input id="userName" name="userName" class="input_25c" type="text" value=""/>
	</div>
		<input  class="save" type="submit" value="Save" />
        <input class="search" type="submit" value="Search" />
	</div>
</fieldset>	
</form>

<script>
	$j(document).ready(function() {
		$j(".search").click(
			function(){
				$j("#body_right").load("/DocSources/adm/userSearchTable.html");return false;}
				);
	});
</script>
</security:authorize>