package ru.clevertec.ecl.pagination;

import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;

import java.util.Objects;

public record RestPagination<T>(CollectionModel<EntityModel<T>> content, int size, int currentPage, long overallPages) {

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof RestPagination<?> that)) return false;

        return size() == that.size() &&
                currentPage() == that.currentPage() &&
                overallPages() == that.overallPages() &&
                Objects.equals(content(), that.content());
    }

    @Override
    public int hashCode() {
        return Objects.hash(content(), size(), currentPage(), overallPages());
    }

    @Override
    public String toString() {
        return "RestPagination{" +
                "content=" + content +
                ", size=" + size +
                ", currentPage=" + currentPage +
                ", overallPages=" + overallPages +
                '}';
    }
}
