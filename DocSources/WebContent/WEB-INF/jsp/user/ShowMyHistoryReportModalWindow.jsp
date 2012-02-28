<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>

	<c:url var="ShowMyHistoryURL" value="/user/ShowMyHistory.do" />

	<c:url var="FiveElementsDocumentURL" value="/user/MyHistoryFirstFiveElementsByCategoryPagination.json">
		<c:param name="category" value="DOCUMENT"/>
	</c:url>
	
	<c:url var="FiveElementsPeopleURL" value="/user/MyHistoryFirstFiveElementsByCategoryPagination.json">
		<c:param name="category" value="PEOPLE"/>
	</c:url>
	
	<c:url var="FiveElementsPlaceURL" value="/user/MyHistoryFirstFiveElementsByCategoryPagination.json">
		<c:param name="category" value="PLACE"/>
	</c:url>
	
	<c:url var="FiveElementsVolumeURL" value="/user/MyHistoryFirstFiveElementsByCategoryPagination.json">
		<c:param name="category" value="VOLUME"/>
	</c:url>

	<c:url var="MoreDocumentsURL" value="/user/ShowMyHistoryReportByCategory.do">
		<c:param name="category" value="DOCUMENT"/>
	</c:url>

	<c:url var="MorePeopleURL" value="/user/ShowMyHistoryReportByCategory.do">
		<c:param name="category" value="PEOPLE"/>
	</c:url>

	<c:url var="MorePlacesURL" value="/user/ShowMyHistoryReportByCategory.do">
		<c:param name="category" value="PLACE"/>
	</c:url>

	<c:url var="MoreVolumesURL" value="/user/ShowMyHistoryReportByCategory.do">
		<c:param name="category" value="VOLUME"/>
	</c:url>

<div id="myHistoryDiv">
	<div id="docsHistory">
		<div class="title">
        	<h5>DOCUMENTS</h5>
        </div>
        
        <div id="myCategoryDocumentsHistoryTableDiv">
        	<table cellpadding="0" cellspacing="0" border="0" class="display" id="myCategoryDocumentsHistoryTable">
            	<thead>
                	<tr>
                    	<th>Date</th>
                    	<th>Action</th>
                    	<th>Volume/Folio</th>
                	</tr>
            	</thead>
            	<tbody>
                	<tr>                                                                                              
                    	<td colspan="3" class="dataTables_empty">Loading data from server</td>                        
                	</tr> 
            	</tbody>
        	</table>
        </div>
        
        <a href="${MoreDocumentsURL}" id="moreDocs">More &rarr;</a>
    </div>

	 <div id="volumesHistory">
			<div class="title">
	        	<h5>VOLUME</h5>
	        </div>
	        
	        <div id="myCategoryVolumesHistoryTableDiv">
	        	<table cellpadding="0" cellspacing="0" border="0" class="display" id="myCategoryVolumesHistoryTable">
	            	<thead>
	                	<tr>
	                    	<th>Date</th>
	                    	<th>Action</th>
	                    	<th>Volume Number</th>
	                	</tr>
	            	</thead>
	            	<tbody>
	                	<tr>                                                                                              
	                    	<td colspan="3" class="dataTables_empty">Loading data from server</td>                        
	                	</tr> 
	            	</tbody>
	        	</table>
	        </div>
	        
	        <a href="${MoreVolumesURL}" id="moreVolumes">More &rarr;</a>
	    </div>
	    
	    <div id="placeHistory">
			<div class="title">
	        	<h5>PLACE</h5>
	        </div>
	        
	        <div id="myCategoryPlacesHistoryTableDiv">
	        	<table cellpadding="0" cellspacing="0" border="0" class="display" id="myCategoryPlacesHistoryTable">
	            	<thead>
	                	<tr>
	                    	<th>Date</th>
	                    	<th>Action</th>
	                    	<th>Place Name</th>
	                	</tr>
	            	</thead>
	            	<tbody>
	            	    <tr>                                                                                              
	                	    <td colspan="3" class="dataTables_empty">Loading data from server</td>                        
	                	</tr> 
	            	</tbody>
	        	</table>
	        </div>
	        
	        <a href="${MorePlacesURL}" id="morePlaces">More &rarr;</a>
	    </div>
	    
	    <div id="peopleHistory">
			<div class="title">
	        	<h5>PERSON</h5>
	        </div>
	        
	        <div id="myCategoryPeopleHistoryTableDiv">
	        	<table cellpadding="0" cellspacing="0" border="0" class="display" id="myCategoryPeopleHistoryTable">
	            	<thead>
	                	<tr>
	                    	<th>Date</th>
	                    	<th>Action</th>
	                    	<th>Name</th>
	                	</tr>
	            	</thead>
	            	<tbody>
	                	<tr>                                                                                              
	                    	<td colspan="3" class="dataTables_empty">Loading data from server</td>                        
	                	</tr> 
	            	</tbody>
	        	</table>
	        </div>
	        
	        <a href="${MorePeopleURL}" id="morePeople">More &rarr;</a>
	    </div>
	    
	    <div id="MyHistoryButtons">
	        <a href="${ShowMyHistoryURL}" id="generalHistory">Research History</a>
	        <a id="closeMyHistory" href="#" title="Close History window">Close</a>
	    </div>
	</div>

    
