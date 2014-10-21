<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>

	<security:authorize ifAnyGranted="ROLE_ADMINISTRATORS, ROLE_ONSITE_FELLOWS, ROLE_FELLOWS">
		<c:url var="EditSpousesPersonURL" value="/de/peoplebase/EditSpousesPerson.do">
			<c:param name="personId"   value="${person.personId}" />
		</c:url>
	</security:authorize>

	<div id="EditSpousesPersonDiv" class="background">
		<div class="title">
			<h5><fmt:message key="peoplebase.showSpousesPerson.spouses"/></h5>
		<security:authorize ifAnyGranted="ROLE_ADMINISTRATORS, ROLE_ONSITE_FELLOWS, ROLE_FELLOWS">
			<c:if test="${person.personId > 0}">
				<a id="EditSpousesPerson" href="${EditSpousesPersonURL}" class="editButton" />
				<span id="loading"/>
			</c:if>
		</security:authorize>
		</div>

		<div class="list">
			<c:forEach items="${marriages}" var="currentMarriage">
				<c:choose>
					<c:when test="${person.personId == currentMarriage.husband.personId}">
						<c:set var="spouse" value="${currentMarriage.wife}" />
					</c:when>
					<c:otherwise>
						<c:set var="spouse" value="${currentMarriage.husband}" />
					</c:otherwise>
				</c:choose>
				<div class="row">
					<c:url var="ComparePersonURL" value="/src/peoplebase/ComparePerson.do">
						<c:param name="personId"   value="${spouse.personId}" />
					</c:url>
					<div class="value">
						<a class="linkSpouse" href="${ComparePersonURL}">
							${spouse}
							<input type="hidden" style="display:none;" class="tabId" value="peopleId${spouse.personId}" />
						</a>
					</div>
					<div class="info">
						<fmt:message key="peoplebase.showSpousesPerson.marriage"/> ${currentMarriage.startYear} - ${currentMarriage.endYear}
						<c:if test="${not empty currentMarriage.marTerm and currentMarriage.marTerm != 'Unknown'}">
						| ${currentMarriage.marTerm}
						</c:if>
					</div>
				</div>
			</c:forEach>
		</div>
	</div>

<security:authorize ifAnyGranted="ROLE_ADMINISTRATORS, ROLE_ONSITE_FELLOWS, ROLE_FELLOWS, SROLE_DIGITIZATION_TECHNICIANS, ROLE_COMMUNITY_USERS">
	<script type="text/javascript">
		$j(document).ready(function() {
			$j("#EditDetailsPerson").css('visibility', 'visible');
			$j("#EditNamesPerson").css('visibility', 'visible');
	        $j("#EditTitlesOrOccupationsPerson").css('visibility', 'visible'); 
			$j("#EditParentsPerson").css('visibility', 'visible');
			$j("#EditChildrenPerson").css('visibility', 'visible');
	        $j("#EditResearchNotesPerson").css('visibility', 'visible'); 

			$j("#EditSpousesPerson").click(function(){
				$j(this).next().css('visibility', 'visible');
				$j("#EditSpousesPersonDiv").load($j(this).attr("href"));
				return false;
			});

			$j(".linkSpouse").click(function(){
				var tabName = $j(this).text();
				var numTab = 0;
				var id = $j(this).find(".tabId").val();
				
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
					if(this.text == tabName || this.text == "PersonId#" + id.substring(8, id.length) + " - " + tabName || this.text.substring(this.text.indexOf(" - ") + 3, this.text.length) == tabName){
						if($j(this).find("input").val() == id){
							tabExist = true;
						}else{
							//To change name of the tab
							if(this.text.indexOf("#") == -1){
								$j(this).find("span").text("PersonId#" + $j(this).find("input").val().substring(8, $j(this).find("input").val().length) + " - " + this.text);
							}
							if(tabName.indexOf("#") == -1){
								tabName = "PersonId#" + id.substring(8, id.length) + " - " + tabName;		
							}
						}
					}
				});
				
				if(!tabExist){
					$j( "#tabs" ).tabs( "add" , $j(this).attr("href"), tabName + "</span><input type=\"hidden\" value=\"" + id + "\" /></a><span class=\"ui-icon ui-icon-close\" title=\"Close Tab\">Remove Tab");
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