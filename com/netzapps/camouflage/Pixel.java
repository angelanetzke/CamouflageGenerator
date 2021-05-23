/*
 * Created by Angela Netzke
 * angela.netzke@gmail.com
 * CC0 license
 */

package com.netzapps.camouflage;

public final class Pixel implements Comparable<Pixel> {
	final public int x;
	final public int y;
	final public int color;
	private int distance;

	public Pixel(int x, int y, int color) {
		this.x = x;
		this.y = y;
		this.color = color;
		distance = 0;
	}

	public void setDistance(Pixel other, int width, int height) {
		setDistance(other.x, other.y, width, height);
	}

	public void setDistance(int otherX, int otherY, int width, int height) {
		int shortest = width + height;
		for (int xFactor = -1; xFactor <= 1; xFactor++) {
			for (int yFactor = -1; yFactor <= 1; yFactor++) {
				int thisDistance = Math.abs(x - (otherX + xFactor * width)) + Math.abs(y - (otherY + yFactor * height));
				if (thisDistance < shortest) {
					shortest = thisDistance;
				}
			}
		}
		distance = shortest;
	}

	public int getDistance() {
		return distance;
	}

	@Override
	public int compareTo(Pixel other) {
		if (distance < other.distance) {
			return -1;
		}
		else if (distance > other.distance) {
			return 1;
		}
		else {
			return 0;
		}
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + x;
		result = prime * result + y;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Pixel other = (Pixel) obj;
		if (x != other.x)
			return false;
		if (y != other.y)
			return false;
		return true;
	}

}
