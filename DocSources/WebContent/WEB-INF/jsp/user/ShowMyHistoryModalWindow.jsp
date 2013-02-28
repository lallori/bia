<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>

	<c:url var="ShowMyHistoryReportURL" value="/user/ShowMyHistoryReport.do" />

	<c:url var="MyHistoryPaginationURL" value="/user/MyHistoryPagination.json" />
	
	<c:url var="ShowConfirmEraseMyHistoryURL" value="/user/ShowConfirmEraseMyHistory.do" />
	
	
	<div id="researchHistoryTableDiv">
	<table cellpadding="0" cellspacing="0" border="0" class="display" id="researchHistoryTable">
	    <thead>
	        <tr>
	            <th>Date</th>
	            <th>Type</th>
	            <th>Action</th>
	            <th>Volume/Folio</th>
	            <th>Volume Number</th>
	            <th>Place Name</th>
	            <th>Name</th>
	        </tr>
	    </thead>
	    <tbody>
	        <tr>                                                                                              
	            <td colspan="5" class="dataTables_empty">Loading data from server</td>                        
	        </tr> 
	    </tbody>
	</table>
	</div>
	
	<div id="MyHistoryButtons">
		<a id="eraseHistory" href="${ShowConfirmEraseMyHistoryURL}">Erase History</a>
	    <a id="closeMyHistory" href="#" title="Close My History window">Close</a>
	    <a id="categoryHistory" href="${ShowMyHistoryReportURL}">Category View</a>
	</div>


	<script type="text/javascript" charset="utf-8">                                                           
			$j(document).ready(function() {                                                                       
				$j('#researchHistoryTable').dataTable( {                                                             
					"aoColumnDefs": [ { "sWidth": "90%", "aTargets": [ "_all" ] }],   
					"aaSorting": [[0, "desc"]],
					"bAutoWidth" : false,
						"aoColumns" : [
						{ sWidth : "40px" },
						{ sWidth : "40px" },
						{ sWidth : "50px" },
						{ sWidth : "150px", "bSortable": false },
						{ sWidth : "150px", "bSortable": false },
						{ sWidth : "150px", "bSortable": false },
						{ sWidth : "150px", "bSortable": false },
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
					}
				} );                                                                                              
																												  
				// We need to remove any previous live function                                                   
				$j('#researchHistoryTable').find('.searchResult').die();                                                                        
				// Result links have a specific class style on which we attach click live.                        
				$j('#researchHistoryTable').find('.searchResult').live('click', function() {                                                    
					$j("#body_left").load($j(this).attr("href"));
					Modalbox.hide();
					return false;
				}); 
				// We need to remove any previous live function                                                   
				$j('#researchHistoryTable').find('.searchResultUserSearch').die();                                                                        
				// Result links have a specific class style on which we attach click live.                        
				$j('#researchHistoryTable').find('.searchResultUserSearch').live('click', function() {                                                    
					window.open($j(this).attr("href"), 'User Search History', 'width=960,height=350,scrollbars=yes');
					Modalbox.hide();
					return false;
				}); 
			} );                                                                                                  
	</script>
		
	<script type="text/javascript">
		$j(document).ready(function() {
			$j("#categoryHistory").click(function() {															
				Modalbox.show($j(this).attr("href"), {title: "CATEGORY VIEW", width: 750});
				return false;
			});	
			$j("#closeMyHistory").click(function(){
				Modalbox.hide();
				return false;
			});
			$j("#eraseHistory").click(function() {
				Modalbox.show($j(this).attr("href"), {title: "ERASE HISTORY", width: 310, height: 120});
				return false;
			});
		});
	</script>