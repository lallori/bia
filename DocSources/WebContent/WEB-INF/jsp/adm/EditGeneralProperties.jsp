<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn2" uri="http://bia.medici.org/jsp:jstl" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>

<security:authorize ifAnyGranted="ROLE_ADMINISTRATORS">
<div id="loadingDiv"></div>
<form:form id="generalPropertiesForm" method="post" cssClass="edit">
	<fieldset>
		<legend><b>GENERAL PROPERTIES</b></legend>
		<div class="listForm">
			<div class="row">
				<div class="col_l"><form:label id="websiteProtocolLabel" for="websiteProtocol" path="websiteProtocol">Website protocol</form:label></div>
				<div class="col_l"><form:input id="websiteProtocol" cssClass="input_24c" type="text" path="websiteProtocol" /></div>
			</div>
			<div class="row">
				<div class="col_l"><form:label id="websiteDomainLabel" for="websiteDomain" path="websiteDomain">Website domain</form:label></div>
				<div class="col_l"><form:input id="websiteDomain" cssClass="input_24c" type="text" path="websiteDomain" /></div>
			</div>
			<div class="row">
				<div class="col_l"><form:label id="websiteContextPathLabel" for="websiteContextPath" path="websiteContextPath">Website Context Path</form:label></div>
				<div class="col_l"><form:input id="websiteContextPath" cssClass="input_24c" type="text" path="websiteContextPath" /></div>
			</div>
			<div class="row">
				<div class="col_l"><form:label id="imagePathLabel" for="imagesPath" path="imagesPath">Images Path</form:label></div>
				<div class="col_l"><form:input id="imagePath" cssClass="input_24c" type="text" path="imagesPath" /></div>
			</div>
		</div>
		
		<div>
			<input id="close" type="submit" value="Close" title="do not save changes" class="button_small fl" />
			<input id="save" type="submit" value="Save" class="button_small fr" />
		</div>
		<input type="hidden" value="" id="modify" />
	</fieldset>
</form:form>

<c:url var="ShowApplicationProperties" value="/admin/ShowApplicationProperties.do" />

<script type="text/javascript">
	$j(document).ready(function(){
		$j("#EditEmailSystemProperties").css('visibility', 'hidden');
		$j("#EditForumProperties").css('visibility', 'hidden');
		$j("#EditRecaptchaProperties").css('visibility', 'hidden');
		$j("#EditIipImageProperties").css('visibility', 'hidden');
		$j("#EditSchedoneProperties").css('visibility', 'hidden');
		$j("#EditUserSystemProperties").css('visibility', 'hidden');

		$j("#generalPropertiesForm :input").change(function(){
			$j("#modify").val(1); <%-- //set the hidden field if an element is modified --%>
			return false;
		});
		
		$j("#save").click(function(){
	       	$j("#loadingDiv").css('height', $j("#loadingDiv").parent().height());
	       	$j("#loadingDiv").css('visibility', 'visible');
	    });
		
		$j("#generalPropertiesForm").submit(function (){
			$j.ajax({ type:"POST", url:$j(this).attr("action"), data:$j(this).serialize(), async:false, success:function(html) { 
				$j("#EditGeneralPropertiesDiv").html(html);
			}});
			return false;
		});
		
		$j("#close").click(function(){
			if($j("#modify").val() == 1){
				$j("#generalPropertiesForm").block({ message: $j("#question"),
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
				$j.ajax({ url: '${ShowApplicationProperties}', cache: false, success:function(html) { 
					$j("#body_left").html(html);
				}});
				return false;
			}
		});
	})
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
			// Block is attached to form otherwise this block does not function when we use in transcribe and contextualize document
			$j("#generalPropertiesForm").append($j("#question"));
			$j(".blockUI").remove();
			return false; 
		}); 
        
		$j('#yes').click(function() { 
			$j.ajax({ url: '${ShowApplicationProperties}', cache: false, success:function(html) { 
				$j("#body_left").html(html);
			}});
				
			return false; 
		}); 
     	
	});
</script>
</security:authorize>