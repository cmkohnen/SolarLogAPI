package Interface.Graph;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class DayView extends JPanel {

        private Color GridColor = Color.DARK_GRAY;
        private Color BackgroundColor = Color.WHITE;
        private Color AxisColor = Color.BLACK;
        private Color LabelColor = Color.BLACK;
        private Color Row1Color = new Color(191, 97, 106);
        private Color Row2Color = new Color(94, 129, 172);
        private Color Row3Color = new Color(235, 203, 139);
        private Color Row4Color = new Color(136, 192, 208);
        private Color Row5Color = new Color(163, 190, 140);

        private int padding = 25;
        private int labelPadding = 25;
        private final int sidespacing = 0;
        private final int pointsperday = 287;
        private final int topspacing = 0;

        private boolean Row1Visible = true;
        private boolean Row2Visible = true;
        private boolean Row3Visible = true;
        private boolean Row4Visible = true;
        private boolean Row5Visible = true;

        private static final Stroke GRAPH_STROKE = new BasicStroke(2f);
        private final List<List<Double>> data;

        public DayView(List<List<Double>> data) {
            this.data = data;
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2 = (Graphics2D) g;
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            double xScale = ((double) getWidth() - sidespacing - (2 * padding) - labelPadding - labelPadding) / (pointsperday - 1);

             double yaScale = ((double) getHeight()- topspacing - 2 * padding - labelPadding) / (getMaxaScore() - getMinScore());
             double ybScale = ((double) getHeight()- topspacing - 2 * padding - labelPadding) / (getMaxbScore() - getMinScore());

            List<Point> Row1 = new ArrayList<>();
            List<Point> Row2 = new ArrayList<>();
            List<Point> Row3 = new ArrayList<>();
            List<Point> Row4 = new ArrayList<>();
            List<Point> Row5 = new ArrayList<>();
            for (int i = 0; i < pointsperday; i++) {
                int x = (int) (i * xScale + padding + labelPadding + sidespacing);

                int Row1y = (int) ((getMaxaScore() - data.get(i).get(0)) * yaScale + padding + topspacing);
                int Row2y = (int) ((getMaxbScore() - data.get(i).get(1)) * ybScale + padding + topspacing);
                int Row3y = (int) ((getMaxaScore() - data.get(i).get(2)) * yaScale + padding + topspacing);
                int Row4y = (int) ((getMaxbScore() - data.get(i).get(3)) * ybScale + padding + topspacing);
                int Row5y = (int) ((getMaxaScore() - data.get(i).get(4)) * yaScale + padding + topspacing);
                Row1.add(new Point(x, Row1y));
                Row2.add(new Point(x, Row2y));
                Row3.add(new Point(x, Row3y));
                Row4.add(new Point(x, Row4y));
                Row5.add(new Point(x, Row5y));
            }


            //paint background
            g2.setColor(BackgroundColor);
            g2.fillRect(0,0,getWidth(),getHeight());


            g2.setColor(AxisColor);

            // create hatch marks and grid lines for y axis.
            int pointWidth = 4;
            int numberYDivisions = 10;
            for (int i = 0; i < numberYDivisions + 1; i++) {
                int x0 = padding + labelPadding + sidespacing;
                int x1 = pointWidth + padding + labelPadding + sidespacing;
                int y0 = getHeight() - ((i * (getHeight() - padding * 2 - labelPadding - topspacing)) / numberYDivisions + padding + labelPadding);
                g2.setColor(GridColor);
                g2.drawLine(padding + labelPadding + 1 + pointWidth + sidespacing, y0, getWidth() - padding - labelPadding, y0);
                g2.setColor(LabelColor);
                String y1Label = ((int) ((getMinScore() + (getMaxaScore() - getMinScore()) * ((i * 1.0) / numberYDivisions)) * 100)) / 100.0 + "";
                String y2Label = ((int) ((getMinScore() + (getMaxbScore() - getMinScore()) * ((i * 1.0) / numberYDivisions)) * 100)) / 100.0 + "";
                FontMetrics metrics = g2.getFontMetrics();
                int label1Width = metrics.stringWidth(y1Label);
                g2.drawString(y1Label, x0 - label1Width - 5, y0 + (metrics.getHeight() / 2) - 3);
                g2.drawString(y2Label, getWidth() - padding - labelPadding + 5, y0 + (metrics.getHeight() / 2) - 3);
                g2.drawLine(x0, y0, x1, y0);
            }

            // and for x axis
            for (int i = 0; i < pointsperday; i++) {
                //int x0 = i * (getWidth() - padding * 2 - labelPadding) / (scores.size() - 1) + padding + labelPadding;
                int x0 = i * (getWidth() - sidespacing - padding * 2 - labelPadding - labelPadding) / 24 + sidespacing + padding + labelPadding;
                int y0 = getHeight() - padding - labelPadding;
                int y1 = y0 - pointWidth;
                // if ((i % ((int) ((scores.size() / 20.0)) + 1)) == 0) {
                if ((i % ((pointsperday / 144) + 1)) == 0) {
                    g2.setColor(GridColor);
                    g2.drawLine(x0, getHeight() - padding - labelPadding - 1 - pointWidth , x0, padding + topspacing);
                    g2.setColor(LabelColor);
                    String xLabel = i + "";
                    FontMetrics metrics = g2.getFontMetrics();
                    int labelWidth = metrics.stringWidth(xLabel);
                    g2.drawString(xLabel, x0 - labelWidth / 2, y0 + metrics.getHeight() + 3);
                }
                g2.drawLine(x0, y0, x0, y1);
            }

            // create x and y*2 axes
            g2.setColor(AxisColor);
            g2.drawLine(padding + labelPadding + sidespacing, getHeight() - padding - labelPadding, padding + labelPadding + sidespacing, padding + topspacing);
            g2.drawLine(padding + labelPadding + sidespacing, getHeight() - padding - labelPadding, getWidth() - padding - labelPadding , getHeight() - padding - labelPadding);
            g2.drawLine(getWidth() - padding - labelPadding, getHeight() - padding - labelPadding, getWidth() - padding - labelPadding ,   padding + topspacing);

            g2.setStroke(GRAPH_STROKE);
            if(Row1Visible) {
                g2.setColor(Row1Color);
                for (int i = 0; i < Row1.size() - 1; i++) {
                    int x1 = Row1.get(i).x;
                    int y1 = Row1.get(i).y;
                    int x2 = Row1.get(i + 1).x;
                    int y2 = Row1.get(i + 1).y;
                    g2.drawLine(x1, y1, x2, y2);
                }
            }
            if(Row2Visible) {
                g2.setColor(Row2Color);
                for (int i = 0; i < Row2.size() - 1; i++) {
                    int x1 = Row2.get(i).x;
                    int y1 = Row2.get(i).y;
                    int x2 = Row2.get(i + 1).x;
                    int y2 = Row2.get(i + 1).y;
                    g2.drawLine(x1, y1, x2, y2);
                }
            }
            if(Row3Visible) {
                g2.setColor(Row3Color);
                for (int i = 0; i < Row3.size() - 1; i++) {
                    int x1 = Row3.get(i).x;
                    int y1 = Row3.get(i).y;
                    int x2 = Row3.get(i + 1).x;
                    int y2 = Row3.get(i + 1).y;
                    g2.drawLine(x1, y1, x2, y2);
                }
            }
            if(Row4Visible) {
                g2.setColor(Row4Color);
                for (int i = 0; i < Row4.size() - 1; i++) {
                    int x1 = Row4.get(i).x;
                    int y1 = Row4.get(i).y;
                    int x2 = Row4.get(i + 1).x;
                    int y2 = Row4.get(i + 1).y;
                    g2.drawLine(x1, y1, x2, y2);
                }
            }
            if(Row5Visible) {
                g2.setColor(Row5Color);
                for (int i = 0; i < Row5.size() - 1; i++) {
                    int x1 = Row5.get(i).x;
                    int y1 = Row5.get(i).y;
                    int x2 = Row5.get(i + 1).x;
                    int y2 = Row5.get(i + 1).y;
                    g2.drawLine(x1, y1, x2, y2);
                }
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

        private double getMaxaScore() {
            double maxScore = Double.MIN_VALUE;
            assert data != null;
            for (List<Double> score : data) {
                maxScore = Math.max(maxScore, score.get(0));
                maxScore = Math.max(maxScore, score.get(2));
                maxScore = Math.max(maxScore, score.get(4));
            }
            return maxScore;
        }

        private double getMaxbScore() {
            double maxScore = Double.MIN_VALUE;
            for (List<Double> score : data) {
                maxScore = Math.max(maxScore, score.get(1));
                maxScore = Math.max(maxScore, score.get(3));
            }
            return maxScore;
        }

        public void setGridColor(Color color){
            GridColor = color;
        }

        public void setBackgroundColor(Color color){
            BackgroundColor = color;
        }

        public void setAxisColor(Color color){
            AxisColor = color;
        }

        public void setLabelColor(Color color){
            LabelColor = color;
        }

        public void setRow1Color(Color color){
            Row1Color = color;
        }

        public void setRow2Color(Color color){
            Row2Color = color;
        }

        public void setRow3Color(Color color){
            Row3Color = color;
        }

        public void setRow4Color(Color color){
            Row4Color = color;
        }

         public void setRow5Color(Color color){
            Row5Color = color;
        }

        public void setPadding(int i){
            padding = i;
        }

        public void setLabelPadding(int i){
            labelPadding = i;
        }

        public void setRow1Visible(boolean rowVisible) {
            Row1Visible = rowVisible;
        }

        public void setRow2Visible(boolean rowVisible) {
            Row2Visible = rowVisible;
        }

        public void setRow3Visible(boolean rowVisible) {
            Row3Visible = rowVisible;
        }

        public void setRow4Visible(boolean rowVisible) {
            Row4Visible = rowVisible;
        }

        public void setRow5Visible(boolean rowVisible) {
            Row5Visible = rowVisible;
        }

}
