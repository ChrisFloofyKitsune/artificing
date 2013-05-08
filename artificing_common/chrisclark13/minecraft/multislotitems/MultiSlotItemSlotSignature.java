package chrisclark13.minecraft.multislotitems;

import java.util.HashMap;

public class MultiSlotItemSlotSignature {
	private static HashMap<String, MultiSlotItemSlotSignature> stringToSingatureMap = new HashMap<>();
	
	private String signatureString;
	private int width;
	private int height;
	private int centerX;
	private int centerY;
	
	private boolean designatedSlots[];
	
	public static MultiSlotItemSlotSignature getFromString(String signatureString) {
		if (stringToSingatureMap.containsKey(stringToSingatureMap)) {
			return stringToSingatureMap.get(stringToSingatureMap);
		} else {
			return new MultiSlotItemSlotSignature(signatureString);
		}
		
		
	}
	
	private MultiSlotItemSlotSignature(String signatureString) {
		this.signatureString = signatureString;
		
		String[] lines = signatureString.split("\n");
		height = lines.length;
		for (String line : lines) {
			width = (width < line.length()) ? line.length() : width;
		}
		
		designatedSlots = new boolean[width * height];
		
		for (int y = 0; y < height; y++) {
			for (int x = 0; x < lines[y].length(); x++) {
				switch(lines[y].charAt(x)) {
					case ' ':
						designatedSlots[x + (y * width)] = false;
						break;
					case 'X':
						designatedSlots[x + (y * width)] = true;
						centerX = x;
						centerY = y;
						break;
					default:
						designatedSlots[x + (y * width)] = true;
						break;
				}
			}
		}
	}

	public String getSignatureString() {
		return signatureString;
	}

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}

	public int getCenterX() {
		return centerX;
	}

	public int getCenterY() {
		return centerY;
	}
	
	/**
	 * @return The y-coordinate of the top relative to the center
	 */
	public int getRelativeTop() {
		return -centerY;
	}
	
	/**
	 * @return The y-coordinate of the bottom relative to the center
	 */
	public int getRelativeBottom() {
		return height - centerY;
	}
	
	/**
	 * @return The x-coordinate of the left side relative to the center
	 */
	
	public int getRelativeLeft() {
		return -centerX;
	}
	
	/**
	 * @return The x-coordinate of the right side relative to the center
	 */
	public int getRelativeRight() {
		return width - centerX;
	}
	
	public boolean isSlotDesignated(int x, int y) {
		return designatedSlots[x + (y * width)];
	}
	
	/**
	 * Checks if the SlotSignature is a one by one square.
	 * @return If the signature is just a one by one square.
	 */
	public boolean isOneByOne() {
		return width == 1 && height == 1;
	}
}
