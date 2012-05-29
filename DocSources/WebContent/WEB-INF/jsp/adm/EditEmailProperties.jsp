<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn2" uri="http://docsources.medici.org/jsp:jstl" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>

<security:authorize ifAnyGranted="ROLE_ADMINISTRATORS">
<div id="loadingDiv"></div>
<form:form id="EditEmailSystemPropertiesForm" method="post" cssClass="edit">
	<fieldset>
		<legend><b>EMAIL SYSTEM PROPERTIES</b></legend>
		<div class="listForm">
			<div class="row">
				<div class="col_l"><form:label path="activationSubject" for="activationSubject" id="activationMessageSubjectLabel">Activation message subject</form:label></div>
			</div>
			<div class="row">
				<div class="col_l"><form:textarea path="activationSubject" for="activationSubject" id="activationMessageSubject" class="txtarea"/></div>
			</div>
			<br />
			<div class="row">
				<div class="col_l"><form:label path="activationText" for="activationText" id="activationMessageTextLabel">Activation message text</form:label></div>
			</div>
			<div class="row">
				<div class="col_l"><form:textarea path="activationText" for="activationText" id="activationMessageText" class="txtarea"/></div>
			</div>
			<br />
			<div class="row">
				<div class="col_l"><form:label path="resetUserPasswordSubject" for="resetUserPasswordSubject" id="resetUserPasswordSubjectLabel">Reset user password subject</form:label></div>
			</div>
			<div class="row">
				<div class="col_l"><form:textarea path="resetUserPasswordSubject" for="resetUserPasswordSubject" id="resetUserPasswordSubject" class="txtarea"/></div>
			</div>
			<br />
			<div class="row">
				<div class="col_l"><form:label path="resetUserPasswordText" for="resetUserPasswordText" id="resetUserPasswordTextLabel">Reset user password text</form:label></div>
			</div>
			<div class="row">
				<div class="col_l"><form:textarea path="resetUserPasswordText" for="resetUserPasswordText" id="resetUserPasswordText" class="txtarea"/></div>
			</div>
		</div>
		
		<div>
			<input id="close" type="submit" value="Close" title="do not save changes" class="button" />
			<input id="save" type="submit" value="Save" />
		</div>
		<input type="hidden" value="" id="modify" />
	</fieldset>
</form:form>

<c:url var="ShowApplicationProperties" value="/admin/ShowApplicationProperties.do" />

<script type="text/javascript">
	$j(document).ready(function(){
		$j("#EditGeneralProperties").css('visibility', 'hidden');
		$j("#EditRecaptchaProperties").css('visibility', 'hidden');
		$j("#EditIipImageProperties").css('visibility', 'hidden');
		
		$j("#EditEmailSystemPropertiesForm :input").change(function(){
			$j("#modify").val(1); <%-- //set the hidden field if an element is modified --%>
			return false;
		});
		
		$j("#save").click(function(){
	       	$j("#loadingDiv").css('height', $j("#loadingDiv").parent().height());
	       	$j("#loadingDiv").css('visibility', 'visible');
	    });
		
		$j("#EditEmailSystemPropertiesForm").submit(function(){
			$j.ajax({ type:"POST", url:$j(this).attr("action"), data:$j(this).serialize(), async:false, success:function(html) { 
				$j("#body_left").html(html);
			}});
			return false;
		});
		
		$j("#close").click(function(){
			if($j("#modify").val() == 1){
				$j("#EditEmailSystemPropertiesForm").block({ message: $j("#question"),
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
			$j("#EditEmailSystemPropertiesForm").append($j("#question"));
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