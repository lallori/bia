<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>

	<c:url var="MyHistoryPaginationURL" value="/user/MyHistoryPagination.json">
		<c:param name="searchType" value="VOLUME" />
	</c:url>

<table cellpadding="0" cellspacing="0" border="0" class="display" id="MyDocumentsHistoryTable">
    <thead>
        <tr>
            <th>Date</th>
            <th>Action</th>
            <th>Volume Number</th>
            <th>Carteggio</th>
            <th>Volume Start Date</th>
            <th>Volume End Date</th>
        </tr>
    </thead>
    <tbody>
        <tr>                                                                                              
            <td colspan="5" class="dataTables_empty">Loading data from server</td>                        
        </tr> 
    </tbody>
</table>

<div id="MyHistoryButtons">
	<a id="closeMyHistory" href="#" title="Close Saved Filters window">Close</a>
	<a id="goBackToMyHistory" href="<c:url value="/user/ShowMyHistory.do"/>" title="Go Back to My History">Go back</a>
</div>

<script type="text/javascript" charset="utf-8">                                                           
	$j(document).ready(function() {                                                                       
		$j('#MyDocumentsHistoryTable').dataTable( {                                                             
			"aoColumnDefs": [ { "sWidth": "90%", "aTargets": [ "_all" ] }],    
			"bAutoWidth" : false,
				"aoColumns" : [
				{ sWidth : "40px" },
				{ sWidth : "40px" },
				{ sWidth : "50px" },
				{ sWidth : "150px" },
				{ sWidth : "150px" },
				],                           
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

		$j("#goBackToMyHistory").click(function() {															
			Modalbox.show($j(this).attr("href"), {title: "MY HISTORY", width: 750});
			return false;
		});	
		$j("#closeMyHistory").click(function(){
			Modalbox.hide(); 
			return false;
		});
	} );                                                                                                  
</script>
	
<script type="text/javascript">
	$j(document).ready(function() {
	});
</script>