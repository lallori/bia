<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>

	<c:url var="ShowMyHistoryReportURL" value="/user/ShowMyHistoryReport.do"/>

	<c:url var="MyHistoryPaginationURL" value="/user/MyHistoryByCategoryPagination.json">
		<c:param name="category" value="${command.category}" />
	</c:url>

	<table cellpadding="0" cellspacing="0" border="0" class="display" id="MyDocumentsHistoryTable">
		<thead>
			<tr></tr>
		</thead>
		<tbody>
			<tr>
				<td colspan="5" class="dataTables_empty">Loading data from server</td>
			</tr>
		</tbody>
	</table>
	
	<div id="MyHistoryButtons">
	    <a id="closeMyHistory" href="#" title="Close My History window">Close</a>
	    <a id="categoryHistory" href="${ShowMyHistoryReportURL}">Category View</a>
	</div>
	
	<script type="text/javascript" charset="utf-8">                                                           
			$j(document).ready(function() {                                                                       
				//dynamic field management
				$j("#MyDocumentsHistoryTable > thead > tr").append('<c:forEach items="${outputFields}" var="outputField"><c:out escapeXml="false" value="<th>${outputField}</th>"/></c:forEach>');

				$j('#MyDocumentsHistoryTable').dataTable( {                                                             
					"aoColumnDefs": [ { "sWidth": "90%", "aTargets": [ "_all" ] }],    
					"bDestroy" : true,  
					"bFilter" : false,
					"bLengthChange": false,                                                                          
					"bProcessing": true,                                                                          
					"bServerSide": true,                                                                          
					"iDisplayLength": 10,                                                                         
					"iDisplayStart": 0,                                                                           
					"oSearch": {"sSearch": ""},                                                                   
					"sAjaxSource": "${MyHistoryPaginationURL}",                                           
					"sDom": 'T<"clear">lfrtip',                                                                   
					"sPaginationType": "full_numbers", 
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
					return false;                                                                                 
				});                                                                                               
			} );                                                                                                  
	</script>

	<script type="text/javascript">
		$j(document).ready(function() {
			$j("#categoryHistory").click(
				function() {															
					Modalbox.show($j(this).attr("href"), {title: "CATEGORY VIEW", width: 750});return false;}
					);	
			$j("#closeMyHistory").click(
				function(){
					Modalbox.hide(); return false;}
					);
		});
	</script>