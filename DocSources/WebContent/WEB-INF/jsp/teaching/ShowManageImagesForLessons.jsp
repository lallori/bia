<%@ taglib prefix="bia" uri="http://bia.medici.org/jsp:jstl" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>

	<c:url var="ShowManageImagesForLessonsPageURL" value="/teaching/ShowManageImagesForLessons.do" />
	
	<c:url var="ShowUploadNewImagesURL" value="/teaching/ShowUploadNewImages.do" />
	
	<div id="imagesForLessonsTitleSection">
		<h3 style="display: inline-block;">Images For Lessons</h3>
		<c:choose>
			<c:when test="${not empty images.list}">
				<a id="uploadBtn" href="#" class="button_medium">Upload other images</a>
			</c:when>
			<c:otherwise>
				<a id="uploadBtn" href="#" class="button_medium">Upload images</a>
			</c:otherwise>
		</c:choose>
	</div>
	
	<hr style="width: 100%; margin-top: 10px;" />
	
	<form id="imagesOrderingForm">
		<div id="titleFilterSection">
			<label id="titleFilterLabel" for="titleFilter">Title filter:</label>
			<input id="titleFilter" name="filter" type="text" value="${command.filter}" />
			<a id="filterByTitleBtn" href="#" class="button_medium">Filter images</a>
			<a id="clearFilterBtn" href="#" class="button_medium">Clear filter</a>
		</div>
		<input id="imagesOrderByTableField" name="orderByTableField" type="hidden" value="${command.orderByTableField}" /> 
		<input id="imagesAscendingOrder" name="ascendingOrder" type="hidden" value="${command.ascendingOrder}" />
	</form>
	
	<c:choose>
		<c:when test="${not empty images.list}">
		
			<table id="imagesTable" style="width: 100%;">
				<tbody>
					<tr class="titleRow">
						<th columnid="0" class="col0 sortableColumn ${command.orderByTableField == 0 ? (command.ascendingOrder ? 'sorting_asc' : 'sorting_desc') : 'sorting'}">Image #</th>
						<th columnid="1" class="col1 sortableColumn ${command.orderByTableField == 1 ? (command.ascendingOrder ? 'sorting_asc' : 'sorting_desc') : 'sorting'}">Image Title</th>
						<th columnid="2" class="col2 sortableColumn ${command.orderByTableField == 2 ? (command.ascendingOrder ? 'sorting_asc' : 'sorting_desc') : 'sorting'}">Created on</th>
						<th columnid="3" class="col3">Actions</th>
					</tr>
					<c:forEach items="${images.list}" var="image">
						<c:url var="ShowImagePreviewURL" value="/teaching/ShowImagePreview.do">
							<c:param name="imageId" value="${image.imageId}" />
						</c:url>
						<c:url var="ShowCreateLessonFromImageURL" value="/teaching/ShowCreateLessonFromImageModalWindow.do">
							<c:param name="imageId" value="${image.imageId}" />
						</c:url>
						<tr>
							<td>${image.imageId}</td>
							<td>${image.imageTitle}</td>
							<td><fmt:formatDate pattern="MM/dd/yyyy HH:mm" value="${image.dateCreated}" /></td>
							<td>
								<a href="${ShowImagePreviewURL}" class="showPreview" imageId="${image.imageId}" title="Show Image Preview" />
								<a href="${ShowCreateLessonFromImageURL}" class="createLesson" imageId="${image.imageId}" title="Create new lesson with this image" />
							</td>
						</tr>
					</c:forEach>
				</tbody>
			</table>
			
			<div id="imagesPaginate">
				<bia:paginator page="${images}" url="${ShowManageImagesForLessonsPageURL}"
	   				thisPageAlias="pageNumber" totalPagesAlias="pageTotal" elementsForPageAlias="elementsForPage"
	   				buttonClass="paginateButton" activeButtonClass="paginateActive" />
			</div>
		</c:when>
		<c:otherwise>
			Uploaded images list is empty.
		</c:otherwise>
	</c:choose>
	
	<script type="text/javascript">
		$j(document).ready(function() {

			//XXX: remove all modals definied in ShowUploadNewImages.jsp due to a problem of jquery.dialogextend plugin
			$j(".ui-dialog").remove();
			
			if ($j("#body_left .waitingModal").length === 0) {
				$j("#body_left").append("<div class='waitingModal' style='display: none;'></div>");
			}
		
			$j("#filterByTitleBtn").die();
			$j("#filterByTitleBtn").click(function() {
				$j(".waitingModal").show();
				var url = null;
				if (typeof $j("#imagesPaginate .paginateActive").attr('href') !== 'undefined') {
					url = $j("#imagesPaginate .paginateActive").attr('href') + "&" + $j("#imagesOrderingForm").serialize();
				} else {
					url = '${ShowManageImagesForLessonsPageURL}' + '?' + $j("#imagesOrderingForm").serialize();
				}
				$j("#body_left").load(url, function(responseText, statusText, xhr) {
					if (statusText === 'error') {
						alert('Server error...if problem persists please contact the admin!');
					}
				});
				return false;
			});
			
			$j("#clearFilterBtn").die();
			$j("#clearFilterBtn").click(function() {
				var titleFilter = $j("#titleFilter").val();
				if (typeof $j(titleFilter) === 'undefined' || titleFilter.match(/^ * *$/)) {
					return false;
				}
				$j("#titleFilter").val('');
				$j("#filterByTitleBtn").click();
				return false;
			});

			$j(".createLesson").die();
			$j(".createLesson").click(function() {
				Modalbox.show($j(this).attr('href'), {title: "CREATE LESSON WITH IMAGE #" + $j(this).attr('imageId'), width: 470, height: 180});
				return false;
			});
			
			$j("#uploadBtn").die();
			$j("#uploadBtn").click(function() {
				$j("#body_left").load('${ShowUploadNewImagesURL}', function(responseText, statusText, xhr) {
					if (statusText === 'error') {
						alert('Server error...if problem persists please contact the admin!');
					}
				});
				return false;
			});

			$j("#imagesTable .showPreview").die();
			$j("#imagesTable .showPreview").click(function() {
				var imageId = $j(this).attr('imageId');
				var tabId = 'titleTab_imagePreview_' + imageId;
				var tabName = "<span id='" + tabId + "'>Image Preview #" + imageId + "</span>";

				// Check if the tab already exists
				var numTab = 0;
				var tabExist = false;
				$j("#tabs ul li a").each(function() {
					var sameId = $j(this).find("span").find("span");
					// MD: Declare variable toTest for fix problem with IE
					var toTest = "";
					toTest += this.text;
					if (!tabExist) {
						if (toTest != "") {
							numTab++;
						}
					}
					if (toTest === tabName || $j(sameId).attr("id") === tabId) {
						tabExist = true;
					}
				});

				if (!tabExist) {
					$j("#tabs").tabs("add", $j(this).attr("href"), tabName + "</span></a><span class=\"ui-icon ui-icon-close\" title=\"Close Tab\">Remove Tab");
					$j("#tabs").tabs("select", $j("#tabs").tabs("length")-1);
					return false;
				} else {
					$j("#tabs").tabs("select", numTab);
					$j('#tabs ul li').eq(numTab).data('loaded', false).find('a').attr('href', $j(this).attr("href"));
					$j("#tabs").tabs("load", numTab);
					return false;
				}
				
				return false;
			});
			
			$j("#imagesPaginate .paginateButton").die();
			$j("#imagesPaginate .paginateButton").click(function() {
				if (typeof $j(this).attr('href') !== 'undefined') {
					$j(".waitingModal").show();
					var url = $j(this).attr('href') + "&" + $j("#imagesOrderingForm").serialize();
					$j("#body_left").load(url, function(responseText, statusText, xhr) {
						if (statusText === 'error') {
							alert('Server error...if problem persists please contact the admin!');
						}
					});
				}
				return false;
			});
			
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
			
			$j("#imagesTable .sortableColumn").die();
			$j("#imagesTable .sortableColumn").click(function() {
				$j(".waitingModal").show();
				
				var columnIdx = $j(this).attr('columnid');
				var columnAscendingOrder = columnIdx === serverColumnIdx ? (serverAscending == null ? true : (serverAscending == true ? false : null)) : true;
				$j("#imagesOrderByTableField").val(columnAscendingOrder != null ? columnIdx : null);
				$j("#imagesAscendingOrder").val(columnAscendingOrder);
				
				var url = $j("#imagesPaginate .paginateActive").attr('href') + "&" + $j("#imagesOrderingForm").serialize();
				$j("#body_left").load(url, function(responseText, statusText, xhr) {
					if (statusText === 'error') {
						alert('Server error...if problem persists please contact the admin!');
					}
				});
				return false;
			});
			
		});
	</script>