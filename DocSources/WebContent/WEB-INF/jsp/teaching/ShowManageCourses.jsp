<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>

	<c:url var="ShowAllURL" value="/teaching/ShowManageCourses.do">
		<c:param name="showActives" value="false" />
	</c:url>
	
	<c:url var="ShowActiveURL" value="/teaching/ShowManageCourses.do">
		<c:param name="showActives" value="true" />
	</c:url>
	
	<c:choose>
		<c:when test="${command.showActives == true}">
			<div>
				<h2 style="display: inline-block; margin-right: 40px;">Activate courses</h2>
				<a class="showButton button_large" href="${ShowAllURL}">Show All</a>
			</div>
		</c:when>
		<c:otherwise>
			<div>
				<h2 style="display: inline-block; margin-right: 40px;">All courses</h2>
				<a class="showButton button_large" href="${ShowActiveURL}">Show Only Actives</a>
			</div>
		</c:otherwise>
	</c:choose>
	
	<c:choose>
		<c:when test="${not empty courses}">
			<table style="width: 450px;">
				<tbody>
					<tr>
						<th width="10%">Course #</th>
						<th width="50%">Title</th>
						<th width="10%">Active (Y/N)</th>
						<th width="20%">Action</th>
					</tr>
					<c:forEach items="${courses}" var="course">
						<tr style="margin-bottom: 10px;">
							<td width="10%" style="text-align: center;">${course.courseId}</td>
							<td width="50%">${course.forum.title}</td>
							<td width="10%" style="text-align: center;">${course.active ? 'Y' : 'N'}</td>
							<td width="20%">
								<c:choose>
									<c:when test="${course.active == true}">
										<c:url var="deactivateCourseURL" value="/teaching/deactivateCourse.json">
											<c:param name="courseId" value="${course.courseId}" />
										</c:url>
										<a href="${deactivateCourseURL}" style="display: inline-block;" class="button_medium activeLink">Deactivate</a>
									</c:when>
									<c:otherwise>
										<c:url var="activeCourseURL" value="/teaching/doActivateCourse.json">
											<c:param name="courseId" value="${course.courseId}" />
										</c:url>
										<a href="${activeCourseURL}" style="display: inline-block;" class="button_medium activeLink">Activate</a>
									</c:otherwise>
								</c:choose>
							</td>
						</tr>
					</c:forEach>
				</tbody>
			</table>
		</c:when>
		<c:otherwise>
			No courses found
		</c:otherwise>
	</c:choose>
	
	<script>
		$j(document).ready(function() {
			
			$j(".showButton").click(function() {
				$j("#body_left").load($j(this).attr("href")); 
				return false;
			});
			
			$j(".activeLink").click(function() {
				$j.ajax({
					url: $j(this).attr("href"),
					cache: false,
					type: "POST",
					success: function(json) { 
    					if (json.operation = "OK") {
    						<c:choose>
    							<c:when test="${command.showActives == true}">
    								$j("#body_left").load('${ShowActiveURL}');
    							</c:when>
	    						<c:otherwise>
	    							$j("#body_left").load('${ShowAllURL}');
	    						</c:otherwise>
    						</c:choose>
    					} else {
    						alert("Operation failed...please contact the admin");
    					}
    				},
    				error: function() {
    					alert("Server error...please contact the admin");
    				}
				});
				return false;
			});
			
		});
	</script>