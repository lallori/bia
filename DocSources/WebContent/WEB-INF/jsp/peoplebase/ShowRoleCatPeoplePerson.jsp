<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>

	
	<c:url var="ShowRoleCatPeoplePersonURL" value="/src/peoplebase/ShowRoleCatPeoplePerson.json"></c:url>
	
	<div class="yourSearchDiv">
		<p><fmt:message key="peoplebase.showRoleCatPeoplePerson.roleCategory"/> <font color="red" style="margin-left:5px">"${roleCat}"</font></p>
		<p><fmt:message key="peoplebase.showRoleCatPeoplePerson.totalRecordsFound"/> <span id="recordsNum${roleCatId}" class="recordsNum"></span></p>
	</div>
	
	<table cellpadding="0" cellspacing="0" border="0" class="display"  id="showRoleCatPeopleId${roleCatId}">
		<thead>
			<tr></tr>
		</thead>
		<tbody>

		</tbody>
	</table>
	
	<script type="text/javascript" charset="utf-8">
		//TableToolsInit.sSwfPath = "${zeroClipboard}";

		$j(document).ready(function() {
			
			
			//dynamic field management
			$j("#showRoleCatPeopleId${roleCatId} > thead > tr").append('<c:forEach items="${outputFields}" var="outputField"><c:out escapeXml="false" value="<th>${outputField}</th>"/></c:forEach>');

			$j('#showRoleCatPeopleId${roleCatId}').dataTable( {
				"aoColumnDefs": [ { "sWidth": "80%", "aTargets": [ "_all" ] }], 
				"aaSorting": [[0, "asc"]],
				"bDestroy" : true,
				"bProcessing": true,
				"bServerSide": true,
				"iDisplayLength": 10,
				"iDisplayStart": 0,
				"oSearch": {"sSearch": "${roleCatId}"},
				"sAjaxSource": "${ShowRoleCatPeoplePersonURL}",
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
				"fnDrawCallback": function(){
					$j("#recordsNum${roleCatId}").text(this.fnSettings()._iRecordsTotal + ' Records');
					
					$j("tr.odd").mouseover(
							function(){
								$j(this).find("td.sorting_1").addClass('rolloverRow');
								return false;
							}
					);
					
					$j("tr.odd").mouseout(
							function(){
								$j(this).find("td.sorting_1").removeClass('rolloverRow');
								return false;
							}
					);
					$j("tr.even").mouseover(
							function(){
								$j(this).find("td.sorting_1").addClass('rolloverRow');
								return false;
							}
					);
					
					$j("tr.even").mouseout(
							function(){
								$j(this).find("td.sorting_1").removeClass('rolloverRow');
								return false;
							}
					);
				}
			});
			
			$j("#showRoleCatPeopleId${roleCatId}_length").css('margin', '0 0 0 0');
			$j("#showRoleCatPeopleId${roleCatId}_filter").remove();

			// We need to remove any previous live function
			$j('.showResult').die();
			 
			
			$j(".showResult").live('click', function() {
				var tabName = $j(this).attr("title");
				var numTab = 0;
				
				//Check if already exist a tab with this person
				var tabExist = false;
				$j("#tabs ul li a").each(function(){
					if(!tabExist){
						if(this.text != ""){
							numTab++;
						}
					}
					if(this.text == tabName){
						tabExist = true;
					}
				});
				
				if(!tabExist){
					$j( "#tabs" ).tabs( "add" , $j(this).attr("href"), tabName + "</span></a><span class=\"ui-icon ui-icon-close\" title=\"Close Tab\">Remove Tab");
					$j("#tabs").tabs("select", $j("#tabs").tabs("length")-1);
					return false;
				}else{
					$j("#tabs").tabs("select", numTab);
					return false;
				}
			});

		} );
	</script>
	
	