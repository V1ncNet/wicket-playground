package de.vinado.app.playground.data.jpa;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import lombok.NonNull;

public class OffsetPageable implements Pageable {

    private final long first;
    private final long count;
    private final Sort sort;

    public OffsetPageable(long first, long count, @NonNull Sort sort) {
        if (first < 0) {
            throw new IllegalArgumentException("Page index must not be less than zero");
        }

        if (count < 1) {
            throw new IllegalArgumentException("Page size must not be less than one");
        }

        this.first = first;
        this.count = count;
        this.sort = sort;
    }

    @Override
    public int getPageNumber() {
        return Math.toIntExact(first / count);
    }

    @Override
    public int getPageSize() {
        return Math.toIntExact(count);
    }

    @Override
    public long getOffset() {
        return first;
    }

    @Override
    public Sort getSort() {
        return sort;
    }

    @Override
    public Pageable next() {
        return new OffsetPageable(first + count, count, sort);
    }

    @Override
    public Pageable previousOrFirst() {
        return hasPrevious()
            ? new OffsetPageable(first - count, count, sort)
            : first();
    }

    @Override
    public Pageable first() {
        return new OffsetPageable(0, count, sort);
    }

    @Override
    public Pageable withPage(int pageNumber) {
        if (pageNumber < 0) {
            throw new IllegalArgumentException("pageNumber must not be negative");
        }

        return new OffsetPageable((long) pageNumber * count, count, sort);
    }

    @Override
    public boolean hasPrevious() {
        return first >= count;
    }
}
