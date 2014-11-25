<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>

	<security:authorize ifAnyGranted="ROLE_ADMINISTRATORS, ROLE_DIGITIZATION_COORDINATORS, DIGITIZATION_TECHNICIANS">
		<c:url var="EditJpegImagesSchedoneURL" value="/digitization/EditJpegImagesSchedone.do">
			<c:param name="schedoneId"   value="${command.schedoneId}" />
		</c:url>
		<c:url var="ShowSchedoneURL" value="/digitization/ShowSchedone.do">
			<c:param name="schedoneId"   value="${command.schedoneId}" />
		</c:url>
	</security:authorize>
	
	<%-- Loading div when saving the form --%>
	<div id="loadingDiv"></div>
	<form:form id="EditJpegImagesForm" action="${EditJpegImagesSchedoneURL}" method="post" cssClass="edit">
	    <fieldset>
	    <legend><b><fmt:message key="digitization.editJpegImagesSchedone.jPegImages"/></b></legend>
	    <div class="listForm">
	        <div class="row">
	        	<div class="col_l">
	            	<a class="helpIcon" title="In che formato sono le immagini ad alta risoluzione (default is JPEG)">?</a>
	            	<label for="formato" id ="formatoLabel" title="Formato"><fmt:message key="digitization.editJpegImagesSchedone.formato"/></label>
	            </div>
	            <div class="col_r">
					<input id="formato" class="input_4c_disabled" value="JPEG" disabled="disabled"/>
				</div>
	        </div>
	        <div class="row">
	        	<div class="col_l">
	            	<a class="helpIcon" title="Che compressione hanno queste immagini (dafault is 1:1)">?</a>
	            	<form:label for="compressioneJpeg" path="compressioneJpeg" id ="compressioneLabel" cssErrorClass="error" title="Compressione"><fmt:message key="digitization.editJpegImagesSchedone.compressione"/></form:label>
	            </div>
	            <div class="col_r">
					<form:input path="compressioneJpeg" id="compressione" cssClass="input_4c_disabled" />
				</div>
	        </div>
	    </div> 
	        
	    <hr />
	
	    <div class="listForm">
	    	<div class="row"> 
	        	<div class="col_l">
	        		<a class="helpIcon" title="Numero esatto di immagini in alta risoluzione prodotte per questa filza (compresi i test)">?</a>
	        		<form:label for="numeroTotaleImmaginiJpeg" path="numeroTotaleImmaginiJpeg" id ="numeroImmaginiLabel" cssErrorClass="error" title="Numero totale Immagini"><fmt:message key="digitization.editJpegImagesSchedone.numeroTotalImmagini"/></form:label>
	        	</div>
	        	<div class="col_r">
					<form:input path="numeroTotaleImmaginiJpeg" id="numeroImmagini" cssClass="input_10c" />
				</div>
			</div>
			<div class="row">
	            <div class="col_l">
	            	<a class="helpIcon" title="Dimensione Media delle immagini (usare script adeguato per calcolare)">?</a>
	           		<form:label for="dimMediaImmaginiJpeg" path="dimMediaImmaginiJpeg" id ="dimensioneMediaImmaginiLabel" cssErrorClass="error" title="Dimensione Media Immagini"><fmt:message key="digitization.editJpegImagesSchedone.dimensioneMediaImmagini"/></form:label>
	           	</div>
	           	<div class="col_r">
					<form:input path="dimMediaImmaginiJpeg" id="dimensioneMediaImmagini" cssClass="input_10c" />
	            	<form:select id="formatoMediaImmagini" path="formatoMediaImmaginiJpeg" cssClass="selectform_short" items="${formato}"/>
	            	<%-- the items variable for this select tag must be set. The tree values are Mb, Kb, and Gb, default is Mb--%>
	            </div>
	        </div>
	        <div class="row">
	        	<div class="col_l">
	            	<a class="helpIcon" title="Dimensione Totale delle immagini (usare script adeguato per calcolare)">?</a>
	            	<form:label for="dimTotaleImmaginiJpeg" path="dimTotaleImmaginiJpeg" id ="dimensioneTotaleImmaginiLabel" cssErrorClass="error" title="Dimensione Totale Immagini"><fmt:message key="digitization.editJpegImagesSchedone.dimensioneTotaleImmagini"/></form:label>
	            </div>
	            <div class="col_r">
					<form:input path="dimTotaleImmaginiJpeg" id="dimTotaleImmaginiJpeg" cssClass="input_10c" />
					<form:select id="formatoTotaleImmagini" path="formatoTotaleImmaginiJpeg" cssClass="selectform_short" items="${formato}"/>
	            	<%-- the items variable for this select tag must be set. The tree values are Mb, Kb, and Gb, default is Gb--%>
	            </div>
	        </div>
	    </div>   
	        
	    <div>
	    	<input id="close" class="button_small fl" type="submit" value="Close" title="Do not save changes" />
	        <input id="save" class="button_small fr" type="submit" value="Save" />
	    </div>
	    
	    <input type="hidden" value="" id="modify" />
	
	    </fieldset>
	</form:form>


	<script type="text/javascript">
		$j(document).ready(function() {
			<%-- Initialize input type dates default values --%>
			<%-- TO BE DONE--%>			
			$j("#EditDetailsSchedone").css('visibility', 'hidden');
			$j("#EditTiffImagesSchedone").css('visibility', 'hidden'); 
	        $j("#EditJpegImagesSchedone").css('visibility', 'hidden'); 
	        $j("#EditPdfImagesSchedone").css('visibility', 'hidden');
	        
			$j("#EditJpegImagesForm :input").change(function(){
				$j("#modify").val(1); //set the hidden field if an element is modified
				return false;
			});
	        
	        $j('.helpIcon').tooltip({
				track: true,
				fade: 350 
			});
			
			 $j("#save").click(function(){
		        	$j("#loadingDiv").css('height', $j("#loadingDiv").parent().height());
		        	$j("#loadingDiv").css('visibility', 'visible');
		        });

			 $j('#close').click(function() {
		        	if($j("#modify").val() == 1){
		        		// Block is attached to form otherwise this block does not function when we use in transcribe and contextualize document
						$j('#EditJpegImagesForm').block({ message: $j('#question'),
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
		        		$j.ajax({ url: '${ShowSchedoneURL}', cache: false, success:function(html) { 
		    				$j("#body_left").html(html);
		    			}});
		    				
		    			return false; 
		        	}	        		
			});
	        
			$j("#EditJpegImagesForm").submit(function (){
				$j.ajax({ type:"POST", url:$j(this).attr("action"), data:$j(this).serialize(), async:false, success:function(html) { 
					$j("#body_left").html(html);
				}});
				return false;
			});
		});
	</script>

	<div id="question" style="display:none; cursor: default"> 
		<h1><fmt:message key="digitization.editJpegImagesSchedone.discardChanges"/></h1> 
		<input type="button" id="yes" value="Yes" /> 
		<input type="button" id="no" value="No" /> 
	</div>
	
	<script type="text/javascript">
		$j(document).ready(function() {
			$j('#no').click(function() { 
				$j.unblockUI();
				$j(".blockUI").fadeOut("slow");
				$j("#question").hide();
				$j("#EditJpegImagesSchedoneDiv").append($j("#question"));
				$j(".blockUI").remove();
				return false; 
			}); 
	        
			$j('#yes').click(function() { 
				$j.ajax({ url: '${ShowSchedoneURL}', cache: false, success:function(html) { 
					$j("#body_left").html(html);
				}});
					
				return false; 
			}); 
	     
		});
	</script>
</div>