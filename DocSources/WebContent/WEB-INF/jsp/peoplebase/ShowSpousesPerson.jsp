<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>

	<security:authorize ifAnyGranted="ROLE_ADMINISTRATORS, ROLE_ONSITE_FELLOWS, ROLE_DISTANT_FELLOWS">
		<c:url var="EditSpousesPersonURL" value="/de/peoplebase/EditSpousesPerson.do">
			<c:param name="personId"   value="${person.personId}" />
		</c:url>
	</security:authorize>

	<div id="EditSpousesPersonDiv" class="background">
		<div class="title">
			<h5>SPOUSES</h5>
		<security:authorize ifAnyGranted="ROLE_ADMINISTRATORS, ROLE_ONSITE_FELLOWS, ROLE_DISTANT_FELLOWS">
			<c:if test="${person.personId > 0}">
			<a id="EditSpousesPerson" href="${EditSpousesPersonURL}" class="editButton"></a><span id="loading"/>
			</c:if>
		</security:authorize>
		</div>

		<div class="list">
			<c:forEach items="${marriages}" var="currentMarriage">
				<div class="row">
					<div class="value"><a class="linkSpouse" href="#">${currentMarriage.wife}</a></div> 
					<div class="info">Marriage ${currentMarriage.startYear} - ${currentMarriage.endYear} | Death ${currentMarriage.wife.deathYear}</div>
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
			$j("#EditChildrenPerson").css('visibility', 'visible');
	        $j("#EditResearchNotesPerson").css('visibility', 'visible'); 

			$j("#EditSpousesPerson").click(function(){
				$j(this).next().css('visibility', 'visible');
				$j("#EditSpousesPersonDiv").load($j(this).attr("href"));
				return false;
			});
		});
	</script>
</security:authorize>