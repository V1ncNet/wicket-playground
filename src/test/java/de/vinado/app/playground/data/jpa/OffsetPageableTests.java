package de.vinado.app.playground.data.jpa;

import org.junit.jupiter.api.Test;
import org.springframework.data.domain.Sort;

import static org.junit.jupiter.api.Assertions.*;

class OffsetPageableTests {

    @Test
    void creatingPageableWithNegativeOffset_shouldThrowException() {
        assertThrows(IllegalArgumentException.class, () -> new OffsetPageable(-1, 10, Sort.unsorted()));
    }

    @Test
    void creatingPageableWithZeroPageSize_shouldThrowException() {
        assertThrows(IllegalArgumentException.class, () -> new OffsetPageable(0, 0, Sort.unsorted()));
    }

    @Test
    void gettingPageNumber_shouldUseOffsetDividedByPageSize() {
        var pageable = new OffsetPageable(25, 10, Sort.unsorted());

        assertEquals(2, pageable.getPageNumber());
    }

    @Test
    void gettingPageSize_shouldReturnConfiguredPageSize() {
        var pageable = new OffsetPageable(0, 15, Sort.unsorted());

        assertEquals(15, pageable.getPageSize());
    }

    @Test
    void gettingOffset_shouldReturnConfiguredOffset() {
        var pageable = new OffsetPageable(30, 10, Sort.unsorted());

        assertEquals(30, pageable.getOffset());
    }

    @Test
    void gettingSort_shouldReturnConfiguredSort() {
        var sort = Sort.by("name");
        var pageable = new OffsetPageable(0, 10, sort);

        assertSame(sort, pageable.getSort());
    }

    @Test
    void requestingNextPage_shouldIncreaseOffsetByPageSize() {
        var pageable = new OffsetPageable(20, 10, Sort.unsorted());

        assertEquals(30, pageable.next().getOffset());
    }

    @Test
    void requestingPreviousPage_shouldDecreaseOffsetByPageSizeWhenPossible() {
        var pageable = new OffsetPageable(20, 10, Sort.unsorted());

        assertEquals(10, pageable.previousOrFirst().getOffset());
    }

    @Test
    void requestingPreviousPage_shouldReturnFirstPageWhenNoPreviousExists() {
        var pageable = new OffsetPageable(5, 10, Sort.unsorted());

        assertEquals(0, pageable.previousOrFirst().getOffset());
    }

    @Test
    void requestingFirstPage_shouldReturnPageWithOffsetZero() {
        var pageable = new OffsetPageable(40, 10, Sort.unsorted());

        assertEquals(0, pageable.first().getOffset());
    }

    @Test
    void requestingSpecificPageWithNegativeIndex_shouldThrowException() {
        var pageable = new OffsetPageable(0, 10, Sort.unsorted());

        assertThrows(IllegalArgumentException.class, () -> pageable.withPage(-1));
    }

    @Test
    void requestingSpecificPage_shouldUsePageNumberAndPageSizeToCalculateOffset() {
        var pageable = new OffsetPageable(0, 10, Sort.unsorted());

        assertEquals(30, pageable.withPage(3).getOffset());
    }

    @Test
    void checkingForPreviousPage_shouldBeFalseWhenOffsetIsSmallerThanPageSize() {
        var pageable = new OffsetPageable(9, 10, Sort.unsorted());

        assertFalse(pageable.hasPrevious());
    }

    @Test
    void checkingForPreviousPage_shouldBeTrueWhenOffsetMatchesOrExceedsPageSize() {
        var pageable = new OffsetPageable(10, 10, Sort.unsorted());

        assertTrue(pageable.hasPrevious());
    }
}
