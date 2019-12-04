package edu.neu.coe.info6205.life.base;

import java.util.*;

class Selector {

    public static void Select(List<String> patternList) {
        Map<String, Long> select = new HashMap<>();
        for(String pattern: patternList){
            long generation = Game.run(0L, Point.points(pattern));
            select.put(pattern, generation);
        }

        List<Map.Entry<String, Long>> list = new ArrayList<>(select.entrySet());
        Collections.sort(list, new Comparator<Map.Entry<String, Long>>() {
            @Override
            public int compare(Map.Entry<String, Long> o1, Map.Entry<String, Long> o2) {
                return o2.getValue().compareTo(o1.getValue());   
            }
        });

        patternList.clear();
        for (int i=0; i<list.size()/2; i++) {
            patternList.add(list.get(i).getKey());
        }
    }
}