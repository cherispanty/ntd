package com.zftx.mcdaily.bean;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * 周报  困难
 */
@Data
@Accessors(chain = true)
public class WeeklyDifficulty {
        private Integer id;
        private Integer difficultyId;
        private String  difficultyTitle;
        private String  difficultyContent;
}
