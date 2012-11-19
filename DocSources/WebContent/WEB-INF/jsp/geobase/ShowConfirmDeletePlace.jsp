<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>

	<c:url var="DeletePlaceURL" value="/de/geobase/DeletePlace.do">
		<c:param name="placeAllId"   value="${command.placeAllId}" />
	</c:url>
	<c:url var="CheckPlaceIsDeletableURL" value="/de/geobase/CheckPlaceIsDeletable.json">
		<c:param name="placeAllId"   value="${command.placeAllId}" />
	</c:url>
	<c:url var="ShowPlaceURL" value="/src/geobase/ShowPlace.do">
		<c:param name="placeAllId"   value="${command.placeAllId}" />
	</c:url>

	<div id="DeleteThisRecordDiv">
		<h1>Are you sure you want to delete this record?</h1>
		
		<a id="yes" href="${DeletePlaceURL}">YES</a>
	
		<a id="no" href="#">NO</a>
	</div>

	<script>
		$j(document).ready(function() {
			
			$j("#no").click(function() {			
				Modalbox.hide();
				return false;
			});

			$j("#yes").click(function() {
				$j.ajax({ type:"GET", url: '${CheckPlaceIsDeletableURL}', async:false, success:function(json) { 
					if (json.isDeletable == 'false') {
						$j("#DeleteThisRecordDiv").html("");
						$j("#DeleteThisRecordDiv").append('<h1>Please remove people and documents indexed to this place before deleting it.<p></h1>');
						
						if (json.documentsInTopicsPlace>0) {
							//$j("#DeleteThisRecordDiv").append(json.documentsInTopicsPlaceURL + ' <span class=\"num_docs\"> ' + json.topicsPlace + '</span><p>');
							$j("#DeleteThisRecordDiv").append(json.documentsInTopicsPlaceURL);
							
							$j("#DeleteThisRecordDiv > .topics").append(' (' + json.topicsPlace + ')');
							$j("#DeleteThisRecordDiv > .topics").attr('title','Show me those items');
							
							$j(".topics").die();
							$j(".topics").live('click', function() {
								var tabName = "Topics " + json.placeName;
								var numTab = 0;
								
								//Check if already exist a tab with this person
								var tabExist = false;
								$j("#tabs ul li a").each(function(){
									if(!tabExist){
										if(this.text != ""){
											numTab++;
										}
									}
									if(this.text == tabName){
										tabExist = true;
									}
								});
								
								if(!tabExist){
									$j( "#tabs" ).tabs( "add" , $j(this).attr("href"), tabName + "</span></a><span class=\"ui-icon ui-icon-close\" title=\"Close Tab\">Remove Tab");
									$j("#tabs").tabs("select", $j("#tabs").tabs("length")-1);
									return false;
								}else{
									$j("#tabs").tabs("select", numTab);
									return false;
								}
							});
						}
						
						if(json.senderDocumentsPlace>0) {
							//$j("#DeleteThisRecordDiv").append(json.senderDocumentsPlaceURL + ' <span class=\"num_docs\"> ' + json.senderDocumentsPlace + '</span><p>');
							$j("#DeleteThisRecordDiv").append(json.senderDocumentsPlaceURL);
							
							$j("#DeleteThisRecordDiv > .sender").append(' (' + json.senderDocumentsPlace + ')');
							$j("#DeleteThisRecordDiv > .sender").attr('title','Show me those items');
							$j(".sender").click(function(){
								var tabName = "Senders " + json.placeName;
								var numTab = 0;
								
								//Check if already exist a tab with this person
								var tabExist = false;
								$j("#tabs ul li a").each(function(){
									if(!tabExist){
										if(this.text != ""){
											numTab++;
										}
									}
									if(this.text == tabName){
										tabExist = true;
									}
								});
								
								if(!tabExist){
									$j( "#tabs" ).tabs( "add" , $j(this).attr("href"), tabName + "</span></a><span class=\"ui-icon ui-icon-close\" title=\"Close Tab\">Remove Tab");
									$j("#tabs").tabs("select", $j("#tabs").tabs("length")-1);
									return false;
								}else{
									$j("#tabs").tabs("select", numTab);
									return false;
								}
							});
						}
				
						if(json.recipientDocumentsPlace>0) {
							//$j("#DeleteThisRecordDiv").append(json.recipientDocumentsPlaceURL + ' <span class=\"num_docs\"> ' + json.recipientDocumentsPlace + '</span><p>');
							$j("#DeleteThisRecordDiv").append(json.recipientDocumentsPlaceURL);
							
							$j("#DeleteThisRecordDiv > .recipient").append(' (' + json.recipientDocumentsPlace + ')');
							$j("#DeleteThisRecordDiv > .recipient").attr('title','Show me those items');

							$j(".recipient").click(function(){
								var tabName = "Recipients " + json.placeName;
								var numTab = 0;
								
								//Check if already exist a tab with this person
								var tabExist = false;
								$j("#tabs ul li a").each(function(){
									if(!tabExist){
										if(this.text != ""){
											numTab++;
										}
									}
									if(this.text == tabName){
										tabExist = true;
									}
								});
								
								if(!tabExist){
									$j( "#tabs" ).tabs( "add" , $j(this).attr("href"), tabName + "</span></a><span class=\"ui-icon ui-icon-close\" title=\"Close Tab\">Remove Tab");
									$j("#tabs").tabs("select", $j("#tabs").tabs("length")-1);
									return false;
								}else{
									$j("#tabs").tabs("select", numTab);
									return false;
								}
							});
						}
		
						if(json.birthPlace>0) {
							//$j("#DeleteThisRecordDiv").append(json.birthPlaceURL + ' <span class=\"num_docs\"> ' + json.birthPlace + '</span><p>');
							$j("#DeleteThisRecordDiv").append(json.birthPlaceURL);
							
							$j("#DeleteThisRecordDiv > .birth").append(' (' + json.birthPlace + ')');
							$j("#DeleteThisRecordDiv > .birth").attr('title','Show me those items');
							$j(".birth").click(function(){
								var tabName = "Birth " + json.placeName;
								var numTab = 0;
								
								//Check if already exist a tab with this person
								var tabExist = false;
								$j("#tabs ul li a").each(function(){
									if(!tabExist){
										if(this.text != ""){
											numTab++;
										}
									}
									if(this.text == tabName){
										tabExist = true;
									}
								});
								
								if(!tabExist){
									$j( "#tabs" ).tabs( "add" , $j(this).attr("href"), tabName + "</span></a><span class=\"ui-icon ui-icon-close\" title=\"Close Tab\">Remove Tab");
									$j("#tabs").tabs("select", $j("#tabs").tabs("length")-1);
									return false;
								}else{
									$j("#tabs").tabs("select", numTab);
									return false;
								}
							});
						}

						if(json.activeStartPlace>0) {
							//$j("#DeleteThisRecordDiv").append(json.activeStartPlaceURL + ' <span class=\"num_docs\"> ' + json.activeStartPlace + '</span><p>');
							$j("#DeleteThisRecordDiv").append(json.activeStartPlaceURL);
							
							$j("#DeleteThisRecordDiv > .activeStart").append(' (' + json.activeStartPlace + ')');
							$j("#DeleteThisRecordDiv > .activeStart").attr('title','Show me those items');
							
							$j(".activeStart").click(function(){
								var tabName = "Active Start " + json.placeName;
								var numTab = 0;
								
								//Check if already exist a tab with this person
								var tabExist = false;
								$j("#tabs ul li a").each(function(){
									if(!tabExist){
										if(this.text != ""){
											numTab++;
										}
									}
									if(this.text == tabName){
										tabExist = true;
									}
								});
								
								if(!tabExist){
									$j( "#tabs" ).tabs( "add" , $j(this).attr("href"), tabName + "</span></a><span class=\"ui-icon ui-icon-close\" title=\"Close Tab\">Remove Tab");
									$j("#tabs").tabs("select", $j("#tabs").tabs("length")-1);
									return false;
								}else{
									$j("#tabs").tabs("select", numTab);
									return false;
								}
							});							
						}

						if(json.deathPlace>0) {
							//$j("#DeleteThisRecordDiv").append(json.deathPlaceURL + ' <span class=\"num_docs\"> ' + json.deathPlace + '</span><p>');
							$j("#DeleteThisRecordDiv").append(json.deathPlaceURL);
							
							$j("#DeleteThisRecordDiv > .death").append(' (' + json.deathPlace + ')');
							$j("#DeleteThisRecordDiv > .death").attr('title','Show me those items');
							
							$j(".death").click(function(){
								var tabName = "Death " + json.placeName;
								var numTab = 0;
								
								//Check if already exist a tab with this person
								var tabExist = false;
								$j("#tabs ul li a").each(function(){
									if(!tabExist){
										if(this.text != ""){
											numTab++;
										}
									}
									if(this.text == tabName){
										tabExist = true;
									}
								});
								
								if(!tabExist){
									$j( "#tabs" ).tabs( "add" , $j(this).attr("href"), tabName + "</span></a><span class=\"ui-icon ui-icon-close\" title=\"Close Tab\">Remove Tab");
									$j("#tabs").tabs("select", $j("#tabs").tabs("length")-1);
									return false;
								}else{
									$j("#tabs").tabs("select", numTab);
									return false;
								}
							});
						}

						if(json.activeEndPlace>0) {
							//$j("#DeleteThisRecordDiv").append(json.activeEndPlaceURL + ' <span class=\"num_docs\"> ' + json.activeEndPlace + '</span><p>');
							$j("#DeleteThisRecordDiv").append(json.activeEndPlaceURL);
							
							$j("#DeleteThisRecordDiv > .activeEnd").append(' (' + json.activeEndPlace + ')');
							$j("#DeleteThisRecordDiv > .activeEnd").attr('title','Show me those items');
							$j(".activeEnd").click(function(){
								var tabName = "Active End ${place.placeName}";
								var numTab = 0;
								
								//Check if already exist a tab with this person
								var tabExist = false;
								$j("#tabs ul li a").each(function(){
									if(!tabExist){
										if(this.text != ""){
											numTab++;
										}
									}
									if(this.text == tabName){
										tabExist = true;
									}
								});
								
								if(!tabExist){
									$j( "#tabs" ).tabs( "add" , $j(this).attr("href"), tabName + "</span></a><span class=\"ui-icon ui-icon-close\" title=\"Close Tab\">Remove Tab");
									$j("#tabs").tabs("select", $j("#tabs").tabs("length")-1);
									return false;
								}else{
									$j("#tabs").tabs("select", numTab);
									return false;
								}
							});
						}
				
					} else {
						$j.ajax({ type:"POST", url:'${DeletePlaceURL}', data:$j(this).serialize(), async:false, success:function(html) {
							$j("#DeleteThisRecordDiv").load(html);
							$j("#body_left").load('${ShowPlaceURL}');
						}})
					}
				}});
				return false;
			});
		});
	</script>
