package org.jts.dataparser.data.holder;

import org.jts.dataparser.data.annotations.Element;
import org.jts.dataparser.data.holder.categorydata.Category;
import org.jts.dataparser.data.holder.categorydata.CategoryDefine;
import org.mmocore.commons.data.AbstractHolder;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author : Camelion
 * @date : 26.08.12 12:11
 */
public class CategoryDataHolder extends AbstractHolder {
    private static CategoryDataHolder ourInstance = new CategoryDataHolder();
    @Element(start = "category_define_begin", end = "category_define_end")
    private List<CategoryDefine> definedCategories;
    private final Map<Category, List<Integer>> categories = new HashMap<>();

    private CategoryDataHolder() {
    }

    public static CategoryDataHolder getInstance() {
        return ourInstance;
    }

    @Override
    public int size() {
        return definedCategories.size();
    }

    public List<CategoryDefine> getDefinedCategories() {
        return definedCategories;
    }

    public List<Integer> getClasses(Category name) {
        return categories.get(name);
    }

    @Override
    public void clear() {
        definedCategories.clear();
        categories.clear();
    }

    @Override
    public void afterParsing(){
        super.afterParsing();
        for(CategoryDefine define : definedCategories){
            List<Integer> lst = new ArrayList<>();
            for(int value : define.category)
                lst.add(value);
            categories.put(Category.valueOf(define.name), lst);
        }
    }
}