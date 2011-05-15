<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>

	<security:authorize ifAnyGranted="ROLE_ADMINISTRATORS, ROLE_ONSITE_FELLOWS, ROLE_DISTANT_FELLOWS">
		<c:url var="EditChildrenPersonURL" value="/de/peoplebase/EditChildrenPerson.do">
			<c:param name="personId"   value="${person.personId}" />
		</c:url>
	</security:authorize>

	<div id="EditChildrenPersonDiv" class="background">
		<div class="title">
			<h5>CHILDREN</h5>
	 	<security:authorize ifAnyGranted="ROLE_ADMINISTRATORS, ROLE_ONSITE_FELLOWS, ROLE_DISTANT_FELLOWS">
	 		<c:if test="${person.personId > 0}">
			<a id="EditChildrenPerson" href="${EditChildrenPersonURL}" class="editButton"></a><span id="loading"/>
			</c:if>
		</security:authorize>
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

<security:authorize ifAnyGranted="ROLE_ADMINISTRATORS, ROLE_ONSITE_FELLOWS, ROLE_DISTANT_FELLOWS">
	<script type="text/javascript">
		$j(document).ready(function() {
			$j("#EditDetailsPerson").css('visibility', 'visible');
			$j("#EditNamesPerson").css('visibility', 'visible');
	        $j("#EditTitlesOccupationsPerson").css('visibility', 'visible'); 
			$j("#EditParentsPerson").css('visibility', 'visible');
			$j("#EditSpousesPerson").css('visibility', 'visible');
	        $j("#EditResearchNotesPerson").css('visibility', 'visible'); 

	        $j(".linkChild").click(function() {
				$j( "#tabs" ).tabs( "add" , $j(this).attr("href"), "Person</span></a><span class=\"ui-icon ui-icon-close\" title=\"Close Tab\">Remove Tab");
				return false;
			});
	        
	        $j("#EditChildrenPerson").click(function(){
				$j(this).next().css('visibility', 'visible');
				$j("#EditChildrenPersonDiv").load($j(this).attr("href"));
				return false;
			});
		});
	</script>
</security:authorize>