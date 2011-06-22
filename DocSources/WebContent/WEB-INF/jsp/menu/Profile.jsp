<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>

				<li class="advsearchMenu"><a id="advsearchMenu" href="<c:url value="/src/ChoiceAdvancedSearch.do"/>"></a></li>
			<security:authorize ifNotGranted="ROLE_GUESTS">
				<li class="myHistoryMenu"><a id="myHistoryMenu" href="#"></a></li>
				<li class="myprofileMenu"><a id="myprofileMenu" href="<c:url value="/user/ShowUserProfile.do"/>" title="MY PROFILE"></a></li>
				<li class="messagesMenu"><a id="messagesMenu" href="#"></a></li>
			</security:authorize>
				<li class="logOutMenu"><a id="logoutMenu" href="<c:url value="/LogoutUser.do"/>"></a></li>
				<script type="text/javascript">
					$j(document).ready(function() {
						$j("#advsearchMenu").click(function() {															
							Modalbox.show($j(this).attr("href"), {title: "ADVANCED SEARCH", width: 750, height: 380});return false;
						});							   	
						$j("#myHistoryMenu").click(function() {															
							Modalbox.show($j(this).attr("href"), {title: "MY HISTORY", width: 750, height: 500});return false;
						});	
						$j("#myprofileMenu").click(function() {
							Modalbox.show($j(this).attr("href"), {title: "MY PROFILE", width: 750, height: 400});return false;}																	
						);	
						$j("#messagesMenu").click(function() {															
							Modalbox.show($j(this).attr("href"), {title: "MESSAGES", width: 750, height: 350});return false;}
						);
					});
				</script>						
