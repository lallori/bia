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
		
		<div style="margin-top:5px;">
			<form:label path="activationSubject" for="activationSubject" id="activationMessageSubjectLabel">Activation message subject</form:label>
		</div>
		<div>
			<form:textarea path="activationSubject" for="activationSubject" id="activationMessageSubject" class="txtarea"/>
		</div>
		<br />
		<div>
			<form:label path="activationText" for="activationText" id="activationMessageTextLabel">Activation message text</form:label>
		</div>
		<div>
			<form:textarea path="activationText" for="activationText" id="activationMessageText" class="txtarea"/>
		</div>
		<br />
		<div>
			<form:label path="resetUserPasswordSubject" for="resetUserPasswordSubject" id="resetUserPasswordSubjectLabel">Reset user password subject</form:label>
		</div>
		<div>
			<form:textarea path="resetUserPasswordSubject" for="resetUserPasswordSubject" id="resetUserPasswordSubject" class="txtarea"/>
		</div>
		<br />
		<div>
			<form:label path="resetUserPasswordText" for="resetUserPasswordText" id="resetUserPasswordTextLabel">Reset user password text</form:label>
		</div>
		<div>
			<form:textarea path="resetUserPasswordText" for="resetUserPasswordText" id="resetUserPasswordText" class="txtarea"/>
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
			$j.ajax({ url: '${ShowApplicationProperties}', cache: false, success:function(html) { 
				$j("#body_left").html(html);
			}});
			return false;
		});
	})
</script>
</security:authorize>