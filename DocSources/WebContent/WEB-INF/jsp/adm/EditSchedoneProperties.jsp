<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn2" uri="http://docsources.medici.org/jsp:jstl" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>

<security:authorize ifAnyGranted="ROLE_ADMINISTRATORS">
<div id="loadingDiv"></div>
<form:form id="EditSchedonePropertiesForm" method="post" cssClass="edit">
	<fieldset>
		<legend><b>SCHEDONE</b></legend>
		<div style="margin-top:5px">
			<form:label path="istituto" for="istituto" id="istitutoLabel">Istituto</form:label>
			<form:input path="istituto" for="istituto" id="istituto" cssClass="input_29c" type="text" value=""/>
		</div>
		<div>
			<form:label path="fondo" for="fondo" id="fondoLabel">Supporto</form:label>
			<form:input path="fondo" for="fondo" id="fondo" cssClass="input_29c" type="text" value=""/>
		</div>
		<div>
			<form:label path="legatura" for="legatura" id="legaturaLabel">Legatura</form:label>
			<form:input path="legatura" for="legatura" id="legatura" cssClass="input_29c" type="text" value=""/>
		</div>
		<div>
			<form:label path="tipoRipresa" for="tipoRipresa" id="tipoRipresaLabel">Tipo di ripresa</form:label>
			<form:input path="tipoRipresa" for="tipoRipresa" id="tipoRipresa" cssClass="input_24c" type="text" value=""/>
		</div>
		<div>
			<form:label path="coloreImmagine" for="coloreImmagine" id="coloreImmagineLabel">Colore immagine</form:label>
			<form:input path="coloreImmagine" for="coloreImmagine" id="coloreImmagine" cssClass="input_24c" type="text" value=""/>
		</div>
		<div style="margin-top:5px">
			<form:label path="nomeFiles" for="nomeFiles" id="nomeFilesLabel">Nome files</form:label>
		</div>
		<div>
			<form:textarea path="nomeFiles" for="nomeFiles" id="nomeFiles" cssClass="txtarea"></form:textarea>
		</div>
		<div>
			<form:label path="responsabileFotoRiproduzione" for="responsabileFotoRiproduzione" id="responsabileFotoRiproduzioneLabel">Responsabile fotoriproduzione</form:label>
			<form:input path="responsabileFotoRiproduzione" for="responsabileFotoRiproduzione" id="responsabileFotoRiproduzione" cssClass="input_24c" type="text" value=""/>
		</div>
		<div>
			<form:label path="operatore" for="operatore" id="operatoreLabel">Operatore</form:label>
			<form:input path="operatore" for="operatore" id="operatore" cssClass="input_29c" type="text" value=""/>
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
		$j("#EditIipImageProperties").css('visibility', 'hidden');
		
		$j("#EditSchedonePropertiesForm :input").change(function(){
			$j("#modify").val(1); <%-- //set the hidden field if an element is modified --%>
			return false;
		});
		
		$j("#save").click(function(){
	       	$j("#loadingDiv").css('height', $j("#loadingDiv").parent().height());
	       	$j("#loadingDiv").css('visibility', 'visible');
	    });
		
		$j("#EditSchedonePropertiesForm").submit(function(){
			$j.ajax({ type:"POST", url:$j(this).attr("action"), data:$j(this).serialize(), async:false, success:function(html) { 
				$j("#body_left").html(html);
			}});
			return false;
		});
		
		$j("#close").click(function(){
			if($j("#modify").val() == 1){
				$j("#EditSchedonePropertiesForm").block({ message: $j("#question"),
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
			$j("#EditSchedonePropertiesForm").append($j("#question"));
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