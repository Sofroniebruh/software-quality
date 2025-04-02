package com.nhl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class ObserverPatternTests {
    private Presentation presentation;
    private DummyObserver dummy;

    private static class DummyObserver implements PresentationObserver {
        private Presentation observedPresentation;
        private Slide observedSlide;
        public void update(Presentation p, Slide s) {
            observedPresentation = p;
            observedSlide = s;
        }
        public Presentation getObservedPresentation() {
            return observedPresentation;
        }
        public Slide getObservedSlide() {
            return observedSlide;
        }
    }

    @BeforeEach
    public void setUp() {
        presentation = new Presentation();
        Slide slide1 = new Slide();
        slide1.setTitle("Slide 1");
        presentation.append(slide1);
        presentation.setSlideNumber(0);
        dummy = new DummyObserver();
        presentation.registerObserver(dummy);
    }

    @Test
    public void testObserverUpdateOnSlideChange() {
        Slide slide2 = new Slide();
        slide2.setTitle("Slide 2");
        presentation.append(slide2);
        presentation.setSlideNumber(1);
        assertEquals(1, presentation.getSlideNumber());
        assertNotNull(dummy.getObservedPresentation());
        assertEquals(slide2, dummy.getObservedSlide());
    }

    @Test
    public void testObserverUpdateOnTitleChange() {
        presentation.setTitle("New Title");
        assertEquals("New Title", presentation.getTitle());
        assertNotNull(dummy.getObservedPresentation());
    }
}
