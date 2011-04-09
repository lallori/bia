<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>

	<security:authorize ifAnyGranted="ROLE_ADMINISTRATORS, ROLE_ONSITE_FELLOWS, ROLE_DISTANT_FELLOWS">
		<c:url var="EditChildrenPersonURL" value="/de/peoplebase/EditChildrenPerson.do">
			<c:param name="personId"   value="${command.personId}" />
		</c:url>
		<c:url var="AddChildPersonURL" value="/de/peoplebase/EditChildPerson.do">
			<c:param name="personId"   value="${command.personId}" />
			<c:param name="nameId"  value="0" />
		</c:url>
		<c:url var="ShowPersonURL" value="/src/peoplebase/ShowPerson.do">
			<c:param name="personId"   value="${command.personId}" />
		</c:url>
	</security:authorize>

	<form:form id="EditChildrenPersonForm" method="post" cssClass="edit">
		<fieldset>
		<legend><b>CHILDREN</b></legend>
		<c:forEach items="${children}" var="currentChild">
			<c:url var="EditChildPersonURL" value="/de/peoplebase/EditChildPerson.do">
				<c:param name="parentId" value="${command.personId}" />
				<c:param name="childId" value="${currentChild.personId}" />
			</c:url>

			<c:url var="DeleteChildPersonURL" value="/de/peoplebase/DeleteNamePerson.do" >
				<c:param name="parentId" value="${command.personId}" />
				<c:param name="childId" value="${currentChild.personId}" />
			</c:url>

			<div>
      			<input id="child_${currentChild.personId}" name="child_${currentChild.personId}" class="input_28c_disabled" type="text" value="${currentChild.mapNameLf}" disabled="disabled" />
				<a class="deleteIcon" title="Delete this entry" href="${DeleteChildPersonURL}"></a>
				<a class="editValue" class="editValue" href="${EditChildPersonURL}">edit value</a>
			</div>
		</c:forEach>
			
			<div>
				<input id="close" type="submit" value="" title="do not save changes" class="button" />
				<a id="AddNewValue" title="Add new Name" href="${AddChildPersonURL}"></a>
			</div>
			
		</fieldset>	
		
		<div id="EditChildPersonDiv"></div>
	
	</form:form>

	<script type="text/javascript">
		$j(document).ready(function() {
			$j("#EditDetailsPerson").css('visibility', 'hidden');
			$j("#EditNamesPerson").css('visibility', 'hidden');
	        $j("#EditTitlesOrOccupationsPerson").css('visibility', 'hidden'); 
			$j("#EditParentsPerson").css('visibility', 'hidden');
			$j("#EditSpousesPerson").css('visibility', 'hidden');
	        $j("#EditResearchNotesPerson").css('visibility', 'hidden'); 
	        
	        $j('#close').click(function() {
				$j('#EditChildrenPersonDiv').block({ message: $j('#question') }); 
				return false;
			});

	        $j(".deleteValue").click(function() {
				$j.get(this.href, function(data) {
					if(data.match(/KO/g)){
			            var resp = $j('<div></div>').append(data); // wrap response
					} else {
						$j("#EditChildrenPersonDiv").load('${EditChildrenPersonURL}');
					}
		        });
				return false;
			});

			$j(".editValue").click(function() {
				$j("#EditChildPersonDiv").load($j(this).attr("href"));
				return false;
			});

			$j("#AddNewValue").click(function(){
				$j("#EditChildPersonDiv").load($j(this).attr("href"));
				return false;
			});
		});
	</script>

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