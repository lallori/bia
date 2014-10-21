<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>

	<c:url var="ShowReferringToDocumentsRelatedPersonURL" value="/src/peoplebase/ShowReferringToDocumentsRelatedPerson.json"></c:url>
	
	<c:url var="AdvancedSearchRefineURL" value="/src/AdvancedSearch.do">
		<c:param name="searchUUID" value="${UUID}" />
		<c:param name="searchType" value="DOCUMENT" />
	</c:url>
	
	<div class="yourSearchDiv">
		<p><fmt:message key=“people.showReferringToDocumentsPerson.recipientDocumentsIndexedTo”/> <font color="red" style="margin-left:5px">"${mapNameLf}"</font></p>
		<p><fmt:message key=“people.showReferringToDocumentsPerson.totalRecordsFound”/> <span id="referringToRecordsNum${personId}" class="recordsNum"></span></p>
	</div>
	
	<a id="refineReferringTo${personId}" class="refine button_medium" href="${AdvancedSearchRefineURL}"><fmt:message key=“people.showReferringToDocumentsPerson.refineThisSearch”/></a>
	
	<table cellpadding="0" cellspacing="0" border="0" class="display"  id="showReferringToDocumentsPersonId${personId}">
		<thead>
			<tr></tr>
		</thead>
		<tbody>

		</tbody>
	</table>
	
	<script type="text/javascript" charset="utf-8">
		//TableToolsInit.sSwfPath = "${zeroClipboard}";

		$j(document).ready(function() {
			
			
			//dynamic field management
			$j("#showReferringToDocumentsPersonId${personId} > thead > tr").append('<c:forEach items="${outputFields}" var="outputField"><c:out escapeXml="false" value="<th>${outputField}</th>"/></c:forEach>');

			$j('#showReferringToDocumentsPersonId${personId}').dataTable( {
				"aoColumnDefs": [ { "sWidth": "80%", "aTargets": [ "_all" ] }], 
				"aaSorting": [[2, "asc"]],
				"bDestroy" : true,
				"bProcessing": true,
				"bServerSide": true,
				"iDisplayLength": 10,
				"iDisplayStart": 0,
				"oSearch": {"sSearch": "${personId}"},
				"sAjaxSource": "${ShowReferringToDocumentsRelatedPersonURL}",
				"sDom": 'T<"clear">lfrtip',
				"sPaginationType": "full_numbers",
				"fnServerData": function ( sSource, aoData, fnCallback ) {
					/* Add some extra data to the sender */
					aoData.push( { "name": "more_data", "value": "xxx" } );
					$j.getJSON( sSource, aoData, function (json) { 
						/* Do whatever additional processing you want on the callback, then tell DataTables */
						fnCallback(json)
					}); 					
				},
				"fnDrawCallback": function(){
					$j("#referringToRecordsNum${personId}").text(this.fnSettings()._iRecordsTotal + ' Records');
					
					$j("tr.odd").mouseover(
							function(){
								$j(this).find("td.sorting_1").addClass('rolloverRow');
								return false;
							}
					);
					
					$j("tr.odd").mouseout(
							function(){
								$j(this).find("td.sorting_1").removeClass('rolloverRow');
								return false;
							}
					);
					$j("tr.even").mouseover(
							function(){
								$j(this).find("td.sorting_1").addClass('rolloverRow');
								return false;
							}
					);
					
					$j("tr.even").mouseout(
							function(){
								$j(this).find("td.sorting_1").removeClass('rolloverRow');
								return false;
							}
					);
				}
			});
			
			$j("#showReferringToDocumentsPersonId${personId}_length").css('margin', '0 0 0 0');
			$j("#showReferringToDocumentsPersonId${personId}_filter").remove();

			// We need to remove any previous live function
			$j('.showResult').die();
			 
			
			$j(".showResult").live('click', function() {
				var tabName = $j(this).attr("title");
				var numTab = 0;
				var id = $j(this).attr("id");
				
				//Check if already exist a tab with this person
				var tabExist = false;
				$j("#tabs ul li a").each(function(){
					if(!tabExist){
						if(this.text != ""){
							numTab++;
						}
					}
					//Check if exist a tab with the same name or with the same name without id
					if(this.text == tabName || this.text == "DocId#" + id.substring(5, id.length) + " - " + tabName || this.text.substring(this.text.indexOf(" - ") + 3, this.text.length) == tabName){
						if($j(this).find("input").val() == id){
							tabExist = true;
						}else{
							if(this.text.indexOf("#") == -1){
								$j(this).find("span").text("DocId#" + $j(this).find("input").val().substring(5, $j(this).find("input").val().length) + " - " + this.text);
							}
							if(tabName.indexOf("#") == -1){
								tabName = "DocId#" + id.substring(5, id.length) + " - " + tabName;		
							}
						}
					}
				});
				
				if(!tabExist){
					$j( "#tabs" ).tabs( "add" , $j(this).attr("href"), tabName + "</span><input type=\"hidden\" value=\"" + $j(this).attr("id") + "\" /></a><span class=\"ui-icon ui-icon-close\" title=\"Close Tab\">Remove Tab");
					$j("#tabs").tabs("select", $j("#tabs").tabs("length")-1);
					return false;
				}else{
					$j("#tabs").tabs("select", numTab);
					return false;
				}
			});
			
			$j("#refineReferringTo${personId}").open({width: 960, height: 350, scrollbars: "yes"});

		} );
	</script>
	
	