package com.bala.customsearchview.model;
public class ViewModel {
    private String text;
    private String image;
    private int imageResc;

	public ViewModel(String text, String image, int imageResc) {
        this. text = text;
        this.image = image;
        this.imageResc = imageResc;
    }

    public String getText() {
        return text;
    }

    public String getImage() {
        return image;
    }
    
    public int getImageResc() {
		return imageResc;
	}
}