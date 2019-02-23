package com.moroz.books.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@NoArgsConstructor
public class FilterResult {

    private Set<Category> categories;

    public FilterResult(Set<Category> categorySet) {
        this.categories = categorySet;
    }

    public Set<Category> getCategorySet() {
        return categories;
    }

    public void setCategorySet(Set<Category> categorySet) {
        this.categories = categorySet;
    }
}
