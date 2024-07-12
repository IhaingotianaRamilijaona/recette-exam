package com.api.recette.repository;

import java.util.List;

public interface RecetteRepositoryCustom {
    List<Object> searchRecette(String sql);
}
