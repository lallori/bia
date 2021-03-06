<%@ taglib prefix="biasecurity" uri="http://bia.medici.org/jsp:jstl_security" %>  
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>

<security:authorize ifAnyGranted="ROLE_ADMINISTRATORS">
	<c:url var="ShowUserURL" value="/admin/ShowUser.do">
		<c:param name="account" value="${command.account}" />
	</c:url>
	
	<c:url var="EditUserEmailURL" value="/admin/EditUserEmail.do" />

	<form:form id="EditUserEmailForm" method="post" cssClass="edit" action="${EditUserEmailURL}">
		<fieldset>
			<legend><b><fmt:message key="adm.editUserEmail.eMailControl"/></b></legend>
			<div class="listForm">
		        <div class="row">
		            <div class="col_l"><form:label for="mail" path="mail"><fmt:message key="adm.editUserEmail.emailAddress"/></form:label></div>
		            <div class="col_l"><form:input path="mail" cssClass="input_23c"/></div>
		        </div>
		        <div class="row">
		            <div class="col_l"><form:label for="mailHide" path="mailHide"><fmt:message key="adm.editUserEmail.emailDisplay"/></form:label></div>
		            <div class="col_l">
		            	<form:select path="mailHide" items="${mailHide}" cssClass="selectform_Xlong" />
		            </div>
		        </div>
		        <div class="row">
		            <div class="col_l"><form:label for="reciveNotifEmail" path="receiveNotificationByMail"><fmt:message key="adm.editUserEmail.receiveNotification"/></form:label></div>
		            <div class="col_l">
		            	<form:select path="receiveNotificationByMail" items="${mailNotification}" cssClass="selectform_Mlong" />
		            </div>
		        </div>
		        <div class="row">
		            <div class="col_l"><form:label for="forumTopicSubscription" path="forumTopicSubscription"><fmt:message key="adm.editUserEmail.subscribe"/></form:label></div>
		            <div class="col_l">
		            	<form:select path="forumTopicSubscription" items="${forumTopicSubscription}" cssClass="selectform_Mlong" />
		            </div>
		        </div>
		    </div>
		    
		    <div>
				<input id="close" class="button_small fl" type="submit" value="Close" title="Do not save changes" />
				<input id="save" class="button_small fr" type="submit" value="Save" />
			</div>
		</fieldset>	
		<form:hidden path="account"/>
	</form:form>

<script type="text/javascript">
	$j(document).ready(function() {
		$j("#EditUserEmailForm :input").change(function(){
			$j("#modify").val(1); <%-- //set the hidden field if an element is modified --%>
			return false;
		});
		
		$j("#EditUserEmailForm").submit(function (){
			$j.ajax({ type:"POST", url:$j(this).attr("action"), data:$j(this).serialize(), async:false, success:function(html) {
				$j("#body_left").html(html);
			}});
		});
		
		$j("#close").click(function(){
			if($j("#modify").val() == 1){
				$j("#EditUserEmailForm").block({ message: $j("#question"),
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
				$j.ajax({ url: '${ShowUserURL}', cache: false, success:function(html) { 
					$j("#body_left").html(html);
				}});
				return false;
			}
		});
	});
</script>

<div id="question" style="display:none; cursor: default"> 
	<h1><fmt:message key="adm.editUserEmail.discardChanges"/></h1> 
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
			$j("#EditUserEmailForm").append($j("#question"));
			$j(".blockUI").remove();
			return false; 
		}); 
        
		$j('#yes').click(function() { 
			$j.ajax({ url: '${ShowUserURL}', cache: false, success:function(html) { 
				$j("#body_left").html(html);
			}});
				
			return false; 
		}); 
     	
	});
</script>
</security:authorize>