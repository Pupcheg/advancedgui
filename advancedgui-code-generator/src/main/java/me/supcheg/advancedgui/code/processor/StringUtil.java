package me.supcheg.advancedgui.code.processor;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
final class StringUtil {
    static String capitalize(String str) {
        return !str.isEmpty() ?
                Character.toUpperCase(str.charAt(0)) + str.substring(1) :
                str;
    }

    static String decapitalize(String str) {
        return !str.isEmpty() ?
                Character.toLowerCase(str.charAt(0)) + str.substring(1) :
                str;
    }
}
