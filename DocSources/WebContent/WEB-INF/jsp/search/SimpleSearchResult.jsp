<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>

	<c:url var="SimpleSearchPaginationURL" value="/src/SimpleSearchPagination.json">
		<c:param name="simpleSearchPerimeter" value="${command.simpleSearchPerimeter}" />
	</c:url>
	
	<c:url var="ExpandResultsURL" value="/src/ExpandResultsSimpleSearch.do">
		<c:param name="simpleSearchPerimeter" value="${command.simpleSearchPerimeter}" />
	</c:url>

	<c:url var="AdvancedSearchRefineURL" value="/src/ConvertSimpleSearchToAdvancedSearch.do">
		<c:param name="searchUUID" value="${command.searchUUID}"></c:param>
		<c:param name="simpleSearchPerimeter" value="${command.simpleSearchPerimeter}" />
		<c:param name="text" value="${command.text}" />
	</c:url>

	<c:url var="zeroClipboard" value="/swf/ZeroClipboard.swf"/>

	<script type="text/javascript" charset="utf-8">
		//TableToolsInit.sSwfPath = "${zeroClipboard}";

		$j(document).ready(function() {
			//dynamic field management
			$j("#${command.searchUUID} > thead > tr").append('<c:forEach items="${outputFields}" var="outputField"><c:out escapeXml="false" value="<th>${outputField}</th>"/></c:forEach>');
			var test;
			var colsort;
			<c:choose>
				<c:when test="${command.simpleSearchPerimeter.toString() == 'PEOPLE'}">
					test = [[0, "asc"]];
					colsort = [ { "sWidth": "80%", "aTargets": [ "_all" ] }, { "bSortable": false, "aTargets": [4]}];
				</c:when>			
				<c:when test="${command.simpleSearchPerimeter.toString() == 'EXTRACT' || command.simpleSearchPerimeter.toString() == 'SYNOPSIS'}">
					test = [[2, "asc"]];
					colsort = [ { "sWidth": "80%", "aTargets": [ "_all" ] }];
				</c:when>
				<c:when test="${command.simpleSearchPerimeter.toString() == 'VOLUME'}">
					test = [[1, "asc"]];
					colsort = [ { "sWidth": "80%", "aTargets": [ "_all" ] }];
				</c:when>
				<c:when test="${command.simpleSearchPerimeter.toString() == 'PLACE'}">
					test = [[0, "asc"]];
					colsort = [ { "sWidth": "80%", "aTargets": [ "_all" ], "bSortable": false, "aTargets": [2,3,4] }];
				</c:when>
			</c:choose>
			
			var table = $j('#${command.searchUUID}').dataTable( {
				"aoColumnDefs": colsort,
				"aaSorting": test,
				"bDestroy" : true,
				"bProcessing": true,
				"bServerSide": true,
				"iDisplayLength": 20,
				"iDisplayStart": 0,
				"oSearch": {"sSearch": "${command.text}"},
				"sAjaxSource": "${SimpleSearchPaginationURL}",
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
					$j("#recordsNum${command.searchUUID}").text(this.fnSettings()._iRecordsTotal + ' Records');
					$j("tr.odd").mouseover(
							function(){
								$j(this).find("td.sorting_1").css('background-color','#b0addd');
								$j(this).find("td a.searchResult").css('color','#000');
								return false;
							}
					);
					
					$j("tr.odd").mouseout(
							function(){
								$j(this).find("td.sorting_1").css('background-color','#DCC0BA');
								$j(this).find("td a.searchResult").css('color','#6F6B69');
								return false;
							}
					);
					$j("tr.even").mouseover(
							function(){
								$j(this).find("td.sorting_1").css('background-color','#b0addd');
								$j(this).find("td a.searchResult").css('color','#000');
								return false;
							}
					);
					
					$j("tr.even").mouseout(
							function(){
								$j(this).find("td.sorting_1").css('background-color','#EAD9D6');
								$j(this).find("td a.searchResult").css('color','#6F6B69');
								return false;
							}
					);
					var href = '${ExpandResultsURL}';
					href += "&iDisplayStart=" + this.fnSettings()._iDisplayStart;
					href += "&iDisplayLength=" + this.fnSettings()._iDisplayLength;
					href += "&sSearch=" + '${command.text}';
					href += "&iSortingCols=" + this.fnSettings()._iSortingCols;
					href += "&iSortCol_0=" + this.fnSettings().aaSorting[0][0];
					href += "&sSortDir_0=" + this.fnSettings().aaSorting[0][1];
					$j("#expand${command.searchUUID}").attr("href", href);
				}
			});
			
			var searchTable = table.fnSettings();

			// We need to remove any previous live function
			$j('.searchResult').die();
			
			// Result links have a specific class style on which we attach click live. 
			$j('.searchResult').live('click', function() {
				$j("#body_left").load($j(this).attr("href"));
				
				if(!$j(this).parent().parent().hasClass("selected")){
					$j("#${command.searchUUID} tbody").find("tr.selected td.selected").addClass("sorting_1");
					$j("#${command.searchUUID} tbody").find("tr.odd.selected td.selected").css('background-color','#DCC0BA');
					$j("#${command.searchUUID} tbody").find("tr.even.selected td.selected").css('background-color','#EAD9D6');
					$j("#${command.searchUUID} tbody").find("tr.selected td.selected").removeClass("selected");
				
					
					$j("#${command.searchUUID} tbody").find("tr.selected").removeClass("selected");
					var tr = $j(this).parent().parent();
					$j(tr).addClass("selected");
					
					var tdSort = $j(tr).find("td.sorting_1");
					$j(tdSort).removeClass("sorting_1");
					$j(tdSort).addClass("selected");
				}
				
				
				$j.scrollTo("#body_left");
				return false;
			}); 
			
			//MD: This code is for click in any space inside a row
			$j("#${command.searchUUID} tbody tr").live('click', function(){
				$j("#body_left").load($j(this).children().children().attr("href"));
				
				if(!$j(this).hasClass("selected")){
					$j("#${command.searchUUID} tbody").find("tr.selected td.selected").addClass("sorting_1");
					$j("#${command.searchUUID} tbody").find("tr.odd.selected td.selected").css('background-color','#DCC0BA');
					$j("#${command.searchUUID} tbody").find("tr.even.selected td.selected").css('background-color','#EAD9D6');
					$j("#${command.searchUUID} tbody").find("tr.selected td.selected").removeClass("selected");
				
					
					$j("#${command.searchUUID} tbody").find("tr.selected").removeClass("selected");
				
					$j(this).addClass("selected");
				
					var tdSort = $j(this).find("td.sorting_1");
					$j(tdSort).removeClass("sorting_1");
					$j(tdSort).addClass("selected");
					//$j(this).css('background-color','#b0addd');
				}
				
				$j.scrollTo("#body_left");
				return false;
			});

			$j('.tabResult').die();
			$j('.tabResult').live('click', function(){
				var tabName = "Topics " + $j(this).parent().parent().children().first().text();
				var numTab = 0;
				
				//Check if already exist a tab with this person
				var tabExist = false;
				$j("#tabs ul li a").each(function(){
					if(!tabExist)
						numTab++;
					if(this.text == tabName){
						tabExist = true;
					}
				});
				
				if(!tabExist){
					$j( "#tabs" ).tabs( "add" , $j(this).attr("href"), tabName + "</span></a><span class=\"ui-icon ui-icon-close\" title=\"Close Tab\">Remove Tab");
					$j("#tabs").tabs("select", $j("#tabs").tabs("length")-1);
					return false;
				}else{
					$j("#tabs").tabs("select", numTab-1);
					return false;
				}
			});

			$j("#refine${command.searchUUID}").open({width: 960, height: 680, scrollbars: "yes"});
			
			$j(".dataTables_filter").css('display', 'none');
			
			$j("#expand${command.searchUUID}").open({width: 850, height: 600, scrollbars: "yes"});

		} );
	</script>
	
	<div class="yourSearchDiv">
		<p>Your search:
		<font color="red" style="margin-left:5px">${yourSearch}</font></p>
		<p>Total records found:
		<span class="recordsNum" id="recordsNum${command.searchUUID}"></span></p>
		<a id="refine${command.searchUUID}" class="refine" href="${AdvancedSearchRefineURL}">Refine this search</a>
		<a id="print${command.searchUUID}" class="print" href="${AdvancedSearchRefineURL}">Print Records</a>
		<c:if test="${command.simpleSearchPerimeter.toString() == 'EXTRACT' || command.simpleSearchPerimeter.toString() == 'SYNOPSIS'}">
			<a href="#" class="button_medium expand" id="expand${command.searchUUID}">Expand Results</a>
		</c:if>
	</div>

	<table cellpadding="0" cellspacing="0" border="0" class="display"  id="${command.searchUUID}">
		<thead>
			<tr></tr>
		</thead>
		<tbody>
			<tr>
				<td colspan="5" class="dataTables_empty">Loading data from server</td>
			</tr>
		</tbody>
	</table>