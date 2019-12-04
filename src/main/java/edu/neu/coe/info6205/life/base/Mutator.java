package edu.neu.coe.info6205.life.base;


import java.util.ArrayList;
import java.util.List;

class Mutator {

    public static void Mutate(List<Integer> gene) {
        //List<Integer> gene=new ArrayList<>();
        List<Integer> mutagene=new ArrayList<>();
        for(int i: gene){
            int x=i % 10000;
            int y=(i/10000) % 10000;
            int alive=i/100000000;
            double xNum = Math.random();
            double yNum = Math.random();
            double liveNum = Math.random();
            double muteNum = Math.random();
            
            // Mutate the genecode，第一位为1/2，1代表存活，2代表死亡
            // 2-5位为y，6-9位为x
            if(muteNum < 0.5) {
                double xr = Math.random();
                if(xr < 0.5){
                    if(xNum < 0.5 && (x+10)<=1000) {
                        x+=10;
                    }
                    if(xNum >= 0.5 && (x-10)>=0){
                        x-=10;
                    }
                } else {
                    if(xNum < 0.5 && (x+1)<=1000) {
                        x++;
                    }
                    if(xNum >= 0.5 && (x-1)>=0){
                        x--;
                    }
                }  
            } else {
                double yr = Math.random();
                if(yr < 0.5) {
                    if(yNum < 0.5 && (y+10)<=1000) {
                        y+=10;
                    }
                    if(yNum >= 0.5 && (y-10)>=0){
                        y-=10;
                    }
                } else {
                    if(yNum < 0.5 && (y+1)<=1000) {
                        y++;
                    }
                    if(yNum >= 0.5 && (y-1)>=0){
                        y--;
                    }
                }
            }

            if(liveNum < 0.5) {
                if(alive==1) alive=2;
                else alive=1;
            }

            //重新拼接code
            i=alive*100000000 + y*10000 + x;
            mutagene.add(i);
        }       
        gene.clear();
        for(int i:mutagene) gene.add(i);
    }
}