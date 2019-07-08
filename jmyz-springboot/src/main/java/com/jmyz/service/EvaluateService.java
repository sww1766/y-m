package com.jmyz.service;

import com.jmyz.entity.Evaluate;
import com.jmyz.utils.ResponseData;

public interface EvaluateService {
    ResponseData create(Evaluate evaluate);
    ResponseData listForAdmin(Evaluate evaluate);
    ResponseData list(Evaluate evaluate);
    ResponseData detail(Evaluate evaluate);
    ResponseData delete(Evaluate evaluate);
}
