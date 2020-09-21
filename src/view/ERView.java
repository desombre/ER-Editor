package view;
/**
 * EReditor
 * view.ERView.java
 * Created by Palle on 30.05.2014
 * Copyright (c) 2014 - 2017 Palle
 * <p>
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 * <p>
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 * <p>
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */

import controller.*;
import main.ER_Editor;
import model.*;
import view.er_objects.DescriptionView;
import view.er_objects.ERObjectView;
import view.er_objects.EntityView;
import view.er_objects.RelationshipView;

import javax.swing.JComponent;
import java.awt.AlphaComposite;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.HeadlessException;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.Toolkit;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.font.FontRenderContext;
import java.awt.font.TextLayout;
import java.awt.geom.Rectangle2D;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class ERView extends JComponent implements MouseMotionListener, MouseListener, KeyListener, RepaintRequest, ERModelQuery {

    private static final long serialVersionUID = -7189232076193021142L;

    protected ERModel model;
    private final List<ERSelectionNotifier> notifier;

    private boolean dragging;
    private Point draggingStart;
    private Point draggingEnd;
    private Point draggingPrevious;
    private boolean dragsObject;
    private float zoom;

    private boolean requestRelationship;
    private boolean relationshipHasFirstEntity;
    private RelationshipView newRelationship;

    private Rectangle visibleRect;

    private ERChangeHistory history;

    public ERView() {
        this.addMouseListener(this);
        this.addMouseMotionListener(this);
        this.addKeyListener(this);
        draggingStart = new Point();
        draggingEnd = new Point();
        draggingPrevious = new Point();
        model = new ERModel();
        zoom = 1.0f;
        notifier = new ArrayList<>();
    }

    public void addEntity() {
        EntityView e = new EntityView();
        e.getBounds().x = (int) (visibleRect.getCenterX() / zoom);
        e.getBounds().y = (int) (visibleRect.getCenterY() / zoom);
        model.getEntities().add(e.getErObject());
        EntityChangeEvent changeEvent = new EntityChangeEvent(model.getEntities(), EntityChangeEvent.CHANGE_ADD, null, e);
        history.pushEvent(changeEvent);
    }

    public void addDescriptionBox() {
        DescriptionView b = new DescriptionView();
        b.getBounds().x = (int) (visibleRect.getCenterX() / zoom);
        b.getBounds().y = (int) (visibleRect.getCenterY() / zoom);
        model.getDescriptions().add(b.getErObject());
        DescriptionBoxChangeEvent changeEvent = new DescriptionBoxChangeEvent(model.getDescriptions(), DescriptionBoxChangeEvent.CHANGE_ADD, null, b);
        history.pushEvent(changeEvent);
    }

    public void copySelected() throws IOException {
        Stack<ERObjectView> objects = new Stack<>();
        for (ERObjectView v : model.getAllERObjectViews())
            if (v.isSelected())
                objects.add(v);
        ERObject[] objs = objects.toArray(new ERObject[objects.size()]);
        ERSelection selection = new ERSelection(objs);
        Toolkit.getDefaultToolkit().getSystemClipboard().setContents(selection, null);
    }

    public void cutSelected() throws IOException {
        copySelected();
        deleteSelected();
    }

    public void deleteSelected() {
        Stack<Entity> removedEntities = new Stack<>();
        Stack<Relationship> removedRelationships = new Stack<>();
        Stack<DescriptionBox> removedDescriptions = new Stack<>();
        for (int i = 0; i < model.getRelationships().size(); i++) {
            ERObjectView<Relationship> r = model.getRelationshipViews().get(i);
            if (r.isSelected() || r.getErObject().getFirstEntity().getView().isSelected()
                    || r.getErObject().getSecondEntity().getView().isSelected()) {
                removedRelationships.push(model.getRelationships().get(i));
                model.getRelationships().remove(i);
                i--;
            }
        }
        for (int i = 0; i < model.getEntities().size(); i++) {
            Entity e1 = model.getEntities().get(i);
            if (e1.getView().isSelected()) {
                removedEntities.push(model.getEntities().get(i));
                model.getEntities().remove(i);
                i--;
            }
        }
        for (int i = 0; i < model.getDescriptions().size(); i++) {
            DescriptionBox b = model.getDescriptions().get(i);
            if (b.getView().isSelected()) {
                removedDescriptions.push(model.getDescriptions().get(i));
                model.getDescriptions().remove(i);
                i--;
            }
        }
        Entity[] r_entities = removedEntities.toArray(new Entity[removedEntities.size()]);
        Relationship[] r_relationships = removedRelationships.toArray(new Relationship[removedRelationships.size()]);
        DescriptionBox[] r_descriptions = removedDescriptions.toArray(new DescriptionBox[removedDescriptions.size()]);
        if (r_relationships.length > 0) {
            RelationshipChangeEvent r_changeEvent = new RelationshipChangeEvent(model.getRelationships(),
                    RelationshipChangeEvent.CHANGE_DELETE_MULTIPLE, r_relationships, null);
            history.pushEvent(r_changeEvent);
        }
        if (r_entities.length > 0) {
            EntityChangeEvent e_changeEvent = new EntityChangeEvent(model.getEntities(), EntityChangeEvent.CHANGE_DELETE_MULTIPLE, r_entities,
                    null);
            history.pushEvent(e_changeEvent);
        }
        if (r_descriptions.length > 0) {
            DescriptionBoxChangeEvent b_changeEvent = new DescriptionBoxChangeEvent(model.getDescriptions(), DescriptionBoxChangeEvent.CHANGE_DELETE_MULTIPLE, r_descriptions, null);
            history.pushEvent(b_changeEvent);
        }
        for (ERSelectionNotifier ersn : notifier)
            ersn.didSelectRelationship(null);
    }

    public void deselectAll() {
        for (ERObjectView v : model.getAllERObjectViews())
            v.deselect();

        for (ERSelectionNotifier ersn : notifier)
            ersn.didSelectRelationship(null);
        for (ERSelectionNotifier ersn : notifier)
            ersn.didSelectEntity(null);
        for (ERSelectionNotifier ersn : notifier)
            ersn.didSelectDescriptionBox(null);
    }

    public void expand() {
        for (ERObjectView e : model.getAllERObjectViews()) {
            e.getBounds().x = (int) (1.25 * e.getBounds().x);
            e.getBounds().y = (int) (1.25 * e.getBounds().y);
        }

    }

    @Override
    public List<Entity> getAllEntities() {
        return model.getEntities();
    }

    @Override
    public List<Relationship> getAllRelationships() {
        return model.getRelationships();
    }

    public void shrink() {
        for (ERObjectView e : model.getAllERObjectViews()) {
            e.getBounds().x = (int) (0.8 * e.getBounds().x);
            e.getBounds().y = (int) (0.8 * e.getBounds().y);
        }

    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ESCAPE)
            requestRelationship = false;
        repaint();
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void mouseClicked(MouseEvent ev) {
        if (ev.getClickCount() == 2) {
            Point p = new Point((int) (ev.getX() / zoom), (int) (ev.getY() / zoom));
            for (Entity en : model.getEntities()) {
                if (en.getView().isAffectedByTouch(p)) {
                    requestRelationship();
                    if (!relationshipHasFirstEntity && requestRelationship) {
                        newRelationship.getErObject().setFirstEntity(en);
                        relationshipHasFirstEntity = true;
                    } else if (relationshipHasFirstEntity && requestRelationship && en != newRelationship.getErObject().getFirstEntity()) {
                        newRelationship.getErObject().setSecondEntity(en);
                        requestRelationship = false;
                        relationshipHasFirstEntity = false;
                        newRelationship.getErObject().getView().getBounds().x = (int) ((newRelationship.getErObject().getFirstEntity().getView().getBounds().getCenterX()
                                + newRelationship.getErObject().getSecondEntity().getView().getBounds().getCenterX()) / 2) - newRelationship.getErObject().getView().getBounds().width / 2;
                        newRelationship.getErObject().getView().getBounds().y = (int) ((newRelationship.getErObject().getFirstEntity().getView().getBounds().getCenterY()
                                + newRelationship.getErObject().getSecondEntity().getView().getBounds().getCenterY()) / 2) - newRelationship.getErObject().getView().getBounds().height / 2;
                        model.getRelationships().add(newRelationship.getErObject());
                        RelationshipChangeEvent changeEvent = new RelationshipChangeEvent(model.getRelationships(),
                                RelationshipChangeEvent.CHANGE_ADD, null, newRelationship.getErObject());
                        history.pushEvent(changeEvent);
                        deselectAll();
                    }
                    repaint();
                    return;
                }
            }
            EntityView e = new EntityView();
            e.getBounds().x = (int) (ev.getX() / zoom);
            e.getBounds().y = (int) (ev.getY() / zoom);
            model.getEntities().add(e.getErObject());
            EntityChangeEvent changeEvent = new EntityChangeEvent(model.getEntities(), EntityChangeEvent.CHANGE_ADD, null, e);
            history.pushEvent(changeEvent);
            repaint();
        }
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        draggingEnd = new Point((int) (e.getX() / zoom), (int) (e.getY() / zoom));
        if (dragsObject) {
            Dimension size = getSize();
            for (ERObjectView en : model.getAllERObjectViews()) {
                if (en.isSelected()) {
                    en.getBounds().x = Math.max(draggingEnd.x - draggingPrevious.x + en.getBounds().x, 0);
                    en.getBounds().y = Math.max(draggingEnd.y - draggingPrevious.y + en.getBounds().y, 0);
                    size.width = Math.max(size.width, en.getBounds().x + en.getBounds().width + 20);
                    size.height = Math.max(size.height, en.getBounds().y + en.getBounds().height + 20);
                }
            }

        } else {
            Rectangle draggingRect = new Rectangle();
            draggingRect.x = Math.min(draggingEnd.x, draggingStart.x);
            draggingRect.width = Math.abs(draggingEnd.x - draggingStart.x);
            draggingRect.y = Math.min(draggingEnd.y, draggingStart.y);
            draggingRect.height = Math.abs(draggingEnd.y - draggingStart.y);
            for (ERObjectView v : model.getAllERObjectViews()) {
                if (v.getBounds().intersects(draggingRect))
                    v.select();
            }
        }

        repaint();
        draggingPrevious = new Point((int) (e.getX() / zoom), (int) (e.getY() / zoom));
    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

    @Override
    public void mouseMoved(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {
        this.requestFocus();
        Entity selectedEntity = null;
        Relationship selectedRelationship = null;
        DescriptionBox selectedDescription = null;
        boolean hitsObject = false;
        Point p = new Point((int) (e.getX() / zoom), (int) (e.getY() / zoom));
        boolean deselect = true;
        for (ERObjectView view : model.getAllERObjectViews())
            if (view.isSelected() && view.isAffectedByTouch(p))
                deselect = false;

        for (ERObjectView<Entity> en : model.getEntityViews()) {
            if (deselect)
                en.deselect();
            if (en.isAffectedByTouch(p)) {
                hitsObject = true;
                for (ERSelectionNotifier ersn : notifier)
                    ersn.didSelectEntity(en.getErObject());
                selectedEntity = en.getErObject();
                if (requestRelationship) {
                    if (!relationshipHasFirstEntity) {
                        newRelationship.getErObject().setFirstEntity(en.getErObject());
                        relationshipHasFirstEntity = true;
                    } else {
                        newRelationship.getErObject().setSecondEntity(en.getErObject());
                        requestRelationship = false;
                        newRelationship.getErObject().getView().getBounds().x = (int) ((newRelationship.getErObject().getFirstEntity().getView().getBounds().getCenterX()
                                + newRelationship.getErObject().getSecondEntity().getView().getBounds().getCenterX()) / 2) - newRelationship.getErObject().getView().getBounds().width / 2;
                        newRelationship.getErObject().getView().getBounds().y = (int) ((newRelationship.getErObject().getFirstEntity().getView().getBounds().getCenterY()
                                + newRelationship.getErObject().getSecondEntity().getView().getBounds().getCenterY()) / 2) - newRelationship.getErObject().getView().getBounds().height / 2;
                        model.getRelationships().add(newRelationship.getErObject());
                        RelationshipChangeEvent changeEvent = new RelationshipChangeEvent(model.getRelationships(),
                                RelationshipChangeEvent.CHANGE_ADD, null, newRelationship.getErObject());
                        history.pushEvent(changeEvent);
                    }

                }
            }
        }
        if (selectedEntity != null)
            selectedEntity.getView().select();

        for (ERObjectView<Relationship> r : model.getRelationshipViews()) {
            if (deselect)
                r.deselect();
            if (r.isAffectedByTouch(p)) {
                hitsObject = true;
                for (ERSelectionNotifier ersn : notifier)
                    ersn.didSelectRelationship(r.getErObject());
                selectedRelationship = r.getErObject();
            }
        }

        if (selectedEntity == null && selectedRelationship != null)
            selectedRelationship.getView().select();

        for (ERObjectView<DescriptionBox> b : model.getDescriptionViews()) {
            if (deselect)
                b.deselect();
            if (b.isAffectedByTouch(p)) {
                hitsObject = true;
                for (ERSelectionNotifier ersn : notifier)
                    ersn.didSelectDescriptionBox(b.getErObject());
                selectedDescription = b.getErObject();
            }
        }

        if (selectedEntity == null && selectedRelationship == null && selectedDescription != null)
            selectedDescription.getView().select();

        dragsObject = hitsObject;
        draggingStart = new Point((int) (e.getX() / zoom), (int) (e.getY() / zoom));
        draggingPrevious = new Point((int) (e.getX() / zoom), (int) (e.getY() / zoom));
        draggingEnd = new Point((int) (e.getX() / zoom), (int) (e.getY() / zoom));
        dragging = true;

        if (!hitsObject) {
            for (ERSelectionNotifier ersn : notifier)
                ersn.didSelectRelationship(null);
            for (ERSelectionNotifier ersn : notifier)
                ersn.didSelectEntity(null);
            for (ERSelectionNotifier ersn : notifier)
                ersn.didSelectDescriptionBox(null);
        }
        repaint();
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        if (dragsObject) {
            int n_drag_x = draggingStart.x - draggingEnd.x;
            int n_drag_y = draggingStart.y - draggingEnd.y;
            if (n_drag_x != 0 || n_drag_y != 0) {
                List<ERObjectView> draggedObjects = new ArrayList<>();
                for (ERObjectView en : model.getAllERObjectViews())
                    if (en.isSelected())
                        draggedObjects.add(en);

                Point[] before = new Point[draggedObjects.size()];
                Point[] after = new Point[draggedObjects.size()];
                for (int i = 0; i < draggedObjects.size(); i++) {
                    after[i] = new Point();
                    before[i] = new Point();
                    after[i].x = draggedObjects.get(i).getBounds().x;
                    after[i].y = draggedObjects.get(i).getBounds().y;
                    before[i].x = after[i].x + n_drag_x;
                    before[i].y = after[i].y + n_drag_y;
                }
                EntityChangeEvent changeEvent = new EntityChangeEvent(draggedObjects.toArray(), EntityChangeEvent.CHANGE_POSITION_MULTIPLE, before, after);
                history.pushEvent(changeEvent);
            }
        }
        dragging = false;
        dragsObject = false;
        repaint();
    }

    @Override
    public void objectNeedsRepaint(ERObject object) {
        this.repaint();
    }

    public void paste() throws HeadlessException, UnsupportedFlavorException, IOException, ClassNotFoundException {
        if (!Toolkit.getDefaultToolkit().getSystemClipboard()
                .isDataFlavorAvailable(new DataFlavor(ERObject.class, "model.Entity-model.Relationship Object")))
            return;
        for (ERObjectView v : model.getAllERObjectViews())
            v.deselect();

        ERObject[] objects = (ERObject[]) Toolkit.getDefaultToolkit().getSystemClipboard()
                .getData(new DataFlavor(ERObject.class, "model.Entity-model.Relationship Object"));

        List<Entity> addedEntities = new ArrayList<>();
        List<Relationship> addedRelationships = new ArrayList<>();
        List<DescriptionBox> addedDescriptions = new ArrayList<>();

        for (ERObject obj : objects) {
            obj.getView().getBounds().x += 20;
            obj.getView().getBounds().y += 20;
            obj.getView().select();
            if (obj instanceof Entity) {
                Entity e = (Entity) obj;
                addedEntities.add(e);
                int num = 1;
                boolean match = false;
                String name = e.getName();
                for (int i = -1; i < model.getEntities().size(); i++) {
                    if (i == -1) {
                        for (int j = 0; j < model.getEntities().size(); j++) {
                            Entity e1 = model.getEntities().get(j);
                            if (e1.getName().equals(name)) {
                                match = true;
                            }
                        }
                    } else {
                        for (int j = i; j < model.getEntities().size(); j++) {
                            Entity e1 = model.getEntities().get(j);
                            if (e1.getName().equals(name + num)) {
                                num++;
                            }
                        }
                    }
                }
                if (match) {
                    e.setName(name + num);
                }

                model.getEntities().add((Entity) obj);
            } else if (obj instanceof Relationship) {
                addedRelationships.add((Relationship) obj);
                model.getRelationships().add((Relationship) obj);
            } else if (obj instanceof DescriptionBox) {
                addedDescriptions.add((DescriptionBox) obj);
                model.getDescriptions().add((DescriptionBox) obj);
            }
            if (addedEntities.size() > 0) {
                EntityChangeEvent changeEvent = new EntityChangeEvent(model.getEntities(), EntityChangeEvent.CHANGE_PASTE, null, addedEntities);
                history.pushEvent(changeEvent);
            }
            if (addedRelationships.size() > 0) {
                RelationshipChangeEvent changeEvent = new RelationshipChangeEvent(model.getRelationships(), RelationshipChangeEvent.CHANGE_PASTE,
                        null, addedRelationships);
                history.pushEvent(changeEvent);
            }
            if (addedDescriptions.size() > 0) {
                DescriptionBoxChangeEvent changeEvent = new DescriptionBoxChangeEvent(model.getDescriptions(), DescriptionBoxChangeEvent.CHANGE_PASTE, null, addedDescriptions);
                history.pushEvent(changeEvent);
            }
        }
    }

    public void requestRelationship() {
        int selectedCount = 0;
        for (Entity e : model.getEntities())
            if (e.getView().isSelected())
                selectedCount++;

        requestRelationship = true;
        relationshipHasFirstEntity = false;
        newRelationship = new RelationshipView();

        if (selectedCount >= 1 && selectedCount <= 2) {
            Entity[] selected = new Entity[selectedCount];
            int j = 0;
            for (int i = 0; i < model.getEntities().size(); i++)
                if (model.getEntities().get(i).getView().isSelected())
                    selected[j++] = model.getEntities().get(i);

            relationshipHasFirstEntity = true;
            newRelationship.getErObject().setFirstEntity(selected[0]);

            if (selectedCount == 2) {
                requestRelationship = false;
                relationshipHasFirstEntity = false;
                newRelationship.getErObject().setSecondEntity(selected[1]);
                newRelationship.getErObject().getView().getBounds().x = (int) ((newRelationship.getErObject().getFirstEntity().getView().getBounds().getCenterX()
                        + newRelationship.getErObject().getSecondEntity().getView().getBounds().getCenterX()) / 2) - newRelationship.getErObject().getView().getBounds().width / 2;
                newRelationship.getErObject().getView().getBounds().y = (int) ((newRelationship.getErObject().getFirstEntity().getView().getBounds().getCenterY()
                        + newRelationship.getErObject().getSecondEntity().getView().getBounds().getCenterY()) / 2) - newRelationship.getErObject().getView().getBounds().height / 2;
                model.getRelationships().add(newRelationship.getErObject());
                RelationshipChangeEvent changeEvent = new RelationshipChangeEvent(model.getRelationships(), RelationshipChangeEvent.CHANGE_ADD,
                        null, newRelationship.getErObject());
                history.pushEvent(changeEvent);
            }
        } else {
            requestRelationship = true;
            relationshipHasFirstEntity = false;
            newRelationship = new RelationshipView();
        }

        deselectAll();
        repaint();
    }

    public void selectAll() {
        for (ERObjectView view : model.getAllERObjectViews())
            view.select();

    }

    public void setChangeHistory(ERChangeHistory history) {
        this.history = history;
    }

    public void setERSelectionNotifier(ERSelectionNotifier n) {
        notifier.add(n);
    }

    public void setVisibleRect(Rectangle r) {
        visibleRect = r;
        if (requestRelationship)
            repaint();
    }

    public void zoomIn() {
        zoom *= 1.25f;
    }

    public void zoomOriginal() {
        zoom = 1.0f;
    }

    public void zoomOut() {
        zoom /= 1.25f;
    }

    @Override
    protected void paintComponent(Graphics graphics) {
        int maxX = this.getParent().getWidth();
        int maxY = this.getParent().getHeight();

        for (ERObjectView view : model.getAllERObjectViews()) {
            Rectangle bounds = view.getBounds();
            maxX = (int) Math.max(maxX, bounds.getMaxX() * zoom + 200 * zoom);
            maxY = (int) Math.max(maxY, bounds.getMaxY() * zoom + 200 * zoom);
        }



        setSize(maxX, maxY);
        setPreferredSize(new Dimension(maxX, maxY));

        Graphics2D g = (Graphics2D) graphics.create();
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        g.setColor(Color.WHITE);
        g.fillRect(0, 0, maxX, maxY);
        g.scale(zoom, zoom);
        g.setColor(new Color(230, 230, 230));
        g.setStroke(new BasicStroke(0.5f));
        for (int x = 0; x < maxX / zoom; x += 50)
            g.drawLine(x, 0, x, (int) (maxY / zoom));
        for (int y = 0; y < maxY / zoom; y += 50)
            g.drawLine(0, y, (int) (maxX / zoom), y);

        g.setStroke(new BasicStroke(1));

        for (ERObjectView view : model.getAllERObjectViews())
            view.paint(g);



        g.scale(1.0f / zoom, 1.0f / zoom);
        if (dragging && !dragsObject) {
            g.setColor(new Color(0, 150, 180));
            Rectangle draggingRect = new Rectangle();
            draggingRect.x = (int) (Math.min(draggingEnd.x, draggingStart.x) * zoom);
            draggingRect.width = (int) (Math.abs(draggingEnd.x - draggingStart.x) * zoom);
            draggingRect.y = (int) (Math.min(draggingEnd.y, draggingStart.y) * zoom);
            draggingRect.height = (int) (Math.abs(draggingEnd.y - draggingStart.y) * zoom);
            g.draw(draggingRect);
            g.setComposite(AlphaComposite.SrcOver);
            g.setColor(new Color(0, 150, 180, 10));
            g.fill(draggingRect);
        }

        if (requestRelationship) {
            FontRenderContext frc = g.getFontRenderContext();
            Font f = new Font("Helvetica", Font.PLAIN, 20);
            TextLayout tl = new TextLayout(
                    relationshipHasFirstEntity ? ER_Editor.getLOCALIZATION().getString("relationship_choose_second_entity")
                            : ER_Editor.getLOCALIZATION().getString("relationship_choose_first_entity"),
                    f, frc);
            Rectangle2D textBounds = tl.getBounds();
            g.setColor(new Color(220, 220, 220, 150));
            g.fillRoundRect((int) (visibleRect.getCenterX() - textBounds.getWidth() / 2 - 20), visibleRect.y + 20,
                    (int) textBounds.getWidth() + 40, (int) textBounds.getHeight() + 28, 20, 20);
            g.setColor(Color.BLACK);
            tl.draw(g, (int) (visibleRect.getCenterX() - textBounds.getWidth() / 2), visibleRect.y + 50);
        }
    }
}
