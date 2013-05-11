<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>

	<c:url var="PersonalAnnotationsPaginationURL" value="/user/PersonalAnnotationsPagination.json" />
	
	<div id="personalAnnotationsTableDiv">
	<table cellpadding="0" cellspacing="0" border="0" class="display" id="personalAnnotationsTable">
	    <thead>
	        <tr>
	            <th>Annotation Date</th>
	            <th>Title</th>
	            <th>Annotation Text</th>	            
	            <th>Volume Number</th>
	            <th>Folio Type</th>
	            <th>Folio Number</th>
	        </tr>
	    </thead>
	    <tbody>
	        <tr>                                                                                              
	            <td colspan="6" class="dataTables_empty">Loading data from server</td>                        
	        </tr> 
	    </tbody>
	</table>
	</div>
	
	<div id="personalAnnotationsButtons">
		<a id="eraseHistory" class="button_medium" href="#" style="visibility:hidden;">Erase History</a>
		<a id="closePersonalAnnotations" class="button_small" href="#" title="Close PersonalAnnotations window">Close</a>
	</div>


	<script type="text/javascript" charset="utf-8">                                                           
			$j(document).ready(function() {                                                                       
				$j('#personalAnnotationsTable').dataTable( {                                                             
					"aoColumnDefs": [ { "sWidth": "90%", "aTargets": [ "_all" ] }],   
					"aaSorting": [[0, "desc"]],
					"bAutoWidth" : false,
						"aoColumns" : [
						{ sWidth : "128px"},
						{ sWidth : "128px"},
						{ sWidth : "128px"},
						{ sWidth : "128px"},
						{ sWidth : "128px"},
						{ sWidth : "128px"}
						],                           
					"bDestroy" : true,  
					"bFilter" : false,
					"bLengthChange": false,                                                                          
					"bProcessing": true,                                                                          
					"bServerSide": true,                                                                          
					"iDisplayLength": 10,                                                                         
					"iDisplayStart": 0,                                                                           
					"oSearch": {"sSearch": ""},                                                                   
					"sAjaxSource": "${PersonalAnnotationsPaginationURL}",                                           
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
																												  
				// We need to remove any previous live function                                                   
				$j('#personalAnnotationsTable').find('.searchResult').die();                                                                        
				// Result links have a specific class style on which we attach click live.                        
				$j('#personalAnnotationsTable').find('.searchResult').live('click', function() {                                                    
					var tabName = "Personal Annotation"
					var numTab = 0;
																		
					//Check if already exist a tab with this person
					var tabExist = false;
					$j("#tabs ul li a").each(function(){
						var toTest = "";
						toTest += this.text;
						if(!tabExist){
							if(toTest != ""){
								numTab++;
							}
						}
						if(this.text == tabName || toTest.indexOf("Explore Volume ${volume.volNum}${volume.volLetExt}") != -1){
							tabExist = true;
						}
					});
					
					if(!tabExist){
						$j( "#tabs" ).tabs( "add" , $j(this).attr("href"), tabName + "</span></a><span class=\"ui-icon ui-icon-close\" title=\"Close Tab\">Remove Tab");
						$j("#tabs").tabs("select", $j("#tabs").tabs("length")-1);
					}else{
						$j("#tabs").tabs("select", numTab);
						$j('#tabs ul li').eq(numTab).data('loaded', false).find('a').attr('href', $j(this).attr('href'));
						$j("#tabs").tabs("load", numTab);
					}
					Modalbox.hide();
					return false;
				}); 
			} );                                                                                                  
	</script>
		
	<script type="text/javascript">
		$j(document).ready(function() {
			$j("#closePersonalAnnotations").click(function(){
				Modalbox.hide();
				return false;
			});
		});
	</script>