<script type="text/javascript" charset="utf-8">                                                           
		$j(document).ready(function() {                                                                       
			$j('#myCategoryDocumentsHistoryTable').dataTable( {                                                             
				"aoColumnDefs": [ { "sWidth": "100%", "aTargets": [ "_all" ] }],    
				"bAutoWidth" : false,
					"aoColumns" : [
					{ sWidth : "70px" },
					{ sWidth : "70px" },
					{ sWidth : "200px" },
					],                           
				"bDestroy" : true,  
				"bFilter" : false,
				"bLengthChange": false,                                                                          
				"bProcessing": true,                                                                          
				"bServerSide": true,                                                                          
				"iDisplayLength": 4,                                                                         
				"iDisplayStart": 0,                                                                           
				"oSearch": {"sSearch": ""},                                                                   
				"sAjaxSource": "${FiveElementsDocumentURL}",                                           
				"sDom": 'T<"clear">lfrtip',
				"bInfo":false,             
				"bFilter":false,                                                      
				"sPaginationType": "two_button", 
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
			
			$j('#myCategoryVolumesHistoryTable').dataTable( {                                                             
				"aoColumnDefs": [ { "sWidth": "100%", "aTargets": [ "_all" ] }],    
				"bAutoWidth" : false,
					"aoColumns" : [
					{ sWidth : "70px" },
					{ sWidth : "70px" },
					{ sWidth : "200px" },
					],                           
				"bDestroy" : true,  
				"bFilter" : false,
				"bLengthChange": false,                                                                          
				"bProcessing": true,                                                                          
				"bServerSide": true,                                                                          
				"iDisplayLength": 4,                                                                         
				"iDisplayStart": 0,                                                                           
				"oSearch": {"sSearch": ""},                                                                   
				"sAjaxSource": "${FiveElementsVolumeURL}",                                           
				"sDom": 'T<"clear">lfrtip',
				"bInfo":false,             
				"bFilter":false,                                                      
				"sPaginationType": "two_button", 
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
			
			$j('#myCategoryPlacesHistoryTable').dataTable( {                                                             
				"aoColumnDefs": [ { "sWidth": "100%", "aTargets": [ "_all" ] }],    
				"bAutoWidth" : false,
					"aoColumns" : [
					{ sWidth : "70px" },
					{ sWidth : "70px" },
					{ sWidth : "200px" },
					],                           
				"bDestroy" : true,  
				"bFilter" : false,
				"bLengthChange": false,                                                                          
				"bProcessing": true,                                                                          
				"bServerSide": true,                                                                          
				"iDisplayLength": 4,                                                                         
				"iDisplayStart": 0,                                                                           
				"oSearch": {"sSearch": ""},                                                                   
				"sAjaxSource": "${FiveElementsPlaceURL}",                                           
				"sDom": 'T<"clear">lfrtip',
				"bInfo":false,             
				"bFilter":false,                                                      
				"sPaginationType": "two_button", 
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
			
			$j('#myCategoryPeopleHistoryTable').dataTable( {                                                             
				"aoColumnDefs": [ { "sWidth": "100%", "aTargets": [ "_all" ] }],    
				"bAutoWidth" : false,
					"aoColumns" : [
					{ sWidth : "70px" },
					{ sWidth : "70px" },
					{ sWidth : "200px" },
					],                           
				"bDestroy" : true,  
				"bFilter" : false,
				"bLengthChange": false,                                                                          
				"bProcessing": true,                                                                          
				"bServerSide": true,                                                                          
				"iDisplayLength": 4,                                                                         
				"iDisplayStart": 0,                                                                           
				"oSearch": {"sSearch": ""},                                                                   
				"sAjaxSource": "${FiveElementsPeopleURL}",                                           
				"sDom": 'T<"clear">lfrtip',
				"bInfo":false,             
				"bFilter":false,                                                      
				"sPaginationType": "two_button", 
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
		$j("#moreDocs").click(function() {															
			Modalbox.show($j(this).attr("href"), {title: "MY DOCUMENTS HISTORY ", width: 750});return false;
		});	
		$j("#morePeople").click(function() {															
				Modalbox.show($j(this).attr("href"), {title: "MY PEOPLE HISTORY", width: 750});return false;
		});	
		$j("#moreVolumes").click(function() {
			Modalbox.show($j(this).attr("href"), {title: "MY VOLUMES HISTORY", width: 750});return false;
		});							   	
		$j("#morePlaces").click(function() {
			Modalbox.show($j(this).attr("href"), {title: "MY PLACE HISTORY", width: 750});return false;
		});
		$j(".showHistory").click(function() {
			$j("#body_left").load($j(this).attr("href"));
			Modalbox.hide(); 
			return false;
		});
		$j("#generalHistory").click(function() {															
			Modalbox.show($j(this).attr("href"), {title: "RESEARCH HISTORY", width: 750});return false;}
		);	
		$j("#closeMyHistory").click(function(){
			Modalbox.hide(); return false;}
		);
	});
</script>