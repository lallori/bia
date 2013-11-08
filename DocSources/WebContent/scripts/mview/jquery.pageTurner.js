/**
*  Ajax Page Turner for jQuery, version 1.0
 * 
 * Developed by Medici Archive Project (2010-2012).
 * 
 * This file is part of DocSources.
 * 
 * DocSources is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 2 of the License, or
 * (at your option) any later version.
 * 
 * DocSources is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with this library; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301  USA
 *
 * As a special exception, if you link this library with other files to
 * produce an executable, this library does not by itself cause the
 * resulting executable to be covered by the GNU General Public License.
 * This exception does not however invalidate any other reasons why the
 * executable file might be covered by the GNU General Public License.
 * 
 * Last Review: 11/26/2010
*/
(function ($) {

    $.pageTurnerForm = {};

    $.pageTurnerForm.defaultParams = {
        "searchUrl":  "/DocSources/src/mview/SearchCarta.json",
        "getLinkedDocumentUrl":  "/DocSources/src/mview/GetLinkedDocument.json",
        "IIPImageServer": "/DocSources/mview/ReverseProxyIIPImage.do",
        "imagePrefix": "/DocSources/images/mview",
        "status":      "no",
        "canTranscribe":  "false",
        "scale": "0",
        "annotationsType": "remote",
        "retrieveAnnotationsUrl": "",
        "updateAnnotationsUrl": "",
        "annotations": new Array(),
        "textVolume": "Volume",
        "textExtension": "Ext",
        "textInsert": "Insert",
        "textIndexOfNames": "index of names &nbsp;",
        "textFolio": "folio &nbsp; &nbsp;",
        "textAttachment": "allegato &nbsp; &nbsp;",
        "textGuardia" : "guardia &nbsp; &nbsp;",
        "textCoperta" : "coperta &nbsp; &nbsp;",
        "textSpine" : "SPINE",
        "textRecto" : "recto",
        "textVerso" : "verso"
    };

    $.fn.pageTurnerForm = function (params) {
    	var functionParams = $.extend($.pageTurnerForm.defaultParams, params);

        // Loop over all matching elements
        this.each(function (){

            // Add an onClick behavior to this element
            $(this).submit(function (event) {
                // Prevent the browser's default onClick handler
                event.preventDefault();
                
                var parameters = '';
                $(this).find('input').each(function() {
                	parameters += $(this).attr('id') + '=' + $(this).val() + '&';
                });
				
                $.get(functionParams["searchUrl"], parameters, function(data){
					if (data.error) {
						$j('#notFound').dialog('open');
					} else {
						$("#targetframe").html('');

						// RR: Added volume informations and insert informations (if needed)
						var volExt = data.volLetExt != null ? data.volLetExt : '';
						var insNum = data.insertNum != null ? data.insertNum : '';
						var insExt = data.insertExt != null ? data.insertExt : '';
						var credit = '<span style=\'font-size:16px\'>' + functionParams["textVolume"] + ' ' + data.volNum + (volExt != '' ? ' ' + functionParams["textExtension"] + ' ' + volExt : '') + '&nbsp; - </span>';
						if (insNum != '')
							credit += '<span style=\'font-size:16px\'>' + functionParams["textInsert"] + ' ' + insNum + (insExt != '' ? ' ' + functionParams["textExtension"] + ' ' + insExt : '') + '&nbsp; - </span>';
						
						if (data.imageType == 'R') {
							credit += '<span style=\'font-size:16px\'>' + functionParams["textIndexOfNames"];
						}  else if (data.imageType == 'C') {
							credit += '<span style=\'font-size:16px\'>' + functionParams["textFolio"];
						} else if (data.imageType == 'A') {
							credit += '<span style=\'font-size:16px\'>' + functionParams["textAttachment"];
						} else if (data.imageType == 'G') {
							credit += '<span style=\'font-size:16px\'>' + functionParams["textGuardia"];
						}else if (data.imageType == 'O') {
							//MD: Is it correct the imageType 'O' for "costola" and "coperta"?
							if(data.imageName.indexOf("COPERTA") != -1){
								credit += '<span style=\'font-size:16px\'>' + functionParams["textCoperta"];
							}
						} else {
	                		credit += ' ';
	                	}
						
						credit+= '<span style=\'font-size:22px\'>' + data.imageProgTypeNum;
						if (data.missedNumbering) {
							credit += ' ' + data.missedNumbering;
						}
						if (data.imageRectoVerso == 'R') {
							credit += '</span> ' + functionParams["textRecto"] + '</span>';
						} else if(data.imageRectoVerso == 'V'){
							credit += '</span> ' + functionParams["textVerso"] + '</span>';
						}
						
						//MD:The last control is to verify if the image is a spine
						if(data.imageName.indexOf("SPI") != -1){
							credit = '<span style=\'font-size:16px\'>' + functionParams["textSpine"] + '</span>';
						}

						iipMooViewer = new IIPMooViewer( "targetframe", {
							server: functionParams["IIPImageServer"],
							image: data.imageCompleteName,
							prefix: functionParams["imagePrefix"],
							credit: credit, 
							navWinPos: 'left',
							navigation: true,
							showNavWindow: true,
							showNavImage: true, // this property hide navigation image
							showNavButtons: true,
							winResize: true,
							zoom: 3,
							scale: 0,
							annotationsType: functionParams["annotationsType"],
							retrieveAnnotationsUrl: functionParams["retrieveAnnotationsUrl"] + "?imageId=" + data.imageId + "&imageName=" + data.imageName,
							updateAnnotationsUrl: functionParams["updateAnnotationsUrl"] + "?imageId=" + data.imageId + "&imageName=" + data.imageName,
							annotations: functionParams["annotations"]
						});

						if (data.previousPage == '') {
							$("#previous").removeAttr('href');
						} else {
							$("#previous").attr('href', data.previousPage);
						}
						if (data.nextPage == '') {
							$("#next").removeAttr('href');
						} else {
							$("#next").attr('href', data.nextPage);
						}
						
						$.get(functionParams["getLinkedDocumentUrl"], parameters, function(data){
							// We set currentImage
							currentImage = data.imageId;
							$j("#currentImageOrder").val(data.imageOrder);
							if (transcribing == false) {
								if (data.error == 'wrongType' || data.imageType == 'R') {
									$j("#unvailableTranscribe").css('display', 'block');
			    					$j("#alreadyTranscribe").css('display', 'none');
			    					$j("#showAlreadyTranscribed").css('display', 'none');
			    					$j("#showAlreadyTranscribed").removeAttr('href');
			    					$j("#showAlreadyTranscribedDocs").css('display', 'none');
			    					$j("#showTranscription").css('display', 'none');
									$j("#transcribeAnyway").css('display', 'none');
			    					$j("#notExtract").css('display', 'none');
		    						$j("#extractTranscribe").css('display', 'none');
			    					$j("#readyToTranscribe").css('display', 'none');
			    					$j("#choiceThisFolioStart").css('display', 'none');
			    				} else if (data.linkedDocument == 'true') {
			    					if(data.isExtract == 'false'){
			    						$j("#notExtract").css('display', 'block');
			    						$j("#extractTranscribe").css('display', 'block');
			    						$j("#currentEntryId").val(data.entryId);
			    						$j("#alreadyTranscribe").css('display', 'none');
			    						$j("#showAlreadyTranscribed").css('display', 'none');
			    						$j("#showAlreadyTranscribedDocs").css('display', 'none');
			    						$j("#showTranscription").css('display', 'none');
			    						$j("#transcribeAnyway").css('display', 'none');
			    						$j("#unvailableTranscribe").css('display', 'none');
			    						$j("#readyToTranscribe").css('display', 'none');
			    						$j("#choiceThisFolioStart").css('display', 'none');
			    					}else{
			    						if(data.countAlreadyEntered == 1){
											$j("#alreadyTranscribe").css('display', 'block');
											$j("#showTranscription").css('display', 'block');
											$j("#showAlreadyTranscribed").css('display', 'none');
											$j("#transcribeAnyway").css('display', 'block');
											$j("#showAlreadyTranscribedDocs").css('display', 'none');
											$j("#notExtract").css('display', 'none');
											$j("#extractTranscribe").css('display', 'none');
											$j("#unvailableTranscribe").css('display', 'none');
											$j("#readyToTranscribe").css('display', 'none');
											$j("#choiceThisFolioStart").css('display', 'none');
											$j("#showAlreadyTranscribed").attr("href", data.showLinkedDocument);
											$j("#currentEntryId").val(data.entryId);
											//If the extract of this document is already opened, we visualize the "Show record" button
					    					if($j("#ShowExtractDocumentDiv").dialog("isOpen")){
					    						if($j("#extractEntryId").val() == $j('#currentEntryId').val()){
					    							$j("#showTranscription").css('display', 'none');
													$j("#showAlreadyTranscribed").css('display', 'block');
					    						}
					    					}
										}else if(data.countAlreadyEntered > 1){
											$j("#alreadyTranscribe").css('display', 'block');
											$j("#showAlreadyTranscribed").css('display', 'none');
											$j("#showAlreadyTranscribedDocs").css('display', 'block');
											$j("#showTranscription").css('display', 'none');
											$j("#transcribeAnyway").css('display', 'block');
											$j("#notExtract").css('display', 'none');
											$j("#extractTranscribe").css('display', 'none');
											$j("#unvailableTranscribe").css('display', 'none');
											$j("#readyToTranscribe").css('display', 'none');
											$j("#choiceThisFolioStart").css('display', 'none');
											$j("#showAlreadyTranscribedDocs").attr("href", data.showLinkedDocument);
										}
			    					}
			    					
			    				} else if (data.linkedDocument == 'false') {
			    					// Only users with special role can transcribe new document. 
			    					if (functionParams["canTranscribe"] =='true' && data.imageType != "R") {
			    						$j("#readyToTranscribe").css('display', 'block');
			    					}
			    					$j("#alreadyTranscribe").css('display', 'none');
			    					$j("#showAlreadyTranscribed").css('display', 'none');
			    					$j("#showAlreadyTranscribed").removeAttr('href');
			    					$j("#showAlreadyTranscribedDocs").css('display', 'none');
			    					$j("#showTranscription").css('display', 'none');
									$j("#transcribeAnyway").css('display', 'none');
			    					$j("#notExtract").css('display', 'none');
		    						$j("#extractTranscribe").css('display', 'none');
			    					$j("#unvailableTranscribe").css('display', 'none');
			    					$j("#choiceThisFolioStart").css('display', 'none');
			    				} else {
			    					$j("#unvailableTranscribe").css('display', 'none');
			    					$j("#alreadyTranscribe").css('display', 'none');
			    					$j("#showAlreadyTranscribed").css('display', 'none');
			    					$j("#showAlreadyTranscribed").removeAttr('href');
			    					$j("#showAlreadyTranscribedDocs").css('display', 'none');
			    					$j("#showTranscription").css('display', 'none');
									$j("#transcribeAnyway").css('display', 'none');
			    					$j("#notExtract").css('display', 'none');
		    						$j("#extractTranscribe").css('display', 'none');
			    					$j("#readyToTranscribe").css('display', 'none');
			    					$j("#choiceThisFolioStart").css('display', 'none');
			    				}
							} else {
								if($j("#EditExtractDocumentForm").length != 0){
									$j("#unvailableTranscribe").css('display', 'none');
									$j("#alreadyTranscribe").css('display', 'none');
									$j("#showAlreadyTranscribed").css('display', 'none');
									$j("#showAlreadyTranscribedDocs").css('display', 'none');
									$j("#showTranscription").css('display', 'none');
									$j("#transcribeAnyway").css('display', 'none');
									$j("#notExtract").css('display', 'none');
									$j("#extractTranscribe").css('display', 'none');
									$j("#readyToTranscribe").css('display', 'none');
									$j("#choiceThisFolioStart").css('display', 'none');
									$j("#transcribeDiv").append($j("#transcribeMode"));
									$j("#transcribeMode").css('display', 'inline');
								}else if(data.linkedDocument == 'true'){ 							
									//In this case we choose the start folio to transcribe
									$j("#unvailableTranscribe").css('display', 'none');
									$j("#alreadyTranscribe").css('display', 'block');
									$j("#showTranscription").css('display', 'none');
									$j("#showAlreadyTranscribed").css('display', 'none');
									$j("#showAlreadyTranscribedDocs").css('display', 'none');
									$j("#transcribeAnyway").css('display', 'none');
									$j("#notExtract").css('display', 'none');
									$j("#extractTranscribe").css('display', 'none');
									$j("#readyToTranscribe").css('display', 'none');
									$j("#choiceThisFolioStart").css('display', 'block');
									$j("#choiceThisFolioStart").css('opacity', '0.5');
									$j("#transcribeDiv").append($j("#transcribeMode"));
									$j("#transcribeMode").css('display', 'block');
								}else if(data.linkedDocument == 'false'){
									$j("#unvailableTranscribe").css('display', 'none');
									$j("#alreadyTranscribe").css('display', 'none');
									$j("#showTranscription").css('display', 'none');
									$j("#showAlreadyTranscribed").css('display', 'none');
									$j("#showAlreadyTranscribedDocs").css('display', 'none');
									$j("#transcribeAnyway").css('display', 'none');
									$j("#notExtract").css('display', 'none');
									$j("#extractTranscribe").css('display', 'none');
									$j("#readyToTranscribe").css('display', 'none');
									$j("#choiceThisFolioStart").css('display', 'block');
									$j("#choiceThisFolioStart").css('opacity', '1');
									$j("#transcribeDiv").append($j("#transcribeMode"));
									$j("#transcribeMode").css('display', 'block');
								}
							}
							
		    			});
					}
				});

            });
            
        });

        return $;
    };

    $.pageTurnerPage = {};

    $.pageTurnerPage.defaultParams = {
        "searchUrl":  "/DocSources/src/mview/SearchCarta.json",
        "getLinkedDocumentUrl":  "/DocSources/src/mview/GetLinkedDocument.json",
        "IIPImageServer": "/DocSources/mview/ReverseProxyIIPImage.do",
        "imagePrefix": "/DocSources/images/mview",
        "status":      "no",
        "canTranscribe":  "false",
        "scale": "0",
        "annotationsType": "remote",
        "retrieveAnnotationsUrl": "",
        "updateAnnotationsUrl": "",
        "annotations": new Array(),
        "textVolume": "Volume",
        "textExtension": "Ext",
        "textInsert": "Insert",
        "textIndexOfNames": "index of names &nbsp;",
        "textFolio": "folio &nbsp; &nbsp;",
        "textAttachment": "allegato &nbsp; &nbsp;",
        "textGuardia" : "guardia &nbsp; &nbsp;",
        "textCoperta" : "coperta &nbsp; &nbsp;",
        "textSpine" : "SPINE",
        "textRecto" : "recto",
        "textVerso" : "verso"
    };

    $.fn.pageTurnerPage = function (params) {
        var functionParams = $.extend($.pageTurnerPage.defaultParams, params);
        
        // Loop over all matching elements
        this.each(function (){

            // Add an onClick behavior to this element
            $(this).click(function (event) {
                // Prevent the browser's default onClick handler
                event.preventDefault();

                // We extract parameter from page link...
                var parameters = $j(this).attr("href").substring($j(this).attr("href").indexOf("?"));

                $.ajax({ type:"GET", url:$j(this).attr("href"), async:false, success:function(data) {
					$("#targetframe").html('');
					
					// RR: Added volume informations and insert informations (if needed)
					var volExt = data.volLetExt != null ? data.volLetExt : '';
					var insNum = data.insertNum != null ? data.insertNum : '';
					var insExt = data.insertExt != null ? data.insertExt : '';
					var credit = '<span style=\'font-size:16px\'>' + functionParams["textVolume"] + ' ' + data.volNum + (volExt != '' ? ' ' + functionParams["textExtension"] + ' ' + volExt : '') + '&nbsp; - </span>';
					if (insNum != '')
						credit += '<span style=\'font-size:16px\'>' + functionParams["textInsert"] + ' ' + insNum + (insExt != '' ? ' ' + functionParams["textExtension"] + ' ' + insExt : '') + '&nbsp; - </span>';
					
					if (data.imageType == 'R') {
						credit += '<span style=\'font-size:16px\'>' + functionParams["textIndexOfNames"] + '</span>';
					}  else if (data.imageType == 'C') {
						credit += '<span style=\'font-size:16px\'>' + functionParams["textFolio"] + '</span>';
					} else if (data.imageType == 'A') {
						credit += '<span style=\'font-size:16px\'>' + functionParams["textAttachment"] + '</span>';
					} else if (data.imageType == 'G') {
						credit += '<span style=\'font-size:16px\'>' + functionParams["textGuardia"] + '</span>';
					}else if (data.imageType == 'O') {
						//MD: Is it correct the imageType 'O' for "costola" and "coperta"?
						if(data.imageName.indexOf("COPERTA") != -1){
							credit += '<span style=\'font-size:16px\'>' + functionParams["textCoperta"] + '</span>';
						}
					} else {
                		credit += ' ';
                	}
					
					credit+= '<span style=\'font-size:22px\'>' + data.imageProgTypeNum;
					if (data.missedNumbering) {
						credit += ' ' + data.missedNumbering;
					}
					if (data.imageRectoVerso == 'R') {
						credit += '</span> ' + functionParams["textRecto"] + '</span>';
					} else if(data.imageRectoVerso == 'V'){
						credit += '</span> ' + functionParams["textVerso"] + '</span>';
					}
					
					//MD:The last control is to verify if the image is a spine
					if(data.imageName.indexOf("SPI") != -1){
						credit = '<span style=\'font-size:16px\'>' + functionParams["textSpine"] + '</span>';
					}
					
					iipMooViewer = new IIPMooViewer( "targetframe", {
						server: functionParams["IIPImageServer"],
						image: data.imageCompleteName,
						prefix: functionParams["imagePrefix"],
						credit: credit, 
						navigation: true,
						showNavWindow: true,
						navWinPos: 'left',
						showNavImage: true, // this property hide navigation image
						showNavButtons: true,
						winResize: true,
						zoom: 3,
						scale: 0,
						annotationsType: functionParams["annotationsType"],
						retrieveAnnotationsUrl: functionParams["retrieveAnnotationsUrl"] + "?imageId=" + data.imageId + "&imageName=" + data.imageName,
						updateAnnotationsUrl: functionParams["updateAnnotationsUrl"] + "?imageId=" + data.imageId + "&imageName=" + data.imageName,
						annotations: functionParams["annotations"]
					});
					if (data.previousPage == '') {
						$("#previous").removeAttr('href');
					} else {
						$("#previous").attr('href', data.previousPage);
					}
					if (data.nextPage == '') {
						$("#next").removeAttr('href');
					} else {
						$("#next").attr('href', data.nextPage);
					}

					$.get(functionParams["getLinkedDocumentUrl"], parameters, function(data){
						// We set currentImage
						currentImage = data.imageId;
						$j("#currentImageOrder").val(data.imageOrder);
						if (transcribing == false) {
							if (data.error == 'wrongType') {
		    					$j("#unvailableTranscribe").css('display', 'block');
		    					$j("#alreadyTranscribe").css('display', 'none');
		    					$j("#showTranscription").css('display', 'none');
		    					$j("#showAlreadyTranscribed").css('display', 'none');
		    					$j("#showAlreadyTranscribed").removeAttr('href');
		    					$j("#showAlreadyTranscribedDocs").css('display', 'none');
								$j("#transcribeAnyway").css('display', 'none');
		    					$j("#notExtract").css('display', 'none');
	    						$j("#extractTranscribe").css('display', 'none');
		    					$j("#readyToTranscribe").css('display', 'none');
		    					$j("#choiceThisFolioStart").css('display', 'none');
		    				} else if (data.linkedDocument == 'true') {
		    					if(data.isExtract == 'false'){
		    						$j("#notExtract").css('display', 'block');
		    						$j("#extractTranscribe").css('display', 'block');
		    						$j("#currentEntryId").val(data.entryId);
		    						$j("#alreadyTranscribe").css('display', 'none');
		    						$j("#showAlreadyTranscribed").css('display', 'none');
		    						$j("#showAlreadyTranscribedDocs").css('display', 'none');
		    						$j("#showTranscription").css('display', 'none');
									$j("#transcribeAnyway").css('display', 'none');
		    						$j("#unvailableTranscribe").css('display', 'none');
		    						$j("#readyToTranscribe").css('display', 'none');
		    						$j("#choiceThisFolioStart").css('display', 'none');
		    					}else{
		    						if(data.countAlreadyEntered == 1){
										$j("#alreadyTranscribe").css('display', 'block');
										$j("#showAlreadyTranscribed").css('display', 'none');
										$j("#showTranscription").css('display', 'block');
										$j("#transcribeAnyway").css('display', 'block');
										$j("#showAlreadyTranscribedDocs").css('display', 'none');
										$j("#notExtract").css('display', 'none');
										$j("#extractTranscribe").css('display', 'none');
										$j("#unvailableTranscribe").css('display', 'none');
										$j("#readyToTranscribe").css('display', 'none');
										$j("#choiceThisFolioStart").css('display', 'none');
										$j("#showAlreadyTranscribed").attr("href", data.showLinkedDocument);
										$j("#currentEntryId").val(data.entryId);
										//If the extract of this document is already opened, we visualize the "Show record" button
				    					if($j("#ShowExtractDocumentDiv").dialog("isOpen")){
				    						if($j("#extractEntryId").val() == $j('#currentEntryId').val()){
				    							$j("#showTranscription").css('display', 'none');
												$j("#showAlreadyTranscribed").css('display', 'block');
				    						}
				    					}
									}else if(data.countAlreadyEntered > 1){
										$j("#alreadyTranscribe").css('display', 'block');
										$j("#showAlreadyTranscribed").css('display', 'none');
										$j("#showAlreadyTranscribedDocs").css('display', 'block');
										$j("#showTranscription").css('display', 'none');
										$j("#transcribeAnyway").css('display', 'block');
										$j("#notExtract").css('display', 'none');
										$j("#extractTranscribe").css('display', 'none');
										$j("#unvailableTranscribe").css('display', 'none');
										$j("#readyToTranscribe").css('display', 'none');
										$j("#choiceThisFolioStart").css('display', 'none');
										$j("#showAlreadyTranscribedDocs").attr("href", data.showLinkedDocument);
									}
		    					}
		    					
		    				} else if (data.linkedDocument == 'false') {
		    					// Only users with special role can transcribe new document. 
		    					if (functionParams["canTranscribe"] =='true') {
		    						$j("#readyToTranscribe").css('display', 'block');
		    					}
		    					$j("#alreadyTranscribe").css('display', 'none');
		    					$j("#showTranscription").css('display', 'none');
		    					$j("#showAlreadyTranscribed").css('display', 'none');
		    					$j("#showAlreadyTranscribed").removeAttr('href');
		    					$j("#showAlreadyTranscribedDocs").css('display', 'none');
								$j("#transcribeAnyway").css('display', 'none');
		    					$j("#notExtract").css('display', 'none');
	    						$j("#extractTranscribe").css('display', 'none');
		    					$j("#unvailableTranscribe").css('display', 'none');
		    					$j("#choiceThisFolioStart").css('display', 'none');
		    				} else {
		    					$j("#unvailableTranscribe").css('display', 'none');
		    					$j("#alreadyTranscribe").css('display', 'none');
		    					$j("#showTranscription").css('display', 'none');
		    					$j("#showAlreadyTranscribed").css('display', 'none');
		    					$j("#showAlreadyTranscribed").removeAttr('href');
		    					$j("#showAlreadyTranscribedDocs").css('display', 'none');
								$j("#transcribeAnyway").css('display', 'none');
		    					$j("#notExtract").css('display', 'none');
	    						$j("#extractTranscribe").css('display', 'none');
		    					$j("#readyToTranscribe").css('display', 'none');
		    					$j("#choiceThisFolioStart").css('display', 'none');
		    				}
						} else {
							if($j("#EditExtractDocumentForm").length != 0){
								$j("#unvailableTranscribe").css('display', 'none');
								$j("#alreadyTranscribe").css('display', 'none');
								$j("#showAlreadyTranscribed").css('display', 'none');
								$j("#showAlreadyTranscribedDocs").css('display', 'none');
								$j("#showTranscription").css('display', 'none');
								$j("#transcribeAnyway").css('display', 'none');
								$j("#notExtract").css('display', 'none');
								$j("#extractTranscribe").css('display', 'none');
								$j("#readyToTranscribe").css('display', 'none');
								$j("#choiceThisFolioStart").css('display', 'none');
								$j("#transcribeDiv").append($j("#transcribeMode"));
								$j("#transcribeMode").css('display', 'inline');
							}else if(data.linkedDocument == 'true'){ 							
								//In this case we choose the start folio to transcribe
								$j("#unvailableTranscribe").css('display', 'none');
								$j("#alreadyTranscribe").css('display', 'block');
								$j("#showTranscription").css('display', 'none');
								$j("#showAlreadyTranscribed").css('display', 'none');
								$j("#showAlreadyTranscribedDocs").css('display', 'none');
								$j("#transcribeAnyway").css('display', 'none');
								$j("#notExtract").css('display', 'none');
								$j("#extractTranscribe").css('display', 'none');
								$j("#readyToTranscribe").css('display', 'none');
								$j("#choiceThisFolioStart").css('display', 'block');
								$j("#choiceThisFolioStart").css('opacity', '0.5');
								$j("#transcribeDiv").append($j("#transcribeMode"));
								$j("#transcribeMode").css('display', 'block');
							}else if(data.linkedDocument == 'false'){
								$j("#unvailableTranscribe").css('display', 'none');
								$j("#alreadyTranscribe").css('display', 'none');
								$j("#showTranscription").css('display', 'none');
								$j("#showAlreadyTranscribed").css('display', 'none');
								$j("#showAlreadyTranscribedDocs").css('display', 'none');
								$j("#transcribeAnyway").css('display', 'none');
								$j("#notExtract").css('display', 'none');
								$j("#extractTranscribe").css('display', 'none');
								$j("#readyToTranscribe").css('display', 'none');
								$j("#choiceThisFolioStart").css('display', 'block');
								$j("#choiceThisFolioStart").css('opacity', '1');
								$j("#transcribeDiv").append($j("#transcribeMode"));
								$j("#transcribeMode").css('display', 'block');
							}
						}
	    			});

				}});
            });
            
        });

        return $;
    };
})(jQuery);