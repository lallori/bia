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

			$j('#${command.searchUUID}').dataTable( {
				"aoColumnDefs": [ { "sWidth": "80%", "aTargets": [ "_all" ] }], 
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
				}
			} );

			// We need to remove any previous live function
			$j('.searchResult').die();
			// Result links have a specific class style on which we attach click live. 
			$j('.searchResult').live('click', function() {
				$j("#body_left").load($j(this).attr("href"));
				return false;
			});
			
			$j("#refine${command.searchUUID}").open({width: 960, height: 680, scrollbars: "yes"});
		} );
	</script>

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

