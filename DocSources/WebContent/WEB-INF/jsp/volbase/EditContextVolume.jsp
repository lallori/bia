<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>

	<form:form id="EditContextVolumeForm" method="post" cssClass="edit">
		<fieldset>
			<legend><b>Context</b></legend>
			<div><fomr:label for="ccontext" id="ccontextLabel">Context:</fomr:label></div>
			<div><form:textarea id="ccontext" path="ccontext" cssClass="txtarea"/><form:errors path="ccontext" cssClass="inputerrors"/></div>
			<br />
			<div><fomr:label for="inventarioSommarioDescription" id="inventarioSommarioDescriptionLabel">Inventario Sommario Description:</fomr:label></div>
			<div><form:textarea id="inventarioSommarioDescription" path="inventarioSommarioDescription" class="txtarea_medium"/></div>

			<div>
				<input id="close" type="submit" value="" title="do not save changes" class="button" />
				<input id="save" type="submit" value="" style="margin-left:300px" class="button"/>
			</div>
			<form:hidden path="summaryId"/>
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

			$j('#close').click(function() {
	            $j('#EditContextVolumeDiv').block({ message: $j('#question') }); 
				return false;
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
			$j.ajax({ url: '${ShowVolume}', cache: false, success:function(html) { 
				$j("#body_left").html(html);
			}});
				
			return false; 
		}); 
     
	});
</script>