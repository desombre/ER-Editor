package view.er_objects;

import model.ERObject;

import java.awt.*;

public abstract class ERObjectView<T extends ERObject> {



    private T erObject;

    private boolean selected = false;
    private Rectangle bounds = new Rectangle();

    public ERObjectView(T erObject) {
        this.erObject = erObject;
    }

    public ERObjectView() {
    }

    public T getErObject() {
        return erObject;
    }

    public Rectangle getBounds() {
        return bounds;
    }

    public void setBounds(Rectangle bounds) {
        this.bounds = bounds;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    public abstract void paint(Graphics2D g);

    public void select() {
        setSelected(true);
    }

    public void setLocation(int x, int y) {
        getBounds().x = x;
        getBounds().y = y;
    }

    public void setLocation(Point p) {
        getBounds().x = p.x;
        getBounds().y = p.y;
    }

    public boolean isAffectedBySelectionRectangle(Rectangle r) {
        return getBounds().intersects(r);
    }

    public boolean isAffectedByTouch(Point p) {
        return getBounds().contains(p);
    }

    public boolean isSelected() {
        return selected;
    }

    public void deselect() {
        setSelected(false);
    }

    public Point getLocation() {
        return getBounds().getLocation();
    }


    public void setErObject(T erObject){
        this.erObject = erObject;
    }


}
