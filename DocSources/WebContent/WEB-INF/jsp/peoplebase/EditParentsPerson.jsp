<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>

	<security:authorize ifAnyGranted="ROLE_ADMINISTRATORS, ROLE_ONSITE_FELLOWS, ROLE_DISTANT_FELLOWS">
		<c:url var="EditParentsPersonURL" value="/de/peoplebase/EditParentsPerson.do">
			<c:param name="personId"   value="${command.personId}" />
		</c:url>
		<c:url var="ShowPersonURL" value="/src/peoplebase/ShowPerson.do">
			<c:param name="personId"   value="${command.personId}" />
		</c:url>
	</security:authorize>

	<form:form id="EditParentsPersonForm" method="post" cssClass="edit">
		<fieldset>
		<legend><b>PARENTS</b></legend>
			<c:url var="EditFatherPersonURL" value="/de/peoplebase/EditFatherPerson.do">
				<c:param name="personId" value="${command.personId}" />
			</c:url>
			<c:url var="DeleteFatherPersonURL" value="/de/peoplebase/DeleteFatherPerson.do" >
				<c:param name="personId" value="${command.personId}" />
				<c:param name="nameId" value="${currentName.nameId}" />
			</c:url>
			<div>
				Father:
      			<input id="father" name="father" class="input_28c_disabled" type="text" value="${person.father.last}" disabled="disabled" />
				<a class="deleteIcon" title="Delete this entry" href="${DeleteFatherPersonURL}"></a>
				<a class="editValue" class="editValue" href="${EditFatherPersonURL}">edit value</a>
			</div>

			<c:url var="EditMotherPersonURL" value="/de/peoplebase/EditMotherPerson.do">
				<c:param name="personId" value="${command.personId}" />
			</c:url>
			<c:url var="DeleteMotherPersonURL" value="/de/peoplebase/DeleteMotherPerson.do" >
				<c:param name="personId" value="${command.personId}" />
			</c:url>

			<div>
				Mother: 
      			<input id="mother" name="mother" class="input_28c_disabled" type="text" value="${person.mother.last}" disabled="disabled" />
				<a class="deleteIcon" title="Delete this entry" href="${DeleteMotherPersonURL}"></a>
				<a class="editValue" class="editValue" href="${EditMotherPersonURL}">edit value</a>
			</div>
			
			<div>
				<input id="close" type="submit" value="" title="do not save changes" class="button" />
			</div>
			
		</fieldset>	
		<div id="EditParentPersonDiv"></div>
	
		<script type="text/javascript">
			$j(document).ready(function() {
		        $j("#EditDetailsPerson").css('visibility', 'hidden'); 
		        $j("#EditCorrespondentsOrPeopleDocument").css('visibility', 'hidden'); 
		        $j("#EditExtractOrSynopsisDocument").css('visibility', 'hidden');
		        $j("#EditDocumentInManuscriptViewer").css('visibility', 'hidden');
		        $j("#EditDocumentInModal").css('visibility', 'hidden');
		        $j("#EditFactCheckDocument").css('visibility', 'hidden');
		        
		        $j('#close').click(function() {
					$j('#EditNamesPersonDiv').block({ message: $j('#question') }); 
					return false;
				});

		        $j(".deleteValue").click(function() {
					$j.get(this.href, function(data) {
						if(data.match(/KO/g)){
				            var resp = $j('<div></div>').append(data); // wrap response
						} else {
							$j("#EditParentsPersonDiv").load('${EditParentsPersonURL}');
						}
			        });
					return false;
				});

				$j(".editValue").click(function() {
					$j("#EditParentPersonDiv").load($j(this).attr("href"));
					return false;
				});
			});
		</script>
	</form:form>

<div id="question" style="display:none; cursor: default"> 
	<h1>discard changes?</h1> 
	<input type="button" id="yes" value="Yes" /> 
	<input type="button" id="no" value="No" /> 
</div>

<script type="text/javascript">
	$j(document).ready(function() {
		$j('#no').click(function() { 
			$j.unblockUI();
			$j(".blockUI").fadeOut("slow");
			return false; 
		}); 
        
		$j('#yes').click(function() { 
			$j.ajax({ url: '${ShowPersonURL}', cache: false, success:function(html) { 
				$j("#body_left").html(html);
			}});
				
			return false; 
		}); 
     
	});
</script>