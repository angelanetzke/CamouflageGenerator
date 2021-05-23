/*
 * Created by Angela Netzke
 * angela.netzke@gmail.com
 * CC0 license
 */

package com.netzapps.camouflage;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import javax.imageio.ImageIO;

public class Main {

	public static void main(String[] args) throws IOException {
		//Choose your palette by commenting out all the palettes you don't want.
		//final int[] PALETTE = getDesertPalette();
		//final int[] PALETTE = getGreyPalette();
		//final int[] PALETTE = getUniversalPalette();
		final int[] PALETTE = getWoodlandPalette();
		
		final int SCALE = 512;
		final int PIXEL_SIZE = 2;
		final int WIDTH = SCALE * PIXEL_SIZE;
		final int HEIGHT = SCALE * PIXEL_SIZE;
		BufferedImage output = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_4BYTE_ABGR);
		final Random RNG = new Random();
		final List<Pixel> startPoints = new ArrayList<Pixel>();
		final int START_POINT_COUNT = WIDTH * HEIGHT / (SCALE / 2) / PIXEL_SIZE;
		for (int i = 0; i < START_POINT_COUNT; i++) {
			int x = RNG.nextInt(WIDTH / PIXEL_SIZE);
			int y = RNG.nextInt(HEIGHT / PIXEL_SIZE);
			int color = PALETTE[RNG.nextInt(PALETTE.length)];
			startPoints.add(new Pixel(x, y, color));
		}
		for (int startX = 0; startX < WIDTH; startX += PIXEL_SIZE) {
			for (int startY = 0; startY < HEIGHT; startY += PIXEL_SIZE) {
				Pixel thisPixel = new Pixel(startX / PIXEL_SIZE, startY / PIXEL_SIZE, 0);
				int color = 0;
				if (startPoints.contains(thisPixel)) {
					color = startPoints.get(startPoints.indexOf(thisPixel)).color;
				}
				else {
					for (Pixel p : startPoints) {
						p.setDistance(thisPixel, WIDTH / PIXEL_SIZE, HEIGHT / PIXEL_SIZE);
					}
					Collections.sort(startPoints);
					Pixel[] threeClosest = new Pixel[] { startPoints.get(0), startPoints.get(1), startPoints.get(2) };
					int roll = RNG
							.nextInt(threeClosest[0].getDistance() + threeClosest[1].getDistance() + threeClosest[2].getDistance());
					if (roll <= threeClosest[0].getDistance()) {
						color = threeClosest[2].color;
					}
					else if (roll < threeClosest[0].getDistance() + threeClosest[1].getDistance()) {
						color = threeClosest[1].color;
					}
					else {
						color = threeClosest[0].color;
					}					
				}
				for (int deltaX = 0; deltaX < PIXEL_SIZE; deltaX++) {
					for (int deltaY = 0; deltaY < PIXEL_SIZE; deltaY++) {
						output.setRGB(startX + deltaX, startY + deltaY, color);
					}
				}
			}
		}
		ImageIO.write(output, "png", new File("camouflage.png"));
	}

	public static int[] getUniversalPalette() {
		final int COLOR1 = (new Color(123, 102, 74)).getRGB();
		final int COLOR2 = (new Color(149, 138, 104)).getRGB();
		final int COLOR3 = (new Color(119, 119, 95)).getRGB();
		final int COLOR4 = (new Color(149, 139, 96)).getRGB();
		final int COLOR5 = (new Color(157, 155, 128)).getRGB();
		return new int[] { COLOR1, COLOR2, COLOR3, COLOR4, COLOR5 };
	}

	public static int[] getWoodlandPalette() {
		final int COLOR1 = (new Color(19, 34, 31)).getRGB();
		final int COLOR2 = (new Color(54, 89, 56)).getRGB();
		final int COLOR3 = (new Color(119, 111, 49)).getRGB();
		return new int[] { COLOR1, COLOR2, COLOR3 };
	}

	public static int[] getDesertPalette() {
		final int COLOR1 = (new Color(147, 133, 98)).getRGB();
		final int COLOR2 = (new Color(176, 158, 128)).getRGB();
		final int COLOR3 = (new Color(125, 91, 63)).getRGB();
		return new int[] { COLOR1, COLOR2, COLOR3 };
	}

	public static int[] getGreyPalette() {
		final int COLOR1 = (new Color(105, 111, 101)).getRGB();
		final int COLOR2 = (new Color(162, 155, 137)).getRGB();
		final int COLOR3 = (new Color(131, 131, 120)).getRGB();
		return new int[] { COLOR1, COLOR2, COLOR3 };
	}
	
}