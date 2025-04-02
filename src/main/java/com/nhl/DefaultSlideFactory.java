package com.nhl;

public class DefaultSlideFactory implements SlideFactory {
    @Override
    public Slide createSlide(String title) {
        Slide slide = new Slide();
        slide.setTitle(title);
        return slide;
    }

    @Override
    public SlideItem createSlideItem(String type, int level, String content) {
        if ("text".equalsIgnoreCase(type)) {
            return new TextItem(level, content);
        } else if ("image".equalsIgnoreCase(type)) {
            return new BitmapItem(level, content);
        }
        return null;
    }
}
