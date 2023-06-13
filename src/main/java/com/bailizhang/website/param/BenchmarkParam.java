package com.bailizhang.website.param;

import lombok.Data;

@Data
public class BenchmarkParam {
    private Integer threadSize;
    private Integer connectionSize;
    private Integer keyLength;
    private Integer valueLength;
    private Integer keySize;
    private String columnFamily;
    private String column;
}
