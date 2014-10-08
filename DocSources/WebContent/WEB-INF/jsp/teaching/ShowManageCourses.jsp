<%@ taglib prefix="bia" uri="http://bia.medici.org/jsp:jstl" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>

	<c:url var="ShowManageCoursesURL" value="/teaching/ShowManageCourses.do" />
	
	<c:choose>
		<c:when test="${command.showActives}">
			<div>
				<h2 style="display: inline-block; margin-right: 40px;">Active courses</h2>
				<a class="showButton button_large" href="${ShowManageCoursesURL}?showActives=false">Show All</a>
			</div>
		</c:when>
		<c:otherwise>
			<div>
				<h2 style="display: inline-block; margin-right: 40px;">All courses</h2>
				<a class="showButton button_large" href="${ShowManageCoursesURL}?showActives=true">Show Only Active</a>
			</div>
		</c:otherwise>
	</c:choose>
	
	<div id="forumPaginate_upper" style="text-align: right; width: 100%;">
		<c:url var="ShowManageCourseExtendedURL" value="/teaching/ShowManageCourses.do">
			<c:param name="showActives" value="${not empty command.showActives ? command.showActives : 'false'}" />
			<c:if test="${not empty command.orderByTableField}">
				<c:param name="orderByTableField" value="${command.orderByTableField}" />
				<c:param name="ascendingOrder" value="${command.ascendingOrder}" />
			</c:if>
		</c:url>
		<bia:paginator page="${coursesPage}" url="${ShowManageCourseExtendedURL}"
			thisPageAlias="thisPage" totalPagesAlias="total" elementsForPageAlias="elementsForPage" 
			pageViewId="body_left" buttonClass="paginateButton" activeButtonClass="paginateActive" useInternalCSS="true"/>
	</div>
	
	<hr style="width: 100%"/>
	
	<c:choose>
		<c:when test="${not empty coursesPage.list}">
		
			<c:url var="ShowManagedCourseForOrderingURL" value="/teaching/ShowManageCourses.do">
				<c:param name="showActives" value="${not empty command.showActives ? command.showActives : 'false'}" />
				<c:param name="thisPage" value="${coursesPage.thisPage}" />
				<c:param name="total" value="${coursesPage.total}" />
				<c:param name="elementsForPage" value="${coursesPage.elementsForPage}" />
			</c:url>
			
			<c:url var="ShowManageCoursesWithOrderingURL" value="/teaching/ShowManageCourses.do">
				<c:param name="showActives" value="${not empty command.showActives ? command.showActives : 'false'}" />
				<c:param name="thisPage" value="${coursesPage.thisPage}" />
				<c:param name="total" value="${coursesPage.total}" />
				<c:param name="elementsForPage" value="${coursesPage.elementsForPage}" />
				<c:if test="${not empty command.orderByTableField}">
					<c:param name="orderByTableField" value="${command.orderByTableField}" />
					<c:param name="ascendingOrder" value="${command.ascendingOrder}" />
				</c:if>
			</c:url>
			
			<table style="width: 100%;">
				<tbody>
					<tr>
						<th width="10%" columnid="0" style="cursor:pointer;" class="sortableColumn ${command.orderByTableField == 0 ? (command.ascendingOrder ? 'sorting_asc' : 'sorting_desc') : 'sorting'}">#</th>
						<th width="25%" columnid="1" style="cursor:pointer;" class="sortableColumn ${command.orderByTableField == 1 ? (command.ascendingOrder ? 'sorting_asc' : 'sorting_desc') : 'sorting'}">Created on</th>
						<th width="40%" columnid="2" style="cursor:pointer;" class="sortableColumn ${command.orderByTableField == 2 ? (command.ascendingOrder ? 'sorting_asc' : 'sorting_desc') : 'sorting'}">Title</th>
						<th width="25%">Actions</th>
					</tr>
					<c:forEach items="${coursesPage.list}" var="course">
						<tr style="margin-bottom: 10px;">
							<td width="10%" style="text-align: center;">${course.courseId}</td>
							<td width="25%" style="text-align: center;"><fmt:formatDate pattern="MM/dd/yyyy" value="${course.forum.dateCreated}" /></td>
							<td width="40%">${course.forum.title}</td>
							<td width="25%" style="text-align: center;">
								<c:choose>
									<c:when test="${course.active}">
										<c:url var="DeactivateCourseURL" value="/teaching/deactivateCourse.json">
											<c:param name="courseId" value="${course.courseId}" />
										</c:url>
										<a href="${DeactivateCourseURL}" style="display: inline-block;" class="activeLink">
											<img title="The course is active: click to deactivate it!" src="<c:url value='/images/1024/img_active.gif'/>" /> 
										</a>
									</c:when>
									<c:otherwise>
										<c:url var="ActiveCourseURL" value="/teaching/doActivateCourse.json">
											<c:param name="courseId" value="${course.courseId}" />
										</c:url>
										<a href="${ActiveCourseURL}" style="display: inline-block;" class="activeLink">
											<img title="The course is not active: click to re-activate it!" src="<c:url value='/images/1024/img_closed.gif'/>" />
										</a>
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
	
	<script type="text/javascript">
		$j(document).ready(function() {
		
			<c:choose>
				<c:when test="${not empty command.ascendingOrder}">
					var serverAscending = ${command.ascendingOrder};
				</c:when>
				<c:otherwise>
					var serverAscending = null;
				</c:otherwise>
			</c:choose>
			
			<c:choose>
				<c:when test="${not empty command.orderByTableField}">
					var serverColumnIdx = '${command.orderByTableField}';
				</c:when>
				<c:otherwise>
					var serverColumnIdx = -1;
				</c:otherwise>
			</c:choose>
		
			$j(".showButton").click(function() {
				$j("#body_left").load($j(this).attr("href")); 
				return false;
			});
			
			$j(".sortableColumn").click(function() {
				var columnIdx = $j(this).attr('columnid');
				var columnAscendingOrder = columnIdx === serverColumnIdx ? (serverAscending == null ? true : (serverAscending == true ? false : null)) : true;  
				var url = '${ShowManagedCourseForOrderingURL}';
				if (columnAscendingOrder != null) {
					url += '&orderByTableField=' + columnIdx + '&ascendingOrder=' + columnAscendingOrder;
				}
				$j("#body_left").load(url, function(responseText, statusText, xhr) {
					if (statusText === 'error') {
						alert('Server error...if problem persists please contact the admin!');
					}
				});
				return false;
			});
			
			$j(".activeLink").click(function() {
				$j.ajax({
					url: $j(this).attr("href"),
					cache: false,
					type: "POST",
					success: function(json) { 
    					if (json.operation = "OK") {
 							$j("#body_left").load('${ShowManageCoursesWithOrderingURL}');
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