<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>

	<form:form id="EditCorrespondentsVolumeForm" method="post" class="edit">
		<fieldset>
			<legend><b>Correspondents</b></legend>
			<div style="margin-top:5px"><form:label id="sendersLabel" for="senders" path="senders" cssErrorClass="error">From:</form:label></div>
			<div style="margin:0"><form:textarea id="senders" path="senders" cssClass="txtarea"/><form:errors path="senders" cssClass="inputerrors"/></div>

			<div style="margin-top:5px"><form:label id="recipsLabel" for="recips" path="recips" cssErrorClass="error">To:</form:label></div>
			<div style="margin:0"><form:textarea id="recips" path="recips" cssClass="txtarea"/><form:errors path="recips" cssClass="inputerrors"/></div>
			<div style="margin-top:5px">
				<input id="close" type="submit" value="Close" title="do not save changes" class="button" /><input id="save" type="submit" value="Save" style="margin-left:300px" class="button"/>
			</div>
			<form:hidden path="summaryId"/>
		</fieldset>
	</form:form>

	<c:url var="ShowVolume" value="/src/volbase/ShowVolume.do">
		<c:param name="summaryId"   value="${command.summaryId}" />
	</c:url>

	<script type="text/javascript">
		$j(document).ready(function() {
			$j('#close').click(function() {
	            $j('#EditCorrespondentsVolumeDiv').block({ message: $j('#question') }); 
				return false;
			});
	        
			$j('#no').click(function() { 
				$j.unblockUI();
				$j(".blockUI").fadeOut("slow");
				$j('#question').hide();
				$j('#EditCorrespondentsVolumeDiv').append($j("#question"));
				$j(".blockUI").remove();
	            return false; 
	        }); 
	        
			$j('#yes').click(function() { 
				$j.ajax({ url: '${ShowVolume}', cache: false, success:function(html) { 
					$j("#body_left").html(html);
	 			}});
				
				return false; 
	        }); 

			$j("#EditCorrespondentsVolumeForm").submit(function (){
	 			$j.ajax({ type:"POST", url:$j(this).attr("action"), data:$j(this).serialize(), async:false, success:function(html) { 
					$j("#EditCorrespondentsVolumeDiv").replaceWith(html);
				}});

				return false;
			});

			$j("#EditContextVolume").removeAttr("href"); 
	        $j("#EditDescriptionVolume").removeAttr("href"); 
			$j("#EditDetailsVolume").removeAttr("href"); 
		});
	</script>