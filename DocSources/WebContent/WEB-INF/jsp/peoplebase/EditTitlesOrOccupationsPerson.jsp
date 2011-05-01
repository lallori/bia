<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>

	<security:authorize ifAnyGranted="ROLE_ADMINISTRATORS, ROLE_ONSITE_FELLOWS, ROLE_DISTANT_FELLOWS">
		<c:url var="EditTitlesOrOccupationsPersonURL" value="/de/peoplebase/EditTitlesOrOccupationsPerson.do">
			<c:param name="personId"   value="${command.personId}" />
		</c:url>
		<c:url var="AddTitleOrOccupationPersonURL" value="/de/peoplebase/EditTitleOrOccupationPerson.do">
			<c:param name="personId"   value="${command.personId}" />
			<c:param name="prfLinkId"  value="0" />
		</c:url>
		<c:url var="ShowPersonURL" value="/src/peoplebase/ShowPerson.do">
			<c:param name="personId"   value="${command.personId}" />
		</c:url>
	</security:authorize>

	<form:form id="EditTitlesOrOccupationsPersonForm" method="post" cssClass="edit">
		<fieldset>
		<legend><b>TITLES / OCCUPATIONS</b></legend>
		<c:forEach items="${person.poLink}" var="currentTitleOrOccupation">
			<c:url var="EditTitleOrOccupationPersonURL" value="/de/peoplebase/EditTitleOrOccupationPerson.do">
				<c:param name="prfLinkId" value="${currentTitleOrOccupation.prfLinkId}" />
				<c:param name="titleOccId" value="${currentTitleOrOccupation.titleOccList.titleOccId}" />
				<c:param name="personId" value="${command.personId}" />
			</c:url>

			<c:url var="DeleteTitleOrOccupationPersonURL" value="/de/peoplebase/DeleteTitleOrOccupationPerson.do" >
				<c:param name="prfLinkId" value="${currentTitleOrOccupation.prfLinkId}" />
				<c:param name="titleOccId" value="${currentTitleOrOccupation.titleOccList.titleOccId}" />
				<c:param name="personId" value="${command.personId}" />
			</c:url>

			<div>
      			<input id="title_${currentTitleOrOccupation.prfLinkId}" name="name_${currentTitleOrOccupation.prfLinkId}" class="input_28c_disabled" type="text" value="${currentTitleOrOccupation.titleOccList.titleOcc}" disabled="disabled" />
				<a class="deleteIcon" title="Delete this entry" href="${DeleteTitleOrOccupationPersonURL}"></a>
				<a class="editValue" class="editValue" href="${EditTitleOrOccupationPersonURL}">edit value</a>
			</div>
		</c:forEach>
			
			<div>
				<input id="close" type="submit" value="" title="do not save changes" class="button" />
				<a id="AddNewValue" title="Add new Name" href="${AddTitleOrOccupationPersonURL}"></a>
			</div>
			
		</fieldset>	
		<div id="EditTitleOrOccupationPersonDiv"></div>
	
		<script type="text/javascript">
			$j(document).ready(function() {
				$j("#EditDetailsPerson").css('visibility', 'hidden');
				$j("#EditNamesPerson").css('visibility', 'hidden');
		        $j("#EditParentsPerson").css('visibility', 'hidden'); 
				$j("#EditChildrenPerson").css('visibility', 'hidden');
				$j("#EditSpousesPerson").css('visibility', 'hidden');
		        $j("#EditResearchNotesPerson").css('visibility', 'hidden'); 
		        
		        $j('#close').click(function() {
					$j.ajax({ url: '${ShowPersonURL}', cache: false, success:function(html) { 
						$j("#body_left").html(html);
					}});

					return false;
				});

		        $j(".deleteIcon").click(function() {
					$j.get(this.href, function(data) {
						if(data.match(/KO/g)){
				            var resp = $j('<div></div>').append(data); // wrap response
						} else {
							$j("#EditTitlesOrOccupationsPersonDiv").load('${EditTitlesOrOccupationsPersonURL}');
						}
			        });
					return false;
				});

				$j(".editValue").click(function() {
					$j("#EditTitleOrOccupationPersonDiv").load($j(this).attr("href"));
					return false;
				});

				$j("#AddNewValue").click(function(){
					$j("#EditTitleOrOccupationPersonDiv").load($j(this).attr("href"));
					return false;
				});
			});
		</script>
	</form:form>
