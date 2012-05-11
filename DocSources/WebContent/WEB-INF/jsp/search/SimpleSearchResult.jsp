<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>

	<c:url var="SimpleSearchPaginationURL" value="/src/SimpleSearchPagination.json">
		<c:param name="simpleSearchPerimeter" value="${command.simpleSearchPerimeter}" />
	</c:url>

	<c:url var="AdvancedSearchRefineURL" value="/src/ConvertSimpleSearchToAdvancedSearch.do">
		<c:param name="searchUUID" value="${command.searchUUID}"></c:param>
		<c:param name="simpleSearchPerimeter" value="${command.simpleSearchPerimeter}" />
		<c:param name="text" value="${command.text}" />
	</c:url>

	<c:url var="zeroClipboard" value="/swf/ZeroClipboard.swf"/>

	<script type="text/javascript" charset="utf-8">
		//TableToolsInit.sSwfPath = "${zeroClipboard}";

		$j(document).ready(function() {
			//dynamic field management
			$j("#${command.searchUUID} > thead > tr").append('<c:forEach items="${outputFields}" var="outputField"><c:out escapeXml="false" value="<th>${outputField}</th>"/></c:forEach>');
			var test;
			var colsort;
			<c:choose>
				<c:when test="${command.simpleSearchPerimeter.toString() == 'PEOPLE'}">
					test = [[0, "asc"]];
					colsort = [ { "sWidth": "80%", "aTargets": [ "_all" ] }, { "bSortable": false, "aTargets": [4]}];
				</c:when>			
				<c:when test="${command.simpleSearchPerimeter.toString() == 'EXTRACT' || command.simpleSearchPerimeter.toString() == 'SYNOPSIS'}">
					test = [[2, "asc"]];
					colsort = [ { "sWidth": "80%", "aTargets": [ "_all" ] }];
				</c:when>
				<c:when test="${command.simpleSearchPerimeter.toString() == 'VOLUME'}">
					test = [[1, "asc"]];
					colsort = [ { "sWidth": "80%", "aTargets": [ "_all" ] }];
				</c:when>
				<c:when test="${command.simpleSearchPerimeter.toString() == 'PLACE'}">
					test = [[0, "asc"]];
					colsort = [ { "sWidth": "80%", "aTargets": [ "_all" ], "bSortable": false, "aTargets": [2,3,4] }];
				</c:when>
			</c:choose>
						
			$j('#${command.searchUUID}').dataTable( {
				"aoColumnDefs": colsort,
				"aaSorting": test,
				"bDestroy" : true,
				"bProcessing": true,
				"bServerSide": true,
				"iDisplayLength": 20,
				"iDisplayStart": 0,
				"oSearch": {"sSearch": "${command.text}"},
				"sAjaxSource": "${SimpleSearchPaginationURL}",
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
					$j("#recordsNum${command.searchUUID}").text(this.fnSettings()._iRecordsTotal + ' Records');
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

			// We need to remove any previous live function
			$j('.searchResult').die();
			
			// Result links have a specific class style on which we attach click live. 
			$j('.searchResult').live('click', function() {
				//$j(this).parent().parent().css('background-color','#b0addd');
				$j("#body_left").load($j(this).attr("href"));
				return false;
			}); 

			$j("#refine${command.searchUUID}").open({width: 960, height: 680, scrollbars: "yes"});
			
			$j(".dataTables_filter").css('visibility', 'hidden');

		} );
	</script>
	
	<div class="yourSearchDiv">
		<p>Your search:
		<font color="red" style="margin-left:5px">${command.text}</font></p>
		<p>Total record found:
		<span class="recordsNum" id="recordsNum${command.searchUUID}"></span></p>
		<a id="refine${command.searchUUID}" class="refine" href="${AdvancedSearchRefineURL}">Refine this search</a>
		<a id="print${command.searchUUID}" class="print" href="${AdvancedSearchRefineURL}">Print Records</a>
	</div>

	<table cellpadding="0" cellspacing="0" border="0" class="display"  id="${command.searchUUID}">
		<thead>
			<tr></tr>
		</thead>
		<tbody>
			<tr>
				<td colspan="5" class="dataTables_empty">Loading data from server</td>
			</tr>
		</tbody>
	</table>