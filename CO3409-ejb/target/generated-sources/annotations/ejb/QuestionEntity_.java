package ejb;

import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import shapes.Shape;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2018-03-20T17:25:06")
@StaticMetamodel(QuestionEntity.class)
public class QuestionEntity_ { 

    public static volatile SingularAttribute<QuestionEntity, String> question;
    public static volatile SingularAttribute<QuestionEntity, Double> answer;
    public static volatile SingularAttribute<QuestionEntity, Shape> shape;
    public static volatile SingularAttribute<QuestionEntity, Long> id;

}