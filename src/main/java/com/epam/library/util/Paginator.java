package com.epam.library.util;

import java.util.ArrayList;
import java.util.List;

public class Paginator<T> {

    public List<T> getElementsOfPage(List<T> allElements, int targetPage, int elementsPerPage) {
        int elementNumber = allElements.size();
        List<T> elementsOfPage = new ArrayList<>();
        for (int i = (targetPage - 1) * elementsPerPage; i < targetPage * elementsPerPage && i < elementNumber; i++) {
            T elementOfPage = allElements.get(i);
            elementsOfPage.add(elementOfPage);
        }
        return elementsOfPage;
    }

    public int getNumberOfPagesNeededToFitElements(List<T> allElements, int elementsPerPage) {
        int elementNumber = allElements.size();
        return elementNumber / elementsPerPage + (elementNumber % elementsPerPage > 0 ? 1 : 0);
    }
}
