package com.everhomes.learning.sample.mission;

import java.util.List;

public class TaskAllocate {
    public static final byte RANDOM = (byte)1;
    public static final byte AVERAGE = (byte)2;

    private static final Allocator random_allocator;
    private static final Allocator average_allocator;

    private Allocator running_allocator;
    static {
        random_allocator = new Allocator(){
            @Override
            public String alloc(List<Long> machines, List<Long> tasks) {
                if (machines == null || machines.size() == 0)
                    return "";
                if (tasks == null || tasks.size() == 0)
                    return "";
                StringBuilder builder = new StringBuilder();
                String sperate = "";
                for (Long task :tasks){
                    builder.append(sperate);
                    Double random = machines.size()*Math.random();
                    builder.append(machines.get(random.intValue()));
                    builder.append("-").append(task);
                    sperate = " ";
                }
                return builder.toString();
            }
        };

        average_allocator = new Allocator(){
            @Override
            public String alloc(List<Long> machines, List<Long> tasks) {
                if (machines == null || machines.size() == 0)
                    return "";
                if (tasks == null || tasks.size() == 0)
                    return "";
                StringBuilder builder = new StringBuilder();
                String sperate = "";
                for (int i = 0;i<tasks.size();i++){
                    builder.append(sperate);
                    builder.append(machines.get(i % machines.size()));
                    builder.append("-").append(tasks.get(i));
                    sperate = " ";
                }
                return builder.toString();
            }
        };
    }

    public TaskAllocate(byte type) throws Exception {
        this.setAllocator(type);
    }

    public TaskAllocate(Allocator allocator){
        this.running_allocator = allocator;
    }

    public TaskAllocate(){

    }

    public void setAllocator(byte type) throws Exception {
        if (type != RANDOM && type != AVERAGE)
            throw new Exception("illegal allocator");
        if (type == RANDOM)
            this.running_allocator = this.random_allocator;
        else
            this.running_allocator = this.average_allocator;
    }

    public void setAllocator(Allocator allocator){
        this.running_allocator = allocator;
    }

    public String alloc(List<Long> machines, List<Long> tasks) throws Exception {
        if (this.running_allocator == null)
            throw new Exception("allocator is null");
        return this.running_allocator.alloc(machines,tasks);
    }


}
