<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>

	<c:url var="AdvancedSearchPaginationURL" value="/src/AdvancedSearchPagination.json">
		<c:param name="searchType" value="${command.searchType}" />
	</c:url>

	<c:url var="AdvancedSearchRefineURL" value="/src/AdvancedSearch.do">
		<c:param name="searchUUID" value="${command.searchUUID}"></c:param>
		<c:param name="searchType" value="${command.searchType}" />
	</c:url>
	
	<c:url var="ExpandResultsURL" value="/src/ExpandResultsAdvancedSearch.do">
		<c:param name="searchUUID" value="${command.searchUUID}" />
	</c:url>
	
	<c:url var="PrintSearchURL" value="/src/PrintSearch.do">
		<c:param name="searchUUID" value="${command.searchUUID}"></c:param>
	</c:url>

	<script type="text/javascript" charset="utf-8">
		$j(document).ready(function() {
			//dynamic field management
			$j("#${command.searchUUID} > thead > tr").append('<c:forEach items="${outputFields}" var="outputField"><fmt:message key="${outputField}" var="outputFieldMessage"/><c:out escapeXml="false" value="<th>${outputFieldMessage}</th>"/></c:forEach>');

			var test;
			var colsort;
			<c:choose>
				<c:when test="${command.searchType.toString() == 'PEOPLE'}">
					test = [[0, "asc"]];
					colsort = [ { "sWidth": "80%", "aTargets": [ "_all" ] }, { "bSortable": false, "aTargets": [4]}];
				</c:when>			
				<c:when test="${command.searchType.toString() == 'DOCUMENT'}">
					test = [[2, "asc"]];
					colsort = [ { "sWidth": "80%", "aTargets": [ "_all" ] }];
				</c:when>
				<c:when test="${command.searchType.toString() == 'VOLUME'}">
					test = [[1, "asc"]];
					colsort = [ { "sWidth": "80%", "aTargets": [ "_all" ] }];
				</c:when>
				<c:when test="${command.searchType.toString() == 'PLACE'}">
					test = [[0, "asc"]];
					colsort = [ { "sWidth": "80%", "aTargets": [ "_all" ], "bSortable": false, "aTargets": [2,3,4] }];
				</c:when>
			</c:choose>
			
			$j('#${command.searchUUID}').dataTable( {
				"aoColumnDefs": colsort, 
				"aaSorting": test,
				"bDestroy" : true,
				"bFilter" : false,
				"bProcessing": true,
				"bServerSide": true,
				"iDisplayLength": 10,
				"iDisplayStart": 0,
				"oSearch": {"sSearch": ""},
				"sAjaxSource": "${AdvancedSearchPaginationURL}",
				"sDom": 'T<"clear">lfrtip',
				"sPaginationType": "full_numbers",
				"fnServerData": function ( sSource, aoData, fnCallback ) {
					/* Add some extra data to the sender */
					aoData.push( { "name": "searchUUID", "value": "${command.searchUUID}" } );
					$j.getJSON( sSource, aoData, function (json) { 
						/* Do whatever additional processing you want on the callback, then tell DataTables */
						fnCallback(json)
					} );
				},
				"fnDrawCallback" : function(){
					var idTabTitle;
					var titleString
					$j("#recordsNum${command.searchUUID}").text(this.fnSettings()._iRecordsTotal + ' Records');
					$j("tr.odd").mouseover(function(){
						$j(this).find("td.sorting_1").addClass('rolloverRow');
						$j(this).find("td a.searchResult").addClass('rolloverAncor');
						return false;
					});
					$j("tr.odd").mouseout(function(){
						$j(this).find("td.sorting_1").removeClass('rolloverRow');
						$j(this).find("td a.searchResult").removeClass('rolloverAncor');
						return false;
					});
					$j("tr.even").mouseover(function(){
						$j(this).find("td.sorting_1").addClass('rolloverRow');
						$j(this).find("td a.searchResult").addClass('rolloverAncor');
						return false;
					});
					$j("tr.even").mouseout(function(){
						$j(this).find("td.sorting_1").removeClass('rolloverRow');
						$j(this).find("td a.searchResult").removeClass('rolloverAncor');
						return false;
					});
					<c:choose>
					<c:when test="${command.searchType.toString() == 'PEOPLE'}">
						idTabTitle = $j("#refine${command.searchUUID}").parent().attr("aria-labelledby");
						titleString = $j("#" + idTabTitle).find("span").text();
						if(titleString.charAt(titleString.length - 1) != parseInt($j("#personSearchTabNumber").val() - 1)){
							$j("#" + idTabTitle).find("span").append(" " + $j("#personSearchTabNumber").val());
							$j("#personSearchTabNumber").val(parseInt($j("#personSearchTabNumber").val()) + 1);
						}
					</c:when>			
					<c:when test="${command.searchType.toString() == 'DOCUMENT'}">
						idTabTitle = $j("#refine${command.searchUUID}").parent().attr("aria-labelledby");
						titleString = $j("#" + idTabTitle).find("span").text();
						if(titleString.charAt(titleString.length - 1) != parseInt($j("#documentSearchTabNumber").val() - 1)){
							$j("#" + idTabTitle).find("span").append(" " + $j("#documentSearchTabNumber").val());
							$j("#documentSearchTabNumber").val(parseInt($j("#documentSearchTabNumber").val()) + 1);
						}
						var href = '${ExpandResultsURL}';
						href += "&iDisplayStart=" + this.fnSettings()._iDisplayStart;
						href += "&iDisplayLength=" + this.fnSettings()._iDisplayLength;
						href += "&iSortCol_0=" + this.fnSettings().aaSorting[0][0];
						href += "&sSortDir_0=" + this.fnSettings().aaSorting[0][1];
						$j("#expand${command.searchUUID}").attr("href", href);
					</c:when>
					<c:when test="${command.searchType.toString() == 'VOLUME'}">
						idTabTitle = $j("#refine${command.searchUUID}").parent().attr("aria-labelledby");
						titleString = $j("#" + idTabTitle).find("span").text();
						if(titleString.charAt(titleString.length - 1) != parseInt($j("#volumeSearchTabNumber").val() - 1)){
							$j("#" + idTabTitle).find("span").append(" " + $j("#volumeSearchTabNumber").val());
							$j("#volumeSearchTabNumber").val(parseInt($j("#volumeSearchTabNumber").val()) + 1);
						}
					</c:when>
					<c:when test="${command.searchType.toString() == 'PLACE'}">
						idTabTitle = $j("#refine${command.searchUUID}").parent().attr("aria-labelledby");
						titleString = $j("#" + idTabTitle).find("span").text();
						if(titleString.charAt(titleString.length - 1) != parseInt($j("#placeSearchTabNumber").val() - 1)){
							$j("#" + idTabTitle).find("span").append(" " + $j("#placeSearchTabNumber").val());
							$j("#placeSearchTabNumber").val(parseInt($j("#placeSearchTabNumber").val()) + 1);
						}
					</c:when>
				</c:choose>
				}
			});

			// We need to remove any previous live function
			$j('.searchResult').die();
			// Result links have a specific class style on which we attach click live. 
			$j('.searchResult').live('click', function() {
				$j("#body_left").load($j(this).attr("href"));
				
				if(!$j(this).parent().parent().hasClass("selected")){
					$j("#${command.searchUUID} tbody").find("tr.selected td.selected").addClass("sorting_1");
					$j("#${command.searchUUID} tbody").find("tr.odd.selected td.selected").removeClass("rolloverRow").addClass("darkCell");
					$j("#${command.searchUUID} tbody").find("tr.even.selected td.selected").removeClass("rolloverRow").addClass("lightCell");
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
					$j("#${command.searchUUID} tbody").find("tr.odd.selected td.selected").removeClass("rolloverRow").addClass("darkCell");
					$j("#${command.searchUUID} tbody").find("tr.even.selected td.selected").removeClass("rolloverRow").addClass("lightCell");
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
			
			$j("#refine${command.searchUUID}").open({width: 960, height: 350, scrollbars: "yes"});
			
			$j("#expand${command.searchUUID}").open({width: 850, height: 600, scrollbars: "yes"});
			
			$j(".tabLink").open({width: 960, height: 350, scrollbars: "yes"});	
			
			$j("#yourSearch${command.searchUUID}").click(function(){
				$j("#yourSearchDialog${command.searchUUID}").dialog({
					  autoOpen : false,
					  modal: true,
					  resizable: false,
					  width: 300,
					  buttons: {
						  Ok: function() {
							  $j(this).dialog("close");
							  $j("#yourSearchDialog${command.searchUUID}").dialog('destroy');
							  $j("#yourSearchDialog${command.searchUUID}").appendTo("#yourSearchDiv").css("display", "none");
						  }
					  }
				  });
				$j("#yourSearchDialog${command.searchUUID}").dialog('open');
				return false;
			});
			
			/*var idTabTitle = $j("#refine${command.searchUUID}").parent().attr("aria-labelledby");
			$j("#" + idTabTitle).find("span").append(" " + $j("#documentSearchTabNumber").val());
			$j("#documentSearchTabNumber").val(parseInt($j("#documentSearchTabNumber").val()) + 1);*/
			$j("#print${command.searchUUID}").click(function(){
				window.open($j(this).attr("href") , 'PRINT ELEMENTS', 'width=687,height=700,screenX=0,screenY=0,scrollbars=yes');
				return false;
			});
			
		});
	</script>
	
	<div class="yourSearchDiv">
		<p>Your search:
		<c:if test="${yourSearch.length() > 45}">
			<a id="yourSearch${command.searchUUID}" href="${AdvancedSearchRefineURL}">${yourSearch.substring(0,41)}...</a></p>
		</c:if>
		<c:if test="${yourSearch.length() <= 45}">
			<font color="red" style="margin-left:5px">${yourSearch}</font></p>
		</c:if>
		<p>Total records found:
		<span class="recordsNum" id="recordsNum${command.searchUUID}"></span></p>
		<a id="refine${command.searchUUID}" class="refine button_medium" href="${AdvancedSearchRefineURL}">Refine this search</a>
		<a id="print${command.searchUUID}" class="print button_medium" href="#" style="visibility:hidden;">Print Records</a>
		<c:if test="${command.searchType.toString() == 'DOCUMENT'}">
			<a href="#" class="button_medium expand" id="expand${command.searchUUID}">Expand Results</a>
		</c:if>
		
		<div id="yourSearchDialog${command.searchUUID}" title="Your search" style="display:none"> 
			<p>
				${yourSearch}
			</p>
		</div>
	</div>
	
	

	<table cellpadding="0" cellspacing="0" border="0" class="display"  id="${command.searchUUID}">
		<thead>
			<tr>
			</tr>
		</thead>
		<tbody>
			<tr>
				<td colspan="5" class="dataTables_empty">Loading data from server</td>
			</tr>
		</tbody>
	</table>