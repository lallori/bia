<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>

			<security:authorize ifNotGranted="ROLE_GUESTS">
				<c:url var="ShowUserProfileURL" value="/user/ShowUserProfile.do"/>

				<li><a href="${ShowUserProfileURL}" id="myprofileMenu">User Preferences</a></li>
				<script type="text/javascript">
					$j(document).ready(function() {					   	
						$j("#myprofileMenu").click(function() {
							Modalbox.show($j(this).attr("href"), {title: "USER PREFERENCES", width: 760, height: 470});return false;}																	
						);	
					});
				</script>						
			</security:authorize>
				