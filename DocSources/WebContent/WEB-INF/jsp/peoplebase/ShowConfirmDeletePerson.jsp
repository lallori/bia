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
	</div>

	<script>
		$j(document).ready(function() {
			$j("#no").click(function() {			
				Modalbox.hide();
				return false;
			});

			$j("#yes").click(function() {
				$j.ajax({ type:"GET", url: '${CheckPersonIsDeletableURL}', async:false, success:function(json) { 
					if (json.isDeletable == 'false') {
						$j("#DeleteThisRecordDiv").html("");
						$j("#DeleteThisRecordDiv").append('<h1>Please remove this person from all the documents listed here under before trying to deleting it:</h1>');
						if (json.senderDocsRelated>0) {
							$j("#DeleteThisRecordDiv").append(json.senderDocsRelatedURL);
						
							$j("#DeleteThisRecordDiv > .sender_docs").append(' (' + json.senderDocsRelated + ')');
							$j("#DeleteThisRecordDiv > .sender_docs").attr('title','Show me those documents');

							
							$j(".sender_docs").die();
							$j(".sender_docs").live('click', function() {
								var tabName = json.mapNameLf + " - Documents as Sender";
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
							$j("#DeleteThisRecordDiv").append(json.recipientDocsRelatedURL);
							
							$j("#DeleteThisRecordDiv > .recipient_docs").append(' (' + json.recipientDocsRelated + ')');
							$j("#DeleteThisRecordDiv > .recipient_docs").attr('title','Show me those documents');
							$j(".recipient_docs").die();
							$j(".recipient_docs").live('click', function() {
								var tabName = json.mapNameLf + " - Documents as Recipient";
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
							$j("#DeleteThisRecordDiv").append(json.referringDocsRelatedURL);
							
							$j("#DeleteThisRecordDiv > .referred_docs").append(' (' + json.referringDocsRelated + ')');
							$j("#DeleteThisRecordDiv > .referred_docs").attr('title','Show me those documents');
							$j(".referred_docs").die();
							$j(".referred_docs").live('click', function() {
								var tabName = json.mapNameLf + " - Documents Referring to";
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
