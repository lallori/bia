<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>

	<security:authorize ifAnyGranted="ROLE_ADMINISTRATORS, ROLE_ONSITE_FELLOWS, ROLE_DISTANT_FELLOWS">
		<c:url var="EditParentsPersonURL" value="/de/peoplebase/EditParentsPerson.do">
			<c:param name="personId"   value="${person.personId}" />
		</c:url>
	</security:authorize>

	<div id="EditParentsPersonDiv" class="background">
		<div class="title">	
			<h5>PARENTS</h5>
		 	<security:authorize ifAnyGranted="ROLE_ADMINISTRATORS, ROLE_ONSITE_FELLOWS, ROLE_DISTANT_FELLOWS">
		 		<c:if test="${person.personId > 0}">
				<a id="EditParentsPerson" href="${EditParentsPersonURL}" class="editButton"></a><span id="loading"/>
				</c:if>
			</security:authorize>
		</div>
		<div class="list">
			<div class="row">
				<div class="item">Father</div> 
		<c:forEach items="${person.parents}" var="currentParent">
			<c:url var="ComparePersonURL" value="/src/peoplebase/ComparePerson.do">
				<c:param name="personId"   value="${currentParent.parent.personId}" />
			</c:url>
			<c:if test="${currentParent.parent.gender == 'M'}">
				<div class="value"><a class="linkParent" href="${ComparePersonURL}">${currentParent.parent}</a></div> 
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
				<div class="value"><a class="linkParent" href="${ComparePersonURL}">${currentParent.parent}</a></div> 
				<div class="info">Born ${currentParent.parent.bornYear} | Death ${currentParent.parent.deathYear}</div>
			</c:if>				
		</c:forEach>
			</div>
		</div>
	</div>

<security:authorize ifAnyGranted="ROLE_ADMINISTRATORS, ROLE_ONSITE_FELLOWS, ROLE_DISTANT_FELLOWS">
	<script type="text/javascript">
		$j(document).ready(function() {
			$j("#EditDetailsPerson").css('visibility', 'visible');
			$j("#EditNamesPerson").css('visibility', 'visible');
	        $j("#EditTitlesOccupationsPerson").css('visibility', 'visible'); 
			$j("#EditChildrenPerson").css('visibility', 'visible');
			$j("#EditSpousesPerson").css('visibility', 'visible');
	        $j("#EditResearchNotesPerson").css('visibility', 'visible'); 

			$j(".linkParent").click(function() {
				$j( "#tabs" ).tabs( "add" , $j(this).attr("href"), "Person</span></a><span class=\"ui-icon ui-icon-close\" title=\"Close Tab\">Remove Tab");
				return false;
			});

			$j("#EditParentsPerson").click(function(){
				$j(this).next().css('visibility', 'visible');
				$j("#EditParentsPersonDiv").load($j(this).attr("href"));
				return false;
			});
		});
	</script>
</security:authorize>
