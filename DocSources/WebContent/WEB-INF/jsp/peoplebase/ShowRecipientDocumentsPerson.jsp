<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>

	<c:url var="ShowRecipientDocumentsRelatedPersonURL" value="/src/peoplebase/ShowRecipientDocumentsRelatedPerson.json"></c:url>
	
	<c:url var="AdvancedSearchRefineURL" value="/src/AdvancedSearch.do">
		<c:param name="searchUUID" value="${UUID}" />
		<c:param name="searchType" value="DOCUMENT" />
	</c:url>
	
	<div class="yourSearchDiv">
		<p>Recipient Documents indexed to <font color="red" style="margin-left:5px">"${mapNameLf}"</font></p>
		<p>Total record found: <span id="recipientRecordsNum${personId}" class="recordsNum"></span></p>
	</div>
	
	<a id="refineRecipient${personId}" class="refine" href="${AdvancedSearchRefineURL}">Refine this search</a>
	
	<table cellpadding="0" cellspacing="0" border="0" class="display"  id="showRecipientDocumentsPersonId${personId}">
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
			$j("#showRecipientDocumentsPersonId${personId} > thead > tr").append('<c:forEach items="${outputFields}" var="outputField"><c:out escapeXml="false" value="<th>${outputField}</th>"/></c:forEach>');

			$j('#showRecipientDocumentsPersonId${personId}').dataTable( {
				"aoColumnDefs": [ { "sWidth": "80%", "aTargets": [ "_all" ] }], 
				"aaSorting": [[2, "asc"]],
				"bDestroy" : true,
				"bProcessing": true,
				"bServerSide": true,
				"iDisplayLength": 10,
				"iDisplayStart": 0,
				"oSearch": {"sSearch": "${personId}"},
				"sAjaxSource": "${ShowRecipientDocumentsRelatedPersonURL}",
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
					$j("#recipientRecordsNum${personId}").text(this.fnSettings()._iRecordsTotal + ' Records');
					
					$j("tr.odd").mouseover(
							function(){
								$j(this).find("td.sorting_1").css('background-color','#b0addd');
								return false;
							}
					);
					
					$j("tr.odd").mouseout(
							function(){
								$j(this).find("td.sorting_1").css('background-color','#DCC0BA');
								return false;
							}
					);
					$j("tr.even").mouseover(
							function(){
								$j(this).find("td.sorting_1").css('background-color','#b0addd');
								return false;
							}
					);
					
					$j("tr.even").mouseout(
							function(){
								$j(this).find("td.sorting_1").css('background-color','#EAD9D6');
								return false;
							}
					);
				}
			});
			
			$j("#showRecipientDocumentsPersonId${personId}_length").css('margin', '0 0 0 0');
			$j("#showRecipientDocumentsPersonId${personId}_filter").remove();

			// We need to remove any previous live function
			$j('.showResult').die();
			 
			
			$j(".showResult").live('click', function() {
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
			
			$j("#refineRecipient${personId}").open({width: 960, height: 350, scrollbars: "yes"});

		} );
	</script>
	
	