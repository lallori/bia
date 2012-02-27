<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>

<c:url var="ChoiceAdvancedSearchURL" value="/src/ChoiceAdvancedSearch.do"/>

<c:url var="ShowMyHistoryURL" value="/user/ShowMyHistory.do"/>

<c:url var="ShowUserProfileURL" value="/user/ShowUserProfile.do"/>

<c:url var="ShowMessagesURL" value="/community/ShowMessagesByCategory.do">
	<c:param name="userMessageCategory" value="INBOX" />
</c:url>

				<li class="advsearchMenu"><a id="advsearchMenu" href="${ChoiceAdvancedSearchURL}"></a></li>
			<security:authorize ifNotGranted="ROLE_GUESTS">
				<li class="myHistoryMenu"><a id="myHistoryMenu" href="${ShowMyHistoryURL}"></a></li>
				<li class="myprofileMenu"><a id="myprofileMenu" href="${ShowUserProfileURL}" title="MY PROFILE"></a></li>
				<li class="messagesMenu"><a id="messagesMenu" href="${ShowMessagesURL}"></a></li>
			</security:authorize>
				<li class="logOutMenu"><a id="logoutMenu" href="<c:url value="/LogoutUser.do"/>"></a></li>
				<script type="text/javascript">
					$j(document).ready(function() {
						$j("#advsearchMenu").click(function() {															
							Modalbox.show($j(this).attr("href"), {title: "ADVANCED SEARCH", width: 750, height: 325});return false;
						});							   	
						$j("#myHistoryMenu").click(function() {															
							Modalbox.show($j(this).attr("href"), {title: "MY HISTORY", width: 750, height: 500});return false;
						});	
						$j("#myprofileMenu").click(function() {
							Modalbox.show($j(this).attr("href"), {title: "MY PROFILE", width: 760, height: 440});return false;}																	
						);	
						$j("#messagesMenu").click(function() {															
							Modalbox.show($j(this).attr("href"), {title: "MESSAGES", width: 750, height: 350});return false;}
						);
					});
				</script>						
