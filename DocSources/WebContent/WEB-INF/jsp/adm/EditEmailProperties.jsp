<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn2" uri="http://bia.medici.org/jsp:jstl" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>

<security:authorize ifAnyGranted="ROLE_ADMINISTRATORS">
<div id="loadingDiv"></div>
<form:form id="EditEmailSystemPropertiesForm" method="post" cssClass="edit">
	<fieldset>
		<legend><b><fmt:message key="adm.editEmailProperties.eMailSystemProperties"/></b></legend>
		<div class="listForm">
			<div class="row">
				<div class="col_l"><form:label path="mailServerHost" for="mailServerHost" id="smtpServerHostLabel"><fmt:message key="adm.editEmailProperties.smtpSerHost"/></form:label></div>
				<div class="col_r"><form:input id="smtpServerHost" for="mailServerHost" cssClass="input_28c" type="text" path="mailServerHost"/></div>
			    </div>
            <div class="row">
                <div class="col_l"><form:label for="mailServerPort" id="smtpServerPortLabel" path="mailServerPort"><fmt:message key="adm.editEmailProperties.smtpSerPort"/></form:label></div>
                <div class="col_r"><form:input id="smtpServerPort" for="mailServerPort" cssClass="input_28c" type="text" path="mailServerPort"/></div>
            </div>
            <div class="row">
                <div class="col_l"><form:label for="mailTransportProtocol" id="mailTransportProtocolLabel" path="mailTransportProtocol"><fmt:message key="adm.editEmailProperties.mailTrans"/></form:label></div>
                <div class="col_r">
                <form:select id="mailTransportProtocol" for="mailTransportProtocol" cssClass="selectform_XXXlong" path="mailTransportProtocol">
                     <!-- In atesa di sapere il contenuto definitivo -->
                     	<form:option path="mailTransportProtocol" value="smtp"/>
                    </form:select>
                </div>
            </div>
            <div class="row">
                <div class="col_l"></div>
                <div class="col_r">
                <form:label for="mailSmtpAuth" id="smtpAuthLabel" path="mailSmtpAuth"><fmt:message key="adm.editEmailProperties.smtpAuth"/></form:label>
                    <form:checkbox for="mailSmtpAuth" cssClass="checkboxPers1" path="mailSmtpAuth" id="mailSmtpAuth"/><!-- se cliccato abilita quelli sotto che altrimenti sono oscurati -->
                </div>
            </div>
            <div class="row">
                <div class="col_l"></div>
                <div class="col_r">
                <form:label for="mailSmtpStarttlsEnable" id="smtpStarttlsLabel" path="mailSmtpStarttlsEnable"><fmt:message key="adm.editEmailProperties.smtpStar"/></form:label>
                    <form:checkbox for="mailSmtpStarttlsEnable" cssClass="checkboxPers1" path="mailSmtpStarttlsEnable" id="mailSmtpStarttlsEnable"/>
                </div>
            </div>
            <div class="row">
                <div class="col_l"><form:label for="mailServerUsername" id="smtpServerUsernameLabel" path="mailServerUsername"><fmt:message key="adm.editEmailProperties.smtpSUsername"/></form:label></div>
                <div class="col_r"><form:input id="smtpServerUsername" for="smtpServerUsername" cssClass="input_28c" type="text" path="mailServerUsername"/></div>
            </div>
            <div class="row">
                <div class="col_l"><form:label for="mailServerPassword" id="smtpServerPasswordLabel" path="mailServerPassword"><fmt:message key="adm.editEmailProperties.smtpSPass"/></form:label></div>
                <div class="col_r"><form:input id="smtpServerPassword" for="mailServerPassword" cssClass="input_28c" type="password" path="mailServerPassword"/></div>
     		</div>
   		</div>  
		<div class="listForm">
			<div class="row">
				<div class="col_l"><form:label path="activationSubject" for="activationSubject" id="activationMessageSubjectLabel"><fmt:message key="adm.editEmailProperties.actSubject"/></form:label></div>
			</div>
			<div class="row">
				<div class="col_l"><form:textarea path="activationSubject" for="activationSubject" id="activationMessageSubject" cssClass="txtarea"/></div>
			</div>
			<br />
			<div class="row">
				<div class="col_l"><form:label path="activationText" for="activationText" id="activationMessageTextLabel"><fmt:message key="adm.editEmailProperties.actText"/></form:label></div>
			</div>
			<div class="row">
				<div class="col_l"><form:textarea path="activationText" for="activationText" id="activationMessageText" cssClass="txtarea"/></div>
			</div>
			<br />
			<div class="row">
				<div class="col_l"><form:label path="resetUserPasswordSubject" for="resetUserPasswordSubject" id="resetUserPasswordSubjectLabel"><fmt:message key="adm.editEmailProperties.resetUserPasswordSubject"/></form:label></div>
			</div>
			<div class="row">
				<div class="col_l"><form:textarea path="resetUserPasswordSubject" for="resetUserPasswordSubject" id="resetUserPasswordSubject" cssClass="txtarea"/></div>
			</div>
			<br />
			<div class="row">
				<div class="col_l"><form:label path="resetUserPasswordText" for="resetUserPasswordText" id="resetUserPasswordTextLabel"><fmt:message key="adm.editEmailProperties.resetUPText"/></form:label></div>
			</div>
			<div class="row">
				<div class="col_l"><form:textarea path="resetUserPasswordText" for="resetUserPasswordText" id="resetUserPasswordText" cssClass="txtarea"/></div>
			</div>
		</div>
		
		<div>
			<input id="close" type="submit" value="Close" title="do not save changes" class="button_small fl" />
			<input id="save" class="button_small fr" type="submit" value="Save" />
		</div>
		<input type="hidden" value="" id="modify" />
	</fieldset>
