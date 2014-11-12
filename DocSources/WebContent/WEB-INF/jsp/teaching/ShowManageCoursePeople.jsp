<%@ taglib prefix="bia" uri="http://bia.medici.org/jsp:jstl" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>

	<c:url var="ShowManageCoursePeopleURL" value="/teaching/ShowManageCoursePeople.do">
		<c:param name="courseId" value="${command.courseId}" />
	</c:url>

	<c:url var="ShowCourseStudentsURL" value="/teaching/ShowCourseStudents.do">
		<c:param name="courseId" value="${command.courseId}" />
		<c:param name="firstRecord" value="${command.courseStudentsFirstRecord}" />
		<c:param name="pageNumber" value="${command.courseStudentsPageNumber}" />
		<c:param name="pageTotal" value="${command.courseStudentsPageTotal}" />
		<c:param name="peopleForPage" value="${command.courseStudentsForPage}" />
		<c:param name="orderByTableField" value="${command.courseStudentsOrderByTableField}" />
		<c:param name="ascendingOrder" value="${command.courseStudentsAscendingOrder}" />
	</c:url>
	
	<c:url var="ShowOtherStudentsURL" value="/teaching/ShowOtherStudents.do">
		<c:param name="courseId" value="${command.courseId}" />
		<c:param name="firstRecord" value="${command.otherStudentsFirstRecord}" />
		<c:param name="pageNumber" value="${command.otherStudentsPageNumber}" />
		<c:param name="pageTotal" value="${command.otherStudentsPageTotal}" />
		<c:param name="peopleForPage" value="${command.otherStudentsForPage}" />
		<c:param name="orderByTableField" value="${command.otherStudentsOrderByTableField}" />
		<c:param name="ascendingOrder" value="${command.otherStudentsAscendingOrder}" />
	</c:url>
	
	<c:url var="MoveStudentsToCourseURL" value="/teaching/MoveStudentsToCourse.json">
		<c:param name="courseId" value="${command.courseId}" />
	</c:url>
	
	<c:url var="RemoveCoursePeopleURL" value="/teaching/RemoveCoursePeople.json">
		<c:param name="courseId" value="${command.courseId}" />
	</c:url>
	
	<c:url var="MoveAllOtherStudentsToCourseURL" value="/teaching/MoveAllOtherStudentsToCourse.json">
		<c:param name="courseId" value="${command.courseId}" />
	</c:url>
	
	<c:url var="RemoveAllCoursePeopleURL" value="/teaching/RemoveAllCoursePeople.json">
		<c:param name="courseId" value="${command.courseId}" />
	</c:url>

	<div id="courseTitle">${courseTitle}</div>
	
	<div id="courseStudentsSection">
		<form id="courseStudentsForm" style="display: none;">
			<input type="hidden" id="courseStudentsFirstRecord" name="courseStudentsFirstRecord" value="${command.courseStudentsFirstRecord}" />
			<input type="hidden" id="courseStudentsPageNumber" name="courseStudentsPageNumber" value="${command.courseStudentsPageNumber}" />
			<input type="hidden" id="courseStudentsPageTotal" name="courseStudentsPageTotal" value="${command.courseStudentsPageTotal}" />
			<input type="hidden" id="courseStudentsForPage" name="courseStudentsForPage" value="${command.courseStudentsForPage}" />
			<input type="hidden" id="courseStudentsOrderByTableField" name="courseStudentsOrderByTableField" value="${command.courseStudentsOrderByTableField}" />
			<input type="hidden" id="courseStudentsAscendingOrder" name="courseStudentsAscendingOrder" value="${command.courseStudentsAscendingOrder}" />
		</form>
		
		<div id="courseStudentsError" style="display: none;">
			<span style="color: red; display: block;">There was a problem during the &quot;course students table&quot; load.</span>
			<a href="#" id="retryCourseStudentsButton" class="buttonSmall">Retry</a>
		</div>
		<div id="courseStudentsTable"></div>
	</div>
	
	<hr style="width: 100%;" />
	
	<div id="manageCoursePeopleCommands" style="text-align: center;">
		<a href="#" id="moveToCourseStudents" class="buttonUpDisabled" title="Click to move only the selected 'other' students to the course"></a>
		<a href="#" id="moveToOtherStudents" class="buttonDownDisabled" title="Click to remove the selected course students (from the upper table)"></a>
		<a href="#" id="moveAllToCourseStudents" style="display: none;" class="button_medium" title="Click to move all 'other' students to the course">Move All Up</a>
		<a href="#" id="moveAllToOtherStudents" style="display: none;" class="button_medium" title="Click to remove all course students">Move All Down</a>
	</div>
	
	<hr style="width: 100%;" />
	
	<div id="otherStudentsSection">
		<form id="otherStudentsForm" style="display: none;">
			<input type="hidden" id="otherStudentsFirstRecord" name="otherStudentsFirstRecord" value="${command.otherStudentsFirstRecord}" />
			<input type="hidden" id="otherStudentsPageNumber" name="otherStudentsPageNumber" value="${command.otherStudentsPageNumber}" />
			<input type="hidden" id="otherStudentsPageTotal" name="otherStudentsPageTotal" value="${command.otherStudentsPageTotal}" />
			<input type="hidden" id="otherStudentsForPage" name="otherStudentsForPage" value="${command.otherStudentsForPage}" />
			<input type="hidden" id="otherStudentsOrderByTableField" name="otherStudentsOrderByTableField" value="${command.otherStudentsOrderByTableField}" />
			<input type="hidden" id="otherStudentsAscendingOrder" name="otherStudentsAscendingOrder" value="${command.otherStudentsAscendingOrder}" />
		</form>
		
		<div id="otherStudentsError" style="display: none;">
			<span style="color: red; display: block;">There was a problem during the &quot;other students table&quot; load.</span>
			<a href="#" id="retryOtherStudentsButton" class="buttonSmall">Retry</a>
		</div>
		
		<div id="otherStudentsTable"></div>
	</div>
	
	<script type="text/javascript">
		$j(document).ready(function() {
			
			// append the loading div
			if ($j("#loadingDiv").length === 0) {
				$j("#body_left").append("<div id='loadingDiv' style='display: none; width: 100%; height: 100%;'></div>");
			}
			
			$j("#courseStudentsTable").load('${ShowCourseStudentsURL}', function(responseText, statusText, xhr) {
				if (statusText == 'error') {
					$j("#courseStudentsError").show();
				}
			});
			
			$j("#otherStudentsTable").load('${ShowOtherStudentsURL}', function(responseText, statusText, xhr) {
				if (statusText == 'error') {
					$j("#otherStudentsError").show();
				}
			});
			
			$j("#retryCourseStudentsButton").click(function() {
				$j("#courseStudentsTable").load('${ShowCourseStudentsURL}', function(responseText, statusText, xhr) {
					if (statusText !== 'error') {
						$j("#courseStudentsError").hide();
					}
				});
				return false;
			});
			
			$j("#retryOtherStudentsButton").click(function() {
				$j("#otherStudentsTable").load('${ShowOtherStudentsURL}', function(responseText, statusText, xhr) {
					if (statusText !== 'error') {
						$j("#otherStudentsError").hide();
					}
				});
				return false;
			});
			
			$j("#moveAllToCourseStudents").die();
			$j("#moveAllToCourseStudents").click(function() {
				if ($j("#otherStudentsTable input").length == 0) {
					return false;
				}
				
				manageCourseStudentsCallback('${MoveAllOtherStudentsToCourseURL}');
				
				return false;
			});
			
			$j("#moveAllToOtherStudents").die();
			$j("#moveAllToOtherStudents").click(function() {
				if ($j("#courseStudentsTable input").length == 0) {
					return false;
				}
				
				manageCourseStudentsCallback('${RemoveAllCoursePeopleURL}');
				
				return false;
			});
			
			$j("#moveToOtherStudents").die();
			$j("#moveToOtherStudents").click(function() {
				debugger;
				if ($j(this).hasClass('buttonDownDisabled')) {
					return false;
				}
				
				$j("#loadingDiv").show();
				
				var accounts = "";
				$j("#courseStudentsTable input.selectStudent:checkbox:checked").each(function() {
					var inputId = $j(this).attr('id');
					var account = inputId.substring(inputId.indexOf('_') + 1);
					if (accounts === "") {
						accounts = account;
					} else {
						accounts += "," + account;
					}
				});
				
				manageCourseStudentsCallback('${RemoveCoursePeopleURL}' + '&accounts=' + accounts);
				
				return false;
			});
			
			
			$j("#moveToCourseStudents").die();
			$j("#moveToCourseStudents").click(function() {
				debugger;
				if ($j(this).hasClass('buttonUpDisabled')) {
					return false;
				}
				
				$j("#loadingDiv").show();
				
				var accounts = "";
				$j("#otherStudentsTable input.selectStudent:checkbox:checked").each(function() {
					var inputId = $j(this).attr('id');
					var account = inputId.substring(inputId.indexOf('_') + 1);
					if (accounts === "") {
						accounts = account;
					} else {
						accounts += "," + account;
					}
				});
				
				manageCourseStudentsCallback('${MoveStudentsToCourseURL}' + '&accounts=' + accounts);
				
				return false;
			});
			
			function manageCourseStudentsCallback($url) {
				console.log($url);
				$j.ajax({
					url: $url,
					cache: false,
					async: false,
					dataType: 'json',
					type: "POST",
					success: function(json) { 
   						$j("#loadingDiv").hide();
    					if (json.operation === "OK") {
    						$j("#body_left").load('${ShowManageCoursePeopleURL}');
    					} else {
    						alert("Operation failed...please contact the admin");
    					}
    				},
    				error: function() {
    					$j("#loadingDiv").hide();
    					alert("Server error...please contact the admin");
    				}
				});
			}
			
		});
	</script>