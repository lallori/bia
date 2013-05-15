<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>

<c:url var="ShowPersonalDirectoryURL" value="/src/ShowPersonalDirectory.do"/>

			<security:authorize ifNotGranted="ROLE_GUESTS">
				<li><a href="${ShowPersonalDirectoryURL}" id="personalDirectory"><fmt:message key="menu.myPersonalDirectory.button"/></a></li>
			</security:authorize>
				<script type="text/javascript">
					$j(document).ready(function() {					   	
						$j("#personalDirectory").click(function(){
							Modalbox.show($j(this).attr("href"), {title: "<fmt:message key="menu.myPersonalDirectory.open"/>", width: 270, height: 200});
							return false;
						}						
						);
					});
				</script>						
