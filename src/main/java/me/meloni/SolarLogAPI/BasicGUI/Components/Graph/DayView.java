/*
Copyright 2020 - 2021 "Christoph Kohnen", "Hovercraft Full of Eels", "Rodrigo Azevedo"

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
 */
package me.meloni.SolarLogAPI.BasicGUI.Components.Graph;

import me.meloni.SolarLogAPI.BasicGUI.GetGraphData;
import me.meloni.SolarLogAPI.DataConversion.Entries;
import me.meloni.SolarLogAPI.DataConversion.GetStartOf;
import me.meloni.SolarLogAPI.InverterMap;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * This class represents the visualization of data in a graph mapped to a {@link JPanel}
 *
 * @author "Hovercraft Full of Eels", "Rodrigo Azevedo", "Christoph Kohnen"
 *
 * This builds upon an improved version of Hovercraft Full of Eels (https://stackoverflow.com/users/522444/hovercraft-full-of-eels)
 * answer on StackOverflow: https://stackoverflow.com/a/8693635/753012 by Rodrigo Azevedo
 *
 * @since 0.1.0
 */
@SuppressWarnings({"unused", "DuplicatedCode"})
public class DayView extends JPanel {

    private final Date day;
    private final List<List<Double>> data;

    private Color gridColor = Color.DARK_GRAY;
    private Color backgroundColor = Color.GRAY;
    private Color graphBackgroundColor = Color.WHITE;
    private Color axisColor = Color.BLACK;
    private Color labelColor = Color.BLACK;
    private Color row1Color = new Color(191, 97, 106);
    private Color row2Color = new Color(94, 129, 172);
    private Color row3Color = new Color(235, 203, 139);
    private Color row4Color = new Color(136, 192, 208);
    private Color row5Color = new Color(163, 190, 140);

    private int padding = 25;
    private int labelPadding = 25;
    private int valuePadding = 10;

    private boolean row1Visible = true;
    private boolean row2Visible = true;
    private boolean row3Visible = true;
    private boolean row4Visible = true;
    private boolean row5Visible = true;

    private boolean row1Shaded = true;
    private boolean row3Shaded = true;
    private boolean row5Shaded = true;

    private boolean mouseGUI = true;

    private int mouseX;
    private int mouseY;

    private static final Stroke GRAPH_STROKE = new BasicStroke(2f);

    public DayView(InverterMap inverterMap, Date day) {
        this.data = GetGraphData.getDayGraphData(GetStartOf.day(day), inverterMap.getAsMap());
        this.day = day;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        double xScale = ((double) getWidth() - (2 * padding) - labelPadding - labelPadding) / (data.size() - 1);

        Stroke stroke = g2.getStroke();

        double yaScale = ((double) getHeight() - 2 * padding - labelPadding) / (getMaxAScore() - getMinScore());
        double ybScale = ((double) getHeight() - 2 * padding - labelPadding) / (getMaxBScore() - getMinScore());

        List<Point> row1 = new ArrayList<>();
        List<Point> row2 = new ArrayList<>();
        List<Point> row3 = new ArrayList<>();
        List<Point> row4 = new ArrayList<>();
        List<Point> row5 = new ArrayList<>();
        for (int i = 0; i < data.size(); i++) {
            int x = (int) (i * xScale + padding + labelPadding);

            int row1y = (int) ((getMaxAScore() - data.get(i).get(0)) * yaScale + padding);
            int row2y = (int) ((getMaxBScore() - data.get(i).get(1)) * ybScale + padding);
            int row3y = (int) ((getMaxAScore() - data.get(i).get(2)) * yaScale + padding);
            int row4y = (int) ((getMaxBScore() - data.get(i).get(3)) * ybScale + padding);
            int row5y = (int) ((getMaxAScore() - data.get(i).get(4)) * yaScale + padding);
            row1.add(new Point(x, row1y));
            row2.add(new Point(x, row2y));
            row3.add(new Point(x, row3y));
            row4.add(new Point(x, row4y));
            row5.add(new Point(x, row5y));
        }

        //paint background
        g2.setColor(backgroundColor);
        g2.fillRect(0,0,getWidth(),getHeight());

        g2.setColor(graphBackgroundColor);
        g2.fillRect(padding + labelPadding, padding, getWidth() - (padding * 2) - (labelPadding * 2), getHeight() - (padding * 2) - labelPadding);

        g2.setColor(axisColor);

        // create hatch marks and grid lines for y axis.
        int pointWidth = 4;
        int numberYDivisions = 10;
        for (int i = 0; i < numberYDivisions + 1; i++) {
            int x0 = padding + labelPadding;
            int x1 = pointWidth + padding + labelPadding;
            int y0 = getHeight() - ((i * (getHeight() - padding * 2 - labelPadding)) / numberYDivisions + padding + labelPadding);
            g2.setColor(gridColor);
            g2.drawLine(padding + labelPadding + 1 + pointWidth, y0, getWidth() - padding - labelPadding, y0);
            g2.setColor(labelColor);
            String y1Label = ((int) ((getMinScore() + (getMaxAScore() - getMinScore()) * ((i * 1.0) / numberYDivisions)) * 100)) / 100 + "";
            String y2Label = ((int) ((getMinScore() + (getMaxBScore() - getMinScore()) * ((i * 1.0) / numberYDivisions)) * 100)) / 100 + "";
            FontMetrics metrics = g2.getFontMetrics();
            int label1Width = metrics.stringWidth(y1Label);
            g2.drawString(y1Label, x0 - label1Width - 5, y0 + (metrics.getHeight() / 2) - 3);
            g2.drawString(y2Label, getWidth() - padding - labelPadding + 5, y0 + (metrics.getHeight() / 2) - 3);
            g2.drawLine(x0, y0, x1, y0);
        }

        // and for x axis
        for (int i = 0; i < 25; i++) {
            int x0 = i * (getWidth() - padding * 2 - labelPadding * 2) / 24 + padding + labelPadding;
            int y0 = getHeight() - padding - labelPadding;
            int y1 = y0 - pointWidth;
            int i2 = 96;
            if(getWidth() > 740) {
                i2 = i2 * 2;
            }
            if(getWidth() > 1560) {
                i2 = i2 * 3;
            }
            if ((i % ((data.size() / i2) + 1)) == 0) {
                g2.setColor(gridColor);
                g2.drawLine(x0, getHeight() - padding - labelPadding - 1 - pointWidth , x0, padding);
                g2.setColor(labelColor);
                String xLabel = i + ":00";
                FontMetrics metrics = g2.getFontMetrics();
                int labelWidth = metrics.stringWidth(xLabel);
                g2.drawString(xLabel, x0 - labelWidth / 2, y0 + metrics.getHeight() + 3);
            }
            g2.drawLine(x0, y0, x0, y1);
        }

        // create x and y*2 axes
        g2.setColor(axisColor);
        g2.drawLine(padding + labelPadding, getHeight() - padding - labelPadding, padding + labelPadding, padding);
        g2.drawLine(padding + labelPadding, getHeight() - padding - labelPadding, getWidth() - padding - labelPadding , getHeight() - padding - labelPadding);
        g2.drawLine(getWidth() - padding - labelPadding, getHeight() - padding - labelPadding, getWidth() - padding - labelPadding ,   padding);

        g2.setStroke(GRAPH_STROKE);
        if(row1Visible) {
            g2.setColor(row1Color);
            if (row1Shaded) {
                Polygon row1Polygon = new Polygon();
                row1Polygon.addPoint(labelPadding + padding, getHeight() - padding - labelPadding);
                for (Point point : row1) {
                    int x = point.x;
                    int y = point.y;
                    row1Polygon.addPoint(x, y);
                }
                row1Polygon.addPoint(getWidth() - labelPadding - padding, getHeight() - padding - labelPadding);
                g2.fillPolygon(row1Polygon);
            } else {
                for (int i = 0; i < row1.size() - 1; i++) {
                    int x1 = row1.get(i).x;
                    int y1 = row1.get(i).y;
                    int x2 = row1.get(i + 1).x;
                    int y2 = row1.get(i + 1).y;
                    g2.drawLine(x1, y1, x2, y2);
                }
            }
        }
        if(row3Visible) {
            g2.setColor(row3Color);
            if (row3Shaded) {
                Polygon row3Polygon = new Polygon();
                row3Polygon.addPoint(labelPadding + padding, getHeight() - padding - labelPadding);
                for (Point point : row3) {
                    int x = point.x;
                    int y = point.y;
                    row3Polygon.addPoint(x, y);
                }
                row3Polygon.addPoint(getWidth() - labelPadding - padding, getHeight() - padding - labelPadding);
                g2.fillPolygon(row3Polygon);
            } else {
                for (int i = 0; i < row3.size() - 1; i++) {
                    int x1 = row3.get(i).x;
                    int y1 = row3.get(i).y;
                    int x2 = row3.get(i + 1).x;
                    int y2 = row3.get(i + 1).y;
                    g2.drawLine(x1, y1, x2, y2);
                }
            }
        }
        if(row5Visible) {
            g2.setColor(row5Color);
            if (row5Shaded) {
                Polygon row5Polygon = new Polygon();
                row5Polygon.addPoint(labelPadding + padding, getHeight() - padding - labelPadding);
                for (Point point : row5) {
                    int x = point.x;
                    int y = point.y;
                    row5Polygon.addPoint(x, y);
                }
                row5Polygon.addPoint(getWidth() - labelPadding - padding, getHeight() - padding - labelPadding);
                g2.fillPolygon(row5Polygon);
            } else {
                for (int i = 0; i < row5.size() - 1; i++) {
                    int x1 = row5.get(i).x;
                    int y1 = row5.get(i).y;
                    int x2 = row5.get(i + 1).x;
                    int y2 = row5.get(i + 1).y;
                    g2.drawLine(x1, y1, x2, y2);
                }
            }
        }
        if(row2Visible) {
            g2.setColor(row2Color);
            for (int i = 0; i < row2.size() - 1; i++) {
                int x1 = row2.get(i).x;
                int y1 = row2.get(i).y;
                int x2 = row2.get(i + 1).x;
                int y2 = row2.get(i + 1).y;
                g2.drawLine(x1, y1, x2, y2);
            }
        }
        if(row4Visible) {
            g2.setColor(row4Color);
            for (int i = 0; i < row4.size() - 1; i++) {
                int x1 = row4.get(i).x;
                int y1 = row4.get(i).y;
                int x2 = row4.get(i + 1).x;
                int y2 = row4.get(i + 1).y;
                g2.drawLine(x1, y1, x2, y2);
            }
        }

        if(mouseX >= labelPadding + padding & mouseX <= getWidth() - labelPadding - padding & mouseY >= padding & mouseY <= getHeight() - padding - labelPadding & mouseGUI & getVisibleRows() > 0){
            g2.setStroke(stroke);
            g2.setColor(graphBackgroundColor);
            g2.fillRect(padding + labelPadding, padding, 300, ((valuePadding + 12) * getVisibleRows()) + 20);
            g2.setColor(gridColor);
            g2.drawRect(padding + labelPadding, padding, 300, ((valuePadding + 12) * getVisibleRows()) + 20);
            double ExactMouseXValue = (mouseX - padding -labelPadding) / xScale;

            g2.setColor(labelColor);
            g2.drawString(String.format("Values at %s:", Entries.timestamps().get((int)Math.floor(ExactMouseXValue))), padding + labelPadding + valuePadding, padding + valuePadding * 2);

            int i = 2;
            if(row1Visible) {
                g2.drawString(String.format("consumption: %s W", Math.round(data.get((int)ExactMouseXValue).get(0))), padding + labelPadding + valuePadding, padding + (valuePadding * 2) * i);
                i++;
            }
            if(row2Visible) {
                g2.drawString(String.format("consumption sum: %s kWh", Math.round(data.get((int)ExactMouseXValue).get(1))), padding + labelPadding + valuePadding, padding + (valuePadding * 2) * i);
                i++;
            }
            if(row3Visible) {
                g2.drawString(String.format("production: %s W", Math.round(data.get((int)ExactMouseXValue).get(2))), padding + labelPadding + valuePadding, padding + (valuePadding * 2) * i);
                i++;
            }
            if(row4Visible) {
                g2.drawString(String.format("production sum: %s kWh", Math.round(data.get((int)ExactMouseXValue).get(3))), padding + labelPadding + valuePadding, padding + (valuePadding * 2) * i);
                i++;
            }
            if(row5Visible) {
                g2.drawString(String.format("own consumption: %s W", Math.round(data.get((int)ExactMouseXValue).get(4))), padding + labelPadding + valuePadding, padding + (valuePadding * 2) * i);
            }
        }

        if(mouseGUI) {
            addMouseMotionListener(new MouseMotionListener() {
                @Override
                public void mouseDragged(MouseEvent mouseEvent) {

            }

            @Override
            public void mouseMoved(MouseEvent mouseEvent) {
                mouseX = mouseEvent.getX();
                mouseY = mouseEvent.getY();

                repaint();
            }
            });
        }
    }

    private double getMinScore() {
        double minScore = Double.MAX_VALUE;
        assert data != null;
        for (List<Double> score : data) {
            for (Double aDouble : score) minScore = Math.min(minScore, aDouble);
        }
        return minScore;
    }

    private double getMaxAScore() {
        double maxScore = Double.MIN_VALUE;
        assert data != null;
        for (List<Double> score : data) {
            maxScore = Math.max(maxScore, score.get(0));
            maxScore = Math.max(maxScore, score.get(2));
            maxScore = Math.max(maxScore, score.get(4));
        }
        maxScore = Math.ceil(maxScore / 1000) * 1000;
        return maxScore;
    }

    private double getMaxBScore() {
        double maxScore = Double.MIN_VALUE;
        for (List<Double> score : data) {
            maxScore = Math.max(maxScore, score.get(1));
            maxScore = Math.max(maxScore, score.get(3));
        }
        maxScore = Math.ceil(maxScore / 1000) * 1000;
        return maxScore;
    }

    public int getVisibleRows() {
        int i = 0;
        if(row1Visible) {i++;}
        if(row2Visible) {i++;}
        if(row3Visible) {i++;}
        if(row4Visible) {i++;}
        if(row5Visible) {i++;}
        return i;
    }

    public void setGridColor(Color color){
            gridColor = color;
        }

    public void setBackgroundColor(Color color){
            backgroundColor = color;
        }

    public void setGraphBackgroundColor(Color graphBackgroundColor) { this.graphBackgroundColor = graphBackgroundColor; }

    public void setAxisColor(Color color){
            axisColor = color;
        }

    public void setLabelColor(Color color){
            labelColor = color;
        }

    public void setRow1Color(Color color){
            row1Color = color;
        }

    public void setRow2Color(Color color){
            row2Color = color;
        }

    public void setRow3Color(Color color){
            row3Color = color;
        }

    public void setRow4Color(Color color){
            row4Color = color;
        }

    public void setRow5Color(Color color){
            row5Color = color;
        }

    public void setPadding(int i){
            padding = i;
        }

    public void setLabelPadding(int i){
            labelPadding = i;
        }

    public void setValuePadding(int i) { valuePadding = i; }

    public void setRow1Visible(boolean rowVisible) {
            row1Visible = rowVisible;
        }

    public void setRow2Visible(boolean rowVisible) {
            row2Visible = rowVisible;
        }

    public void setRow3Visible(boolean rowVisible) {
            row3Visible = rowVisible;
        }

    public void setRow4Visible(boolean rowVisible) {
            row4Visible = rowVisible;
        }

    public void setRow5Visible(boolean rowVisible) {
            row5Visible = rowVisible;
        }

    public void setRow1Shaded(boolean rowShaded) {
        row1Shaded = rowShaded;
    }

    public void setRow3Shaded(boolean rowShaded) {
        row3Shaded = rowShaded;
    }

    public void setRow5Shaded(boolean rowShaded) {
        row5Shaded = rowShaded;
    }

    public void setMouseGUIVisible(boolean mouseGUIVisible) {
            mouseGUI = mouseGUIVisible;
        }

    public Date getDay() {
        return day;
    }

    public String getTitle() {
        String day = new SimpleDateFormat("dd. MM. yyyy").format(getDay());
        return String.format("Visualizing %s", day);
    }

    public Color getGridColor() {
        return gridColor;
    }

    public Color getBackgroundColor() {
        return backgroundColor;
    }

    public Color getGraphBackgroundColor() {
        return graphBackgroundColor;
    }

    public Color getAxisColor() {
        return axisColor;
    }

    public Color getLabelColor() {
        return labelColor;
    }

    public Color getRow1Color() {
        return row1Color;
    }

    public Color getRow2Color() {
        return row2Color;
    }

    public Color getRow3Color() {
        return row3Color;
    }

    public Color getRow4Color() {
        return row4Color;
    }

    public Color getRow5Color() {
        return row5Color;
    }

    public int getPadding() {
        return padding;
    }

    public int getLabelPadding() {
        return labelPadding;
    }

    public int getValuePadding() {
        return valuePadding;
    }

    public boolean isRow1Visible() {
        return row1Visible;
    }

    public boolean isRow2Visible() {
        return row2Visible;
    }

    public boolean isRow3Visible() {
        return row3Visible;
    }

    public boolean isRow4Visible() {
        return row4Visible;
    }

    public boolean isRow5Visible() {
        return row5Visible;
    }

    public boolean isRow1Shaded() {
        return row1Shaded;
    }

    public boolean isRow3Shaded() {
        return row3Shaded;
    }

    public boolean isRow5Shaded() {
        return row5Shaded;
    }

    public boolean isMouseGUIVisible() {
        return mouseGUI;
    }
}
