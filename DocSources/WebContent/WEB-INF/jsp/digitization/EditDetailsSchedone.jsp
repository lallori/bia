<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>

	<security:authorize ifAnyGranted="ROLE_ADMINISTRATORS, ROLE_DIGITIZATION_COORDINATORS, DIGITIZATION_TECHNICIANS">
		<c:url var="ShowSchedoneURL" value="/src/docbase/ShowSchedone.do">
			<c:param name="schedoneId"   value="${command.schedoneId}" />
		</c:url>
	</security:authorize>

	<%-- Loading div when saving the form --%>
	<div id="loadingDiv"></div>
	<form:form id="EditDetailsSchedoneForm" method="post" cssClass="edit">
	    <fieldset>
	    <legend><b>SCHEDONE DETAILS</b></legend>
	        <div style="margin-top:5px">
	            <a class="helpIcon" title="Search here for words (in English) that appear in document synopses and/or words (in the original language and with the original spelling) that appear in document extracts.">?</a>
	            <label for="istituto" id="istitutoLabel">Istituto</label>
	            <input id="istituto" name="istituto" class="input_29c" type="text" value="Archivio di Stato di Firenze"/>
	        </div>
	        <div>
	            <a class="helpIcon" title="Search here for words (in English) that appear in document synopses and/or words (in the original language and with the original spelling) that appear in document extracts.">?</a>
	            <label for="fondo" id="fondoLabel">Fondo</label>
	            <input id="fondo" name="fondo" class="input_29c" type="text" value="Mediceo del Principato"/>
	        </div>
	        <div>
	            <a class="helpIcon" title="Search here for words (in English) that appear in document synopses and/or words (in the original language and with the original spelling) that appear in document extracts.">?</a>
	            <label for="serie" id="serieLabel">Serie</label>
	            <input id="serie" name="serie" class="input_29c" type="text" value="Minute di Lettere"/>
	        </div>
	        <div>
	            <a class="helpIcon" title="Search here for words (in English) that appear in document synopses and/or words (in the original language and with the original spelling) that appear in document extracts.">?</a>
	            <label for="unita" id="unitaLabel">N. Unità</label>
	            <input id="unita" name="unita" class="input_5c" type="text" value=""/>
	        </div>
	        <div>
	            <a class="helpIcon" title="Search here for words (in English) that appear in document synopses and/or words (in the original language and with the original spelling) that appear in document extracts.">?</a>
	            <label for="dateEstreme" id="dateEstremeLabel">Date estreme</label>
	            <input id="fondo" name="fondo" class="input_5c" type="text" value="yyyy"/>
	            <input id="fondo" name="fondo" class="input_5c" type="text" value="mm"/>
	            <input id="fondo" name="fondo" class="input_5c" type="text" value="dd"/>
	        </div>
	        <div style="margin-top:5px">
	            <a class="helpIcon" title="Search here for words (in English) that appear in document synopses and/or words (in the original language and with the original spelling) that appear in document extracts.">?</a>
	            <label for="dateNotes" id="dateNotesLabel">Descrizione contenuto</label>
	        </div>
	        <div><textarea id="dateNotes" name="dateNotes" class="txtarea"></textarea></div>
	        
	        <div>
	            <a class="helpIcon" title="Search here for words (in English) that appear in document synopses and/or words (in the original language and with the original spelling) that appear in document extracts.">?</a>
	            <label for="legatura" id="legaturaLabel">Legatura</label>
	            <input id="legatura" name="legatura" class="input_29c" type="text" value=""/>
	        </div>
	        <div>
	            <a class="helpIcon" title="Search here for words (in English) that appear in document synopses and/or words (in the original language and with the original spelling) that appear in document extracts.">?</a>
	            <label for="supporto" id="supportoLabel">Supporto</label>
	            <input id="supporto" name="supporto" class="input_29c" type="text" value=""/>
	        </div>
	        <div>
	            <a class="helpIcon" title="Search here for words (in English) that appear in document synopses and/or words (in the original language and with the original spelling) that appear in document extracts.">?</a>
	            <label for="cartulazione" id="cartulazioneLabel">Cartulazione</label>
	            <input id="cartulazione" name="cartulazione" class="input_29c" type="text" value=""/>
	        </div>
	        <div style="margin-top:5px">
	            <a class="helpIcon" title="Search here for words (in English) that appear in document synopses and/or words (in the original language and with the original spelling) that appear in document extracts.">?</a>
	            <label for="noteCartulazione" id="noteCartulazioneLabel">Note alla cartulazione</label>
	        </div>
	        <div><textarea id="noteCartulazione" name="noteCartulazione" class="txtarea"></textarea></div>
	        <div style="margin-top:5px">
	            <a class="helpIcon" title="Search here for words (in English) that appear in document synopses and/or words (in the original language and with the original spelling) that appear in document extracts.">?</a>
	            <label for="carteBianche" id="carteBiancheLabel">Carte bianche</label>
	        </div>
	        <div><textarea id="carteBianche" name="carteBianche" class="txtarea"></textarea></div>
	        <div>
	            <a class="helpIcon" title="Search here for words (in English) that appear in document synopses and/or words (in the original language and with the original spelling) that appear in document extracts.">?</a>
	            <label for="carteMancanti" id="carteMancantiLabel">Carte mancanti</label>
	            <input id="carteMancanti" name="carteMancanti" class="input_29c" type="text" value=""/>
	        </div>
	        <div>
	            <a class="helpIcon" title="Search here for words (in English) that appear in document synopses and/or words (in the original language and with the original spelling) that appear in document extracts.">?</a>
	            <label for="dimensioniBase" id="dimensioniBaseLabel">Dimensioni base</label>
	            <input id="dimensioniBase" name="dimensioniBase" class="input_5c" type="text" value=""/>mm
	        </div>
	        <div>
	            <a class="helpIcon" title="Search here for words (in English) that appear in document synopses and/or words (in the original language and with the original spelling) that appear in document extracts.">?</a>
	            <label for="dimensioniAltezza" id="dimensioniAltezzaLabel">Dimensioni altezza</label>
	            <input id="dimensioniAltezza" name="dimensioniAltezza" class="input_5c" type="text" value=""/>mm
	        </div>
	        <div>
	            <a class="helpIcon" title="Search here for words (in English) that appear in document synopses and/or words (in the original language and with the original spelling) that appear in document extracts.">?</a>
	            <label for="tipoRipresa" id="tipoRipresaLabel">Tipo di ripresa</label>
	            <input id="tipoRipresa" name="tipoRipresa" class="input_29c" type="text" value="Da originale"/>
	        </div>
	        <div>
	            <a class="helpIcon" title="B/N, toni di griggio, colori">?</a>
	            <label for="coloreImmagine" id="coloreImmagineLabel">Colore immagine</label>
	            <input id="coloreImmagine" name="coloreImmagine" class="input_29c" type="text" value="RGB"/>
	        </div>
	        <div>
	            <a class="helpIcon" title="B/N, toni di griggio, colori">?</a>
	            <label for="risoluzione" id="risoluzioneLabel">Risoluzione</label>
	            <input id="risoluzione" name="risoluzione" class="input_29c" type="text" value="300 ppi"/>
	        </div>
	        <div style="margin-top:5px">
	            <a class="helpIcon" title="<b>Quattro cifre</b> (numero progressivo immagine) <b>_coperta/C</b> (carta) <b>/G</b> (guardia) <b>/A</b>  (allegato) <b>/R</b> (repertorio) <b>_tre cifre</b> (numero della carta) <b>_tre lettere</b> (bis ter qua) <b>_R/V</b>">?</a>
	            <label for="carteBianche" id="carteBiancheLabel">Nome files</label>
	        </div>
	        <div><textarea id="carteBianche" name="carteBianche" class="txtarea"></textarea></div>
	        <div style="margin-top:5px">
	            <a class="helpIcon" title="B/N, toni di griggio, colori">?</a>
	            <label for="responsabileFotoriproduzione" id="responsabileFotoriproduzioneLabel">Responsabile fotoriproduzione</label>
	            <input id="responsabileFotoriproduzione" name="responsabileFotoriproduzione" class="input_23c" type="text" value="dr. Francesca Klein"/>
	        </div>
	        <div>
	            <a class="helpIcon" title="B/N, toni di griggio, colori">?</a>
	            <label for="daRipresa" id="daRipresaLabel">Da ripresa</label>
	            <input id="daRipresa" name="daRipresa" class="input_29c" type="text" value=""/>
	        </div>
	        <div>
	            <a class="helpIcon" title="B/N, toni di griggio, colori">?</a>
	            <label for="operatore" id="operatoreLabel">Operatore</label>
	            <input id="operatore" name="operatore" class="input_29c" type="text" value=""/>
	        </div>
	        
	        <div>
	            <input id="close" type="submit" value="Close" title="Do not save changes" />
	            <input id="save" class="save" type="submit" value="Save" />
	        </div>
	        <input type="hidden" value="" id="modify" />
	    </fieldset>
	</form:form>

	<script type="text/javascript">
		$j(document).ready(function() {
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

