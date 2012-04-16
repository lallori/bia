<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn2" uri="http://docsources.medici.org/jsp:jstl" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>

<security:authorize ifAnyGranted="ROLE_ADMINISTRATORS">
<div id="loadingDiv"></div>
<form:form id="generalPropertiesForm" method="post" cssClass="edit">
	<fieldset>
		<legend><b>GENERAL PROPERTIES</b></legend>
	
		<div style="margin-top:5px;">
			<form:label id="websiteDomainLabel" for="websiteDomain" path="websiteDomain">Website domain</form:label>
			<form:input id="websiteDomain" cssClass="input_24c" type="text" path="websiteDomain" />
		</div>
		<div>
			<form:label id="imagePathLabel" for="imagesPath" path="imagesPath">Images Path</form:label>
			<form:input id="imagePath" cssClass="input_24c" type="text" path="imagesPath" />
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
		$j("#EditEmailSystemProperties").css('visibility', 'hidden');
		$j("#EditRecaptchaProperties").css('visibility', 'hidden');
		
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
			$j.ajax({ url: '${ShowApplicationProperties}', cache: false, success:function(html) { 
				$j("#body_left").html(html);
			}});
			return false;
		});
	})
</script>
</security:authorize>