<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>

	<c:url var="AdvancedSearchPaginationURL" value="/src/AdvancedSearchPagination.json">
		<c:param name="searchType" value="${command.searchType}" />
	</c:url>

	<c:url var="AdvancedSearchRefineURL" value="/src/AdvancedSearch.do">
		<c:param name="searchUUID" value="${command.searchUUID}"></c:param>
		<c:param name="searchType" value="${command.searchType}" />
	</c:url>

	<script type="text/javascript" charset="utf-8">
		$j(document).ready(function() {
			//dynamic field management
			$j("#${command.searchUUID} > thead > tr").append('<c:forEach items="${outputFields}" var="outputField"><c:out escapeXml="false" value="<th>${outputField}</th>"/></c:forEach>');

			var test;
			<c:choose>
				<c:when test="${command.searchType.toString() == 'PEOPLE'}">
					test = [[0, "desc"]];
				</c:when>			
				<c:when test="${command.searchType.toString() == 'DOCUMENT'}">
					test = [[2, "asc"]];
				</c:when>
				<c:when test="${command.searchType.toString() == 'VOLUME'}">
					test = [[1, "desc"]];
				</c:when>
				<c:when test="${command.searchType.toString() == 'PLACE'}">
					test = [[0, "asc"]];
				</c:when>
			</c:choose>
			
			$j('#${command.searchUUID}').dataTable( {
				"aoColumnDefs": [ { "sWidth": "80%", "aTargets": [ "_all" ] }], 
				"aaSorting": test,
				"bDestroy" : true,
				"bFilter" : false,
				"bProcessing": true,
				"bServerSide": true,
				"iDisplayLength": 10,
				"iDisplayStart": 0,
				"oSearch": {"sSearch": ""},
				"sAjaxSource": "${AdvancedSearchPaginationURL}",
				"sDom": 'T<"clear">lfrtip',
				"sPaginationType": "full_numbers",
				"fnServerData": function ( sSource, aoData, fnCallback ) {
					/* Add some extra data to the sender */
					aoData.push( { "name": "searchUUID", "value": "${command.searchUUID}" } );
					$j.getJSON( sSource, aoData, function (json) { 
						/* Do whatever additional processing you want on the callback, then tell DataTables */
						fnCallback(json)
					} );
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
			} );

			// We need to remove any previous live function
			$j('.searchResult').die();
			// Result links have a specific class style on which we attach click live. 
			$j('.searchResult').live('click', function() {
				$j("#body_left").load($j(this).attr("href"));
				return false;
			});
			
			$j("#refine${command.searchUUID}").open({width: 960, height: 350, scrollbars: "yes"});
			
			$j("#tabLink").click(function(){
				//Modalbox.show('<h1>This Search:</h1><br /><br /><p>${yourSearch}</p><br /><input type=\'submit\' id=\"closeModal\" title=\"Close This Search window\" value=\"Close\"\ onclick=\'Modalbox.hide()\' style=\"margin: 55px 0 0 335px; position: absolute;\" />', {title: "THIS SEARCH", width: 750, height: 190});return false;
			});
			
		} );
	</script>
	
	<div class="yourSearchDiv">
		Your search:
		<c:if test="${yourSearch.length() > 45}">
			<a class="tabLink" id="tabLink" href="#">${yourSearch.substring(0,41)}...</a>
		</c:if>
		<c:if test="${yourSearch.length() <= 45}">
			<font color="red">${yourSearch}</font>
		</c:if>
		<span class="recordsNum" id="recordsNum${command.searchUUID}"></span>
	</div>
	
	<a id="refine${command.searchUUID}" class="refine" href="${AdvancedSearchRefineURL}">Refine this search</a>

	<table cellpadding="0" cellspacing="0" border="0" class="display"  id="${command.searchUUID}">
		<thead>
			<tr>
			</tr>
		</thead>
		<tbody>
			<tr>
				<td colspan="5" class="dataTables_empty">Loading data from server</td>
			</tr>
		</tbody>
	</table>