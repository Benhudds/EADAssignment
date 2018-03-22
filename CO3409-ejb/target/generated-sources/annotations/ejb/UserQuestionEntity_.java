package ejb;

import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2018-03-22T09:05:41")
@StaticMetamodel(UserQuestionEntity.class)
public class UserQuestionEntity_ { 

    public static volatile SingularAttribute<UserQuestionEntity, Long> questionID;
    public static volatile SingularAttribute<UserQuestionEntity, Double> answer;
    public static volatile SingularAttribute<UserQuestionEntity, Boolean> correct;
    public static volatile SingularAttribute<UserQuestionEntity, Long> id;
    public static volatile SingularAttribute<UserQuestionEntity, Date> attempted;
    public static volatile SingularAttribute<UserQuestionEntity, Long> userID;

}