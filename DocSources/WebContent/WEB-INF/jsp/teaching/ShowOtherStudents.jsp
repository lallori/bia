<%@ taglib prefix="bia" uri="http://bia.medici.org/jsp:jstl" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
	
	<c:url var="ShowOtherStudentsPageURL" value="/teaching/ShowOtherStudents.do">
		<c:param name="courseId" value="${command.courseId}" />
	</c:url>
	
	<h3>Other Students</h3>
	
	<c:choose>
		<c:when test="${not empty studentsPage.list}">
			<div id="otherStudentsPaginate" style="text-align: right; width: 100%;">
				<bia:paginator page="${studentsPage}" url="${ShowOtherStudentsPageURL}"
	   				thisPageAlias="pageNumber" totalPagesAlias="pageTotal" elementsForPageAlias="peopleForPage"
	   				buttonClass="paginateButton" activeButtonClass="paginateActive" />
			</div>
			
			<table id="osTable">
				<tbody>
					<tr>
						<th style="width: 20%;">Select</th>
						<th style="width: 30%;" columnid="0" style="cursor:pointer;" class="sortableColumn ${command.orderByTableField == 0 ? (command.ascendingOrder ? 'sorting_asc' : 'sorting_desc') : 'sorting'}">First Name</th>
						<th style="width: 30%;" columnid="1" style="cursor:pointer;" class="sortableColumn ${command.orderByTableField == 1 ? (command.ascendingOrder ? 'sorting_asc' : 'sorting_desc') : 'sorting'}">Last Name</th>
						<th style="width: 20%;" columnid="2" style="cursor:pointer;" class="sortableColumn ${command.orderByTableField == 2 ? (command.ascendingOrder ? 'sorting_asc' : 'sorting_desc') : 'sorting'}">Account</th>
					</tr>
					<c:forEach items="${studentsPage.list}" var="student">
						<tr>
							<td><input id="select_${student.account}" class="selectStudent" type="checkbox" /></td>
							<td>${student.firstName}</td>
							<td>${student.lastName}</td>
							<td>${student.account}</td>
						</tr>
					</c:forEach>
				</tbody>
			</table>
		</c:when>
		<c:otherwise>
			There are no other students out of this course.
		</c:otherwise>
	</c:choose>
	
	<script type="text/javascript">
		$j(document).ready(function() {
			
			<c:if test="${studentsPage.total > 0}">
				$j("#moveAllToCourseStudents").show();
			</c:if>
			
			$j("#otherStudentsPaginate .paginateButton").die();
			$j("#otherStudentsPaginate .paginateButton").click(function() {
				if (typeof $j(this).attr('href') !== 'undefined') {
					$j("#otherStudentsTable").load($j(this).attr('href'), function(responseText, statusText, xhr) {
						if (statusText !== 'error') {
							$j("#moveToCourseStudents").removeClass('buttonUp');
							$j("#moveToCourseStudents").addClass('buttonUpDisabled');
						} else {
							// TODO: handle error
						}
					});
				}
				return false;
			});
			
			<c:choose>
				<c:when test="${not empty command.ascendingOrder}">
					var osServerAscending = ${command.ascendingOrder};
				</c:when>
				<c:otherwise>
					var osServerAscending = null;
				</c:otherwise>
			</c:choose>
			
			<c:choose>
				<c:when test="${not empty command.orderByTableField}">
					var osServerColumnIdx = '${command.orderByTableField}';
				</c:when>
				<c:otherwise>
					var osServerColumnIdx = -1;
				</c:otherwise>
			</c:choose>
			
			$j("#osTable .sortableColumn").die();
			$j("#osTable .sortableColumn").click(function() {
				$j("#loadingDiv").show();
				
				var columnIdx = $j(this).attr('columnid');
				var columnAscendingOrder = columnIdx === osServerColumnIdx ? (osServerAscending == null ? true : (osServerAscending == true ? false : null)) : true;
				$j("#otherStudentsOrderByTableField").val(columnIdx);
				$j("#otherStudentsAscendingOrder").val(columnAscendingOrder);
				
				var url = '${ShowOtherStudentsPageURL}' + "?" + $j("#otherStudentsForm").serialize();
				$j("#otherStudentsTable").load(url, function(responseText, statusText, xhr) {
					$j("#loadingDiv").hide();
					if (statusText === 'error') {
						alert('Server error...if problem persists please contact the admin!');
					}
				});
				return false;
			});
			
			$j("#osTable .selectStudent").die();
			$j("#osTable .selectStudent").change(function() {
				var checkedNumber = $j("#osTable input.selectStudent:checkbox:checked").length;
				if (checkedNumber > 0) {
					$j("#moveToCourseStudents").removeClass('buttonUpDisabled');
					$j("#moveToCourseStudents").addClass('buttonUp');
				} else if (!$j("#moveToCourseStudents").hasClass('buttonUpDisabled')) {
					$j("#moveToCourseStudents").removeClass('buttonUp');
					$j("#moveToCourseStudents").addClass('buttonUpDisabled');
				}
			});
			
			
			// The following lines to update paginator filters of the ShowManageCoursePeople.jsp
			<c:if test="${not empty command.firstRecord}">
				$j("#otherStudentsFirstRecord").val(${command.firstRecord});
			</c:if>
			<c:if test="${not empty command.pageNumber}">
				$j("#otherStudentsPageNumber").val(${command.pageNumber});
			</c:if>
			<c:if test="${not empty command.pageTotal}">
				$j("#otherStudentsPageTotal").val(${command.pageTotal});
			</c:if>
			<c:if test="${not empty command.peopleForPage}">
				$j("#otherStudentsForPage").val(${command.peopleForPage});
			</c:if>
			<c:if test="${not empty command.orderByTableField}">
				$j("#otherStudentsOrderByTableField").val(${command.orderByTableField});
			</c:if>
			<c:if test="${not empty command.ascendingOrder}">
				$j("#otherStudentsAscendingOrder").val(${command.ascendingOrder});
			</c:if>
		});
	</script>