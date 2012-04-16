<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn2" uri="http://docsources.medici.org/jsp:jstl" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>

<security:authorize ifAnyGranted="ROLE_ADMINISTRATORS">
<div id="loadingDiv"></div>
<form:form id="EditIipImagePropertiesForm" method="post" cssClass="edit">
	<fieldset>
		<legend><b>IIP IMAGE</b></legend>
		<div style="margin-top:5px">
			<form:label path="serverFcgiBinPath" for="serverFcgiBinPath" id="serverPathLabel">Server fcgi-bin path</form:label>
			<form:input path="serverFcgiBinPath" for="serverFcgiBinPath" id="serverPath" cssClass="input_29c" type="text" value=""/>
		</div>
		<div>
			<form:label path="serverHostName" for="serverHostName" id="serverHostNameLabel">Server host-name</form:label>
			<form:input path="serverHostName" for="serverHostName" id="serverHostName" cssClass="input_29c" type="text" value=""/>
		</div>
		<div>
			<form:label path="serverPort" for="serverPort" id="serverPortLabel">Server port</form:label>
			<form:input path="serverPort" for="serverPort" id="serverPort" cssClass="input_15c" type="text" value=""/>
		</div>
		<div>
			<form:label path="serverProtocol" for="serverProtocol" id="serverProtocolLabel">Server protocol</form:label>
			<form:input path="serverProtocol" for="serverProtocol" id="serverProtocol" cssClass="input_15c" type="text" value=""/>
		</div>
		<div>
			<form:label path="serverVersion" for="serverVersion" id="serverVersionLabel">Server version</form:label>
			<form:input path="serverVersion" for="serverVersion" id="serverVersion" cssClass="input_15c" type="text" value=""/>
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
		$j("#EditRecaptchaProperties").css('visibility', 'hidden');
		
		$j("#save").click(function(){
	       	$j("#loadingDiv").css('height', $j("#loadingDiv").parent().height());
	       	$j("#loadingDiv").css('visibility', 'visible');
	    });
		
		$j("#EditIipImagePropertiesForm").submit(function(){
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