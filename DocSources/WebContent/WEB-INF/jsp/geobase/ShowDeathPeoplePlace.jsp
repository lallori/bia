<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>

	
	<div class="yourSearchDiv">
		Death in "${placeNameFull}"
	</div>
	
	<table cellpadding="0" cellspacing="0" border="0" class="display"  id="showDeathPeoplePlace">
		<thead>
			<tr></tr>
		</thead>
		<tbody>
			<c:forEach items="${deathPeople}" var="currentPerson">
			<c:url var="ShowPersonURL" value="/src/peoplebase/ShowPerson.do">
				<c:param name="personId"   value="${currentPerson.personId}" />
			</c:url>
			<tr>
				<td><a class="searchResult" href="${ShowPersonURL}">${currentPerson.personId}</a></td>
				<td><a class="searchResult" href="${ShowPersonURL}">${currentPerson.mapNameLf}</a></td>
				<td><a class="searchResult" href="${ShowPersonURL}">${currentPerson.deathYear} ${currentPerson.deathMonth} ${currentPerson.deathDay}</a></td>
			</tr>
			</c:forEach>
		</tbody>
	</table>
	
	<script type="text/javascript" charset="utf-8">
		//TableToolsInit.sSwfPath = "${zeroClipboard}";

		$j(document).ready(function() {
			
			
			//dynamic field management
			$j("#showDeathPeoplePlace > thead > tr").append('<c:forEach items="${outputFields}" var="outputField"><c:out escapeXml="false" value="<th>${outputField}</th>"/></c:forEach>');

			$j('#showDeathPeoplePlace').dataTable( {
				"aoColumns": [
				              { "sWidth": "20%" },
				              { "sWidth": "80%" },
				              { "sWidth": "80%" }
				              ],
				"bDestroy" : true,
				"sDom": 'T<"clear">lfrtip',
				"sPaginationType": "full_numbers"
			});
			
			$j("#showDeathPeoplePlace_length").css('margin', '0 0 0 0');
			$j("#showDeathPeoplePlace_filter").remove();

			// We need to remove any previous live function
			//$j('.searchResult').die();
			// Result links have a specific class style on which we attach click live. 
			//$j('.searchResult').live('click', function() {
				//$j("#body_left").load($j(this).attr("href"));
				//return false;
			//}); 

		} );
	</script>
	
	