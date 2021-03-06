<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>

	<form:form id="EditContextVolumeForm" method="post" cssClass="edit">
		<div id="loadingDiv"></div>
		<fieldset>
			<legend><b><fmt:message key="volbase.editContextVolume.title"/></b></legend>
			<div class="listForm">
				<div class="row">
					<a class="helpIcon" title="<fmt:message key="volbase.editContextVolume.help.context"/>">?</a>
					<form:label for="ccontext" id="ccontextLabel" path="ccontext"><fmt:message key="volbase.editContextVolume.context"/></form:label>
				</div>
				<div class="row"><form:textarea path="ccontext" cssClass="txtarea_medium"/><form:errors path="ccontext" cssClass="inputerrors"/></div>
				
				<br />
				
				<div class="row">
					<a class="helpIcon" title="<fmt:message key="volbase.editContextVolume.help.inventarioSommarioDescription"/>">?</a>
					<form:label for="inventarioSommarioDescription" id="inventarioSommarioDescriptionLabel" path="inventarioSommarioDescription"><fmt:message key="volbase.editContextVolume.inventarioSommarioDescription"/></form:label>
				</div>
				<div class="row"><form:textarea path="inventarioSommarioDescription" class="txtarea_medium"/></div>
			</div>

			<div>
				<input id="close" class="button_small fl" type="submit" value="Close" title="do not save changes" />
				<input id="save" class="button_small fr" type="submit" value="Save"/>
			</div>
			<form:hidden path="summaryId"/>
			<input type="hidden" value="" id="modify" />
		</fieldset>
	</form:form>

	<c:url var="ShowVolume" value="/src/volbase/ShowVolume.do">
		<c:param name="summaryId"   value="${command.summaryId}" />
	</c:url>

	<script type="text/javascript">
		$j(document).ready(function() {
			$j("#EditContextVolume").css('visibility', 'hidden'); 
	        $j("#EditCorrespondentsVolume").css('visibility', 'hidden'); 
	        $j("#EditDescriptionVolume").css('visibility', 'hidden'); 
			$j("#EditDetailsVolume").css('visibility', 'hidden'); 
			
			$j("#EditContextVolumeForm :input").change(function(){
				$j("#modify").val(1); //set the hidden field if an element is modified
				return false;
			});
			
			 $j("#save").click(function(){
		        	$j("#loadingDiv").css('height', $j("#loadingDiv").parent().height());
		        	$j("#loadingDiv").css('visibility', 'visible');
		        });

			 $j('#close').click(function() {
		        	if($j("#modify").val() == 1){
						$j('#EditContextVolumeDiv').block({ message: $j('#question'),
							css: { 
								border: 'none', 
								padding: '5px',
								boxShadow: '1px 1px 10px #666',
								'-webkit-box-shadow': '1px 1px 10px #666'
								} ,
								overlayCSS: { backgroundColor: '#999' }	
						}); 
						return false;
		        	}else{
		        		$j.ajax({ url: '${ShowVolume}', cache: false, success:function(html) { 
		    				$j("#body_left").html(html);
		    			}});
		    				
		    			return false; 
		        	}	        		
				});
	        
			$j("#EditContextVolumeForm").submit(function (){
				$j.ajax({ type:"POST", url:$j(this).attr("action"), data:$j(this).serialize(), async:false, success:function(html) { 
					$j("#EditContextVolumeDiv").html(html);
				}});
				return false;
			});
		});
	</script>

<div id="question" style="display:none; cursor: default"> 
	<h1><fmt:message key="volbase.editContextVolume.discardChangesQuestion"/></h1> 
	<input type="button" id="yes" value="Yes" /> 
	<input type="button" id="no" value="No" /> 
</div>

<script type="text/javascript">
	$j(document).ready(function() {
		
		$j('.helpIcon').tooltip({ 
			track: true, 
			fade: 350 
		});
		
		$j('#no').click(function() { 
			$j.unblockUI();
			$j(".blockUI").fadeOut("slow");
			$j("#question").hide();
			$j("#EditContextVolumeDiv").append($j("#question"));
			$j(".blockUI").remove();
			return false; 
		}); 
        
		$j('#yes').click(function() { 
			$j.ajax({ url: '${ShowVolume}', cache: false, success:function(html) { 
				$j("#body_left").html(html);
			}});
				
			return false; 
		}); 
     
	});
</script>