<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>

	
	<div class="yourSearchDiv">
		Senders From "${placeNameFull}"
	</div>
	
	<table cellpadding="0" cellspacing="0" border="0" class="display"  id="showSenderDocumentsPlace">
		<thead>
			<tr></tr>
		</thead>
		<tbody>
			<c:forEach items="${senderDocs}" var="currentDocument">
			<c:url var="CompareDocumentURL" value="/src/docbase/CompareDocument.do">
				<c:param name="entryId"   value="${currentDocument.entryId}" />
			</c:url>
			<c:url var="ComparePersonURL" value="/src/peoplebase/ComparePerson.do">
				<c:param name="personId"   value="${currentDocument.senderPeople.personId}" />
			</c:url>
			<tr>
				<td><a class="searchResult" href="${CompareDocumentURL}" title="${currentDocument.getMDPAndFolio()}">${currentDocument.getEntryId()}</a></td>
				<td><a class="searchResult" href="${CompareDocumentURL}" title="${currentDocument.getMDPAndFolio()}">${currentDocument.docYear} ${currentDocument.docMonthNum} ${currentDocument.docDay}</a></td>
				<c:if test="${currentDocument.senderPeople.personId != 9285 && currentDocument.senderPeople.personId != 3905}">
					<td><a class="searchResult" href="${ComparePersonURL}" title="${currentDocument.getSenderPeople().getMapNameLf()}">${currentDocument.getSenderPeople().getPersonId()}</a></td>
					<td><a class="searchResult" href="${ComparePersonURL}" title="${currentDocument.getSenderPeople().getMapNameLf()}">${currentDocument.getSenderPeople().getMapNameLf()}</a></td>
				</c:if>
				<c:if test="${currentDocument.senderPeople.personId == 9285 || currentDocument.senderPeople.personId == 3905}">
					<td><a class="searchResult" title="${currentDocument.getSenderPeople().getMapNameLf()}">${currentDocument.getSenderPeople().getPersonId()}</a></td>
					<td><a class="searchResult" title="${currentDocument.getSenderPeople().getMapNameLf()}">${currentDocument.getSenderPeople().getMapNameLf()}</a></td>
				</c:if>
				<td><a class="searchResult" href="${CompareDocumentURL}" title="${currentDocument.getMDPAndFolio()}"><b>${currentDocument.getMDPAndFolio()}</b></a></td>
			</tr>
			</c:forEach>
		</tbody>
	</table>
	
	<script type="text/javascript" charset="utf-8">
		//TableToolsInit.sSwfPath = "${zeroClipboard}";

		$j(document).ready(function() {
			
			
			//dynamic field management
			$j("#showSenderDocumentsPlace > thead > tr").append('<c:forEach items="${outputFields}" var="outputField"><c:out escapeXml="false" value="<th>${outputField}</th>"/></c:forEach>');

			$j('#showSenderDocumentsPlace').dataTable( {
				"aoColumnDefs": [ { "sWidth": "80%", "aTargets": [ "_all" ] }], 
				"bDestroy" : true,
				"sDom": 'T<"clear">lfrtip',
				"sPaginationType": "full_numbers"
			});
			
			$j("#showSenderDocumentsPlace_length").css('margin', '0 0 0 0');
			$j("#showSenderDocumentsPlace_filter").remove();

			// We need to remove any previous live function
			$j('.searchResult').die();
			// Result links have a specific class style on which we attach click live. 
			//$j('.searchResult').live('click', function() {
				//$j("#body_left").load($j(this).attr("href"));
				//return false;
			//}); 
			
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
	
	