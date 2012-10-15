<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>

	<c:url var="IIPImageServer" value="/mview/IIPImageServer.do"/>
	<c:url var="ImagePrefixURL" value="/images/mview/"/>

	<c:url var="EditExtractDialogUrl" value="/de/mview/EditExtractDocumentDialog.do" >
		<c:param name="entryId" value="${requestCommand.entryId}" />
	</c:url>
	<c:url var="EditSynopsisDialogUrl" value="/de/mview/EditSynopsisDocumentDialog.do" >
		<c:param name="entryId" value="${requestCommand.entryId}" />
	</c:url>
	
	<c:url var="PageTurnerDialogUrl" value="/de/mview/PageTurnerDialog.do" >
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
		<c:param name="modeEdit" value="true" />
	</c:url>
	
		<script type="text/javascript">
			transcribing=true;

			var credit = '';	
			var imageName = "${documentExplorer.image.imageName}";
			if ("${documentExplorer.image.imageType}" == 'R') {
				credit += '<span style=\'font-size:16px\'>' + 'index of names &nbsp;';
			} else if ("${documentExplorer.image.imageType}" == 'C') {
				credit += '<span style=\'font-size:16px\'>' + 'folio &nbsp; &nbsp;';
			} else if ("${documentExplorer.image.imageType}" == 'A') {
				credit += '<span style=\'font-size:16px\'>' + 'allegato &nbsp; &nbsp;';
			} else if ("${documentExplorer.image.imageType}" == 'G') {
				credit += '<span style=\'font-size:16px\'>' + 'guardia &nbsp; &nbsp;';
			} else if ("${documentExplorer.image.imageType}" == 'O') {
				//MD: Is it correct the imageType 'O' for "costola" and "coperta"?
				if(imageName.indexOf("COPERTA") != -1){
					credit += '<span style=\'font-size:16px\'>' + 'coperta &nbsp; &nbsp;';
				}
			} else {
	    		var credit = ' ';
	    	}

			credit+= '<span style=\'font-size:22px\'>' + "${documentExplorer.image.imageProgTypeNum}";
			
			if ("${documentExplorer.image.missedNumbering}") {
				credit += ' ' + "${documentExplorer.image.missedNumbering}";
			}

			if ("${documentExplorer.image.imageRectoVerso}" == 'R') {
				credit += '</span>' + ' recto' + '</span>';
			} else if ("${documentExplorer.image.imageRectoVerso}" == 'V'){
				credit += '</span>' + ' verso' + '</span>';
			}
			
			//MD:The last control is to verify if the image is a spine
			if(imageName.indexOf("SPI") != -1){
				credit = '<span style=\'font-size:16px\'>' + 'SPINE' + '</span>';
			}
		
			iip = new IIPMooViewer( "targetframe", {
				server: '${IIPImageServer}',
				prefix: '${ImagePrefixURL}',
				image: '${documentExplorer.image}',
				credit: credit,	
				navigation: true,
				showNavWindow: true,
				showNavImage: true, // this property hide navigation image
				showNavButtons: true,
				navWinPos: 'left',
				winResize: true,
				zoom: 3
			});
			
			$j(document).ready(function() {
				var $dialogExtract = $j('<div id="EditExtractDocumentDiv"></div>')
				.dialog({                                                                                                                                                                   
					autoOpen: true,
					width: 355,
					minWidth: 350,
					minHeight: 200,                                                                                                                                                         
					title: 'EDIT TRANSCRIPTION',
					position: ['center','middle'],                                                                                                                                                       
					closeOnEscape: false,
					maximized:false,
					
					open: function(event, ui) { 
						$j(".ui-dialog-titlebar-close").hide(); 
						$(this).load('${EditExtractDialogUrl}');
					},
					//drag: function(event, ui) {$j(this).append(ui.position.left);},
					dragStart: function(event, ui) {$j(".ui-widget-content").css('opacity', 0.30);}, 
					dragStop: function(event, ui) {$j(".ui-widget-content").css('opacity', 1);}
				}).dialogExtend({"minimize" : true});

				var $dialogSynopsis = $j('<div id="EditSynopsisDocumentDiv"></div>')
				.dialog({                                                                                                                                                                   
					autoOpen: false,
					width: 352,
					minWidth: 350,
					minHeight: 200,                                                                                                                                                         
					title: 'EDIT SYNOPSIS',
					position: ['left','bottom'],                                                                                                                                       
					closeOnEscape: false,
					maximized:false,
					
					open: function(event, ui) { 
						$j(".ui-dialog-titlebar-close").hide(); 
						$(this).load('${EditSynopsisDialogUrl}');
					},
					dragStart: function(event, ui) {$j(".ui-widget-content").css('opacity', 0.30);},
					dragStop: function(event, ui) {$j(".ui-widget-content").css('opacity', 1);}
				}).dialogExtend({"minimize" : true});;
				
				
// 				var $pageTurner = $j('<div id="PageTurnerDiv"></div>')
// 				.dialog({
// 					autoOpen: true,
// 					resizable: false,
// 					width: 470,
// 					height: 112,
// 					minWidth: 470,
// 					minHeight: 112,                                                                                                                                                         
// 					title: 'PAGE TURNER',
// 					position: ['right','middle'],                                                                                                                                                       
// 					closeOnEscape: false,
// 					maximized:false,
					
// 					open: function(event, ui) { 
// 						$j(".ui-dialog-titlebar-close").hide(); 
//                 		$(this).load('${PageTurnerDialogUrl}');
//                		},
// 					dragStart: function(event, ui) {$j(".ui-widget-content").css('opacity', 0.30);},
// 					dragStop: function(event, ui) {$j(".ui-widget-content").css('opacity', 1);}
// 				}).dialogExtend({"minimize" : true});
			
	
// 			});
			
			var $pageTurner = $j('<div id="PageTurnerVerticalDiv"></div>')
			.dialog({                                                                                                                                                                   
				autoOpen: true,
				resizable: false,
				width: 145,
				height: 380, 
				minWidth: 145,
				maxWidth: 145,
				maxHeight: 380,
				
				                                                                                                                                                         
				//title: 'Page T.',
				title: 'Page Turner',
				position: ['left','middle'],                                                                                                                                                       
				closeOnEscape: false,
				open: function(event, ui) { 
					$j(".ui-dialog-titlebar-close").hide();
					//$j("#PageTurnerVerticalDiv").prev().append("<img src='/DocSources/images/mview/button_rotateHorizontal.png' id='rotateHorizontal' title='Horizontal Page Turner'/>");
            		$(this).load('${PageTurnerDialogUrl}');
           			},
				dragStart: function(event, ui) {$j(".ui-widget-content").css('opacity', 0.30);},
				dragStop: function(event, ui) {$j(".ui-widget-content").css('opacity', 1);}
			}).dialogExtend({"minimize" : true}); 

		});

		</script>