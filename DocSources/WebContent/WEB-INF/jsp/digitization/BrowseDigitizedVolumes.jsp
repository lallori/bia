<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>

	<c:url var="BrowseDigitizedVolumesURL" value="/digitization/BrowseDigitizedVolumes.json">
		<c:param name="searchType" value="${command.searchType}" />
		<c:param name="volNum" value="${command.volNum}" />
		<c:param name="volLetExt" value="${command.volLetExt}" />
		<c:param name="volNumBetween" value="${command.volNumBetween}" />
	</c:url>

	<c:url var="zeroClipboard" value="/swf/ZeroClipboard.swf"/>

	<script type="text/javascript" charset="utf-8">

		$j(document).ready(function() {
			//dynamic field management
			$j("#${command.searchUUID} > thead > tr").append('<c:forEach items="${outputFields}" var="outputField"><c:out escapeXml="false" value="<th>${outputField}</th>"/></c:forEach>');
						
			$j('#${command.searchUUID}').dataTable( {
				"aoColumnDefs": [ { "sWidth": "90%", "aTargets": [ "_all" ] , "bSortable": false, "aTargets": [2]}],    
				"bAutoWidth" : false,
				"aoColumns" : [
				{ sWidth : "50px" },
				{ sWidth : "50px" },
				{ sWidth : "50px" },
// 				{ sWidth : "150px" },
// 				{ sWidth : "150px" },
				],                           
				"bDestroy" : true,  
				"bFilter" : false,
				"bLengthChange": false,                                                                          
				"bProcessing": true,                                                                          
				"bServerSide": true,                                                                          
				"iDisplayLength": 10,                                                                         
				"iDisplayStart": 0,                                                                           
				"oSearch": {"sSearch": ""},                                                                   
				"sAjaxSource": "${BrowseDigitizedVolumesURL}",                                           
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
				"fnDrawCallback" : function(){
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

			// We need to remove any previous live function
			$j('.searchResult').die();
			
			// Result links have a specific class style on which we attach click live. 
			$j('.searchResult').live('click', function() {
				$j("#body_left").load($j(this).attr("href"));
				
				if(!$j(this).parent().parent().hasClass("selected")){
					$j("#${command.searchUUID} tbody").find("tr.selected td.selected").addClass("sorting_1");
					$j("#${command.searchUUID} tbody").find("tr.odd.selected td.selected").removeClass("rolloverRow").addClass("darkCell");
					$j("#${command.searchUUID} tbody").find("tr.even.selected td.selected").removeClass("rolloverRow").addClass("lightCell");
					$j("#${command.searchUUID} tbody").find("tr.selected td.selected").removeClass("selected");
				
					
					$j("#${command.searchUUID} tbody").find("tr.selected").removeClass("selected");
					var tr = $j(this).parent().parent();
					$j(tr).addClass("selected");
					
					var tdSort = $j(tr).find("td.sorting_1");
					$j(tdSort).removeClass("sorting_1");
					$j(tdSort).addClass("selected");
				}
				
				return false;
			});
			
			//MD: This code is for click in any space inside a row
			$j("#${command.searchUUID} tbody tr").live('click', function(){
				$j("#body_left").load($j(this).children().children().attr("href"));
				
				if(!$j(this).hasClass("selected")){
					$j("#${command.searchUUID} tbody").find("tr.selected td.selected").addClass("sorting_1");
					$j("#${command.searchUUID} tbody").find("tr.odd.selected td.selected").removeClass("rolloverRow").addClass("darkCell");
					$j("#${command.searchUUID} tbody").find("tr.even.selected td.selected").removeClass("rolloverRow").addClass("lightCell");
					$j("#${command.searchUUID} tbody").find("tr.selected td.selected").removeClass("selected");
				
					
					$j("#${command.searchUUID} tbody").find("tr.selected").removeClass("selected");
				
					$j(this).addClass("selected");
				
					var tdSort = $j(this).find("td.sorting_1");
					$j(tdSort).removeClass("sorting_1");
					$j(tdSort).addClass("selected");
					//$j(this).css('background-color','#b0addd');
				}
				
				return false;
			});

			$j(".dataTables_filter").css('visibility', 'hidden');

		} );
	</script>

	<table cellpadding="0" cellspacing="0" border="0" class="display"  id="${command.searchUUID}">
		<thead>
			<tr></tr>
		</thead>
		<tbody>
			<tr>
				<td colspan="5" class="dataTables_empty"><fmt:message key="digitization.browseDigitizedVolumes.loadingData"/></td>
			</tr>
		</tbody>
	</table>