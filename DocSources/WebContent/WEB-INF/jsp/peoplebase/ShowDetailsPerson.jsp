<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>

	<security:authorize ifAnyGranted="ROLE_ADMINISTRATORS, ROLE_ONSITE_FELLOWS, ROLE_DISTANT_FELLOWS">
		<c:url var="EditDetailsPersonURL" value="/de/peoplebase/EditDetailsPerson.do">
			<c:param name="personId"   value="${person.personId}" />
		</c:url>
	</security:authorize>
	
	<c:url var="CompareBirthURL" value="/src/geobase/ComparePlace.do">
		<c:param name="placeAllId" value="${person.bornPlace.placeAllId}" />
	</c:url>
	
	<c:url var="CompareDeathURL" value="/src/geobase/ComparePlace.do">
		<c:param name="placeAllId" value="${person.deathPlace.placeAllId}" />
	</c:url>
	
	<c:url var="ShowDocumentsPersonURL" value="/de/peoplebase/ShowDocumentsPerson.do">
		<c:param name="personId" value="${person.personId}" />
	</c:url>

	<div id="EditDetailsPersonDiv" class="background">
		<div class="title">
			<h5>PERSON DETAILS</h5>
			<security:authorize ifAnyGranted="ROLE_ADMINISTRATORS, ROLE_ONSITE_FELLOWS, ROLE_DISTANT_FELLOWS">
				<a id="EditDetailsPerson" href="${EditDetailsPersonURL}" class="editButton"></a><span id="loading"/>
			</security:authorize>
		</div>
		<h3>${person.mapNameLf}</h3>
		
		<c:if test="${docsRelated != 0 && docsRelated != 1}">
			<a href="${ShowDocumentsPersonURL}" class="num_docs" title="Click here to view all documents related">${docsRelated} Documents related</a>
		</c:if>
		<c:if test="${docsRelated == 0}">
			<a class="num_docs">0 Documents related</a>
		</c:if>
		<c:if test="${docsRelated == 1}">
			<a href="${ShowDocumentsPersonURL}" class="num_docs" title="Click here to view all documents related">${docsRelated} Document related</a>
		</c:if>
		<br />
		<br />
		
		<div id="EditPortraitPersonDiv">
			<div id="imgPortraitPerson"></div>
			<p style="text-align:center"><b>Portrait</b></p>
		</div>
		
		<div class="listDetails">
			<div class="row">
				<div class="item">Gender</div> <div class="value">${person.gender}</div>
			</div>
			<div class="row">
				<div class="item">Date of Birth</div> <div class="value">${person.bornYear} ${person.bornMonth} ${person.bornDay}</div>
			</div>
			<div class="row">
				<div class="item">Birth Place</div>
				<c:if test="${person.bornPlace.placeAllId != 53384 && person.bornPlace.placeAllId != 55627 && person.bornPlace.placeAllId != 54332}">
					<div class="value"><a href="${CompareBirthURL}" id="linkSearch" class="linkSearch">${person.bornPlace.placeNameFull}</a></div>
				</c:if>
				<c:if test="${person.bornPlace.placeAllId == 53384 || person.bornPlace.placeAllId == 55627 || person.bornPlace.placeAllId == 54332 }">
					<div class="value">${person.bornPlace.placeNameFull}</div>
				</c:if>
			</div>
			<div class="row">
				<div class="item">Active Start</div> <div class="value">${person.activeStart}</div>
			</div>
			<div class="row">
				<div class="item">Date of Death</div> <div class="value">${person.deathYear} ${person.deathMonth} ${person.deathDay}</div>
			</div>
			<div class="row">
				<div class="item">Death Place</div>
				<c:if test="${person.deathPlace.placeAllId != 53384 && person.deathPlace.placeAllId != 55627 && person.deathPlace.placeAllId != 54332}">
					<div class="value"><a href="${CompareDeathURL}" id="linkSearch" class="linkSearch">${person.deathPlace.placeNameFull}</a></div>
				</c:if>
				<c:if test="${person.deathPlace.placeAllId == 53384 || person.deathPlace.placeAllId == 55627 || person.deathPlace.placeAllId == 54332 }">
					<div class="value">${person.deathPlace.placeNameFull}</div>
				</c:if>
			</div>
			<div class="row">
				<div class="item">Active End</div> <div class="value">${person.activeEnd}</div>
			</div>
		</div>
	</div>

<security:authorize ifAnyGranted="ROLE_ADMINISTRATORS, ROLE_ONSITE_FELLOWS, ROLE_DISTANT_FELLOWS">
	<script type="text/javascript">
		$j(document).ready(function() {
			$j("#EditNamesPerson").css('visibility', 'visible');
	        $j("#EditTitlesOrOccupationsPerson").css('visibility', 'visible'); 
			$j("#EditParentsPerson").css('visibility', 'visible');
			$j("#EditChildrenPerson").css('visibility', 'visible');
			$j("#EditSpousesPerson").css('visibility', 'visible');
	        $j("#EditResearchNotesPerson").css('visibility', 'visible'); 

			$j("#EditDetailsPerson").click(function(){
				$j(this).next().css('visibility', 'visible');
				$j("#EditDetailsPersonDiv").load($j(this).attr("href"));
				return false;
			});
			
			$j(".linkSearch").click(function() {
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
			
			$j(".num_docs").click(function(){
				var tabName = "Docs ${person.mapNameLf}";
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
		});
	</script>
</security:authorize>