package me.supcheg.advancedgui.platform.paper;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.SneakyThrows;

import java.lang.reflect.Modifier;
import java.util.StringJoiner;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class ToStringHelper {
    @SneakyThrows
    public static String toStringReflectively(Object obj) {
        var joiner = new StringJoiner(", ", obj.getClass().getSimpleName() + "[", "]");

        var cursor = obj.getClass();
        while (cursor != Object.class) {
            for (var field : cursor.getDeclaredFields()) {
                if (Modifier.isStatic(field.getModifiers())) {
                    continue;
                }
                field.setAccessible(true);
                var value = field.get(obj);
                joiner.add(field.getName() + "=" + value);
            }
            cursor = cursor.getSuperclass();
        }
        return joiner.toString();
    }
}
