package edu.neu.coe.info6205.life.base;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class MutatorTest {
    
    @Test
    public void testMutator1() {
            List<Integer> gene=new ArrayList<>();
            gene.add(207940458);
            gene.add(200010234);
            gene.add(100870022);
            gene.add(208540034);
            List<Integer> origin=new ArrayList<>(gene);
            Mutator.Mutate(gene);
            for(int i=0; i<gene.size(); i++)
                assertNotEquals(gene.get(i), origin.get(i));
    }

    @Test
    public void testMutator2() {
            List<Integer> gene2=new ArrayList<>();
            gene2.add(210000000);
            gene2.add(100000000);
            gene2.add(110001000);
            gene2.add(208540034);
            gene2.add(100870697);
            gene2.add(208540345);
            List<Integer> origin=new ArrayList<>(gene2);
            Mutator.Mutate(gene2);
            for(int i=0; i<gene2.size(); i++)
                assertNotEquals(gene2.get(i), origin.get(i));
    }

    @Test
    public void testMutator3() {
            List<Integer> gene3=new ArrayList<>();
            gene3.add(110000000);
            List<Integer> origin=new ArrayList<>(gene3);
            Mutator.Mutate(gene3);
            for(int i=0; i<gene3.size(); i++)
                assertNotEquals(gene3.get(i), origin.get(i));
    }

}