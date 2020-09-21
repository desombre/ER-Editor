package view.er_objects;

import model.DescriptionBox;

import java.awt.*;
import java.awt.font.FontRenderContext;
import java.awt.font.TextLayout;

public class DescriptionView extends ERObjectView<DescriptionBox> {


    public DescriptionView() {
        super();
        DescriptionBox descriptionBox = new DescriptionBox();
        descriptionBox.setView(this);
        super.setErObject(descriptionBox);


    }
    @Override
    public void paint(Graphics2D g)
    {
        g.setColor(Color.WHITE);

        if (getErObject().getText().length() != 0)
        {
            String[] lines = getErObject().getText().split("\n");
            int maxWidth = 0;
            for (String line : lines)
            {
                if (line.length() == 0)
                    continue;
                FontRenderContext frc = g.getFontRenderContext();
                Font f = new Font("Helvetica", Font.PLAIN, 14);
                TextLayout tl = new TextLayout(line, f, frc);
                maxWidth = Math.max((int) tl.getBounds().getWidth(), maxWidth);
            }

            FontRenderContext frc = g.getFontRenderContext();
            Font f = new Font("Helvetica", Font.BOLD, 22);
            TextLayout tl = new TextLayout(getErObject().getName(), f, frc);
            maxWidth = Math.max((int) tl.getBounds().getWidth(), maxWidth);

            getBounds().width = maxWidth + 20;

            int height = getErObject().getName().trim().isEmpty() ? 0 : 24;
            height += lines.length * 18;

            getBounds().height = height + 20;
        }

        g.fillRoundRect(getBounds().x, getBounds().y, getBounds().width, getBounds().height, 10, 10);

        if (isSelected())
            g.setColor(new Color(0, 150, 180));
        else
            g.setColor(Color.BLACK);

        g.drawRoundRect(getBounds().x, getBounds().y, getBounds().width, getBounds().height, 10, 10);

        if (!getErObject().getName().trim().isEmpty())
        {
            FontRenderContext frc = g.getFontRenderContext();
            Font f = new Font("Helvetica", Font.BOLD, 22);
            TextLayout tl = new TextLayout(getErObject().getName(), f, frc);
            tl.draw(g, getBounds().x + 10, (float) (getBounds().y + 17 + 11));
        }

        if (getErObject().getText().length() != 0)
        {
            String[] lines = getErObject().getText().split("\n");

            int offset;

            if (getErObject().getName().trim().isEmpty())
            {
                offset = 20;
            }
            else
            {
                offset = 48;
            }

            for (int i = 0; i < lines.length; i++)
            {
                String line = lines[i];
                if (line.length() == 0)
                    continue;
                FontRenderContext frc = g.getFontRenderContext();
                Font f = new Font("Helvetica", Font.PLAIN, 14);
                TextLayout tl = new TextLayout(line, f, frc);
                tl.draw(g, getBounds().x + 10, getBounds().y + offset + i * 18);
            }
        }

    }
}
