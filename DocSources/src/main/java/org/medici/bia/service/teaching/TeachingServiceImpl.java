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

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.persistence.PersistenceException;

import org.medici.bia.common.pagination.Page;
import org.medici.bia.common.pagination.PaginationFilter;
import org.medici.bia.common.util.ApplicationError;
import org.medici.bia.common.util.ForumUtils;
import org.medici.bia.common.util.StringUtils;
import org.medici.bia.dao.annotation.AnnotationDAO;
import org.medici.bia.dao.course.CourseDAO;
import org.medici.bia.dao.coursecheckpoint.CourseCheckPointDAO;
import org.medici.bia.dao.coursepostext.CoursePostExtDAO;
import org.medici.bia.dao.coursetopicoption.CourseTopicOptionDAO;
import org.medici.bia.dao.document.DocumentDAO;
import org.medici.bia.dao.forum.ForumDAO;
import org.medici.bia.dao.forumoption.ForumOptionDAO;
import org.medici.bia.dao.forumpost.ForumPostDAO;
import org.medici.bia.dao.forumpostnotified.ForumPostNotifiedDAO;
import org.medici.bia.dao.forumtopic.ForumTopicDAO;
import org.medici.bia.dao.forumtopicwatch.ForumTopicWatchDAO;
import org.medici.bia.dao.image.ImageDAO;
import org.medici.bia.dao.user.UserDAO;
import org.medici.bia.dao.userauthority.UserAuthorityDAO;
import org.medici.bia.dao.userhistory.UserHistoryDAO;
import org.medici.bia.dao.userpersonalnotes.UserPersonalNotesDAO;
import org.medici.bia.dao.userrole.UserRoleDAO;
import org.medici.bia.domain.Annotation;
import org.medici.bia.domain.Course;
import org.medici.bia.domain.CourseCheckPoint;
import org.medici.bia.domain.CoursePostExt;
import org.medici.bia.domain.CourseTopicOption;
import org.medici.bia.domain.CourseTopicOption.CourseTopicMode;
import org.medici.bia.domain.Document;
import org.medici.bia.domain.Forum;
import org.medici.bia.domain.Forum.Type;
import org.medici.bia.domain.ForumOption;
import org.medici.bia.domain.ForumPost;
import org.medici.bia.domain.ForumPostNotified;
import org.medici.bia.domain.ForumTopic;
import org.medici.bia.domain.ForumTopicWatch;
import org.medici.bia.domain.Image;
import org.medici.bia.domain.User;
import org.medici.bia.domain.UserAuthority;
import org.medici.bia.domain.UserAuthority.Authority;
import org.medici.bia.domain.UserHistory;
import org.medici.bia.domain.UserHistory.Action;
import org.medici.bia.domain.UserHistory.Category;
import org.medici.bia.domain.UserPersonalNotes;
import org.medici.bia.domain.UserRole;
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
@Transactional(readOnly=true, rollbackFor=ApplicationThrowable.class)
public class TeachingServiceImpl implements TeachingService {
	
	@Autowired
	private AnnotationDAO annotationDAO;
	@Autowired
	private CourseCheckPointDAO courseCheckPointDAO;
	@Autowired
	private CourseDAO courseDAO;
	@Autowired
	private CoursePostExtDAO coursePostExtDAO;
	@Autowired
	private CourseTopicOptionDAO courseTopicOptionDAO;
	@Autowired
	private DocumentDAO documentDAO;
	@Autowired
	private ForumDAO forumDAO;
	@Autowired
	private ForumOptionDAO forumOptionDAO;
	@Autowired
	private ForumPostDAO forumPostDAO;
	@Autowired
	private ForumPostNotifiedDAO forumPostNotifiedDAO;
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
	@Autowired
	private UserPersonalNotesDAO userPersonalNotesDAO;
	@Autowired
	private UserRoleDAO userRoleDAO;
	
	public AnnotationDAO getAnnotationDAO() {
		return annotationDAO;
	}

	public void setAnnotationDAO(AnnotationDAO annotationDAO) {
		this.annotationDAO = annotationDAO;
	}

	public CourseCheckPointDAO getCourseCheckPointDAO() {
		return courseCheckPointDAO;
	}

	public void setCourseCheckPointDAO(CourseCheckPointDAO courseCheckPointDAO) {
		this.courseCheckPointDAO = courseCheckPointDAO;
	}

	public CourseDAO getCourseDAO() {
		return courseDAO;
	}

	public void setCourseDAO(CourseDAO courseDAO) {
		this.courseDAO = courseDAO;
	}

	public CoursePostExtDAO getCoursePostExtDAO() {
		return coursePostExtDAO;
	}

	public void setCoursePostExtDAO(CoursePostExtDAO coursePostExtDAO) {
		this.coursePostExtDAO = coursePostExtDAO;
	}

	public CourseTopicOptionDAO getCourseTopicOptionDAO() {
		return courseTopicOptionDAO;
	}

