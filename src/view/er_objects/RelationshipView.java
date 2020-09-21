package view.er_objects;

import model.Relationship;

import java.awt.*;
import java.awt.font.FontRenderContext;
import java.awt.font.TextLayout;
import java.awt.geom.Rectangle2D;

public class RelationshipView extends ERObjectView<Relationship> {


    public RelationshipView() {
        super();
        Relationship relationship = new Relationship();
        relationship.setView(this);
        super.setErObject(relationship);
        getBounds().x = 400;
        getBounds().y = 200;
        getBounds().width = 200;
        getBounds().height = 80;

    }

    @Override
    public void paint(Graphics2D g) {
        getBounds().width = getErObject().isWeak() ? 210 : 200;
        getBounds().height = getErObject().isWeak() ? 90 : 80;

        if (isSelected())
            g.setColor(new Color(0, 150, 180));
        else
            g.setColor(Color.BLACK);

        int e1shiftX = (int) ((getErObject().getFirstEntity().getView().getBounds().getCenterX() - getBounds().getCenterX()) * 0.05f);
        int e2shiftX = (int) ((getErObject().getSecondEntity().getView().getBounds().getCenterX() - getBounds().getCenterX()) * 0.05f);
        int e1shiftY = (int) ((getErObject().getFirstEntity().getView().getBounds().getCenterY() - getBounds().getCenterY()) * 0.05f);
        int e2shiftY = (int) ((getErObject().getSecondEntity().getView().getBounds().getCenterY() - getBounds().getCenterY()) * 0.05f);

        if (e1shiftX > getErObject().getFirstEntity().getView().getBounds().width / 3)
            e1shiftX = getErObject().getFirstEntity().getView().getBounds().width / 3;
        if (e1shiftX < -getErObject().getFirstEntity().getView().getBounds().width / 3)
            e1shiftX = -getErObject().getFirstEntity().getView().getBounds().width / 3;

        if (e2shiftX > getErObject().getSecondEntity().getView().getBounds().width / 3)
            e2shiftX = getErObject().getSecondEntity().getView().getBounds().width / 3;
        if (e2shiftX < -getErObject().getSecondEntity().getView().getBounds().width / 3)
            e2shiftX = -getErObject().getSecondEntity().getView().getBounds().width / 3;

        if (e1shiftY > getErObject().getFirstEntity().getView().getBounds().height / 3)
            e1shiftY = getErObject().getFirstEntity().getView().getBounds().height / 3;
        if (e1shiftY < -getErObject().getFirstEntity().getView().getBounds().height / 3)
            e1shiftY = -getErObject().getFirstEntity().getView().getBounds().height / 3;

        if (e2shiftY > getErObject().getSecondEntity().getView().getBounds().height / 3)
            e2shiftY = getErObject().getSecondEntity().getView().getBounds().height / 3;
        if (e2shiftY < -getErObject().getSecondEntity().getView().getBounds().height / 3)
            e2shiftY = -getErObject().getSecondEntity().getView().getBounds().height / 3;

        e1shiftX *= -1;
        e2shiftX *= -1;
        e1shiftY *= -1;
        e2shiftY *= -1;

        if (getErObject().getFirstEntity().getView().getBounds().getCenterX() < getErObject().getSecondEntity().getView().getBounds().getCenterX())
        {
            if (Math.abs(getErObject().getFirstEntity().getView().getBounds().getCenterY() - getBounds().getCenterY()) < 100 && getErObject().getFirstEntity().getView().getBounds().getMaxX() + 10 < getBounds().x)
            {
                g.drawLine(getBounds().x, (int) getBounds().getCenterY(), getBounds().x + (int) ((getErObject().getFirstEntity().getView().getBounds().getMaxX() - getBounds().x) / 2.0f),
                        (int) getBounds().getCenterY());
                g.drawLine(getBounds().x + (int) ((getErObject().getFirstEntity().getView().getBounds().getMaxX() - getBounds().x) / 2.0f), (int) getBounds().getCenterY(),
                        getBounds().x + (int) ((getErObject().getFirstEntity().getView().getBounds().getMaxX() - getBounds().x) / 2.0f), (int) getErObject().getFirstEntity().getView().getBounds().getCenterY() + e1shiftY);
                g.drawLine(getBounds().x + (int) ((getErObject().getFirstEntity().getView().getBounds().getMaxX() - getBounds().x) / 2.0f), (int) getErObject().getFirstEntity().getView().getBounds().getCenterY() + e1shiftY,
                        getErObject().getFirstEntity().getView().getBounds().x + getErObject().getFirstEntity().getView().getBounds().width, (int) getErObject().getFirstEntity().getView().getBounds().getCenterY() + e1shiftY);
            }
            else if (getErObject().getFirstEntity().getView().getBounds().getCenterX() > getBounds().x - 50)
            {
                g.drawLine(getBounds().x, (int) getBounds().getCenterY(), getBounds().x - 50, (int) getBounds().getCenterY());
                g.drawLine(getBounds().x - 50, (int) getBounds().getCenterY(), getBounds().x - 50,
                        (int) (getBounds().getCenterY() + (getErObject().getFirstEntity().getView().getBounds().getCenterY() - getBounds().getCenterY()) / 2.0f));
                g.drawLine(getBounds().x - 50, (int) (getBounds().getCenterY() + (getErObject().getFirstEntity().getView().getBounds().getCenterY() - getBounds().getCenterY()) / 2.0f),
                        (int) getErObject().getFirstEntity().getView().getBounds().getCenterX() + e1shiftX,
                        (int) (getBounds().getCenterY() + (getErObject().getFirstEntity().getView().getBounds().getCenterY() - getBounds().getCenterY()) / 2.0f));
                g.drawLine((int) getErObject().getFirstEntity().getView().getBounds().getCenterX() + e1shiftX,
                        (int) (getBounds().getCenterY() + (getErObject().getFirstEntity().getView().getBounds().getCenterY() - getBounds().getCenterY()) / 2.0f),
                        (int) getErObject().getFirstEntity().getView().getBounds().getCenterX() + e1shiftX,
                        getErObject().getFirstEntity().getView().getBounds().y + ((getErObject().getFirstEntity().getView().getBounds().getCenterY() > getBounds().getCenterY()) ? getErObject().getFirstEntity().getView().getBounds().height : 0));
            }
            else
            {
                g.drawLine(getBounds().x, (int) getBounds().getCenterY(), (int) getErObject().getFirstEntity().getView().getBounds().getCenterX() + e1shiftX, (int) getBounds().getCenterY());
                g.drawLine((int) getErObject().getFirstEntity().getView().getBounds().getCenterX() + e1shiftX, (int) getBounds().getCenterY(), (int) getErObject().getFirstEntity().getView().getBounds().getCenterX() + e1shiftX,
                        getErObject().getFirstEntity().getView().getBounds().y + ((getErObject().getFirstEntity().getView().getBounds().getCenterY() > getBounds().getCenterY()) ? getErObject().getFirstEntity().getView().getBounds().height : 0));
            }
            if (Math.abs(getErObject().getSecondEntity().getView().getBounds().getCenterY() - getBounds().getCenterY()) < 100 && getErObject().getSecondEntity().getView().getBounds().x - 10 > getBounds().getMaxX())
            {
                g.drawLine(getBounds().x + getBounds().width, (int) getBounds().getCenterY(),
                        getBounds().x + getBounds().width + (int) ((getErObject().getSecondEntity().getView().getBounds().x - getBounds().getMaxX()) / 2.0f), (int) getBounds().getCenterY());
                g.drawLine(getBounds().x + getBounds().width + (int) ((getErObject().getSecondEntity().getView().getBounds().x - getBounds().getMaxX()) / 2.0f), (int) getBounds().getCenterY(),
                        getBounds().x + getBounds().width + (int) ((getErObject().getSecondEntity().getView().getBounds().x - getBounds().getMaxX()) / 2.0f), (int) getErObject().getSecondEntity().getView().getBounds().getCenterY() + e2shiftY);
                g.drawLine(getBounds().x + getBounds().width + (int) ((getErObject().getSecondEntity().getView().getBounds().x - getBounds().getMaxX()) / 2.0f),
                        (int) getErObject().getSecondEntity().getView().getBounds().getCenterY() + e2shiftY, getErObject().getSecondEntity().getView().getBounds().x, (int) getErObject().getSecondEntity().getView().getBounds().getCenterY() + e2shiftY);
            }
            else if (getErObject().getSecondEntity().getView().getBounds().getCenterX() < getBounds().x + getBounds().width + 50)
            {
                g.drawLine(getBounds().x + getBounds().width, (int) getBounds().getCenterY(), getBounds().x + getBounds().width + 50, (int) getBounds().getCenterY());
                g.drawLine(getBounds().x + getBounds().width + 50, (int) getBounds().getCenterY(), getBounds().x + getBounds().width + 50,
                        (int) (getBounds().getCenterY() + (getErObject().getSecondEntity().getView().getBounds().getCenterY() - getBounds().getCenterY()) / 2.0f));
                g.drawLine(getBounds().x + getBounds().width + 50,
                        (int) (getBounds().getCenterY() + (getErObject().getSecondEntity().getView().getBounds().getCenterY() - getBounds().getCenterY()) / 2.0f),
                        (int) getErObject().getSecondEntity().getView().getBounds().getCenterX() + e2shiftX,
                        (int) (getBounds().getCenterY() + (getErObject().getSecondEntity().getView().getBounds().getCenterY() - getBounds().getCenterY()) / 2.0f));
                g.drawLine((int) getErObject().getSecondEntity().getView().getBounds().getCenterX() + e2shiftX,
                        (int) (getBounds().getCenterY() + (getErObject().getSecondEntity().getView().getBounds().getCenterY() - getBounds().getCenterY()) / 2.0f),
                        (int) getErObject().getSecondEntity().getView().getBounds().getCenterX() + e2shiftX,
                        getErObject().getSecondEntity().getView().getBounds().y + ((getErObject().getSecondEntity().getView().getBounds().getCenterY() > getBounds().getCenterY()) ? getErObject().getSecondEntity().getView().getBounds().height : 0));
            }
            else
            {
                g.drawLine(getBounds().x + getBounds().width, (int) getBounds().getCenterY(), (int) getErObject().getSecondEntity().getView().getBounds().getCenterX() + e2shiftX,
                        (int) getBounds().getCenterY());
                g.drawLine((int) getErObject().getSecondEntity().getView().getBounds().getCenterX() + e2shiftX, (int) getBounds().getCenterY(), (int) getErObject().getSecondEntity().getView().getBounds().getCenterX() + e2shiftX,
                        getErObject().getSecondEntity().getView().getBounds().y + ((getErObject().getSecondEntity().getView().getBounds().getCenterY() > getBounds().getCenterY()) ? getErObject().getSecondEntity().getView().getBounds().height : 0));
            }
        }
        else
        {
            if (Math.abs(getErObject().getSecondEntity().getView().getBounds().getCenterY() - getBounds().getCenterY()) < 100 && getErObject().getSecondEntity().getView().getBounds().getMaxX() + 10 < getBounds().x)
            {
                g.drawLine(getBounds().x, (int) getBounds().getCenterY(), getBounds().x + (int) ((getErObject().getSecondEntity().getView().getBounds().getMaxX() - getBounds().x) / 2.0f),
                        (int) getBounds().getCenterY());
                g.drawLine(getBounds().x + (int) ((getErObject().getSecondEntity().getView().getBounds().getMaxX() - getBounds().x) / 2.0f), (int) getBounds().getCenterY(),
                        getBounds().x + (int) ((getErObject().getSecondEntity().getView().getBounds().getMaxX() - getBounds().x) / 2.0f), (int) getErObject().getSecondEntity().getView().getBounds().getCenterY() + e2shiftY);
                g.drawLine(getBounds().x + (int) ((getErObject().getSecondEntity().getView().getBounds().getMaxX() - getBounds().x) / 2.0f), (int) getErObject().getSecondEntity().getView().getBounds().getCenterY() + e2shiftY,
                        getErObject().getSecondEntity().getView().getBounds().x + getErObject().getSecondEntity().getView().getBounds().width, (int) getErObject().getSecondEntity().getView().getBounds().getCenterY() + e2shiftY);
            }
            else if (getErObject().getSecondEntity().getView().getBounds().getCenterX() > getBounds().x - 50)
            {
                g.drawLine(getBounds().x, (int) getBounds().getCenterY(), getBounds().x - 50, (int) getBounds().getCenterY());
                g.drawLine(getBounds().x - 50, (int) getBounds().getCenterY(), getBounds().x - 50,
                        (int) (getBounds().getCenterY() + (getErObject().getSecondEntity().getView().getBounds().getCenterY() - getBounds().getCenterY()) / 2.0f));
                g.drawLine(getBounds().x - 50, (int) (getBounds().getCenterY() + (getErObject().getSecondEntity().getView().getBounds().getCenterY() - getBounds().getCenterY()) / 2.0f),
                        (int) getErObject().getSecondEntity().getView().getBounds().getCenterX() + e2shiftX,
                        (int) (getBounds().getCenterY() + (getErObject().getSecondEntity().getView().getBounds().getCenterY() - getBounds().getCenterY()) / 2.0f));
                g.drawLine((int) getErObject().getSecondEntity().getView().getBounds().getCenterX() + e2shiftX,
                        (int) (getBounds().getCenterY() + (getErObject().getSecondEntity().getView().getBounds().getCenterY() - getBounds().getCenterY()) / 2.0f),
                        (int) getErObject().getSecondEntity().getView().getBounds().getCenterX() + e2shiftX,
                        getErObject().getSecondEntity().getView().getBounds().y + ((getErObject().getSecondEntity().getView().getBounds().getCenterY() > getBounds().getCenterY()) ? getErObject().getSecondEntity().getView().getBounds().height : 0));
            }
            else
            {
                g.drawLine(getBounds().x, (int) getBounds().getCenterY(), (int) getErObject().getSecondEntity().getView().getBounds().getCenterX() + e2shiftX, (int) getBounds().getCenterY());
                g.drawLine((int) getErObject().getSecondEntity().getView().getBounds().getCenterX() + e2shiftX, (int) getBounds().getCenterY(), (int) getErObject().getSecondEntity().getView().getBounds().getCenterX() + e2shiftX,
                        getErObject().getSecondEntity().getView().getBounds().y + ((getErObject().getSecondEntity().getView().getBounds().getCenterY() > getBounds().getCenterY()) ? getErObject().getSecondEntity().getView().getBounds().height : 0));
            }
            if (Math.abs(getErObject().getFirstEntity().getView().getBounds().getCenterY() - getBounds().getCenterY()) < 100 && getErObject().getFirstEntity().getView().getBounds().x - 10 > getBounds().getMaxX())
            {
                g.drawLine(getBounds().x + getBounds().width, (int) getBounds().getCenterY(),
                        getBounds().x + getBounds().width + (int) ((getErObject().getFirstEntity().getView().getBounds().x - getBounds().getMaxX()) / 2.0f), (int) getBounds().getCenterY());
                g.drawLine(getBounds().x + getBounds().width + (int) ((getErObject().getFirstEntity().getView().getBounds().x - getBounds().getMaxX()) / 2.0f), (int) getBounds().getCenterY(),
                        getBounds().x + getBounds().width + (int) ((getErObject().getFirstEntity().getView().getBounds().x - getBounds().getMaxX()) / 2.0f), (int) getErObject().getFirstEntity().getView().getBounds().getCenterY() + e1shiftY);
                g.drawLine(getBounds().x + getBounds().width + (int) ((getErObject().getFirstEntity().getView().getBounds().x - getBounds().getMaxX()) / 2.0f),
                        (int) getErObject().getFirstEntity().getView().getBounds().getCenterY() + e1shiftY, getErObject().getFirstEntity().getView().getBounds().x, (int) getErObject().getFirstEntity().getView().getBounds().getCenterY() + e1shiftY);
            }
            else if (getErObject().getFirstEntity().getView().getBounds().getCenterX() < getBounds().x + getBounds().width + 50)
            {
                g.drawLine(getBounds().x + getBounds().width, (int) getBounds().getCenterY(), getBounds().x + getBounds().width + 50, (int) getBounds().getCenterY());
                g.drawLine(getBounds().x + getBounds().width + 50, (int) getBounds().getCenterY(), getBounds().x + getBounds().width + 50,
                        (int) (getBounds().getCenterY() + (getErObject().getFirstEntity().getView().getBounds().getCenterY() - getBounds().getCenterY()) / 2.0f));
                g.drawLine(getBounds().x + getBounds().width + 50,
                        (int) (getBounds().getCenterY() + (getErObject().getFirstEntity().getView().getBounds().getCenterY() - getBounds().getCenterY()) / 2.0f),
                        (int) getErObject().getFirstEntity().getView().getBounds().getCenterX() + e1shiftX,
                        (int) (getBounds().getCenterY() + (getErObject().getFirstEntity().getView().getBounds().getCenterY() - getBounds().getCenterY()) / 2.0f));
                g.drawLine((int) getErObject().getFirstEntity().getView().getBounds().getCenterX() + e1shiftX,
                        (int) (getBounds().getCenterY() + (getErObject().getFirstEntity().getView().getBounds().getCenterY() - getBounds().getCenterY()) / 2.0f),
                        (int) getErObject().getFirstEntity().getView().getBounds().getCenterX() + e1shiftX,
                        getErObject().getFirstEntity().getView().getBounds().y + ((getErObject().getFirstEntity().getView().getBounds().getCenterY() > getBounds().getCenterY()) ? getErObject().getFirstEntity().getView().getBounds().height : 0));
            }
            else
            {
                g.drawLine(getBounds().x + getBounds().width, (int) getBounds().getCenterY(), (int) getErObject().getFirstEntity().getView().getBounds().getCenterX() + e1shiftX,
                        (int) getBounds().getCenterY());
                g.drawLine((int) getErObject().getFirstEntity().getView().getBounds().getCenterX() + e1shiftX, (int) getBounds().getCenterY(), (int) getErObject().getFirstEntity().getView().getBounds().getCenterX() + e1shiftX,
                        getErObject().getFirstEntity().getView().getBounds().y + ((getErObject().getFirstEntity().getView().getBounds().getCenterY() > getBounds().getCenterY()) ? getErObject().getFirstEntity().getView().getBounds().height : 0));
            }
        }

        if (getErObject().getFirstEntity().getView().getBounds().getCenterX() < getErObject().getSecondEntity().getView().getBounds().getCenterX())
        {
            g.drawString((getErObject().getFirstEntityToMany()) ? "n" : "1", getBounds().x - 5, getBounds().y + 30);
            if (getErObject().getFirstEntityToMany())
                g.drawString((getErObject().getSecondEntityToMany()) ? "m" : "1", getBounds().x + getBounds().width + 5, getBounds().y + 30);
            else
                g.drawString((getErObject().getSecondEntityToMany()) ? "n" : "1", getBounds().x + getBounds().width + 5, getBounds().y + 30);
        }
        else
        {
            g.drawString((getErObject().getFirstEntityToMany()) ? "n" : "1", getBounds().x + getBounds().width + 5, getBounds().y + 30);
            if (getErObject().getFirstEntityToMany())
                g.drawString((getErObject().getSecondEntityToMany()) ? "m" : "1", getBounds().x - 5, getBounds().y + 30);
            else
                g.drawString((getErObject().getSecondEntityToMany()) ? "n" : "1", getBounds().x - 5, getBounds().y + 30);
        }

        Polygon p = new Polygon();
        p.addPoint(getBounds().x, getBounds().y + getBounds().height / 2);
        p.addPoint(getBounds().x + getBounds().width / 2, getBounds().y);
        p.addPoint(getBounds().x + getBounds().width, getBounds().y + getBounds().height / 2);
        p.addPoint(getBounds().x + getBounds().width / 2, getBounds().y + getBounds().height);

        g.setColor(Color.WHITE);
        g.fill(p);

        if (isSelected())
            g.setColor(new Color(0, 150, 180));
        else
            g.setColor(Color.BLACK);

        g.draw(p);

        if (getErObject().isWeak())
        {
            Polygon ip = new Polygon();
            ip.addPoint(getBounds().x + 10, getBounds().y + getBounds().height / 2);
            ip.addPoint(getBounds().x + getBounds().width / 2, getBounds().y + 5);
            ip.addPoint(getBounds().x + getBounds().width - 10, getBounds().y + getBounds().height / 2);
            ip.addPoint(getBounds().x + getBounds().width / 2, getBounds().y + getBounds().height - 5);

            g.draw(ip);
        }

        FontRenderContext frc = g.getFontRenderContext();
        Font f = new Font("Helvetica", Font.PLAIN, 18);
        TextLayout tl = new TextLayout(getErObject().getName(), f, frc);
        Rectangle2D textBounds = tl.getBounds();
        tl.draw(g, (int) (getBounds().x + getBounds().width / 2 - textBounds.getWidth() / 2), getBounds().y + getBounds().height / 2 + 7);

    }
}
