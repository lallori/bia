<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>

	<security:authorize ifAnyGranted="ROLE_ADMINISTRATORS, ROLE_ONSITE_FELLOWS, ROLE_FORMER_FELLOWS, ROLE_FELLOWS, ROLE_DIGITIZATION_TECHNICIANS, ROLE_COMMUNITY_USERS">
		<c:url var="CheckNewMessagesURL" value="/community/CheckNewMessages.json"/>
		
		<c:url var="ShowMessagesURL" value="/community/ShowMessagesByCategory.do">
			<c:param name="userMessageCategory" value="INBOX"/>
			<c:param name="completeDOM" value="true"/>
		</c:url>
		
		<li><a id="messagesMenu" href="${ShowMessagesURL}" target="_blank"><fmt:message key="menu.myMessages.button"/></a><span id="messagesCount">(${numberOfNewMessages})</span></li>
	
		<script type="text/javascript">
			$j(document).ready(function() {					   	
				setInterval(function() {
					$j.ajax({ type:"GET", url:"${CheckNewMessagesURL}", async:false, success:function(data) {
						if(data.newMessages == 'true'){
							$j("#messagesCount").html('(' + data.numberOfNewMessages + ')');
						}else{
							$j("#messagesCount").html('(0)');
						}
					}});
				}, 60000); //every 1 minute...
			});
		</script>						
	</security:authorize>
