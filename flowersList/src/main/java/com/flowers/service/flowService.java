package com.flowers.service;

import com.flowers.entity.flower;
import org.springframework.stereotype.Service;

import java.util.List;

public interface flowService {
    List<flower> selectAll();
}
