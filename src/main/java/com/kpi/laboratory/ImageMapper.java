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
        graphics.setPaint ( new Color ( 255, 255, 255 ) );
        for(int i = 0; i < points.size()-2; i++)
        {
            String[] coordinates = points.get(i).split(" ");
            graphics.fillRect(Integer.parseInt(coordinates[1]) ,height-Integer.parseInt(coordinates[0]), 1, 1);
        }
        File outFile = new File("result.png");
        ImageIO.write(image, "png", outFile);
    }
}
