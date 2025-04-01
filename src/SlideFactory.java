public interface SlideFactory {
    Slide createSlide(String title);
    SlideItem createSlideItem(String type, int level, String content);
}
