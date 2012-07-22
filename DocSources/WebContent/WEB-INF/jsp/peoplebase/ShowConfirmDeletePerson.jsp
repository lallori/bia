<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>

	<c:url var="DeletePersonURL" value="/de/peoplebase/DeletePerson.do">
		<c:param name="personId"   value="${command.personId}" />
	</c:url>
	<c:url var="CheckPersonIsDeletableURL" value="/de/peoplebase/CheckPersonIsDeletable.json">
		<c:param name="personId"   value="${command.personId}" />
	</c:url>
	<c:url var="ShowPersonURL" value="/src/peoplebase/ShowPerson.do">
		<c:param name="personId"   value="${command.personId}" />
	</c:url>

	<div id="DeleteThisRecordDiv">
		<h1>Are you sure you want to delete this record?</h1>
		
		<a id="yes" href="${DeletePersonURL}">YES</a>
	
		<a id="no" href="#">NO</a>
			
		<input id="close" type="submit" title="Close Delete Menu window" value="Close"/>
	</div>

	<script>
		$j(document).ready(function() {
			$j("#close").click(function(){
				Modalbox.hide();
				return false;
			});
			
			$j("#no").click(function() {			
				Modalbox.hide();
				return false;
			});

			$j("#yes").click(function() {
				$j.ajax({ type:"GET", url: '${CheckPersonIsDeletableURL}', async:false, success:function(json) { 
					if (json.isDeletable == 'false') {
						$j("#DeleteThisRecordDiv").html("");
						$j("#DeleteThisRecordDiv").append('<h1>Please remove from any documents related to this Person<p>');
						if (json.senderDocsRelated>0) {
							$j("#DeleteThisRecordDiv").append(json.senderDocsRelatedURL + ' <span class=\"num_docs\"> ' + json.senderDocsRelated + '</span><p>');
							
							$j(".sender_docs").die();
							$j(".sender_docs").live('click', function() {
								var tabName = "Sender Docs " + json.mapNameLf;
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
						if (json.recipientDocsRelated>0){
							$j("#DeleteThisRecordDiv").append(json.recipientDocsRelatedURL  + ' <span class=\"num_docs\"> ' + json.recipientDocsRelated + '</span><p>');

							$j(".recipient_docs").die();
							$j(".recipient_docs").live('click', function() {
								var tabName = "Recipient Docs " + json.mapNameLf;
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
						if (json.referringDocsRelated>0){
							$j("#DeleteThisRecordDiv").append(json.referringDocsRelatedURL + ' <span class=\"num_docs\"> ' + json.referringDocsRelated + '</span><p>');
							
							$j(".referred_docs").die();
							$j(".referred_docs").live('click', function() {
								var tabName = "Docs Referring To " + json.mapNameLf;
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
						$j("#DeleteThisRecordDiv").append('</h1>');

					} else {
						$j.ajax({ type:"POST", url: '${DeletePersonURL}', async:false, success:function(html) {
							$j("#DeleteThisRecordDiv").html(html);
							$j("#body_left").load('${ShowPersonURL}');
						}});
					}
				}});
				return false;
			});
			
		});
	</script>
