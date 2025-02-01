package me.supcheg.advancedgui.api.coordinate.util;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import me.supcheg.advancedgui.api.coordinate.Coordinate;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;

import static me.supcheg.advancedgui.api.TestInventoryConstants.SLOTS_IN_ROW;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class CoordinateIterable {
    @NotNull
    public static Iterable<Coordinate> matrix(int[][]... matrix) {
        return Arrays.stream(matrix)
                .flatMap(Arrays::stream)
                .map(CoordinateIterable::coordinate)
                .toList();
    }

    public static int @NotNull [] @NotNull [] rows(int start, int amount) {
        int startIndex = start * SLOTS_IN_ROW;
        int indexAmount = amount * SLOTS_IN_ROW;

        int[][] matrix = new int[indexAmount][2];
        for (int i = 0; i < matrix.length; i++) {
            int index = startIndex + i;
            matrix[i] = new int[]{index % 9, index / 9};
        }
        return matrix;
    }

    @NotNull
    private static Coordinate coordinate(int @NotNull [] xy) {
        if (xy.length != 2) {
            throw new IllegalArgumentException("Invalid coordinate array: " + Arrays.toString(xy));
        }
        return Coordinate.coordinate(xy[0], xy[1]);
    }
}
