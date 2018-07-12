package com.everhomes.learning.sample.mission;

import java.util.List;

@FunctionalInterface
public interface Allocator {

    String alloc(List<Long> machines, List<Long> tasks);
}
