<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>

	<c:url var="ShowMyHistoryReportURL" value="/user/ShowMyHistoryReport.do"/>
	
	<c:url var="ShowMyHistoryURL" value="/user/ShowMyHistory.do" />

	<c:url var="MyHistoryPaginationURL" value="/user/MyHistoryByCategoryPagination.json">
		<c:param name="category" value="${command.category}" />
	</c:url>

	
	<c:if test="${command.category == 'PEOPLE'}">
		<div id="myPeopleHistoryTableDiv">
		<table cellpadding="0" cellspacing="0" border="0" class="display" id="myPeopleHistoryTable" style="width:725px">
	</c:if>
	<c:if test="${command.category == 'DOCUMENT'}">
		<div id="myDocumentsHistoryTableDiv">
		<table cellpadding="0" cellspacing="0" border="0" class="display" id="myDocumentsHistoryTable" style="width:725px">
	</c:if>
	<c:if test="${command.category == 'VOLUME'}">
		<div id="myVolumeHistoryTableDiv">
		<table cellpadding="0" cellspacing="0" border="0" class="display" id="myVolumeHistoryTable" style="width:725px">
	</c:if>
	<c:if test="${command.category == 'PLACE'}">
		<div id="myPlaceHistoryTableDiv">
		<table cellpadding="0" cellspacing="0" border="0" class="display" id="myPlaceHistoryTable" style="width:725px">
	</c:if>
<!-- 	<table cellpadding="0" cellspacing="0" border="0" class="display" id="MyDocumentsHistoryTable"> -->
		<thead>
			<tr></tr>
		</thead>
		<tbody>
			<tr>
				<td colspan="5" class="dataTables_empty">Loading data from server</td>
			</tr>
		</tbody>
	</table>
	</div>
	
	<div id="MyHistoryButtons">
		<a id="generalHistory" href="${ShowMyHistoryURL}" title="Research History">Research History</a>
	    <a id="closeMyHistory" href="#" title="Close My History window">Close</a>
	    <a id="categoryHistory" href="${ShowMyHistoryReportURL}">Category View</a>
	</div>
	
	<script type="text/javascript" charset="utf-8">                                                           
			$j(document).ready(function() {                                                                       
				//dynamic field management
				//$j("#MyDocumentsHistoryTable > thead > tr").append('<c:forEach items="${outputFields}" var="outputField"><c:out escapeXml="false" value="<th>${outputField}</th>"/></c:forEach>');
				
				<c:choose>
					<c:when test="${command.category == 'PEOPLE'}">
						$j("#myPeopleHistoryTable > thead > tr").append('<c:forEach items="${outputFields}" var="outputField"><c:out escapeXml="false" value="<th>${outputField}</th>"/></c:forEach>');
						$j('#myPeopleHistoryTable').dataTable( {                                                             
							"bAutoWidth" : false,
							"aoColumns" : [
											{ sWidth : "40px" },
											{ sWidth : "40px" },
											{ sWidth : "170px" },
											{ sWidth : "80px" },
											{ sWidth : "80px" },
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
							}
						} );
					</c:when>
					<c:when test="${command.category == 'DOCUMENT'}">
						$j("#myDocumentsHistoryTable > thead > tr").append('<c:forEach items="${outputFields}" var="outputField"><c:out escapeXml="false" value="<th>${outputField}</th>"/></c:forEach>');
						$j('#myDocumentsHistoryTable').dataTable( {                                                             
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
							}
						} );	
					</c:when>
					<c:when test="${command.category == 'VOLUME'}">
					$j("#myVolumeHistoryTable > thead > tr").append('<c:forEach items="${outputFields}" var="outputField"><c:out escapeXml="false" value="<th>${outputField}</th>"/></c:forEach>');
					$j('#myVolumeHistoryTable').dataTable( {                                                             
						"bAutoWidth" : false,
						"aoColumns" : [
									{ sWidth : "40px" },
									{ sWidth : "40px" },
									{ sWidth : "60px" },
									{ sWidth : "170px" },
									{ sWidth : "80px" },
									{ sWidth : "80px" },
									{ sWidth : "40px" }
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
						}
					} );	
				</c:when>
				<c:when test="${command.category == 'PLACE'}">
				$j("#myPlaceHistoryTable > thead > tr").append('<c:forEach items="${outputFields}" var="outputField"><c:out escapeXml="false" value="<th>${outputField}</th>"/></c:forEach>');
				$j('#myPlaceHistoryTable').dataTable( {                                                             
					"bAutoWidth" : false,
					"aoColumns" : [
									{ sWidth : "40px" },
									{ sWidth : "40px" },
									{ sWidth : "330px" },
									{ sWidth : "120px" },
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
									$j(this).find("td.sorting_1").addClass("rolloverRow");
									return false;
								}
						);
						
						$j("tr.odd").mouseout(
								function(){
									$j(this).find("td.sorting_1").addClass("rolloverRow");
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
									$j(this).find("td.sorting_1").addClass("rolloverRow");
									return false;
								}
						);
					}
				} );
			</c:when>
			</c:choose>

// 				$j('#MyDocumentsHistoryTable').dataTable( {                                                             
// 					"aoColumnDefs": colDef,
// 					"bAutoWidth" : false,
// 					"aoColumns" : col,
// 					"bDestroy" : true,  
// 					"bFilter" : false,
// 					"bLengthChange": false,                                                                          
// 					"bProcessing": true,                                                                          
// 					"bServerSide": true,                                                                          
// 					"iDisplayLength": 10,                                                                         
// 					"iDisplayStart": 0,                                                                           
// 					"oSearch": {"sSearch": ""},                                                                   
// 					"sAjaxSource": "${MyHistoryPaginationURL}",                                           
// 					"sDom": 'T<"clear">lfrtip',                                                                   
// 					"sPaginationType": "full_numbers", 
// 					"fnServerData": function ( sSource, aoData, fnCallback ) {                                    
// 						/* Add some extra data to the sender */                                                   
// 						aoData.push( { "name": "more_data", "value": "xxx" } );                                   
// 						$j.getJSON( sSource, aoData, function (json) {                                            
// 							/* Do whatever additional processing you want on the callback, then tell DataTables */
// 							fnCallback(json)                                                                      
// 						} );                                                                                      
// 					}                                                                                             
// 				} );                                                                                              
																												  
				// We need to remove any previous live function                                                   
				$j('.searchResult').die();                                                                        
				// Result links have a specific class style on which we attach click live.                        
				$j('.searchResult').live('click', function() {                                                    
					$j("#body_left").load($j(this).attr("href"));
					Modalbox.hide();
					return false;                                                                                 
				});  
				$j("#categoryHistory").click(function() {															
					Modalbox.show($j(this).attr("href"), {title: "CATEGORY VIEW", width: 760});
					return false;
				});
				
				$j("#generalHistory").click(function(){
					Modalbox.show($j(this).attr("href"), {title: "RESEARCH HISTORY", width: 750});
					return false;
				});
				
			} );                                                                                                  
	</script>

	<script type="text/javascript">
		$j(document).ready(function() {
				
			$j("#closeMyHistory").click(function(){
				Modalbox.hide(); 
				return false;
			});
		});
	</script>