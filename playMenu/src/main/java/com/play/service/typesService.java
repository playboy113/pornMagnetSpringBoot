package com.play.service;

import java.util.List;

public interface typesService {

    List<String> selectAllTypes();

    List<String> selectVideosByType(String type);
}
