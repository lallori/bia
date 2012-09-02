<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn2" uri="http://bia.medici.org/jsp:jstl" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>

<%-- 	<security:authorize ifAnyGranted="ROLE_ADMINISTRATORS"> --%>
<%-- 		<c:url var="" 	value=""> --%>
<%-- 			<c:param name=""   	value="" /> --%>
<%-- 		</c:url> --%>
<%-- 	</security:authorize> --%>
	
	

<security:authorize ifAnyGranted="ROLE_ADMINISTRATORS">

<div id="EditEmailControlDiv" class="background">
	<div class="title">
		<h5>EMAIL CONTROL</h5>
		<a id="EditEmailControl" class="editButton" href="/DocSources/adm/EditEmailControl.html" title="Edit Email Control"></a>
	</div>
	<div class="list">
		<div class="row">
			<div class="item37">Email adress</div> 
			<div class="value">${user.mail}</div>
		</div>
		<div class="row">
			<div class="item37">Edit display</div> 
			<div class="value">Hide my email from everyone</div>
		</div>
		<div class="row">
			<div class="item37">Recive notification by email</div> 
			<div class="value">Deactivated</div>
		</div>
	</div>
</div>

<script type="text/javascript">
// 	$j(document).ready(function() {			
// 		$j("#EditEmailControl").click(
// 				function(){
// 					$j("#EditEmailControlDiv").load($j(this).attr("href"));
// 					return false;
// 				});
// 	});
</script>
</security:authorize>