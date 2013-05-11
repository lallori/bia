<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>

	<c:url var="ExpandResultsAdvancedSearchURL" value="/src/ExpandResultsAdvancedSearch.json">
		<c:param name="simpleSearchPerimeter" value="${command.simpleSearchPerimeter}" />
	</c:url>
	
	

<%-- 	<c:url var="AdvancedSearchRefineURL" value="/src/ConvertSimpleSearchToAdvancedSearch.do"> --%>
<%-- 		<c:param name="searchUUID" value="${command.searchUUID}"></c:param> --%>
<%-- 		<c:param name="simpleSearchPerimeter" value="${command.simpleSearchPerimeter}" /> --%>
<%-- 		<c:param name="text" value="${command.text}" /> --%>
<%-- 	</c:url> --%>

	<c:url var="zeroClipboard" value="/swf/ZeroClipboard.swf"/>

	<script type="text/javascript" charset="utf-8">
		//TableToolsInit.sSwfPath = "${zeroClipboard}";
		$j.extend($j.expr[":"], {
		  "containsIgnoreCase": function(elem, i, match, array) {
		     return (elem.textContent || elem.innerText || "").toLowerCase().indexOf((match[3] || "").toLowerCase()) >= 0;
		}}); 

		$j(document).ready(function() {
			//dynamic field management
			$j("#ExpandResults > thead > tr").append('<c:forEach items="${outputFields}" var="outputField"><c:out escapeXml="false" value="<th>${outputField}</th>"/></c:forEach>');
									
			var table = $j('#ExpandResults').dataTable( {
				"aoColumnDefs": [ { "sWidth": "100%", "aTargets": [ "_all" ], "bSortable": false, "aTargets": [6] }],
				"bAutoWidth" : false,
				"aoColumns" : [
								{ sWidth : "40px" },
								{ sWidth : "40px" },
								{ sWidth : "40px" },
								{ sWidth : "40px" },
								{ sWidth : "40px" },
								{ sWidth : "40px" },
								{ sWidth : "350px" }
								],      
				"aaSorting": [[parseInt('${command.iSortCol_0}'), "${command.sSortDir_0}"]],
				"bDestroy" : true,
				"bProcessing": true,
				"bServerSide": true,
				"iDisplayLength": "${command.iDisplayLength}",
				"iDisplayStart": "${command.iDisplayStart}",
				"iSortingCols": "${command.iSortingCols}",
// 				"iSortCol_0": "${command.iSortCol_0}",
// 				"sSortDir_0": "${command.sSortDir_0}",
				"oSearch": {"sSearch": "${command.searchUUID}"},
				"sAjaxSource": "${ExpandResultsAdvancedSearchURL}",
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
				"fnDrawCallback" : function(){
// 					$j("#recordsNum${command.searchUUID}").text(this.fnSettings()._iRecordsTotal + ' Records');
					$j("tr.odd").mouseover(
							function(){
								$j(this).find("td.sorting_1").addClass("rolloverRow");
								$j(this).find("td a.searchResult").addClass("rolloverAncor");
								return false;
							}
					);
					
					$j("tr.odd").mouseout(
							function(){
								$j(this).find("td.sorting_1").removeClass("rolloverRow");
								$j(this).find("td a.searchResult").removeClass("rolloverAncor");
								return false;
							}
					);
					$j("tr.even").mouseover(
							function(){
								$j(this).find("td.sorting_1").addClass("rolloverRow");
								$j(this).find("td a.searchResult").addClass("rolloverAncor");
								return false;
							}
					);
					
					$j("tr.even").mouseout(
							function(){
								$j(this).find("td.sorting_1").removeClass("rolloverRow");
								$j(this).find("td a.searchResult").removeClass("rolloverAncor");
								return false;
							}
					);
					
					var test = [];
					test = $j('#textSearch').val().split(" ");
					
// 					$j(".textDoc").each(function(){
// 						var newText = $j(this).text().split(" ").join("</span> <span class='toRemove'>");
// 					  	newText = "<span class='toRemove'>" + newText + "</span>";
// 					  	for(var i = 0; i < test.length; i++){
// 					  		$j(this).html(newText).find('span').end().find(":containsIgnoreCase('" + test[i] + "')").wrap("<span class='highlighted' />");
// 					  		newText = $j(this).html();
// 					  	}
// 						$j(".toRemove").contents().unwrap();

// 					});
				}
			});
			
			var searchTable = table.fnSettings();

			// We need to remove any previous live function
			$j('.searchResult').die();
			
			// Result links have a specific class style on which we attach click live. 
			$j('.searchResult').live('click', function() {
				window.opener.$j("#body_left").load($j(this).attr("href"));
				window.opener.focus();
				
				if(!$j(this).parent().parent().hasClass("selected")){
					$j("#ExpandResults tbody").find("tr.selected td.selected").addClass("sorting_1");
					$j("#ExpandResults tbody").find("tr.odd.selected td.selected").removeClass("rolloverRow").addClass("darkCell");
					$j("#ExpandResults tbody").find("tr.even.selected td.selected").removeClass("rolloverRow").addClass("lightCell");
					$j("#ExpandResults tbody").find("tr.selected td.selected").removeClass("selected");
				
					
					$j("#ExpandResults tbody").find("tr.selected").removeClass("selected");
					var tr = $j(this).parent().parent();
					$j(tr).addClass("selected");
					
					var tdSort = $j(tr).find("td.sorting_1");
					$j(tdSort).removeClass("sorting_1");
					$j(tdSort).addClass("selected");
				}
				
				
				window.opener.$j.scrollTo("#body_left");
				return false;
			}); 
			
			//MD: This code is for click in any space inside a row
			$j("#ExpandResults tbody tr").live('click', function(){
				window.opener.$j("#body_left").load($j(this).children().children().attr("href"));
				window.opener.focus();
				
				if(!$j(this).hasClass("selected")){
					$j("#ExpandResults tbody").find("tr.selected td.selected").addClass("sorting_1");
					$j("#ExpandResults tbody").find("tr.odd.selected td.selected").addClass("darkCell");
					$j("#ExpandResults tbody").find("tr.even.selected td.selected").addClass("lightCell");
					$j("#ExpandResults tbody").find("tr.selected td.selected").removeClass("selected");
				
					
					$j("#ExpandResults tbody").find("tr.selected").removeClass("selected");
				
					$j(this).addClass("selected");
				
					var tdSort = $j(this).find("td.sorting_1");
					$j(tdSort).removeClass("sorting_1");
					$j(tdSort).addClass("selected");
					//$j(this).css('background-color','#b0addd');
				}
				
				window.opener.$j.scrollTo("#body_left");
				return false;
			});

// 			$j('.tabResult').die();
// 			$j('.tabResult').live('click', function(){
// 				var tabName = "Topics " + $j(this).parent().parent().children().first().text();
// 				var numTab = 0;
				
// 				//Check if already exist a tab with this person
// 				var tabExist = false;
// 				$j("#tabs ul li a").each(function(){
// 					if(!tabExist)
// 						numTab++;
// 					if(this.text == tabName){
// 						tabExist = true;
// 					}
// 				});
				
// 				if(!tabExist){
// 					$j( "#tabs" ).tabs( "add" , $j(this).attr("href"), tabName + "</span></a><span class=\"ui-icon ui-icon-close\" title=\"Close Tab\">Remove Tab");
// 					$j("#tabs").tabs("select", $j("#tabs").tabs("length")-1);
// 					return false;
// 				}else{
// 					$j("#tabs").tabs("select", numTab-1);
// 					return false;
// 				}
// 			});

// 			$j("#refine${command.searchUUID}").open({width: 960, height: 680, scrollbars: "yes"});
			
			$j(".dataTables_filter").css('display', 'none');

		} );
	</script>
	
<!-- 	<div class="yourSearchDiv"> -->
<!-- 		<p>Your search: -->
<%-- 		<font color="red" style="margin-left:5px">${yourSearch}</font></p> --%>
<!-- 		<p>Total records found: -->
<%-- 		<span class="recordsNum" id="recordsNum${command.searchUUID}"></span></p> --%>
<%-- 		<a id="refine${command.searchUUID}" class="refine" href="${AdvancedSearchRefineURL}">Refine this search</a> --%>
<%-- 		<a id="print${command.searchUUID}" class="print" href="${AdvancedSearchRefineURL}">Print Records</a> --%>
<%-- 		<a href="#" class="button_medium expand" id="expand${command.searchUUID}">Expand Results</a> --%>
<!-- 	</div> -->

	<table cellpadding="0" cellspacing="0" border="0" class="display"  id="ExpandResults">
		<thead>
			<tr></tr>
		</thead>
		<tbody>
			<tr>
				<td colspan="7" class="dataTables_empty">Loading data from server</td>
			</tr>
		</tbody>
	</table>
	
	<input id="textSearch" type="hidden" value="${yourSearch}" />