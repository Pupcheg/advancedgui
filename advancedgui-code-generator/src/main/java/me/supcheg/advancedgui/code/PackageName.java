package me.supcheg.advancedgui.code;

import javax.lang.model.element.Name;
import javax.lang.model.element.PackageElement;

public record PackageName(String value) implements Name {
    public PackageName(PackageElement element) {
        this(element.getQualifiedName().toString());
    }

    @Override
    public int length() {
        return value.length();
    }

    @Override
    public char charAt(int index) {
        return value.charAt(index);
    }

    @Override
    public CharSequence subSequence(int start, int end) {
        return value.substring(start, end);
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean contentEquals(CharSequence cs) {
        return value.contentEquals(cs);
    }
}
