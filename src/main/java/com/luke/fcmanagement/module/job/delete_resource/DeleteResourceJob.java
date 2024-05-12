package com.luke.fcmanagement.module.job.delete_resource;

import com.luke.fcmanagement.constants.JobType;
import com.luke.fcmanagement.module.job.Job;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DeleteResourceJob implements Job {
    private String path;

    @Override
    public JobType getType() {
        return JobType.DELETE_RESOURCE;
    }
}
