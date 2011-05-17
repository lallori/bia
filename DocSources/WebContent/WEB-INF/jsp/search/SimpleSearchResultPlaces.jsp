<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>

	<c:url var="SimpleSearchPaginationURL" value="/src/SimpleSearchPagination.json">
		<c:param name="searchType" value="places" />
	</c:url>

	<c:url var="zeroClipboard" value="/swf/ZeroClipboard.swf"/>

	<script type="text/javascript" charset="utf-8">
		TableToolsInit.sSwfPath = "${zeroClipboard}";

		$j(document).ready(function() {
			$j('#resultPlace${searchNumber}').dataTable( {
				"bDestroy" : true,
				"bProcessing": true,
				"bServerSide": true,
				"sPaginationType": "full_numbers",
				"iDisplayLength": 10,
				"iDisplayStart": 0,
				"sDom": 'T<"clear">lfrtip',
				"oSearch": {"sSearch": "${command.text}"},
				"sAjaxSource": "${SimpleSearchPaginationURL}",
				"fnServerData": function ( sSource, aoData, fnCallback ) {
					/* Add some extra data to the sender */
					aoData.push( { "name": "more_data", "value": "xxx" } );
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
		} );
	</script>

	<table cellpadding="0" cellspacing="0" border="0" class="display"  id="resultPlace${searchNumber}">
		<thead>
			<tr>
				<th>Place Name</th>
				<th>Place Type</th>
				<th>Parent Name</th>
				<th>Type</th>
			</tr>
		</thead>
		<tbody>
			<tr>
				<td colspan="5" class="dataTables_empty">Loading data from server</td>
			</tr>
		</tbody>
	</table>