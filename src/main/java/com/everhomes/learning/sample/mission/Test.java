package com.everhomes.learning.sample.mission;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

public class Test {

    public static void main(String []args) throws Exception {
        List<Long> machines = Arrays.asList(101l,102l,103l,104l,105l);
        List<Long> tasks = Arrays.asList(1001l,1002l,1003l,1004l,1005l,1006l,1007l,1008l,1009l,1010l);
        TaskAllocate allocate1 = new TaskAllocate(TaskAllocate.RANDOM);
        System.out.println(allocate1.alloc(machines,tasks));

        TaskAllocate2 allocate2 = new TaskAllocate2();
        System.out.println(allocate2.alloc(machines,tasks));

        allocate1.setAllocator((p,q)->{
            if (p == null || p.size() == 0)
                return "";
            if (q == null || q.size() == 0)
                return "";
            StringBuilder builder = new StringBuilder();
            String sperate = "";
            for (int i = q.size()-1;i>=0;i--){
                builder.append(sperate);
                builder.append(p.get(i % p.size()));
                builder.append("-").append(q.get(i));
                sperate = " ";
            }
            return builder.toString();
        });

        System.out.println(allocate1.alloc(machines,tasks));
    }
}
