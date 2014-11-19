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
	
	<div class="courseStudentsTitle">Course Students</div>

	<c:choose>
		<c:when test="${not empty courseStudents}">
			<table id="csTable">
				<tr class="titleRow">
					<th class="col0" style="cursor: pointer;" title="Click to select/unselect all visible">Select</th>
					<th columnid="0" class="col1 sortableColumn ${command.orderByTableField == 0 ? (command.ascendingOrder ? 'sorting_asc' : 'sorting_desc') : 'sorting'}">Student</th>
					<th columnid="1" class="col2 sortableColumn ${command.orderByTableField == 1 ? (command.ascendingOrder ? 'sorting_asc' : 'sorting_desc') : 'sorting'}">Account</th>
					<th class="col3">Automatic<br/>subscription</th>
				</tr>
				<c:forEach items="${courseStudents}" var="student">
					<tr>
						<td><input id="select_${student.account}" class="selectStudent" type="checkbox" /></td>
						<td>${student.name}</td>
						<td>${student.account}</td>
						<td>
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
			
			<div id="courseStudentsPaginate">
				<bia:paginator page="${courseStudentsPage}" url="${ShowCourseStudentsPageURL}"
	   				thisPageAlias="pageNumber" totalPagesAlias="pageTotal" elementsForPageAlias="peopleForPage"
	   				buttonClass="paginateButton" activeButtonClass="paginateActive" />
			</div>
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
					var otherParams = "";
					if ($j("#courseStudentsOrderByTableField").val() != null || $j("#courseStudentsOrderByTableField").val() != "") {
						otherParams += "&orderByTableField=" + $j("#courseStudentsOrderByTableField").val();
					}
					if ($j("#courseStudentsAscendingOrder").val() != null || $j("#courseStudentsAscendingOrder").val() != "") {
						otherParams += "&ascendingOrder=" + $j("#courseStudentsAscendingOrder").val();
					}
					
					$j("#courseStudentsTable").load($j(this).attr('href') + otherParams, function(responseText, statusText, xhr) {
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
				$j(".waitingModal").show();
				
				var columnIdx = $j(this).attr('columnid');
				var columnAscendingOrder = columnIdx === csServerColumnIdx ? (csServerAscending == null ? true : (csServerAscending == true ? false : null)) : true;
				$j("#courseStudentsOrderByTableField").val(columnAscendingOrder != null ? columnIdx : null);
				$j("#courseStudentsAscendingOrder").val(columnAscendingOrder);
				
				var url = '${ShowCourseStudentsPageURL}' + "&" + $j("#courseStudentsForm").serialize();
				$j("#courseStudentsTable").load(url, function(responseText, statusText, xhr) {
					$j(".waitingModal").hide();
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
			
			$j("#csTable .subscription").change(function() {
				$j(".waitingModal").show();
				
				var inputId = $j(this).attr('id');
				var account = inputId.substring(inputId.indexOf('_') + 1);
				
				$j.ajax({
					url: '${ToggleCourseSubscriptionURL}' + '&account=' + account,
					cache: false,
					async: false,
					type: "POST",
					success: function(json) {
   						$j(".waitingModal").hide();
    					if (json.operation === "KO") {
    						alert("Operation failed...please contact the admin");
    						$j("#courseStudentsTable").load($j("#courseStudentsPaginate .paginateActive").attr('href'));
    					}
    				},
    				error: function() {
    					$j(".waitingModal").hide();
    					alert("Server error...please contact the admin");
    					$j("#courseStudentsTable").load($j("#courseStudentsPaginate .paginateActive").attr('href'));
    				}
				});
				
				return false;
			});
			
			$j("#csTable .titleRow .col0").click(function() {
				var numRows = $j("#csTable .selectStudent").length;
				var numSelected = $j("#csTable input.selectStudent:checkbox:checked").length;
				if (numRows > numSelected) {
					$j("#csTable .selectStudent").each(function() {
						$j(this).attr("checked", "checked");
					});
					$j("#moveToOtherStudents").removeClass('buttonDownDisabled');
					$j("#moveToOtherStudents").addClass('buttonDown');
				} else {
					$j("#csTable .selectStudent").each(function() {
						$j(this).removeAttr("checked");
					});
					$j("#moveToOtherStudents").removeClass('buttonDown');
					$j("#moveToOtherStudents").addClass('buttonDownDisabled');
				}
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