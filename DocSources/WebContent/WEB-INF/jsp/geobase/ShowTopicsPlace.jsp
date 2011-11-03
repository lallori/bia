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
			<c:url var="CompareDocumentURL" value="/src/docbase/CompareDocument.do">
				<c:param name="entryId"   value="${currentTopic.document.entryId}" />
			</c:url>
			
			<tr>
				<td><a class="searchResult" href="${CompareDocumentURL}" title="${currentTopic.document.getMDPAndFolio()}">${currentTopic.document.getEntryId()}</a></td>
				<td><a class="searchResult" href="${CompareDocumentURL}" title="${currentTopic.document.getMDPAndFolio()}">${currentTopic.topic.topicTitle}</a></td>
				<td><a class="searchResult" href="${CompareDocumentURL}" title="${currentTopic.document.getMDPAndFolio()}">${currentTopic.document.docYear} ${currentTopic.document.docMonthNum} ${currentTopic.document.docDay}</a></td>
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
			$j('.searchResult').die();
			// Result links have a specific class style on which we attach click live. 
			$j(".searchResult").live('click', function() {
				var tabName = $j(this).attr("title");
				var numTab = 0;
				
				//Check if already exist a tab with this person
				var tabExist = false;
				$j("#tabs ul li a").each(function(){
					if(!tabExist)
						numTab++;
					if(this.text == tabName){
						tabExist = true;
					}
				});
				
				if(!tabExist){
					$j( "#tabs" ).tabs( "add" , $j(this).attr("href"), tabName + "</span></a><span class=\"ui-icon ui-icon-close\" title=\"Close Tab\">Remove Tab");
					$j("#tabs").tabs("select", $j("#tabs").tabs("length")-1);
					return false;
				}else{
					$j("#tabs").tabs("select", numTab-1);
					return false;
				}
			}); 

		} );
	</script>
	
	