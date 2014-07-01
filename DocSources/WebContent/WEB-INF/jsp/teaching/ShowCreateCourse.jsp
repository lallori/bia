<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>

	<c:url var="CreateCourseURL" value="/teaching/ShowCreateCourse.do" />
	
	<c:url var="ShowActiveURL" value="/teaching/ShowManageCourses.do">
		<c:param name="showActives" value="true" />
	</c:url>
	
	<c:choose>
		<c:when test="${empty course}">
			<form:form id="createCourseForm" method="post" cssClass="edit">
				<fieldset>
					<legend><b>NEW COURSE</b></legend>
					<div class="listForm">
						<div class="row">
							<div class="col_r"><form:label path="courseTitle" for="courseTitle">Title</form:label></div>
							<div class="col_l"><form:input id="courseTitle" path="courseTitle" cssClass="input_25c"/></div>	
						</div>
						<div class="row" id="formError" style="display: none;">
							<div class="col_r"></div>
							<div class="col_l" style="color: red;">Course Title must be not empty!</div>
						</div>
						<div class="row">
							<div class="col_r"><form:label path="courseDescription" for="courseDescription">Description</form:label></div>
							<div class="col_l"><form:input id="courseDescription" path="courseDescription" cssClass="input_25c"/></div>	
						</div>
					</div>
					<input class="button_small" type="submit" value="Create" />
				</fieldset>
			</form:form>
		</c:when>
		<c:otherwise>
			<h2>Your course <i>${course.forum.title}</i> has been created!</h2>
			<a id="showCoursesButton" class="button_medium" style="display: inline-block;" href="${ShowActiveURL}">Show Courses</a>
			<a id="createCourseButton" class="button_medium" style="display: inline-block;" href="${CreateCourseURL}">Create another course</a>
		</c:otherwise>
	</c:choose>
	
	
	
	<script>
		$j(document).ready(function() {
			
			function isEmpty(input) {
				var onlySpaces = /^ * *$/;
				return input.match(onlySpaces);
			};
			
			<c:choose>
				<c:when test="${empty course}">
					$j("#createCourseForm").submit(function() {
						if (isEmpty($j("#courseTitle").val())) {
							$j("#formError").show();
							return false;
						}
						if (isEmpty($j("#courseDescription").val())) {
							$j("#courseDescription").val($j("#courseTitle").val());
						}
						
						$j.ajax({
							url: '${CreateCourseURL}',
							data: $j(this).serialize(),
							type: 'POST',
							cache: false, 
							success: function(html) { 
		    					$j("#body_left").html(html);
		    				},
		    				error: function() {
		    					alert('Server error...please contact the admin!');
		    				}
						});
						
						return false;
						
					});
				</c:when>
				<c:otherwise>
					$j("#showCoursesButton").click(function() {
						$j("#body_left").load($j(this).attr("href"));
						return false;
					});
					
					$j("#createCourseButton").click(function() {
						$j("#body_left").load($j(this).attr("href"));
						return false;
					});
				</c:otherwise>
			</c:choose>
			
		});
	</script>