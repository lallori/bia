<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>

	<form:form id="EditCorrespondentsVolumeForm" method="post" class="edit">
		<div id="loadingDiv"></div>
		<fieldset>
			<legend><b>CORRESPONDENTS</b></legend>
			<div class="listForm">
				<div class="row">
					<a class="helpIcon" title="Text goes here">?</a>
					<form:label id="sendersLabel" for="senders" path="senders" cssErrorClass="error">From</form:label>
				</div>
				<div class="row"><form:textarea path="senders" cssClass="txtarea_medium"/><form:errors path="senders" cssClass="inputerrors"/></div>
				
				<br />
				
				<div class="row">
					<a class="helpIcon" title="Text goes here">?</a>
					<form:label id="recipsLabel" for="recips" path="recips" cssErrorClass="error">To</form:label>
				</div>
				<div class="row"><form:textarea path="recips" cssClass="txtarea_medium"/><form:errors path="recips" cssClass="inputerrors"/></div>
			
			</div>
			
			<div>
				<input id="close" type="submit" value="Close" title="do not save changes" class="button" />
				<input id="save" type="submit" value="Save" class="button"/>
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
			
			 $j("#EditCorrespondentsVolumeForm :input").change(function(){
					$j("#modify").val(1); <%-- //set the hidden field if an element is modified --%>
					return false;
			});
			
			$j("#save").click(function(){
		       	$j("#loadingDiv").css('height', $j("#loadingDiv").parent().height());
		       	$j("#loadingDiv").css('visibility', 'visible');
		    });

			$j('#close').click(function() {
	        	if($j("#modify").val() == 1){
					$j('#EditCorrespondentsVolumeForm').block({ message: $j('#question'),
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
	        
			$j("#EditCorrespondentsVolumeForm").submit(function (){
	 			$j.ajax({ type:"POST", url:$j(this).attr("action"), data:$j(this).serialize(), async:false, success:function(html) { 
					$j("#EditCorrespondentsVolumeDiv").html(html);
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
		$j('#no').click(function() { 
			$j.unblockUI();
			$j(".blockUI").fadeOut("slow");
			$j("#question").hide();
			$j("#EditCorrespondentsVolumeDiv").append($j("#question"));
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