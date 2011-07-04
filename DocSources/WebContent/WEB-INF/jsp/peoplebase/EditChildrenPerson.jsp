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
			<c:param name="id"  value="0" />
			<c:param name="parentId"  value="${command.personId}" />
			<c:param name="childId"  value="0" />
		</c:url>
		<c:url var="ShowPersonURL" value="/src/peoplebase/ShowPerson.do">
			<c:param name="personId"   value="${command.personId}" />
		</c:url>
	</security:authorize>

	<form:form id="EditChildrenPersonForm" method="post" cssClass="edit">
		<fieldset>
		<legend><b>CHILDREN</b></legend>
		<c:forEach items="${person.children}" var="currentChild">
			<c:url var="EditChildPersonURL" value="/de/peoplebase/EditChildPerson.do">
				<c:param name="id" value="${currentChild.id}" />
				<c:param name="parentId" value="${command.personId}" />
				<c:param name="childId" value="${currentChild.child.personId}" />
			</c:url>

			<c:url var="DeleteChildPersonURL" value="/de/peoplebase/DeleteChildPerson.do" >
				<c:param name="id" value="${currentChild.id}" />
				<c:param name="parentId" value="${command.personId}" />
				<c:param name="childId" value="${currentChild.child.personId}" />
			</c:url>
			
			<c:url var="ComparePersonURL" value="/src/peoplebase/ComparePerson.do">
					<c:param name="personId"   value="${currentChild.child.personId}" />
				</c:url>

			<div>
      			<input id="child_${currentChild.id}" name="child_${currentChild.id}" class="input_35c_disabled" type="text" value="${currentChild.child.mapNameLf}" disabled="disabled" />
				<a class="deleteIcon" title="Delete this entry" href="${DeleteChildPersonURL}"></a>
				<a class="editValue" class="editValue" href="${EditChildPersonURL}">edit value</a>
				<a href="${ComparePersonURL }" class="personIcon" title="Show this person record"></a>
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
						$j("#EditChildrenPersonDiv").load('${EditChildrenPersonURL}');
					}
		        });
				return false;
			});*/

			$j(".deleteIcon").click(function() {
				var temp = $j(this);
				$j("#EditChildrenPersonDiv").block({ message: $j("#question")});

				$j('#no').click(function() {
					$j.unblockUI();
					$j(".blockUI").fadeOut("slow");
					$j("#question").hide();
					$j("#EditChildrenPersonDiv").append($j("#question"));
					$j(".blockUI").remove();
					return false; 
				}); 

				$j('#yes').click(function() { 
					$j.get(temp.attr("href"), function(data) {
					if(data.match(/KO/g)){
			            var resp = $j('<div></div>').append(data); // wrap response
					} else {
						$j("#EditChildrenPersonDiv").load('${EditChildrenPersonURL}');
					}
					
					return false; 
				}); 	
									     
				});
				return false;
			});

			$j(".editValue").click(function() {
				$j(".deleteIcon").css('visibility', 'hidden');
				$j("#EditChildPersonDiv").load($j(this).attr("href"));
				return false;
			});

			$j("#AddNewValue").click(function(){
				$j("#EditChildPersonDiv").load($j(this).attr("href"));
				return false;
			});

			$j(".personIcon").click(function(){
				$j("#tabs").tabs("add", $j(this).attr("href"), "Person</span></a><span class=\"ui-icon ui-icon-close\" title=\"Close Tab\">Remove Tab");
				$j("#tabs").tabs("select", $j("#tabs").tabs("length")-1);
				return false;
			});
		});
	</script>
	
	<div id="question" style="display:none; cursor: default"> 
		<h1>Delete this Child entry?</h1> 
		<input type="button" id="yes" value="Yes" /> 
		<input type="button" id="no" value="No" /> 
	</div>
	
