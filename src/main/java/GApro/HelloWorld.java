/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GApro;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import io.jenetics.BitChromosome;
import io.jenetics.BitGene;
import io.jenetics.Genotype;
import io.jenetics.engine.Engine;
import io.jenetics.engine.EvolutionResult;
import io.jenetics.util.Factory;
import GApro.GenoType;
/**
 *
 * @author wangbaichao
 */
public class HelloWorld {
    // 2.) Definition of the fitness function.
//    private static int eval(Genotype<BitGene> gt) {
//        return gt.getChromosome()
//            .as(BitChromosome.class)
//            .bitCount();
//    }
    
    
 
    public static void main(String[] args) {
        // 1.) Define the genotype (factory) suitable
        //     for the problem.
//        Factory<Genotype<BitGene>> gtf =
//            Genotype.of(BitChromosome.of(8,0.5),5);
 
//        // 3.) Create the execution environment.
//        Engine<BitGene, Integer> engine = Engine
//            .builder(HelloWorld::eval, gtf)
//            .build();
// 
//        // 4.) Start the execution (evolution) and
//        //     collect the result.
//        Genotype<BitGene> result = engine.stream()
//            .limit(100)
//            .collect(EvolutionResult.toBestGenotype());
        GenoType genoType = new GenoType(20);
        String pattern = genoType.getPetternStr();
        System.out.println(pattern);
        
    }

}

