package view.er_objects;

import model.Attribute;
import model.Entity;

import java.awt.*;
import java.awt.font.FontRenderContext;
import java.awt.font.TextLayout;
import java.awt.geom.Rectangle2D;

public class EntityView extends ERObjectView<Entity> {

    public EntityView(){
        super();
        Entity e = new Entity();
        e.setView(this);
        super.setErObject(e);

        getBounds().x = 500;
        getBounds().y = 400;
        getBounds().width = 200;
        getBounds().height = 80;
    }

    @Override
    public void paint(Graphics2D g)
    {
        getBounds().width = getErObject().isWeak() ? 210 : 200;
        getBounds().height = getErObject().isWeak() ? 90 : 80;

        // Parent Entity

        if (getErObject().getParentEntity() != null)
        {
            int shiftX = (int) ((getErObject().getParentEntity().getView().getBounds().getCenterX() - getBounds().getCenterX()) * 0.05f);
            int shiftY = (int) ((getErObject().getParentEntity().getView().getBounds().getCenterY() - getBounds().getCenterY()) * 0.05f);
            shiftX *= -1;
            shiftY *= -1;

            if (isSelected())
                g.setColor(new Color(0, 150, 180));
            else
                g.setColor(Color.BLACK);
            if (Math.abs(getBounds().getCenterX() - getErObject().getParentEntity().getView().getBounds().getCenterX()) < 130)
            {
                g.drawLine((int) getBounds().getCenterX(), (int) getBounds().getCenterY(), (int) getBounds().getCenterX(),
                        (int) (getBounds().getCenterY() + (getErObject().getParentEntity().getView().getBounds().getCenterY() - getBounds().getCenterY()) / 2.0f));
                g.drawLine((int) getBounds().getCenterX(),
                        (int) (getBounds().getCenterY() + (getErObject().getParentEntity().getView().getBounds().getCenterY() - getBounds().getCenterY()) / 2.0f),
                        (int) getErObject().getParentEntity().getView().getBounds().getCenterX() + shiftX,
                        (int) (getBounds().getCenterY() + (getErObject().getParentEntity().getView().getBounds().getCenterY() - getBounds().getCenterY()) / 2.0f));
                if (getBounds().getCenterY() < getErObject().getParentEntity().getView().getBounds().getCenterY())
                {
                    g.drawLine((int) getErObject().getParentEntity().getView().getBounds().getCenterX() + shiftX,
                            (int) (getBounds().getCenterY() + (getErObject().getParentEntity().getView().getBounds().getCenterY() - getBounds().getCenterY()) / 2.0f),
                            (int) getErObject().getParentEntity().getView().getBounds().getCenterX() + shiftX, getErObject().getParentEntity().getView().getBounds().y);
                    int tcPosX = (int) getErObject().getParentEntity().getView().getBounds().getCenterX() + shiftX;
                    int tcPosY = getErObject().getParentEntity().getView().getBounds().y;
                    g.drawLine(tcPosX, tcPosY, tcPosX - 5, tcPosY - 8);
                    g.drawLine(tcPosX + 5, tcPosY - 8, tcPosX - 5, tcPosY - 8);
                    g.drawLine(tcPosX, tcPosY, tcPosX + 5, tcPosY - 8);
                }
                else
                {
                    g.drawLine((int) getErObject().getParentEntity().getView().getBounds().getCenterX() + shiftX,
                            (int) (getBounds().getCenterY() + (getErObject().getParentEntity().getView().getBounds().getCenterY() - getBounds().getCenterY()) / 2.0f),
                            (int) getErObject().getParentEntity().getView().getBounds().getCenterX() + shiftX, getErObject().getParentEntity().getView().getBounds().y + getErObject().getParentEntity().getView().getBounds().height);
                    int tcPosX = (int) getErObject().getParentEntity().getView().getBounds().getCenterX() + shiftX;
                    int tcPosY = getErObject().getParentEntity().getView().getBounds().y + getErObject().getParentEntity().getView().getBounds().height;
                    g.drawLine(tcPosX, tcPosY, tcPosX - 5, tcPosY + 8);
                    g.drawLine(tcPosX + 5, tcPosY + 8, tcPosX - 5, tcPosY + 8);
                    g.drawLine(tcPosX, tcPosY, tcPosX + 5, tcPosY + 8);
                }
            }
            else if (Math.abs(getBounds().getCenterY() - getErObject().getParentEntity().getView().getBounds().getCenterY()) < 100)
            {
                g.drawLine((int) getBounds().getCenterX(), (int) (getBounds().getCenterY()),
                        (int) (getBounds().getCenterX() + (getErObject().getParentEntity().getView().getBounds().getCenterX() - getBounds().getCenterX()) / 2.0f),
                        (int) (getBounds().getCenterY()));
                g.drawLine((int) (getBounds().getCenterX() + (getErObject().getParentEntity().getView().getBounds().getCenterX() - getBounds().getCenterX()) / 2.0f),
                        (int) getBounds().getCenterY(),
                        (int) (getBounds().getCenterX() + (getErObject().getParentEntity().getView().getBounds().getCenterX() - getBounds().getCenterX()) / 2.0f),
                        (int) getErObject().getParentEntity().getView().getBounds().getCenterY() + shiftY);
                if (getBounds().getCenterX() < getErObject().getParentEntity().getView().getBounds().getCenterX())
                {
                    g.drawLine((int) (getBounds().getCenterX() + (getErObject().getParentEntity().getView().getBounds().getCenterX() - getBounds().getCenterX()) / 2.0f),
                            (int) getErObject().getParentEntity().getView().getBounds().getCenterY() + shiftY, getErObject().getParentEntity().getView().getBounds().x,
                            (int) getErObject().getParentEntity().getView().getBounds().getCenterY() + shiftY);
                    int tcPosX = getErObject().getParentEntity().getView().getBounds().x;
                    int tcPosY = (int) getErObject().getParentEntity().getView().getBounds().getCenterY() + shiftY;
                    g.drawLine(tcPosX, tcPosY, tcPosX - 8, tcPosY - 5);
                    g.drawLine(tcPosX - 8, tcPosY - 5, tcPosX - 8, tcPosY + 5);
                    g.drawLine(tcPosX - 8, tcPosY + 5, tcPosX, tcPosY);
                }
                else
                {
                    g.drawLine((int) (getBounds().getCenterX() + (getErObject().getParentEntity().getView().getBounds().getCenterX() - getBounds().getCenterX()) / 2.0f),
                            (int) getErObject().getParentEntity().getView().getBounds().getCenterY() + shiftY, getErObject().getParentEntity().getView().getBounds().x + getErObject().getParentEntity().getView().getBounds().width,
                            (int) getErObject().getParentEntity().getView().getBounds().getCenterY() + shiftY);
                    int tcPosX = getErObject().getParentEntity().getView().getBounds().x + getErObject().getParentEntity().getView().getBounds().width;
                    int tcPosY = (int) getErObject().getParentEntity().getView().getBounds().getCenterY() + shiftY;
                    g.drawLine(tcPosX, tcPosY, tcPosX + 8, tcPosY - 5);
                    g.drawLine(tcPosX + 8, tcPosY - 5, tcPosX + 8, tcPosY + 5);
                    g.drawLine(tcPosX + 8, tcPosY + 5, tcPosX, tcPosY);
                }
            }
            else
            {
                g.drawLine((int) getBounds().getCenterX(), (int) getBounds().getCenterY(), (int) getErObject().getParentEntity().getView().getBounds().getCenterX() + shiftX,
                        (int) getBounds().getCenterY());
                if (getBounds().getCenterY() < getErObject().getParentEntity().getView().getBounds().getCenterY())
                {
                    g.drawLine((int) getErObject().getParentEntity().getView().getBounds().getCenterX() + shiftX, (int) getBounds().getCenterY(),
                            (int) getErObject().getParentEntity().getView().getBounds().getCenterX() + shiftX, getErObject().getParentEntity().getView().getBounds().y);
                    int tcPosX = (int) getErObject().getParentEntity().getView().getBounds().getCenterX() + shiftX;
                    int tcPosY = getErObject().getParentEntity().getView().getBounds().y;
                    g.drawLine(tcPosX, tcPosY, tcPosX - 5, tcPosY - 8);
                    g.drawLine(tcPosX + 5, tcPosY - 8, tcPosX - 5, tcPosY - 8);
                    g.drawLine(tcPosX, tcPosY, tcPosX + 5, tcPosY - 8);
                }
                else
                {
                    g.drawLine((int) getErObject().getParentEntity().getView().getBounds().getCenterX() + shiftX, (int) getBounds().getCenterY(),
                            (int) getErObject().getParentEntity().getView().getBounds().getCenterX() + shiftX, (int) getErObject().getParentEntity().getView().getBounds().getMaxY());
                    int tcPosX = (int) getErObject().getParentEntity().getView().getBounds().getCenterX() + shiftX;
                    int tcPosY = getErObject().getParentEntity().getView().getBounds().y + getErObject().getParentEntity().getView().getBounds().height;
                    g.drawLine(tcPosX, tcPosY, tcPosX - 5, tcPosY + 8);
                    g.drawLine(tcPosX + 5, tcPosY + 8, tcPosX - 5, tcPosY + 8);
                    g.drawLine(tcPosX, tcPosY, tcPosX + 5, tcPosY + 8);
                }
            }

        }

        // Aggregated Entity

        if (getErObject().getAggregatedEntity() != null)
        {
            int shiftX = (int) ((getErObject().getAggregatedEntity().getView().getBounds().getCenterX() - getBounds().getCenterX()) * 0.05f);
            int shiftY = (int) ((getErObject().getAggregatedEntity().getView().getBounds().getCenterY() - getBounds().getCenterY()) * 0.05f);
            shiftX *= -1;
            shiftY *= -1;

            if (isSelected())
                g.setColor(new Color(0, 150, 180));
            else
                g.setColor(Color.BLACK);
            if (Math.abs(getBounds().getCenterX() - getErObject().getAggregatedEntity().getView().getBounds().getCenterX()) < 130)
            {
                g.drawLine((int) getBounds().getCenterX(), (int) getBounds().getCenterY(), (int) getBounds().getCenterX(),
                        (int) (getBounds().getCenterY() + (getErObject().getAggregatedEntity().getView().getBounds().getCenterY() - getBounds().getCenterY()) / 2.0f));
                g.drawLine((int) getBounds().getCenterX(),
                        (int) (getBounds().getCenterY() + (getErObject().getAggregatedEntity().getView().getBounds().getCenterY() - getBounds().getCenterY()) / 2.0f),
                        (int) getErObject().getAggregatedEntity().getView().getBounds().getCenterX() + shiftX,
                        (int) (getBounds().getCenterY() + (getErObject().getAggregatedEntity().getView().getBounds().getCenterY() - getBounds().getCenterY()) / 2.0f));
                if (getBounds().getCenterY() < getErObject().getAggregatedEntity().getView().getBounds().getCenterY())
                {
                    g.drawLine((int) getErObject().getAggregatedEntity().getView().getBounds().getCenterX() + shiftX,
                            (int) (getBounds().getCenterY() + (getErObject().getAggregatedEntity().getView().getBounds().getCenterY() - getBounds().getCenterY()) / 2.0f),
                            (int) getErObject().getAggregatedEntity().getView().getBounds().getCenterX() + shiftX, getErObject().getAggregatedEntity().getView().getBounds().y);
                    int tcPosX = (int) getErObject().getAggregatedEntity().getView().getBounds().getCenterX() + shiftX;
                    int tcPosY = getErObject().getAggregatedEntity().getView().getBounds().y;
                    g.drawLine(tcPosX, tcPosY, tcPosX - 8, tcPosY - 12);
                    g.drawLine(tcPosX, tcPosY, tcPosX + 8, tcPosY - 12);
                    g.drawLine(tcPosX, tcPosY - 24, tcPosX - 8, tcPosY - 12);
                    g.drawLine(tcPosX, tcPosY - 24, tcPosX + 8, tcPosY - 12);
                }
                else
                {
                    g.drawLine((int) getErObject().getAggregatedEntity().getView().getBounds().getCenterX() + shiftX,
                            (int) (getBounds().getCenterY() + (getErObject().getAggregatedEntity().getView().getBounds().getCenterY() - getBounds().getCenterY()) / 2.0f),
                            (int) getErObject().getAggregatedEntity().getView().getBounds().getCenterX() + shiftX,
                            getErObject().getAggregatedEntity().getView().getBounds().y + getErObject().getAggregatedEntity().getView().getBounds().height);
                    int tcPosX = (int) getErObject().getAggregatedEntity().getView().getBounds().getCenterX() + shiftX;
                    int tcPosY = getErObject().getAggregatedEntity().getView().getBounds().y + getErObject().getAggregatedEntity().getView().getBounds().height;
                    g.drawLine(tcPosX, tcPosY, tcPosX - 8, tcPosY + 12);
                    g.drawLine(tcPosX, tcPosY, tcPosX + 8, tcPosY + 12);
                    g.drawLine(tcPosX, tcPosY + 24, tcPosX - 8, tcPosY + 12);
                    g.drawLine(tcPosX, tcPosY + 24, tcPosX + 8, tcPosY + 12);
                }
            }
            else if (Math.abs(getBounds().getCenterY() - getErObject().getAggregatedEntity().getView().getBounds().getCenterY()) < 100)
            {
                g.drawLine((int) getBounds().getCenterX(), (int) (getBounds().getCenterY()),
                        (int) (getBounds().getCenterX() + (getErObject().getAggregatedEntity().getView().getBounds().getCenterX() - getBounds().getCenterX()) / 2.0f),
                        (int) (getBounds().getCenterY()));
                g.drawLine((int) (getBounds().getCenterX() + (getErObject().getAggregatedEntity().getView().getBounds().getCenterX() - getBounds().getCenterX()) / 2.0f),
                        (int) getBounds().getCenterY(),
                        (int) (getBounds().getCenterX() + (getErObject().getAggregatedEntity().getView().getBounds().getCenterX() - getBounds().getCenterX()) / 2.0f),
                        (int) getErObject().getAggregatedEntity().getView().getBounds().getCenterY() + shiftY);
                if (getBounds().getCenterX() < getErObject().getAggregatedEntity().getView().getBounds().getCenterX())
                {
                    g.drawLine((int) (getBounds().getCenterX() + (getErObject().getAggregatedEntity().getView().getBounds().getCenterX() - getBounds().getCenterX()) / 2.0f),
                            (int) getErObject().getAggregatedEntity().getView().getBounds().getCenterY() + shiftY, getErObject().getAggregatedEntity().getView().getBounds().x,
                            (int) getErObject().getAggregatedEntity().getView().getBounds().getCenterY() + shiftY);
                    int tcPosX = getErObject().getAggregatedEntity().getView().getBounds().x;
                    int tcPosY = (int) getErObject().getAggregatedEntity().getView().getBounds().getCenterY() + shiftY;
                    g.drawLine(tcPosX, tcPosY, tcPosX - 12, tcPosY - 8);
                    g.drawLine(tcPosX, tcPosY, tcPosX - 12, tcPosY + 8);
                    g.drawLine(tcPosX - 12, tcPosY - 8, tcPosX - 24, tcPosY);
                    g.drawLine(tcPosX - 12, tcPosY + 8, tcPosX - 24, tcPosY);
                }
                else
                {
                    g.drawLine((int) (getBounds().getCenterX() + (getErObject().getAggregatedEntity().getView().getBounds().getCenterX() - getBounds().getCenterX()) / 2.0f),
                            (int) getErObject().getAggregatedEntity().getView().getBounds().getCenterY() + shiftY, getErObject().getAggregatedEntity().getView().getBounds().x + getErObject().getAggregatedEntity().getView().getBounds().width,
                            (int) getErObject().getAggregatedEntity().getView().getBounds().getCenterY() + shiftY);
                    int tcPosX = getErObject().getAggregatedEntity().getView().getBounds().x + getErObject().getAggregatedEntity().getView().getBounds().width;
                    int tcPosY = (int) getErObject().getAggregatedEntity().getView().getBounds().getCenterY() + shiftY;
                    g.drawLine(tcPosX + 24, tcPosY, tcPosX + 12, tcPosY - 8);
                    g.drawLine(tcPosX + 24, tcPosY, tcPosX + 12, tcPosY + 8);
                    g.drawLine(tcPosX + 12, tcPosY + 8, tcPosX, tcPosY);
                    g.drawLine(tcPosX + 12, tcPosY - 8, tcPosX, tcPosY);
                }
            }
            else
            {
                g.drawLine((int) getBounds().getCenterX(), (int) getBounds().getCenterY(), (int) getErObject().getAggregatedEntity().getView().getBounds().getCenterX() + shiftX,
                        (int) getBounds().getCenterY());
                if (getBounds().getCenterY() < getErObject().getAggregatedEntity().getView().getBounds().getCenterY())
                {
                    g.drawLine((int) getErObject().getAggregatedEntity().getView().getBounds().getCenterX() + shiftX, (int) getBounds().getCenterY(),
                            (int) getErObject().getAggregatedEntity().getView().getBounds().getCenterX() + shiftX, getErObject().getAggregatedEntity().getView().getBounds().y);
                    int tcPosX = (int) getErObject().getAggregatedEntity().getView().getBounds().getCenterX() + shiftX;
                    int tcPosY = getErObject().getAggregatedEntity().getView().getBounds().y;
                    g.drawLine(tcPosX, tcPosY, tcPosX - 8, tcPosY - 12);
                    g.drawLine(tcPosX, tcPosY, tcPosX + 8, tcPosY - 12);
                    g.drawLine(tcPosX, tcPosY - 24, tcPosX - 8, tcPosY - 12);
                    g.drawLine(tcPosX, tcPosY - 24, tcPosX + 8, tcPosY - 12);
                }
                else
                {
                    g.drawLine((int) getErObject().getAggregatedEntity().getView().getBounds().getCenterX() + shiftX, (int) getBounds().getCenterY(),
                            (int) getErObject().getAggregatedEntity().getView().getBounds().getCenterX() + shiftX, (int) getErObject().getAggregatedEntity().getView().getBounds().getMaxY());
                    int tcPosX = (int) getErObject().getAggregatedEntity().getView().getBounds().getCenterX() + shiftX;
                    int tcPosY = getErObject().getAggregatedEntity().getView().getBounds().y + getErObject().getAggregatedEntity().getView().getBounds().height;
                    g.drawLine(tcPosX, tcPosY, tcPosX - 8, tcPosY + 12);
                    g.drawLine(tcPosX, tcPosY, tcPosX + 8, tcPosY + 12);
                    g.drawLine(tcPosX, tcPosY + 24, tcPosX - 8, tcPosY + 12);
                    g.drawLine(tcPosX, tcPosY + 24, tcPosX + 8, tcPosY + 12);
                }
            }

        }

        // Entity

        float a_radius_x = Math.max(0, getErObject().getAttributes().size() - 8) * 15 + 200;
        float a_radius_y = Math.max(0, getErObject().getAttributes().size() - 8) * 15 + 150;

        for (int i = 0; i < getErObject().getAttributes().size(); i++)
        {
            Attribute a = getErObject().getAttributes().get(i);
            float angle = (float) i / getErObject().getAttributes().size() * -2.0f * 3.141592653f + 3.141592653f;
            float posX = (float) (Math.cos(angle) * a_radius_x + getBounds().getCenterX());
            float posY = (float) (Math.sin(angle) * -a_radius_y + getBounds().getCenterY());

            if (isSelected())
                g.setColor(new Color(0, 150, 180));
            else
                g.setColor(Color.BLACK);
            g.drawLine((int) posX, (int) posY, getBounds().x + getBounds().width / 2, getBounds().y + getBounds().height / 2);

            if (a.getName().length() != 0)
            {
                FontRenderContext frc = g.getFontRenderContext();
                Font f = new Font("Helvetica", Font.PLAIN, 14);
                TextLayout tl = new TextLayout(a.getName(), f, frc);
                Rectangle2D textBounds = tl.getBounds();

                int radius = Math.max((int) (textBounds.getWidth() / 2) + 8, 40);

                g.setColor(Color.WHITE);
                g.fillOval((int) posX - radius, (int) posY - 40, 2 * radius, 80);

                if (isSelected())
                    g.setColor(new Color(0, 150, 180));
                else
                    g.setColor(Color.BLACK);
                g.drawOval((int) posX - radius, (int) posY - 40, 2 * radius, 80);
                if (a.isKeyAttribute())
                    g.drawLine((int) posX - radius+5, (int) posY , (int) posX + radius-5,(int) posY );
                //g.fillArc((int) posX - radius, (int) posY - 40, 2 * radius, 80, 180, 180);

                tl.draw(g, (int) (posX - textBounds.getWidth() / 2), posY + (a.isKeyAttribute() ? -2 : 3));
            }

        }

        g.setColor(Color.WHITE);
        g.fill(getBounds());

        if (isSelected())
            g.setColor(new Color(0, 150, 180));
        else
            g.setColor(Color.BLACK);

        g.draw(getBounds());
        if (getErObject().isWeak())
        {
            Rectangle innerRect = new Rectangle(getBounds().x + 5, getBounds().y + 5, getBounds().width - 10, getBounds().height - 10);
            g.draw(innerRect);
        }
        if (getErObject().getName().length() != 0)
        {
            FontRenderContext frc = g.getFontRenderContext();
            Font f = new Font("Helvetica", Font.PLAIN, 18);
            TextLayout tl = new TextLayout(getErObject().getName(), f, frc);
            Rectangle2D textBounds = tl.getBounds();
            tl.draw(g, (int) (getBounds().x + getBounds().width / 2 - textBounds.getWidth() / 2), getBounds().y + getBounds().height / 2 + 7);
        }
    }
}
