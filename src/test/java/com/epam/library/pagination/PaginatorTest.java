package com.epam.library.pagination;

import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class PaginatorTest {

    private final Paginator<Integer> paginator = new Paginator<>();

    @Test
    public void testGetNumberOfPagesNeededToFitElementsShouldReturnTheRightNumberOfPagesWhenThereAreNotEnoughElementsToCompletelyFillThePages() {
        //given
        int elementsPerPage = 4;
        List<Integer> elements = Collections.nCopies(10, 5);
        int expectedNumberOfPages = 3;
        //when
        int actualNumberOfPages = paginator.getNumberOfPagesNeededToFitElements(elements, elementsPerPage);
        //then
        Assert.assertEquals(expectedNumberOfPages, actualNumberOfPages);
    }

    @Test
    public void testGetNumberOfPagesNeededToFitElementsShouldReturnTheRightNumberOfPagesWhenThereAreEnoughElementsToCompletelyFillThePages() {
        //given
        int elementsPerPage = 10;
        List<Integer> elements = Collections.nCopies(20, 5);
        int expectedNumberOfPages = 2;
        //when
        int actualNumberOfPages = paginator.getNumberOfPagesNeededToFitElements(elements, elementsPerPage);
        //then
        Assert.assertEquals(expectedNumberOfPages, actualNumberOfPages);
    }

    @Test
    public void testGetNumberOfPagesNeededToFitElementsShouldReturnTheRightNumberOfPagesWhenThereAreNotEnoughElementsToCompletelyFillOnePage() {
        //given
        int elementsPerPage = 10;
        List<Integer> elements = Collections.nCopies(5, 5);
        int expectedNumberOfPages = 1;
        //when
        int actualNumberOfPages = paginator.getNumberOfPagesNeededToFitElements(elements, elementsPerPage);
        //then
        Assert.assertEquals(expectedNumberOfPages, actualNumberOfPages);
    }

    @Test
    public void testGetElementsOfPageShouldReturnASublistOfElementsOfRequiredPageWhenThereAreNotEnoughElementsToCompletelyFillTheLastPage() {
        //given
        int elementsPerPage = 5;
        List<Integer> elements = new ArrayList<>();
        for (int i = 1; i < 20; i++) {
            elements.add(i);
        }
        int targetPage = 4;
        List<Integer> expectedElementsOfTargetPage = new ArrayList<>();
        for (int i = 16; i < 20; i++) {
            expectedElementsOfTargetPage.add(i);
        }
        //when
        List<Integer> actualElementsOfTargetPage = paginator.getElementsOfPage(elements, targetPage, elementsPerPage);
        //then
        Assert.assertEquals(expectedElementsOfTargetPage, actualElementsOfTargetPage);
    }

    @Test
    public void testGetElementsOfPageShouldReturnASublistOfElementsOfRequiredPageWhenTheTargetPageIsNotTheLastOrTheFirst() {
        //given
        int elementsPerPage = 5;
        List<Integer> elements = new ArrayList<>();
        for (int i = 1; i < 20; i++) {
            elements.add(i);
        }
        int targetPage = 2;
        List<Integer> expectedElementsOfTargetPage = new ArrayList<>();
        for (int i = 6; i < 11; i++) {
            expectedElementsOfTargetPage.add(i);
        }
        //when
        List<Integer> actualElementsOfTargetPage = paginator.getElementsOfPage(elements, targetPage, elementsPerPage);
        //then
        Assert.assertEquals(expectedElementsOfTargetPage, actualElementsOfTargetPage);
    }

}