	public void setCourseTopicOptionDAO(CourseTopicOptionDAO courseTopicOptionDAO) {
		this.courseTopicOptionDAO = courseTopicOptionDAO;
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

	public ForumOptionDAO getForumOptionDAO() {
		return forumOptionDAO;
	}

	public void setForumOptionDAO(ForumOptionDAO forumOptionDAO) {
		this.forumOptionDAO = forumOptionDAO;
	}

	public ForumPostDAO getForumPostDAO() {
		return forumPostDAO;
	}

	public void setForumPostDAO(ForumPostDAO forumPostDAO) {
		this.forumPostDAO = forumPostDAO;
	}

	public ForumPostNotifiedDAO getForumPostNotifiedDAO() {
		return forumPostNotifiedDAO;
	}

	public void setForumPostNotifiedDAO(ForumPostNotifiedDAO forumPostNotifiedDAO) {
		this.forumPostNotifiedDAO = forumPostNotifiedDAO;
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
	
	/**
	 * @return the userPersonalNotesDAO
	 */
	public UserPersonalNotesDAO getUserPersonalNotesDAO() {
		return userPersonalNotesDAO;
	}

	/**
	 * @param userPersonalNotesDAO the userPersonalNotesDAO to set
	 */
	public void setUserPersonalNotesDAO(UserPersonalNotesDAO userPersonalNotesDAO) {
		this.userPersonalNotesDAO = userPersonalNotesDAO;
	}

	public UserRoleDAO getUserRoleDAO() {
		return userRoleDAO;
	}
	
	public void setUserRoleDAO(UserRoleDAO userRoleDAO) {
		this.userRoleDAO = userRoleDAO;
	}
	
	/* Service API implementations */

	/**
	 * {@inheritDoc}
	 */
	@Transactional(readOnly=false, propagation=Propagation.REQUIRED)
	@Override
	public CourseCheckPoint addCourseCheckPoint(Integer topicId, CoursePostExt extendedPost, Date date) throws ApplicationThrowable {
		CourseTopicOption option = getCourseTopicOptionDAO().getOption(topicId);
		if (option == null) {
			throw new ApplicationThrowable(ApplicationError.RECORD_NOT_FOUND_ERROR, "Cannot retrieve course option for topic [" + topicId +"]....add course check point ABORTED!!!");
		}
		CourseCheckPoint checkPoint = new CourseCheckPoint(option, extendedPost);
		checkPoint.setCheckPointTime(date != null ? date : new Date());
		
		getCourseCheckPointDAO().persist(checkPoint);
		
		return checkPoint;
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Transactional(readOnly=false, propagation=Propagation.REQUIRED)
	@Override
	public ForumTopic addCourseTopic(Integer courseId, Integer documentId, String topicTitle, CourseTopicMode mode, String remoteAddress) throws ApplicationThrowable {
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
		
		if (StringUtils.isNullableString(topicTitle)) {
			throw new ApplicationThrowable(ApplicationError.MISSING_PARAMETER, "ADD NEW COURSE TOPIC --> course topic title is missing");
		}
		
		if (mode == null) {
			throw new ApplicationThrowable(ApplicationError.MISSING_PARAMETER, "ADD NEW COURSE TOPIC --> course topic mode is missing");
		}
		
		Date now = new Date();
		User user = getCurrentUser();
		
		try {
			// Forum container creation
			Forum container = new Forum();
			SimpleDateFormat date = new SimpleDateFormat("MM/dd/yyyy");
			String nowdate = date.format(new Date()); 
			container.setTitle(topicTitle.trim() + " resources");
			// container.setDescription("Resources of " + topicTitle.trim());
			container.setDescription("Created " + nowdate);
			// the forum container must not have a document, it is only a topic container
			// container.setDocument(document);
			container.setForumParent(course.getForum());
			container.setFullPath(course.getForum().getFullPath()); // To do not violate the table constraint
			container.setHierarchyLevel(course.getForum().getHierarchyLevel() + 1);
			container.setDispositionOrder(0);
			container.setStatus(Forum.Status.ONLINE);
			container.setType(Forum.Type.FORUM);
			container.setSubType(Forum.SubType.COURSE);
			container.setDateCreated(now);
			container.setLastUpdate(now);
			container.setPostsNumber(0);
			container.setTopicsNumber(1); // it will contain the collaborative transcription topic
			container.setSubForumsNumber(0);
			container.setLogicalDelete(Boolean.FALSE);

			getForumDAO().persist(container);
			getUserHistoryDAO().persist(new UserHistory(user, "Create new forum", Action.CREATE, Category.FORUM, container));

			container.setFullPath(container.getFullPath() + container.getForumId() + ".");
			// increase the course subforums number
			increaseSubForumsNumber(course.getForum());
			
			ForumOption forumOption = ForumUtils.getForumOptionForForumTopicContainer(container);
			getForumOptionDAO().persist(forumOption);
			
			// Course Topic creation
			ForumTopic courseTopic = new ForumTopic();
			courseTopic.setForum(container);
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
			courseTopic.setLocked(Boolean.FALSE);
			
			getForumTopicDAO().persist(courseTopic);
			
			// Course Topic Option creation
			CourseTopicOption courseTopicOption = new CourseTopicOption();
			courseTopicOption.setCourseTopic(courseTopic);
			courseTopicOption.setMode(mode);
			
			getCourseTopicOptionDAO().persist(courseTopicOption);
			
			// increase the number of topics of the course
			increaseForumTopicsNumber(course.getForum());
			
			getUserHistoryDAO().persist(new UserHistory(user, "Create new course topic", Action.CREATE, Category.FORUM_TOPIC, courseTopic));
			
			// topic subscription
			for(UserRole personRole : getCoursePeople(course)) {
				if (personRole.getUser().getForumTopicSubscription().equals(Boolean.TRUE)) {
					ForumTopicWatch personTopicWatch = new ForumTopicWatch(courseTopic, personRole.getUser());
					getForumTopicWatchDAO().persist(personTopicWatch);
				}
			}
			
			return courseTopic;
		} catch (PersistenceException e) {
			throw new ApplicationThrowable(e);
		}
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Transactional(readOnly=false, propagation=Propagation.REQUIRED)
	@Override
	public CoursePostExt addCourseTranscriptionPost(
			Integer courseTopicId, 
			String postSubject, 
			String postContent,
			String transcription,
			Integer volNum, 
			String volLetExt, 
			String insertNum,
			String insertLet, 
			Integer folioNum, 
			String folioMod,
			String folioRV, 
			String remoteAddr,
			CourseTopicMode mode,
			Integer relatedCheckPointPostId) throws ApplicationThrowable {
		
		CourseTopicOption option = checkCourseTopicConsistency(courseTopicId, mode);
		
		User user = getCurrentUser();
		Date now = new Date();
		
		try {
			CoursePostExt extendedPost = doAddCourseTranscriptionPost(
					option.getCourseTopic(), 
					postSubject, 
					postContent, 
					transcription, 
					volNum, 
					volLetExt, 
					insertNum, 
					insertLet, 
					folioNum, 
					folioMod, 
					folioRV, 
					remoteAddr, 
					now, 
					user);
			
			switch (mode) {
				case I:
					CourseCheckPoint checkPoint = getCourseCheckPointDAO().getLastCheckPointByTopicId(courseTopicId);
					if (checkPoint == null) {
						checkPoint = new CourseCheckPoint(option, extendedPost);
						checkPoint.setCheckPointTime(now);
						getCourseCheckPointDAO().persist(checkPoint);
					} else {
						checkPoint.setCheckPointPost(extendedPost);
						checkPoint.setCheckPointTime(now);
					}
					break;
				case R:
					// nothing to do
					break;
				case C:
					if (relatedCheckPointPostId == null || relatedCheckPointPostId <= 0) {
						CourseCheckPoint newCheckPoint = new CourseCheckPoint(option, extendedPost);
						newCheckPoint.setCheckPointTime(now);
						
						getCourseCheckPointDAO().persist(newCheckPoint);
						extendedPost.setRelatedCheckPoint(newCheckPoint);
					} else {
						CourseCheckPoint relatedCheckPoint = getCourseCheckPointDAO().getCheckPointByPost(relatedCheckPointPostId);
						extendedPost.setRelatedCheckPoint(relatedCheckPoint);
					}
					break;
			}
			
			return extendedPost;
		} catch (Throwable th) {
			throw new ApplicationThrowable(th);
		} 
		
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Transactional(readOnly=false, propagation=Propagation.REQUIRED)
	@Override
	public ForumPost addNewPost(ForumPost forumPost) throws ApplicationThrowable {
		Date operationDate = new Date();
		try {
			User user = getCurrentUser();
			
			forumPost.setPostId(null);
			Forum forum = getForumDAO().find(forumPost.getForum().getForumId());

			if (forumPost.getTopic().getTopicId() == 0) {
				// the help resources course forum is not a course fragment but in this case
				// it is consider as a course fragment
				Course course = getCourseDAO().getCourseByCourseFragment(forumPost.getForum().getForumId());
				
				//Create the first post of a new topic
				ForumTopic forumTopic = new ForumTopic(null);
				forumTopic.setForum(forum);
				forumTopic.setDateCreated(operationDate);
				forumTopic.setLastUpdate(operationDate);
				forumTopic.setIpAddress(forumPost.getIpAddress());
				
				forumTopic.setUser(user);
				forumTopic.setSubject(forumPost.getSubject());
				forumTopic.setTotalReplies(0);
				forumTopic.setTotalViews(0);
				forumTopic.setLastPost(null);
				forumTopic.setFirstPost(null);
				forumTopic.setLogicalDelete(Boolean.FALSE);
				forumTopic.setLocked(Boolean.FALSE);
				
				getForumTopicDAO().persist(forumTopic);
				
				CourseTopicOption transcriptionOption = getCourseTopicOptionDAO().getCourseTranscriptionOptionFromForum(forum.getForumId());
				if (transcriptionOption != null) {
					forumTopic.setDocument(transcriptionOption.getCourseTopic().getDocument());
				}
				
				CourseTopicOption topicOption = new CourseTopicOption();
				topicOption.setCourseTopic(forumTopic);
				topicOption.setMode(CourseTopicMode.D);
				
				getCourseTopicOptionDAO().persist(topicOption);
				
				forumPost.setTopic(forumTopic);
				
				// increase the number of topics of the course
				increaseForumTopicsNumber(forum);
				
				// topic subscription -> to all course people
				for(UserRole personRole : getCoursePeople(course)) {
					if (personRole.getUser().getForumTopicSubscription().equals(Boolean.TRUE)) {
						ForumTopicWatch personTopicWatch = new ForumTopicWatch(forumTopic, personRole.getUser());
						getForumTopicWatchDAO().persist(personTopicWatch);
					}
				}
				
				getUserHistoryDAO().persist(new UserHistory(user, "Create new course topic", Action.CREATE, Category.FORUM_TOPIC, forumTopic));
				
			} else {
				ForumTopic forumTopic = getForumTopicDAO().find(forumPost.getTopic().getTopicId());
				forumPost.setTopic(forumTopic);
			}
			forumPost.setDateCreated(operationDate);
			forumPost.setLogicalDelete(Boolean.FALSE);
			forumPost.setLastUpdate(operationDate);
			forumPost.setUser(user);
			forumPost.setUpdater(user);
			getForumPostDAO().persist(forumPost);
			
			// update topic data
			forumPost.getTopic().setLastPost(forumPost);
			if (forumPost.getTopic().getFirstPost() == null) {
				// it is a new topic
				forumPost.getTopic().setFirstPost(forumPost);
			} else {
				forumPost.getTopic().setTotalReplies(forumPost.getTopic().getTotalReplies() + 1);
			}
			forumPost.getTopic().setLastUpdate(operationDate);
			
			// update forum data
			getForumDAO().recursiveIncreasePostsNumber(forum);
			recursiveSetLastPost(forum, forumPost, operationDate);
			
			// forum post notification
			ForumPostNotified forumPostNotified = new ForumPostNotified(forumPost.getPostId());
			forumPostNotified.setMailSended(Boolean.FALSE);
			
			getForumPostNotifiedDAO().persist(forumPostNotified);

			// update number of post of the user 
			user.setForumNumberOfPost(user.getForumNumberOfPost() + 1);
			user.setLastActiveForumDate(operationDate);
			user.setLastForumPostDate(operationDate);

			getUserHistoryDAO().persist(new UserHistory(user, "Create new post", Action.CREATE, Category.FORUM_POST, forumPost));
			
			return forumPost;
		} catch (Throwable th) {
			throw new ApplicationThrowable(th);
		}
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Transactional(readOnly=false, propagation=Propagation.REQUIRED)
	@Override
	public ForumTopic askAQuestion(Integer forumContainerId, Integer courseTranscriptionTopicId, String questionTitle, String questionText, String remoteAddr) throws ApplicationThrowable {
		try {
			Date now = new Date();
			User user = getCurrentUser();
			
			// search the course associated to this course fragment and retrieve the course resources container
			Forum container = null;
			Document document = null;
			Course course = null;
			if (courseTranscriptionTopicId != null) {
				ForumTopic courseTranscriptionTopic = getForumTopicDAO().find(courseTranscriptionTopicId);
				container = courseTranscriptionTopic.getForum();
				document = courseTranscriptionTopic.getDocument();
				course = getCourseDAO().getCourseByCourseFragment(container.getForumId());
			} else {
				container = getForumDAO().find(forumContainerId);
				document = getDocumentDAO().getTeachingDocument(forumContainerId);
				course = getCourseDAO().getCourseByCourseFragment(forumContainerId);
			}
			
			// question topic creation			
			ForumTopic questionTopic = new ForumTopic();
			questionTopic.setDateCreated(now);
			questionTopic.setLastUpdate(now);
			questionTopic.setUser(user);
			questionTopic.setForum(container);
			questionTopic.setDocument(document);
			questionTopic.setSubject(questionTitle);
			questionTopic.setIpAddress(remoteAddr);
			questionTopic.setTotalReplies(0);
			questionTopic.setTotalViews(0);
			questionTopic.setLogicalDelete(Boolean.FALSE);
			questionTopic.setLocked(Boolean.FALSE);
			
			getForumTopicDAO().persist(questionTopic);
			
			increaseForumTopicsNumber(container);
			
			// question topic option
			CourseTopicOption topicOption = new CourseTopicOption();
			topicOption.setCourseTopic(questionTopic);
			topicOption.setMode(CourseTopicMode.D);
			
			getCourseTopicOptionDAO().persist(topicOption);
			
			// first post of the new topic
			ForumPost firstPost = new ForumPost();
			firstPost.setDateCreated(now);
			firstPost.setLastUpdate(now);
			firstPost.setSubject(questionTitle);
			firstPost.setText(questionText);
			firstPost.setTopic(questionTopic);
			firstPost.setForum(container);
			firstPost.setIpAddress(remoteAddr);
			firstPost.setLogicalDelete(Boolean.FALSE);
			firstPost.setUser(user);
			firstPost.setUpdater(user);
			
			getForumPostDAO().persist(firstPost);
			
			// adjust the first and the last post of the new topic
			questionTopic.setFirstPost(firstPost);
			questionTopic.setLastPost(firstPost);
			
			// update forum data
			getForumDAO().recursiveIncreasePostsNumber(container);
			recursiveSetLastPost(container, firstPost, now);
			
			// topic subscription -> to all course people
			for(UserRole personRole : getCoursePeople(course)) {
				if (personRole.getUser().getForumTopicSubscription().equals(Boolean.TRUE)) {
					ForumTopicWatch personTopicWatch = new ForumTopicWatch(questionTopic, personRole.getUser());
					getForumTopicWatchDAO().persist(personTopicWatch);
				}
			}
			
			// add a forum post notification for email submission
			ForumPostNotified forumPostNotified = new ForumPostNotified(firstPost.getPostId());
			forumPostNotified.setMailSended(Boolean.FALSE);
				
			getForumPostNotifiedDAO().persist(forumPostNotified);
			
			// Changing the user last forum dates and number of post
			user.setLastActiveForumDate(now);
			user.setLastForumPostDate(now);
			user.setForumNumberOfPost(user.getForumNumberOfPost() + 1);
			
			getUserHistoryDAO().persist(new UserHistory(user, "Create new lesson discussion topic", Action.CREATE, Category.FORUM_TOPIC, questionTopic));
			getUserHistoryDAO().persist(new UserHistory(user, "Create new lesson discussion post", Action.CREATE, Category.FORUM_POST, firstPost));
			
			return questionTopic;
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
	@Override
	public Long countCheckPointPosts(Integer checkPointId) throws ApplicationThrowable {
		try {
			return getCoursePostExtDAO().countCheckPointPosts(checkPointId);
		} catch (PersistenceException e) {
			throw new ApplicationThrowable(e);
		}
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Transactional(readOnly=false, propagation=Propagation.REQUIRED)
	@Override
	public Course createCourse(String title, String description) throws ApplicationThrowable {
		try {
			Date now = new Date();
			User user = getCurrentUser();
			
			Forum coursesContainer = getForumDAO().getCoursesContainer();
			
			if (coursesContainer == null) {
				throw new ApplicationThrowable(ApplicationError.RECORD_NOT_FOUND_ERROR, "Course container not found!!!");
			}
			
			Forum courseForum = new Forum();
			courseForum.setDescription(description);
			courseForum.setTitle(title);
			courseForum.setDateCreated(now);
			courseForum.setLastUpdate(now);
			courseForum.setForumParent(coursesContainer);
			courseForum.setHierarchyLevel(coursesContainer.getHierarchyLevel() + 1);
			courseForum.setFullPath(coursesContainer.getFullPath());
			courseForum.setDispositionOrder(0);
			courseForum.setStatus(Forum.Status.ONLINE);
			courseForum.setType(Forum.Type.FORUM);
			courseForum.setSubType(Forum.SubType.COURSE);
			courseForum.setPostsNumber(0);
			courseForum.setTopicsNumber(0);
			courseForum.setSubForumsNumber(1); // it will contain the help resources subforum
			courseForum.setLogicalDelete(Boolean.FALSE);
			
			getForumDAO().persist(courseForum);
			getUserHistoryDAO().persist(new UserHistory(user, "Create new course", Action.CREATE, Category.FORUM, courseForum));

			courseForum.setFullPath(coursesContainer.getFullPath() + courseForum.getForumId() + ".");
			
			ForumOption forumOption = ForumUtils.getForumOptionForCourseForum(courseForum);
			getForumOptionDAO().persist(forumOption);
			
			// increase the number of the courses
			increaseSubForumsNumber(coursesContainer);
			
			Course course = new Course();
			course.setActive(Boolean.TRUE);
			course.setForum(courseForum);
			
			getCourseDAO().persist(course);
			
			// help resources container
			Forum helpResources = new Forum();
			
			helpResources.setTitle("Help resources");
			helpResources.setDescription("Help resources of " + title.trim());
			helpResources.setForumParent(courseForum);
			helpResources.setFullPath(courseForum.getFullPath()); // To do not violate the table constraint
			helpResources.setHierarchyLevel(courseForum.getHierarchyLevel() + 1);
			helpResources.setDispositionOrder(0);
			helpResources.setStatus(Forum.Status.ONLINE);
			helpResources.setType(Forum.Type.FORUM);
			helpResources.setSubType(Forum.SubType.COURSE);
			helpResources.setDateCreated(now);
			helpResources.setLastUpdate(now);
			helpResources.setPostsNumber(0);
			helpResources.setTopicsNumber(0);
			helpResources.setSubForumsNumber(0);
			helpResources.setLogicalDelete(Boolean.FALSE);

			getForumDAO().persist(helpResources);
			getUserHistoryDAO().persist(new UserHistory(user, "Create new forum", Action.CREATE, Category.FORUM, helpResources));

			helpResources.setFullPath(helpResources.getFullPath() + helpResources.getForumId() + ".");
			
			ForumOption helpResourcesOption = ForumUtils.getForumOptionForForumTopicContainer(helpResources);
			getForumOptionDAO().persist(helpResourcesOption);
			
			return course;
		} catch(Throwable th) {
			throw new ApplicationThrowable(th);
		}
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Transactional(readOnly=false, propagation=Propagation.REQUIRED)
	@Override
	public boolean deactivateCourse(Integer courseId) throws ApplicationThrowable {
		try {
			Course course = getCourseDAO().find(courseId);
			if (course == null) {
				return false;
			}
			course.setActive(false);
			// adjust 'All courses' subforums number
			decreaseSubForumsNumber(course.getForum().getForumParent());
			// adjust the last post of the 'All courses' container
			if (course.getForum().getForumParent().getLastPost() != null && 
				course.getForum().getLastPost().equals(course.getForum().getForumParent().getLastPost())) {
					course.getForum().getForumParent().setLastPost(determineAllCourseLastPost());
			}
			return true;
		} catch (PersistenceException e) {
			throw new ApplicationThrowable(e);
		}
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Transactional(readOnly=false, propagation=Propagation.REQUIRED)
	@Override
	public void deleteCourseForum(Integer forumId) throws ApplicationThrowable {
		Date now = new Date();
		try {
			Forum forum = getForumDAO().find(forumId);
			forum.setLastUpdate(now);
			forum.setLogicalDelete(Boolean.TRUE);
			
			List<Integer> deletedForumIds = getForumDAO().deleteForumsFromAncestorForum(forumId);
			// ancestor forum is also added to the deleted forums list
			deletedForumIds.add(forumId);
			List<Integer> deletedTopicIds = getForumTopicDAO().deleteForumTopicsFromForums(deletedForumIds);
			List<Integer> deletedPostIds = getForumPostDAO().deleteAllForumTopicPosts(deletedTopicIds);
			getForumPostNotifiedDAO().removeAllNotSentForumPostNotifications(deletedPostIds);
			
			if (forum.getForumParent() != null) {
				decreaseSubForumsNumber(forum.getForumParent());
				if (deletedPostIds.size() > 0) {
					getForumDAO().recursiveDecreasePostsNumber(forum.getForumParent(), deletedPostIds.size());
					ForumPost lastPost = getForumPostDAO().getLastForumPostByCreationDate(forum.getForumParent());
					recursiveSetLastPost(forum.getForumParent(), lastPost, now);
				}
			}
		} catch (Throwable th) {
			throw new ApplicationThrowable(th);
		}
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Transactional(readOnly=false, propagation=Propagation.REQUIRED)
	@Override
	public void deleteCourseTopic(Integer topicId) throws ApplicationThrowable {
		try {
			Date now = new Date();
			User user = getCurrentUser();
			
			ForumTopic courseTopic = getForumTopicDAO().find(topicId);
			courseTopic.setLogicalDelete(Boolean.TRUE);
			courseTopic.setLastUpdate(now);
			
			if (courseTopic.getAnnotation() != null) {
				courseTopic.getAnnotation().setLogicalDelete(Boolean.TRUE);
			}
			
			Forum forum = courseTopic.getForum();
			
			// remove not sent post notification
			getForumPostNotifiedDAO().removeAllNotSentForumPostNotificationsByTopic(courseTopic.getTopicId());
			// remove the posts
			Integer numberOfRemovedPosts = getForumPostDAO().deleteAllForumTopicPosts(courseTopic.getTopicId());
			getForumDAO().recursiveDecreasePostsNumber(forum, numberOfRemovedPosts);
			// update only the topics number of the course fragment forum container
			decreaseForumTopicsNumber(forum);
			
			// update forum last post
			ForumPost lastForumPost = getForumPostDAO().getLastForumPostByCreationDate(forum);
			recursiveSetLastPost(forum, lastForumPost, now);
			
			user.setLastActiveForumDate(now);
			
			getUserHistoryDAO().persist(new UserHistory(user, "Delete course topic", Action.DELETE, Category.FORUM_TOPIC, courseTopic));
		} catch (PersistenceException e) {
			throw new ApplicationThrowable(e);
		}
	}
		
	/**
	 * {@inheritDoc}
	 */
	@Transactional(readOnly=false, propagation=Propagation.REQUIRED)
	@Override
	public void deleteCourseTranscriptionPost(Integer postId, CourseTopicMode mode) throws ApplicationThrowable {
		
		ForumPost post;
		ForumTopic courseTopic;
		
			try {
				post = getForumPostDAO().find(postId);
				courseTopic = post.getTopic();
			} catch (Throwable th) {
				throw new ApplicationThrowable(th);
			}
			
		checkCourseTopicConsistency(post.getTopic().getTopicId(), mode);
		
		Date now = new Date();
		User user = getCurrentUser();
		
		try {
			doDeleteCoursePost(post, now, user);
			
			switch (mode) {
				case I:
					CourseCheckPoint checkPoint = getLastCheckPoint(courseTopic.getTopicId());
					if (checkPoint != null && checkPoint.getCheckPointPost().getPost().getPostId().equals(postId)) {
						updateCheckPointToLastTopicPost(checkPoint, true);
					} else if (checkPoint == null) {
						// something is bad...we create new check point to fix the course topic status
						CoursePostExt lastPost = getLastPostOfTopic(courseTopic.getTopicId(), true);
						addCourseCheckPoint(courseTopic.getTopicId(), lastPost, null);
					}
					break;
				case R:
					// NOP
					break;
				case C:
					// NOP
					break;
			}
			
		} catch (Throwable th) {
			throw new ApplicationThrowable(th);
		}
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Transactional(readOnly=false, propagation=Propagation.REQUIRED)
	@Override
	public void deleteForumPost(Integer postId) throws ApplicationThrowable {
		Date now = new Date();
		try {
			User user = getCurrentUser();
			
			ForumPost forumPost = getForumPostDAO().find(postId);
			
			// delete the post
			doDeleteCoursePost(forumPost, now, user);
			
			// remove post notification if needed
			ForumPostNotified mailNotification = getForumPostNotifiedDAO().find(postId);
			if (mailNotification != null && Boolean.FALSE.equals(mailNotification.getMailSended())) {
				getForumPostNotifiedDAO().remove(mailNotification);
			}
			
			// update user data 
			user.setLastActiveForumDate(now);
			user.setLastForumPostDate(now);
			user.setForumNumberOfPost(user.getForumNumberOfPost() - 1);
			
			getUserHistoryDAO().persist(new UserHistory(user, "Delete course topic post", Action.DELETE, Category.FORUM_POST, forumPost));
		} catch (Throwable th) {
			throw new ApplicationThrowable(th);
		}
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Transactional(readOnly=false, propagation=Propagation.REQUIRED)
	@Override
	public boolean doActivateCourse(Integer courseId) throws ApplicationThrowable {
		try {
			Course course = getCourseDAO().find(courseId);
			if (course == null) {
				return false;
			}
			course.setActive(true);
			// adjust 'All courses' subforums number
			increaseSubForumsNumber(course.getForum().getForumParent());
			// adjust the last post of the 'All courses' container
			if (course.getForum().getForumParent().getLastPost() == null ||
				(course.getForum().getLastPost() != null && 
					course.getForum().getLastPost().getDateCreated().after(course.getForum().getForumParent().getLastPost().getDateCreated()))) {
				course.getForum().getForumParent().setLastPost(course.getForum().getLastPost());
			}
			return true;
		} catch (PersistenceException e) {
			throw new ApplicationThrowable(e);
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
	public List<Course> getActiveCourses() throws ApplicationThrowable {
		try {
			return getCourseDAO().getActiveCourses();
		} catch (Throwable th) {
			throw new ApplicationThrowable(th);
		}
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<Course> getActiveCourses(Integer entryId) throws ApplicationThrowable {
		try {
			return getCourseDAO().getActiveCoursesByDocument(entryId);
		} catch (Throwable th) {
			throw new ApplicationThrowable(th);
		}
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public Page getCoursePeople(Integer courseId, PaginationFilter paginationFilter) throws ApplicationThrowable {
		// TODO
		return null;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Page getCourses(Boolean onlyActives, PaginationFilter paginationFilter) throws ApplicationThrowable {
		try {
			return getCourseDAO().getCourses(onlyActives, paginationFilter);
		} catch (Throwable th) {
			throw new ApplicationThrowable(th);
		}
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public CourseCheckPoint getCheckPointByPost(Integer postId) throws ApplicationThrowable {
		try {
			return getCourseCheckPointDAO().getCheckPointByPost(postId);
		} catch (Throwable th) {
			throw new ApplicationThrowable(th);
		}
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public CoursePostExt getCoursePostByForumPostId(Integer postId) throws ApplicationThrowable {
		try {
			return getCoursePostExtDAO().getExtendedPostByForumPost(postId);
		} catch (Throwable th) {
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
			topic.setTotalViews(topic.getTotalViews() != null && topic.getTotalViews() > 0 ? topic.getTotalViews() + 1 : 1);
			return topic;
		} catch(Throwable th) {
			throw new ApplicationThrowable(th);
		}
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public CourseTopicMode getCourseTopicMode(Integer topicId) throws ApplicationThrowable {
		try {
			CourseTopicOption option = getCourseTopicOptionDAO().getOption(topicId);
			return option != null ? option.getMode() : null; 
		} catch (Throwable th) {
			throw new ApplicationThrowable(th);
		}
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public Map<Integer, CourseTopicMode> getCourseTopicsMode(List<ForumTopic> topics) throws ApplicationThrowable {
		try {
			Set<Integer> topicIds = new HashSet<Integer>();
			for(ForumTopic topic : topics) {
				topicIds.add(topic.getTopicId());
			}
			List<CourseTopicOption> options = getCourseTopicOptionDAO().getOptions(topicIds);
			Map<Integer, CourseTopicMode> topicsMode = new HashMap<Integer, CourseTopicOption.CourseTopicMode>();
			for(CourseTopicOption option : options) {
				Integer topicId = option.getCourseTopic().getTopicId();
				topicsMode.put(topicId, option.getMode());
				topicIds.remove(topicId);
			}
			// Not found topics are considered as course discussions
			for(Integer notFoundId : topicIds) {
				topicsMode.put(notFoundId, CourseTopicMode.D);
			}
			return topicsMode;
		} catch(Throwable th) {
			throw new ApplicationThrowable(th);
		}
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public Integer getCourseTranscriptionResourcesForum(Integer topicId) throws ApplicationThrowable {
		try {
			ForumTopic courseTopic = getForumTopicDAO().find(topicId);
			if (courseTopic == null) {
				throw new ApplicationThrowable(ApplicationError.RECORD_NOT_FOUND_ERROR, "Course topic [" + topicId + "] is missing...");
			}
			return courseTopic.getForum().getForumId();
		} catch (Throwable th) {
			throw new ApplicationThrowable(th);
		}
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public CourseTopicOption getCourseTranscriptionTopicOption(Integer forumId) throws ApplicationThrowable {
		try {
			return getCourseTopicOptionDAO().getCourseTranscriptionOptionFromForum(forumId);
		} catch (Throwable th) {
			throw new ApplicationThrowable(th);
		}
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public Page getCoursesElements(Integer courseForumId, PaginationFilter paginationFilterForum) throws ApplicationThrowable {
		try {
			Forum container = getForumDAO().find(courseForumId);
			if (container != null && Type.CATEGORY.equals(container.getForumParent().getType())) {
				// container is a course
				return getForumDAO().findCoursesElements(courseForumId, true, paginationFilterForum);
			}
			// container is a course fragment
			return getForumDAO().findCoursesElements(courseForumId, false, paginationFilterForum);
		} catch (Throwable th) {
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
	public List<ForumTopic> getDocumentTopicsFromCourse(Integer entryId, Integer courseId) throws ApplicationThrowable {
		try {
			Course course = getCourseDAO().find(courseId);
			if (course == null) {
				throw new ApplicationThrowable(ApplicationError.RECORD_NOT_FOUND_ERROR, "Course with id [" + courseId + "] has been not found!");
			}
			List<Forum> courseFragmentsContainers = getForumDAO().findSubForumsByDocument(course.getForum().getForumId(), entryId);
			List<ForumTopic> topics = new ArrayList<ForumTopic>();
			for(Forum container : courseFragmentsContainers) {
				topics.addAll(getForumTopicDAO().getForumTopicsByParentForumAndDocument(container.getForumId(), entryId));
			}
			return topics;
		} catch(Throwable th) {
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
	public Course getLastActiveCourse(Integer entryId) throws ApplicationThrowable {
		try {
			return getCourseDAO().getLastActiveCourseByDocument(entryId);
		} catch (Throwable th) {
			throw new ApplicationThrowable(th);
		}
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public CourseCheckPoint getLastCheckPoint(Integer topicId) throws ApplicationThrowable {
		try {
			return getCourseCheckPointDAO().getLastCheckPointByTopicId(topicId);
		} catch (Throwable th) {
			throw new ApplicationThrowable(th);
		}
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public CoursePostExt getLastPostOfTopic(Integer topicId, boolean byDateCreated) throws ApplicationThrowable {
		try {
			return getCoursePostExtDAO().getLastPostInTopic(topicId, byDateCreated);
		} catch (Throwable th) {
			throw new ApplicationThrowable(th);
		}
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<CourseTopicOption> getMasterOptionsByDocumentForActiveCourses(Integer entryId) throws ApplicationThrowable {
		try {
			return getCourseTopicOptionDAO().getMasterOptionsByDocumentInActiveCourses(entryId);
		} catch (Throwable th) {
			throw new ApplicationThrowable(th);
		}
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public CourseTopicOption getOptionByCourseTopic(Integer topicId) throws ApplicationThrowable {
		try {
			return getCourseTopicOptionDAO().getOption(topicId);
		} catch (Throwable th) {
			throw new ApplicationThrowable(th);
		}
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public Page getPostsFromCourseTopic(ForumTopic courseTopic, PaginationFilter filter) throws ApplicationThrowable {
		try {
			return getCoursePostExtDAO().getExtendedCourseTopicPosts(courseTopic, filter);
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
	public Map<String, List<?>> getTeachingForumStatistics(Integer numberOfTranscriptions, Integer numberOfQuestions, Integer numberOfTopics, String account) throws ApplicationThrowable {
		Map<String, List<?>> returnMap = new HashMap<String, List<?>>();
		try {
			returnMap.put("MOST RECENT COURSE TOPICS", getCourseTopicOptionDAO().getMostRecentExtendedCourseTopics(numberOfTopics, account));
			returnMap.put("MOST RECENT TRANSCRIPTION TOPICS", getCourseTopicOptionDAO().getMostRecentCollaborativeTranscriptionTopics(numberOfTranscriptions, account));
			returnMap.put("MOST RECENT COURSE DISCUSSIONS", getCourseTopicOptionDAO().getMostRecentCourseDiscussions(numberOfQuestions, account));
			return returnMap;
		} catch (Throwable th) {
			throw new ApplicationThrowable(th);
		}
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<CourseCheckPoint> getTopicCheckPoints(Integer topicId) throws ApplicationThrowable {
		try {
			return getCourseCheckPointDAO().getCheckPointsByTopic(topicId);
		} catch (Throwable th) {
			throw new ApplicationThrowable(th);
		}
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<Annotation> getTopicImageAnnotations(String imageName, Integer forumId, Annotation.Type type) throws ApplicationThrowable {
		try {
			return getAnnotationDAO().getTopicImageAnnotations(imageName, forumId, type);
		} catch (Throwable th) {
			throw new ApplicationThrowable(th);
		}
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public UserAuthority getUserCourseAuthority(String account) throws ApplicationThrowable {
		try {
			return getUserAuthorityDAO().getUserCourseAuthority(account);
		} catch (Throwable th) {
			throw new ApplicationThrowable(th);
		}
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public UserPersonalNotes getUserPersonalNotes(String account) throws ApplicationThrowable {
		try {
			User user = account != null ? getUserDAO().findUser(account) : getCurrentUser();
			return getUserPersonalNotesDAO().getPersonalNotes(user);
		} catch (Throwable th) {
			throw new ApplicationThrowable(th);
		}
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public Page getUsers(User user, Boolean studentRoleSearch, PaginationFilter paginationFilter) throws ApplicationThrowable {
		try {
			Set<Authority> authoritiesToSearch = new HashSet<Authority>();
			if (studentRoleSearch != null) {
				authoritiesToSearch.add(Authority.STUDENTS);
			}
			return getUserDAO().findUsers(user, authoritiesToSearch, Boolean.TRUE.equals(studentRoleSearch), paginationFilter);
		} catch(Throwable th){
			throw new ApplicationThrowable(th);
		}	
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public Map<String,UserAuthority> getUsersCourseAuthority(Set<String> accountIds) throws ApplicationThrowable {
		try {
			return getUserAuthorityDAO().getUsersCourseAuthority(accountIds);
		} catch (Throwable th) {
			throw new ApplicationThrowable(th);
		}
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Transactional(readOnly=false, propagation=Propagation.REQUIRED)
	@Override
	public Boolean grantStudentPermission(String account) throws ApplicationThrowable {
		try {
			User user = getUserDAO().findUser(account);
			
			if (user != null) {
				boolean found = false;
				for (UserRole role : user.getUserRoles()) {
					if (Authority.STUDENTS.equals(role.getUserAuthority().getAuthority())) {
						found = true;
						break;
					}
				}
				if (!found) {
					UserAuthority studentAuthority = getUserAuthorityDAO().find(Authority.STUDENTS);
					UserRole studentRole = new UserRole(user, studentAuthority);
					getUserRoleDAO().persist(studentRole);
					user.getUserRoles().add(studentRole);
					return Boolean.TRUE;
				}
			}
		} catch (Throwable th) {
			throw new ApplicationThrowable(th);
		}
		return Boolean.FALSE;
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public Boolean isDeletableAnnotation(Annotation annotation) throws ApplicationThrowable {
		if (annotation.getForumTopic() != null) {
			return getForumPostDAO().countTopicPosts(annotation.getForumTopic().getTopicId()) == 0;
		}
		return Boolean.TRUE;
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean isDocumentInActiveCourse(Integer entryId) throws ApplicationThrowable {
		try {
			return getCourseDAO().isDocumentInActiveCourse(entryId);
		} catch (Throwable th) {
			throw new ApplicationThrowable(th);
		}
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean isDocumentInCourse(Integer entryId) throws ApplicationThrowable {
		try {
			return getCourseDAO().isDocumentInCourse(entryId);
		} catch (Throwable th) {
			throw new ApplicationThrowable(th);
		}
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean isForumInActiveCourse(Integer forumId) throws ApplicationThrowable {
		try {
			return getCourseDAO().isForumInActiveCourse(forumId);
		} catch (Throwable th) {
			throw new ApplicationThrowable(th);
		}
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean isForumInCourse(Integer forumId) throws ApplicationThrowable {
		try {
			return getCourseDAO().isForumInCourse(forumId);
		} catch (Throwable th) {
			throw new ApplicationThrowable(th);
		}
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public Boolean isSubscribedToCourseTranscription(Integer topicId) throws ApplicationThrowable {
		try {
			ForumTopic courseTopic = getForumTopicDAO().getNotDeletedForumTopic(topicId);
			
			if (courseTopic != null) {
				//This control is for anonymous user that look a topic 
				if (SecurityContextHolder.getContext().getAuthentication().getPrincipal() instanceof String) {
					return null;
				}
				User user = getCurrentUser();
				if (user != null && getForumTopicWatchDAO().findByTopicAndUser(user, courseTopic) != null) {
					return true;
				}
			}
			return false;
		} catch (Throwable th) {
			throw new ApplicationThrowable(th);
		}
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Transactional(readOnly=false, propagation=Propagation.REQUIRED)
	@Override
	public void openCloseCourseTopic(Integer courseTopicId, Boolean close) throws ApplicationThrowable {
		try {
			ForumTopic courseTopic = getForumTopicDAO().find(courseTopicId);
			if (!courseTopic.getLocked().equals(close)) {
				courseTopic.setLastUpdate(new Date());
				courseTopic.setLocked(close);
				getUserHistoryDAO().persist(new UserHistory(getCurrentUser(), (close ? "Close" : "Open") + " course topic", Action.MODIFY, Category.FORUM_TOPIC, courseTopic));
			}
		} catch (Throwable th) {
			throw new ApplicationThrowable(th);
		}
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Transactional(readOnly=false, propagation=Propagation.REQUIRED)
	@Override
	public ForumPost replyPost(ForumPost forumPost) throws ApplicationThrowable {
		Date operationDate = new Date();
		try {
			forumPost.setPostId(null);
			
			Forum forum = getForumDAO().find(forumPost.getForum().getForumId());
			ForumPost parentPost = getForumPostDAO().find(forumPost.getParentPost().getPostId());
			User user = getCurrentUser();
	
			forumPost.setForum(forum);
			forumPost.setDateCreated(operationDate);
			forumPost.setLastUpdate(operationDate);
			forumPost.setParentPost(parentPost);
			forumPost.setUser(user);
			
			getForumPostDAO().persist(forumPost);
			
			parentPost.setReplyNumber(parentPost.getReplyNumber() + 1);
	
			getForumDAO().recursiveIncreasePostsNumber(forum);
			recursiveSetLastPost(forum, forumPost, operationDate);
			
			// forum post notification
			ForumPostNotified postNotified = new ForumPostNotified();
			postNotified.setPostId(forumPost.getPostId());
			postNotified.setMailSended(Boolean.FALSE);
			
			getForumPostNotifiedDAO().persist(postNotified);
	
			// Update number of post 
			user.setForumNumberOfPost(user.getForumNumberOfPost() + 1);
			user.setLastActiveForumDate(operationDate);
			user.setLastForumPostDate(operationDate);
	
			getUserHistoryDAO().persist(new UserHistory(user, "Reply to post", Action.CREATE, Category.FORUM_POST, forumPost));
			
			return forumPost;
		} catch (Throwable th) {
			throw new ApplicationThrowable(th);
		}
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Transactional(readOnly=false, propagation=Propagation.REQUIRED)
	@Override
	public Boolean revokeStudentPermission(String account) throws ApplicationThrowable {
		try {
			User user = getUserDAO().findUser(account);
			
			if (user != null) {
				UserRole studentUserRole = null;
				for (UserRole role : user.getUserRoles()) {
					if (Authority.STUDENTS.equals(role.getUserAuthority().getAuthority())) {
						studentUserRole = role;
						break;
					}
				}
				if (studentUserRole != null) {
					user.getUserRoles().remove(studentUserRole);
					getUserRoleDAO().remove(studentUserRole);
					return Boolean.TRUE;
				}
			}
		} catch (Throwable th) {
			throw new ApplicationThrowable(th);
		}
		return Boolean.FALSE;
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Transactional(readOnly=false, propagation=Propagation.REQUIRED)
	@Override
	public UserPersonalNotes saveUserPersonalNotes(Integer personalNotesId, String personalNotes) throws ApplicationThrowable {
		Date now = new Date();
		try {
			if (!new Integer(0).equals(personalNotesId)) {
				UserPersonalNotes personalNote = getUserPersonalNotesDAO().find(personalNotesId);
				personalNote.setPersonalNotes(personalNotes);
				personalNote.setLastUpdate(now);
				
				return personalNote;
			}
			UserPersonalNotes newPersonalNote = new UserPersonalNotes(personalNotes);
			newPersonalNote.setUser(getCurrentUser());
			newPersonalNote.setDateCreated(now);
			newPersonalNote.setLastUpdate(now);
			
			getUserPersonalNotesDAO().persist(newPersonalNote);
			
			return newPersonalNote;
		} catch (Throwable th) {
			throw new ApplicationThrowable(th);
		}
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Transactional(readOnly=false, propagation=Propagation.REQUIRED)
	@Override
	public CourseCheckPoint setIncrementalTranscription(CoursePostExt postExt) throws ApplicationThrowable {
		try {
			if (getCoursePostExtDAO().find(postExt.getPostExtId()) == null) {
				throw new ApplicationThrowable(ApplicationError.RECORD_NOT_FOUND_ERROR, "Extended post [" + postExt.getPostExtId() + "] is missing...");
			}
			CourseTopicOption option = getCourseTopicOptionDAO().getOption(postExt.getPost().getTopic().getTopicId());
			if (option == null) {
				throw new ApplicationThrowable(ApplicationError.RECORD_NOT_FOUND_ERROR, "Course Topic Option is missing for topic [" + postExt.getPost().getTopic().getTopicId() + "]...");
			}
			CourseCheckPoint checkPoint = getCourseCheckPointDAO().getLastCheckPointByTopicId(postExt.getPost().getTopic().getTopicId());
			if (checkPoint == null) {
				checkPoint = new CourseCheckPoint(option, postExt);
				checkPoint.setCheckPointTime(postExt.getPost().getLastUpdate());
				getCourseCheckPointDAO().persist(checkPoint);
			} else {
				checkPoint.setCheckPointPost(postExt);
				checkPoint.setCheckPointTime(postExt.getPost().getLastUpdate());
			}
			
			return checkPoint;
		} catch (Throwable th) {
			throw new ApplicationThrowable(th);
		}
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Transactional(readOnly=false, propagation=Propagation.REQUIRED)
	@Override
	public Boolean subscribeCourseTopic(Integer courseTopicId) throws ApplicationThrowable {
		try {
			ForumTopic forumTopic = getForumTopicDAO().getNotDeletedForumTopic(courseTopicId);
			User user = getCurrentUser();
			
			if (forumTopic == null || user == null) {
				return Boolean.FALSE;
			}

			ForumTopicWatch forumTopicWatch = new ForumTopicWatch();
			forumTopicWatch.setTopic(forumTopic);
			forumTopicWatch.setUser(user);
			
			getForumTopicWatchDAO().persist(forumTopicWatch);
			return Boolean.TRUE;
		} catch (Throwable th) {
			throw new ApplicationThrowable(th);
		}
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Transactional(readOnly=false, propagation=Propagation.REQUIRED)
	@Override
	public Boolean unsubscribeForumTopic(Integer courseTopicId) throws ApplicationThrowable {
		try {
			ForumTopic courseTopic = getForumTopicDAO().getNotDeletedForumTopic(courseTopicId);
			
			if (courseTopic != null) {
				User user = getCurrentUser();
				ForumTopicWatch courseTopicWatch = getForumTopicWatchDAO().findByTopicAndUser(user, courseTopic);
				
				if (courseTopicWatch != null) {
					getForumTopicWatchDAO().remove(courseTopicWatch);
				}
				return Boolean.TRUE;
			} else {
				return Boolean.FALSE;
			}
		} catch (Throwable th) {
			throw new ApplicationThrowable(th);
		}
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Transactional(readOnly=false, propagation=Propagation.REQUIRED)
	@Override
	public Map<Annotation, Integer> updateAnnotations(
			Integer imageId, 
			Integer forumContainerId,
			List<Annotation> fromViewAnnotations, 
			String ipAddress,
			boolean teacherMode) throws ApplicationThrowable {
		try {
			Map<Annotation, Integer> returnMap = new HashMap<Annotation, Integer>();
			Image image = getImageDAO().find(imageId);
			if (image == null) {
				return new HashMap<Annotation, Integer>(0);
			}

			User user = getCurrentUser();
			
			List<Annotation> persistedAnnotations = getAnnotationDAO().getTopicImageAnnotations(image.getImageName(), forumContainerId, Annotation.Type.TEACHING);
			
			Date operationDate = new Date();

			for (Annotation viewAnnotation : fromViewAnnotations) {
				Annotation annotation = getAnnotationDAO().findByAnnotationId(viewAnnotation.getAnnotationId());
				
				boolean isChanged = annotation == null || (annotation != null && 
						(!annotation.getTitle().equals(viewAnnotation.getTitle()) 
							|| !annotation.getText().equals(viewAnnotation.getText())
							|| !annotation.getX().equals(viewAnnotation.getX())
							|| !annotation.getY().equals(viewAnnotation.getY())
							|| !annotation.getWidth().equals(viewAnnotation.getWidth())
							|| !annotation.getHeight().equals(viewAnnotation.getHeight()))
							|| !annotation.getVisible().equals(viewAnnotation.getVisible())
							|| (annotation.getRgbColor() == null && viewAnnotation.getRgbColor() != null)
							|| (annotation.getRgbColor() != null && !annotation.getRgbColor().equals(viewAnnotation.getRgbColor())));
				
				if (annotation == null) {
					annotation = new Annotation();
				} 
				
				if (isChanged) {
					// We set general annotation details
					annotation.setLastUpdate(operationDate);
					annotation.setTitle(viewAnnotation.getTitle());
					annotation.setText(viewAnnotation.getText());
					annotation.setX(viewAnnotation.getX());
					annotation.setY(viewAnnotation.getY());
					annotation.setWidth(viewAnnotation.getWidth());
					annotation.setHeight(viewAnnotation.getHeight());
					annotation.setVisible(viewAnnotation.getVisible());
					annotation.setRgbColor(viewAnnotation.getRgbColor());
				}
				
				if (annotation.getAnnotationId() != null) {
					// The annotation already exists in the database, we remove it from the persistedAnnotation list
					for (int i = persistedAnnotations.size() - 1 ; i >= 0; i--) {
						if (persistedAnnotations.get(i).getAnnotationId() == annotation.getAnnotationId()) {
							persistedAnnotations.remove(i);
							break;
						}
					}
					
					returnMap.put(annotation, -1);
				} else {
					// This is a new annotation
					annotation.setDateCreated(operationDate);
					annotation.setLogicalDelete(Boolean.FALSE);
					annotation.setUser(user);
					annotation.setImage(image);
					annotation.setType(Annotation.Type.TEACHING);
					annotation.setVisible(Boolean.TRUE);
					
					getAnnotationDAO().persist(annotation);
					
					Forum forum = getForumDAO().find(forumContainerId);
					
					ForumTopic topicAnnotation = new ForumTopic(null);
					topicAnnotation.setForum(forum);
					topicAnnotation.setDateCreated(operationDate);
					topicAnnotation.setLastUpdate(operationDate);
					topicAnnotation.setIpAddress(ipAddress);
					topicAnnotation.setUser(user);
					topicAnnotation.setSubject(annotation.getTitle()/* + " (Question)"*/);
					topicAnnotation.setTotalReplies(0);
					topicAnnotation.setTotalViews(0);
					topicAnnotation.setLastPost(null);
					topicAnnotation.setFirstPost(null);
					topicAnnotation.setLogicalDelete(Boolean.FALSE);
					topicAnnotation.setLocked(Boolean.FALSE);
					
					topicAnnotation.setAnnotation(annotation);
					getForumTopicDAO().persist(topicAnnotation);
					
					annotation.setForumTopic(topicAnnotation);
					returnMap.put(annotation, topicAnnotation.getTopicId());
					
					getUserHistoryDAO().persist(new UserHistory(user, "Create annotation topic", Action.CREATE, Category.FORUM_TOPIC, topicAnnotation));
					
					// we increase the course fragment resources topics number
					// we do not have to increase parent forum topics number
					increaseForumTopicsNumber(forum);
					
					// we save first post of annotation topic
					ForumPost annotationPost = new ForumPost();
					annotationPost.setForum(forum);
					annotationPost.setTopic(topicAnnotation);
					annotationPost.setDateCreated(operationDate);
					annotationPost.setLastUpdate(operationDate);
					annotationPost.setUser(user);
					annotationPost.setUpdater(user);
					annotationPost.setIpAddress(ipAddress);
					annotationPost.setLogicalDelete(Boolean.FALSE);
					annotationPost.setSubject(annotation.getTitle());
					annotationPost.setText("<p>" + annotation.getText() + "</p>");
					
					getForumPostDAO().persist(annotationPost);
					
					topicAnnotation.setFirstPost(annotationPost);
					topicAnnotation.setLastPost(annotationPost);
					
					getUserHistoryDAO().persist(new UserHistory(user, "Create annotation first post", Action.CREATE, Category.FORUM_POST, annotationPost));
					
					recursiveSetLastPost(forum, annotationPost, operationDate);
					
					// we increase the number of forum posts number
					getForumDAO().recursiveIncreasePostsNumber(forum);

					// we have to add a course topic option
					CourseTopicOption option = new CourseTopicOption();
					option.setCourseTopic(topicAnnotation);
					option.setMode(CourseTopicMode.Q);
					
					getCourseTopicOptionDAO().persist(option);
					
					Course course = getCourseDAO().find(forum.getForumParent().getForumId());
					
					// topic subscription
					if (isStudent(user)) {
						// a student has created an annotation: subscription for the current student and for teachers only
						if (user.getForumTopicSubscription().equals(Boolean.TRUE)) {
							ForumTopicWatch forumTopicWatch = new ForumTopicWatch(topicAnnotation, user);
							getForumTopicWatchDAO().persist(forumTopicWatch);
						}
						
						for(UserRole teacher : getCourseTeachers(course)) {
							if (teacher.getUser().getForumTopicSubscription().equals(Boolean.TRUE)) {
								ForumTopicWatch forumTopicWatch = new ForumTopicWatch(topicAnnotation, teacher.getUser());
								getForumTopicWatchDAO().persist(forumTopicWatch);
							}
						}
					} else {
						// a teacher or admin has created an annotation: subscription for all the people of the course
						for(UserRole coursePerson : getCoursePeople(course)) {
							if (coursePerson.getUser().getForumTopicSubscription().equals(Boolean.TRUE)) {
								ForumTopicWatch forumTopicWatch = new ForumTopicWatch(topicAnnotation, coursePerson.getUser());
								getForumTopicWatchDAO().persist(forumTopicWatch);
							}
						}
					}
					
					// annotation post notification
					ForumPostNotified annotationForumPostNotified = new ForumPostNotified(annotationPost.getPostId());
					annotationForumPostNotified.setMailSended(Boolean.FALSE);
					getForumPostNotifiedDAO().persist(annotationForumPostNotified);
				}
			}
			
			// We remove the old persisted annotations that are still in the list
			for(Annotation toRemoveAnnotation : persistedAnnotations) {
				if (!teacherMode && Boolean.FALSE.equals(toRemoveAnnotation.getVisible())) {
					// RR: Non admin/teacher users do not return not visible annotations from the view,
					// so those annotations have not to be removed
					returnMap.put(toRemoveAnnotation, -1);
					continue;
				}
				if (toRemoveAnnotation.getForumTopic() != null) {
					// RR: It should not be possible to remove an annotation associated to a topic
					ForumTopic annotationTopic = toRemoveAnnotation.getForumTopic();
					annotationTopic.setLogicalDelete(Boolean.TRUE);
					
					getUserHistoryDAO().persist(new UserHistory(user, "Delete annotation topic", Action.DELETE, Category.FORUM_TOPIC, annotationTopic));

					Integer removedPostsNumber = getForumPostDAO().deleteAllForumTopicPosts(annotationTopic.getTopicId());
					Forum forum = annotationTopic.getForum();
					ForumPost lastPost = getForumPostDAO().getLastForumPostByCreationDate(forum);
					recursiveSetLastPost(forum, lastPost, operationDate);
					
					getForumDAO().recursiveDecreasePostsNumber(forum, removedPostsNumber);
					decreaseForumTopicsNumber(forum);
				}
				toRemoveAnnotation.setLastUpdate(operationDate);
				toRemoveAnnotation.setLogicalDelete(Boolean.TRUE);
			}
			
			return returnMap;
		} catch (Throwable throwable){
			throw new ApplicationThrowable(throwable);
		}
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Transactional(readOnly=false, propagation=Propagation.REQUIRED)
	@Override
	public CoursePostExt updateCourseTranscriptionPost(
			Integer postId,
			String postSubject, 
			String postContent, 
			String transcription, 
			Integer volNum,
			String volLetExt, 
			String insertNum, 
			String insertLet,
			Integer folioNum, 
			String folioMod, 
			String folioRV,
			CourseTopicMode mode) throws ApplicationThrowable {
		
		CoursePostExt postExt = getCoursePostExtDAO().getExtendedPostByForumPost(postId);
		
		if (postExt == null) {
			throw new ApplicationThrowable(ApplicationError.RECORD_NOT_FOUND_ERROR, "Corse Topic Post [" + postId + "] not found!");
		}
		
		Date now = new Date();
		User user = getCurrentUser();
		
		CourseTopicOption courseTopicOption = checkCourseTopicConsistency(postExt.getPost().getTopic().getTopicId(), mode);
		
		doUpdateCourseTranscriptionPost(postSubject, postContent, transcription, volNum, volLetExt, insertNum, insertLet, folioNum, folioMod, folioRV, postExt, now, user);
		
		switch (mode) {
			case I:
				// updates the course checkpoint only if it is not defined (problems???)
				CourseCheckPoint checkPoint = getCourseCheckPointDAO().getLastCheckPointByTopicId(postExt.getPost().getTopic().getTopicId());
				if (checkPoint == null) {
					checkPoint = new CourseCheckPoint(courseTopicOption, postExt);
					checkPoint.setCheckPointTime(now);
					getCourseCheckPointDAO().persist(checkPoint);
				}
				break;
			case R:
				// nothing to do
				break;
			case C:
				// TODO
				break;
		}

		return postExt;
	}
	
	/* Privates */
	
	private CourseTopicOption checkCourseTopicConsistency(Integer courseTopicId, CourseTopicMode mode) throws ApplicationThrowable {
		try {
			ForumTopic courseTopic = getForumTopicDAO().find(courseTopicId);
			if (courseTopic == null) {
				throw new ApplicationThrowable(ApplicationError.NULLPOINTER_ERROR, this.getClass().getName() + "#addCourseTranscriptionPost --> course topic [" + courseTopicId + "] is missing");
			}
			
			CourseTopicOption option = getCourseTopicOptionDAO().getOption(courseTopicId);
			if (option != null && (mode != null && !option.getMode().equals(mode))) {
				throw new ApplicationThrowable(ApplicationError.ILLEGAL_STATUS, "Course topic [" + courseTopicId + "] was defined with {" + option.getMode() + "} mode, so it is not allowed to add a post with {" + mode + "} mode");
			} else if (option == null) {
				// Bad course topic status (no option defined)
				throw new ApplicationThrowable(ApplicationError.ILLEGAL_STATUS, "Course topic [" + courseTopicId + "] has no topic option defined...it is not possible to manage this topic. Please contact the admin!!!");
			}
			
			if (!CourseTopicMode.I.equals(option.getMode()) && 
					!CourseTopicMode.C.equals(option.getMode()) &&
					!CourseTopicMode.R.equals(option.getMode())) {
				throw new ApplicationThrowable(ApplicationError.ILLEGAL_CALL, this.getClass().getName() + "#addCourseTranscriptionPost cannot be called for a topic with {" + option.getMode() + "} mode");
			}
			
			return option;
		} catch (Throwable th) {
			throw new ApplicationThrowable(th);
		}
	}
	
	private void decreaseForumTopicsNumber(Forum forum) throws PersistenceException {
		if (forum == null) {
			return;
		}
		Integer oldTopicsNumber = forum.getTopicsNumber();
		forum.setTopicsNumber(oldTopicsNumber != null && oldTopicsNumber > 0 ? oldTopicsNumber - 1 : 0);
	}
	
	private void decreaseSubForumsNumber(Forum forum) throws PersistenceException {
		if (forum == null) {
			return;
		}
		Integer oldSubForumsNumber = forum.getSubForumsNumber();
		forum.setSubForumsNumber(oldSubForumsNumber != null && oldSubForumsNumber > 0 ? oldSubForumsNumber - 1 : 0);
	}
	
	private ForumPost determineAllCourseLastPost() {
		ForumPost lastPost = null;
		for(Course current : getCourseDAO().getActiveCourses()) {
			ForumPost currentLastPost = current.getForum().getLastPost();
			if (lastPost == null || (currentLastPost != null && currentLastPost.getDateCreated().after(lastPost.getDateCreated()))) {
				lastPost = currentLastPost;
			}
		}
		return lastPost;
	}
	
	private CoursePostExt doAddCourseTranscriptionPost(
			ForumTopic courseTopic, 
			String postSubject, 
			String postContent,
			String transcription,
			Integer volNum,
			String volLetExt,
			String insertNum,
			String insertLet,
			Integer folioNum,
			String folioMod,
			String folioRV,
			String remoteAddr,
			Date now,
			User user) throws PersistenceException {
		
		ForumPost post = new ForumPost();
		post.setTopic(courseTopic);
		post.setForum(courseTopic.getForum());
		post.setSubject(postSubject);
		post.setText(postContent);
		post.setIpAddress(remoteAddr);
		
		post.setDateCreated(now);
		post.setLastUpdate(now);
		post.setLogicalDelete(Boolean.FALSE);
		post.setUser(user);
		post.setUpdater(user);
		
		getForumPostDAO().persist(post);
		
		CoursePostExt coursePostExt = new CoursePostExt(post);
		coursePostExt.setTranscription(transcription);
		coursePostExt.setVolNum(volNum);
		coursePostExt.setVolLetExt(StringUtils.safeTrim(volLetExt));
		coursePostExt.setInsertNum(StringUtils.safeTrim(insertNum));
		coursePostExt.setInsertLet(StringUtils.safeTrim(insertLet));
		coursePostExt.setFolioNum(folioNum);
		coursePostExt.setFolioMod(StringUtils.safeTrim(folioMod));
		coursePostExt.setFolioRV(CoursePostExt.RectoVerso.find(StringUtils.safeTrim(folioRV)));
		
		getCoursePostExtDAO().persist(coursePostExt);
		
		if (courseTopic.getFirstPost() == null) {
			courseTopic.setFirstPost(post);
			courseTopic.setTotalReplies(0);
		} else {
			courseTopic.setTotalReplies(courseTopic.getTotalReplies() + 1);
		}
		courseTopic.setLastPost(post);
		courseTopic.setLastUpdate(now);
		
		getForumDAO().recursiveIncreasePostsNumber(courseTopic.getForum());
		recursiveSetLastPost(courseTopic.getForum(), post, now);
		
		// add a forum post notification for email submission
		ForumPostNotified forumPostNotified = new ForumPostNotified(post.getPostId());
		forumPostNotified.setMailSended(Boolean.FALSE);
			
		getForumPostNotifiedDAO().persist(forumPostNotified);
		
		// Changing the user last forum dates and number of post
		user.setLastActiveForumDate(now);
		user.setLastForumPostDate(now);
		user.setForumNumberOfPost(user.getForumNumberOfPost() + 1);
		
		getUserHistoryDAO().persist(new UserHistory(user, "Add course transcription post", Action.CREATE, Category.FORUM_POST, post));
		
		return coursePostExt;
	}
	
	/**
	 * Deletes a post.
	 * 
	 * @param post the post to delete
	 * @param now the operation date
	 * @param user the user
	 * @throws PersistenceException
	 */
	private void doDeleteCoursePost(ForumPost post, Date now, User user) throws PersistenceException {
		Forum courseForum = post.getForum();
		ForumTopic courseTopic = post.getTopic();
		post.setLogicalDelete(Boolean.TRUE);
		post.setLastUpdate(now);
		post.setUpdater(user);
		
		getUserHistoryDAO().persist(new UserHistory(user, "Delete course post", Action.DELETE, Category.FORUM_POST, post));
		
		// if mails (for subscribed users) are still not sent, the forum post notification is removed
		ForumPostNotified forumPostNotified = getForumPostNotifiedDAO().find(post.getPostId());
		if (forumPostNotified != null && Boolean.FALSE.equals(forumPostNotified.getMailSended())) {
			getForumPostNotifiedDAO().remove(forumPostNotified);
		}
		
		ForumPost forumLastPost = getForumPostDAO().getLastForumTopicPostByCreationDate(courseTopic);
		recursiveSetLastPost(courseForum, forumLastPost, now);
		
		if (courseTopic.getTotalReplies() != null && courseTopic.getTotalReplies() > 1) {
			courseTopic.setTotalReplies(courseTopic.getTotalReplies() - 1);
		} else {
			courseTopic.setTotalReplies(0);
		}
		
		getForumDAO().recursiveDecreasePostsNumber(courseForum);
		
		// adjust first and last topic post if needed
		if (courseTopic.getFirstPost() == null || Boolean.TRUE.equals(courseTopic.getFirstPost().getLogicalDelete())) { 
			ForumPost firstTopicPost = getForumPostDAO().getFirstForumTopicPostByCreationDate(courseTopic.getTopicId());
			courseTopic.setFirstPost(firstTopicPost);
		}
		if (courseTopic.getLastPost() == null || Boolean.TRUE.equals(courseTopic.getLastPost().getLogicalDelete())) {
			ForumPost lastTopicPost = getForumPostDAO().getLastForumTopicPostByCreationDate(courseTopic);
			courseTopic.setLastPost(lastTopicPost);
		}
		
		
		// Changing the user last forum dates and number of post
		user.setLastActiveForumDate(now);
		user.setLastForumPostDate(now);
		user.setForumNumberOfPost(user.getForumNumberOfPost() - 1);
	}
	
	private CoursePostExt doUpdateCourseTranscriptionPost(
			String postSubject, 
			String postContent, 
			String transcription, 
			Integer volNum,
			String volLetExt, 
			String insertNum, 
			String insertLet,
			Integer folioNum, 
			String folioMod, 
			String folioRV,
			CoursePostExt postExt,
			Date now,
			User user) throws PersistenceException {
		
		// update post
		postExt.getPost().setSubject(postSubject);
		postExt.getPost().setText(postContent);
		postExt.getPost().setLastUpdate(now);
		postExt.getPost().setUpdater(user);

		// update topic
		postExt.getPost().getTopic().setLastUpdate(now);
		
		// update post extensions
		postExt.setTranscription(transcription);
		postExt.setVolNum(volNum);
		postExt.setVolLetExt(StringUtils.safeTrim(volLetExt));
		postExt.setInsertNum(StringUtils.safeTrim(insertNum));
		postExt.setInsertLet(StringUtils.safeTrim(insertLet));
		postExt.setFolioNum(folioNum);
		postExt.setFolioMod(StringUtils.safeTrim(folioMod));
		postExt.setFolioRV(CoursePostExt.RectoVerso.find(StringUtils.safeTrim(folioRV)));
		
		getUserHistoryDAO().persist(new UserHistory(user, "Update course post", Action.MODIFY, Category.FORUM_POST, postExt.getPost()));
		
		return postExt;
	}
	
	private List<UserRole> getCoursePeople(Course course) {
		// FIXME: when CoursePeople table will be managed we will have to retrieve
		// course people from that. Now we retrieve roles from UserRole table.
		List<UserRole> studentsRoles = getUserRoleDAO().filterUserRoles(Authority.STUDENTS);
		List<UserRole> teachersRoles = getUserRoleDAO().filterUserRoles(Authority.TEACHERS);
		List<UserRole> roles = new ArrayList<UserRole>();
		roles.addAll(studentsRoles);
		roles.addAll(teachersRoles);
		
		// we suppose if current user is an administrator it cannot also be a teacher.
		for (UserRole role : getCurrentUser().getUserRoles()) {
			if (role.getUserAuthority().getAuthority().equals(Authority.ADMINISTRATORS)) {
				roles.add(role);
				break;
			}
		}
		
		return roles;
	}
	
	private List<UserRole> getCourseTeachers(Course course) {
		// FIXME: when CoursePeople table will be managed we will have to retrieve
		// teachers from that. Now we retrieve roles from UserRole table.
		return getUserRoleDAO().filterUserRoles(Authority.TEACHERS);
	}
	
	private void increaseForumTopicsNumber(Forum forum) throws PersistenceException {
		if (forum == null) {
			return;
		}
		Integer oldTopicsNumber = forum.getTopicsNumber();
		forum.setTopicsNumber(oldTopicsNumber != null && oldTopicsNumber >= 0 ? oldTopicsNumber + 1 : 1);
	}
	
	private void increaseSubForumsNumber(Forum forum) throws PersistenceException {
		if (forum == null) {
			return;
		}
		Integer oldSubForumsNumber = forum.getSubForumsNumber();
		forum.setSubForumsNumber(oldSubForumsNumber != null && oldSubForumsNumber >= 0 ? oldSubForumsNumber + 1 : 1);
	}
	
	private boolean isStudent(User user) {
		for(UserRole role : user.getUserRoles()) {
			if (role.getUserAuthority().getAuthority().equals(Authority.STUDENTS)) {
				return true;
			}
		}
		return false;
	}
	
	/**
	 * This method updates the association between the provided check point and the last post of its topic.<br/>
	 * If the check point cannot be associated with a post (there are no post in the topic) it is removed.
	 * 
	 * @param checkPoint the check point
	 * @param byCreationDate if true the last post is considered by creation date, otherwise by last update
	 * @throws ApplicationThrowable
	 */
	private void updateCheckPointToLastTopicPost(CourseCheckPoint checkPoint, boolean byCreationDate) throws PersistenceException {
		CoursePostExt lastPost = getCoursePostExtDAO().getLastPostInTopic(checkPoint.getCheckPointPost().getPost().getTopic().getTopicId(), byCreationDate);
		if (lastPost == null) {
			getCourseCheckPointDAO().remove(checkPoint);
		} else {
			checkPoint.setCheckPointPost(lastPost);
		}
	}
	
	private void recursiveSetLastPost(Forum forum, ForumPost post, Date now) throws PersistenceException {
		if (Type.CATEGORY.equals(forum.getType())) {
			return;
		}
		
		if (post != null && (forum.getLastPost() == null || Boolean.TRUE.equals(forum.getLastPost().getLogicalDelete()) ||
				(!post.equals(forum.getLastPost()) && post.getDateCreated().after(forum.getLastPost().getDateCreated())))) {
			forum.setLastPost(post);
			forum.setLastUpdate(now);
			recursiveSetLastPost(forum.getForumParent(), post, now);
		} else if (post == null) {
			ForumPost forumLastPost = getForumPostDAO().getLastForumPostByCreationDate(forum);
			if (forumLastPost == null || !forumLastPost.equals(forum.getLastPost())) {
				forum.setLastPost(forumLastPost);
				forum.setLastUpdate(now);
				recursiveSetLastPost(forum.getForumParent(), forumLastPost, now);
			}
		}
	}
	
}
