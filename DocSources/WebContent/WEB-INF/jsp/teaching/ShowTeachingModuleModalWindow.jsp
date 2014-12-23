<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
	
	<security:authorize ifAnyGranted="ROLE_ADMINISTRATORS, ROLE_TEACHERS">
		
		<div id="teachingModalDiv">
			<div id="grantUsersDiv" class="adminModalButtonContainer">
		        <a id="grantUsers" class="button_large" href="<c:url value='/teaching/ShowTeachingUserSearch.do' />"><fmt:message key="teaching.showTeachingModuleModalWindow.grantRevokeUsers"/></a>
			</div>
		    
			<div id="manageCoursesDiv" class="adminModalButtonContainer">
		        <a id="manageCourses" class="button_large" href="<c:url value='/teaching/ShowManageCourses.do' />"><fmt:message key="teaching.showTeachingModuleModalWindow.manageCourses"/></a>
			</div>
		    
			<div id="createCoursesDiv" class="adminModalButtonContainer">
		        <a id="createCourses" class="button_large" href="<c:url value='/teaching/ShowCreateCourse.do' />"><fmt:message key="teaching.showTeachingModuleModalWindow.createCourse"/></a>
			</div>
			
			<!-- Work in progress
			<div id="uploadImagesDiv" class="adminModalButtonContainer">
				<a id="uploadImages" class="button_large" href="<c:url value='/teaching/ShowManageImagesForLessons.do' />">Manage Images For Lessons</a>
			</div>
			-->
		    
			<input id="close" class="button_small" type="submit" title="Close Teaching Module window" value="Close"/>
		</div>
		
		<script type="text/javascript">
			$j(document).ready(function() {
				$j("#grantUsers").click(function() {
					$j("#body_left").load($j(this).attr("href"));
					Modalbox.hide();
					return false;
				});
		
				$j("#manageCourses").click(function() {
					$j("#body_left").load($j(this).attr("href"));
					Modalbox.hide();
					return false;
				});
		
				$j("#createCourses").click(function() {
					$j("#body_left").load($j(this).attr("href"));
					Modalbox.hide();
					return false;
				});
				
				$j("#uploadImages").click(function() {
					$j("#body_left").load($j(this).attr("href"));
					Modalbox.hide();
					return false;
				});
				
				$j("#close").click(function() {
					Modalbox.hide();
					return false;
				});
			});
		</script>
		
	</security:authorize>