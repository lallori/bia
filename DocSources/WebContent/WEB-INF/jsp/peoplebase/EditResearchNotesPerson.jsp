<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>

	<security:authorize ifAnyGranted="ROLE_ADMINISTRATORS, ROLE_ONSITE_FELLOWS, ROLE_FELLOWS">
		<c:url var="ShowPersonURL" value="/src/peoplebase/ShowPerson.do">
			<c:param name="personId"   value="${command.personId}" />
		</c:url>
		<c:url var="EditResearchNotesURL" value="/de/peoplebase/EditResearchNotesPerson.do">
			<c:param name="personId"   value="${command.personId}" />
		</c:url>
	</security:authorize>

	<form:form id="EditResearchNotesPersonForm" class="edit" method="post" action="${EditResearchNotesURL}">
	<%-- Loading div when saving the form --%>
	<div id="loadingDiv"></div>
		<fieldset>
			<legend><b>RESEARCH NOTES</b></legend>
			<div class="listForm">
				<div class="row"><a class="helpIcon" title="<fmt:message key="peoplebase.editResearchNotes.help.edit"></fmt:message>">?</a></div>
				<div class="row"><form:textarea id="bioNotes" path="bioNotes" class="txtarea_big" /></div>
			</div>
			
			<form:hidden path="personId"/>
			
			<div>
				<input id="close" class="button_small fl" type="submit" value="Close" title="Do not save changes" />
				<input id="save" class="button_small fr" type="submit" value="Save" />
			</div>
			<input type="hidden" value="" id="modify" />
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
	        
	        $j("#EditResearchNotesPersonForm :input").change(function(){
				$j("#modify").val(1); <%-- //set the hidden field if an element is modified --%>
				return false;
			});

	        $j("#save").click(function(){
	        	$j("#loadingDiv").css('height', $j("#loadingDiv").parent().height());
	        	$j("#loadingDiv").css('visibility', 'visible');
	        });
	        
	        $j('#close').click(function() {
	        	if($j("#modify").val() == 1){
	        		// Block is attached to form otherwise this block does not function when we use in transcribe and contextualize document
					$j('#EditResearchNotesPersonDiv').block({ message: $j('#question'),
						css: { 
							border: 'none', 
							padding: '5px',
							boxShadow: '1px 1px 10px #666',
							'-webkit-box-shadow': '1px 1px 10px #666'
							} ,
							overlayCSS: { backgroundColor: '#999' }
					});; 
					return false;
	        	}else{
	        		$j.ajax({ url: '${ShowPersonURL}', cache: false, success:function(html) { 
	    			$j("#body_left").html(html);
	    			}});
				}
	         	return false;
			});
	        
	        $j("#EditResearchNotesPersonForm").submit(function (){
				$j.ajax({ type:"POST", url:$j(this).attr("action"), data:$j(this).serialize(), async:false, success:function(html) { 
					$j("#EditResearchNotesPersonDiv").html(html);
				}});
				return false;
			});
		});
	</script>

<div id="question" style="display:none; cursor: default"> 
	<h1>Discard changes?</h1> 
	<input type="button" id="yes" value="Yes" /> 
	<input type="button" id="no" value="No" /> 
</div>

<script type="text/javascript">
	$j(document).ready(function() {
		
		$j('.helpIcon').tooltip({ 
			track: true, 
			fade: 350 
		});
		
		$j.scrollTo("#EditResearchNotesPersonForm");
		
		$j('#no').click(function() { 
			$j.unblockUI();
			$j(".blockUI").fadeOut("slow");
			$j("#question").hide();
			$j("#EditResearchNotesPersonDiv").append($j("#question"));
			$j(".blockUI").remove();
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