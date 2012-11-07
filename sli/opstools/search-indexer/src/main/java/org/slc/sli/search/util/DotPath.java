package org.slc.sli.search.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

public class DotPath {
    private List<String> path;
    public static DotPath EMPTY = DotPath.from(Collections.unmodifiableList(new ArrayList<String>()));
    
    public DotPath() {
        this.path = new ArrayList<String>();
    }
    
    public DotPath(String path) {
        this.path = (path  == null) ? new ArrayList<String>() : new ArrayList<String>(Arrays.asList(path.split(Constants.DOT_REGEX)));
    }
    
    @Override
    public DotPath clone() {
        return from(path);
    }
    
    public static DotPath from(List<String> path) {
        DotPath dp = new DotPath(null);
        dp.path = new ArrayList<String>(path);
        return dp;
    }
    
    public static List<String> to(DotPath dp) {
        return new ArrayList<String>(dp.path);
    }

    public int size() {
        return path.size();
    }

    public boolean isEmpty() {
        return path.isEmpty();
    }

    public Iterator<String> iterator() {
        return path.iterator();
    }

    @Override
    public String toString() {
        return StringUtils.join(this.path, Constants._DOT);
    }

    public boolean add(String e) {
        return path.add(e);
    }

    public boolean remove(Object o) {
        return path.remove(o);
    }

    public boolean addAll(Collection<? extends String> c) {
        return path.addAll(c);
    }

    public boolean addAll(int index, Collection<? extends String> c) {
        return path.addAll(index, c);
    }

    public boolean removeAll(Collection<?> c) {
        return path.removeAll(c);
    }

    @Override
    public boolean equals(Object o) {
        return path.equals(o);
    }

    @Override
    public int hashCode() {
        return path.hashCode();
    }

    public String get(int index) {
        return path.get(index);
    }

    public void add(int index, String element) {
        path.add(index, element);
    }

    public String remove(int index) {
        return path.remove(index);
    }
}
