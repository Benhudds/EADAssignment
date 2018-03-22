/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package web.questions;

import ejb.QuestionEntity;
import imagehelpers.ImageHelper;
import java.io.Serializable;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.concurrent.ThreadLocalRandom;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElementRef;
import javax.xml.bind.annotation.XmlRootElement;
import questions.CircleQuestions;
import questions.EllipseQuestions;
import questions.LineQuestions;
import questions.SquareQuestions;
import questions.TriangleQuestions;
import shapes.Circle;
import shapes.Ellipse;
import shapes.Line;
import shapes.Shape;
import shapes.Square;
import shapes.Triangle;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class Question implements Serializable {

    @XmlElementRef
    private Shape shape;
    private String question;
    private double answer;

    public Shape getShape() {
        return shape;
    }

    public void setShape(Shape shape) {
        this.shape = shape;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public double getAnswer() {
        DecimalFormat df = new DecimalFormat("#.##");
        df.setRoundingMode(RoundingMode.HALF_UP);
        return Double.valueOf(df.format(answer));
    }

    public void setAnswer(double answer) {
        this.answer = answer;
    }

    public Question() {
    }

    public Question(Shape shape, String question, double answer) {
        this.shape = shape;
        this.question = question;
        this.answer = answer;
    }

    private static int getNewNonZeroRandom(int min, int max) {
        int ran = 0;
        do {
            ran = ThreadLocalRandom.current().nextInt(min, max);
        } while (ran == 0);

        return ran;
    }

    private static int getNewNonZeroRandom(int max) {
        return getNewNonZeroRandom(1, max);
    }

    private static int getNewRandom(int min, int max) {
        return ThreadLocalRandom.current().nextInt(min, max);
    }

    private static int getNewRandom(int max) {
        return getNewRandom(0, max);
    }

    public static QuestionEntity generateQuestion() {
        int ran = getNewRandom(5);
        QuestionEntity returnQuestion = new QuestionEntity();
        String question = "Calculate the ";
        // No questions for points as they would make no sense.
        switch (ran) {
            case 0:
                // Line
                Line line = new Line();
                ran = getNewRandom(LineQuestions.values().length);
                LineQuestions lineEnum = LineQuestions.values()[ran];
                question = question + LineQuestions.values()[ran];
                question = question + " of a line with points (";
                ran = getNewRandom(-ImageHelper.SCALE, ImageHelper.SCALE);
                line.setX(ran);
                question = question + ran + ",";
                ran = getNewRandom(-ImageHelper.SCALE, ImageHelper.SCALE);
                line.setY(ran);
                question = question + ran + ") and (";
                ran = getNewRandom(-ImageHelper.SCALE, ImageHelper.SCALE);
                line.setX2(ran);
                question = question + ran + ",";
                ran = getNewRandom(-ImageHelper.SCALE, ImageHelper.SCALE);
                line.setY2(ran);
                question = question + ran + ")";
                returnQuestion.setShape(line);
                returnQuestion.setQuestion(question);
                returnQuestion.setAnswer(line.getAnswer(lineEnum));
                break;
            case 1:
                // Square
                Square square = new Square();
                ran = getNewRandom(SquareQuestions.values().length);
                SquareQuestions squareEnum = SquareQuestions.values()[ran];
                question = question + SquareQuestions.values()[ran];
                question = question + " of a square with size ";
                ran = getNewNonZeroRandom(ImageHelper.SCALE);
                square.setSize(ran);
                question = question + ran;
                returnQuestion.setShape(square);
                returnQuestion.setQuestion(question);
                returnQuestion.setAnswer(square.getAnswer(squareEnum));
                break;
            case 2:
                // Triangle
                Triangle triangle = new Triangle();
                // Repeat until the triangle is valid
                do {
                    ran = getNewRandom(TriangleQuestions.values().length);
                    TriangleQuestions triangleEnum = TriangleQuestions.values()[ran];
                    question = "Calculate the ";
                    question = question + TriangleQuestions.values()[ran];
                    question = question + " of a triangle with points (";
                    ran = getNewRandom(-ImageHelper.SCALE, ImageHelper.SCALE);
                    triangle.setX(ran);
                    question = question + ran + ",";
                    ran = getNewRandom(-ImageHelper.SCALE, ImageHelper.SCALE);
                    triangle.setY(ran);
                    question = question + ran + ") and (";
                    ran = getNewRandom(-ImageHelper.SCALE, ImageHelper.SCALE);
                    triangle.setX2(ran);
                    question = question + ran + ",";
                    ran = getNewRandom(-ImageHelper.SCALE, ImageHelper.SCALE);
                    triangle.setY2(ran);
                    question = question + ran + ") and (";
                    ran = getNewRandom(-ImageHelper.SCALE, ImageHelper.SCALE);
                    triangle.setX3(ran);
                    question = question + ran + ",";
                    ran = getNewRandom(-ImageHelper.SCALE, ImageHelper.SCALE);
                    triangle.setY3(ran);
                    question = question + ran + ")";
                    returnQuestion.setShape(triangle);
                    returnQuestion.setQuestion(question);
                    returnQuestion.setAnswer(triangle.getAnswer(triangleEnum));
                } while (!"".equals(triangle.validate()));
                break;
            case 3:
                // Circle
                Circle circle = new Circle();
                ran = getNewRandom(CircleQuestions.values().length);
                CircleQuestions circleEnum = CircleQuestions.values()[ran];
                question = question + CircleQuestions.values()[ran];
                question = question + " of a circle with radius ";
                ran = getNewNonZeroRandom(ImageHelper.SCALE);
                circle.setRadius(ran);
                question = question + ran;
                returnQuestion.setShape(circle);
                returnQuestion.setQuestion(question);
                returnQuestion.setAnswer(circle.getAnswer(circleEnum));
                break;
            case 4:
                // Ellipse
                Ellipse ellipse = new Ellipse();
                ran = getNewRandom(EllipseQuestions.values().length);
                EllipseQuestions ellipseEnum = EllipseQuestions.values()[ran];
                question = question + EllipseQuestions.values()[ran];
                question = question + " of an ellipse with x radius ";
                ran = getNewNonZeroRandom(ImageHelper.SCALE);
                ellipse.setXRadius(ran);
                question = question + ran + " and y radius ";
                ran = getNewNonZeroRandom(ImageHelper.SCALE);
                ellipse.setYRadius(ran);
                question = question + ran;
                returnQuestion.setShape(ellipse);
                returnQuestion.setQuestion(question);
                returnQuestion.setAnswer(ellipse.getAnswer(ellipseEnum));
                break;
        }

        return returnQuestion;
    }
    
    public static boolean TestAnswer(double ans1, double ans2) {
        DecimalFormat df = new DecimalFormat("#.##");
        df.setRoundingMode(RoundingMode.HALF_UP);
        return df.format(ans1).equals(df.format(ans2));
    }
}
