<%@ taglib prefix="bia" uri="http://bia.medici.org/jsp:jstl" %>  
<%@ taglib prefix="biasecurity" uri="http://bia.medici.org/jsp:jstl_security" %> 
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>

<script type='text/javascript' src="<c:url value="/scripts/jquery.autocomplete.general.js"/>"></script>

<link rel="stylesheet" type="text/css" media="screen" href="<c:url value="/styles/1024/js/jquery.autocomplete2.css" />"/>

<c:url var="EmailMessageURL" value="/community/EmailMessage.do"/>

<c:url var="LoadingImageURL" value="/images/loading_autocomplete.gif"/>

<c:url var="MyInboxURL" value="/community/ShowMessagesByCategory.do?userMessageCategory=INBOX"/>

<form:form id="emailMessageForm">
	<div class="listMessages">
		<div class="row">
			<div class="col_r">
	        	<form:label path="userRoles" for="to" id="toLabel"><fmt:message key="community.emailMessage.to"/></form:label>
	        </div>
	        <div class="col_l">
	        	<div>
	        	<ul style="list-style:none outside none;;height:130px;overflow:hidden;width:450px;">
	        	<c:forEach var="currentAuthority" items="${authorities}" varStatus="iterator">	
  						<li style="width:220px;float:left;">
  							<form:checkbox id="groupPolicies" name="userRoles" cssClass="userRoles" value="${currentAuthority.authority.value}" path="userRoles" />
							<label for="groupPolicies">${authorities[iterator.index].description}</label>
						</li>			
		 		</c:forEach>
		 			<li style="width:220px;float:left;">
		 				<form:checkbox id="allUser" name="selectAll" cssClass="" value="All" path="userRoles"/>
		 				<label for="allUser"><fmt:message key="community.emailMessage.allUsers"/></label>
		 			</li>
		 		</ul>
		 		</div>
		 	</div>
		 </div>
		 <div class="row">
		 	<div class="col_r">
		 		<fmt:message key="community.emailMessage.orSelect"/>
		 	</div>
		 	<div class="col_l"> 
		 		<form:input path="accountDescription" id="to" name="subject" class="input_25c" type="text" value=""/><!-- Autocompleter members -->
	        </div>
	    </div>
		<div class="row">
			<div class="col_r">
	        	<form:label path="subject" for="subject" id="subjectLabel"><fmt:message key="community.emailMessage.subject"/></form:label>
	        </div>
	        <div class="col_l">
	        	<form:input path="subject" id="subject" name="subject" class="input_25c" type="text" value=""/>
	    	</div>
	    </div>
	</div>
	<div>
		<form:textarea id="htmlboxEmail" path="text" style="width:970px; height:300px"></form:textarea>
	</div>
    <input type="submit" value="Send" class="buttonSmall button_small" id="send">
    <a href="#" id="preview" class="buttonSmall button_small"><fmt:message key="community.emailMessage.preview"/></a>
    <form:hidden path="account"/>
</form:form>

<div id="messageSent" title="Sent Message" style="display:none"> 
	<p>
		<span class="ui-icon ui-icon-circle-check" style="float:left; margin:0 7px 0 0;"></span>
		<fmt:message key="community.emailMessage.thisMessage"/>
	</p>
</div>

<div id="messageNotValid" title="Post" style="display:none"> 
	<p>
		<span class="ui-icon ui-icon-circle-check" style="float:left; margin:0 7px 0 0;"></span>
		<fmt:message key="community.emailMessage.writeSubjectAndText"/>
	</p>
</div>

<div id="messagePreview" style="display:none">
	<h1><fmt:message key="community.emailMessage.pReview"/></h1>
    <h2 id="previewSubject"></h2>
    <p id="previewText"></p>
</div>
					
