<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>

<c:url var="ShowPersonURL" value="/src/peoplebase/ShowPerson.do">
	<c:param name="personId" value="${person.personId}" />
</c:url>

<c:url var="CompareBirthURL" value="/src/geobase/ComparePlace.do">
	<c:param name="placeAllId" value="${person.bornPlace.placeAllId}" />
</c:url>

<c:url var="CompareDeathURL" value="/src/geobase/ComparePlace.do">
	<c:param name="placeAllId" value="${person.deathPlace.placeAllId}" />
</c:url>

<security:authorize ifAnyGranted="ROLE_ADMINISTRATORS, ROLE_ONSITE_FELLOWS, ROLE_DISTANT_FELLOWS">
	<div>
		<a href="${ShowPersonURL}" id="editLink${person.personId}" class="showOrEditCompare">Show or Edit this Person</a>
	</div>
</security:authorize>
<security:authorize ifAnyGranted="ROLE_COMMUNITY_USERS, ROLE_DIGITIZATION_TECHNICIANS, ROLE_GUESTS">
	<div>
		<a href="${ShowPersonURL}" id="editLink${person.personId}" class="showCompare">Show this Person</a>
	</div>
</security:authorize>

<div id="EditDetailsPersonDiv" class="background">
	<div class="title">
		<h5>PERSON DETAILS</h5>
	</div>
	<h3>${person.mapNameLf}</h3>
	
	<c:if test="${docsRelated != 0}">
		<span class="num_docs">${docsRelated} Documents related</span>
	</c:if>
	<c:if test="${docsRelated == 0}">
		<a class="num_docs">0 Document related</a>
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
			<div class="item">Birth Place</div><div class="value"><a class="linkSearch" href="${CompareBirthURL}">${person.bornPlace.placeNameFull}</a></div>
		</div>
		<div class="row">
			<div class="item">Active Start</div> <div class="value">${person.activeStart}</div>
		</div>
		<div class="row">
			<div class="item">Date of Death</div> <div class="value">${person.deathYear} ${person.deathMonth} ${person.deathDay}</div>
		</div>
		<div class="row">
			<div class="item">Death Place</div> <div class="value"><a class="linkSearch" href="${CompareDeathURL}">${person.deathPlace.placeNameFull}</a></div>
		</div>
		<div class="row">
			<div class="item">Active End</div> <div class="value">${person.activeEnd}</div>
		</div>
	</div>
</div>


<div id="EditNamesPersonDiv" class="background">
	<div class="title">
		<h5>NAMES </h5>
	</div>
	<div class="list">
		<c:forEach items="${person.altName}" var="currentName">
			<div class="row">
				<div class="item">${currentName.nameType}</div> 
				<c:if test="${currentName.nameType == 'Family' }">
					<c:url var="ShowFamilyPersonURL" value="/src/peoplebase/ShowFamilyPerson.do">
						<c:param name="altName" value="${currentName.altName}" />
					</c:url>
						<div class="value"><a class="linkFamilyCompare" href="${ShowFamilyPersonURL}">${currentName.namePrefix} ${currentName.altName}</a></div>
				</c:if>
				<c:if test="${currentName.nameType != 'Family' }"> 
					<div class="value">${currentName.namePrefix} ${currentName.altName}</div>
				</c:if>  
			</div>
		</c:forEach>
	</div>	
</div>


<div id="EditTitlesOrOccupationsPersonDiv" class="background">
	<div class="title">	
		<h5>TITLES / OCCUPATIONS</h5>
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
					<div class="value60"><a class="linkOccupationCompare" href="${ShowTitlesOrOccupationsPeoplePersonURL}"><b>${currentPoLink.titleOccList.titleOcc}</b></a><br>
					<a class="linkOccupationCompare" href="${ShowRoleCatPeoplePersonURL}">${currentPoLink.titleOccList.roleCat.roleCatMinor}</a></div> 
					<div class="info">Start ${currentPoLink.startDate} | End ${currentPoLink.endDate}</div>
				</div>
		</c:forEach>
	</div>
</div>


<div id="EditParentsPersonDiv" class="background">
	<div class="title">	
		<h5>PARENTS</h5>
	</div>
	<div class="list">
		<div class="row">
				<div class="item">Father</div> 
		<c:forEach items="${person.parents}" var="currentParent">
			<c:url var="ComparePersonURL" value="/src/peoplebase/ComparePerson.do">
				<c:param name="personId"   value="${currentParent.parent.personId}" />
			</c:url>
			<c:if test="${currentParent.parent.gender == 'M'}">
				<div class="value"><a class="linkParentCompare" href="${ComparePersonURL}">${currentParent.parent}</a></div> 
				<div class="info">Born ${currentParent.parent.bornYear} | Death ${currentParent.parent.deathYear}</div>
			</c:if>				
		</c:forEach>
			</div>
			<div class="row">
				<div class="item">Mother</div> 
		<c:forEach items="${person.parents}" var="currentParent">
			<c:url var="ComparePersonURL" value="/src/peoplebase/ComparePerson.do">
				<c:param name="personId"   value="${currentParent.parent.personId}" />
			</c:url>
			<c:if test="${currentParent.parent.gender == 'F'}">
				<div class="value"><a class="linkParentCompare" href="${ComparePersonURL}">${currentParent.parent}</a></div> 
				<div class="info">Born ${currentParent.parent.bornYear} | Death ${currentParent.parent.deathYear}</div>
			</c:if>				
		</c:forEach>
			</div>
	</div>
