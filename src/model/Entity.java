package model;
/**
 * EReditor
 * Entity.java
 * Created by Palle on 30.05.2014
 * Copyright (c) 2014 - 2017 Palle
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */

import main.ER_Editor;
import view.er_objects.EntityView;

import java.util.ArrayList;
import java.util.Objects;

public class Entity extends ERObject<EntityView>
{
	private static final long serialVersionUID = 1L;

	private ArrayList<Attribute>	attributes;
	private boolean					abstractEntity;
	private Entity					parentEntity;
	private Entity					aggregatedEntity;


	public Entity()
	{
		super();

		setAttributes(new ArrayList<Attribute>());

		setName(ER_Editor.LOCALIZATION.getString("entity_default_name"));
	}

	public Entity getAggregatedEntity()
	{
		return aggregatedEntity;
	}

	public Entity getParent()
	{
		return getParentEntity();
	}

	public boolean hasAggregatedEntity()
	{
		return getAggregatedEntity() != null;
	}

	public boolean hasParentEntity()
	{
		return getParentEntity() != null;
	}

	public boolean isAbstract()
	{
		return isAbstractEntity();
	}



	public void setAbstract(boolean aFlag)
	{
		setAbstractEntity(aFlag);
	}

	public void setAggregatedEntity(Entity aggregatedEntity)
	{
		this.aggregatedEntity = aggregatedEntity;
	}

	public void setParentEntity(Entity e)
	{
		parentEntity = e;
	}

	public ArrayList<Attribute> getAttributes() {
		return attributes;
	}

	public void setAttributes(ArrayList<Attribute> attributes) {
		this.attributes = attributes;
	}

	public boolean isAbstractEntity() {
		return abstractEntity;
	}

	public void setAbstractEntity(boolean abstractEntity) {
		this.abstractEntity = abstractEntity;
	}

	public Entity getParentEntity() {
		return parentEntity;
	}

	public boolean addAttribute(Attribute a){
		return this.getAttributes().add(a);
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		Entity entity = (Entity) o;
		return getName().equals(entity.getName());
	}

	@Override
	public int hashCode() {
		return Objects.hash(getName());
	}
}