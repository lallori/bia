<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>

	<security:authorize ifAnyGranted="ROLE_ADMINISTRATORS, ROLE_DIGITIZATION_COORDINATORS, DIGITIZATION_TECHNICIANS">
		<c:url var="EditTiffImagesSchedoneURL" value="/digitization/EditTiffImagesSchedone.do">
			<c:param name="schedoneId"   value="${command.schedoneId}" />
		</c:url>
		<c:url var="ShowSchedoneURL" value="/src/docbase/ShowSchedone.do">
			<c:param name="schedoneId"   value="${command.schedoneId}" />
		</c:url>
	</security:authorize>
	
	<%-- Loading div when saving the form --%>
	<div id="loadingDiv"></div>
	<form:form id="EditEditTiffImagesForm" action="${editTiffImagesSchedoneURL}" method="post" cssClass="edit">
	    <fieldset>
	    <legend><b>HIGH RESOLUTION IMAGES (TIFFS)</b></legend>
	        <div style="margin-top:5px">
	            <a class="helpIcon" title="In che formato sono le immagini ad alta risoluzione (default is TIFF)">?</a>
	            <form:label for="formato" path="formato" id ="formatoLabel" cssErrorClass="error" title="Formato">Formato</form:label>
				<form:input path="formato" id="formato" cssClass="input_4c_disabled" />
	        </div>
	        <div>
	            <a class="helpIcon" title="Che compressione hanno queste immagini (dafault is 1:1)">?</a>
	            <form:label for="compressioneTiff" path="compressioneTiff" id ="compressioneLabel" cssErrorClass="error" title="Compressione">Compressione</form:label>
				<form:input path="compressioneTiff" id="compressione" cssClass="input_4c_disabled" />
	        </div>
	        
	        <hr />
	
	        
	        <div>
	        	<a class="helpIcon" title="Numero esatto di immagini in alta risoluzione prodotte per questa filza (compresi i test)">?</a>
	        	<form:label for="numeroTotaleImmaginiTiff" path="numeroTotaleImmaginiTiff" id ="numeroImmaginiLabel" cssErrorClass="error" title="Numero totale Immagini">Numero totale immagini</form:label>
				<form:input path="numeroTotaleImmaginiTiff" id="numeroImmagini" cssClass="input_4c_disabled" />
	        </div>
	        <div>
	            <a class="helpIcon" title="Dimensione Media delle immagini (usare script adeguato per calcolare)">?</a>
	            <form:label for="dimMediaImmaginiTiff" path="dimMediaImmaginiTiff" id ="dimensioneMediaImmaginiLabel" cssErrorClass="error" title="Dimensione Media Immagini">Dimensione media immagini</form:label>
				<form:input path="dimMediaImmaginiTiff" id="dimensioneMediaImmagini" cssClass="input_10c" />
	            <form:select id="formatoMediaImmagini" path="formatoMediaImmagini" cssClass="selectform_short" items="${}" itemValue="" itemLabel=""/>
	            <%-- the items variable for this select tag must be set. The tree values are Mb, Kb, and Gb, default is Mb--%>
	        </div>
	        <div>
	            <a class="helpIcon" title="Dimensione Totale delle immagini (usare script adeguato per calcolare)">?</a>
	            <form:label for="dimTotaleImmaginiTiff" path="dimTotaleImmaginiTiff" id ="dimensioneTotaleImmaginiLabel" cssErrorClass="error" title="Dimensione Totale Immagini">Dimensione totale immagini</form:label>
				<form:input path="dimTotaleImmaginiTiff" id="dimTotaleImmaginiTiff" cssClass="input_10c" />
				<form:select id="formatoTotaleImmagini" path="formatoTotaleImmagini" cssClass="selectform_short" items="${}" itemValue="" itemLabel=""/>
	        	<select id="formatoTotaleImmagini" name="formatoTotaleImmagini" class="selectform_short">
	            <%-- the items variable for this select tag must be set. The tree values are Mb, Kb, and Gb, default is Gb--%>
	        </div>
	        
	        <div>
	            <input id="close" type="submit" value="Close" title="Do not save changes" />
	            <input id="save" class="save" type="submit" value="Save" />
	        </div>
	
	    </fieldset>
	</form:form>


	<script type="text/javascript">
		$j(document).ready(function() {
			<%-- Initialize input type dates default values --%>
			<%-- TO BE DONE--%>
			
			
			$j("#EditDetailsSchedone").css('visibility', 'hidden');
			$j("#EditTiffImagesSchedone").css('visibility', 'hidden'); 
	        $j("#EditJpegImagesSchedone").css('visibility', 'hidden'); 
	        $j("#EditPDFImagesSchedone").css('visibility', 'hidden'); 
			
			 $j("#save").click(function(){
		        	$j("#loadingDiv").css('height', $j("#loadingDiv").parent().height());
		        	$j("#loadingDiv").css('visibility', 'visible');
		        });

			 $j('#close').click(function() {
		        	if($j("#modify").val() == 1){
		        		// Block is attached to form otherwise this block does not function when we use in transcribe and contextualize document
						$j('#EditDetailsSchedoneForm').block({ message: $j('#question') }); 
						return false;
		        	}else{
		        		$j.ajax({ url: '${ShowCatalogURL}', cache: false, success:function(html) { 
		    				$j("#body_left").html(html);
		    			}});
		    				
		    			return false; 
		        	}	        		
			});
	        
			$j("#EditDetailsSchedoneForm").submit(function (){
				$j.ajax({ type:"POST", url:$j(this).attr("action"), data:$j(this).serialize(), async:false, success:function(html) { 
					$j("#EditDetailsSchedoneDiv").html(html);
				}});
				return false;
			});
		});
	</script>

	<script type="text/javascript">
		$j(function() {
			$j('.helpIcon').tooltip({
				track: true,
				fade: 350 
			});
		});
	</script>
	
	<div id="question" style="display:none; cursor: default"> 
		<h1>discard changes?</h1> 
		<input type="button" id="yes" value="Yes" /> 
		<input type="button" id="no" value="No" /> 
	</div>
	
	<script type="text/javascript">
		$j(document).ready(function() {
			$j('#no').click(function() { 
				$j.unblockUI();
				$j(".blockUI").fadeOut("slow");
				$j("#question").hide();
				$j("#EditDetailsSchedoneDiv").append($j("#question"));
				$j(".blockUI").remove();
				return false; 
			}); 
	        
			$j('#yes').click(function() { 
				$j.ajax({ url: '${ShowCatalog}', cache: false, success:function(html) { 
					$j("#body_left").html(html);
				}});
					
				return false; 
			}); 
	     
		});
	</script>
</div>