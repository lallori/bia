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
	
	<h2>Advanced Search</h2>
	<form:form id="advancedSearchForm" method="post" action="${AdvancedSearchForumURL}">
		<div id="searchQueryDiv">
			<h1>SEARCH QUERY</h1>
			<p><b>Search for keywords:</b><br />
			<!-- Place + in front of a word which must be found and - in front of a word which must not be found. Put a list of words separated by | into brackets if only one of the words must be found. Use * as a wildcard for partial matches.</p> -->
    	
    		<form:input id="text" name="text" class="input_50c" type="text" value="" path="text"/>
    		<form:label for="allTerms" id="allTermsLabel" path="allTerms"><form:radiobutton name="allTerms" id="allTerms" value="true" path="allTerms" />Search for all terms or use query as entered</form:label>
        	<form:label for="anyTerms" id="anyTermsLabel" path="allTerms"><form:radiobutton name="allTerms" id="anyTerms" value="false" path="allTerms" />Search for any terms</form:label>
        	<p><b>Search for author:</b><br />
			<!-- Use * as a wildcard for partial matches.</p> -->
    		<form:input id="textAuthor" name="textAuthor" class="input_50c" type="text" path="textAuthor"/>
		
		
		
			<h1>SEARCH OPTIONS</h1>
			<p><b>Search in forums:</b><br />
   			Select the forum or forums you wish to search in.</p>
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
		            	<label for="searchWithin" id="searchWithinLabel">Search within:</label>
		            </div>
		            <div class="value">
		                <ul>
		                    <li><form:label for="searchWithin" path="wordsType"><form:radiobutton name="wordsType" path="wordsType" id="sw_1" value="SUBJECT_TEXT" /> User post subjects and text</form:label></li>
		                    <li><form:label for="searchWithin" path="wordsType"><form:radiobutton name="wordsType" path="wordsType" id="swMessage" value="TEXT" /> User posts only</form:label></li>
		                    <li><form:label for="searchWithin" path="wordsType"><form:radiobutton name="wordsType" path="wordsType" id="swTopicTitles" value="TITLE" /> Discussion titles only</form:label></li>
		                    <li><form:label for="searchWithin" path="wordsType"><form:radiobutton name="wordsType" path="wordsType" id="swFirstPost" value="FIRST_POST" /> First post of topics only</form:label></li>
		                </ul>
		            </div>
		        </div>
		        
		        <div class="row">
		        	<div class="item">
		                <label for="displayResults" id="displayResultsLabel">Display results as:</label>
		             </div>
		             <div class="value">
		                <form:label for="drPosts" path="displayResults"><form:radiobutton name="displayResults" id="drPosts" value="Posts" path="displayResults" /> Posts</form:label> 
		                <form:label for="drTopics" path="displayResults"><form:radiobutton name="displayResults" id="drTopics" value="Topics" path="displayResults" /> Discussions</form:label> 
		             </div>
				</div>
		        
		        <div class="row">
		        	<div class="item">
		                <form:label for="sortResults" id="sortResultsLabel" path="sortResults">Sort results by:</form:label>	
		             </div>
		             <div class="value">
		                <form:select name="sortResults" id="sortResults" path="sortResults">
		                    <form:option value="AUTHOR">Author</form:option>
		                    <form:option value="POST_TIME">Post time</form:option>
		                    <form:option value="FORUM">Forum</form:option>
		                    <form:option value="TOPIC_TITLE">Discussion title</form:option>
		                    <form:option value="POST_SUBJECT">Post subject</form:option>
		                </form:select>
		                <form:label for="ascending" path="order"><form:radiobutton name="order" id="ascending" value="asc" path="order" /> Ascending</form:label> 
		                <form:label for="descending" path="order"><form:radiobutton name="order" id="descending" value="desc" path="order" /> Descending</form:label> 
		             </div>
		        </div>
		        
		        <div class="row">
		        	<div class="item">
		                <form:label for="limitResults" id="limitResultsLabel" path="limitResults">Limit results to previous:</form:label>	
		             </div>
		             <div class="value">
		                <form:select name="limitResults" id="limitResults" path="limitResults">
		                    <form:option value="0">All results</form:option>
		                    <form:option value="1">1 day</form:option>
		                    <form:option value="7">7 days</form:option>
		                    <form:option value="14">2 weeks</form:option>
		                    <form:option value="30">1 month</form:option>
		                    <form:option value="90">3 months</form:option>
		                    <form:option value="180">6 months</form:option>
		                    <form:option value="365">1 year</form:option>
		                 </form:select>
		             </div>
		        </div>
		        
		        <div class="row">
		        	<div class="item">
		                <form:label for="returnFirst" id="returnFirstLabel" path="returnFirst">Return first:</form:label>	
		             </div>
		             <div class="value">
		                <form:select name="returnFirst" title="Return first" path="returnFirst">
		                    <form:option value="-1">All available</form:option>
		                    <form:option value="0">0</form:option>
		                    <form:option value="25">25</form:option>
		                    <form:option value="50">50</form:option>
		                    <form:option value="100">100</form:option>
		                    <form:option value="200">200</form:option>
		                    <form:option value="300">300</form:option>
		                    <form:option value="400">400</form:option>
		                    <form:option value="500">500</form:option>
		                    <form:option value="600">600</form:option>
		                    <form:option value="700">700</form:option>
		                    <form:option value="800">800</form:option>
		                    <form:option value="900">900</form:option>
		                    <form:option value="1000">1000</form:option>
		                </form:select>
		                characters of posts
		             </div>
		        </div>
		     </div>
			 <div id="buttons">
				<input type="submit" value="Search" class="buttonSmall" id="submitSearch"> 
				<a href="${ShowAdvancedSearchForumPostURL}" id="cancel" class="buttonSmall">Reset</a> 
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
