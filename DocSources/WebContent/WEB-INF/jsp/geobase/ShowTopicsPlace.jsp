<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>

	
	<div class="yourSearchDiv">
		Document Topics Indexed to "${placeNameFull}"
	</div>
	
	<table cellpadding="0" cellspacing="0" border="0" class="display"  id="showTopicsPlace">
		<thead>
			<tr></tr>
		</thead>
		<tbody>
			<c:forEach items="${topicsPlace}" var="currentTopic">
			<c:url var="ShowDocumentURL" value="/src/docbase/ShowDocument.do">
				<c:param name="entryId"   value="${currentTopic.document.entryId}" />
			</c:url>
			
			<tr>
				<td><a class="searchResult" href="${ShowDocumentURL}">${currentTopic.document.getEntryId()}</a></td>
				<td><a class="searchResult" href="${ShowDocumentURL}">${currentTopic.topic.topicTitle}</a></td>
				<td><a class="searchResult" href="${ShowDocumentURL}">${currentTopic.document.docYear} ${currentTopic.document.docMonthNum} ${currentTopic.document.docDay}</a></td>
			</tr>
			</c:forEach>
		</tbody>
	</table>
	
	<script type="text/javascript" charset="utf-8">
		//TableToolsInit.sSwfPath = "${zeroClipboard}";

		$j(document).ready(function() {
			
			
			//dynamic field management
			$j("#showTopicsPlace > thead > tr").append('<c:forEach items="${outputFields}" var="outputField"><c:out escapeXml="false" value="<th>${outputField}</th>"/></c:forEach>');

			$j('#showTopicsPlace').dataTable( {
				"aoColumnDefs": [ { "sWidth": "80%", "aTargets": [ "_all" ] }], 
				"bDestroy" : true,
				"sDom": 'T<"clear">lfrtip',
				"sPaginationType": "full_numbers"
			});
			
			$j("#showTopicsPlace_length").css('margin', '0 0 0 0');
			$j("#showTopicsPlace_filter").remove();

			// We need to remove any previous live function
			//$j('.searchResult').die();
			// Result links have a specific class style on which we attach click live. 
			//$j('.searchResult').live('click', function() {
				//$j("#body_left").load($j(this).attr("href"));
				//return false;
			//}); 

		} );
	</script>
	
	