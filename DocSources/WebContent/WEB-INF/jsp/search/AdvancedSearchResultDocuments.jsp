<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>

	<c:url var="AdvancedSearchPaginationURL" value="/src/AdvancedSearchPagination.json">
		<c:param name="searchType" value="documents" />
	</c:url>

	<c:url var="AdvancedSearchRefineURL" value="/src/AdvancedSearchDocuments.do">
		<c:param name="searchUUID" value="${command.searchUUID}"></c:param>
	</c:url>

	<script type="text/javascript" charset="utf-8">
		$j(document).ready(function() {
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
			
			// We need to remove any previous live function
			$j('.refine').die();
			// Refine button have a specific class style on which we attach click live. 
			$j('.refine').live('click', function() {
				$j(this).open({width: 960, height: 680, scrollbars: "yes"});
				return false;
			});
		} );
	</script>

	<a id="refine${command.searchUUID}" class="refine" href="${AdvancedSearchRefineURL}">REFINE THIS SEARCH</a>

	<table cellpadding="0" cellspacing="0" border="0" class="display"  id="${command.searchUUID}">
		<thead>
			<tr>
				<th>Sender</th>
				<th>Recipient</th>
				<th>Date</th>
				<th>Sender Location</th>
				<th>Recipient Location</th>
				<th>Volume / Folio</th>
				<!--<th>Folio</th> -->
			</tr>
		</thead>
		<tbody>
			<tr>
				<td colspan="5" class="dataTables_empty">Loading data from server</td>
			</tr>
		</tbody>
	</table>

