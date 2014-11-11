<%@ taglib prefix="bia" uri="http://bia.medici.org/jsp:jstl" %>  
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>

	<c:url var="ReplyForumPostURL" value="/community/ReplyForumPost.do">
		<c:param name="postId" value="0"/>
		<c:param name="forumId" value="${topic.forum.forumId}"/>
		<c:param name="topicId" value="${topic.topicId}"/>
	</c:url>
	
	<c:url var="ShowAdvancedSearchForumPostURL" value="/community/ShowAdvancedSearchForumPost.do"/>
	<c:url var="AdvancedSearchForumURL" value="/community/AdvancedSearchForumPost.do"/>
	
	<h2><fmt:message key="community.showAdvancedSearchForumPost.advancedSearch"/></h2>
	<form:form id="advancedSearchForm" method="post" action="${AdvancedSearchForumURL}">
		<div id="searchQueryDiv">
			<h1><fmt:message key="community.showAdvancedSearchForumPost.sEarchQuery"/></h1>
			<p><b><fmt:message key="community.showAdvancedSearchForumPost.searchForKeyboards"/></b><br />
			<!-- Place + in front of a word which must be found and - in front of a word which must not be found. Put a list of words separated by | into brackets if only one of the words must be found. Use * as a wildcard for partial matches.</p> -->
    	
    		<form:input id="text" name="text" class="input_50c" type="text" value="" path="text"/>
    		<form:label for="allTerms" id="allTermsLabel" path="allTerms"><form:radiobutton name="allTerms" id="allTerms" value="true" path="allTerms" /><fmt:message key="community.showAdvancedSearchForumPost.searchForAllTerms"/></form:label>
        	<form:label for="anyTerms" id="anyTermsLabel" path="allTerms"><form:radiobutton name="allTerms" id="anyTerms" value="false" path="allTerms" /><fmt:message key="community.showAdvancedSearchForumPost.searchForAnyTerms"/></form:label>
        	<p><b><fmt:message key="community.showAdvancedSearchForumPost.searchForAuthor"/></b><br />
			<!-- Use * as a wildcard for partial matches.</p> -->
    		<form:input id="textAuthor" name="textAuthor" class="input_50c" type="text" path="textAuthor"/>
		
		
		
			<h1><fmt:message key="community.showAdvancedSearchForumPost.sEarchOptions"/></h1>
			<p><b><fmt:message key="community.showAdvancedSearchForumPost.searchInForums"/></b><br />
   			<fmt:message key="community.showAdvancedSearchForumPost.selectTheForum"/></p>
   			<div>
   				<select name="forumsId" id="searchForum" multiple="multiple" size="8" title="Search in forums">
   				<c:forEach items="${subCategories}" var="currentCategory" varStatus="status">
   					<c:set var="forums" value="${subForums[currentCategory.forumId]}"/>
   					<c:forEach items="${forums}" var="currentForum">
						<option value="${currentForum.forumId}">${currentForum.title}</option>
					</c:forEach>
				</c:forEach>
   				</select>
   			</div>
   			<div class="listForm"> 
		        <div class="row">
		            <div class="item">
		            	<label for="searchWithin" id="searchWithinLabel"><fmt:message key="community.showAdvancedSearchForumPost.searchWithin"/></label>
		            </div>
		            <div class="value">
		                <ul>
		                    <li><form:label for="searchWithin" path="wordsType"><form:radiobutton name="wordsType" path="wordsType" id="sw_1" value="SUBJECT_TEXT" /> <fmt:message key="community.showAdvancedSearchForumPost.useerPostSubjects"/></form:label></li>
		                    <li><form:label for="searchWithin" path="wordsType"><form:radiobutton name="wordsType" path="wordsType" id="swMessage" value="TEXT" /> <fmt:message key="community.showAdvancedSearchForumPost.userPostsOnly"/></form:label></li>
		                    <li><form:label for="searchWithin" path="wordsType"><form:radiobutton name="wordsType" path="wordsType" id="swTopicTitles" value="TITLE" /> <fmt:message key="community.showAdvancedSearchForumPost.discussionTitlesOnly"/></form:label></li>
		                    <li><form:label for="searchWithin" path="wordsType"><form:radiobutton name="wordsType" path="wordsType" id="swFirstPost" value="FIRST_POST" /> <fmt:message key="community.showAdvancedSearchForumPost.firstPostOfTopics"/></form:label></li>
		                </ul>
		            </div>
		        </div>
		        
		        <div class="row">
		        	<div class="item">
		                <label for="displayResults" id="displayResultsLabel"><fmt:message key="community.showAdvancedSearchForumPost.displaysResultsAs"/></label>
		             </div>
		             <div class="value">
		                <form:label for="drPosts" path="displayResults"><form:radiobutton name="displayResults" id="drPosts" value="Posts" path="displayResults" /> <fmt:message key="community.showAdvancedSearchForumPost.posts"/></form:label> 
		                <form:label for="drTopics" path="displayResults"><form:radiobutton name="displayResults" id="drTopics" value="Topics" path="displayResults" /> <fmt:message key="community.showAdvancedSearchForumPost.discussions"/></form:label> 
		             </div>
				</div>
		        
		        <div class="row">
		        	<div class="item">
		                <form:label for="sortResults" id="sortResultsLabel" path="sortResults"><fmt:message key="community.showAdvancedSearchForumPost.sortResultsBy"/></form:label>	
		             </div>
		             <div class="value">
		                <form:select name="sortResults" id="sortResults" path="sortResults">
		                    <form:option value="AUTHOR"><fmt:message key="community.showAdvancedSearchForumPost.author"/></form:option>
		                    <form:option value="POST_TIME"><fmt:message key="community.showAdvancedSearchForumPost.postTime"/></form:option>
		                    <form:option value="FORUM"><fmt:message key="community.showAdvancedSearchForumPost.forum"/></form:option>
		                    <form:option value="TOPIC_TITLE"><fmt:message key="community.showAdvancedSearchForumPost.discussionTitle"/></form:option>
		                    <form:option value="POST_SUBJECT"><fmt:message key="community.showAdvancedSearchForumPost.postSubject"/></form:option>
		                </form:select>
		                <form:label for="ascending" path="order"><form:radiobutton name="order" id="ascending" value="asc" path="order" /> <fmt:message key="community.showAdvancedSearchForumPost.ascending"/></form:label> 
		                <form:label for="descending" path="order"><form:radiobutton name="order" id="descending" value="desc" path="order" /> <fmt:message key="community.showAdvancedSearchForumPost.descending"/></form:label> 
		             </div>
		        </div>
		        
		        <div class="row">
		        	<div class="item">
		                <form:label for="limitResults" id="limitResultsLabel" path="limitResults"><fmt:message key="community.showAdvancedSearchForumPost.limitResults"/></form:label>	
		             </div>
		             <div class="value">
		                <form:select name="limitResults" id="limitResults" path="limitResults">
		                    <form:option value="0"><fmt:message key="community.showAdvancedSearchForumPost.allResults"/></form:option>
		                    <form:option value="1"><fmt:message key="community.showAdvancedSearchForumPost.oneDay"/></form:option>
		                    <form:option value="7"><fmt:message key="community.showAdvancedSearchForumPost.sevenDay"/></form:option>
		                    <form:option value="14"><fmt:message key="community.showAdvancedSearchForumPost.twoWeeks"/></form:option>
		                    <form:option value="30"><fmt:message key="community.showAdvancedSearchForumPost.oneMonth"/></form:option>
		                    <form:option value="90"><fmt:message key="community.showAdvancedSearchForumPost.threeMonths"/></form:option>
		                    <form:option value="180"><fmt:message key="community.showAdvancedSearchForumPost.sixMonths"/></form:option>
		                    <form:option value="365"><fmt:message key="community.showAdvancedSearchForumPost.oneYear"/></form:option>
		                 </form:select>
		             </div>
		        </div>
		        
		        <div class="row">
		        	<div class="item">
		                <form:label for="returnFirst" id="returnFirstLabel" path="returnFirst"><fmt:message key="community.showAdvancedSearchForumPost.returnFirst"/></form:label>	
		             </div>
		             <div class="value">
		                <form:select name="returnFirst" title="Return first" path="returnFirst">
		                    <form:option value="-1"><fmt:message key="community.showAdvancedSearchForumPost.allAvailable"/></form:option>
		                    <form:option value="0"><fmt:message key="community.showAdvancedSearchForumPost.zero"/></form:option>
		                    <form:option value="25"><fmt:message key="community.showAdvancedSearchForumPost.twof"/></form:option>
		                    <form:option value="50"><fmt:message key="community.showAdvancedSearchForumPost.fif"/></form:option>
		                    <form:option value="100"><fmt:message key="community.showAdvancedSearchForumPost.hund"/></form:option>
		                    <form:option value="200"><fmt:message key="community.showAdvancedSearchForumPost.twohund"/></form:option>
		                    <form:option value="300"><fmt:message key="community.showAdvancedSearchForumPost.threehund"/></form:option>
		                    <form:option value="400"><fmt:message key="community.showAdvancedSearchForumPost.fourhund"/></form:option>
		                    <form:option value="500"><fmt:message key="community.showAdvancedSearchForumPost.fivehund"/></form:option>
		                    <form:option value="600"><fmt:message key="community.showAdvancedSearchForumPost.sixhund"/></form:option>
		                    <form:option value="700"><fmt:message key="community.showAdvancedSearchForumPost.sevenhund"/></form:option>
		                    <form:option value="800"><fmt:message key="community.showAdvancedSearchForumPost.eighthund"/></form:option>
		                    <form:option value="900"><fmt:message key="community.showAdvancedSearchForumPost.ninehund"/></form:option>
		                    <form:option value="1000"><fmt:message key="community.showAdvancedSearchForumPost.thous"/></form:option>
		                </form:select>
		                <fmt:message key="community.showAdvancedSearchForumPost.charactersOfPosts"/>
		             </div>
		        </div>
		     </div>
			 <div id="buttons">
				<input type="submit" value="Search" class="buttonSmall button_small" id="submitSearch"> 
				<a href="${ShowAdvancedSearchForumPostURL}" id="cancel" class="buttonSmall button_small"><fmt:message key="community.showAdvancedSearchForumPost.reset"/></a> 
			</div>
		
			<div>
			   	<input type="hidden" name="searchUUID" value="${command.searchUUID}">
				<input type="hidden" name="newSearch" value="${command.newSearch}">
    		</div>
    	</div>
	</form:form>



	<script type="text/javascript">
		$j(document).ready(function() {
			$j.ajax({ url: '${ShowForumChronologyURL}', cache: false, success:function(json) {
   				$j("#chronologyDiv").html(json.chronology);
				$j(".arrowForum").css('visibility','visible');
				$j(".forum").css('visibility','visible');
   			}});

			$j('.pageHref').die();
			// Result links have a specific class style on which we attach click live. 
			$j('.pageHref').live('click', function() {
				$j("#mainContent").load($j(this).attr("href"));
				return false;
			});
			
// 			$j('.boardIndex').die();
			// Result links have a specific class style on which we attach click live. 
// 			$j('.boardIndex').live('click', function() {
// 				$j("#main").load($j(this).attr("href"));
// 				return false;
// 			});

			$j('.quotePost').die();
			$j('.quotePost').click(function (){
				$j("#tabs").tabs("load", $j("#tabs").tabs("option", "selected"));
				return false;
			});

			$j('#postReply').click(function (){
				$j("#main").load($j(this).attr("href"));
				return false;
			});
			
			$j('.returnTo').click(function(){
				$j("#main").load($j(this).attr("href"));
				return false;
			});
			
			$j('#advancedSearchForm').submit(function(){
				var formSubmitURL = $j(this).attr("action") + '?' + $j(this).serialize();
				var formPreviousURL = "${ShowAdvancedSearchForumPostURL}" + '?' + $j(this).serialize();
				$j("#prevUrl").val(formPreviousURL);
				$j("#main").load(formSubmitURL);
// 				$j.post(formSubmitURL, function(html) {
// 					$j("#main").html(html);
//    				});
				return false;
			});
			
			$j('#cancel').click(function (){
				$j("#main").load($j(this).attr("href"));
				return false;
			});

		});
	</script>
