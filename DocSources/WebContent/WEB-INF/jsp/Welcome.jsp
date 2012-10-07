<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>

	<a id="mapcourses" href="http://courses.medici.org/" target="_blank"></a>
	<div class="welcome_list">
		<h2>Welcome back <security:authentication property="principal.firstName"/>. <br /></h2>

	    <div id="lastLogOnDiv">
	    	<p>From your last log on:</p>    
	        <table cellpadding="0" cellspacing="0" border="0" class="display" id="lastLogOnTable">
	            <thead>
	                <tr>
	                    <th></th>
	                    <th></th>
	                </tr>
	            </thead>
	            <tbody>
	                <tr>                                                                                              
	                    <td colspan="3" class="dataTables_empty">Loading data from server</td>                        
	                </tr> 
	            </tbody>
	        </table>
	    </div>
	    
	    <div id="thisWeekDiv">
	    	<p>This week:</p>    
	        <table cellpadding="0" cellspacing="0" border="0" class="display" id="thisWeekTable">
	            <thead>
	                <tr>
	                    <th></th>
	                    <th></th>
	                </tr>
	            </thead>
	            <tbody>
	                <tr>                                                                                              
	                    <td colspan="3" class="dataTables_empty">Loading data from server</td>                        
	                </tr> 
	            </tbody>
	        </table>
	    </div>
	    
	    <div id="thisMonthDiv">
	    	<p>This month:</p>    
	        <table cellpadding="0" cellspacing="0" border="0" class="display" id="thisMonthTable">
	            <thead>
	                <tr>
	                    <th></th>
	                    <th></th>
	                </tr>
	            </thead>
	            <tbody>
	                <tr>                                                                                              
	                    <td colspan="3" class="dataTables_empty">Loading data from server</td>                        
	                </tr> 
	            </tbody>
	        </table>
	    </div>
   	</div>

<script type="text/javascript" charset="utf-8">
		$j(document).ready(function() {
			$j('#lastLogOnTable').dataTable( {
				"aoColumnDefs": [ { "sWidth": "100%", "aTargets": [ "_all" ] }],
				"bAutoWidth" : false,
				"bSort": false,
					"aoColumns" : [
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
				"sAjaxSource": "/DocSources/template/lastLogOn.json",
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
			
			$j('#thisWeekTable').dataTable( {
				"aoColumnDefs": [ { "sWidth": "100%", "aTargets": [ "_all" ] }],
				"bAutoWidth" : false,
				"bSort": false,
					"aoColumns" : [
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
				"sAjaxSource": "/DocSources/template/lastLogOn.json",
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
			
			$j('#thisMonthTable').dataTable( {
				"aoColumnDefs": [ { "sWidth": "100%", "aTargets": [ "_all" ] }],
				"bAutoWidth" : false,
				"bSort": false,
					"aoColumns" : [
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
				"sAjaxSource": "/DocSources/template/lastLogOn.json",
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
			
			$j('#lastWeekTable').dataTable( {                                                             
				"aoColumnDefs": [ { "sWidth": "100%", "aTargets": [ "_all" ] }],    
				"bAutoWidth" : false,
				"bSort": false,
					"aoColumns" : [
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
				"sAjaxSource": "/DocSources/template/lastLogOn.json",                       
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

			$j('#lastMonthTable').dataTable( {
				"aoColumnDefs": [ { "sWidth": "100%", "aTargets": [ "_all" ] }],
				"bAutoWidth" : false,
				"bSort": false,
					"aoColumns" : [
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
				"sAjaxSource": "/DocSources/template/lastLogOn.json",                       
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

	
