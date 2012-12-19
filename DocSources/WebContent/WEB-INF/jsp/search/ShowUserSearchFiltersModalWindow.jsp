<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>

	<c:url var="UserSearchFiltersPaginationURL" value="/src/UserSearchFiltersPagination.json">
		<c:param name="searchType" value="all"/>
	</c:url>
	
	<c:url var="ChoiceAdvancedSearchURL" value="/src/ChoiceAdvancedSearch.do"/>

	<script type="text/javascript" charset="utf-8">
		$j(document).ready(function() {
			$j('#savedSearchFiltersForm').dataTable( {
				"aoColumnDefs": [ { "sWidth": "80%", "aTargets": [ "_all" ] }],
				"bAutoWidth" : false,
				"aoColumns" : [
				{ sWidth : "200px" },
				{ sWidth : "30px" },
				{ sWidth : "70px" },
				{ sWidth : "70px" },
				{ sWidth : "40px" }
				], 
				"bLengthChange": false,
				"bDestroy" : true,
				"bFilter" : false,
				"bProcessing": true,
				"bServerSide": true,
				"iDisplayLength": 10,
				"iDisplayStart": 0,
				"oSearch": {"sSearch": ""},
				"sAjaxSource": "${UserSearchFiltersPaginationURL}",
				"sDom": 'T<"clear">lfrtip',
				"sPaginationType": "full_numbers",
				"fnServerData": function ( sSource, aoData, fnCallback ) {
					/* Add some extra data to the sender */
					aoData.push( { "name": "more_data", "value": "xxx" } );
					$j.getJSON( sSource, aoData, function (json) { 
						/* Do whatever additional processing you want on the callback, then tell DataTables */
						fnCallback(json)
					} );
				},
				"fnDrawCallback" : function(){
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
					
					$j('.searchResult > input:checkbox').unwrap();
				}
			} );

			// We need to remove any previous live function
			$j('.searchResult').die();
			// Result links have a specific class style on which we attach click live. 
			$j('.searchResult').live('click', function() {
				window.open($j(this).attr("href"),"_blank",'width=960, height=350, scrollbars=yes');
				Modalbox.hide();
				return false;
			});
			
			$j("#closeSavedFilters").click(function(){
				Modalbox.hide();return false;
			});
			
			$j("#goBackToAdvancedSearch").click(function(){
				Modalbox.show($j(this).attr("href"), {title: "ADVANCED SEARCH", width: 750, height: 325});return false;
			});
		} );
	</script>

<div id="savedSearchFiltersDiv">
	<div id="savedSearchFiltersTableDiv">
		<table cellpadding="0" cellspacing="0" border="0" class="display"  id="savedSearchFiltersForm">
			<thead>
				<tr>
					<th>FILTER NAME</th>
					<th>RESULTS</th>
					<th>RECORD TYPES</th>
					<th>DATE</th>
					<th>SELECT</th>
				</tr>
			</thead>
			<tbody>
				<tr>
					<td colspan="4" class="dataTables_empty">Loading data from server</td>
				</tr>
			</tbody>
		</table>
	</div>
	
	<div id="savedFiltersButtons">
		<a id="closeSavedFilters" type="submit" title="Close Saved Search Filters window" href="#">Close</a>
		<a id="goBackToAdvancedSearch" title="Go Back to Advanced Search" href="${ChoiceAdvancedSearchURL}">Go back</a>
	</div>
	
</div>
