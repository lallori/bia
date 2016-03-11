/*
 * UserMessage.java
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
 */
package org.medici.bia.domain;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * UserMessage entity.
 *
 * @author Lorenzo Pasquinelli (<a href=mailto:l.pasquinelli@gmail.com>l.pasquinelli@gmail.com</a>)
 * @author Matteo Doni (<a href=mailto:donimatteo@gmail.com>donimatteo@gmail.com</a>)
 */
@Entity
@Table ( name = "\"tblUserMessage\"" ) 
public class UserMessage implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 6277942095026283497L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column (name="\"messageId\"", length=10, nullable=false)
	private Integer messageId;
	@Temporal(TemporalType.TIMESTAMP)
	@Column (name="\"messageSendedDate\"")
	private Date sendedDate;
	@Temporal(TemporalType.TIMESTAMP)
	@Column (name="\"messageReadedDate\"")
	private Date readedDate;
	@Column (name="\"messageSubject\"", length=2000)
	private String subject;
	@Column (name="\"messageBody\"", length=4000)
	private String body;
	@Column (name="\"sender\"", length=100)
	private String sender;
	@Column (name="\"recipient\"", length=100)
	private String recipient;
	@OneToOne( fetch=FetchType.LAZY )
	@JoinColumn(name="parentId")
	private UserMessage parentMessage;
	@Column (name="\"recipientStatus\"", length=10)
	private RecipientStatus recipientStatus;
	//MD: User that has the message in his inbox or outbox
	@ManyToOne (fetch=FetchType.LAZY)
	@JoinColumn(name="\"account\"")
	private User user;

	/**
	 * Default constructor
	 */
	public UserMessage(){
		super();
	}
	
	/**
	 * 
	 * @param messageId
	 */
	public UserMessage(Integer messageId){
		super();
		setMessageId(messageId);
	}
	
	/**
	 * @return the messageId
	 */
	public Integer getMessageId() {
		return messageId;
	}


	/**
	 * @param messageId the messageId to set
	 */
	public void setMessageId(Integer messageId) {
		this.messageId = messageId;
	}


	/**
	 * @return the sendedDate
	 */
	public Date getSendedDate() {
		return sendedDate;
	}


	/**
	 * @param sendedDate the sendedDate to set
	 */
	public void setSendedDate(Date sendedDate) {
		this.sendedDate = sendedDate;
	}


	/**
	 * @return the readedDate
	 */
	public Date getReadedDate() {
		return readedDate;
	}


	/**
	 * @param readedDate the readedDate to set
	 */
	public void setReadedDate(Date readedDate) {
		this.readedDate = readedDate;
	}


	/**
	 * @return the subject
	 */
	public String getSubject() {
		return subject;
	}


	/**
	 * @param subject the subject to set
	 */
	public void setSubject(String subject) {
		this.subject = subject;
	}


	/**
	 * @return the body
	 */
	public String getBody() {
		return body;
	}


	/**
	 * @param body the body to set
	 */
	public void setBody(String body) {
		this.body = body;
	}


	/**
	 * @return the sender
	 */
	public String getSender() {
		return sender;
	}


	/**
	 * @param sender the sender to set
	 */
	public void setSender(String sender) {
		this.sender = sender;
	}


	/**
	 * @return the recipient
	 */
	public String getRecipient() {
		return recipient;
	}


	/**
	 * @param recipient the recipient to set
	 */
	public void setRecipient(String recipient) {
		this.recipient = recipient;
	}


	/**
	 * @return the parentMessage
	 */
	public UserMessage getParentMessage() {
		return parentMessage;
	}


	/**
	 * @param parentMessage the parentMessage to set
	 */
	public void setParentMessage(UserMessage parentMessage) {
		this.parentMessage = parentMessage;
	}


	/**
	 * @return the recipientStatus
	 */
	public RecipientStatus getRecipientStatus() {
		return recipientStatus;
	}


	/**
	 * @param recipientStatus the recipientStatus to set
	 */
	public void setRecipientStatus(RecipientStatus recipientStatus) {
		this.recipientStatus = recipientStatus;
	}


	/**
	 * @return the user
	 */
	public User getUser() {
		return user;
	}


	/**
	 * @param user the user to set
	 */
	public void setUser(User user) {
		this.user = user;
	}


	/**
	 * 
	 * @author Lorenzo Pasquinelli (<a href=mailto:l.pasquinelli@gmail.com>l.pasquinelli@gmail.com</a>)
	 *
	 */
	public static enum RecipientStatus {
		NOT_READ(null), READ("READ"), DELETED("DELETED"), DRAFT("DRAFT");
		
		private final String status;

	    private RecipientStatus(String value) {
	    	status = value;
	    }

	    @Override
	    public String toString(){
	        return status;
	    }
	}

	/**
	 * 
	 * @author Lorenzo Pasquinelli (<a href=mailto:l.pasquinelli@gmail.com>l.pasquinelli@gmail.com</a>)
	 *
	 */
	public static enum UserMessageCategory {
		INBOX("INBOX"), OUTBOX("OUTBOX"), DRAFT("DRAFT");
		
		private final String category;

	    private UserMessageCategory(String value) {
	    	category = value;
	    }

	    @Override
	    public String toString(){
	        return category;
	    }
	}
}
