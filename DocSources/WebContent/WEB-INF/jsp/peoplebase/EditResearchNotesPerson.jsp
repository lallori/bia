<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>

	<security:authorize ifAnyGranted="ROLE_ADMINISTRATORS, ROLE_ONSITE_FELLOWS, ROLE_DISTANT_FELLOWS">
		<c:url var="ShowPersonURL" value="/src/peoplebase/ShowPerson.do">
			<c:param name="personId"   value="${command.personId}" />
		</c:url>
	</security:authorize>

	<form:form id="EditResearchNotesPersonForm" class="edit" method="post">
		<fieldset>
			<legend><b>RESEARCH NOTES</b></legend>
			
			<div>
				<form:textarea id="bioNotes" path="bioNotes" class="txtarea_big" />
			</div>
			
			<form:hidden path="personId"/>
			
			<div>
				<input id="close" type="submit" value="" title="Do not save changes" class="button" />
				<input id="save" type="submit" value="" class="button"/>
			</div>
			
		</fieldset>	
	</form:form>
	<script type="text/javascript">
		$j(document).ready(function() {
	        $j("#EditDetailsPerson").css('visibility', 'hidden'); 
	        $j("#EditNamesPerson").css('visibility', 'hidden');
	        $j("#EditTitlesOrOccupationsPerson").css('visibility', 'hidden');
	        $j("#EditParentsPerson").css('visibility', 'hidden');
	        $j("#EditChildrenPerson").css('visibility', 'hidden');
	        $j("#EditSpousesPerson").css('visibility', 'hidden');

	        $j('#close').click(function() {
				$j('#EditResearchNotesPersonDiv').block({ message: $j('#question') }); 
				return false;
			});
	        
	        $("#save").click(function (){
				$.ajax({ type:"POST", url:$(this).attr("action"), data:$(this).serialize(), async:false, success:function(html) { 
					$("#EditResearchNotesPersonDiv").html(html);
				}});
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