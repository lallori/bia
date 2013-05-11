<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>

	<c:url var="UserSearchFiltersPaginationURL" value="/src/UserSearchFiltersPagination.json">
		<c:param name="searchType" value="all"/>
	</c:url>
	
	<c:url var="ChoiceAdvancedSearchURL" value="/src/ChoiceAdvancedSearch.do"/>
	
	<c:url var="EraseSearchFiltersURL" value="/src/EraseSearchFilters.do" />

	<script type="text/javascript" charset="utf-8">
		$j(document).ready(function() {
			var $searchFilterTable = $j('#savedSearchFiltersForm').dataTable( {
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
								$j(this).find("td.sorting_1").addClass("rolloverRow");
								return false;
							}
					);
					
					$j("tr.odd").mouseout(
							function(){
								$j(this).find("td.sorting_1").removeClass("rolloverRow");
								return false;
							}
					);
					$j("tr.even").mouseover(
							function(){
								$j(this).find("td.sorting_1").addClass("rolloverRow");
								return false;
							}
					);
					
					$j("tr.even").mouseout(
							function(){
								$j(this).find("td.sorting_1").removeClass("rolloverRow");
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
			
			var $toRemove = "";
			
			$j("#removeSelected").click(function(){
				$toRemove = "";
				$j('#savedSearchFiltersForm > tbody > tr > td > input:checked').each(function(){
					$toRemove += $j(this).attr("idElement") + "+";
				});
				if($toRemove != ""){
					$j("#savedSearchFiltersTableDiv").block({ message: $j('#questionRemoveFilter'), 
						css: { 
							border: 'none', 
							padding: '5px',
							boxShadow: '1px 1px 10px #666',
							'-webkit-box-shadow': '1px 1px 10px #666'
							} ,
							overlayCSS: { backgroundColor: '#999' }	
					}); 
					return false;
				}else{
					return false;
				}
			});
			
			$j('#no').click(function() { 
				$j.unblockUI();
				$j(".blockUI").fadeOut("slow");
				$j("#questionRemoveFilter").hide();
				// Block is attached to form otherwise this block does not function when we use in transcribe and contextualize document
				$j("#savedSearchFiltersTableDiv").parent().append($j("#questionRemoveFilter"));
				$j(".blockUI").remove();
				return false; 
			}); 
	        
			$j('#yes').click(function() { 
				$j.ajax({ url: '${EraseSearchFiltersURL}', cache: false, data: {"idToErase" : $toRemove} ,success:function(html) { 
// 	 				$j("#body_left").html(html);
					$searchFilterTable.fnDraw();
	 			}});
				$j.unblockUI();
				$j(".blockUI").fadeOut("slow");
				$j("#questionRemoveFilter").hide();
				// Block is attached to form otherwise this block does not function when we use in transcribe and contextualize document
				$j("#savedSearchFiltersTableDiv").parent().append($j("#questionRemoveFilter"));
				$j(".blockUI").remove();						
				return false; 
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
		<a id="closeSavedFilters" class="button_small" type="submit" title="Close Saved Search Filters window" href="#">Close</a>
		<a id="goBackToAdvancedSearch" class="button_small" title="Go Back to Advanced Search" href="${ChoiceAdvancedSearchURL}">Go back</a>
		<a id="removeSelected" href="#">Remove selected</a>
	</div>
	
</div>

<div id="questionRemoveFilter" style="display:none; cursor: default"> 
	<h1>Are you sure?</h1> 
	<input type="button" id="yes" value="Yes" /> 
	<input type="button" id="no" value="No" /> 
</div>
