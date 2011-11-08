<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>

	
	<div class="yourSearchDiv">
		Birth in "${placeNameFull}"
	</div>
	
	<table cellpadding="0" cellspacing="0" border="0" class="display"  id="showBirthPeoplePlace">
		<thead>
			<tr></tr>
		</thead>
		<tbody>
			<c:forEach items="${bornedPeople}" var="currentPerson">
			<c:url var="ComparePersonURL" value="/src/peoplebase/ComparePerson.do">
				<c:param name="personId"   value="${currentPerson.personId}" />
			</c:url>
			<tr>
				<c:if test="${currentPerson.personId != 9285 && currentPerson.personId != 3905 && currentPerson.personId != 198}">
					<td><a class="showResult" href="${ComparePersonURL}" title="${currentPerson.mapNameLf}">${currentPerson.personId}</a></td>
					<td><a class="showResult" href="${ComparePersonURL}" title="${currentPerson.mapNameLf}">${currentPerson.mapNameLf}</a></td>
					<td><a class="showResult" href="${ComparePersonURL}" title="${currentPerson.mapNameLf}">${currentPerson.bornYear} ${currentPerson.bornMonth} ${currentPerson.bornDay}</a></td>
				</c:if>
				<c:if test="${currentPerson.personId == 9285 || currentPerson.personId == 3905 || currentPerson.personId == 198}">
					<td><a class="showResult" href="${ComparePersonURL}" title="${currentPerson.mapNameLf}">${currentPerson.personId}</a></td>
					<td><a class="showResult" href="${ComparePersonURL}" title="${currentPerson.mapNameLf}">${currentPerson.mapNameLf}</a></td>
					<td><a class="showResult" href="${ComparePersonURL}" title="${currentPerson.mapNameLf}">${currentPerson.bornYear} ${currentPerson.bornMonth} ${currentPerson.bornDay}</a></td>
				</c:if>
			</tr>
			</c:forEach>
		</tbody>
	</table>
	
	<script type="text/javascript" charset="utf-8">
		//TableToolsInit.sSwfPath = "${zeroClipboard}";

		$j(document).ready(function() {
			
			
			//dynamic field management
			$j("#showBirthPeoplePlace > thead > tr").append('<c:forEach items="${outputFields}" var="outputField"><c:out escapeXml="false" value="<th>${outputField}</th>"/></c:forEach>');

			$j('#showBirthPeoplePlace').dataTable( {
				"aoColumns": [
				              { "sWidth": "20%" },
				              { "sWidth": "80%" },
				              { "sWidth": "80%" }
				              ],
				"bDestroy" : true,
				"sDom": 'T<"clear">lfrtip',
				"sPaginationType": "full_numbers"
			});
			
			$j("#showBirthPeoplePlace_length").css('margin', '0 0 0 0');
			$j("#showBirthPeoplePlace_filter").remove();

			// We need to remove any previous live function
			$j('.showResult').die();
			// Result links have a specific class style on which we attach click live. 
			$j(".showResult").live('click', function() {
				var tabName = $j(this).attr("title");
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

		} );
	</script>
	
	