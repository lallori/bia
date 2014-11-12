<%@ taglib prefix="bia" uri="http://bia.medici.org/jsp:jstl" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>

	<c:url var="ShowCourseStudentsPageURL" value="/teaching/ShowCourseStudents.do">
		<c:param name="courseId" value="${command.courseId}" />
	</c:url>

	<c:url var="ToggleCourseSubscriptionURL" value="/teaching/toggleCourseSubscription.json">
		<c:param name="courseId" value="${command.courseId}" />
	</c:url>
	
	<h3>Course Students</h3>

	<c:choose>
		<c:when test="${not empty courseStudents}">
			<div id="courseStudentsPaginate" style="text-align: right; width: 100%;">
				<bia:paginator page="${courseStudentsPage}" url="${ShowCourseStudentsPageURL}"
	   				thisPageAlias="pageNumber" totalPagesAlias="pageTotal" elementsForPageAlias="peopleForPage"
	   				buttonClass="paginateButton" activeButtonClass="paginateActive" />
			</div>
			
			<table id="csTable" style="width: 100%;" rules="cols">
				<tr style="height: 45px; border: 1px solid;">
					<th style="width: 12%;">Select</th>
					<th style="width: 40%;" columnid="0" style="cursor:pointer;" class="sortableColumn ${command.orderByTableField == 0 ? (command.ascendingOrder ? 'sorting_asc' : 'sorting_desc') : 'sorting'}">Student</th>
					<th style="width: 29%;" columnid="1" style="cursor:pointer;" class="sortableColumn ${command.orderByTableField == 1 ? (command.ascendingOrder ? 'sorting_asc' : 'sorting_desc') : 'sorting'}">Account</th>
					<th style="width: 29%;">Automatic<br/>subscription</th>
				</tr>
				<c:forEach items="${courseStudents}" var="student">
					<tr>
						<td style="text-align: center;"><input id="select_${student.account}" class="selectStudent" type="checkbox" /></td>
						<td style="text-align: center;">${student.name}</td>
						<td style="text-align: center;">${student.account}</td>
						<td style="text-align: center;">
							<c:choose>
								<c:when test="${student.subscription}">
									<input id="subscription_${student.account}" class="subscription" type="checkbox" checked="checked" value="${student.subscription}" />
								</c:when>
								<c:otherwise>
									<input id="subscription_${student.account}" class="subscription" type="checkbox" value="${student.subscription}" />
								</c:otherwise>
							</c:choose>
						</td>
					</tr>
				</c:forEach>
			</table>
		</c:when>
		<c:otherwise>
			There are no students in this course.
		</c:otherwise>
	</c:choose>
	
	<script type="text/javascript">
		$j(document).ready(function() {
			
			<c:if test="${not empty courseStudents}">
				$j("#moveAllToOtherStudents").show();
			</c:if>
			
			$j("#courseStudentsPaginate .paginateButton").die();
			$j("#courseStudentsPaginate .paginateButton").click(function() {
				if (typeof $j(this).attr('href') !== 'undefined') {
					$j("#courseStudentsTable").load($j(this).attr('href'), function(responseText, statusText, xhr) {
						var _this = $j(this);
						if (statusText !== 'error') {
							// TODO
						} else {
							// TODO: handle error
						}
					});
				}
				return false;
			});
			
			<c:choose>
				<c:when test="${not empty command.ascendingOrder}">
					var csServerAscending = ${command.ascendingOrder};
				</c:when>
				<c:otherwise>
					var csServerAscending = null;
				</c:otherwise>
			</c:choose>
			
			<c:choose>
				<c:when test="${not empty command.orderByTableField}">
					var csServerColumnIdx = '${command.orderByTableField}';
				</c:when>
				<c:otherwise>
					var csServerColumnIdx = -1;
				</c:otherwise>
			</c:choose>
			
			$j("#csTable .sortableColumn").die();
			$j("#csTable .sortableColumn").click(function() {
				$j("#loadingDiv").show();
				
				var columnIdx = $j(this).attr('columnid');
				var columnAscendingOrder = columnIdx === osServerColumnIdx ? (osServerAscending == null ? true : (osServerAscending == true ? false : null)) : true;
				$j("#courseStudentsOrderByTableField").val(columnIdx);
				$j("#courseStudentsAscendingOrder").val(columnAscendingOrder);
				
				var url = '${ShowCourseStudentsPageURL}' + "?" + $j("#courseStudentsForm").serialize();
				$j("#courseStudentsTable").load(url, function(responseText, statusText, xhr) {
					$j("#loadingDiv").hide();
					if (statusText === 'error') {
						alert('Server error...if problem persists please contact the admin!');
					}
				});
				return false;
			});
			
			$j("#csTable .selectStudent").die();
			$j("#csTable .selectStudent").change(function() {
				var checkedNumber = $j("#csTable input.selectStudent:checkbox:checked").length;
				if (checkedNumber > 0) {
					$j("#moveToOtherStudents").removeClass('buttonDownDisabled');
					$j("#moveToOtherStudents").addClass('buttonDown');
				} else if (!$j("#moveToOtherStudents").hasClass('buttonDownDisabled')) {
					$j("#moveToOtherStudents").removeClass('buttonDown');
					$j("#moveToOtherStudents").addClass('buttonDownDisabled');
				}
			});
			
			$j(".subscription").change(function() {
				$j("#loadingDiv").show();
				
				var inputId = $j(this).attr('id');
				var account = inputId.substring(inputId.indexOf('_') + 1);
				
				$j.ajax({
					url: '${ToggleCourseSubscriptionURL}' + '&account=' + account,
					cache: false,
					async: false,
					type: "POST",
					success: function(json) {
   						$j("#loadingDiv").hide();
    					if (json.operation === "KO") {
    						alert("Operation failed...please contact the admin");
    						$j("#courseStudentsTable").load($j("#courseStudentsPaginate .paginateActive").attr('href'));
    					}
    				},
    				error: function() {
    					$j("#loadingDiv").hide();
    					alert("Server error...please contact the admin");
    					$j("#courseStudentsTable").load($j("#courseStudentsPaginate .paginateActive").attr('href'));
    				}
				});
				
				return false;
			});
			
			// The following lines to update paginator filters of the ShowManageCoursePeople.jsp
			<c:if test="${not empty command.firstRecord}">
				$j("#courseStudentsFirstRecord").val(${command.firstRecord});
			</c:if>
			<c:if test="${not empty command.pageNumber}">
				$j("#courseStudentsPageNumber").val(${command.pageNumber});
			</c:if>
			<c:if test="${not empty command.pageTotal}">
				$j("#courseStudentsPageTotal").val(${command.pageTotal});
			</c:if>
			<c:if test="${not empty command.peopleForPage}">
				$j("#courseStudentsForPage").val(${command.peopleForPage});
			</c:if>
			<c:if test="${not empty command.orderByTableField}">
				$j("#courseStudentsOrderByTableField").val(${command.orderByTableField});
			</c:if>
			<c:if test="${not empty command.ascendingOrder}">
				$j("#courseStudentsAscendingOrder").val(${command.ascendingOrder});
			</c:if>
		});
	</script>