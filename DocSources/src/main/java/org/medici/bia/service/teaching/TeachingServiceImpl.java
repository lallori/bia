/*
 * TeachingServiceImpl.java
 *
 * Developed by The Medici Archive Project Inc. (2010-2012)
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
 */
package org.medici.bia.service.teaching;

import java.util.Date;
import java.util.Map;
import java.util.Set;

import javax.persistence.PersistenceException;

import org.medici.bia.common.pagination.Page;
import org.medici.bia.common.pagination.PaginationFilter;
import org.medici.bia.common.util.ApplicationError;
import org.medici.bia.common.util.CourseUtils;
import org.medici.bia.dao.course.CourseDAO;
import org.medici.bia.dao.document.DocumentDAO;
import org.medici.bia.dao.forum.ForumDAO;
import org.medici.bia.dao.forumpost.ForumPostDAO;
import org.medici.bia.dao.forumtopic.ForumTopicDAO;
import org.medici.bia.dao.forumtopicwatch.ForumTopicWatchDAO;
import org.medici.bia.dao.image.ImageDAO;
import org.medici.bia.dao.user.UserDAO;
import org.medici.bia.dao.userauthority.UserAuthorityDAO;
import org.medici.bia.dao.userhistory.UserHistoryDAO;
import org.medici.bia.domain.Course;
import org.medici.bia.domain.Document;
import org.medici.bia.domain.Forum;
import org.medici.bia.domain.Forum.Type;
import org.medici.bia.domain.ForumPost;
import org.medici.bia.domain.ForumTopic;
import org.medici.bia.domain.ForumTopicWatch;
import org.medici.bia.domain.Image;
import org.medici.bia.domain.User;
import org.medici.bia.domain.UserAuthority;
import org.medici.bia.domain.UserHistory;
import org.medici.bia.domain.UserHistory.Action;
import org.medici.bia.domain.UserHistory.Category;
import org.medici.bia.exception.ApplicationThrowable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * This class is the default implementation of service responsible for every action on
 * courses (courses, course topics, messages).
 * 
 * @author Ronny Rinaldi (<a href=mailto:rinaldi.ronny@gmail.com>rinaldi.ronny@gmail.com</a>)
 *
 */
@Service
@Transactional(readOnly=true)
public class TeachingServiceImpl implements TeachingService {
	
	@Autowired
	private CourseDAO courseDAO;
	
	@Autowired
	private DocumentDAO documentDAO;
	
	@Autowired
	private ForumDAO forumDAO;
	
	@Autowired
	private ForumPostDAO forumPostDAO;
	
	@Autowired
	private ForumTopicDAO forumTopicDAO;
	
	@Autowired
	private ForumTopicWatchDAO forumTopicWatchDAO;
	
	@Autowired
	private ImageDAO imageDAO;
	
	@Autowired
	private UserAuthorityDAO userAuthorityDAO;
	
	@Autowired
	private UserHistoryDAO userHistoryDAO;
	
	@Autowired
	private UserDAO userDAO;
	
	public CourseDAO getCourseDAO() {
		return courseDAO;
	}

	public void setCourseDAO(CourseDAO courseDAO) {
		this.courseDAO = courseDAO;
	}

	public DocumentDAO getDocumentDAO() {
		return documentDAO;
	}

	public void setDocumentDAO(DocumentDAO documentDAO) {
		this.documentDAO = documentDAO;
	}

	public ForumDAO getForumDAO() {
		return forumDAO;
	}

	public void setForumDAO(ForumDAO forumDAO) {
		this.forumDAO = forumDAO;
	}

	public ForumPostDAO getForumPostDAO() {
		return forumPostDAO;
	}

	public void setForumPostDAO(ForumPostDAO forumPostDAO) {
		this.forumPostDAO = forumPostDAO;
	}

	public ForumTopicDAO getForumTopicDAO() {
		return forumTopicDAO;
	}

	public void setForumTopicDAO(ForumTopicDAO forumTopicDAO) {
		this.forumTopicDAO = forumTopicDAO;
	}

	public ForumTopicWatchDAO getForumTopicWatchDAO() {
		return forumTopicWatchDAO;
	}

