package com.kpi.laboratory;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.color.ColorSpace;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class ImageMapper {

    private final ArrayList<String> points;

    public ImageMapper(ArrayList<String> points) {
        this.points = points;
    }

    public void mapImage(int width, int height) throws IOException {
        BufferedImage image = new BufferedImage(width, height, ColorSpace.TYPE_RGB);
        Graphics2D graphics = image.createGraphics();
        graphics.setPaint(new Color(255, 255, 255));
        Point[] shapePoints = new Point[points.size()];
        Point[] borderPoints = new Point[points.size()];
        for (int i = 0; i < points.size() - 1; i++) {
            String[] coordinates = points.get(i).split(" ");
            Point point = new Point(Integer.parseInt(coordinates[1]), Integer.parseInt(coordinates[0]));
            shapePoints[i] = point;
        }
        findShape(borderPoints, shapePoints);
        graphics.fillRect(1, 1, width, height);
        graphics.setPaint(new Color(0, 0, 0));
        for (int i = 0; i < points.size() - 1; i++) {
            graphics.fillRect(shapePoints[i].x, height - shapePoints[i].y, 1, 1);
        }
        graphics.setPaint(new Color(0, 0, 255));
        for (int i = 0; i < points.size() - 1; i++) {
            if (borderPoints[i + 1] != null) {
                graphics.drawLine(borderPoints[i].x, height - borderPoints[i].y, borderPoints[i + 1].x, height - borderPoints[i + 1].y);
            }
        }
        File outFile = new File("result.png");
        ImageIO.write(image, "png", outFile);
    }

    private int getDistance(Point dot1, Point dot2) {
        return (dot2.x - dot1.x) * (dot2.x - dot1.x) + (dot2.y - dot1.y) * (dot2.y - dot1.y);
    }

    private long getVectorMultiplication(Point dot1, Point dot2, Point dot3) {
        return (long) (dot2.x - dot1.x) * (dot3.y - dot1.y) - (long) (dot3.x - dot1.x) * (dot2.y - dot1.y);
    }

    public void findShape(Point[] borderCoordinates, Point[] shapeCoordinates) {
        int j = 0;
        Point point = new Point(0, 0);
        for (int i = 0; i < points.size() - 1; i++) {
            if (shapeCoordinates[i].x > point.x)
                point.x = shapeCoordinates[i].x;
        }
        for (int i = 0; i < points.size() - 1; i++) {
            if (shapeCoordinates[i].y > point.y && shapeCoordinates[i].x == point.x) {
                point.y = shapeCoordinates[i].y;
                j = i;
            }
        }
        borderCoordinates[0] = point;
        shapeCoordinates[j] = shapeCoordinates[0];
        shapeCoordinates[0] = borderCoordinates[0];
        j = 0;
        int min = 0;
        do {
            for (int i = 1; i < points.size() - 1; i++) {
                if (getVectorMultiplication(borderCoordinates[j], shapeCoordinates[min], shapeCoordinates[i]) < 0 ||
                        getVectorMultiplication(borderCoordinates[j], shapeCoordinates[min], shapeCoordinates[i]) == 0 &&
                                getDistance(borderCoordinates[j], shapeCoordinates[min]) < getDistance(borderCoordinates[j], shapeCoordinates[i])) {
                    min = i;
                }
            }
            j++;
            borderCoordinates[j] = shapeCoordinates[min];
            min = 0;
        }
        while (!(borderCoordinates[j].x == borderCoordinates[0].x && borderCoordinates[j].y == borderCoordinates[0].y));
    }
}