</div>

<div id="EditChildrenPersonDiv" class="background">
	<div class="title">	
		<h5>CHILDREN</h5>
	</div>
	<div class="list">
	<c:forEach items="${person.children}" var="currentChild">
				<c:url var="ComparePersonURL" value="/src/peoplebase/ComparePerson.do">
					<c:param name="personId"   value="${currentChild.child.personId}" />
				</c:url>
				<div class="row">
					<div class="value"><a class="linkChild" href="${ComparePersonURL}">${currentChild.child}</a></div> 
					<div class="info">Birth ${currentChild.child.bornYear} | Death ${currentChild.child.deathYear}</div>
				</div>
	</c:forEach>
	</div>
</div>

<div id="EditSpousesPersonDiv" class="background">
	<div class="title">	
		<h5>SPOUSES</h5>
	</div>
	<div class="list">
	<c:forEach items="${marriages}" var="currentMarriage">
				<div class="row">
					<c:if test="${person.personId == currentMarriage.husband.personId}">
						<c:url var="ComparePersonURL" value="/src/peoplebase/ComparePerson.do">
							<c:param name="personId"   value="${currentMarriage.wife.personId}" />
						</c:url>
						<div class="value"><a class="linkSpouse" href="${ComparePersonURL}">${currentMarriage.wife}</a></div> 
						<div class="info">Marriage ${currentMarriage.startYear} - ${currentMarriage.endYear} | Death ${currentMarriage.wife.deathYear}</div>
					</c:if>
					<c:if test="${person.personId == currentMarriage.wife.personId}">
						<c:url var="ComparePersonURL" value="/src/peoplebase/ComparePerson.do">
							<c:param name="personId"   value="${currentMarriage.husband.personId}" />
						</c:url>
						<div class="value"><a class="linkSpouse" href="${ComparePersonURL}">${currentMarriage.husband}</a></div> 
						<div class="info">Marriage ${currentMarriage.startYear} - ${currentMarriage.endYear} | Death ${currentMarriage.husband.deathYear}</div>
					</c:if>
				</div>
			</c:forEach>
	</div>
</div>

<div id="EditResearchNotesPersonDiv" class="background">
	<div class="title">	
	<h5>RESEARCH NOTES</h5>
	</div>

	<div class="list">
		<div class="row">
			<div class="value">
				${person.bioNotes}
			</div>
		</div>
	</div>
</div>

<script type="text/javascript">
		$j(document).ready(function(){
			$j("#editLink${person.personId}").click(function(){
				$j("#body_left").load($j(this).attr("href"));
				var selected = $j("#tabs").tabs('option', 'selected');
				$j("#tabs").tabs('remove', selected);
				return false;
			});
			
			$j(".linkSearch").click(function() {
				var tabName = $j(this).text();
				var numTab = 0;
				
				if(tabName.length > 20){
					tabName = tabName.substring(0,17) + "...";
				}
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
			
			$j(".linkOccupationCompare").click(function() {
				var tabName = $j(this).text();
				var numTab = 0;
				
				if(tabName.length > 20){
					tabName = tabName.substring(0,17) + "...";
				}
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
			
			$j(".linkParentCompare").click(function() {
				var tabName = $j(this).text();
				var numTab = 0;
				
				if(tabName.length > 20){
					tabName = tabName.substring(0,17) + "...";
				}
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
			
			$j(".linkChild").click(function() {
	        	var tabName = $j(this).text();
				var numTab = 0;
				
				if(tabName.length > 20){
					tabName = tabName.substring(0,17) + "...";
				}
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
			
			$j(".linkFamilyCompare").click(function() {
	        	var tabName = $j(this).text();
	        	var is_chrome = navigator.userAgent.toLowerCase().indexOf('chrome') > -1;
	        	if(is_chrome){
	        		//For remove whitespaces at start/end of a string
	        		tabName = tabName.replace(/^\s\s*/, '').replace(/\s\s*$/, '');
	        	}

				var numTab = 0;
				
				if(tabName.length > 20){
					tabName = tabName.substring(0,17) + "...";
				}
				
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
			
			$j(".linkSpouse").click(function(){
				var tabName = $j(this).text();
				var numTab = 0;
				
				if(tabName.length > 20){
					tabName = tabName.substring(0,17) + "...";
				}
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
