<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>

	<security:authorize ifAnyGranted="ROLE_ADMINISTRATORS, ROLE_ONSITE_FELLOWS, ROLE_DISTANT_FELLOWS">
		<c:url var="EditSpousesPersonURL" value="/de/peoplebase/EditSpousesPerson.do">
			<c:param name="personId"   value="${command.personId}" />
		</c:url>
		<c:url var="AddSpousePersonURL" value="/de/peoplebase/EditSpousePerson.do">
			<c:param name="personId"   value="${command.personId}" />
			<c:param name="marriageId" value="0" />
		</c:url>
		<c:url var="ShowPersonURL" value="/src/peoplebase/ShowPerson.do">
			<c:param name="personId"   value="${command.personId}" />
		</c:url>
	</security:authorize>

	<form:form id="EditSpousesPersonForm" method="post" cssClass="edit">
		<fieldset>
		<legend><b>SPOUSES</b></legend>
		<c:forEach items="${marriages}" var="currentMarriage">
			<c:url var="EditSpousePersonURL" value="/de/peoplebase/EditSpousePerson.do">
				<c:param name="personId" value="${command.personId}" />
				<c:param name="marriageId" value="${currentMarriage.marriageId}" />
				<c:if test="${command.personId == currentMarriage.wife.personId}">
					<c:param name="husbandId" value="${currentMarriage.husband.personId}" />
				</c:if> 
				<c:if test="${command.personId == currentMarriage.husband.personId}">
					<c:param name="wifeId" value="${currentMarriage.wife.personId}" />
				</c:if> 
			</c:url>

			<c:url var="DeleteSpousePersonURL" value="/de/peoplebase/DeleteSpousePerson.do" >
				<c:param name="personId" value="${command.personId}" />
				<c:param name="marriageId" value="${currentMarriage.marriageId}" />
				<c:if test="${command.personId == currentMarriage.wife.personId}">
					<c:param name="husbandId" value="${currentMarriage.husband.personId}" />
				</c:if> 
				<c:if test="${command.personId == currentMarriage.husband.personId}">
					<c:param name="wifeId" value="${currentMarriage.wife.personId}" />
				</c:if> 
			</c:url>

			<div>
				<c:if test="${command.personId == currentMarriage.husband.personId}">
      				<input id="marriage_${currentMarriage.marriageId}" name="name_${currentMarriage.marriageId}" class="input_40c_disabled" type="text" value="${currentMarriage.wife} - m.(${currentMarriage.startYear} - ${currentMarriage.endYear}) d.${currentMarriage.wife.deathYear}" disabled="disabled" />
				</c:if> 
				<c:if test="${command.personId == currentMarriage.wife.personId}">
      				<input id="marriage_${currentMarriage.marriageId}" name="name_${currentMarriage.marriageId}" class="input_40c_disabled" type="text" value="${currentMarriage.husband} - m.(${currentMarriage.startYear} - ${currentMarriage.endYear}) d.${currentMarriage.husband.deathYear}" disabled="disabled" />
				</c:if> 
				<a class="deleteIcon" title="Delete this entry" href="${DeleteSpousePersonURL}"></a>
				<a class="editValue" class="editValue" href="${EditSpousePersonURL}" title="Edit this entry"></a>
				<c:if test="${command.personId == currentMarriage.husband.personId}">
					<c:url var="ComparePersonURL" value="/src/peoplebase/ComparePerson.do">
						<c:param name="personId"   value="${currentMarriage.wife.personId}" />
					</c:url>
				</c:if> 
				<c:if test="${command.personId == currentMarriage.wife.personId}">
      				<c:url var="ComparePersonURL" value="/src/peoplebase/ComparePerson.do">
						<c:param name="personId"   value="${currentMarriage.husband.personId}" />
					</c:url>
				</c:if> 
				<a href="${ComparePersonURL}" class="personIcon" title="Show this person record"></a>
			</div>
		</c:forEach>
			
			<div>
				<input id="close" type="submit" value="Close" title="do not save changes" class="button" />
				<a id="AddNewValue" title="Add new Name" href="${AddSpousePersonURL}">Add</a>
			</div>
			
		</fieldset>	
		<div id="EditSpousePersonDiv"></div>
	
		<script type="text/javascript">
			$j(document).ready(function() {
				$j.scrollTo("#EditSpousesPersonForm");
				
				$j("#EditDetailsPerson").css('visibility', 'hidden');
				$j("#EditNamesPerson").css('visibility', 'hidden');
		        $j("#EditTitlesOrOccupationsPerson").css('visibility', 'hidden'); 
				$j("#EditParentsPerson").css('visibility', 'hidden');
				$j("#EditChildrenPerson").css('visibility', 'hidden');
		        $j("#EditResearchNotesPerson").css('visibility', 'hidden'); 

		        $j('#close').click(function() {
		        	$j.ajax({ url: '${ShowPersonURL}', cache: false, success:function(html) { 
						$j("#body_left").html(html);
					}}); 
					return false;
				});

		        /*$j(".deleteValue").click(function() {
					$j.get(this.href, function(data) {
						if(data.match(/KO/g)){
				            var resp = $j('<div></div>').append(data); // wrap response
						} else {
							$j("#EditSpousesPersonDiv").load('${EditSpousesPersonURL}');
						}
			        });
					return false;
				});*/

				$j(".deleteIcon").click(function() {
					var temp = $j(this);
					$j("#EditSpousesPersonDiv").block({ message: $j(".question") });

					$j('.no').click(function() {
						$j.unblockUI();
						$j(".blockUI").fadeOut("slow");
						$j(".question").hide();
						$j("#EditSpousesPersonDiv").append($j(".question"));
						$j(".blockUI").remove();
						$j("#EditSpousesPersonDiv").load('${EditSpousesPersonURL}');
						return false; 
					}); 

					$j('.yes').click(function() { 
						$j.get(temp.attr("href"), function(data) {
						if(data.match(/KO/g)){
				            var resp = $j('<div></div>').append(data); // wrap response
						} else {
							$j("#EditSpousesPersonDiv").load('${EditSpousesPersonURL}');
							return false;
						}
						return false; 
					}); 	
						     
					});
					return false;
				});

				$j(".editValue").click(function() {
					$j(".deleteIcon").css('visibility', 'hidden');
					$j("#EditSpousePersonDiv").load($j(this).attr("href"));
					return false;
				});

				$j("#AddNewValue").click(function(){
					$j("#EditSpousePersonDiv").load($j(this).attr("href"));
					return false;
				});

				$j(".personIcon").click(function(){
					var tabName = $j(this).parent();
					tabName = $j(tabName).find('.input_40c_disabled');
					tabName = $j(tabName).val();
					tabName = tabName.substring(0,tabName.indexOf("-"));
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
			});
		</script>
	</form:form>
	
	<div class="question" style="display:none; cursor: default"> 
		<h1>Delete this Spouse entry?</h1> 
		<input type="button" class="yes" value="Yes" /> 
		<input type="button" class="no" value="No" /> 
	</div>
	