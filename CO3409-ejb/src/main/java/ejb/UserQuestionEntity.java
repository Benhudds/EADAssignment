/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import org.eclipse.persistence.jpa.jpql.parser.DateTime;

@XmlRootElement
@Entity
public class UserQuestionEntity implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private Long userID;
    private Long questionID;
    private double answer;
    private boolean correct;
    @Temporal(TemporalType.TIMESTAMP)
    private Date attempted;

    public Date getAttempted() {
        return attempted;
    }

    @XmlElement
    public void setAttempted(Date attempted) {
        this.attempted = attempted;
    }

    public Long getUserID() {
        return userID;
    }

    @XmlElement
    public void setUserID(Long userID) {
        this.userID = userID;
    }

    public Long getQuestionID() {
        return questionID;
    }

    @XmlElement
    public void setQuestionID(Long questionID) {
        this.questionID = questionID;
    }

    public Long getId() {
        return id;
    }

    @XmlElement
    public void setId(Long id) {
        this.id = id;
    }

    public double getAnswer() {
        return answer;
    }

    @XmlElement
    public void setAnswer(double answer) {
        this.answer = answer;
    }

    public boolean isCorrect() {
        return correct;
    }

    @XmlElement
    public void setCorrect(boolean correct) {
        this.correct = correct;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof UserQuestionEntity)) {
            return false;
        }
        UserQuestionEntity other = (UserQuestionEntity) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ejb.UserQuestionEntity[ id=" + id + " ]";
    }

}
