<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>

<c:url var="VettingHistoryVolumeURL" value="/src/volbase/ShowVettingHistoryVolume.json">
	<c:param name="summaryId" value="${summaryId}"/>
</c:url>

<c:url var="GoBackURL" value="/src/advancedSearchModal.html"/>

<div id="vettingHistoryTableDiv">
    <table cellpadding="0" cellspacing="0" border="0" class="display" id="vettingHistoryTable">
        <thead>
            <tr>
                <th><fmt:message key="volbase.showVettingChronologyVolumeModalWindow.date"/></th>
                <th><fmt:message key="volbase.showVettingChronologyVolumeModalWindow.action"/></th>
                <th><fmt:message key="volbase.showVettingChronologyVolumeModalWindow.who"/></th>
            </tr>
        </thead>
        <tbody>
            <tr>                                                                                              
                <td colspan="3" class="dataTables_empty"><fmt:message key="volbase.showVettingChronologyVolumeModalWindow.loading"/></td>                        
            </tr> 
        </tbody>
    </table>
</div>

<div id="vettingHistoryButtons">
	<a id="close" href="#" title="<fmt:message key="volbase.showVettingChronologyVolumeModalWindow.help.close"/>"><fmt:message key="volbase.showVettingChronologyVolumeModalWindow.close"/></a>
</div>

	<script>
		$j(document).ready(function() {
			$j('#vettingHistoryTable').dataTable( {                                                             
				"aoColumnDefs": [ { "sWidth": "90%", "aTargets": [ "_all" ] }],   
				"aaSorting": [[0, "desc"]],
				"bAutoWidth" : false,
					"aoColumns" : [
					{ sWidth : "150px" },
					{ sWidth : "150px" },
					{ sWidth : "150px" }
					],                           
				"bDestroy" : true,  
				"bFilter" : false,
				"bLengthChange": false,                                                                          
				"bProcessing": true,                                                                          
				"bServerSide": true,                                                                          
				"iDisplayLength": 10,                                                                         
				"iDisplayStart": 0,                                                                           
				"oSearch": {"sSearch": ""},                                                                   
				"sAjaxSource": "${VettingHistoryVolumeURL}",                                           
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
			
			$j("#close").click(
				function(){
					Modalbox.hide();return false;}
					);
			$j("#goBack").click(
				function(){
						Modalbox.show("${GoBackURL}", {title: "ENTRY MENU", width: 750, height: 380});return false;}
						);
		});
	</script>