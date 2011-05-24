<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>  
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>

	<security:authorize ifAnyGranted="ROLE_ADMINISTRATORS, ROLE_ONSITE_FELLOWS, ROLE_DISTANT_FELLOWS">
		<c:url var="EditParentsPersonURL" value="/de/peoplebase/EditParentsPerson.do">
			<c:param name="personId"   value="${person.personId}" />
		</c:url>
		<c:url var="ShowPersonURL" value="/src/peoplebase/ShowPerson.do">
			<c:param name="personId"   value="${person.personId}" />
		</c:url>
	</security:authorize>

	<form:form id="EditParentsPersonForm" method="post" cssClass="edit">
		<fieldset>
		<legend><b>PARENTS</b></legend>
			<c:url var="EditFatherPersonURL" value="/de/peoplebase/EditFatherPerson.do">
				<c:param name="id" value="${father.id}" />
				<c:param name="parentId" value="${father.parent.personId}" />
				<c:param name="childId" value="${person.personId}" />
			</c:url>
			<c:url var="DeleteFatherPersonURL" value="/de/peoplebase/DeleteFatherPerson.do" >
				<c:param name="id" value="${father.id}" />
				<c:param name="parentId" value="${father.parent.personId}" />
				<c:param name="childId" value="${person.personId}" />
			</c:url>
			<div>
				Father:
			<c:if test="${not empty father}">
      			<input id="father" name="father" class="input_28c_disabled" type="text" value="${father.parent}" disabled="disabled" />
				<a class="deleteIcon" title="Delete this entry" href="${DeleteFatherPersonURL}"></a>
      		</c:if>
			<c:if test="${empty father}">
      			<input id="father" name="father" class="input_28c_disabled" type="text" value="" disabled="disabled" />
      		</c:if>
				<a class="editValue" class="editValue" href="${EditFatherPersonURL}">edit value</a>
			</div>

			<c:url var="EditMotherPersonURL" value="/de/peoplebase/EditMotherPerson.do">
				<c:param name="id" value="${mother.id}" />
				<c:param name="parentId" value="${mother.parent.personId}" />
				<c:param name="childId" value="${person.personId}" />
			</c:url>
			<c:url var="DeleteMotherPersonURL" value="/de/peoplebase/DeleteMotherPerson.do" >
				<c:param name="id" value="${mother.id}" />
				<c:param name="parentId" value="${mother.parent.personId}" />
				<c:param name="childId" value="${person.personId}" />
			</c:url>

			<div>
				Mother: 
			<c:if test="${not empty mother}">
      			<input id="mother" name="mother" class="input_28c_disabled" type="text" value="${mother.parent}" disabled="disabled" />
				<a class="deleteIcon" title="Delete this entry" href="${DeleteMotherPersonURL}"></a>
      		</c:if>
			<c:if test="${empty mother}">
      			<input id="mother" name="mother" class="input_28c_disabled" type="text" value="" disabled="disabled" />
      		</c:if>
				<a class="editValue" class="editValue" href="${EditMotherPersonURL}">edit value</a>
			</div>
			
			<div>
				<input id="close" type="submit" value="" title="do not save changes" class="button" />
			</div>
			
		</fieldset>	
		<div id="EditParentPersonDiv"></div>
	
	</form:form>

	<script type="text/javascript">
		$j(document).ready(function() {
			$j("#EditDetailsPerson").css('visibility', 'hidden');
			$j("#EditNamesPerson").css('visibility', 'hidden');
	        $j("#EditTitlesOrOccupationsPerson").css('visibility', 'hidden'); 
			$j("#EditChildrenPerson").css('visibility', 'hidden');
			$j("#EditSpousesPerson").css('visibility', 'hidden');
	        $j("#EditResearchNotesPerson").css('visibility', 'hidden'); 
	        
	        $j('#close').click(function() {
				$j.ajax({ url: '${ShowPersonURL}', cache: false, success:function(html) { 
					$j("#body_left").html(html);
				}});
				return false;
			});

	        /*$j(".deleteIcon").click(function() {
				$j.get(this.href, function(data) {
					if(data.match(/KO/g)){
			            var resp = $j('<div></div>').append(data); // wrap response
					} else {
						$j("#EditParentsPersonDiv").load('${EditParentsPersonURL}');
					}
		        });
				return false;
			});*/

			$j(".editValue").click(function() {
				$j("#EditParentPersonDiv").load($j(this).attr("href"));
				return false;
			});

			$j(".deleteIcon").click(function(){
				$j('#EditParentsPersonDiv').block({ message: $j('#question') }); 
				return false;
			});
				
		});
	</script>
	
	<div id="question" style="display:none; cursor: default"> 
		<h1>Delete this Parent entry?</h1> 
		<input type="button" id="yes" value="Yes" /> 
		<input type="button" id="no" value="No" /> 
	</div>
	
	<script type="text/javascript">
		$j(document).ready(function() {
			$j('#no').click(function() {
				$j.unblockUI();
				$j(".blockUI").fadeOut("slow");
				$j("#question").hide();
				$j("#EditParentsPersonDiv").append($j("#question"));
				$j(".blockUI").remove();
				return false; 
			}); 
	        
			$j('#yes').click(function() { 
				$j.get($j(".deleteIcon").attr("href"), function(data) {
					if(data.match(/KO/g)){
			            var resp = $j('<div></div>').append(data); // wrap response
					} else {
						$j("#EditParentsPersonDiv").load('${EditParentsPersonURL}');
					}
					
					return false; 
				}); 	     
			});
		});
	</script>