</form:form>

<c:url var="ShowApplicationProperties" value="/admin/ShowApplicationProperties.do" />

<script type="text/javascript">
	$j(document).ready(function(){
		$j("#EditForumProperties").css('visibility', 'hidden');
		$j("#EditGeneralProperties").css('visibility', 'hidden');
		$j("#EditRecaptchaProperties").css('visibility', 'hidden');
		$j("#EditIipImageProperties").css('visibility', 'hidden');
		$j("#EditSchedoneProperties").css('visibility', 'hidden');
		$j("#EditUserSystemProperties").css('visibility', 'hidden');
		
		$j("#EditEmailSystemPropertiesForm :input").change(function(){
			$j("#modify").val(1); <%-- //set the hidden field if an element is modified --%>
			return false;
		});
		
		var authChange = function(){
			if($j("#mailSmtpAuth:checked").length == 0){
				$j("#mailSmtpStarttlsEnable").attr("disabled", "true");
				$j("#smtpServerUsername").attr("disabled", "disabled");
				$j("#smtpServerUsername").removeClass("input_28c");
				$j("#smtpServerUsername").addClass("input_28c_disabled");
				$j("#smtpServerPassword").removeClass("input_28c");
				$j("#smtpServerPassword").addClass("input_28c_disabled");
				$j("#smtpServerPassword").attr("disabled", "disabled");
				return false;
			}else{
				$j("#mailSmtpStarttlsEnable").removeAttr("disabled");
				$j("#smtpServerUsername").removeAttr("disabled");
				$j("#smtpServerPassword").removeAttr("disabled");
				$j("#smtpServerUsername").addClass("input_28c");
				$j("#smtpServerUsername").removeClass("input_28c_disabled");
				$j("#smtpServerPassword").addClass("input_28c");
				$j("#smtpServerPassword").removeClass("input_28c_disabled");
			}
		}
		
		if($j("#mailSmtpAuth:checked").length == 0){
			$j("#mailSmtpStarttlsEnable").attr("disabled", "true");
			$j("#smtpServerUsername").attr("disabled", "disabled");
			$j("#smtpServerUsername").removeClass("input_28c");
			$j("#smtpServerUsername").addClass("input_28c_disabled");
			$j("#smtpServerPassword").removeClass("input_28c");
			$j("#smtpServerPassword").addClass("input_28c_disabled");
			$j("#smtpServerPassword").attr("disabled", "disabled");
		}
		
		$j("#mailSmtpAuth").change(authChange);
		
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
	<h1><fmt:message key="adm.editEmailProperties.discardChanges"/></h1> 
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