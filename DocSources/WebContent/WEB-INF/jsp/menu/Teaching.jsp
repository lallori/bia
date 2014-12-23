<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
			
		<security:authorize ifAnyGranted="ROLE_TEACHERS" ifNotGranted="ROLE_ADMINISTRATORS">
			<li><a href="<c:url value='/teaching/ShowTeachingModule.do' />" id="teachingModule">Teaching Module</a></li>
			
			<script type="text/javascript">
				$j(document).ready(function() {
					$j("#teachingModule").click(function() {
						Modalbox.show($j(this).attr("href"), {title: "TEACHING MODULE", width: 270, height: 240});
						return false;
					});
				});
			</script>
		</security:authorize>