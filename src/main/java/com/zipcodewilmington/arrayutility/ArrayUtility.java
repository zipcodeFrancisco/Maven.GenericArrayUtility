package com.zipcodewilmington.arrayutility;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Created by leon on 3/6/18.
 */
public class ArrayUtility<E> {

    E[] inputArray;


    public ArrayUtility(E[] inputArray) {
        this.inputArray = inputArray;
    }

    public Integer countDuplicatesInMerge(E[] arrayToMerge, E valueToEvaluate) {

        Stream<E> resultingStream =  mergeStreams(inputArray,arrayToMerge);

        ArrayList<E> toArray = resultingStream.collect(Collectors.toCollection(ArrayList::new));
        return Collections.frequency(toArray, valueToEvaluate);
    }


    public E getMostCommonFromMerge(E[] arrayToMerge) {

        Stream<E> resultingStream = mergeStreams(inputArray,arrayToMerge);

        return mostCommon(resultingStream
                .collect(Collectors.toList()));
    }


    public Integer getNumberOfOccurrences(E valueToEvaluate) {

        Stream<E> stream = Stream.of(inputArray);

        Map<Object, Long> xx = stream
                .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));

        return Math.toIntExact(xx.get(valueToEvaluate));
    }

    public E[] removeValue(E valueToRemove) {

        E[] newArray = Arrays
                .copyOf(inputArray, inputArray.length - getNumberOfOccurrences(valueToRemove));

        return Stream.of(inputArray)
                .filter(val -> val != valueToRemove)
                .collect(Collectors.toList())
                .toArray(newArray);
    }



    public Stream mergeStreams(E[] array1, E[] array2){
        Stream<E> stream1 = Stream.of(array1);
        Stream<E> stream2 = Stream.of(array2);

        return Stream.concat(stream1,stream2);
    }

    public static <T> T mostCommon(List<T> list) {
        Map<T, Integer> map = new HashMap<>();

        for (T t : list) {
            Integer val = map.get(t);
            map.put(t, val == null ? 1 : val + 1);
        }

        Map.Entry<T, Integer> max = null;

        for (Map.Entry<T, Integer> e : map.entrySet()) {
            if (max == null || e.getValue() > max.getValue())
                max = e;
        }

        return max.getKey();
    }

}
