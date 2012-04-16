<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn2" uri="http://docsources.medici.org/jsp:jstl" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>

<security:authorize ifAnyGranted="ROLE_ADMINISTRATORS">
<div id="loadingDiv"></div>
<form:form id="EditRecaptchaPropertiesForm" method="post" cssClass="edit">
	<fieldset>
		<legend><b>RECAPTCHA PROPERTIES</b></legend>
		
		<div style="margin-top:5px">
			<form:label path="domainName" for="domainName" id="domainNameLabel">Domain Name</form:label>
			<form:input path="domainName" for="domainName" id="domainName" cssClass="input_35c" type="text" value=""/>
		</div>
		<div>
			<form:label path="privateKey" for="privateKey" id="privateKeyLabel">Private Key</form:label>
			<form:input path="privateKey" for="privateKey" id="privateKey" cssClass="input_35c" type="text" value=""/>
		</div>
		<div>
			<form:label path="publicKey" for="publicKey" id="publicKeyLabel">Public Key</form:label>
			<form:input path="publicKey" for="publicKey" id="publicKey" cssClass="input_35c" type="text" value=""/>
		</div>
		<div>
			<form:label path="serverUrl" for="serverUrl" id="serverUrlLabel">Server Url</form:label>
			<form:input path="serverUrl" for="serverUrl" id="serverUrl" cssClass="input_35c" type="text" value=""/>
		</div>
		<div>
			<form:label path="siteIdentifier" for="siteIdentifier" id="siteIdentifierLabel">Site Identifier</form:label>
			<form:input path="siteIdentifier" for="siteIdentifier" id="siteIdentifier" cssClass="input_35c" type="text" value=""/>
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
		$j("#EditEmailSystemProperties").css('visibility', 'hidden');
		
		$j("#save").click(function(){
	       	$j("#loadingDiv").css('height', $j("#loadingDiv").parent().height());
	       	$j("#loadingDiv").css('visibility', 'visible');
	    });
		
		$j("#EditRecaptchaPropertiesForm").submit(function(){
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