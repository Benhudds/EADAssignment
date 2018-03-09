/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package web.marks;

import ejb.UserQuestionEntity;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElementRef;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class Marks implements Serializable {

    private int totalAnswered = 0;
    private int totalCorrect = 0;
    
    @XmlElementRef
    private List<UserQuestionEntity> marks = new ArrayList<UserQuestionEntity>();

    public List<UserQuestionEntity> getMarks() {
        return marks;
    }

    public void setMarks(List<UserQuestionEntity> marks) {
        this.marks = marks;
    }
    
    public void addMark(UserQuestionEntity ue) {
        this.marks.add(ue);
    }
    
    public int getTotalAnswered() {
        return this.totalAnswered;
    }
    
    public void incTotalAnswered() {
        this.totalAnswered++;
    }
    
    public int getTotalCorrect() {
        return this.totalCorrect;
    }
    
    public void incTotalCorrect() {
        this.totalCorrect++;
    }
}
