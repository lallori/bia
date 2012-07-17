<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>

	<security:authorize ifAnyGranted="ROLE_ADMINISTRATORS, ROLE_ONSITE_FELLOWS, ROLE_DISTANT_FELLOWS">
		<c:url var="EditTitlesOrOccupationsPersonURL" value="/de/peoplebase/EditTitlesOrOccupationsPerson.do">
			<c:param name="personId"   value="${person.personId}" />
		</c:url>
	</security:authorize>

	<div id="EditTitlesOrOccupationsPersonDiv" class="background">
		<div class="title">
			<h5>TITLES / OCCUPATIONS </h5>
			<security:authorize ifAnyGranted="ROLE_ADMINISTRATORS, ROLE_ONSITE_FELLOWS, ROLE_DISTANT_FELLOWS">
				<c:if test="${person.personId > 0}">
				<a id="EditTitlesOrOccupationsPerson" href="${EditTitlesOrOccupationsPersonURL}" class="editButton"></a><span id="loading"/>
				</c:if>
			</security:authorize>
		</div>
		<div class="list">
			<c:forEach items="${person.poLink}" var="currentPoLink">
				<c:url var="ShowTitlesOrOccupationsPeoplePersonURL" value="/src/peoplebase/ShowTitlesOrOccupationsPeoplePerson.do">
					<c:param name="titleOccId" value="${currentPoLink.titleOccList.titleOccId}" />
				</c:url>
				<c:url var="ShowRoleCatPeoplePersonURL" value="/src/peoplebase/ShowRoleCatPeoplePerson.do">
					<c:param name="roleCatId" value="${currentPoLink.titleOccList.roleCat.roleCatId}" />
				</c:url>
				<div class="row">
					<c:if test="${currentPoLink.preferredRole}">
						<div class="value5" title="Preferred Role" id="preferredRoleIcon"></div>
					</c:if>
					<c:if test="${!currentPoLink.preferredRole}">
						<div class="value5"></div>
					</c:if>
					<div class="value60"><a class="linkOccupation" href="${ShowTitlesOrOccupationsPeoplePersonURL}"><b>${currentPoLink.titleOccList.titleOcc}</b></a><br>
					<a class="linkOccupation" href="${ShowRoleCatPeoplePersonURL}">${currentPoLink.titleOccList.roleCat.roleCatMinor}</a></div> 
					<div class="info">Start ${currentPoLink.startDate} | End ${currentPoLink.endDate}</div>
				</div>
			</c:forEach>
		</div>
	</div>


<security:authorize ifAnyGranted="ROLE_ADMINISTRATORS, ROLE_ONSITE_FELLOWS, ROLE_DISTANT_FELLOWS">
	<script type="text/javascript">
		$j(document).ready(function() {
	        $j("#EditDetailsPerson").css('visibility', 'visible'); 
			$j("#EditNamesPerson").css('visibility', 'visible');
			$j("#EditParentsPerson").css('visibility', 'visible');
			$j("#EditChildrenPerson").css('visibility', 'visible');
			$j("#EditSpousesPerson").css('visibility', 'visible');
	        $j("#EditResearchNotesPerson").css('visibility', 'visible'); 

			$j("#EditTitlesOrOccupationsPerson").click(function(){
				$j(this).next().css('visibility', 'visible');
				$j("#EditTitlesOrOccupationsPersonDiv").load($j(this).attr("href"));
				return false;
			});

			$j(".linkOccupation").click(function() {
	        	var tabName = $j(this).text();
				var numTab = 0;
				
				if(tabName.length > 20){
					tabName = tabName.substring(0,17) + "...";
				}
				
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
		});
	</script>
</security:authorize>