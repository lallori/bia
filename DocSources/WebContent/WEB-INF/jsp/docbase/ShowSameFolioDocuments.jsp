<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>

	<c:url var="ShowSameFolioDocumentsURL" value="/src/docbase/ShowSameFolioDocuments.json">
		<c:param name="volNum" value="${volNum}" />
		<c:param name="volLetExt" value="${volLetExt}" />
		<c:param name="folioNum" value="${folioNum}" />
		<c:param name="folioMod" value="${folioMod}" />
	</c:url>
	
	<div class="yourSearchDiv">
		<p>Documents with Volume/Folio: <font color="red" style="margin-left:5px">${volNum}${volLetExt} / ${folioNum}${folioMod}</font></p>
	</div>
	
	<table cellpadding="0" cellspacing="0" border="0" class="display"  id="showSameFolioDocuments${volNum}${folioNum}">
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
			$j("#showSameFolioDocuments${volNum}${folioNum} > thead > tr").append('<c:forEach items="${outputFields}" var="outputField"><c:out escapeXml="false" value="<th>${outputField}</th>"/></c:forEach>');

			$j('#showSameFolioDocuments${volNum}${folioNum}').dataTable( {
				"aoColumnDefs": [ { "sWidth": "80%", "aTargets": [ "_all" ] }], 
				"aaSorting": [[2, "asc"]],
				"bDestroy" : true,
				"bProcessing": true,
				"bServerSide": true,
				"iDisplayLength": 10,
				"iDisplayStart": 0,
				"oSearch": {"sSearch": "${personId}"},
				"sAjaxSource": "${ShowSameFolioDocumentsURL}",
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
// 					$j("#senderRecordsNum${personId}").text(this.fnSettings()._iRecordsTotal + ' Records');
					
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
			
			$j("#showSameFolioDocuments${volNum}${folioNum}_length").css('margin', '0 0 0 0');
			$j("#showSameFolioDocuments${volNum}${folioNum}_filter").remove();

			// We need to remove any previous live function
			$j('.showResult').die();
			 
			
			$j(".showResult").live('click', function() {
				var tabName = $j(this).attr("title");
				var numTab = 0;
				
				//Check if already exist a tab with this person
				var tabExist = false;
				$j("#tabs ul li a").each(function(){
					if(!tabExist){
						if(toTest != ""){
							numTab++;
						}
					}
					if(this.text == tabName){
						tabExist = true;
					}
				});
				
				if(!tabExist){
					$j( "#tabs" ).tabs( "add" , $j(this).attr("href"), tabName + "</span></a><span class=\"ui-icon ui-icon-close\" title=\"Close Tab\">Remove Tab");
					$j("#tabs").tabs("select", $j("#tabs").tabs("length")-1);
					return false;
				}else{
					$j("#tabs").tabs("select", numTab);
					return false;
				}
			});

		} );
	</script>
	
	