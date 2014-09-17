<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>

	<c:url var="ReverseProxyIIPImage" value="/mview/IIPImageServer.do"/>
	
	<c:url var="ImagePrefixURL" value="/images/mview/"/>

	<c:url var="PageTurnerDialogUrl" value="/teaching/TeachingPageTurnerDialog.do" >
		<c:param name="entryId" value="${documentExplorer.entryId}" />
		<c:param name="volNum" value="${documentExplorer.volNum}" />
		<c:param name="volLetExt" value="${documentExplorer.volLetExt}" />
		<c:param name="imageOrder" value="${documentExplorer.image.imageOrder}" />
		<c:param name="total" value="${documentExplorer.total}" />
		<c:param name="totalRubricario" value="${documentExplorer.totalRubricario}" />
		<c:param name="totalCarta" value="${documentExplorer.totalCarta}" />
		<c:param name="totalAppendix" value="${documentExplorer.totalAppendix}" />
		<c:param name="totalOther" value="${documentExplorer.totalOther}" />
		<c:param name="totalGuardia" value="${documentExplorer.totalGuardia}" />
		<c:param name="modeEdit" value="false" />
		<c:param name="showExtract" value="false" />
		<c:param name="resourcesForum" value="${resourcesForum}" />
	</c:url>
	
	<c:url var="GetImageAnnotationURL" value="/teaching/GetImageAnnotation.json">
		<c:param name="imageName" value="${documentExplorer.image.imageName}" />
		<c:param name="resourcesForum" value="${resourcesForum}" />
	</c:url>
	
	<c:url var="UpdateAnnotationsURL" value="/teaching/UpdateAnnotations.json">
		<c:param name="imageId" value="${documentExplorer.image.imageId}" />
		<c:param name="imageName" value="${documentExplorer.image.imageName}" />
		<c:param name="resourcesForum" value="${resourcesForum}" />
	</c:url>
		
		<%-- <script>
           window.onbeforeunload = function() {
               return "Are you sure you want to leave the Manuscript Transcriber? Changes will be lost.";
           };
    	</script> --%>
		
		
		<script type="text/javascript">
			var $j = jQuery.noConflict();
			<c:choose>
				<c:when test="${closed}">
					var edit = false;
				</c:when>
				<c:otherwise>
					var edit = true;
				</c:otherwise>
			</c:choose>
			$j(document).ready(function() {
				$j.ajaxSetup ({
					// Disable caching of AJAX responses */
					cache: false
				});
				
				// RR: Added volume informations and insert informations (if needed)
				var volExt = "${documentExplorer.image.volLetExt}";
				var insNum = "${documentExplorer.image.insertNum}";
				var insExt = "${documentExplorer.image.insertLet}";
				var credit = '<span style=\'font-size:16px\'><fmt:message key="mview.showDocumentInManuscriptViewerHtml.volume"/> ${documentExplorer.image.volNum}' + (volExt != '' ? ' <fmt:message key="mview.showDocumentInManuscriptViewerHtml.extension"/> '+volExt : '') + '&nbsp; - </span>';
				if (insNum != '')
					credit += '<span style=\'font-size:16px\'><fmt:message key="mview.showDocumentInManuscriptViewerHtml.insert"/> ' + insNum + (insExt != '' ? ' <fmt:message key="mview.showDocumentInManuscriptViewerHtml.extension"/> '+insExt : '') + '&nbsp; - </span>';
					
				var imageName = "${documentExplorer.image.imageName}";
				
				if ("${documentExplorer.image.imageType}" == 'R') {
					credit += '<span style=\'font-size:16px\'>' + '<fmt:message key="mview.showDocumentInManuscriptViewerHtml.indexOfNames"/>&nbsp;</span>';
				} else if ("${documentExplorer.image.imageType}" == 'C') {
					credit += '<span style=\'font-size:16px\'>' + '<fmt:message key="mview.showDocumentInManuscriptViewerHtml.folio"/>&nbsp;</span>';
				} else if ("${documentExplorer.image.imageType}" == 'A') {
					credit += '<span style=\'font-size:16px\'>' + '<fmt:message key="mview.showDocumentInManuscriptViewerHtml.allegato"/>&nbsp;</span>';
				} else if ("${documentExplorer.image.imageType}" == 'G') {
					credit += '<span style=\'font-size:16px\'>' + '<fmt:message key="mview.showDocumentInManuscriptViewerHtml.guardia"/>&nbsp;</span>';
				} else if ("${documentExplorer.image.imageType}" == 'O') {
					//MD: Is it correct the imageType 'O' for "costola" and "coperta"?
					if(imageName.indexOf("COPERTA") != -1){
						credit += '<span style=\'font-size:16px\'>' + '<fmt:message key="mview.showDocumentInManuscriptViewerHtml.coperta"/>&nbsp;</span>;';
					}
				} else {
		    		credit += ' ';
		    	}
				
				credit+= '<span style=\'font-size:22px\'>' + "${documentExplorer.image.imageProgTypeNum}";
				
				if ("${documentExplorer.image.missedNumbering}") {
					credit += ' ' + "${documentExplorer.image.missedNumbering}";
				}
	
				if ("${documentExplorer.image.imageRectoVerso}" == 'R') {
					credit += '</span>' + ' recto' + '</span>';
				} else if("${documentExplorer.image.imageRectoVerso}" == 'V'){
					credit += '</span>' + ' verso' + '</span>';
				}
				
				//MD:The last control is to verify if the image is a spine
				if(imageName.indexOf("SPI") != -1){
					credit = '<span style=\'font-size:16px\'>' + '<fmt:message key="mview.showDocumentInManuscriptViewerHtml.spine"/>' + '</span>';
				}
				
				iip = new IIPMooViewer( "targetframe", {
					server: '${ReverseProxyIIPImage}',
					prefix: '${ImagePrefixURL}',
					image: '${documentExplorer.image}',
					annotationsType: 'remote',
					retrieveAnnotationsUrl: '${GetImageAnnotationURL}',
					updateAnnotationsUrl: '${UpdateAnnotationsURL}',
					openAnnotationTopicMode: '_parent',
					zoom: 3,
					credit: credit,
					navWinPos: 'left',
					navigation: true,
					showNavWindow: true,
					showNavImage: true, // this property hide navigation image
					showNavButtons: true,
					showHideAnnotationButton: true,
					enableEdit: edit,
					editMode: 'teaching',
					annotations: new Array()
				});
				
				var $pageTurner = $j('<div id="PageTurnerVerticalDiv"></div>').dialog({                                                                                                                                                                   
					autoOpen: true,
					resizable: false,
					width: 145,
					height: 380, 
					minWidth: 145,
					maxWidth: 145,
					maxHeight: 380,
					title: '<fmt:message key="mview.showDocumentInManuscriptViewerHtml.pageTurnerWindow.title"/>',
					position: ['right','top'],                                                                                                                                                       
					closeOnEscape: false,
					open: function(event, ui) { 
						$j(".ui-dialog-titlebar-close").hide();
                		$(this).load('${PageTurnerDialogUrl}' + '&editEnabled=' + edit);
               			},
					dragStart: function(event, ui) {$j(".ui-widget-content").css('opacity', 0.30);},
					dragStop: function(event, ui) {$j(".ui-widget-content").css('opacity', 1);}
				}).dialogExtend({"minimize" : true}); 
				
			});
		</script> 

		  