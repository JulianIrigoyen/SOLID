package com.plottwist.solid;

/**
 * You should be able to substitute a subclass
 * for a base class. Always.
 * If you violate it, it will result in incorrect code
 * through inheritance
 */
class Lishkov {
}

class Rectangle{
    protected int width, height;

    public Rectangle() {
    }

    public Rectangle(int width, int height) {
        this.width = width;
        this.height = height;
    }

    public int getWidth() {
        return width;
    }

    public int getArea(){
        return width*height;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    @Override
    public String toString() {
        return "Rectangle{" +
                "width=" + width +
                ", height=" + height +
                '}';
    }

    /**
     * Example Solutions ->
     * Check if Rectangle is a Square or
     * Use Factory DP
     */
    public boolean isSquare(){
        return width==height;
    }
}
class RectangleFactory{
    public static Rectangle newRectangle(int width, int height){
        return new Rectangle(width, height);
    }
    public static Rectangle newSquare(int side){
        return new Rectangle(side, side);
    }
}

/**
 * Now say you want to use inheritance to create
 * the Square class (a Rectangle where width == height)
 */
class Square extends Rectangle{
    public Square(){}

    public Square(int size){
        width = height = size;
    }

    /**
     * The two following setters violates the Lishkov Substitution Principle
     * @param width
     */
    @Override
    public void setWidth(int width) {
        super.setWidth(width);
        super.setHeight(width);
    }

    @Override
    public void setHeight(int height) {
        super.setHeight(height);
        super.setWidth(height);
    }
}
class DemoIII{
    static void useIt(Rectangle r){
        int width = r.getWidth();
        r.setHeight(10);
        //area should then be width * 10
        System.out.println("Expected area of " + (width * 10) + ". Instead got " + r.getArea());
    }

    public static void main(String[] args) {
        Rectangle rc = new Rectangle(2, 3);
        useIt(rc);
        //Proof
        Rectangle sq = new Square();
        sq.setWidth(5);
        useIt(sq);
    }
}
