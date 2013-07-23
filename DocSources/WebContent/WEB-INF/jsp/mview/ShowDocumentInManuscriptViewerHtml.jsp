<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>

	<c:url var="ReverseProxyIIPImage" value="/mview/IIPImageServer.do"/>
	<c:url var="GetImageAnnotationURL" value="/src/mview/GetImageAnnotation.json">
		<c:param name="imageId" value="${documentExplorer.image.imageId}"></c:param>
		<c:param name="imageName" value="${documentExplorer.image.imageName}"></c:param>
	</c:url>
	<c:url var="UpdateAnnotationsURL" value="/src/mview/UpdateAnnotations.json">
		<c:param name="imageId" value="${documentExplorer.image.imageId}"></c:param>
		<c:param name="imageName" value="${documentExplorer.image.imageName}"></c:param>
	</c:url>
	<c:url var="ImagePrefixURL" value="/images/mview/"/>

	<c:url var="PageTurnerDialogUrl" value="/src/mview/PageTurnerDialog.do" >
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
	</c:url>
		
		<%-- <script>
           window.onbeforeunload = function() {
               return "Are you sure you want to leave the Manuscript Transcriber? Changes will be lost.";
           };
    	</script> --%>
		
		
		<script type="text/javascript">
			var $j = jQuery.noConflict();
			$j(document).ready(function() {
				$j.ajaxSetup ({
					// Disable caching of AJAX responses */
					cache: false
				});

				var credit = '';
				var imageName = "${documentExplorer.image.imageName}";
				var annotations = new Array();
				if ("${documentExplorer.image.imageType}" == 'R') {
					credit += '<span style=\'font-size:16px\'>' + '<fmt:message key="mview.showDocumentInManuscriptViewerHtml.indexOfNames"/> &nbsp;';
				} else if ("${documentExplorer.image.imageType}" == 'C') {
					credit += '<span style=\'font-size:16px\'>' + '<fmt:message key="mview.showDocumentInManuscriptViewerHtml.folio"/> &nbsp; &nbsp;';
				} else if ("${documentExplorer.image.imageType}" == 'A') {
					credit += '<span style=\'font-size:16px\'>' + '<fmt:message key="mview.showDocumentInManuscriptViewerHtml.allegato"/> &nbsp; &nbsp;';
				} else if ("${documentExplorer.image.imageType}" == 'G') {
					credit += '<span style=\'font-size:16px\'>' + '<fmt:message key="mview.showDocumentInManuscriptViewerHtml.guardia"/> &nbsp; &nbsp;';
				}else if ("${documentExplorer.image.imageType}" == 'O') {
					//MD: Is it correct the imageType 'O' for "costola" and "coperta"?
					if(imageName.indexOf("COPERTA") != -1){
						credit += '<span style=\'font-size:16px\'>' + '<fmt:message key="mview.showDocumentInManuscriptViewerHtml.coperta"/> &nbsp; &nbsp;';
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
					zoom: 3,
					credit: credit,
					navWinPos: 'left',
					annotationsType: 'remote',
					retrieveAnnotationsUrl: '${GetImageAnnotationURL}',
					updateAnnotationsUrl: '${UpdateAnnotationsURL}',
					annotations: annotations
				});
				
				var $pageTurner = $j('<div id="PageTurnerVerticalDiv"></div>')
				.dialog({                                                                                                                                                                   
					autoOpen: true,
					resizable: false,
					width: 145,
					height: 380, 
					minWidth: 145,
					maxWidth: 145,
					maxHeight: 380,
					
					                                                                                                                                                         
					//title: 'Page Turner',
					title: '<fmt:message key="mview.showDocumentInManuscriptViewerHtml.pageTurnerWindow.title"/>',
					position: ['left','middle'],                                                                                                                                                       
					closeOnEscape: false,
					open: function(event, ui) { 
						$j(".ui-dialog-titlebar-close").hide();
                		$(this).load('${PageTurnerDialogUrl}');
               			},
					dragStart: function(event, ui) {$j(".ui-widget-content").css('opacity', 0.30);},
					dragStop: function(event, ui) {$j(".ui-widget-content").css('opacity', 1);}
				}).dialogExtend({"minimize" : true}); 
				
				//To manage the form of the annotation
				$j("input[name=category]:radio").die();
				$j("input[name=category]:radio").live('change', function(){
					if($j("input[name=category]:checked").val() == 'GENERAL'){
						$j(".annotation form").parent().css("background-color", "rgba(255, 255, 0, 0.2)");
					}
					if($j("input[name=category]:checked").val() == 'PALEOGRAPHY'){
						$j(".annotation form").parent().css("background-color", "rgba(255, 130, 0, 0.2)");
					}
					if($j("input[name=category]:checked").val() == 'PERSONAL'){
						$j("#annotationTextarea").css("display", "inherit");
						$j(".annotation form").parent().css("background-color", "rgba(180, 0, 255, 0.2)");
					}else{
						$j("#annotationTextarea").css("display", "none");
					}
				});
				
				$j(".delAnnotation").die()
				$j(".delAnnotation").live('click',function(e){
					e.preventDefault();
									
					var div = $j(this);
					$j(this).parent().block({ message: $j('#question'),
						css: { 
							border: 'none', 
							padding: '5px',
							boxShadow: '1px 1px 10px #666',
							'-webkit-box-shadow': '1px 1px 10px #666'
							} ,
							overlayCSS: { backgroundColor: '#999' }	
					}); 
					
					$j("#noQuestion").die();
					$j('#noQuestion').live('click', function() {
						$j.unblockUI();
						$j(".blockUI").fadeOut("slow");
						$j("#question").hide();
						$j(div).parent().append($j("#question"));
						$j(".blockUI").remove();
						return false; 
					}); 
				});

			});
		</script> 

		  