<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>

<c:url var="ShowMyHistoryURL" value="/user/ShowMyHistory.do"/>

			<security:authorize ifNotGranted="ROLE_GUESTS">
				<li class="myHistoryMenu"><a id="myHistoryMenu" href="${ShowMyHistoryURL}"></a></li>
			</security:authorize>
				<script type="text/javascript">
					$j(document).ready(function() {					   	
						$j("#myHistoryMenu").click(function() {															
							Modalbox.show($j(this).attr("href"), {title: "MY HISTORY", width: 750, height: 415});return false;
						});	
					});
				</script>						
