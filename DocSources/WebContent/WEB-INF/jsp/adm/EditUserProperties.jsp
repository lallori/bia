<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn2" uri="http://bia.medici.org/jsp:jstl" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>

<security:authorize ifAnyGranted="ROLE_ADMINISTRATORS">
<div id="loadingDiv"></div>
<form:form id="EditUserSystemPropertiesForm" method="post" cssClass="edit">
	<fieldset>
		<legend><b><fmt:message key="adm.editUserProperties.uSerSystemProperties"/></b></legend>
		<div class="listForm">
			<div class="row">
				<div class="col_l"><form:label path="expirationUser" for="expirationUser" id="expirationUserLabel"><fmt:message key="adm.editUserProperties.expUser"/></form:label></div>
			</div>
			<div class="row">
				<div class="col_l"><form:input type="text" path="expirationUser" for="expirationUser" id="expirationUser" cssClass="input_24c"/></div>
			</div>
			<br />
			<div class="row">
				<div class="col_l"><form:label path="expirationPassword" for="expirationPassword" id="expirationPasswordLabel"><fmt:message key="adm.editUserProperties.expPassword"/></form:label></div>
			</div>
			<div class="row">
				<div class="col_l"><form:input type="text" path="expirationPassword" for="expirationPassword" id="expirationPassword" cssClass="input_24c"/></div>
			</div>
			<br />
			<div class="row">
				<div class="col_l"><form:label path="maxBadLogin" for="maxBadLogin" id="maxBadLoginLabel"><fmt:message key="adm.editUserProperties.maxBad"/></form:label></div>
			</div>
			<div class="row">
				<div class="col_l"><form:input type="text" path="maxBadLogin" for="maxBadLogin" id="maxBadLogin" cssClass="input_24c"/></div>
			</div>
			<br />
<!-- 			<div class="row"> -->
<%-- 				<div class="col_l"><form:label path="resetUserPasswordText" for="resetUserPasswordText" id="resetUserPasswordTextLabel">Reset user password text</form:label></div> --%>
<!-- 			</div> -->
<!-- 			<div class="row"> -->
<%-- 				<div class="col_l"><form:textarea path="resetUserPasswordText" for="resetUserPasswordText" id="resetUserPasswordText" class="txtarea"/></div> --%>
<!-- 			</div> -->
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
		$j("#EditIipImageProperties").css('visibility', 'hidden');
		$j("#EditSchedoneProperties").css('visibility', 'hidden');
		
		$j("#EditUserSystemPropertiesForm :input").change(function(){
			$j("#modify").val(1); <%-- //set the hidden field if an element is modified --%>
			return false;
		});
		
		$j("#save").click(function(){
	       	$j("#loadingDiv").css('height', $j("#loadingDiv").parent().height());
	       	$j("#loadingDiv").css('visibility', 'visible');
	    });
		
		$j("#EditUserSystemPropertiesForm").submit(function(){
			$j.ajax({ type:"POST", url:$j(this).attr("action"), data:$j(this).serialize(), async:false, success:function(html) { 
				$j("#body_left").html(html);
			}});
			return false;
		});
		
		$j("#close").click(function(){
			if($j("#modify").val() == 1){
				$j("#EditUserSystemPropertiesForm").block({ message: $j("#question"),
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
	<h1><fmt:message key="adm.editUserProperties.discardChanges"/></h1> 
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
			$j("#EditUserSystemPropertiesForm").append($j("#question"));
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