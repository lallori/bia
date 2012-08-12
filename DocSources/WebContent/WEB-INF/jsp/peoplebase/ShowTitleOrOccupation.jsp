<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>

	<security:authorize ifAnyGranted="ROLE_ADMINISTRATORS, ROLE_ONSITE_FELLOWS, ROLE_DISTANT_FELLOWS">
		<c:url var="EditTitleOrOccupationURL" value="/de/peoplebase/EditTitleOrOccupation.do">
			<c:param name="titleOccId"   value="${titleOccList.titleOccId}" />
		</c:url>
	</security:authorize>

	<div id="EditTitlesOrOccupationsDiv" class="background">
		<div class="title">
			<h5>TITLES / OCCUPATIONS </h5>
			<security:authorize ifAnyGranted="ROLE_ADMINISTRATORS, ROLE_ONSITE_FELLOWS, ROLE_DISTANT_FELLOWS">
				<c:if test="${titleOccList.titleOccId > 0}">
				<a id="EditTitleOrOccupation" href="${EditTitleOrOccupationURL}" class="editButton"></a><span id="loading"/>
				</c:if>
			</security:authorize>
		</div>
		<div class="list">
			<div class="row">
				Title/Occupation Name
				<div class="value60">titleOccList.titleOcc<br>
			</div>
			<div class="row">
				Role
				<div class="value60">titleOccList.<br>
			</div>
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