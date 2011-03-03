<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>

				<li><a id="advsearchMenu" href="#"></a></li>
			<security:authorize ifNotGranted="ROLE_GUESTS">
				<li><a id="chronologyMenu" href="#"></a></li>
				<li><a id="myprofileMenu" href="<c:url value="/user/ShowUserProfile.do"/>" title="MY PROFILE"></a></li>
				<li><a id="messagesMenu" href="#"></a></li>
			</security:authorize>
				<li><a id="logoutMenu" href="<c:url value="/LogoutUser.do"/>"></a></li>
				<script type="text/javascript">
					$j(document).ready(function() {
						$j("#profile").click(function() {
							Modalbox.show($j(this).attr("href"), {title: $j(this).attr("title"), width: 900, height: 500});
							return false;
						});
					});
				</script>						
