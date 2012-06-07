<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>

	<security:authorize ifAnyGranted="ROLE_ADMINISTRATORS, ROLE_DIGITIZATION_TECHNICIANS">
		<li><a id="messagesMenu" href="#" target="_blank">My Messages</a><span>(3)</span></li>
	</security:authorize>
	
	<script>
	$j("#messagesMenu").click(function() {
		//Modalbox.show($j(this).attr("href"), {title: "DIGITIZATION MODULE", width: 350, height: 190});return false;}
	});
	</script>