<a href="<c:url value="/community/ShowForum.do?forumId=1"/>" class="returnTo">&larr; <fmt:message key="community.emailMessage.returnTo"/> <span><fmt:message key="community.emailMessage.boardIndex"/></span> <fmt:message key="community.emailMessage.forum"/></a>


<!-- <div id="deletePostModal" title="Delete post" style="display:none">  -->
<!-- 	<p> -->
<!-- 		<span class="ui-icon ui-icon-alert" style="float:left; margin:0 7px 0 0;"></span> -->
<!-- 		Are you sure you want to delete this post? -->
<!-- 	</p> -->
<!-- </div> -->

<c:url var="searchUsersURL" value="/user/SearchUsers.json"/>

	<script type="text/javascript">
		tinyMCE.init({
			// General options
			mode : "textareas",
			theme : "advanced",
			skin : "o2k7",
			plugins : "autolink,lists,pagebreak,style,layer,table,save,advhr,advimage,advlink,emotions,iespell,insertdatetime,preview,media,searchreplace,print,contextmenu,paste,directionality,fullscreen,noneditable,visualchars,nonbreaking,xhtmlxtras,template,inlinepopups,autosave",
			language : "en",
			elements : "htmlbox",
		
			// Theme options
			theme_advanced_buttons1 : "bold,italic,underline,strikethrough,|,justifyleft,justifycenter,justifyright,justifyfull,|,fontsizeselect,|,cut,copy,paste,pastetext,pasteword,|,search,replace,|,bullist,numlist,|,outdent,indent,|,undo,redo,|,link,unlink,image,|,forecolor,backcolor",
// 			theme_advanced_buttons2 : "cut,copy,paste,pastetext,pasteword,|,search,replace,|,bullist,numlist,|,outdent,indent,blockquote,|,undo,redo,|,link,unlink,anchor,image,cleanup,help,code,|,insertdate,inserttime,preview,|,forecolor,backcolor",
// 			theme_advanced_buttons3 : "tablecontrols,|,hr,removeformat,visualaid,|,sub,sup,|,charmap,emotions,iespell,media,advhr,|,print,|,ltr,rtl,|,fullscreen",
// 			theme_advanced_buttons4 : "insertlayer,moveforward,movebackward,absolute,|,styleprops,|,cite,abbr,acronym,del,ins,attribs,|,visualchars,nonbreaking,template,pagebreak,restoredraft",
			theme_advanced_toolbar_location : "top",
			theme_advanced_toolbar_align : "left",
			theme_advanced_statusbar_location : "bottom",
			theme_advanced_resizing : true,
		
			// Example word content CSS (should be your site CSS) this one removes paragraph margins
//			content_css : "css/word.css",
		
			// Drop lists for link/image/media/template dialogs
			template_external_list_url : "../../../scripts/forum/template_list.js",
			external_link_list_url : "../../../scripts/forum/link_list.js",
			external_image_list_url : "../../../scripts/forum/image_list.js",
			media_external_list_url : "../../../scripts/forum/media_list.js",
			
		
			// Replace values for the template plugin
			template_replace_values : {
				username : "Some User",
				staffid : "991234"
			}
		});
	
	//MD: Fix a problem with tinyMCE alert when change page.
	window.onbeforeunload = function() {};
	
		$j(document).ready(function() {
			

// 			$j.ajax({ url: '${ShowForumChronologyURL}', cache: false, success:function(json) {
//    				$j("#chronologyDiv").html(json.chronology);
// 				$j(".arrowForum").css('visibility','visible');
// 				$j(".forum").css('visibility','visible');
//    			}});

			//This is for reset the url of the tab
// 			var tab = $j( "#tabs" ).find( ".ui-tabs-nav li:eq(2)" );
// 			var panelId = tab.attr( "aria-controls" );
// 			$j( "#" + $j("#" + panelId).attr("aria-labelledby") ).attr("href", "${ComposeMessageURL}");

			$j('.pageHref').die();
			// Result links have a specific class style on which we attach click live. 
			$j('.pageHref').live('click', function() {
				$j("#main").load($j(this).attr("href"));
				return false;
			});
			
// 			$j('.boardIndex').die();
			// Result links have a specific class style on which we attach click live. 
// 			$j('.boardIndex').live('click', function() {
// 				$j("#main").load($j(this).attr("href"));
// 				return false;
// 			});

			$j('.returnTo').click(function(){
				$j("#main").load($j(this).attr("href"));
				return false;
			});
			
			$j('.paginateForumButton').die();
			// Result links have a specific class style on which we attach click live. 
			$j('.paginateForumButton').live('click', function() {
				$j("#main").load($j(this).attr("href"));
				return false;
			});
			
			$j("#allUser").click(function(){
				if($j(this).is(":checked")){
					$j(".userRoles").attr("disabled", true);
				}else{
					$j(".userRoles").removeAttr("disabled");
				}
			});
			
			$j('#preview').click(function(){
				$j("#previewSubject").text($j("#subject").val());
				$j("#htmlboxEmail").text(tinyMCE.get('htmlboxEmail').getContent());
				//MD: In variable 'text' I control if the user has inserted no words in the textarea
				var text = $j("#htmlboxEmail").val();
				$j("#previewText").html($j(text).html());
				$j('#messagePreview').css('display','inherit');
				$j.scrollTo({top:'250px',left:'0px'}, 800 );
				return false;
	         });
			
			var $usersAutoComplete = $j('#to').autocompleteGeneral({ 
			    serviceUrl:'${searchUsersURL}',
			    loadingImageUrl:'${LoadingImageURL}',
			    minChars:3, 
			    delimiter: null, // regex or character
			    maxHeight:400,
			    width:600,
			    zIndex: 9999,
			    deferRequestBy: 0, //miliseconds
			    noCache: true, //default is false, set to true to disable caching
			    onSelect: function(value, data){
				   $j("#account").val(data);
				   $j(".userRoles").attr("disabled", true);
				   $j("#allUser").attr("disabled", true);
			    }			    
			  });
	        
	        //MD: Introduce this code for close the autocompleter on blur event
	        $j('#to').blur(function(){
	        	$usersAutoComplete.killSuggestions();
	        	return false;
	        });
			
			$j("#to").change(function(){
				if($j(this).val() == ''){
					$j(".userRoles").removeAttr("disabled");
					$j("#allUser").removeAttr("disabled");
				}
			});
			
			$j("#emailMessageForm").submit(function (){
	        	$j("#htmlboxEmail").text(tinyMCE.get('htmlboxEmail').getContent());
				//MD: In variable 'text' i control if the user has inserted no words in the textarea
				var text = $j("#htmlboxEmail").val();
	        	if($j("#subject").val() != '' && $j(text).text() != ''){
	        		$j.ajax({ type:"POST", url:"${EmailMessageURL}", data:$j(this).serialize(), async:false, success:function(html) {
	        			$j("#messageSent").css('display','inherit');
						$j("#messageSent").dialog({
							  autoOpen : false,
							  modal: true,
							  resizable: false,
							  width: 300,
							  height: 130, 
							  buttons: {
								  Ok: function() {
									  $j(this).dialog("close");
									  $j("#main").load("${MyInboxURL}");
								  }
							  }
						  });
						$j("#messageSent").dialog('open');
	        		}});
	        		return false;
	        	}else if($j("#subject").val() == '' || $j(text).text() == ''){
	        		$j("#messageNotValid").css('display','inherit');
					$j("#messageNotValid").dialog({
						  autoOpen : false,
						  modal: true,
						  resizable: false,
						  width: 300,
						  height: 130, 
						  buttons: {
							  Ok: function() {
								  $j(this).dialog("close");
							  }
						  }
					  });
					$j("#messageNotValid").dialog('open');
					return false;
	        	}
	        });
			
		});
	</script>
