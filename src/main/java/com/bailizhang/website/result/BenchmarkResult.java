package com.bailizhang.website.result;

import lombok.Data;

@Data
public class BenchmarkResult {
    private Long totalTime;
    private Double timePerRequest;
    private Integer matchKeyCount;
}
