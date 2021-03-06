<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>

<c:url var="MyMarkedListPaginationURL" value="/user/MyMarkedListPagination.json" />

<c:url var="EraseElementsMyMarkedListURL" value="/user/EraseElementsMyMarkedList.do" />

<c:url var="ShowConfirmEraseMyMarkedListURL" value="/user/ShowConfirmEraseMyMarkedList.do" />

<c:url var="PrintElementsMyMarkedListURL" value="/user/PrintElementsMyMarkedList.do" />
	
		
<div id="researchHistoryTableDiv">
	<div id="markedListTable_length">
		<label>
			Show
			<select id="table_length" size="1" aria-controls="markedList">
				<option value="10" selected="selected">10</option>
				<option value="20">20</option>
				<option value="30">30</option>
			</select> 
			entries
		</label>
	</div>
	<table cellpadding="0" cellspacing="0" border="0" class="display" id="researchHistoryTable">
	    <thead>
	        <tr>
	            <th>Date</th>
	            <th>Type</th>
	            <th>Volume/Insert/Folio</th>
	            <th>Volume Number</th>
	            <th>Place Name</th>
	            <th>Person Name</th>
	            <th>Select</th>
	        </tr>
	    </thead>
	    <tbody>
	        <tr>                                                                                              
	            <td colspan="5" class="dataTables_empty">Loading data from server</td>                        
	        </tr> 
	    </tbody>
	</table>
</div>
	
<div id="MarketListButtons">
	<a id="eraseList" class="button_medium" href="${ShowConfirmEraseMyMarkedListURL}">Erase List</a>
    <a id="removeSelected" class="button_medium" href="#">Remove selected</a>
    <a id="selectAll" class="button_medium" href="#">Select all</a>
    <a id="printAllItems" class="button_medium" href="#" target="_blank">Print selected</a>
</div>
	
	

	<script type="text/javascript" charset="utf-8">                                                           
			$j(document).ready(function() {                                                                       
				var $markedTable = $j('#researchHistoryTable').dataTable( {                                                             
					"aoColumnDefs": [ { "sWidth": "90%", "aTargets": [ "_all" ] }],   
					"aaSorting": [[0, "desc"]],
					"bAutoWidth" : false,
						"aoColumns" : [
							{ sWidth : "120px" },
							{ sWidth : "120px" },
							{ sWidth : "120px", "bSortable": false },
							{ sWidth : "120px", "bSortable": false },
							{ sWidth : "120px", "bSortable": false },
							{ sWidth : "120px", "bSortable": false },
							{ sWidth : "50px", "bSortable": false }
						],                           
					"bDestroy" : true,  
					"bFilter" : false,
					"bLengthChange": false,
					"bProcessing": true,                                                                          
					"bServerSide": true,                                                                          
					"iDisplayLength": 10,                                                                         
					"iDisplayStart": 0,                                                                           
					"oSearch": {"sSearch": ""},                                                                   
					"sAjaxSource": "${MyMarkedListPaginationURL}",                                           
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
					$j("#body_left").load($j(this).attr("href"));
					Modalbox.hide();
					return false;
				}); 
				
// 				$j('#researchHistoryTable > tbody > tr > td > input:checkbox').live('click', function(){
// 					alert("ok");
// 					return false;
// 				});

				$j("#closeMarketList").click(function(){
					Modalbox.hide();
					return false;
				});
				
				var $toRemove = "";
				
				$j("#removeSelected").click(function(){
					$toRemove = "";
					$j('#researchHistoryTable > tbody > tr > td > input:checked').each(function(){
						$toRemove += $j(this).attr("idElement") + "+";
					});
					if($toRemove != ""){
						$j("#researchHistoryTableDiv").block({ message: $j('#questionRemoveMarked'), 
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
					$j("#questionRemoveMarked").hide();
					// Block is attached to form otherwise this block does not function when we use in transcribe and contextualize document
					$j("#researchHistoryTableDiv").parent().append($j("#questionRemoveMarked"));
					$j(".blockUI").remove();
					return false; 
				}); 
		        
				$j('#yes').click(function() { 
					$j.ajax({ url: '${EraseElementsMyMarkedListURL}', cache: false, data: {"idToErase" : $toRemove} ,success:function(html) { 
//	 	 				$j("#body_left").html(html);
						$markedTable.fnDraw();
						$j("#body_left").load($j("#currentUrl").val());
		 			}});
					$j.unblockUI();
					$j(".blockUI").fadeOut("slow");
					$j("#questionRemoveMarked").hide();
					// Block is attached to form otherwise this block does not function when we use in transcribe and contextualize document
					$j("#researchHistoryTableDiv").parent().append($j("#questionRemoveMarked"));
					$j(".blockUI").remove();						
					return false; 
				}); 
				
				$j("#eraseList").click(function() {
					if($j(".searchResult").length > 0){
						Modalbox.show($j(this).attr("href"), {title: "ERASE MARKED LIST", width: 330, height: 120});
					}
					return false;
				});
				
				$j("#printAllItems").click(function(){
					$toPrint = "";
					$j('#researchHistoryTable > tbody > tr > td > input:checked').each(function(){
						$toPrint += $j(this).attr("idElement") + "+";
					});
	 				if($toPrint != ""){
		 				window.open('${PrintElementsMyMarkedListURL}' + '?idToPrint=' + $toPrint , 'PRINT ELEMENTS', 'width=687,height=700,screenX=0,screenY=0,scrollbars=yes');

// 						Modalbox.hide();	
						return false; 
	 				}else{
	 					return false;
	 				}
				});
				
				$j("#table_length").change(function(){
					$markedTable.fnSettings()._iDisplayLength = $j("#table_length").val();
					$markedTable.fnDraw();
					return false;
				});
				
				$j("#selectAll").click(function(){
					$j('#researchHistoryTable > tbody > tr > td > input:checkbox').each(function(){
						$j(this).prop('checked' ,true);
					});
					return false;
				});
			} );                                                                                                  
	</script>
		
	<script type="text/javascript">
		$j(document).ready(function() {
			
		});
	</script>
	
<div id="questionRemoveMarked" style="display:none; cursor: default"> 
	<h1>Are you sure?</h1> 
	<input type="button" id="yes" value="Yes" /> 
	<input type="button" id="no" value="No" /> 
</div>