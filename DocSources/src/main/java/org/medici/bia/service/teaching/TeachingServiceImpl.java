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
import org.medici.bia.domain.UserHistory;
import org.medici.bia.domain.UserAuthority.Authority;
import org.medici.bia.domain.UserHistory.Action;
import org.medici.bia.domain.UserHistory.Category;
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
			
			container.setTitle(topicTitle.trim() + " resources");
			container.setDescription("Resources of " + topicTitle.trim());
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
			container.setTopicsNumber(1);
			container.setSubForumsNumber(0);
			container.setLogicalDelete(Boolean.FALSE);

			getForumDAO().persist(container);
			getUserHistoryDAO().persist(new UserHistory(user, "Create new forum", Action.CREATE, Category.FORUM, container));

			container.setFullPath(container.getFullPath() + container.getForumId() + ".");
			getForumDAO().recursiveIncreaseSubForumsNumber(course.getForum());
			
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
			
			getForumTopicDAO().persist(courseTopic);
			
			// Course Topic Option creation
			CourseTopicOption courseTopicOption = new CourseTopicOption();
			courseTopicOption.setCourseTopic(courseTopic);
			courseTopicOption.setMode(mode);
			
			getCourseTopicOptionDAO().persist(courseTopicOption);
			
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
			CoursePostExt extendedPost = doAddCourseTranscrioptionPost(
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
			
			Forum container = new Forum();
			container.setDescription(description);
			container.setTitle(title);
			container.setDateCreated(now);
			container.setLastUpdate(now);
			container.setForumParent(coursesContainer);
			container.setHierarchyLevel(coursesContainer.getHierarchyLevel() + 1);
			container.setFullPath(coursesContainer.getFullPath());
			container.setDispositionOrder(0);
			container.setStatus(Forum.Status.ONLINE);
			container.setType(Forum.Type.FORUM);
			container.setSubType(Forum.SubType.COURSE);
			container.setPostsNumber(0);
			container.setTopicsNumber(0);
			container.setSubForumsNumber(0);
			container.setLogicalDelete(Boolean.FALSE);
			

			getForumDAO().persist(container);
			getUserHistoryDAO().persist(new UserHistory(user, "Create new course", Action.CREATE, Category.FORUM, container));

			container.setFullPath(coursesContainer.getFullPath() + container.getForumId() + ".");
			
			ForumOption forumOption = ForumUtils.getForumOptionForCourseForum(container);
			getForumOptionDAO().persist(forumOption);
			
			getForumDAO().recursiveIncreaseSubForumsNumber(coursesContainer);
			
			Course course = new Course();
			course.setActive(Boolean.TRUE);
			course.setForum(container);
			
			getCourseDAO().persist(course);
			
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
	public void deleteCourseFragmentTopic(Integer topicId) throws ApplicationThrowable {
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

			getForumPostDAO().deleteAllForumTopicPosts(courseTopic.getTopicId());
			getForumDAO().recursiveDecreasePostsNumber(forum, courseTopic.getTotalReplies());
			// update only the topics number of the course fragment forum container 
			forum.setTopicsNumber(forum.getTopicsNumber() - 1);
			
			// update forum last post
			if (forum.getLastPost() != null && forum.getLastPost().getTopic().equals(courseTopic)) {
				CourseTopicOption option = getCourseTopicOptionDAO().determineExtendedTopicWithLastPost(forum.getForumId());
				if (option != null) {
					recursiveSetLastPost(forum, option.getCourseTopic().getLastPost(), now);
				} else {
					forum.setLastPost(null);
				}
			}
			
			user.setLastActiveForumDate(now);
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
		Forum courseForum;
		
			try {
				post = getForumPostDAO().find(postId);
				courseTopic = post.getTopic();
				courseForum = post.getForum();
			} catch (Throwable th) {
				throw new ApplicationThrowable(th);
			}
			
		checkCourseTopicConsistency(post.getTopic().getTopicId(), mode);
		
		Date now = new Date();
		User user = getCurrentUser();
		
		try {
			doDeleteCourseTranscriptionPost(post, now, user);
			
			ForumPost firstTopicPost = null;
			ForumPost lastTopicPost = null;
			ForumPost lastForumPost = null;
			switch (mode) {
				case I:
					if (courseTopic.getFirstPost() == null || courseTopic.getFirstPost().getPostId().equals(postId)) { 
						firstTopicPost = getForumPostDAO().getFirstForumTopicPostByLastUpdate(courseTopic.getTopicId());
					}
					if (courseTopic.getLastPost() == null || courseTopic.getLastPost().getPostId().equals(postId)) {
						lastTopicPost = getForumPostDAO().getLastForumTopicPostByLastUpdate(courseTopic);
					}
					if (courseForum.getLastPost() == null || courseForum.getLastPost().getPostId().equals(postId)) {
						CoursePostExt lastPostOfTopic = getCoursePostExtDAO().getLastPostInTopic(courseTopic.getTopicId(), false);
						lastForumPost = getForumPostDAO().getLastForumPostByCreationDate(courseForum);
						if (lastPostOfTopic != null && lastPostOfTopic.getPost().getLastUpdate().after(lastForumPost.getDateCreated())) {
							lastForumPost = lastPostOfTopic.getPost();
						}
					}
					CourseCheckPoint checkPoint = getLastCheckPoint(courseTopic.getTopicId());
					if (checkPoint != null && checkPoint.getCheckPointPost().getPost().getPostId().equals(postId)) {
						updateCheckPointToLastTopicPost(checkPoint, false);
					} else if (checkPoint == null) {
						// something is bad...we create new check point to fix the course topic status
						CoursePostExt lastPost = getLastPostOfTopic(courseTopic.getTopicId(), false);
						addCourseCheckPoint(courseTopic.getTopicId(), lastPost, null);
					}
					break;
				case R:
					if (courseTopic.getFirstPost() == null || courseTopic.getFirstPost().getPostId().equals(postId)) { 
						firstTopicPost = getForumPostDAO().getFirstForumTopicPostByCreationDate(courseTopic.getTopicId());
					}
					if (courseTopic.getLastPost() == null || courseTopic.getLastPost().getPostId().equals(postId)) {
						lastTopicPost = getForumPostDAO().getLastForumTopicPostByCreationDate(courseTopic);
					}
					if (courseForum.getLastPost() == null || courseForum.getLastPost().getPostId().equals(postId)) {
						lastForumPost = getForumPostDAO().getLastForumPostByCreationDate(courseForum);
					}
					break;
				case C:
					if (courseTopic.getFirstPost() == null || courseTopic.getFirstPost().getPostId().equals(postId)) { 
						firstTopicPost = getForumPostDAO().getFirstForumTopicPostByCreationDate(courseTopic.getTopicId());
					}
					if (courseTopic.getLastPost() == null || courseTopic.getLastPost().getPostId().equals(postId)) {
						lastTopicPost = getForumPostDAO().getLastForumTopicPostByCreationDate(courseTopic);
					}
					if (courseForum.getLastPost() == null || courseForum.getLastPost().getPostId().equals(postId)) {
						lastForumPost = getForumPostDAO().getLastForumPostByCreationDate(courseForum);
					}
					break;
			}
			if (firstTopicPost != null) {
				courseTopic.setFirstPost(firstTopicPost);
			}
			if (lastTopicPost != null) {
				courseTopic.setLastPost(lastTopicPost);
			}
			if (lastForumPost != null) {
				recursiveSetLastPost(courseForum, lastForumPost, now);
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
	public boolean doActivateCourse(Integer courseId) throws ApplicationThrowable {
		try {
			Course course = getCourseDAO().find(courseId);
			if (course == null) {
				return false;
			}
			course.setActive(true);
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
			// Not found topics are considered general discussions
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
				throw new ApplicationThrowable(ApplicationError.RECORD_NOT_FOUND_ERROR, "Unable to find course topic [" + topicId + "]");
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
				throw new ApplicationThrowable(ApplicationError.NULLPOINTER_ERROR);
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
			returnMap.put("MOST RECENT COURSE QUESTIONS", getCourseTopicOptionDAO().getMostRecentCourseQuestions(numberOfQuestions, account));
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
	public boolean isForumInActiveCourse(Integer forumId) throws ApplicationThrowable {
		try {
			return getForumDAO().isInActiveCourse(forumId);
		} catch (Throwable th) {
			throw new ApplicationThrowable(th);
		}
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean isInActiveCourse(Integer entryId) throws ApplicationThrowable {
		try {
			return getCourseDAO().isInActiveCourse(entryId);
		} catch (Throwable th) {
			throw new ApplicationThrowable(th);
		}
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean isInCourse(Integer entryId) throws ApplicationThrowable {
		try {
			return getCourseDAO().isInCourse(entryId);
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
			String ipAddress) throws ApplicationThrowable {
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
							|| !annotation.getHeight().equals(viewAnnotation.getHeight())));
				
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
					
					getAnnotationDAO().persist(annotation);
					
					Forum forum = getForumDAO().find(forumContainerId);
					image = getImageDAO().find(imageId);
					
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
					
					topicAnnotation.setAnnotation(annotation);
					getForumTopicDAO().persist(topicAnnotation);
					
					annotation.setForumTopic(topicAnnotation);
					returnMap.put(annotation, topicAnnotation.getTopicId());
					
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
					topicAnnotation.setTotalReplies(1);
					
					forum.setLastPost(annotationPost);
					forum.setLastUpdate(operationDate);
					
					// we increase the course fragment resources topics number
					// we do not have to increase parent forum topics number
					forum.setTopicsNumber(forum.getTopicsNumber() + 1);
					
					// we increase the number of forum posts number
					getForumDAO().recursiveIncreasePostsNumber(forum);

					if (user.getForumTopicSubscription().equals(Boolean.TRUE)) {
						ForumTopicWatch forumTopicWatch = new ForumTopicWatch(topicAnnotation, user);
						getForumTopicWatchDAO().persist(forumTopicWatch);
					}
					
					// we have to add a course topic option
					CourseTopicOption option = new CourseTopicOption();
					option.setCourseTopic(topicAnnotation);
					option.setMode(CourseTopicMode.Q);
					
					getCourseTopicOptionDAO().persist(option);
				}
			}
			
			// We remove the old persisted annotations that are still in the list
			for(Annotation toRemoveAnnotation : persistedAnnotations) {
				if (toRemoveAnnotation.getForumTopic() != null) {
					// RR: It should not be possible to remove an annotation associated to a topic
					ForumTopic annotationTopic = toRemoveAnnotation.getForumTopic();
					annotationTopic.setLogicalDelete(Boolean.TRUE);

					getForumPostDAO().deleteAllForumTopicPosts(annotationTopic.getTopicId());
					Forum forum = annotationTopic.getForum();
					ForumPost lastPost = getForumPostDAO().getLastForumPostByLastUpdate(forum);
					recursiveSetLastPost(forum, lastPost, operationDate);
					getForumDAO().recursiveDecreasePostsNumber(forum, annotationTopic.getTotalReplies());
					getForumDAO().recursiveDecreaseTopicsNumber(forum);
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
		
		checkCourseTopicConsistency(postExt.getPost().getTopic().getTopicId(), mode);
		
		doUpdateCourseTranscriptionPost(postSubject, postContent, transcription, volNum, volLetExt, insertNum, insertLet, folioNum, folioMod, folioRV, postExt, now, user);
		
		switch (mode) {
			case I:
				postExt.getPost().getTopic().setLastPost(postExt.getPost());
				// in the incremental transcription every updated post become the last post
				recursiveSetLastPost(postExt.getPost().getForum(), postExt.getPost(), now);
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
	
	private CoursePostExt doAddCourseTranscrioptionPost(
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
		
		if (courseTopic.getTotalReplies() == null || courseTopic.getTotalReplies() <= 0) {
			courseTopic.setFirstPost(post);
			courseTopic.setTotalReplies(1);
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
		
		return coursePostExt;
	}
	
	private void doDeleteCourseTranscriptionPost(ForumPost post, Date now, User user) throws PersistenceException {
		Forum courseForum = post.getForum();
		ForumTopic courseTopic = post.getTopic();
		post.setLogicalDelete(Boolean.TRUE);
		post.setLastUpdate(now);
		post.setUpdater(user);
		
		// if mails (for subscribed users) are still not sent, the forum post notification is removed
		ForumPostNotified forumPostNotified = getForumPostNotifiedDAO().getForumPostNotifiedByPost(post.getPostId());
		if (forumPostNotified != null && Boolean.FALSE.equals(forumPostNotified.getMailSended())) {
			getForumPostNotifiedDAO().remove(forumPostNotified);
		}
		
		ForumPost lastPost = getForumPostDAO().getLastForumTopicPostByCreationDate(courseTopic);
		courseTopic.setLastPost(lastPost);
		if (courseTopic.getTotalReplies() != null && courseTopic.getTotalReplies() > 1) {
			courseTopic.setTotalReplies(courseTopic.getTotalReplies() - 1);
		} else {
			courseTopic.setTotalReplies(0);
		}
		
		getForumDAO().recursiveDecreasePostsNumber(courseForum);
		
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
		
		return postExt;
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
		
		forum.setLastPost(post);
		forum.setLastUpdate(now);
		
		recursiveSetLastPost(forum.getForumParent(), post, now);
	}
	
}
