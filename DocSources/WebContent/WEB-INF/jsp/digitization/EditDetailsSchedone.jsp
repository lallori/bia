<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>

	<security:authorize ifAnyGranted="ROLE_ADMINISTRATORS, ROLE_DIGITIZATION_COORDINATORS, DIGITIZATION_TECHNICIANS">
		<c:url var="EditDetailsSchedoneURL" value="/digitization/EditDetailsSchedone.do">
			<c:param name="schedoneId"   value="${command.schedoneId}" />
		</c:url>
		<c:url var="ShowSchedoneURL" value="/digitization/ShowSchedone.do">
			<c:param name="schedoneId"   value="${command.schedoneId}" />
		</c:url>
	</security:authorize>

	<%-- Loading div when saving the form --%>
	<div id="loadingDiv"></div>
	<form:form id="EditDetailsSchedoneForm" action="" method="post" cssClass="edit">
	    <fieldset>
	    <legend><b>SCHEDONE DETAILS</b></legend>
	    	<div class="listForm">
				<div class="row">
					<div class="col_l">
						<a class="helpIcon" title="The instutition this digital collection belongs to">?</a>
						<form:label for="istituto" path="istituto" id ="istitutoLabel" cssErrorClass="error" title="Istituto">Istituto</form:label>
					</div>
					<div class="col_r"><form:input path="istituto" id="istituto" cssClass="input_35c" value="" /></div>
				</div>
				<div class="row">
					<div class="col_l">
						<a class="helpIcon" title="Archival corpus that this Schedone belongs to">?</a>
	            		<form:label for="fondo" path="fondo" id ="fondoLabel" cssErrorClass="error" title="Fondo">Fondo</form:label>
					</div>
					<div class="col_r"><form:input path="fondo" id="fondo" cssClass="input_35c" /></div>
				</div>
				<div class="row">
					<div class="col_l">
						<a class="helpIcon" title="Serie, Carteggio for this digitized volume">?</a>
	            		<form:label for="serie" path="serie" id ="serieLabel" cssErrorClass="error" title="Serie">Serie</form:label>
					</div>
					<div class="col_r"><form:input path="serie" id="serie" cssClass="input_35c" /></div>
				</div>
			</div>
			<div class="listForm">
				<div class="row">
					<div class="col_l">
						<a class="helpIcon" title="Volume number">?</a>
	            		<form:label for="numeroUnita" path="numeroUnita" id ="numeroUnitaLabel" cssErrorClass="error" title="Unit&agrave;">N. unit&agrave; (Numero volume)</form:label>
					</div>
					<div class="col_l"><form:input path="numeroUnita" id="numeroUnita" cssClass="input_4c" /></div>
					<div class="col_r"><form:label for="volLetExt" id="volLetExtLabel" path="volLetExt">Volume extension</form:label></div>
                	<div class="col_r"><form:input id="volLetExt" name="volLetExt" path="volLetExt" cssClass="input_1c" type="text" value="" maxlength="1" cssErrorClass="error"/></div>
				</div>
			</div>
			<div class="listForm">
				<div class="row">
					<div class="col_l">
						<a class="helpIcon" title="Help to be created">?</a>
						<label for="dataInizio" id="dataInizioLabel">Data inizio</label>
					</div>
					<div class="col_r">
						<form:label for="dataInizioAnno" id="dataInizioAnnoLabel" path="dataInizioAnno">Anno</form:label>
						<form:input id="dataInizioAnno" path="dataInizioAnno" class="input_4c" value="" maxlength="4"/>
						<form:label for="dataInizioMese" id="dataInizioMeseLabel" path="dataInizioMese">Mese</form:label>
						<form:select id="dataInizioMese" path="dataInizioMese" cssClass="selectform_long" items="${months}" itemValue="monthNum" itemLabel="monthName"/>
						<form:label for="dataInizioGiorno" id="dataInizioGiornoLabel" path="dataInizioGiorno">Giorno</form:label>
						<form:input id="dataInizioGiorno" path="dataInizioGiorno" class="input_2c" maxlength="2"/>
					</div>
				</div>
				<div class="row">
					<div class="col_l">
						<a class="helpIcon" title="Help to be created">?</a>
						<label for="dataFine" id="dataFineLabel">Data fine</label>
					</div>
					<div class="col_r">
						<form:label for="dataFineAnno" id="dataFineAnnoLabel" path="dataFineAnno">Anno</form:label>
						<form:input id="dataFineAnno" path="dataFineAnno" class="input_4c" value="" maxlength="4"/>
						<form:label for="dataFineMese" id="dataFineMeseLabel" path="dataFineMese">Mese</form:label>
						<form:select id="dataFineMese" path="dataFineMese" cssClass="selectform_long" items="${months}" itemValue="monthNum" itemLabel="monthName"/>
						<form:label for="dataFineGiorno" id="dataFineGiornoLabel" path="dataFineGiorno">Giorno</form:label>
						<form:input id="dataFineGiorno" path="dataFineGiorno" class="input_2c" maxlength="2"/>
					</div>
				</div>
			</div>
	        
	        <div class="listForm">
				<div class="row">
					<a class="helpIcon" title="Description of this volume content in Italian">?</a>
	        	 	<form:label for="descrizioneContenuto" path="descrizioneContenuto" id ="descrizioneContenutoLabel" cssErrorClass="error" title="Descrizione contenuto">Descrizione contenuto</form:label>
				</div>
				<div class="row"><form:textarea path="descrizioneContenuto" id="descrizioneContenuto" cssClass="txtarea"></form:textarea></div>
				<div class="row">
					<a class="helpIcon" title="English translation for Descrizione Contenuto of this volume">?</a>
	        	 	<form:label for="descrizioneContenutoEng" path="descrizioneContenutoEng" id ="descrizioneContenutoEngLabel" cssErrorClass="error" title="Content description">Content Description</form:label>
				</div>
				<div class="row"><form:textarea path="descrizioneContenutoEng" id="descrizioneContenutoEng" cssClass="txtarea"></form:textarea></div>
			</div>
			
			<div class="listForm">
				<div class="row">
					<div class="col_l">
						<a class="helpIcon" title="What kind of legatura does this volume have?">?</a>
	            		<form:label for="legatura" path="legatura" id ="legaturaLabel" cssErrorClass="error" title="Legatura">Legatura</form:label>
					</div>
					<div class="col_r"><form:input path="legatura" id="legatura" cssClass="input_29c" /></div>
				</div>
				<div class="row">
					<div class="col_l">
						<a class="helpIcon" title="This help has to be created">?</a>
	            		<form:label for="supporto" path="supporto" id ="supportoLabel" cssErrorClass="error" title="supporto">Supporto</form:label>
					</div>
					<div class="col_r"><form:input path="supporto" id="supporto" cssClass="input_29c" /></div>
				</div>
				<div class="row">
					<div class="col_l">
						<a class="helpIcon" title="This help has to be created">?</a>
	            		<form:label for="cartulazione" path="cartulazione" id ="cartulazioneLabel" cssErrorClass="error" title="cartulazione">Cartulazione</form:label>
					</div>
					<div class="col_r"><form:input path="cartulazione" id="cartulazione" cssClass="input_29c" /></div>
				</div>
			</div>
	        
			<div class="listForm">
				<div class="row">
					<a class="helpIcon" title="Notes to the Cartulazione in Italian">?</a>
	            	<form:label for="noteCartulazione" path="noteCartulazione" id ="noteCartulazioneLabel" cssErrorClass="error" title="cartulazione">Note alla Cartulazione</form:label>
				</div>
				<div class="row"><form:textarea id="noteCartulazione" path="noteCartulazione" cssClass="txtarea"></form:textarea></div>
				<div class="row">
					<a class="helpIcon" title="English translation for Note alla Cartulazione">?</a>
	            	<form:label for="noteCartulazioneEng" path="noteCartulazioneEng" id ="noteCartulazioneEngLabel" cssErrorClass="error" title="Notes for Cartulation">Notes for the Cartulazione</form:label>
				</div>
				<div class="row"><form:textarea id="noteCartulazioneEng" path="noteCartulazioneEng" cssClass="txtarea"></form:textarea></div>
				<div class="row">
					 <a class="helpIcon" title="Blank Pages">?</a>
	           		<form:label for="carteBianche" path="carteBianche" id ="carteBiancheLabel" cssErrorClass="error" title="carte bianche">Carte bianche</form:label> 
				</div>
				<div class="row"><form:textarea id="carteBianche" path="carteBianche" cssClass="txtarea"></form:textarea></div>
			</div>
			
			<div class="listForm">
				<div class="row">
					<div class="col_l">
						<a class="helpIcon" title="Missing folios">?</a>
	            		<form:label for="carteMancanti" path="carteMancanti" id ="carteMancantiLabel" cssErrorClass="error" title="Carte mancanti">Carte Mancanti</form:label>
					</div>
					<div class="col_r"><form:input path="carteMancanti" id="carteMancanti" cssClass="input_24c" /></div>
				</div>
				<div class="row">
					<div class="col_l">
						<a class="helpIcon" title="use millimeters not inches">?</a>
	            		<form:label for="dimensioniBase" path="dimensioniBase" id ="dimensioniBaseLabel" cssErrorClass="error" title="Dimensioni Base">Dimensioni Base</form:label>
					</div>
					<div class="col_r"><form:input path="dimensioniBase" id="dimensioniBase" cssClass="input_5c" /> mm</div>
				</div>
				<div class="row">
					<div class="col_l">
						 <a class="helpIcon" title="use millimeters not inches">?</a>
	            		 <form:label for="dimensioniAltezza" path="dimensioniAltezza" id ="dimensioniAltezzaLabel" cssErrorClass="error" title="Dimensioni Altezza">Dimensioni Altezza</form:label>
					</div>
					<div class="col_r"><form:input path="dimensioniAltezza" id="dimensioniAltezza" cssClass="input_5c" /> mm</div>
				</div>
				<div class="row">
					<div class="col_l">
						<a class="helpIcon" title="What kind of digitization equipment are you using? A digital camera? A scanner? etc.">?</a>
	            		<form:label for="tipoRipresa" path="tipoRipresa" id ="tipoRipresaLabel" cssErrorClass="error" title="Tipo di ripresa">Digitization Type</form:label>
					</div>
					<div class="col_r"><form:input path="tipoRipresa" id="tipoRipresa" cssClass="input_24c" /></div>
				</div>
				<div class="row">
					<div class="col_l">
						 <a class="helpIcon" title="B&W, grayscale, RGB?">?</a>
	            		 <form:label for="coloreImmagine" path="coloreImmagine" id ="coloreImmagineLabel" cssErrorClass="error" title="Schema colore immagini">Schema colore immagini</form:label>
					</div>
					<div class="col_r"><form:input path="coloreImmagine" id="coloreImmagine" cssClass="input_24c" /></div>
				</div>
				<div class="row">
					<div class="col_l">
						<a class="helpIcon" title="At what resolution? (must use PPI, pixels per inch)">?</a>
	            		<form:label for="risoluzione" path="risoluzione" id ="risoluzioneLabel" cssErrorClass="error" title="Colore immagine">Image Resolution</form:label>
					</div>
					<div class="col_r"><form:input path="risoluzione" id="risoluzione" cssClass="input_24c" /></div>
				</div>
			</div>
	       
			<div class="listForm">
				<div class="row">
					<a class="helpIcon" title="Default filename: <b>Quattro cifre</b> (numero progressivo immagine) <b>_coperta/C</b> (carta) <b>/G</b> (guardia) <b>/A</b>  (allegato) <b>/R</b> (repertorio) <b>_tre cifre</b> (numero della carta) <b>_tre lettere</b> (bis ter qua) <b>_R/V</b>">?</a>
	            	<form:label for="nomeFiles" path="nomeFiles" id ="nomeFilesLabel" cssErrorClass="error" title="Nome files">Nome Files</form:label>
				</div>
				<div class="row"><form:textarea id="nomeFiles" path="nomeFiles" cssClass="txtarea"></form:textarea></div>
			</div>
			
			<div class="listForm">
				<div class="row">
					<div class="col_l">	
						<a class="helpIcon" title="Il responsabile del progetto di digitalizzazione">?</a>
	            		<form:label for="responsabileFotoRiproduzione" path="responsabileFotoRiproduzione" id ="responsabileFotoRiproduzioneLabel" cssErrorClass="error" title="Responsabile Fotoriproduzione">Responsabile Fotoriproduzione</form:label>
					</div>
					<div class="col_r"><form:input path="responsabileFotoRiproduzione" id="responsabileFotoRiproduzione" cssClass="input_23c" /></div>
				</div>
			</div>
			<div class="listForm">
				<div class="row">
					<div class="col_l">
						<a class="helpIcon" title="Data della Ripresa">?</a>
	            		<label for="daRipresa" id="daRipresaLabel">Data ripresa</label>
					</div>
					<div class="col_r">
						<form:label for="dataRipresaAnno" id="dataRipresaAnnoLabel" path="dataRipresaAnno">Anno</form:label>
						<form:input id="dataRipresaAnno" path="dataRipresaAnno" class="input_4c" value="" maxlength="4"/>
						<form:label for="dataRipresaMese" id="dataRipresaMeseLabel" path="dataRipresaMese">Mese</form:label>
						<form:select id="dataRipresaMese" path="dataRipresaMese" cssClass="selectform_long" items="${months}" itemValue="monthNum" itemLabel="monthName"/>
						<form:label for="dataRipresaGiorno" id="dataRipresaGiornoLabel" path="dataRipresaGiorno">Giorno</form:label>
						<form:input id="dataRipresaGiorno" path="dataRipresaGiorno" class="input_2c" maxlength="2"/>
					</div>
				</div>
				<div class="row">
					<div class="col_l">
						<a class="helpIcon" title="Name and Lastname of the Digitization Tecnicians">?</a>
	             		<form:label for="operatore" path="operatore" id ="operatoreLabel" cssErrorClass="error" title="Operatore/1">Operatore</form:label>
					</div>
					<div class="col_r"><form:input path="operatore" id="operatore" cssClass="input_23c" /></div>
				</div>
			</div>
			
			<form:hidden path="seriesRefNum"/>
			
			<form:errors path="numeroUnita" cssClass="inputerrors" htmlEscape="false"/>
			
			<div>
	            <input id="close" type="submit" value="Close" title="Do not save changes" />
	            <input id="save" class="save" type="submit" value="Save" />
	        </div>
	        <input type="hidden" value="" id="modify" />
	    </fieldset>
	</form:form>
	
	<c:url var="searchSeriesListUrl" value="/digitization/SearchSeriesList.json"/>

	<script type="text/javascript">
		$j(document).ready(function() {
			<%-- Initialize input type dates default values --%>
			<%-- TO BE DONE--%>
			
			
			$j("#EditDetailsSchedone").css('visibility', 'hidden');
			$j("#EditTiffImagesSchedone").css('visibility', 'hidden'); 
	        $j("#EditJpegImagesSchedone").css('visibility', 'hidden'); 
	        $j("#EditPDFImagesSchedone").css('visibility', 'hidden'); 
	        
	        var a = $j('#serie').autocompleteGeneral({ 
			    serviceUrl:'${searchSeriesListUrl}',
			    minChars:1, 
			    delimiter: /(,|;)\s*/, // regex or character
			    maxHeight:400,
			    width:600,
			    zIndex: 9999,
			    deferRequestBy: 0, //miliseconds
			    noCache: true, //default is false, set to true to disable caching
			    onSelect: function(value, data){ 
			    	if(data == '')
			    		data = 0;
			    	$j('#seriesRefNum').val(data);
			    }
			});
			
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
		        		$j.ajax({ url: '${ShowSchedoneURL}', cache: false, success:function(html) { 
		    				$j("#body_left").html(html);
		    			}});
		    				
		    			return false; 
		        	}	        		
			});
	        
			$j("#EditDetailsSchedoneForm").submit(function (){
				$j.ajax({ type:"POST", url:$j(this).attr("action"), data:$j(this).serialize(), async:false, success:function(html) { 
					if($j(html).find(".inputerrors").length > 0){
						$j("#EditDetailsSchedoneDiv").html(html);
					}else{
						$j("#body_left").html(html);
					}
					
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
				$j.ajax({ url: '${ShowSchedoneURL}', cache: false, success:function(html) { 
					$j("#body_left").html(html);
				}});
					
				return false; 
			}); 
	     
		});
	</script>

</div>