	public void setForumTopicWatchDAO(ForumTopicWatchDAO forumTopicWatchDAO) {
		this.forumTopicWatchDAO = forumTopicWatchDAO;
	}

	public ImageDAO getImageDAO() {
		return imageDAO;
	}

	public void setImageDAO(ImageDAO imageDAO) {
		this.imageDAO = imageDAO;
	}

	public UserAuthorityDAO getUserAuthorityDAO() {
		return userAuthorityDAO;
	}

	public void setUserAuthorityDAO(UserAuthorityDAO userAuthorityDAO) {
		this.userAuthorityDAO = userAuthorityDAO;
	}

	public UserHistoryDAO getUserHistoryDAO() {
		return userHistoryDAO;
	}

	public void setUserHistoryDAO(UserHistoryDAO userHistoryDAO) {
		this.userHistoryDAO = userHistoryDAO;
	}

	public UserDAO getUserDAO() {
		return userDAO;
	}

	public void setUserDAO(UserDAO userDAO) {
		this.userDAO = userDAO;
	}
	
	/* Service API implementations */
	
	/**
	 * {@inheritDoc}
	 */
	@Transactional(readOnly=false, propagation=Propagation.REQUIRED)
	@Override
	public ForumPost addNewTopicPost(
			Integer courseTopicId, 
			String postSubject, 
			String postContent, 
			String volume, 
			String insert, 
			String folio, 
			String remoteAddress) throws ApplicationThrowable {
		
		ForumTopic courseTopic = null;
		
		try {
			courseTopic = getForumTopicDAO().find(courseTopicId);
			if (courseTopic == null) {
				throw new ApplicationThrowable(ApplicationError.NULLPOINTER_ERROR, "ADD NEW POST TO COURSE TOPIC --> course topic is missing");
			}
		} catch (PersistenceException e) {
			throw new ApplicationThrowable(e);
		}
		
		Date now = new Date();
		User user = getCurrentUser();
		
		try {
			ForumPost post = new ForumPost();
			post.setTopic(courseTopic);
			post.setForum(courseTopic.getForum());
			post.setSubject(postSubject);
			post.setIpAddress(remoteAddress);
			
			post.setDateCreated(now);
			post.setLastUpdate(now);
			post.setLogicalDelete(Boolean.FALSE);
			post.setUser(user);
			post.setUpdater(user);
			
			getForumPostDAO().persist(post);
			// calculate folio details comment after persist
			String folioDetailsComment = "<!--" + CourseUtils.generateFolioLocationComment(post.getPostId(), volume, insert, folio) + "-->";
			// now post text can be stored
			post.setText(folioDetailsComment + postContent);
			
			courseTopic.setLastPost(post);
			if (new Integer(0).equals(courseTopic.getTotalReplies())) {
				courseTopic.setFirstPost(post);
			}
			courseTopic.setTotalReplies(courseTopic.getTotalReplies() + 1);
			
			getForumDAO().recursiveIncreasePostsNumber(courseTopic.getForum());
			recursiveSetLastPost(courseTopic.getForum(), post, now);
			
			// Changing the user last forum dates and number of post
			user.setForumNumberOfPost(user.getForumNumberOfPost() + 1);
			user.setLastActiveForumDate(now);
			user.setLastForumPostDate(now);
			
			return post;
		} catch (PersistenceException e) {
			throw new ApplicationThrowable(e);
		}
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Transactional(readOnly=false, propagation=Propagation.REQUIRED)
	@Override
	public ForumTopic addNewCourseTopic(Integer courseId, Integer documentId, String topicTitle, String remoteAddress) throws ApplicationThrowable {
		Course course = null;
		Document document = null;
		try {
			course = getCourseDAO().find(courseId);
			if (course == null) {
				throw new ApplicationThrowable(ApplicationError.NULLPOINTER_ERROR, "ADD NEW COURSE TOPIC --> course [" + courseId + "] is missing");
			}
			document = getDocumentDAO().find(documentId);
			if (document == null) {
				throw new ApplicationThrowable(ApplicationError.NULLPOINTER_ERROR, "ADD NEW COURSE TOPIC --> document [" + documentId + "] is missing");
			}
		} catch (PersistenceException e) {
			throw new ApplicationThrowable(e);
		}
		
		if (topicTitle == null || "".equals(topicTitle.trim())) {
			throw new ApplicationThrowable(ApplicationError.MISSING_PARAMETER, "ADD NEW COURSE TOPIC --> course topic title is missing");
		}
		
		Date now = new Date();
		User user = getCurrentUser();
		
		try {
			ForumTopic courseTopic = new ForumTopic();
			courseTopic.setForum(course.getForum());
			courseTopic.setDocument(document);
			courseTopic.setSubject(topicTitle.trim());
			courseTopic.setDateCreated(now);
			courseTopic.setLastUpdate(now);
			
			courseTopic.setUser(user);
			courseTopic.setIpAddress(remoteAddress);
			courseTopic.setTotalReplies(0);
			courseTopic.setTotalViews(0);
			courseTopic.setLastPost(null);
			courseTopic.setFirstPost(null);
			courseTopic.setLogicalDelete(Boolean.FALSE);
			
			getForumTopicDAO().persist(courseTopic);
			
			//Increment the topicsNumber in course
			getForumDAO().recursiveIncreaseTopicsNumber(course.getForum());
			
			getUserHistoryDAO().persist(new UserHistory(user, "Create new course topic", Action.CREATE, Category.FORUM_TOPIC, courseTopic));
			
			if (user.getForumTopicSubscription().equals(Boolean.TRUE)) {
				ForumTopicWatch courseTopicWatch = new ForumTopicWatch(courseTopic, user);
				getForumTopicWatchDAO().persist(courseTopicWatch);
			}
			
			return courseTopic;
		} catch (PersistenceException e) {
			throw new ApplicationThrowable(e);
		}
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public Long countActiveCourses() throws ApplicationThrowable {
		try {
			return getCourseDAO().countActiveCourses();
		} catch (PersistenceException e) {
			throw new ApplicationThrowable(e);
		}
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Transactional(readOnly=false, propagation=Propagation.REQUIRED)
	@Override
	public void deleteCourseTopicPost(Integer postId) throws ApplicationThrowable {
		Date now = new Date();
		try {
			User user = getCurrentUser();
			
			ForumPost post = getForumPostDAO().find(postId);
			Forum courseForum = post.getForum();
			ForumTopic courseTopic = post.getTopic();
			post.setLogicalDelete(new Boolean(Boolean.TRUE));
			post.setLastUpdate(now);
			post.setUpdater(user);

			ForumPost lastPost = getForumPostDAO().findLastPostFromForumTopic(courseTopic);
			courseTopic.setLastPost(lastPost);
			courseTopic.setTotalReplies(courseTopic.getTotalReplies() - 1);
			
			getForumDAO().recursiveDecreasePostsNumber(courseForum);
			
			recursiveSetLastPost(courseForum, now);

			// Changing the user last forum dates and number of post
			user.setLastActiveForumDate(now);
			user.setLastForumPostDate(now);
			user.setForumNumberOfPost(user.getForumNumberOfPost() - 1);
		} catch (Throwable th) {
			throw new ApplicationThrowable(th);
		}
		
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public Course findCourse(Integer courseId) throws ApplicationThrowable {
		try {
			return getCourseDAO().find(courseId);
		} catch (PersistenceException e) {
			throw new ApplicationThrowable(e);
		}
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public ForumTopic findCourseTopic(Integer topicId) throws ApplicationThrowable {
		try {
			return getForumTopicDAO().find(topicId);
		} catch (PersistenceException e) {
			throw new ApplicationThrowable(e);
		}
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public Image getDocumentImage(Integer entryId, Integer imageOrder) throws ApplicationThrowable {
		try {
			Document document = getDocumentDAO().find(entryId);
			return getImageDAO().findVolumeImage(document.getVolume().getVolNum(), document.getVolume().getVolLetExt(), imageOrder);
		} catch (Throwable th) {
			throw new ApplicationThrowable(th);
		}
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public ForumTopic getCourseTopic(Integer courseTopicId) throws ApplicationThrowable {
		try {
			return getForumTopicDAO().find(courseTopicId);
		} catch(Throwable th) {
			throw new ApplicationThrowable(th);
		}
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public ForumTopic getCourseTopicForView(Integer courseTopicId) throws ApplicationThrowable {
		try {
			ForumTopic topic = getForumTopicDAO().find(courseTopicId);
			if (topic.getTotalViews() == null) {
				topic.setTotalViews(1);
			} else {
				topic.setTotalViews(topic.getTotalViews() + 1);
			}
			getForumTopicDAO().merge(topic);
			return topic;
		} catch(Throwable th) {
			throw new ApplicationThrowable(th);
		}
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public User getCurrentUser() throws ApplicationThrowable {
		try {
			return getUserDAO().findUser(((UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername());
		} catch (Throwable th) {
			throw new ApplicationThrowable(th);
		}
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public Course getLastActiveCourse() throws ApplicationThrowable {
		try {
			return getCourseDAO().getLastActiveCourse();
		} catch (PersistenceException e) {
			throw new ApplicationThrowable(e);
		}
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public Page getPostsFromCourseTopic(ForumTopic courseTopic, PaginationFilter filter) throws ApplicationThrowable {
		try {
			return getForumPostDAO().findPostsFromTopic(courseTopic, filter);
		} catch (Throwable th) {
			throw new ApplicationThrowable(th);
		}
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public ForumPost getRoundRobinPost(Integer postId) throws ApplicationThrowable {
		try {
			return getForumPostDAO().find(postId);
		} catch (Throwable th) {
			throw new ApplicationThrowable(th);
		}
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public Map<String,UserAuthority> getUsersRoundRobinAuthority(Set<String> accountIds) throws ApplicationThrowable {
		try {
			return getUserAuthorityDAO().getUsersRoundRobinAuthority(accountIds);
		} catch (Throwable th) {
			throw new ApplicationThrowable(th);
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Transactional(readOnly=false, propagation=Propagation.REQUIRED)
	@Override
	public ForumPost updateTopicPost(
			Integer postId,
			String postSubject,
			String postContent,
			String volume,
			String insert,
			String folio) throws ApplicationThrowable {
		
		try {
			ForumPost post = getForumPostDAO().find(postId);
			
			if (post == null) {
				throw new ApplicationThrowable(ApplicationError.RECORD_NOT_FOUND_ERROR, "Corse Topic Post [" + postId + "] not found!");
			}
			
			Date now = new Date();
			User user = getCurrentUser();
			
			post.setSubject(postSubject);
			String oldLocation = CourseUtils.getPostFolioLocationComment(post);
			String newLocation = CourseUtils.generateFolioLocationComment(postId, volume, insert, folio);
			if (!newLocation.equals(oldLocation)) {
				if (oldLocation != null) {
					postContent = postContent.replace(oldLocation, newLocation);
				} else {
					postContent = "<!--" + newLocation + "-->" + postContent;
				}
			}
			post.setText(postContent);
			post.setLastUpdate(now);
			post.setUpdater(user);
			
			// Changing the user last forum
			user.setLastActiveForumDate(now);
			user.setLastForumPostDate(now);

			getUserHistoryDAO().persist(new UserHistory(user, "Edit course topic post", Action.MODIFY, Category.FORUM_POST, post));
			
			return post;
		} catch (Throwable th) {
			throw new ApplicationThrowable(th);
		}
	}
	

	/* Privates */
	
	private void recursiveSetLastPost(Forum course, Date now) throws ApplicationThrowable {
		if(course.getType().equals(Type.CATEGORY)){
			return;
		}

		ForumPost lastPost = getForumPostDAO().findLastPostFromForum(course);
		course.setLastPost(lastPost);
		//last update must be updated to obtain a correct indexing of forum
		course.setLastUpdate(now);
		getForumDAO().merge(course);

		recursiveSetLastPost(course.getForumParent(), lastPost, now);
	}
	
	private void recursiveSetLastPost(Forum course, ForumPost post, Date now) throws ApplicationThrowable {
		if(course.getType().equals(Type.CATEGORY)){
			return;
		}
		
		course.setLastPost(post);
		//last update must be updated to obtain a correct indexing of forum
		course.setLastUpdate(now);
		getForumDAO().merge(course);
		
		recursiveSetLastPost(course.getForumParent(), post, now);
	}
	
}
