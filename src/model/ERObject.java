package model;
/**
 * EReditor
 * ERObject.java
 * Created by Palle on 30.05.2014
 * Copyright (c) 2014 - 2017 Palle.
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

import view.er_objects.ERObjectView;

import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.io.Serializable;

public abstract class ERObject<T extends ERObjectView> implements Serializable {
    private static final long serialVersionUID = 1L;

    private String name = "Empty";
    private boolean weak;
    private T view;

    public ERObject(T view) {
        this.view = view;
    }

    public ERObject() {
    }

    public T getView() {
        return view;
    }

    public void setView(T view) {
        this.view = view;
    }

    public String getName() {
        return name;
    }


    public boolean isWeak() {
        return weak;
    }


    public void setName(String name) {
        if (name.length() == 0)
            name = " ";
        this.name = name;
    }

    public void setWeak(boolean aFlag) {
        weak = aFlag;
    }

}