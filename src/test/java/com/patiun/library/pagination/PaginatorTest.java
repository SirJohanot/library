package com.patiun.library.pagination;

import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class PaginatorTest {

    private final Paginator<Integer> paginator = new Paginator<>();

    @Test
    public void testGetNumberOfPagesNeededToFitElementsShouldReturnTheRightNumberOfPagesWhenTheNumberOfElementsIsEvenAndTheNumberOfElementsPerPageIsOdd() {
        //given
        int elementsPerPage = 7;
        List<Integer> elements = Collections.nCopies(10, 5);
        int expectedNumberOfPages = 2;
        //when
        int actualNumberOfPages = paginator.getNumberOfPagesNeededToFitElements(elements, elementsPerPage);
        //then
        Assert.assertEquals(expectedNumberOfPages, actualNumberOfPages);
    }

    @Test
    public void testGetNumberOfPagesNeededToFitElementsShouldReturnTheRightNumberOfPagesWhenTheNumberOfElementsIsOddAndTheNumberOfElementsPerPageIsOdd() {
        //given
        int elementsPerPage = 15;
        List<Integer> elements = Collections.nCopies(47, 5);
        int expectedNumberOfPages = 4;
        //when
        int actualNumberOfPages = paginator.getNumberOfPagesNeededToFitElements(elements, elementsPerPage);
        //then
        Assert.assertEquals(expectedNumberOfPages, actualNumberOfPages);
    }

    @Test
    public void testGetNumberOfPagesNeededToFitElementsShouldReturnTheRightNumberOfPagesWhenTheNumberOfElementsIsOddAndTheNumberOfElementsPerPageIsEven() {
        //given
        int elementsPerPage = 2;
        List<Integer> elements = Collections.nCopies(5, 5);
        int expectedNumberOfPages = 3;
        //when
        int actualNumberOfPages = paginator.getNumberOfPagesNeededToFitElements(elements, elementsPerPage);
        //then
        Assert.assertEquals(expectedNumberOfPages, actualNumberOfPages);
    }

    @Test
    public void testGetNumberOfPagesNeededToFitElementsShouldReturnTheRightNumberOfPagesWhenTheNumberOfElementsIsEvenAndTheNumberOfElementsPerPageIsEven() {
        //given
        int elementsPerPage = 8;
        List<Integer> elements = Collections.nCopies(4, 5);
        int expectedNumberOfPages = 1;
        //when
        int actualNumberOfPages = paginator.getNumberOfPagesNeededToFitElements(elements, elementsPerPage);
        //then
        Assert.assertEquals(expectedNumberOfPages, actualNumberOfPages);
    }

    @Test
    public void testGetElementsOfPageShouldReturnASublistOfElementsOfRequiredPageWhenTheElementsCompletelyFillThePages() {
        //given
        int elementsPerPage = 5;
        List<Integer> elements = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            elements.add(i);
        }
        int targetPage = 3;
        List<Integer> expectedElementsOfTargetPage = new ArrayList<>();
        for (int i = 10; i <= 14; i++) {
            expectedElementsOfTargetPage.add(i);
        }
        //when
        List<Integer> actualElementsOfTargetPage = paginator.getElementsOfPage(elements, targetPage, elementsPerPage);
        //then
        Assert.assertEquals(expectedElementsOfTargetPage, actualElementsOfTargetPage);
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
