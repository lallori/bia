<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>

	<security:authorize ifAnyGranted="ROLE_ADMINISTRATORS, ROLE_ONSITE_FELLOWS, ROLE_DISTANT_FELLOWS">
		<c:url var="EditNamesPersonURL" value="/de/peoplebase/EditNamesPerson.do">
			<c:param name="personId"   value="${person.personId}" />
		</c:url>
	</security:authorize>
	
	<div id="EditNamesPersonDiv" class="background">
		<div class="title">
			<h5>NAMES </h5>
			<security:authorize ifAnyGranted="ROLE_ADMINISTRATORS, ROLE_ONSITE_FELLOWS, ROLE_DISTANT_FELLOWS">
				<c:if test="${person.personId > 0}">
				<a id="EditNamesPerson" href="${EditNamesPersonURL}" class="editButton"></a><span id="loading"/>
				</c:if>
			</security:authorize>
		</div>
		<div class="list">
			<c:forEach items="${person.altName}" var="currentName">
				<div class="row">
					<div class="item">${currentName.nameType}</div>
					<c:if test="${currentName.nameType == 'Family' }">
					<c:url var="ShowFamilyPersonURL" value="/src/peoplebase/ShowFamilyPerson.do">
						<c:param name="altName" value="${currentName.altName}" />
					</c:url>
						<div class="value"><a class="linkFamily" href="${ShowFamilyPersonURL}">${currentName.namePrefix} ${currentName.altName}</a></div>
					</c:if>
					<c:if test="${currentName.nameType != 'Family' }"> 
						<div class="value">${currentName.namePrefix} ${currentName.altName}</div>
					</c:if> 
				</div>
			</c:forEach>
		</div>	
	</div>

<security:authorize ifAnyGranted="ROLE_ADMINISTRATORS, ROLE_ONSITE_FELLOWS, ROLE_DISTANT_FELLOWS">
	<script type="text/javascript">
		$j(document).ready(function() {
			$j("#EditDetailsPerson").css('visibility', 'visible');
	        $j("#EditTitlesOrOccupationsPerson").css('visibility', 'visible'); 
			$j("#EditParentsPerson").css('visibility', 'visible');
			$j("#EditChildrenPerson").css('visibility', 'visible');
			$j("#EditSpousesPerson").css('visibility', 'visible');
	        $j("#EditResearchNotesPerson").css('visibility', 'visible'); 

			$j("#EditNamesPerson").click(function(){
				$j(this).next().css('visibility', 'visible');
				$j("#EditNamesPersonDiv").load($j(this).attr("href"));
				return false;
			});

			$j(".linkFamily").click(function() {
	        	var tabName = $j(this).text();
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
					$j( "#tabs" ).tabs( "add" , $j(this).attr("href"), $j(this).text() + "</span></a><span class=\"ui-icon ui-icon-close\" title=\"Close Tab\">Remove Tab");
					$j("#tabs").tabs("select", $j("#tabs").tabs("length")-1);
					return false;
				}else{
					$j("#tabs").tabs("select", numTab-1);
					return false;
				}
			});
			
		});
	</script>
</security:authorize>