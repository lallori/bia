<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn2" uri="http://bia.medici.org/jsp:jstl" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>

<security:authorize ifAnyGranted="ROLE_ADMINISTRATORS">
<div id="loadingDiv"></div>
<form:form id="EditIipImagePropertiesForm" method="post" cssClass="edit">
	<fieldset>
		<legend><b><fmt:message key="adm.editIIPImageProperties.iIpImage"/></b></legend>
		<div class="listForm">
			<div class="row">
				<div class="col_l"><form:label path="serverFcgiBinPath" for="serverFcgiBinPath" id="serverPathLabel"><fmt:message key="adm.editIIPImageProperties.serverPath"/></form:label></div>
				<div class="col_r"><form:input path="serverFcgiBinPath" for="serverFcgiBinPath" id="serverPath" cssClass="input_29c" type="text" value=""/></div>
			</div>
			<div class="row">
				<div class="col_l"><form:label path="serverHostName" for="serverHostName" id="serverHostNameLabel"><fmt:message key="adm.editIIPImageProperties.serverHost"/></form:label></div>
				<div class="col_r"><form:input path="serverHostName" for="serverHostName" id="serverHostName" cssClass="input_29c" type="text" value=""/></div>
			</div>
			<div class="row">
				<div class="col_l"><form:label path="serverPort" for="serverPort" id="serverPortLabel"><fmt:message key="adm.editIIPImageProperties.serverPort"/></form:label></div>
				<div class="col_r"><form:input path="serverPort" for="serverPort" id="serverPort" cssClass="input_29c" type="text" value=""/></div>
			</div>
			<div class="row">
				<div class="col_l"><form:label path="serverProtocol" for="serverProtocol" id="serverProtocolLabel"><fmt:message key="adm.editIIPImageProperties.serverProtocol"/></form:label></div>
				<div class="col_r"><form:input path="serverProtocol" for="serverProtocol" id="serverProtocol" cssClass="input_29c" type="text" value=""/></div>
			</div>
			<div class="row">
				<div class="col_l"><form:label path="serverVersion" for="serverVersion" id="serverVersionLabel"><fmt:message key="adm.editIIPImageProperties.serverVersion"/></form:label></div>
				<div class="col_r"><form:input path="serverVersion" for="serverVersion" id="serverVersion" cssClass="input_29c" type="text" value=""/></div>
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
		$j("#EditEmailSystemProperties").css('visibility', 'hidden');
		$j("#EditForumProperties").css('visibility', 'hidden');
		$j("#EditGeneralProperties").css('visibility', 'hidden');
		$j("#EditRecaptchaProperties").css('visibility', 'hidden');
		$j("#EditSchedoneProperties").css('visibility', 'hidden');
		$j("#EditUserSystemProperties").css('visibility', 'hidden');
		
		$j("#EditIipImagePropertiesForm :input").change(function(){
			$j("#modify").val(1); <%-- //set the hidden field if an element is modified --%>
			return false;
		});
		
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
			if($j("#modify").val() == 1){
				$j("#EditIipImagePropertiesForm").block({ message: $j("#question"),
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
	<h1><fmt:message key="adm.editIIPImageProperties.discardChanges"/></h1> 
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
			$j("#EditIipImagePropertiesForm").append($j("#question"));
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