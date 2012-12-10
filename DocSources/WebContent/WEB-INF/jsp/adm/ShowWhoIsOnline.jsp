<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>

	
	<c:url var="ShowUserSearchResultURL" value="/admin/WhoIsOnline.json">
	</c:url>

	<table cellpadding="0" cellspacing="0" border="0" class="display"  id="userSearchTable">
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
			$j("#userSearchTable > thead > tr").append('<c:forEach items="${outputFields}" var="outputField"><c:out escapeXml="false" value="<th>${outputField}</th>"/></c:forEach>');

			$j('#userSearchTable').dataTable( {
				"aoColumnDefs": [ { "sWidth": "80%", "aTargets": [ "_all" ] }], 
				"aaSorting": [[0, "asc"]],
				"bDestroy" : true,
				"bProcessing": true,
				"bServerSide": true,
				"iDisplayLength": 10,
				"iDisplayStart": 0,
				"oSearch": {"sSearch": ""},
				"sAjaxSource": "${ShowUserSearchResultURL}",
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
					$j("tr.odd").mouseover(function(){
						$j(this).find("td.sorting_1").css('background-color','#b0addd');
						return false;
					});
					$j("tr.odd").mouseout(function(){
						$j(this).find("td.sorting_1").css('background-color','#DCC0BA');
						return false;
					});
					$j("tr.even").mouseover(function(){
						$j(this).find("td.sorting_1").css('background-color','#b0addd');
						return false;
					});
					$j("tr.even").mouseout(function(){
						$j(this).find("td.sorting_1").css('background-color','#EAD9D6');
						return false;
					});
				}
			});
			
			$j("#userSearchTable_length").css('margin', '0 0 0 0');
			$j("#userSearchTable_filter").remove();

			// We need to remove any previous live function
			$j('.searchResult').die();
			 
			
			$j(".searchResult").live('click', function() {
				$j("#body_left").load($j(this).attr("href"));
				return false;
			});

		} );
	</script>
	
	